package com.valkryst.VTerminal.plaf;

import com.valkryst.VTerminal.font.VFont;
import com.valkryst.VTerminal.image.SequentialOp;
import com.valkryst.VTerminal.palette.Palette;
import lombok.NonNull;
import lombok.SneakyThrows;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

import static java.awt.RenderingHints.KEY_ALPHA_INTERPOLATION;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.KEY_DITHERING;
import static java.awt.RenderingHints.KEY_INTERPOLATION;
import static java.awt.RenderingHints.KEY_RENDERING;
import static java.awt.RenderingHints.KEY_STROKE_CONTROL;
import static java.awt.RenderingHints.KEY_TEXT_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.RenderingHints.VALUE_DITHER_ENABLE;
import static java.awt.RenderingHints.VALUE_INTERPOLATION_BICUBIC;
import static java.awt.RenderingHints.VALUE_RENDER_QUALITY;
import static java.awt.RenderingHints.VALUE_STROKE_PURE;
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON;

public class VTerminalLookAndFeel extends BasicLookAndFeel {
	/**
	 * Client property key for per-component high-quality rendering toggle.
	 *
	 * <p>Set {@code Boolean.TRUE} or {@code Boolean.FALSE} on any {@link JComponent} to override the
	 * {@linkplain #isHighQualityRenderingEnabled() global default} for that instance. Clear the property
	 * (pass {@code null}) to revert to the global default.</p>
	 *
	 * <pre>
	 * component.putClientProperty(VTerminalLookAndFeel.HIGH_QUALITY_RENDERING_KEY, Boolean.FALSE);
	 * component.putClientProperty(VTerminalLookAndFeel.HIGH_QUALITY_RENDERING_KEY, null); // revert
	 * </pre>
	 *
	 * @see #resolveGraphics(Graphics, JComponent)
	 */
	public static final String HIGH_QUALITY_RENDERING_KEY = "VTerminal.highQualityRendering";

	/** Global default for high-quality rendering. {@code true} unless changed via {@link #enableHighQualityRendering(boolean)}. */
	private static volatile boolean highQualityRenderingDefault = true;

	/**
	 * Cached snapshot of the desktop font rendering hints (e.g. LCD subpixel settings) published by the AWT
	 * {@link Toolkit} under the {@code awt.font.desktophints} property.
	 *
	 * <p>Fetched once at class initialization and refreshed by a {@link java.beans.PropertyChangeListener} on the
	 * {@link Toolkit}, so that {@link #setRenderingHints(Graphics)} can apply them per paint without repeatedly
	 * querying the toolkit or triggering an internal map copy inside {@link Graphics2D#setRenderingHints(Map)}.</p>
	 */
	private static volatile Map<?, ?> cachedDesktopHints;

	static {
		final var toolkit = Toolkit.getDefaultToolkit();
		cachedDesktopHints = (Map<?, ?>) toolkit.getDesktopProperty("awt.font.desktophints");
		toolkit.addPropertyChangeListener("awt.font.desktophints", evt -> cachedDesktopHints = (Map<?, ?>) evt.getNewValue());
	}

	/** The singleton instance. */
	private static VTerminalLookAndFeel instance;

	public final VFont vFont;

	/**
	 * Constructs a VTerminalLookAndFeel.
	 *
	 * @param fontInputStream An input stream for a TTF font file.
	 * @param pointSize A point size for the font which is used by all components.

	 * @throws IOException If an I/O error occurs when loading the font file.
	 * @throws FontFormatException If there is an error with the font file.
	 */
	private VTerminalLookAndFeel(final @NonNull InputStream fontInputStream, final int pointSize) throws IOException, FontFormatException {
		vFont = new VFont(fontInputStream, pointSize);
	}

	@Override
	protected void initClassDefaults(final UIDefaults table) {
		super.initClassDefaults(table);

		final var packageName = this.getClass().getPackageName();
		final var componentNames = new String[] {
			"Button",
			"CheckBox",
			"EditorPane",
			"Label",
			"Panel",
			"PasswordField",
			"ProgressBar",
			"RadioButton",
			"ScrollBar",
			"ScrollPane",
			"TabbedPane",
			"TextArea",
			"TextPane",
			"TextField",
			"ToolTip"
		};

		final var classes = new Object[componentNames.length * 2];
		int index = 0;
		for (final String componentName : componentNames) {
			classes[index++] = componentName + "UI";
			classes[index++] = packageName + ".V" + classes[index - 2];
		}

		table.putDefaults(classes);
	}

	/**
	 * Clamps the given {@link Dimension}'s width and height to the nearest multiples of the tile width and height.
	 *
	 * @param dimension {@link Dimension} to clamp.
	 *
	 * @return A new {@link Dimension} with the clamped width and height.
	 */
	public Dimension clampDimensionToTileMultiples(final Dimension dimension) {
		final int tileWidth = getTileWidth();
		final int tileHeight = getTileHeight();

		if (dimension == null) {
			return new Dimension(tileWidth, tileHeight);
		}

		// Calculate Width
		double current = dimension.getWidth();
		double min =  current < tileWidth ? tileWidth : current - (current % tileWidth);

		double width;
		if (Integer.MAX_VALUE - min < tileWidth) {
			width = (int) min;
		} else {
			double max = min + tileWidth;
			width = (int) (((max - current) <= (current - min)) ? max : min);
		}

		// Calculate Height
		current = dimension.getHeight();
		min = current < tileHeight ? tileHeight : current - (current % tileHeight);

		double height;
		if (Integer.MAX_VALUE - min < tileHeight) {
			height = (int) min;
		} else {
			double max = min + tileHeight;
			height = (int) (((max - current) <= (current - min)) ? max : min);
		}

		return new Dimension((int) width, (int) height);
	}

	public Image generateImage(final int codePoint, final Color color, final SequentialOp sequentialOp) {
		return vFont.generateImage(codePoint, color, sequentialOp);
	}

	/**
	 * Generates a glyph image targeted at {@code component}'s current {@link GraphicsConfiguration}.
	 *
	 * <p>Supplying the painting component lets the font cache produce a {@link java.awt.image.VolatileImage} matched
	 * to the display the component is being rendered on. This matters for HiDPI correctness and for avoiding
	 * {@link java.awt.image.VolatileImage#IMAGE_INCOMPATIBLE} invalidations when a window is dragged between monitors
	 * with different scales or color models.</p>
	 *
	 * @param codePoint Unicode code point to render.
	 * @param color Foreground color.
	 * @param sequentialOp Optional post-render image operation, may be {@code null}.
	 * @param component Painting component whose {@link GraphicsConfiguration} the image should match. If {@code null}
	 * 		or detached (not yet added to a container), the default-screen configuration is used.
	 *
	 * @return A glyph image, or {@code null} if the code point is whitespace or unrenderable by the font.
	 */
	public Image generateImage(final int codePoint, final Color color, final SequentialOp sequentialOp, final Component component) {
		final var gc = component != null ? component.getGraphicsConfiguration() : null;
		return vFont.generateImage(codePoint, color, sequentialOp, gc);
	}

	@Override
	public String getDescription() {
		return "The VTerminal look and feel.";
	}

	@Override
	public String getID() {
		return "VTerminal";
	}

	/**
	 * Retrieves the singleton instance and initializes it if required.
	 *
	 * @return The singleton instance.
	 */
	@SneakyThrows
	public static synchronized VTerminalLookAndFeel getInstance() {
		return instance == null ? getInstance(16) : instance;
	}

	/**
	 * Retrieves the singleton instance and initializes it if required.
	 *
	 * @param pointSize
	 * 		A point size for the font which is used by all components.
	 * 		Values less than 10 are ignored.
	 *
	 * @return The singleton instance.
	 */
	@SneakyThrows
	public static synchronized VTerminalLookAndFeel getInstance(final int pointSize) {
		if (instance == null) {
			return getInstance(VTerminalLookAndFeel.class.getResourceAsStream("/Fonts/DejaVuSansMono.ttf"), pointSize);
		}

		return instance;
	}

	/**
	 * Retrieves the singleton instance.
	 *
	 * @param fontInputStream An input stream for a TTF font file.
	 * @param pointSize
	 * 		A point size for the font which is used by all components.
	 * 		Values less than 10 are ignored.
	 *
	 * @return The singleton instance.
	 *
	 * @throws IOException If an I/O error occurs when loading the font file.
	 * @throws FontFormatException If there is an error with the font file.
	 */
	public static synchronized VTerminalLookAndFeel getInstance(final @NonNull InputStream fontInputStream, int pointSize) throws IOException, FontFormatException {
		if (instance != null) {
			System.err.println("The VTerminalLookAndFeel has already been initialized with a font. The specified font will not be applied.");
			return instance;
		} else {
			try {
				Palette.loadAndRegisterProperties(
					Objects.requireNonNull(
						VTerminalLookAndFeel.class.getResourceAsStream("/Palettes/Dracula.properties")
					)
				);
			} catch (final IOException ignored) {}
		}

		if (pointSize < 10) {
			pointSize = 10;
		}

		instance = new VTerminalLookAndFeel(fontInputStream, pointSize);
		return instance;
	}

	@Override
	public String getName() {
		return "VTerminal Look & Feel";
	}

	/**
	 * Retrieves the height of a tile, in pixels.
	 *
	 * @return The height of a tile, in pixels.
	 */
	public int getTileHeight() {
		return vFont.getMaxTileHeight();
	}

	/**
	 * Retrieves the width of a tile, in pixels.
	 *
	 * @return The width of a tile, in pixels.
	 */
	public int getTileWidth() {
		return vFont.getMaxTileWidth();
	}

	/**
	 * Returns whether high-quality rendering hints are applied by default to all components.
	 *
	 * @return {@code true} if high-quality rendering is enabled by default.
	 *
	 * @see #HIGH_QUALITY_RENDERING_KEY
	 * @see #enableHighQualityRendering(boolean)
	 */
	public static boolean isHighQualityRenderingEnabled() {
		return highQualityRenderingDefault;
	}

	/**
	 * Sets the global default for high-quality rendering hints.
	 *
	 * <p>Individual components can still override this via
	 * {@link JComponent#putClientProperty(Object, Object)} with key {@link #HIGH_QUALITY_RENDERING_KEY}.</p>
	 *
	 * @param enabled {@code true} to enable high-quality rendering by default.
	 *
	 * @see #HIGH_QUALITY_RENDERING_KEY
	 * @see #isHighQualityRenderingEnabled()
	 */
	public static void enableHighQualityRendering(final boolean enabled) {
		highQualityRenderingDefault = enabled;
	}

	/**
	 * Resolves a {@link Graphics2D} context for painting, applying high-quality {@link RenderingHints} when
	 * appropriate.
	 *
	 * <p>Resolution order:</p>
	 * <ol>
	 *   <li>If {@code component} has a {@link Boolean} client property under {@link #HIGH_QUALITY_RENDERING_KEY},
	 *       that value is used.</li>
	 *   <li>Otherwise the {@linkplain #isHighQualityRenderingEnabled() global default} is used.</li>
	 * </ol>
	 *
	 * @param graphics A {@link Graphics} context.
	 * @param component Component being painted.
	 *
	 * @return A {@link Graphics2D} context, with high-quality hints applied when enabled.
	 *
	 * @see #HIGH_QUALITY_RENDERING_KEY
	 * @see #setRenderingHints(Graphics)
	 */
	public static Graphics2D resolveGraphics(@NonNull final Graphics graphics, @NonNull final JComponent component) {
		final var prop = (Boolean) component.getClientProperty(HIGH_QUALITY_RENDERING_KEY);
		final boolean hq = prop != null ? prop : highQualityRenderingDefault;
		return hq ? setRenderingHints(graphics) : (Graphics2D) graphics;
	}

	/**
	 * Applies a set of {@link RenderingHints}, which have been deemed to be of high quality, to a {@link Graphics}
	 * context.
	 *
	 * @param graphics A {@link Graphics} context to apply the {@link RenderingHints} to.
	 *
	 * @return
	 * 		The {@link Graphics} context, cast to a {@link Graphics2D} context, with the high quality
	 * 		{@link RenderingHints} applied.
	 */
	public static Graphics2D setRenderingHints(final @NonNull Graphics graphics) {
		final var graphics2D = (Graphics2D) graphics;

		/*
		 * Apply the desktop font hints entry-by-entry rather than via setRenderingHints(Map). The bulk form
		 * internally copies the map (allocating a HashSet of entries per call), which becomes a per-paint
		 * allocation on every UI delegate. Iterating the cached entry set avoids that copy entirely.
		 */
		final var desktopHints = cachedDesktopHints;
		if (desktopHints != null) {
			for (final var entry : desktopHints.entrySet()) {
				graphics2D.setRenderingHint((RenderingHints.Key) entry.getKey(), entry.getValue());
			}
		}

		graphics2D.setRenderingHint(KEY_ALPHA_INTERPOLATION, VALUE_ALPHA_INTERPOLATION_QUALITY);
		graphics2D.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
		graphics2D.setRenderingHint(KEY_DITHERING, VALUE_DITHER_ENABLE);
		graphics2D.setRenderingHint(KEY_INTERPOLATION, VALUE_INTERPOLATION_BICUBIC);
		graphics2D.setRenderingHint(KEY_RENDERING, VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHint(KEY_STROKE_CONTROL, VALUE_STROKE_PURE);
		graphics2D.setRenderingHint(KEY_TEXT_ANTIALIASING, VALUE_TEXT_ANTIALIAS_ON);

		return graphics2D;
	}

	/**
	 * Sets the given component's font to the one used by this look-and-feel.
	 *
	 * @param component A component.
	 */
	public void setComponentFont(final @NonNull JComponent component) {
		component.setFont(vFont.getFont());
	}

	@Override
	public boolean isNativeLookAndFeel() {
		return false;
	}

	@Override
	public boolean isSupportedLookAndFeel() {
		return true;
	}
}
