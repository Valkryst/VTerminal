package com.valkryst.VTerminal.printer;

import com.valkryst.VTerminal.TileGrid;
import lombok.Getter;
import lombok.ToString;

import java.awt.*;

@ToString
public class TrianglePrinter {
    /** The character to print the triangle with. */
    @Getter private char printChar = '█';

    /** The line printer used to print the triangle. */
    private final LinePrinter linePrinter = new LinePrinter();

    /** Constructs a new TrianglePrinter. */
    public TrianglePrinter() {
        linePrinter.setPrintChar(printChar);
    }

    /**
     * Prints a triangle on a tile grid using a set of points.
     *
     * The algorithm requires exactly three points. If there are more than three
     * points, the extra points are ignored.
     *
     * Does nothing if the grid or points array are null or if the points array doesn't have 3 elements.
     *
     * @param grid
     *          The grid.
     *
     * @param points
     *          The points.
     */
    public void print(final TileGrid grid, Point[] points) {
        if (grid == null || points == null || points.length < 3) {
            return;
        }

        points = new Point[]{points[0], points[1], points[2], points[0]};
        linePrinter.print(grid, points);
    }

    /**
     * Sets the new print character.
     *
     * @param printChar
     *          The character to print the triangle with.
     */
    public void setPrintChar(final char printChar) {
        this.printChar = printChar;
        linePrinter.setPrintChar(printChar);
    }
}
