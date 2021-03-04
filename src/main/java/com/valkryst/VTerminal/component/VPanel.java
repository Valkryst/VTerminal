package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.image.SequentialOp;
import com.valkryst.VTerminal.palette.VColor;
import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;

import javax.swing.*;
import java.awt.*;

public class VPanel extends JPanel implements Scrollable {
	/** Code point of each tile. */
	private final int[][] codePoints;
	/** Background color of each tile. */
	private final Color[][] backgroundColors;
	/** Foreground color of each tile. */
	private final Color[][] foregroundColors;
	/** Sequential image operation of each tile. */
	private final SequentialOp[][] sequentialImageOps;

	/**
	 * Constructs a new instance of {@code VPanel}.
	 *
	 * @param widthInTiles Width of the panel, in tiles.
	 * @param heightInTiles Height of the panel, in tiles.
	 */
	public VPanel(final int widthInTiles, final int heightInTiles) {
		if (widthInTiles < 1) {
			throw new IllegalArgumentException("The width must be >= 1.");
		}

		if (heightInTiles < 1) {
			throw new IllegalArgumentException("The height must be >= 1.");
		}

		codePoints = new int[heightInTiles][widthInTiles];
		backgroundColors = new Color[heightInTiles][widthInTiles];
		foregroundColors = new Color[heightInTiles][widthInTiles];
		sequentialImageOps = new SequentialOp[heightInTiles][widthInTiles];

		final var backgroundColor = super.getBackground();
		final var foregroundColor = super.getForeground();
		for (int y = 0; y < heightInTiles; y++) {
			for (int x = 0; x < widthInTiles; x++) {
				codePoints[y][x] = ' ';
				backgroundColors[y][x] = new VColor(backgroundColor);
				foregroundColors[y][x] = new VColor(foregroundColor);
			}
		}
	}

	private void applyRenderingHints(final Graphics2D graphics) {
		graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		graphics.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
		graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		graphics.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
	}

	@Override
	public void paintComponent(final Graphics graphics) {
		super.paintComponent(graphics);

		final var graphics2D = (Graphics2D) graphics.create();
		applyRenderingHints(graphics2D);

		final var laf = VTerminalLookAndFeel.getInstance();
		final var tileWidth = laf.getTileWidth();
		final var tileHeight = laf.getTileHeight();

		/*
		 * For the VPanel to remain performant in a variety of situations, it is
		 * important that this paint method performs a minimal number of
		 * operations.
		 *
		 * The clip bounds, of the graphics object, represent a region of the
		 * panel that must be repainted. Rather than repainting all of the tiles
		 * on every paint, it is performant to redraw only those tiles that
		 * will appear within the bounds.
		 */
		final var clipBounds = graphics2D.getClip().getBounds2D();

		final int tilesStartX = (int) (clipBounds.getX() / tileWidth);
		final int tilesStartY = (int) (clipBounds.getY() / tileHeight);
		int tilesEndX = (int) Math.ceil((clipBounds.getX() + clipBounds.getWidth()) / (double) tileWidth);
		tilesEndX = Math.min(codePoints[0].length, tilesEndX);
		int tilesEndY = (int) Math.ceil((clipBounds.getY() + clipBounds.getHeight()) / (double) tileHeight);
		tilesEndY = Math.min(codePoints.length, tilesEndY);

		/*
		 * To ensure the clip region is fully repainted and that no visual
		 * artifacts remain after the paint, we must round down to the
		 * coordinates of the nearest tile and start painting from there.
		 */
		final int initialXPosition = (int) (clipBounds.getX() - (clipBounds.getX() % tileWidth));
		int xPosition = initialXPosition;
		int yPosition = (int) (clipBounds.getY() - (clipBounds.getY() % tileHeight));

		for (int tilesY = tilesStartY ; tilesY < tilesEndY ; tilesY++) {
			for (int tilesX = tilesStartX ; tilesX < tilesEndX ; tilesX++) {
				var backgroundColor = backgroundColors[tilesY][tilesX];
				var foregroundColor = foregroundColors[tilesY][tilesX];

				if (super.isOpaque()) {
					if (backgroundColor.getAlpha() != 255) {
						backgroundColor = new Color(backgroundColor.getRGB(), false);
					}

					if (foregroundColor.getAlpha() != 255) {
						foregroundColor = new Color(foregroundColor.getRGB(), false);
					}
				}

				if (backgroundColor.getAlpha() > 0) {
					graphics2D.setColor(backgroundColor);
					graphics2D.fillRect(xPosition, yPosition, tileWidth, tileHeight);
				}

				if (foregroundColor.getAlpha() > 0) {
					final int codePoint = codePoints[tilesY][tilesX];
					final var sequentialOp = sequentialImageOps[tilesY][tilesX];

					final var image = laf.generateImage(codePoint, foregroundColor, sequentialOp);
					if (image != null) {
						graphics2D.drawImage(image, xPosition, yPosition, null);
					}
				}

				xPosition += tileWidth;
			}

			xPosition = initialXPosition;
			yPosition += tileHeight;
		}

		graphics2D.dispose();
	}

	/**
	 * Retrieves the panel's height, in tiles.
	 *
	 * @return The panel's height, in tiles.
	 */
	public int getHeightInTiles() {
		return codePoints.length;
	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return super.getPreferredSize();
	}

	@Override
	public int getScrollableUnitIncrement(final Rectangle visibleRect, final int orientation, final int direction) {
		final var laf = VTerminalLookAndFeel.getInstance();

		if (orientation == SwingConstants.HORIZONTAL) {
			return laf.getTileWidth();
		}

		if (orientation == SwingConstants.VERTICAL) {
			return laf.getTileHeight();
		}

		return 0;
	}

	@Override
	public int getScrollableBlockIncrement(final Rectangle visibleRect, final int orientation, final int direction) {
		return getScrollableUnitIncrement(visibleRect, orientation, direction);
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}

	/**
	 * Retrieves the panel's width, in tiles.
	 *
	 * @return The panel's width, in tiles.
	 */
	public int getWidthInTiles() {
		return codePoints[0].length;
	}

	@Override
	public void setBackground(Color color) {
		if (color == null) {
			color = UIManager.getColor("Panel.background");
		}


		super.setBackground(color);
		if (backgroundColors == null) {
			return;
		}

		final int alpha = color.getAlpha();
		final int blue = color.getBlue();
		final int green = color.getGreen();
		final int red = color.getRed();

		for (int y = 0; y < backgroundColors.length; y++) {
			for (int x = 0; x < backgroundColors[0].length; x++) {
				setBackgroundAt(x, y, new VColor(red, green, blue, alpha));
			}
		}
	}

	/**
	 * Changes the background color of a given tile.
	 *
	 * @param x X-Axis coordinate of the tile.
	 * @param y Y-Axis coordinate of the tile.
	 * @param color A new color.
	 */
	public void setBackgroundAt(final int x, final int y, Color color) {
		if (color == null) {
			color = UIManager.getColor("Panel.background");
		}

		if (!backgroundColors[y][x].equals(color)) {
			backgroundColors[y][x] = new VColor(color);
		}
	}

	/**
	 * Changes the code point (Unicode character) of a given tile.
	 *
	 * @param x X-Axis coordinate of the tile.
	 * @param y Y-Axis coordinate of the tile.
	 * @param codePoint A new code point.
	 */
	public void setCodePointAt(final int x, final int y, final int codePoint) {
		if (codePoints[y][x] != codePoint) {
			codePoints[y][x] = codePoint;
		}
	}

	@Override
	public void setForeground(Color color) {
		if (color == null) {
			color = UIManager.getColor("Panel.foreground");
		}

		super.setForeground(color);
		if (foregroundColors == null) {
			return;
		}

		final int alpha = color.getAlpha();
		final int blue = color.getBlue();
		final int green = color.getGreen();
		final int red = color.getRed();

		for (int y = 0; y < foregroundColors.length; y++) {
			for (int x = 0; x < foregroundColors[0].length; x++) {
				setForegroundAt(x, y, new VColor(red, green, blue, alpha));
			}
		}
	}

	/**
	 * Changes the foreground color of a given tile.
	 *
	 * @param x X-Axis coordinate of the tile.
	 * @param y Y-Axis coordinate of the tile.
	 * @param color A new color.
	 */
	public void setForegroundAt(final int x, final int y, Color color) {
		if (color == null) {
			color = UIManager.getColor("Panel.foreground");
		}

		if (!foregroundColors[y][x].equals(color)) {
			foregroundColors[y][x] = new VColor(color);
		}
	}

	/**
	 * Changes the sequential image operation of a given tile.
	 *
	 * If the sequential image operation is null, then no operations will be
	 * performed on the tile.
	 *
	 * @param x X-Axis coordinate of the tile.
	 * @param y Y-Axis coordinate of the tile.
	 * @param sequentialOp A new sequential image operation, or null.
	 */
	public void setSequentialImageOpAt(final int x, final int y, final SequentialOp sequentialOp) {
		sequentialImageOps[y][x] = sequentialOp;
	}
}
