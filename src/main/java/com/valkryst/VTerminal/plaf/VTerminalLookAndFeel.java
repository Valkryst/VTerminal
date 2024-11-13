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

public class VTerminalLookAndFeel extends BasicLookAndFeel {
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

		// Automatically detect the best text rendering settings and apply them.
		final var desktopHints = (Map<?, ?>) Toolkit.getDefaultToolkit().getDesktopProperty("awt.font.desktophints");
		if (desktopHints != null) {
			graphics2D.setRenderingHints(desktopHints);
		}
		
		graphics2D.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

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
