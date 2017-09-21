package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.builder.component.LayerBuilder;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.misc.ImageCache;
import lombok.NonNull;
import lombok.ToString;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

@ToString
public class Layer extends Component {
    /**
     * Constructs a new Layer.
     *
     * @param builder
     *        The builder to use.
     */
    public Layer(final LayerBuilder builder) {
        super(builder);

        for (final AsciiString string : super.getStrings()) {
            string.setBackgroundColor(builder.getBackgroundColor());
            string.setForegroundColor(builder.getForegroundColor());
        }
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
    public void draw(final @NonNull Graphics2D gc, final @NonNull ImageCache imageCache) {
        final Font font = imageCache.getFont();

        final int iWidth = getWidth() * font.getWidth();
        final int iHeight = getHeight() * font.getHeight();
        final BufferedImage image = new BufferedImage(iWidth, iHeight, BufferedImage.TYPE_INT_ARGB);

        // Draw the layer onto the image:
        for (int row = 0 ; row < getHeight() ; row++) {
            super.getString(row).draw((Graphics2D) image.getGraphics(), imageCache, row);
        }

        // Draw the image onto the canvas:
        final Point position = super.getPosition();
        final int xPos = position.x * font.getWidth();
        final int yPos = position.y * font.getHeight();

        gc.drawImage(image, xPos, yPos, null);
    }
}
