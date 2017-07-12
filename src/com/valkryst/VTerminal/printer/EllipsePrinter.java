package com.valkryst.VTerminal.printer;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.component.Screen;
import lombok.Getter;
import lombok.Setter;

public class EllipsePrinter {
    /** The width of the ellipse to print. */
    @Getter private int width = 2;
    /** The height of the ellipse to print. */
    @Getter private int height = 2;

    /** The character to print the ellipse with. */
    @Getter @Setter private char printChar = '█';

    /**
     * Prints an ellipse on the screen of a panel.
     *
     * @param panel
     *         The panel.
     *
     * @param row
     *         The y-axis (row) coordinate of the top-left character.
     *
     * @param column
     *         The x-axis (column) coordinate of the top-left character.
     */
    public void print(final Panel panel, final int row, final int column) {
        print(panel.getScreen(), row, column);
    }

    /**
     * Prints an ellipse on a screen using the Bresenham algorithm.
     *
     * @param screen
     *         The screen.
     *
     * @param row
     *         The y-axis (row) coordinate of the top-left character.
     *
     * @param column
     *         The x-axis (column) coordinate of the top-left character.
     */
    public void print(final Screen screen, final int row, final int column) {
        int a2 = width * width;
        int b2 = height * height;
        int fa2 = 4 * a2;
        int fb2 = 4 * b2;

        // Draw first half of the ellipse:
        int sigma = 2 * b2 + a2 * (1 - 2 * height);
        int x = 0;
        int y = height;

        for (; (b2 * x) <= (a2 * y) ; x++) {
            screen.write(printChar, column + x, row + y);
            screen.write(printChar, column - x, row + y);
            screen.write(printChar, column + x, row - y);
            screen.write(printChar, column - x, row - y);

            if (sigma >= 0) {
                sigma += fa2 * (1 - y);
                y--;
            }

            sigma += b2 * ((4 * x) + 6);
        }

        // Draw second half of the ellipse:
        sigma = 2 * a2 + b2 * (1 - 2 * width);
        x = width;
        y = 0;

        for (; (a2 * y) <= (b2 * x) ; y++) {
            screen.write(printChar, column + x, row + y);
            screen.write(printChar, column - x, row + y);
            screen.write(printChar, column + x, row - y);
            screen.write(printChar, column - x, row - y);

            if (sigma >= 0) {
                sigma += fb2 * (1 - x);
                x--;
            }

            sigma += b2 * ((4 * y) + 6);
        }
    }

    /**
     * Sets the width.
     *
     * @param width
     *         The new width.
     *
     * @return
     *         This.
     */
    public EllipsePrinter setWidth(final int width) {
        if (width > 0) {
            this.width = width;
        }

        return this;
    }

    /**
     * Sets the height.
     *
     * @param height
     *         The new height.
     *
     * @return
     *         This.
     */
    public EllipsePrinter setHeight(final int height) {
        if (height > 0) {
            this.height = height;
        }

        return this;
    }
}
