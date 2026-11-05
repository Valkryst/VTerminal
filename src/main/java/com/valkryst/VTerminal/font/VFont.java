package com.valkryst.VTerminal.font;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.valkryst.VTerminal.image.SequentialOp;
import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;
import lombok.Getter;
import lombok.NonNull;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class VFont {
	@Getter private final Font font;
	private final Cache<GlyphKey, GlyphEntry> imageCache;

	@Getter private final int maxTileWidth;
	@Getter private final int maxTileHeight;
	private final int fontAscent;
	private final FontMetrics fontMetrics;

	/**
	 * Cache key identifying a rendered glyph.
	 *
	 * @param codePoint Unicode code point.
	 * @param color Foreground color.
	 * @param sequentialOp Optional post-render image operation, may be {@code null}.
	 */
	private record GlyphKey(int codePoint, Color color, SequentialOp sequentialOp) {}

	/**
	 * Cached glyph image pair: the authoritative {@link BufferedImage} source and an optional hardware-accelerated
	 * {@link VolatileImage} tied to a specific {@link GraphicsConfiguration}.
	 *
	 * <p>The {@code source} is immutable and reusable across displays. The {@code accelerated} image is tied to a
	 * single GC and may be invalidated by display changes, lost contents, or incompatible configurations; when that
	 * happens it is recreated from {@code source}.</p>
	 */
	private static final class GlyphEntry {
		final BufferedImage source;
		volatile VolatileImage accelerated;
		volatile GraphicsConfiguration acceleratedGc;

		GlyphEntry(final BufferedImage source) {
			this.source = source;
		}
	}

	public VFont(final @NonNull InputStream inputStream, final int pointSize) throws IOException, FontFormatException {
		font = Font.createFont(Font.TRUETYPE_FONT, inputStream)
				   .deriveFont(Font.PLAIN, pointSize);

		imageCache = Caffeine.newBuilder()
							 .initialCapacity(24)
							 .maximumSize(50_000)
							 .expireAfterAccess(5, TimeUnit.MINUTES)
							 .build();

		fontMetrics = computeFontMetrics();
		maxTileWidth = fontMetrics.charWidth('A');
		maxTileHeight = fontMetrics.getHeight();
		fontAscent = fontMetrics.getAscent();

		/*
		 * The user can reconfigure their desktop environment while the program
		 * is running. This can affect the awt.font.desktophints that are used
		 * when rendering images.
		 *
		 * If the awt.font.desktophints are altered, then we need to clear the
		 * cache and allow all new renders to take advantage of the new hints.
		 *
		 * Source: https://docs.oracle.com/en/java/javase/15/docs/api/java.desktop/java/awt/doc-files/DesktopProperties.html
		 */
		Toolkit.getDefaultToolkit().addPropertyChangeListener("awt.font.desktophints", event -> {
			if (event.getPropertyName().equals("awt.font.desktophints")) {
				if (!event.getOldValue().equals(event.getNewValue())) {
					imageCache.invalidateAll();
				}
			}
		});
	}

	/**
	 * Generates (or retrieves from cache) a rendered glyph image for the default screen's graphics configuration.
	 *
	 * <p>Prefer {@link #generateImage(int, Color, SequentialOp, GraphicsConfiguration)} when a target
	 * {@link GraphicsConfiguration} is available — passing the caller's GC yields correct HiDPI rendering and avoids
	 * {@link VolatileImage#IMAGE_INCOMPATIBLE} invalidations when a window spans multiple displays.</p>
	 *
	 * @param codePoint Unicode code point to render.
	 * @param color Foreground color.
	 * @param sequentialOp Optional post-render image operation, may be {@code null}.
	 *
	 * @return A glyph image, or {@code null} if the code point is whitespace or unrenderable by the font.
	 */
	public Image generateImage(final int codePoint, final @NonNull Color color, final SequentialOp sequentialOp) {
		return generateImage(codePoint, color, sequentialOp, defaultGraphicsConfiguration());
	}

	/**
	 * Generates (or retrieves from cache) a rendered glyph image targeted at the given {@link GraphicsConfiguration}.
	 *
	 * <p>The returned image may be a hardware-accelerated {@link VolatileImage} matched to {@code gc}. If a previously
	 * cached {@link VolatileImage} was created for a different GC, has lost its contents, or has become incompatible
	 * (e.g. the window moved to a different display), it is transparently recreated or restored from the authoritative
	 * {@link BufferedImage} source.</p>
	 *
	 * @param codePoint Unicode code point to render.
	 * @param color Foreground color.
	 * @param sequentialOp Optional post-render image operation, may be {@code null}.
	 * @param gc Target {@link GraphicsConfiguration}; {@code null} falls back to the default screen configuration.
	 *
	 * @return A glyph image, or {@code null} if the code point is whitespace or unrenderable by the font.
	 *
	 * @throws IllegalArgumentException If {@code codePoint} is not a valid Unicode code point.
	 */
	public Image generateImage(final int codePoint, final @NonNull Color color, final SequentialOp sequentialOp, GraphicsConfiguration gc) {
		if (!Character.isValidCodePoint(codePoint)) {
			throw new IllegalArgumentException(codePoint + " is not a valid code point.");
		}

		if (gc == null) {
			gc = defaultGraphicsConfiguration();
		}

		final var key = new GlyphKey(codePoint, color, sequentialOp);
		var entry = imageCache.getIfPresent(key);
		if (entry == null) {
			if (!font.canDisplay(codePoint) || Character.isWhitespace(codePoint)) {
				return null;
			}
			entry = new GlyphEntry(renderSource(codePoint, color, sequentialOp));
			imageCache.put(key, entry);
		}

		return acquireAccelerated(entry, gc);
	}

	/**
	 * Renders the authoritative {@link BufferedImage} for a glyph. The result is cached once per key and is reused to
	 * (re)seed {@link VolatileImage} copies as they are created or restored.
	 */
	private BufferedImage renderSource(final int codePoint, final Color color, final SequentialOp sequentialOp) {
		final var charWidth = fontMetrics.charWidth(codePoint);
		final var imageWidth = Math.max(charWidth, maxTileWidth);
		var image = new BufferedImage(imageWidth, maxTileHeight, Transparency.TRANSLUCENT);

		final var graphics = image.createGraphics();
		this.applyRenderingHints(graphics, codePoint);
		graphics.setFont(font);

		graphics.setColor(new Color(0, 0, 0, 0));
		graphics.fillRect(0, 0, image.getWidth(), image.getHeight());

		graphics.setColor(color);
		graphics.drawString(Character.toString(codePoint), 0, fontAscent);

		graphics.dispose();

		// Allows non-monospaced fonts, but roughly scales them to be monospaced.
		if (charWidth > maxTileWidth) {
			final double scaleWidth = maxTileWidth / (double) charWidth;
			final AffineTransform tx = AffineTransform.getScaleInstance(scaleWidth, 1);
			final AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);
			image = op.filter(image, null);
		}

		if (sequentialOp != null) {
			image = sequentialOp.filter(image, null);
		}

		return image;
	}

	/**
	 * Returns a usable image for {@code entry}, preferring a {@link VolatileImage} matched to {@code gc}.
	 *
	 * <p>Handles the full VolatileImage lifecycle: creates one when missing, recreates it on {@link VolatileImage#IMAGE_INCOMPATIBLE}
	 * or when the cached accelerated image was built for a different {@link GraphicsConfiguration}, and redraws it
	 * from the source on {@link VolatileImage#IMAGE_RESTORED} or when contents are lost. If hardware acceleration is
	 * unavailable, falls back to the {@link BufferedImage} source.</p>
	 */
	private Image acquireAccelerated(final GlyphEntry entry, final GraphicsConfiguration gc) {
		try {
			var accelerated = entry.accelerated;
			final var acceleratedGc = entry.acceleratedGc;

			if (accelerated == null || acceleratedGc != gc) {
				accelerated = createAccelerated(entry.source, gc);
				entry.accelerated = accelerated;
				entry.acceleratedGc = gc;
				return accelerated;
			}

			final int status = accelerated.validate(gc);
			if (status == VolatileImage.IMAGE_INCOMPATIBLE) {
				accelerated = createAccelerated(entry.source, gc);
				entry.accelerated = accelerated;
				entry.acceleratedGc = gc;
				return accelerated;
			}

			if (status == VolatileImage.IMAGE_RESTORED || accelerated.contentsLost()) {
				redrawAccelerated(accelerated, entry.source);
			}

			return accelerated;
		} catch (final Exception ignored) {
			return entry.source;
		}
	}

	/**
	 * Creates a new {@link VolatileImage} sized to {@code source} and compatible with {@code gc}, then copies
	 * {@code source} into it. The copy uses {@link AlphaComposite#Src} so transparent pixels do not accumulate from
	 * whatever the accelerated backing buffer happened to contain.
	 */
	private VolatileImage createAccelerated(final BufferedImage source, final GraphicsConfiguration gc) {
		final var volatileImage = gc.createCompatibleVolatileImage(
			source.getWidth(),
			source.getHeight(),
			Transparency.TRANSLUCENT
		);
		redrawAccelerated(volatileImage, source);
		return volatileImage;
	}

	/** Blits {@code source} onto {@code target} using {@link AlphaComposite#Src}. */
	private void redrawAccelerated(final VolatileImage target, final BufferedImage source) {
		final var graphics = target.createGraphics();
		try {
			graphics.setComposite(AlphaComposite.Src);
			graphics.drawImage(source, 0, 0, null);
		} finally {
			graphics.dispose();
		}
	}

	/**
	 * Returns the default screen's {@link GraphicsConfiguration}, used when callers do not supply one.
	 *
	 * @return The default screen configuration, or {@code null} in headless environments.
	 */
	private GraphicsConfiguration defaultGraphicsConfiguration() {
		try {
			return GraphicsEnvironment
				.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice()
				.getDefaultConfiguration();
		} catch (final Exception ignored) {
			return null;
		}
	}

	private void applyRenderingHints(Graphics2D graphics, final int codePoint) {
		graphics = VTerminalLookAndFeel.setRenderingHints(graphics);

		if (isGraphicCharacter(codePoint)) {
			graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		}
	}

	private boolean isGraphicCharacter(final int codePoint) {
		final var unicodeBlock = Character.UnicodeBlock.of(codePoint);
		return (unicodeBlock == Character.UnicodeBlock.BOX_DRAWING) ||
				(unicodeBlock == Character.UnicodeBlock.BLOCK_ELEMENTS) ||
				(unicodeBlock == Character.UnicodeBlock.GEOMETRIC_SHAPES) ||
				(unicodeBlock == Character.UnicodeBlock.GEOMETRIC_SHAPES_EXTENDED);
	}

	/**
	 * Computes the {@link FontMetrics} for {@link #font} using a transient 1x1 off-screen {@link Graphics2D}. Called
	 * once from the constructor; the result is cached in {@link #fontMetrics} for reuse on every glyph render.
	 *
	 * @return {@link FontMetrics} for {@link #font} under the project's standard rendering hints.
	 */
	private FontMetrics computeFontMetrics() {
		final var image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		final var graphics = image.createGraphics();
		this.applyRenderingHints(graphics, 0);
		graphics.setFont(font);

		final var metrics = graphics.getFontMetrics();
		graphics.dispose();
		return metrics;
	}
}
