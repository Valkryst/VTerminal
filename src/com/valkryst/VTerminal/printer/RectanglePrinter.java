package com.valkryst.VTerminal.printer;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.component.Screen;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

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
     * @param column
     *         The x-axis (column) coordinate of the top-left character.
     *
     * @param row
     *         The y-axis (row) coordinate of the top-left character.
     */
    public void print(final Panel panel, final int column, final int row) {
        print(panel.getScreen(), column, row);
    }

    /**
     * Prints a rectangle on a screen and attempts to connect the new
     * rectangle with existing similar rectangles within the draw area.
     *
     * @param screen
     *         The screen.
     *
     * @param column
     *         The x-axis (column) coordinate of the top-left character.
     *
     * @param row
     *         The y-axis (row) coordinate of the top-left character.
     */
    public void print(final Screen screen, final int column, final int row) {
        print(screen, column, row, true);
    }

    /**
     * Prints a rectangle on a screen.
     *
     * If the function is set to perform connections, then it will attempt
     * to connect the new rectangle with existing similar rectangles in
     * the draw area.
     *
     * @param screen
     *         The screen.
     *
     * @param column
     *         The x-axis (column) coordinate of the top-left character.
     *
     * @param row
     *         The y-axis (row) coordinate of the top-left character.
     *
     * @param performConnections
     *        Whether or not to perform connections.
     */
    public void print(final Screen screen, final int column, final int row, final boolean performConnections) {
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

        if (performConnections) {
            setConnectors(screen, column, row);
        }
    }

    /**
     * Checks for, and sets, all positions of the printed rectangle where a
     * connector character is required.
     *
     * @param screen
     *         The screen.
     *
     * @param column
     *         The x-axis (column) coordinate of the top-left character.
     *
     * @param row
     *         The y-axis (row) coordinate of the top-left character.
     */
    private void setConnectors(final Screen screen, final int column, final int row) {
        System.out.println(column + " " + row + " " + width + " " + height + "\n\n\n\n");
        final int lastRow = row + height - 1;
        final int lastColumn = column + width - 1;

        // Check Corners:
        setConnector(screen, column, row);
        setConnector(screen, lastColumn, row);
        setConnector(screen, column, lastRow);
        setConnector(screen, lastColumn, lastRow);

        // Check Left/Right Sides:
        for (int i = 1 ; i < height - 1 ; i++) {
            setConnector(screen, column, row + i);
            setConnector(screen, lastColumn, row + i);
        }

        // Check Top/Bottom Sides:
        for (int i = 1 ; i < width - 1 ; i++) {
            setConnector(screen, column + i, row);
            setConnector(screen, column + i, lastRow);
        }
    }

    /**
     * Checks if a character should be a connector and changes it to the
     * appropriate connector character if it should be.
     *
     * @param screen
     *         The screen.
     *
     * @param column
     *         The x-axis (column) coordinate of the character.
     *
     * @param row
     *         The y-axis (row) coordinate of the character
     */
    private void setConnector(final Screen screen, final int column, final int row) {
        final boolean validTop = hasValidTopNeighbour(screen, column, row);
        final boolean validBottom = hasValidBottomNeighbour(screen, column, row);
        final boolean validLeft = hasValidLeftNeighbour(screen, column, row);
        final boolean validRight = hasValidRightNeighbour(screen, column, row);

        final boolean[] neighbourPattern = new boolean[]{validRight, validTop, validLeft, validBottom};
        final Optional<Character> optChar = rectangleType.getCharacterByNeighbourPattern(neighbourPattern);

        optChar.ifPresent(character -> screen.write(character, column, row));
    }

    /**
     * Determines if the top-neighbour of a cell is both of the correct
     * RectangleType and that the character of the top-neighbour can be
     * connected to.
     *
     * @param column
     *         The x-axis (column) coordinate of the cell.
     *
     * @param row
     *         The y-axis (row) coordinate of the cell
     *
     * @return
     *        If the top-neighbour is valid.
     */
    private boolean hasValidTopNeighbour(final Screen screen, final int column, final int row) {
        final Optional<AsciiCharacter> optChar = screen.getCharacterAt(column, row - 1);
        return optChar.filter(asciiCharacter -> rectangleType.isValidTopCharacter(asciiCharacter)).isPresent();
    }

    /**
     * Determines if the bottom-neighbour of a cell is both of the correct
     * RectangleType and that the character of the bottom-neighbour can be
     * connected to.
     *
     * @param column
     *         The x-axis (column) coordinate of the cell.
     *
     * @param row
     *         The y-axis (row) coordinate of the cell
     *
     * @return
     *        If the bottom-neighbour is valid.
     */
    private boolean hasValidBottomNeighbour(final Screen screen, final int column, final int row) {
        final Optional<AsciiCharacter> optChar = screen.getCharacterAt(column, row + 1);
        return optChar.filter(asciiCharacter -> rectangleType.isValidBottomCharacter(asciiCharacter)).isPresent();
    }

    /**
     * Determines if the left-neighbour of a cell is both of the correct
     * RectangleType and that the character of the left-neighbour can be
     * connected to.
     *
     * @param column
     *         The x-axis (column) coordinate of the cell.
     *
     * @param row
     *         The y-axis (row) coordinate of the cell
     *
     * @return
     *        If the left-neighbour is valid.
     */
    private boolean hasValidLeftNeighbour(final Screen screen, final int column, final int row) {
        final Optional<AsciiCharacter> optChar = screen.getCharacterAt(column - 1, row);
        return optChar.filter(asciiCharacter -> rectangleType.isValidLeftCharacter(asciiCharacter)).isPresent();
    }

    /**
     * Determines if the right-neighbour of a cell is both of the correct
     * RectangleType and that the character of the right-neighbour can be
     * connected to.
     *
     * @param column
     *         The x-axis (column) coordinate of the cell.
     *
     * @param row
     *         The y-axis (row) coordinate of the cell
     *
     * @return
     *        If the right-neighbour is valid.
     */
    private boolean hasValidRightNeighbour(final Screen screen, final int column, final int row) {
        final Optional<AsciiCharacter> optChar = screen.getCharacterAt(column + 1, row);
        return optChar.filter(asciiCharacter -> rectangleType.isValidRightCharacter(asciiCharacter)).isPresent();
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
