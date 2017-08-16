package com.valkryst.VTerminal;

import com.valkryst.VTerminal.misc.ColoredImageCache;
import lombok.NonNull;
import lombok.ToString;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Objects;

@ToString
public class AsciiTile extends AsciiCharacter {
    /**
     * Constructs a new AsciiTile.
     *
     * @param character
     *         The character.
     */
	public AsciiTile(final char character) {
	    super(character);
    }

    /**
     * Constructs a new AsciiTile by copying the data
     * of an AsciiCharacter.
     *
     * Does not copy the blinkTimer.
     *
     * @param character
     *         The AsciiCharacter.
     *
     * @throws NullPointerException
     *        If the character is null.
     */
    public AsciiTile(final @NonNull AsciiCharacter character) {
	    super(character.getCharacter());

	    super.setHidden(character.isHidden());

	    super.setBackgroundColor(character.getBackgroundColor());
	    super.setForegroundColor(character.getForegroundColor());

	    final Rectangle boundingBox = character.getBoundingBox();
	    super.getBoundingBox().setSize(boundingBox.width, boundingBox.height);
	    super.getBoundingBox().setLocation(boundingBox.x, boundingBox.y);

        super.setUnderlined(character.isUnderlined());
        super.setUnderlineThickness(character.getUnderlineThickness());

        super.setFlippedHorizontally(character.isFlippedHorizontally());
        super.setFlippedVertically(character.isFlippedVertically());
    }

    @Override
    protected void updateCacheHash() {
        super.cacheHash = Objects.hash(super.getCharacter(), super.getBackgroundColor(), Color.WHITE,
                                       super.isFlippedHorizontally(), super.isFlippedVertically());
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
    public void draw(final @NonNull Graphics2D gc, @NonNull final ColoredImageCache imageCache, int columnIndex, int rowIndex) {
        if (super.updateCacheHash) {
            updateCacheHash();
            super.updateCacheHash = false;
        }

        final int fontWidth = imageCache.getFont().getWidth();
        final int fontHeight = imageCache.getFont().getHeight();

        columnIndex *= fontWidth;
        rowIndex *= fontHeight;

        super.getBoundingBox().setLocation(columnIndex, rowIndex);
        super.getBoundingBox().setSize(fontWidth, fontHeight);

        // Handle hidden state:
        if (super.isHidden()) {
            gc.setColor(super.getBackgroundColor());
            gc.fillRect(columnIndex, rowIndex, fontWidth, fontHeight);
        } else {
            final BufferedImage image = imageCache.retrieveFromCache(this);
            gc.drawImage(image, columnIndex, rowIndex, null);
        }

        // Draw underline:
        if (super.isUnderlined()) {
            gc.setColor(super.getForegroundColor());

            final int y = rowIndex + fontHeight - super.getUnderlineThickness();
            gc.fillRect(columnIndex, y, fontWidth, super.getUnderlineThickness());
        }
    }
}