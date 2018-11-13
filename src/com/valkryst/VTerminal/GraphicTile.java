package com.valkryst.VTerminal;

import com.valkryst.VTerminal.misc.ImageCache;
import com.valkryst.VTerminal.shader.Shader;
import com.valkryst.VTerminal.shader.character.CharShader;
import lombok.NonNull;
import lombok.ToString;

import java.awt.*;

@ToString
public class GraphicTile extends Tile {
    /**
     * Constructs a new GraphicTile.
     *
     * @param character
     *         The character.
     */
	public GraphicTile(final char character) {
	    super(character);
    }

    /**
     * Constructs a new GraphicTile by copying the data of a Tile.
     *
     * @param tile
     *         The tile.
     *
     * @throws NullPointerException
     *         If the tile is null.
     */
    public GraphicTile(final @NonNull Tile tile) {
	    super(tile.getCharacter());

	    for (final Shader shader : tile.getShaders()) {
	        addShaders(shader);
        }

	    super.setHidden(tile.isHidden());

	    super.setBackgroundColor(tile.getBackgroundColor());
	    super.setForegroundColor(tile.getForegroundColor());

        super.setUnderlined(tile.isUnderlined());
        super.setUnderlineThickness(tile.getUnderlineThickness());

        cacheHash = super.hash();
    }

    /**
     * Draws the tile onto the specified context.
     *
     * @param gc
     *         The graphics context to draw with.
     *
     * @param imageCache
     *         The image cache to retrieve character images from.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate where the character is to be drawn.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate where the character is to be drawn.
     *
     * @throws NullPointerException
     *         If the gc or image cache are null.
     */
    @Override
    public void draw(final @NonNull Graphics2D gc, final @NonNull ImageCache imageCache, int columnIndex, int rowIndex) {
        final int fontWidth = imageCache.getFont().getWidth();
        final int fontHeight = imageCache.getFont().getHeight();

        columnIndex *= fontWidth;
        rowIndex *= fontHeight;

        // Handle hidden state:
        if (super.isHidden()) {
            gc.setColor(super.getBackgroundColor());
            gc.fillRect(columnIndex, rowIndex, fontWidth, fontHeight);
        } else {
            final Image image = imageCache.retrieve(this);
            gc.drawImage(image, columnIndex, rowIndex, null);
        }
    }


    @Override
    public void addShaders(final Shader... shaders) {
        if (shaders != null) {
            for (final Shader shader : shaders) {
                if (shader instanceof CharShader == false) {
                    super.addShaders(shader);
                }
            }

            cacheHash = super.hash();
        }
    }
}