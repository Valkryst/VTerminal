package com.valkryst.VTerminal.font;

import lombok.NonNull;
import lombok.ToString;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

@ToString
public class Font {
    /** The characters provided by the font. */
    private final HashMap<Integer, FontCharacter> fontCharacters;

    /** The dimensions of the most-common character size supplied by the font. */
    private final Dimension baseDimensions;

    /**
     * Constructs a new Font.
     *
     * @param fontCharacters
     *          The font characters.
     *
     * @param scale
     *          The amount to scale each character image by.
     *
     * @throws NullPointerException
     *           If the characterImages is null.
     */
    public Font(final @NonNull HashMap<Integer, FontCharacter> fontCharacters, final double scale) {
        this.fontCharacters = fontCharacters;

        // Count occurrences of width/heights.
        final HashMap<Integer, Integer> widthCounts = new HashMap<>();
        final HashMap<Integer, Integer> heightCounts = new HashMap<>();

        for (final Map.Entry<Integer, FontCharacter> entry : fontCharacters.entrySet()) {
            final FontCharacter fc = entry.getValue();

            final int fcWidth = fc.getWidth();
            final int fcHeight = fc.getHeight();

            final int widthCount = widthCounts.getOrDefault(fcWidth, 0) + 1;
            final int heightCount = heightCounts.getOrDefault(fcHeight, 0) + 1;

            widthCounts.put(fcWidth, widthCount);
            heightCounts.put(fcHeight, heightCount);
        }

        // Determine the minimum width.
        Map.Entry<Integer, Integer> widthEntry = null;
        int width = Integer.MAX_VALUE;

        for (final Map.Entry<Integer, Integer> entry : widthCounts.entrySet()) {
            if (widthEntry == null) {
                widthEntry = entry;
                width = entry.getKey();
                continue;
            }

            if (widthEntry.getValue() > entry.getValue()) {
                widthEntry = entry;
                width = entry.getKey();
            }
        }

        // Determine the minimum height.
        Map.Entry<Integer, Integer> heightEntry = null;
        int height = Integer.MAX_VALUE;

        for (final Map.Entry<Integer, Integer> entry : heightCounts.entrySet()) {
            if (heightEntry == null) {
                heightEntry = entry;
                height = entry.getKey();
                continue;
            }

            if (heightEntry.getValue() > entry.getValue()) {
                heightEntry = entry;
                height = entry.getKey();
            }
        }

        // Create base dimensions.
        baseDimensions = new Dimension(width, height);

        /*
         * Search for any characters which have dimensions that are not divisible by the most common width
         * and height.
         */
        for (final Map.Entry<Integer, FontCharacter> entry : fontCharacters.entrySet()) {
            final FontCharacter fontCharacter = entry.getValue();
            final int fcWidth = fontCharacter.getWidth();
            final int fcHeight = fontCharacter.getHeight();

            if (fcWidth != width || fcHeight != height) {
                fontCharacter.resizeImage(width, height);
            }
        }

        // Resize font images
        resize(scale, scale);
    }

    public void resize(final double scaleWidth, final double scaleHeight) {
        for (final Map.Entry<Integer, FontCharacter> entry : fontCharacters.entrySet()) {
            entry.getValue().resizeImage(scaleWidth, scaleHeight);
        }
    }

    /**
     * Determines if a unicode character is supported by the font.
     *
     * @param character
     *          The unicode character value
     *
     * @return
     *         Whether or not the character is supported.
     */
    public boolean isCharacterSupported(final int character) {
        return fontCharacters.containsKey(character);
    }

    /**
     * Retrieves the image associated with a character.
     *
     * @param character
     *          The unicode character value
     *
     * @return
     *          The image.
     */
    public BufferedImage getCharacterImage(final int character) {
        return fontCharacters.get(character).getImage();
    }

    /**
     * Retrieves the minimum width of a character cell.
     *
     * @return
     *         The minimum width of a character cell.
     */
    public int getWidth() {
        return baseDimensions.width;
    }

    /**
     * Retrieves the minimum height of a character cell.
     *
     * @return
     *         The minimum height of a character cell.
     */
    public int getHeight() {
        return baseDimensions.height;
    }
}
