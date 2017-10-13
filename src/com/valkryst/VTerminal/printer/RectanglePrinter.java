package com.valkryst.VTerminal.printer;

import com.valkryst.VJSON.VJSONParser;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.component.Screen;
import com.valkryst.VTerminal.misc.ShapeAlgorithms;
import lombok.*;
import org.json.simple.JSONObject;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Optional;

@EqualsAndHashCode
@ToString
public class RectanglePrinter {
    /** The width/height of the rectangle to print. */
    @Getter private final Dimension dimensions = new Dimension(2, 2);

    /** The character to fill the rectangle with, if a filled rectangle is drawn. */
    @Getter @Setter private char fillChar = '█';

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
     * @param position
     *         The x/y-axis (column/row) coordinates of the top-left character.
     *
     * @throws NullPointerException
     *         If the panel is null.
     */
    public void print(final @NonNull Panel panel, final Point position) {
        print(panel.getScreen(), position);
    }

    /**
     * Prints a rectangle on a screen.
     *
     * If the function is set to perform connections, then it will attempt to
     * connect the new rectangle with existing similar rectangles in the draw area.
     *
     * @param screen
     *         The screen.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates of the top-left character.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    public void print(final @NonNull Screen screen, final Point position) {
        final int width = dimensions.width;
        final int height = dimensions.height;
        final int x = position.x;
        final int y = position.y;

        final int lastRow = y + height - 1;
        final int lastColumn = x + width - 1;

        final Point writePosition = new Point(0, 0);

        // Draw Corners:
        screen.write(rectangleType.getTopLeft(), position);

        writePosition.setLocation(lastColumn, y);
        screen.write(rectangleType.getTopRight(), writePosition);

        writePosition.setLocation(x, lastRow);
        screen.write(rectangleType.getBottomLeft(), writePosition);

        writePosition.setLocation(lastColumn, lastRow);
        screen.write(rectangleType.getBottomRight(), writePosition);

        // Draw Left/Right Sides:
        for (int i = 1 ; i < height - 1 ; i++) {
            writePosition.setLocation(x, y + i);
            screen.write(rectangleType.getVertical(), writePosition);

            writePosition.setLocation(lastColumn, y + i);
            screen.write(rectangleType.getVertical(), writePosition);
        }

        // Draw Top/Bottom Sides:
        for (int i = 1 ; i < width - 1 ; i++) {
            writePosition.setLocation(x + i, y);
            screen.write(rectangleType.getHorizontal(), writePosition);

            writePosition.setLocation(x + i, lastRow);
            screen.write(rectangleType.getHorizontal(), writePosition);
        }

        // Draw title on Top Side:
        if (title != null && title.isEmpty() == false) {
            // Draw Title Text:
            final char[] titleChars = title.toCharArray();

            for (int i = 2; i < width - 2 && i - 2 < titleChars.length; i++) {
                writePosition.setLocation(x + i, y);
                screen.write(titleChars[i - 2], writePosition);
            }

            // Draw Title Borders:
            writePosition.setLocation(x + 1, y);
            screen.write(rectangleType.getConnectorLeft(), writePosition);

            writePosition.setLocation(x + titleChars.length + 2, y);
            screen.write(rectangleType.getConnectorRight(), writePosition);
        }

        // Handle Connectors:
        setConnectors(screen, position);
    }

    /**
     * Prints a filled rectangle on the screen of a panel.
     *
     * @param panel
     *         The panel.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates of the top-left character.
     *
     * @throws NullPointerException
     *         If the panel is null.
     */
    public void printFilled(final @NonNull Panel panel, final Point position) {
        printFilled(panel.getScreen(), position);
    }

    /**
     * Prints a rectangle on a screen.
     *
     * If the function is set to perform connections, then it will attempt to
     * connect the new rectangle with existing similar rectangles in the draw area.
     *
     * @param screen
     *         The screen.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates of the top-left character.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    public void printFilled(final @NonNull Screen screen, final Point position) {
        print(screen, position);

        final Dimension dimension = new Dimension(this.dimensions.width - 2, this.dimensions.height - 2);
        position.setLocation(position.x + 1, position.y + 1);

        for (final Point point : ShapeAlgorithms.getFilledRectangle(position, dimension)) {
            screen.write(fillChar, point);
        }
    }

    public void printFromJSON(final @NonNull Screen screen, final JSONObject jsonObject) {
        final VJSONParser parser = jo -> {};

        final Integer column = parser.getInteger(jsonObject, "column");
        final Integer row = parser.getInteger(jsonObject, "row");
        final Integer width = parser.getInteger(jsonObject, "width");
        final Integer height = parser.getInteger(jsonObject, "height");
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

        print(screen, new Point(column, row));
    }

    /**
     * Checks for, and sets, all positions of the printed rectangle where a
     * connector character is required.
     *
     * @param screen
     *         The screen.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates of the top-left character.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    private void setConnectors(final @NonNull Screen screen, final Point position) {
        for (final Point point : ShapeAlgorithms.getRectangle(position, dimensions)) {
            setConnector(screen, point);
        }
    }

    /**
     * Checks if a character should be a connector and changes it to the
     * appropriate connector character if it should be.
     *
     * @param screen
     *         The screen.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates of the top-left character.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    private void setConnector(final @NonNull Screen screen, final Point position) {
        final boolean validTop = hasValidTopNeighbour(screen, new Point(position));
        final boolean validBottom = hasValidBottomNeighbour(screen, new Point(position));
        final boolean validLeft = hasValidLeftNeighbour(screen, new Point(position));
        final boolean validRight = hasValidRightNeighbour(screen, new Point(position));

        final boolean[] neighbourPattern = new boolean[]{validRight, validTop, validLeft, validBottom};
        final Optional<Character> optChar = rectangleType.getCharacterByNeighbourPattern(neighbourPattern);

        optChar.ifPresent(character -> screen.write(character, position));
    }

    /**
     * Determines if the top-neighbour of a cell is both of the correct
     * RectangleType and that the character of the top-neighbour can be
     * connected to.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates of the top-left character.
     *
     * @return
     *        If the top-neighbour is valid.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    private boolean hasValidTopNeighbour(final @NonNull Screen screen, final Point position) {
        try {
            position.setLocation(position.x, position.y - 1);
            return rectangleType.isValidTopCharacter(screen.getCharacterAt(position));
        } catch (final IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Determines if the bottom-neighbour of a cell is both of the correct
     * RectangleType and that the character of the bottom-neighbour can be
     * connected to.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates of the top-left character.
     *
     * @return
     *        If the bottom-neighbour is valid.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    private boolean hasValidBottomNeighbour(final @NonNull Screen screen, final Point position) {
        try {
            position.setLocation(position.x, position.y + 1);
            return rectangleType.isValidBottomCharacter(screen.getCharacterAt(position));
        } catch (final IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Determines if the left-neighbour of a cell is both of the correct
     * RectangleType and that the character of the left-neighbour can be
     * connected to.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates of the cell.
     *
     * @return
     *        If the left-neighbour is valid.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    private boolean hasValidLeftNeighbour(final @NonNull Screen screen, final Point position) {
        try {
            position.setLocation(position.x - 1, position.y);
            return rectangleType.isValidLeftCharacter(screen.getCharacterAt(position));
        } catch (final IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Determines if the right-neighbour of a cell is both of the correct
     * RectangleType and that the character of the right-neighbour can be
     * connected to.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates of the cell.
     *
     * @return
     *        If the right-neighbour is valid.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    private boolean hasValidRightNeighbour(final @NonNull Screen screen, final Point position) {
        try {
            position.setLocation(position.x + 1, position.y);
            return rectangleType.isValidRightCharacter(screen.getCharacterAt(position));
        } catch (final IllegalArgumentException e) {
            return false;
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
    public RectanglePrinter setHeight(final int height) {
        if (height > 0) {
            dimensions.setSize(dimensions.width, height);
        }

        return this;
    }
}
