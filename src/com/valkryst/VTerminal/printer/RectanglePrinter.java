package com.valkryst.VTerminal.printer;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.component.Screen;
import lombok.Getter;
import lombok.Setter;

public class RectanglePrinter {
    /** The width of the rectangle to print. */
    @Getter private int width = 2;
    /** The height of the rectangle to print. */
    @Getter private int height = 2;

    /** The type of rectangle to print. */
    @Getter @Setter private RectangleType rectangleType = RectangleType.HEAVY;

    /**
     * Prints a rectangle on the screen of a panel.
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
        print(panel.getCurrentScreen(), row, column);
    }

    /**
     * Prints a rectangle on a screen.
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
        final int lastRow = row + height - 1;
        final int lastColumn = column + width - 1;

        // Draw Corners:
        screen.write(rectangleType.getTopLeft(), column, row);
        screen.write(rectangleType.getTopRight(), lastColumn, row);
        screen.write(rectangleType.getBottomLeft(), column, lastRow);
        screen.write(rectangleType.getBottomRight(), lastColumn, lastRow);

        // Draw Left/Right Sides:
        for (int i = 1 ; i < height - 1 ; i++) {
            screen.write(rectangleType.getVertical(), column, row + i);
            screen.write(rectangleType.getVertical(), lastColumn, row + i);
        }

        // Draw Top/Bottom Sides:
        for (int i = 1 ; i < width - 1 ; i++) {
            screen.write(rectangleType.getHorizontal(), column + i, row);
            screen.write(rectangleType.getHorizontal(), column + i, lastRow);
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
    public RectanglePrinter setWidth(final int width) {
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
    public RectanglePrinter setHeight(final int height) {
        if (height > 0) {
            this.height = height;
        }

        return this;
    }
}
