package com.valkryst.VTerminal.printer;

import com.valkryst.VTerminal.AsciiCharacter;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    /** The set of characters that can appear above a RectangleType character. */
    @Getter private final char[] validTopCharacters;
    /** The set of characters that can appear below a RectangleType character. */
    @Getter private final char[] validBottomCharacters;
    /** The set of characters that can appear to the left of a RectangleType character. */
    @Getter private final char[] validLeftCharacters;
    /** The set of characters that can appear to the right of a RectangleType character. */
    @Getter private final char[] validRightCharacters;

    @Getter private Map<Boolean[], Character> characterNeighbourPatterns = new HashMap<>();

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

        validTopCharacters = new char[]{vertical, connectorCross, connectorLeft, connectorRight, connnectorDown,
                                        topLeft, topRight};

        validBottomCharacters = new char[]{vertical, connectorCross, connectorLeft, connectorRight, connectorUp,
                                           bottomLeft, bottomRight};

        validLeftCharacters = new char[]{horizontal, connectorCross, connectorRight, connnectorDown, connectorUp,
                                         topLeft, bottomLeft};

        validRightCharacters = new char[]{horizontal, connectorCross, connectorLeft, connnectorDown, connectorUp,
                                          topRight, bottomRight};

        characterNeighbourPatterns.put(new Boolean[]{false, false, true, true}, topRight);
        characterNeighbourPatterns.put(new Boolean[]{false, true, false, true}, vertical);
        characterNeighbourPatterns.put(new Boolean[]{false, true, true, false}, bottomRight);
        characterNeighbourPatterns.put(new Boolean[]{false, true, true, true}, connectorLeft);
        characterNeighbourPatterns.put(new Boolean[]{true, false, false, true}, topLeft);
        characterNeighbourPatterns.put(new Boolean[]{true, false, true, false}, horizontal);
        characterNeighbourPatterns.put(new Boolean[]{true, false, true, true}, connnectorDown);
        characterNeighbourPatterns.put(new Boolean[]{true, true, false, false}, bottomLeft);
        characterNeighbourPatterns.put(new Boolean[]{true, true, false, true}, connectorRight);
        characterNeighbourPatterns.put(new Boolean[]{true, true, true, false}, connectorUp);
        characterNeighbourPatterns.put(new Boolean[]{true, true, true, true}, connectorCross);
    }

    /**
     * Determines if a character is in the set of characters that can
     * appear above a RectangleType character.
     *
     * @param asciiCharacter
     *        The character.
     *
     * @return
     *        If the character is in the set.
     */
    public boolean isValidTopCharacter(final AsciiCharacter asciiCharacter) {
        return asciiCharacter != null && isValidCharacter(asciiCharacter.getCharacter(), validTopCharacters);

    }

    /**
     * Determines if a character is in the set of characters that can
     * appear below a RectangleType character.
     *
     * @param asciiCharacter
     *        The character.
     *
     * @return
     *        If the character is in the set.
     */
    public boolean isValidBottomCharacter(final AsciiCharacter asciiCharacter) {
        return asciiCharacter != null && isValidCharacter(asciiCharacter.getCharacter(), validBottomCharacters);

    }

    /**
     * Determines if a character is in the set of characters that can
     * appear to the left of a RectangleType character.
     *
     * @param asciiCharacter
     *        The character.
     *
     * @return
     *        If the character is in the set.
     */
    public boolean isValidLeftCharacter(final AsciiCharacter asciiCharacter) {
        return asciiCharacter != null && isValidCharacter(asciiCharacter.getCharacter(), validLeftCharacters);

    }

    /**
     * Determines if a character is in the set of characters that can
     * appear to the right of a RectangleType character.
     *
     * @param asciiCharacter
     *        The character.
     *
     * @return
     *        If the character is in the set.
     */
    public boolean isValidRightCharacter(final AsciiCharacter asciiCharacter) {
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
    private boolean isValidCharacter(final char character, final char[] validCharacters) {
        for (final char validCharacter : validCharacters) {
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
    public Optional<Character> getCharacterByNeighbourPattern(final Boolean[] pattern) {
        return Optional.of(characterNeighbourPatterns.get(pattern));
    }
}
