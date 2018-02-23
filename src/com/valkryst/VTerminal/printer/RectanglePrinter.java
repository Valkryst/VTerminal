package com.valkryst.VTerminal.printer;

import com.valkryst.VTerminal.component.Component;
import com.valkryst.VTerminal.misc.ShapeAlgorithms;
import lombok.*;

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
     * Prints a rectangle on a screen.
     *
     * If the function is set to perform connections, then it will attempt to
     * connect the new rectangle with existing similar rectangles in the draw area.
     *
     * @param component
     *         The component.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates of the top-left character.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    public void print(final @NonNull Component component, final Point position) {
        final int width = dimensions.width;
        final int height = dimensions.height;
        final int x = position.x;
        final int y = position.y;

        final int lastRow = y + height - 1;
        final int lastColumn = x + width - 1;

        final Point writePosition = new Point(0, 0);

        // Draw Corners:
        component.getTiles().getTileAt(writePosition).setCharacter(rectangleType.getTopLeft());

        writePosition.setLocation(lastColumn, y);
        component.getTiles().getTileAt(writePosition).setCharacter(rectangleType.getTopRight());

        writePosition.setLocation(x, lastRow);
        component.getTiles().getTileAt(writePosition).setCharacter(rectangleType.getBottomLeft());

        writePosition.setLocation(lastColumn, lastRow);
        component.getTiles().getTileAt(writePosition).setCharacter(rectangleType.getBottomRight());

        // Draw Left/Right Sides:
        for (int i = 1 ; i < height - 1 ; i++) {
            writePosition.setLocation(x, y + i);
            component.getTiles().getTileAt(writePosition).setCharacter(rectangleType.getVertical());

            writePosition.setLocation(lastColumn, y + i);
            component.getTiles().getTileAt(writePosition).setCharacter(rectangleType.getVertical());
        }

        // Draw Top/Bottom Sides:
        for (int i = 1 ; i < width - 1 ; i++) {
            writePosition.setLocation(x + i, y);
            component.getTiles().getTileAt(writePosition).setCharacter(rectangleType.getHorizontal());

            writePosition.setLocation(x + i, lastRow);
            component.getTiles().getTileAt(writePosition).setCharacter(rectangleType.getHorizontal());
        }

        // Draw title on Top Side:
        if (title != null && title.isEmpty() == false) {
            // Draw Title Text:
            final char[] titleChars = title.toCharArray();

            for (int i = 2; i < width - 2 && i - 2 < titleChars.length; i++) {
                writePosition.setLocation(x + i, y);
                component.getTiles().getTileAt(writePosition).setCharacter(titleChars[i - 2]);
            }

            // Draw Title Borders:
            writePosition.setLocation(x + 1, y);
            component.getTiles().getTileAt(writePosition).setCharacter(rectangleType.getConnectorLeft());

            writePosition.setLocation(x + titleChars.length + 2, y);
            component.getTiles().getTileAt(writePosition).setCharacter(rectangleType.getConnectorRight());
        }

        // Handle Connectors:
        setConnectors(component, position);
    }

    /**
     * Prints a rectangle on a component.
     *
     * If the function is set to perform connections, then it will attempt to
     * connect the new rectangle with existing similar rectangles in the draw area.
     *
     * @param component
     *         The component.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates of the top-left character.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    public void printFilled(final @NonNull Component component, final Point position) {
        print(component, position);

        final Dimension dimension = new Dimension(this.dimensions.width - 2, this.dimensions.height - 2);
        position.setLocation(position.x + 1, position.y + 1);

        for (final Point point : ShapeAlgorithms.getFilledRectangle(position, dimension)) {
            component.getTiles().getTileAt(position).setCharacter(fillChar);
        }
    }

    /**
     * Checks for, and sets, all positions of the printed rectangle where a
     * connector character is required.
     *
     * @param component
     *         The component.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates of the top-left character.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    private void setConnectors(final @NonNull Component component, final Point position) {
        for (final Point point : ShapeAlgorithms.getRectangle(position, dimensions)) {
            setConnector(component, point);
        }
    }

    /**
     * Checks if a character should be a connector and changes it to the
     * appropriate connector character if it should be.
     *
     * @param component
     *         The component.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates of the top-left character.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    private void setConnector(final @NonNull Component component, final Point position) {
        final boolean validTop = hasValidTopNeighbour(component, new Point(position));
        final boolean validBottom = hasValidBottomNeighbour(component, new Point(position));
        final boolean validLeft = hasValidLeftNeighbour(component, new Point(position));
        final boolean validRight = hasValidRightNeighbour(component, new Point(position));

        final boolean[] neighbourPattern = new boolean[]{validRight, validTop, validLeft, validBottom};
        final Optional<Character> optChar = rectangleType.getCharacterByNeighbourPattern(neighbourPattern);

        optChar.ifPresent(character -> component.getTiles().getTileAt(position).setCharacter(character));
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
    private boolean hasValidTopNeighbour(final @NonNull Component component, final Point position) {
        try {
            position.setLocation(position.x, position.y - 1);
            return rectangleType.isValidTopCharacter(component.getTiles().getTileAt(position));
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
    private boolean hasValidBottomNeighbour(final @NonNull Component component, final Point position) {
        try {
            position.setLocation(position.x, position.y + 1);
            return rectangleType.isValidBottomCharacter(component.getTiles().getTileAt(position));
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
    private boolean hasValidLeftNeighbour(final @NonNull Component component, final Point position) {
        try {
            position.setLocation(position.x - 1, position.y);
            return rectangleType.isValidLeftCharacter(component.getTiles().getTileAt(position));
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
    private boolean hasValidRightNeighbour(final @NonNull Component component, final Point position) {
        try {
            position.setLocation(position.x + 1, position.y);
            return rectangleType.isValidRightCharacter(component.getTiles().getTileAt(position));
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
