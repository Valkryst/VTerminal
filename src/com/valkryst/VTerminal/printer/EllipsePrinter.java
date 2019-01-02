package com.valkryst.VTerminal.printer;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.TileGrid;
import com.valkryst.VTerminal.misc.ShapeAlgorithms;
import lombok.*;

import java.awt.Dimension;
import java.awt.Point;

@ToString
public class EllipsePrinter {
    /** The width/height of the ellipse to print. */
    @Getter private final Dimension dimensions = new Dimension(2, 2);

    /** The character to print the ellipse with. */
    @Getter @Setter private char printChar = '█';

    /**
     * Prints an ellipse on a tile grid.
     *
     * Does nothing if the grid or point are null.
     *
     * @param grid
     *          The grid.
     *
     * @param position
     *          The x/y-axis (column/row) coordinates of the center character.
     */
    public void print(final TileGrid grid, final Point position) {
        if (grid == null || position == null) {
            return;
        }

        for (final Point point : ShapeAlgorithms.getEllipse(position, dimensions)) {
            final Tile tile = grid.getTileAt(point);

            if (tile != null) {
                tile.setCharacter(printChar);
            }
        }
    }

    /**
     * Prints a filled ellipse on a tile grid.
     *
     * Does nothing if the grid or point are null.
     *
     * @param grid
     *          The grid.
     *
     * @param position
     *          The x/y-axis (column/row) coordinates of the center character.
     */
    public void printFilled(final TileGrid grid, final Point position) {
        if (grid == null || position == null) {
            return;
        }

        for (final Point point : ShapeAlgorithms.getFilledEllipse(position, dimensions)) {
            final Tile tile = grid.getTileAt(point);

            if (tile != null) {
                tile.setCharacter(printChar);
            }
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
            dimensions.setSize(width, dimensions.height);
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
            dimensions.setSize(dimensions.width, height);
        }

        return this;
    }
}
