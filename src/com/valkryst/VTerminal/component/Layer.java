package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.font.Font;

import java.awt.*;

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
    public Layer(int columnIndex, int rowIndex, int width, int height) {
        super(columnIndex, rowIndex, width, height);
    }

    @Override
    public void draw(final Screen screen) {
        throw new UnsupportedOperationException("A Layer must be drawn using the draw(canvas, font) method.");
    }

    /**
     * Draws the layer onto the specified canvas using the specified font.
     *
     * @param gc
     *         The graphics context to draw with.
     *
     * @param font
     *         The font to draw with.
     */
    public void draw(final Graphics2D gc, final Font font) {
        // Draw the screen onto the canvas:
        for (int row = 0 ; row < height ; row++) {
            strings[row].draw(gc, font, row);
        }
    }
}
