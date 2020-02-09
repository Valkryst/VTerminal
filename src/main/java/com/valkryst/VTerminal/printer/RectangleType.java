package com.valkryst.VTerminal.printer;

import com.valkryst.VTerminal.Tile;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

public enum RectangleType {
    NONE(new int[]{'█', '█', '█', '█', '█', '█', '█', '█', '█', '█', '█'}),
    SIMPLE(new int[]{'+', '+', '+', '+', '|', '-', '+', '+', '+', '+', '+'}),
    THIN(new int[]{'┌', '┐', '└', '┘', '│', '─', '┼', '┤', '├', '┬', '┴'}),
    HEAVY(new int[]{'╔', '╗', '╚', '╝', '║', '═', '╬', '╣', '╠', '╦', '╩'}),
    MIXED_HEAVY_HORIZONTAL(new int[]{'╒', '╕', '╘', '╛', '│', '═', '╪', '╡', '╞', '╤', '╧'}),
    MIXED_HEAVY_VERTICAL(new int[]{'╓', '╖', '╙', '╜', '║', '─', '╫', '╢', '╟', '╥', '╨'});

    /** The top left character of a rectangle. */
    @Getter private final int topLeft;
    /** The top right character of a rectangle. */
    @Getter private final int topRight;
    /** The bottom left character of a rectangle. */
    @Getter private final int bottomLeft;
    /** The bottom right character of a rectangle. */
    @Getter private final int bottomRight;
    /** The vertical character of a rectangle. */
    @Getter private final int vertical;
    /** The horizontal character of a rectangle. */
    @Getter private final int horizontal;
    /** The cross connector character of a rectangle. */
    @Getter private final int connectorCross;
    /** The left connector character of a rectangle. */
    @Getter private final int connectorLeft;
    /** The right connector character of a rectangle. */
    @Getter private final int connectorRight;
    /** The downwards connector character of a rectangle. */
    @Getter private final int connectorDown;
    /** The upwards connector character of a rectangle. */
    @Getter private final int connectorUp;

    /** The set of characters that can appear above a RectangleType character. */
    @Getter private final int[] validTopCharacters;
    /** The set of characters that can appear below a RectangleType character. */
    @Getter private final int[] validBottomCharacters;
    /**
     * The set of characters that can appear to the left of a RectangleType
     * character.
     */
    @Getter private final int[] validLeftCharacters;
    /**
     * The set of characters that can appear to the right of a RectangleType
     * character.
     */
    @Getter private final int[] validRightCharacters;

    /**
     * Constructs a new RectangleType.
     *
     * @param boxCharacters
     *          The characters of a rectangle.
     */
    RectangleType(final int[] boxCharacters) {
        if (boxCharacters.length != 11) {
            throw new IllegalArgumentException("A RectangleType requires exactly 11 characters.");
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
        connectorDown = boxCharacters[9];
        connectorUp = boxCharacters[10];

        validTopCharacters = new int[]{vertical, connectorCross, connectorLeft, connectorRight, connectorDown, topLeft, topRight};
        validBottomCharacters = new int[]{vertical, connectorCross, connectorLeft, connectorRight, connectorUp, bottomLeft, bottomRight};
        validLeftCharacters = new int[]{horizontal, connectorCross, connectorRight, connectorDown, connectorUp, topLeft, bottomLeft};
        validRightCharacters = new int[]{horizontal, connectorCross, connectorLeft, connectorDown, connectorUp, topRight, bottomRight};
    }

    /**
     * Determines if a character is in the set of characters that can appear above
     * a RectangleType character.
     *
     * @param asciiCharacter
     *        The character.
     *
     * @return
     *        If the character is in the set.
     */
    public boolean isValidTopCharacter(final Tile asciiCharacter) {
        return asciiCharacter != null && isValidCharacter(asciiCharacter.getCharacter(), validTopCharacters);

    }

    /**
     * Determines if a character is in the set of characters that can appear below
     * a RectangleType character.
     *
     * @param asciiCharacter
     *        The character.
     *
     * @return
     *        If the character is in the set.
     */
    public boolean isValidBottomCharacter(final Tile asciiCharacter) {
        return asciiCharacter != null && isValidCharacter(asciiCharacter.getCharacter(), validBottomCharacters);

    }

    /**
     * Determines if a character is in the set of characters that can appear to
     * the left of a RectangleType character.
     *
     * @param asciiCharacter
     *        The character.
     *
     * @return
     *        If the character is in the set.
     */
    public boolean isValidLeftCharacter(final Tile asciiCharacter) {
        return asciiCharacter != null && isValidCharacter(asciiCharacter.getCharacter(), validLeftCharacters);

    }

    /**
     * Determines if a character is in the set of characters that can appear to
     * the right of a RectangleType character.
     *
     * @param asciiCharacter
     *        The character.
     *
     * @return
     *        If the character is in the set.
     */
    public boolean isValidRightCharacter(final Tile asciiCharacter) {
        return asciiCharacter != null && isValidCharacter(asciiCharacter.getCharacter(), validRightCharacters);

    }

    /**
     * Determines if a character is in a set of characters.
     *
     * @param character
     *        The character.
     *
     * @param validCharacters
     *        The set.
     *
     * @return
     *        If the character is in the set.
     */
    private boolean isValidCharacter(final int character, final int[] validCharacters) {
        for (final int validCharacter : validCharacters) {
            if (character == validCharacter) {
                return true;
            }
        }

        return false;
    }

    /**
     * Retrieves a character by it's neighbour pattern.
     *
     * @param pattern
     *        The pattern.
     *
     * @return
     *        The character.
     */
    public Optional<Integer> getCharacterByNeighbourPattern(final boolean[] pattern) {
        if (Arrays.equals(pattern, new boolean[]{false, false, true, true})) {
            return Optional.of(topRight);
        }

        if (Arrays.equals(pattern, new boolean[]{false, true, false, true})) {
            return Optional.of(vertical);
        }

        if (Arrays.equals(pattern, new boolean[]{false, true, true, false})) {
            return Optional.of(bottomRight);
        }

        if (Arrays.equals(pattern, new boolean[]{false, true, true, true})) {
            return Optional.of(connectorLeft);
        }

        if (Arrays.equals(pattern, new boolean[]{true, false, false, true})) {
            return Optional.of(topLeft);
        }

        if (Arrays.equals(pattern, new boolean[]{true, false, true, false})) {
            return Optional.of(horizontal);
        }

        if (Arrays.equals(pattern, new boolean[]{true, false, true, true})) {
            return Optional.of(connectorDown);
        }

        if (Arrays.equals(pattern, new boolean[]{true, true, false, false})) {
            return Optional.of(bottomLeft);
        }

        if (Arrays.equals(pattern, new boolean[]{true, true, false, true})) {
            return Optional.of(connectorRight);
        }

        if (Arrays.equals(pattern, new boolean[]{true, true, true, false})) {
            return Optional.of(connectorUp);
        }

        if (Arrays.equals(pattern, new boolean[]{true, true, true, true})) {
            return Optional.of(connectorCross);
        }

        return Optional.empty();
    }
}
