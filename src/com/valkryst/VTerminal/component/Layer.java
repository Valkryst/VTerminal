package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.misc.ColoredImageCache;
import lombok.NonNull;
import lombok.ToString;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

@ToString
public class Layer extends Component {
    /**
     * Constructs a new Layer.
     *
     * @param columnIndex
     *        The x-axis (column) coordinate of the top-left character.
     *
     * @param rowIndex
     *        The y-axis (row) coordinate of the top-left character.
     *
     * @param width
     *        The width, in characters.
     *
     * @param height
     *        The height, in characters.
     */
    public Layer(final int columnIndex, final int rowIndex, final int width, final int height) {
        super(columnIndex, rowIndex, width, height);
    }

    @Override
    public void draw(final @NonNull Screen screen) {
        throw new UnsupportedOperationException("A Layer must be drawn using the draw(canvas, font) method.");
    }

    /**
     * Draws the layer onto the specified canvas using the specified font.
     *
     * @param gc
     *         The graphics context to draw with.
     *
     * @param imageCache
     *         The image cache to retrieve the character image from.
     *
     * @throws NullPointerException
     *         If the gc or image cache is null.
     */
    public void draw(final @NonNull Graphics2D gc, final @NonNull ColoredImageCache imageCache) {
        final Font font = imageCache.getFont();

        final int iWidth = getWidth() * font.getWidth();
        final int iHeight = getHeight() * font.getHeight();
        final BufferedImage image = new BufferedImage(iWidth, iHeight, BufferedImage.TYPE_INT_ARGB);

        // Draw the layer onto the image:
        for (int row = 0 ; row < getHeight() ; row++) {
            super.getString(row).draw((Graphics2D) image.getGraphics(), imageCache, row);
        }

        // Draw the image onto the canvas:
        final int xPos = super.getColumnIndex() * font.getWidth();
        final int yPos = super.getRowIndex() * font.getHeight();

        gc.drawImage(image, xPos, yPos, null);
    }
}
