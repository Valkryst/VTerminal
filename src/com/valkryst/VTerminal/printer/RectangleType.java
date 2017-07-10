package com.valkryst.VTerminal.printer;

import lombok.Getter;

public enum RectangleType {
    SIMPLE(new char[]{'+', '+', '+', '+', '|', '-', '+', '+', '+', '+', '+'}),
    THIN(new char[]{'┌', '┐', '└', '┘', '│', '─', '┼', '┤', '├', '┬', '┴'}),
    HEAVY(new char[]{'╔', '╗', '╚', '╝', '║', '═', '╬', '╣', '╠', '╦', '╩'}),
    MIXED_HEAVY_HORIZONTAL(new char[]{'╒', '╕', '╘', '╛', '│', '═', '╪', '╡', '╞', '╤', '╧'}),
    MIXED_HEAVY_VERTICAL(new char[]{'╓', '╖', '╙', '╜', '║', '─', '╫', '╢', '╟', '╥', '╨'});

    /** The top left character of a rectangle. */
    @Getter private final char topLeft;
    /** The top right character of a rectangle. */
    @Getter private final char topRight;
    /** The bottom left character of a rectangle. */
    @Getter private final char bottomLeft;
    /** The bottom right character of a rectangle. */
    @Getter private final char bottomRight;
    /** The vertical character of a rectangle. */
    @Getter private final char vertical;
    /** The horizontal character of a rectangle. */
    @Getter private final char horizontal;
    /** The cross connector character of a rectangle. */
    @Getter private final char connectorCross;
    /** The left connector character of a rectangle. */
    @Getter private final char connectorLeft;
    /** The right connector character of a rectangle. */
    @Getter private final char connectorRight;
    /** The downwards connector character of a rectangle. */
    @Getter private final char connnectorDown;
    /** The upwards connector character of a rectangle. */
    @Getter private final char connectorUp;

    /**
     * Constructs a new RectangleType.
     *
     * @param boxCharacters
     *         The characters of a rectangle.
     */
    RectangleType(char[] boxCharacters) {
        if (boxCharacters.length != 6) {
            boxCharacters = new char[]{'?', '?', '?', '?', '?', '?', '?', '?', '?', '?', '"'};
        }

        topLeft = boxCharacters[0];
        topRight = boxCharacters[1];
        bottomLeft = boxCharacters[2];
        bottomRight = boxCharacters[3];
        vertical = boxCharacters[4];
        horizontal = boxCharacters[5];
        connectorCross = boxCharacters[6];
        connectorLeft = boxCharacters[7];
        connectorRight = boxCharacters[8];
        connnectorDown = boxCharacters[9];
        connectorUp = boxCharacters[10];
    }
}
