package com.valkryst.VTerminal.printer;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.TileGrid;
import com.valkryst.VTerminal.misc.ShapeAlgorithms;
import lombok.*;

import java.awt.Point;

@EqualsAndHashCode
@ToString
public class LinePrinter {
    /** The character to print the line with. */
    @Getter @Setter private char printChar = '█';

    /**
     * Prints one, or more, lines on a tile grid using a set of points.
     *
     * The algorithm draws a line from the first point to the second point,
     * then from the second point to the third point, and so on.
     *
     * @param grid
     *        The grid.
     *
     * @param points
     *        The points.
     *
     * @throws IllegalArgumentException
     *         If there are fewer than two points.
     *
     * @throws NullPointerException
     *         If the screen or points array is null.
     */
    public void print(final @NonNull TileGrid grid, final @NonNull Point[] points) {
        if (points.length < 2) {
            throw new IllegalArgumentException("A line requires two points to be drawn.");
        }

        for (int i = 1 ; i < points.length ; i++) {
            final Point previous = points[i - 1];
            final Point current = points[i];

            print(grid, previous, current);
        }
    }

    /**
     * Prints a line on a tile grid.
     *
     * @param grid
     *        The grid.
     *
     * @param from
     *         The start point of the line.
     *
     * @param to
     *         The end point of the line.
     *
     * @throws NullPointerException
     *         If the component or either point is null.
     */
    public void print(final @NonNull TileGrid grid, final Point from, final Point to) {
        for (final Point point : ShapeAlgorithms.getLine(from.x, from.y, to.x, to.y)) {
            final Tile tile = grid.getTileAt(point);

            if (tile != null) {
                tile.setCharacter(printChar);
            }
        }
    }
}
