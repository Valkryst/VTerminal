package com.valkryst.VTerminal;

import com.valkryst.VTerminal.misc.ImageCache;
import com.valkryst.VTerminal.shader.Shader;
import com.valkryst.VTerminal.shader.character.CharShader;
import lombok.NonNull;
import lombok.ToString;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Objects;

@ToString
public class GraphicTile extends Tile {
    /**
     * Constructs a new AsciiTile.
     *
     * @param character
     *         The character.
     */
	public GraphicTile(final char character) {
	    super(character);
    }

    /**
     * Constructs a new AsciiTile by copying the data of an AsciiCharacter.
     *
     * Does not copy the blinkTimer.
     *
     * @param character
     *         The AsciiCharacter.
     *
     * @throws NullPointerException
     *        If the character is null.
     */
    public GraphicTile(final @NonNull Tile character) {
	    super(character.getCharacter());

	    for (final Shader shader : character.getShaders()) {
	        addShaders(shader);
        }

	    super.setHidden(character.isHidden());

	    super.setBackgroundColor(character.getBackgroundColor());
	    super.setForegroundColor(character.getForegroundColor());

        super.setUnderlined(character.isUnderlined());
        super.setUnderlineThickness(character.getUnderlineThickness());
    }

    @Override
    public void updateCacheHash() {
        if (updateCacheHash) {
            super.updateCacheHash = false;
            super.cacheHash = Objects.hash(super.getCharacter(), super.getBackgroundColor(), super.getShaders());
        }
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
    public void draw(final @NonNull Graphics2D gc, @NonNull final ImageCache imageCache, int columnIndex, int rowIndex) {
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
    public void addShaders(final @NonNull Shader... shaders) {
        for (final Shader shader : shaders) {
            if (shader instanceof CharShader == false) {
                super.addShaders(shader);
            }
        }

        updateCacheHash = true;
    }
}