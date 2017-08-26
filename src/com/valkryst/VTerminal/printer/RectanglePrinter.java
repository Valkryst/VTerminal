package com.valkryst.VTerminal.printer;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.component.Screen;
import com.valkryst.VTerminal.misc.JSONFunctions;
import com.valkryst.VTerminal.misc.ShapeAlgorithms;
import lombok.*;
import org.json.simple.JSONObject;

import java.awt.Point;
import java.util.Optional;

@EqualsAndHashCode
@ToString
public class RectanglePrinter {
    /** The width of the rectangle to print. */
    @Getter private int width = 2;
    /** The height of the rectangle to print. */
    @Getter private int height = 2;

    /** The title string. */
    @Getter @Setter private String title;

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
     *
     * @throws NullPointerException
     *         If the panel is null.
     */
    public void print(final @NonNull Panel panel, final int column, final int row) {
        print(panel.getScreen(), column, row);
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
     * @throws NullPointerException
     *         If the screen is null.
     */
    public void print(final @NonNull Screen screen, final int column, final int row) {
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

        // Draw title on Top Side:
        if (title != null && title.isEmpty() == false) {
            // Draw Title Text:
            final char[] titleChars = title.toCharArray();

            for (int i = 2; i < width - 2 && i - 2 < titleChars.length; i++) {
                screen.write(titleChars[i - 2], column + i, row);
            }

            // Draw Title Borders:
            screen.write(rectangleType.getConnectorLeft(), column + 1, row);
            screen.write(rectangleType.getConnectorRight(), column + titleChars.length + 2, row);
        }

        // Handle Connectors:
        setConnectors(screen, column, row);
    }

    public void printFromJSON(final @NonNull Screen screen, final JSONObject jsonObject) {
        final Integer column = JSONFunctions.getIntElement(jsonObject, "column");
        final Integer row = JSONFunctions.getIntElement(jsonObject, "row");
        final Integer width = JSONFunctions.getIntElement(jsonObject, "width");
        final Integer height = JSONFunctions.getIntElement(jsonObject, "height");
        final String title = (String) jsonObject.get("title");
        final String rectangleType = (String) jsonObject.get("rectangleType");

        if (column == null) {
            throw new IllegalArgumentException("A rectangle printer requires that a column be set.");
        }

        if (row == null) {
            throw new IllegalArgumentException("A rectangle printer requires that a row be set.");
        }

        if (width != null) {
            setWidth(width);
        }

        if (height != null) {
            setHeight(height);
        }

        if (title != null) {
            this.title = title;
        }

        if (rectangleType != null) {
            this.rectangleType = RectangleType.valueOf(rectangleType.toUpperCase());
        }

        print(screen, column, row);
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
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    private void setConnectors(final @NonNull Screen screen, final int column, final int row) {
        for (final Point point : ShapeAlgorithms.getRectangle(column, row, width, height)) {
            setConnector(screen, point.x, point.y);
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
     *         The y-axis (row) coordinate of the character.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    private void setConnector(final @NonNull Screen screen, final int column, final int row) {
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
     *         The y-axis (row) coordinate of the cell.
     *
     * @return
     *        If the top-neighbour is valid.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    private boolean hasValidTopNeighbour(final @NonNull Screen screen, final int column, final int row) {
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
     *         The y-axis (row) coordinate of the cell.
     *
     * @return
     *        If the bottom-neighbour is valid.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    private boolean hasValidBottomNeighbour(final @NonNull Screen screen, final int column, final int row) {
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
     *         The y-axis (row) coordinate of the cell.
     *
     * @return
     *        If the left-neighbour is valid.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    private boolean hasValidLeftNeighbour(final @NonNull Screen screen, final int column, final int row) {
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
     *         The y-axis (row) coordinate of the cell.
     *
     * @return
     *        If the right-neighbour is valid.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    private boolean hasValidRightNeighbour(final @NonNull Screen screen, final int column, final int row) {
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
