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
	private final VColor[][] backgroundColors;
	/** Foreground color of each tile. */
	private final VColor[][] foregroundColors;
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
		backgroundColors = new VColor[heightInTiles][widthInTiles];
		foregroundColors = new VColor[heightInTiles][widthInTiles];
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
        this.applyRenderingHints(graphics2D);

		final var laf = VTerminalLookAndFeel.getInstance();
		final var tileWidth = laf.getTileWidth();
		final var tileHeight = laf.getTileHeight();

		/*
		 * Resolve the GraphicsConfiguration once per paint instead of on every per-tile generateImage call.
		 * Component.getGraphicsConfiguration() walks up to the heavyweight peer to answer, so hoisting it out
		 * of the inner tile loop removes that walk from the hot path.
		 */
		final var graphicsConfiguration = this.getGraphicsConfiguration();

		/*
		 * For the VPanel to remain performant in a variety of situations, it is
		 * important that this paint method performs a minimal number of
		 * operations.
		 *
		 * The clip bounds, of the graphics object, represent a region of the
		 * panel that must be repainted. Rather than repainting all the tiles
		 * on every paint, it is performant to redraw only those tiles that
		 * will appear within the bounds.
		 */
		final var clipBounds = graphics2D.getClipBounds();
		final int clipX = clipBounds.x;
		final int clipY = clipBounds.y;
		final int clipWidth = clipBounds.width;
		final int clipHeight = clipBounds.height;

		final int tilesStartX = clipX / tileWidth;
		final int tilesStartY = clipY / tileHeight;
		final int tilesEndX = Math.min(codePoints[0].length, (clipX + clipWidth + tileWidth - 1) / tileWidth);
		final int tilesEndY = Math.min(codePoints.length, (clipY + clipHeight + tileHeight - 1) / tileHeight);

		/*
		 * To ensure the clip region is fully repainted and that no visual
		 * artifacts remain after the paint, we must round down to the
		 * coordinates of the nearest tile and start painting from there.
		 */
		final int initialXPosition = clipX - (clipX % tileWidth);
		int xPosition = initialXPosition;
		int yPosition = clipY - (clipY % tileHeight);

		final boolean opaque = super.isOpaque();

		for (int tilesY = tilesStartY ; tilesY < tilesEndY ; tilesY++) {
			final int[] codePointRow = codePoints[tilesY];
			final VColor[] backgroundRow = backgroundColors[tilesY];
			final VColor[] foregroundRow = foregroundColors[tilesY];
			final SequentialOp[] sequentialOpRow = sequentialImageOps[tilesY];

			/*
			 * Pass 1: fill backgrounds, coalescing consecutive tiles that share the same color into a single
			 * fillRect call. Each fillRect under the X11 XRender pipeline is an individual server round-trip,
			 * so merging runs sharply reduces the per-frame call count when neighboring tiles share a color
			 * (which is the common case for empty regions, backdrops, and uniformly-colored UI panels).
			 */
			VColor runColor = null;
			int runStartPixelX = 0;
			int runTileCount = 0;
			xPosition = initialXPosition;

			for (int tilesX = tilesStartX ; tilesX < tilesEndX ; tilesX++) {
				VColor backgroundColor = backgroundRow[tilesX];
				if (opaque) {
					backgroundColor = backgroundColor.opaque();
				}

				if (backgroundColor.getAlpha() > 0) {
					if (runColor == null) {
						runColor = backgroundColor;
						runStartPixelX = xPosition;
						runTileCount = 1;
					} else if (runColor.equals(backgroundColor)) {
						runTileCount++;
					} else {
						graphics2D.setColor(runColor);
						graphics2D.fillRect(runStartPixelX, yPosition, runTileCount * tileWidth, tileHeight);
						runColor = backgroundColor;
						runStartPixelX = xPosition;
						runTileCount = 1;
					}
				} else if (runColor != null) {
					graphics2D.setColor(runColor);
					graphics2D.fillRect(runStartPixelX, yPosition, runTileCount * tileWidth, tileHeight);
					runColor = null;
					runTileCount = 0;
				}

				xPosition += tileWidth;
			}

			if (runColor != null) {
				graphics2D.setColor(runColor);
				graphics2D.fillRect(runStartPixelX, yPosition, runTileCount * tileWidth, tileHeight);
			}

			/*
			 * Pass 2: draw glyph foregrounds on top of the filled backgrounds. Kept separate from pass 1 so
			 * that a batched background fill never overwrites glyphs drawn earlier in the same row.
			 */
			xPosition = initialXPosition;
			for (int tilesX = tilesStartX ; tilesX < tilesEndX ; tilesX++) {
				VColor foregroundColor = foregroundRow[tilesX];
				if (opaque) {
					foregroundColor = foregroundColor.opaque();
				}

				if (foregroundColor.getAlpha() > 0) {
					final int codePoint = codePointRow[tilesX];
					final var sequentialOp = sequentialOpRow[tilesX];

					final var image = laf.generateImage(codePoint, foregroundColor, sequentialOp, graphicsConfiguration);
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
	 * Calling this method is equivalent to calling
	 * {@link VPanel#resetBackgroundColors()}, {@link VPanel#resetCodePoints()},
	 * {@link VPanel#resetForegroundColors()}, and
	 * {@link VPanel#resetSequentialImageOps()}.
	 */
	public void reset() {
        this.resetBackgroundColors();
        this.resetForegroundColors();

		for (int y = 0 ; y < this.getHeightInTiles() ; y++) {
			for (int x = 0 ; x < this.getWidthInTiles() ; x++) {
				codePoints[y][x] = ' ';
				sequentialImageOps[y][x] = null;
			}
		}
	}

	/**
	 * Sets the background color of the panel and each tile to
	 * {@code UIManager.getColor("Panel.background")}.
	 */
	public void resetBackgroundColors() {
        this.setBackground(UIManager.getColor("Panel.background"));
	}

	/** Sets the code point of each tile to the space character (code point 32).  */
	public void resetCodePoints() {
		for (int y = 0 ; y < this.getHeightInTiles() ; y++) {
			for (int x = 0 ; x < this.getWidthInTiles() ; x++) {
				codePoints[y][x] = ' ';
			}
		}
	}

	/**
	 * Sets the foreground color of the panel and each tile to
	 * {@code UIManager.getColor("Panel.foreground")}.
	 */
	public void resetForegroundColors() {
        this.setForeground(UIManager.getColor("Panel.foreground"));
	}

	/** Sets the sequential image op of each tile to null. */
	public void resetSequentialImageOps() {
		for (int y = 0 ; y < this.getHeightInTiles() ; y++) {
			for (int x = 0 ; x < this.getWidthInTiles() ; x++) {
				sequentialImageOps[y][x] = null;
			}
		}
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
		return this.getScrollableUnitIncrement(visibleRect, orientation, direction);
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
                this.setBackgroundAt(x, y, new VColor(red, green, blue, alpha));
			}
		}
	}

	/**
	 * Changes the background color of a given tile.
	 *
	 * <p>If the new color differs from the tile's current background, the tile region is marked dirty via
	 * {@link #repaint(int, int, int, int)} so that only changed tiles are re-rendered on the next paint.</p>
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
			backgroundColors[y][x] = (color instanceof VColor vc) ? vc : new VColor(color);
            this.repaintTile(x, y);
		}
	}

	/**
	 * Changes the code point (Unicode character) of a given tile.
	 *
	 * <p>If the new code point differs from the tile's current one, the tile region is marked dirty via
	 * {@link #repaint(int, int, int, int)} so that only changed tiles are re-rendered on the next paint.</p>
	 *
	 * @param x X-Axis coordinate of the tile.
	 * @param y Y-Axis coordinate of the tile.
	 * @param codePoint A new code point.
	 */
	public void setCodePointAt(final int x, final int y, final int codePoint) {
		if (codePoints[y][x] != codePoint) {
			codePoints[y][x] = codePoint;
            this.repaintTile(x, y);
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
                this.setForegroundAt(x, y, new VColor(red, green, blue, alpha));
			}
		}
	}

	/**
	 * Changes the foreground color of a given tile.
	 *
	 * <p>If the new color differs from the tile's current foreground, the tile region is marked dirty via
	 * {@link #repaint(int, int, int, int)} so that only changed tiles are re-rendered on the next paint.</p>
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
			foregroundColors[y][x] = (color instanceof VColor vc) ? vc : new VColor(color);
            this.repaintTile(x, y);
		}
	}

	/**
	 * Changes the sequential image operation of a given tile.
	 *
	 * <p>If the sequential image operation is null, then no operations will be performed on the tile.</p>
	 *
	 * <p>If the new operation is a different reference from the tile's current one, the tile region is marked dirty
	 * via {@link #repaint(int, int, int, int)} so that only changed tiles are re-rendered on the next paint.</p>
	 *
	 * @param x X-Axis coordinate of the tile.
	 * @param y Y-Axis coordinate of the tile.
	 * @param sequentialOp A new sequential image operation, or null.
	 */
	public void setSequentialImageOpAt(final int x, final int y, final SequentialOp sequentialOp) {
		if (sequentialImageOps[y][x] != sequentialOp) {
			sequentialImageOps[y][x] = sequentialOp;
            this.repaintTile(x, y);
		}
	}

	/**
	 * Marks a single tile's screen region as dirty, so that the next paint cycle only re-renders that tile.
	 *
	 * <p>Multiple calls are coalesced by Swing's {@link javax.swing.RepaintManager}, so flagging several tiles
	 * in quick succession results in a single paint event with a clip covering the union of those tiles.</p>
	 *
	 * @param tileX X-Axis coordinate of the tile.
	 * @param tileY Y-Axis coordinate of the tile.
	 */
	private void repaintTile(final int tileX, final int tileY) {
		final var laf = VTerminalLookAndFeel.getInstance();
		final int tileWidth = laf.getTileWidth();
		final int tileHeight = laf.getTileHeight();
        this.repaint(tileX * tileWidth, tileY * tileHeight, tileWidth, tileHeight);
	}
}
