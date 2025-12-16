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
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class VFont {
	@Getter private final Font font;
	private final Cache<Integer, Image> imageCache;

	@Getter private final int maxTileWidth;
	@Getter private final int maxTileHeight;
	private final int fontAscent;

	public VFont(final @NonNull InputStream inputStream, final int pointSize) throws IOException, FontFormatException {
		font = Font.createFont(Font.TRUETYPE_FONT, inputStream)
				   .deriveFont(Font.PLAIN, pointSize);

		imageCache = Caffeine.newBuilder()
							 .initialCapacity(24)
							 .maximumSize(50_000)
							 .expireAfterAccess(5, TimeUnit.MINUTES)
							 .build();

		final var fontMetrics = getFontMetrics();
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

	public Image generateImage(final int codePoint, final @NonNull Color color, final SequentialOp sequentialOp) {
		if (!Character.isValidCodePoint(codePoint)) {
			throw new IllegalArgumentException(codePoint + " is not a valid code point.");
		}

		final var hash = Objects.hash(codePoint, color, sequentialOp);
		final var cachedImage = imageCache.getIfPresent(hash);
		if (cachedImage != null) {
			if (cachedImage instanceof VolatileImage) {
				if (!((VolatileImage) cachedImage).contentsLost()) {
					return cachedImage;
				}
			} else {
				return cachedImage;
			}
		}

		if (!font.canDisplay(codePoint) || Character.isWhitespace(codePoint)) {
			return null;
		}

		final var fontMetrics = getFontMetrics();
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

		/*
		 * We could manually convert the BufferedImage into a VolatileImage
		 * using GraphicsConfiguration#createCompatibleVolatileImage. This would
		 * most likely increase performance.
		 *
		 * Doing this will also cause graphical issues when dragging a J/VFrame
		 * between monitors. All the tiles will render as black rectangles
		 * because the VolatileImages are generated to be displayed on the
		 * GraphicsDevice that the frame is initially displayed on.
		 *
		 * Workarounds for this black rectangle issue aren't incredibly
		 * difficult, but it isn't currently worth the effort and additional
		 * code complexity.
		 *
		 * ---
		 *
		 * It may not be necessary to perform this manual conversion as the
		 * runtime engine will automatically convert BufferedImages to
		 * VolatileImages when it detects that it will provide a speed
		 * advantage.
		 *
		 * Source: https://kitfox.com/projects/javaOne2007/javaOne-notes.pdf
		 */
		imageCache.put(hash, image);
		return image;
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

	private FontMetrics getFontMetrics() {
		final var image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		final var graphics = image.createGraphics();
        this.applyRenderingHints(graphics, 0);
		graphics.setFont(font);

		return graphics.getFontMetrics();
	}
}
