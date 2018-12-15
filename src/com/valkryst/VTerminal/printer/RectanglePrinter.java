package com.valkryst.VTerminal.printer;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.TileGrid;
import com.valkryst.VTerminal.misc.ShapeAlgorithms;
import lombok.*;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Optional;

@ToString
public class RectanglePrinter {
    /** The width/height of the rectangle to print. */
    @Getter private final Dimension dimensions = new Dimension(2, 2);

    /** The character to fill the rectangle with, if a filled rectangle is drawn. */
    @Getter @Setter private int fillChar = '█';

    /** The title. */
    @Getter @Setter private String title;

    /** The type of rectangle to print. */
    @Getter @Setter private RectangleType rectangleType = RectangleType.HEAVY;
    
    /**
     * Prints a rectangle on a grid.
     *
     * If the function is set to perform connections, then it will attempt to connect the new rectangle with
     * existing similar rectangles in the draw area.
     *
     * Does nothing if the grid or position are null.
     *
     * @param grid
     *          The grid.
     *
     * @param position
     *          The x/y-axis (column/row) coordinates of the top-left character.
     */
    public void print(final TileGrid grid, final Point position) {
        if (grid == null || position == null) {
            return;
        }

        final int width = dimensions.width;
        final int height = dimensions.height;
        final int x = position.x;
        final int y = position.y;

        final int lastRow = y + height - 1;
        final int lastColumn = x + width - 1;

        final Point writePosition = new Point(0, 0);

        // Draw Corners:
        printCharacter(grid, position, rectangleType.getTopLeft());

        writePosition.setLocation(lastColumn, y);
        printCharacter(grid, writePosition, rectangleType.getTopRight());

        writePosition.setLocation(x, lastRow);
        printCharacter(grid, writePosition, rectangleType.getBottomLeft());

        writePosition.setLocation(lastColumn, lastRow);
        printCharacter(grid, writePosition, rectangleType.getBottomRight());

        // Draw Left/Right Sides:
        for (int i = 1 ; i < height - 1 ; i++) {
            writePosition.setLocation(x, y + i);
            printCharacter(grid, writePosition, rectangleType.getVertical());

            writePosition.setLocation(lastColumn, y + i);
            printCharacter(grid, writePosition, rectangleType.getVertical());
        }

        // Draw Top/Bottom Sides:
        for (int i = 1 ; i < width - 1 ; i++) {
            writePosition.setLocation(x + i, y);
            printCharacter(grid, writePosition, rectangleType.getHorizontal());

            writePosition.setLocation(x + i, lastRow);
            printCharacter(grid, writePosition, rectangleType.getHorizontal());
        }

        // Draw title on Top Side:
        if (title != null && title.isEmpty() == false) {
            // Draw Title Text:
            final char[] titleChars = title.toCharArray();

            for (int i = 2; i < width - 2 && i - 2 < titleChars.length; i++) {
                writePosition.setLocation(x + i, y);
                printCharacter(grid, writePosition, titleChars[i - 2]);
            }

            // Draw Title Borders:
            writePosition.setLocation(x + 1, y);
            printCharacter(grid, writePosition, rectangleType.getConnectorLeft());

            writePosition.setLocation(x + titleChars.length + 2, y);
            printCharacter(grid, writePosition, rectangleType.getConnectorRight());
        }

        // Handle Connectors:
        setConnectors(grid, position);
    }

    // todo Document this function.
    public void printCharacter(final TileGrid grid, final Point position, final int character) {
        if (grid == null || position == null) {
            return;
        }

        final Tile tile = grid.getTileAt(position);

        if (tile != null) {
            tile.setCharacter(character);
        }
    }

    /**
     * Prints a filled rectangle on a grid.
     *
     * If the function is set to perform connections, then it will attempt to connect the new rectangle with
     * existing similar rectangles in the draw area.
     *
     * Does nothing if the grid or position are null.
     *
     * @param grid
     *          The grid.
     *
     * @param position
     *          The x/y-axis (column/row) coordinates of the top-left character.
     */
    public void printFilled(final TileGrid grid, final Point position) {
        if (grid == null || position == null) {
            return;
        }

        print(grid, position);

        final Dimension dimension = new Dimension(this.dimensions.width - 2, this.dimensions.height - 2);
        position.setLocation(position.x + 1, position.y + 1);

        for (final Point point : ShapeAlgorithms.getFilledRectangle(position, dimension)) {
            grid.getTileAt(point).setCharacter(fillChar);
        }
    }

    /**
     * Checks for, and sets, all positions of the printed rectangle where a connector character is required.
     *
     * Does nothing if the grid or position are null.
     *
     * @param grid
     *          The grid.
     *
     * @param position
     *          The x/y-axis (column/row) coordinates of the top-left character.
     */
    private void setConnectors(final TileGrid grid, final Point position) {
        if (grid == null || position == null) {
            return;
        }

        for (final Point point : ShapeAlgorithms.getRectangle(position, dimensions)) {
            setConnector(grid, point);
        }
    }

    /**
     * Checks if a character should be a connector and changes it to the appropriate connector character if it
     * should be.
     *
     * Does nothing if the grid or position are null.
     *
     * @param grid
     *          The grid.
     *
     * @param position
     *          The x/y-axis (column/row) coordinates of the top-left character.
     */
    private void setConnector(final TileGrid grid, final Point position) {
        if (grid == null || position == null) {
            return;
        }

        final boolean validTop = hasValidTopNeighbour(grid, new Point(position));
        final boolean validBottom = hasValidBottomNeighbour(grid, new Point(position));
        final boolean validLeft = hasValidLeftNeighbour(grid, new Point(position));
        final boolean validRight = hasValidRightNeighbour(grid, new Point(position));

        final boolean[] neighbourPattern = new boolean[]{validRight, validTop, validLeft, validBottom};
        final Optional<Integer> optChar = rectangleType.getCharacterByNeighbourPattern(neighbourPattern);

        optChar.ifPresent(character -> grid.getTileAt(position).setCharacter(character));
    }

    /**
     * Determines if the top-neighbour of a cell is both of the correct RectangleType and that the character of
     * the top-neighbour can be connected to.
     *
     * @param position
     *          The x/y-axis (column/row) coordinates of the top-left character.
     *
     * @return
     *          True if the top-neighbour is valid, false otherwise.
     *          False if the grid or position are null.
     *
     */
    private boolean hasValidTopNeighbour(final TileGrid grid, final Point position) {
        if (grid == null || position == null) {
            return false;
        }

        try {
            position.setLocation(position.x, position.y - 1);
            return rectangleType.isValidTopCharacter(grid.getTileAt(position));
        } catch (final IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Determines if the bottom-neighbour of a cell is both of the correct RectangleType and that the character
     * of the bottom-neighbour can be connected to.
     *
     * @param position
     *          The x/y-axis (column/row) coordinates of the top-left character.
     *
     * @return
     *          If the bottom-neighbour is valid, false otherwise.
     *          False if the grid or position are null.
     */
    private boolean hasValidBottomNeighbour(final TileGrid grid, final Point position) {
        if (grid == null || position == null) {
            return false;
        }

        try {
            position.setLocation(position.x, position.y + 1);
            return rectangleType.isValidBottomCharacter(grid.getTileAt(position));
        } catch (final IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Determines if the left-neighbour of a cell is both of the correct RectangleType and that the character
     * of the left-neighbour can be connected to.
     *
     * @param position
     *          The x/y-axis (column/row) coordinates of the cell.
     *
     * @return
     *          If the left-neighbour is valid, false otherwise.
     *          False if the grid or position are null.
     */
    private boolean hasValidLeftNeighbour(final TileGrid grid, final Point position) {
        if (grid == null || position == null) {
            return false;
        }

        try {
            position.setLocation(position.x - 1, position.y);
            return rectangleType.isValidLeftCharacter(grid.getTileAt(position));
        } catch (final IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Determines if the right-neighbour of a cell is both of the correct RectangleType and that the character
     * of the right-neighbour can be connected to.
     *
     * @param position
     *          The x/y-axis (column/row) coordinates of the cell.
     *
     * @return
     *          If the right-neighbour is valid, false otherwise.
     *          False if the grid or position are null.
     */
    private boolean hasValidRightNeighbour(final TileGrid grid, final Point position) {
        if (grid == null || position == null) {
            return false;
        }

        try {
            position.setLocation(position.x + 1, position.y);
            return rectangleType.isValidRightCharacter(grid.getTileAt(position));
        } catch (final IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Sets the width of the rectangle to print.
     *
     * @param width
     *          The new width.
     *
     * @return
     *          This.
     */
    public RectanglePrinter setWidth(final int width) {
        if (width > 0) {
            dimensions.setSize(width, dimensions.height);
        }

        return this;
    }

    /**
     * Sets the height of the rectangle to print.
     *
     * @param height
     *          The new height.
     *
     * @return
     *          This.
     */
    public RectanglePrinter setHeight(final int height) {
        if (height > 0) {
            dimensions.setSize(dimensions.width, height);
        }

        return this;
    }
}
