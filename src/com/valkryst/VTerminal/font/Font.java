package com.valkryst.VTerminal.font;

import lombok.NonNull;
import lombok.ToString;
import org.apache.logging.log4j.LogManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ToString
public class Font {
    /** The characters provided by the font. */
    private final HashMap<Character, FontCharacter> fontCharacters;

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
    public Font(final @NonNull HashMap<Character, FontCharacter> fontCharacters, final double scale) {
        this.fontCharacters = fontCharacters;

        final HashMap<Integer, Integer> widthCounters = new HashMap<>();
        final HashMap<Integer, Integer> heightCounters = new HashMap<>();

        for (final Map.Entry<Character, FontCharacter> entry : fontCharacters.entrySet()) {
            final FontCharacter fontCharacter = entry.getValue();

            // Resizes the character images.
            fontCharacter.resizeImage(scale, scale);

            // Count occurrences of width/height dimensions.
            final int width = fontCharacter.getWidth();
            final int height = fontCharacter.getHeight();

            if (widthCounters.containsKey(width)) {
                final int count = widthCounters.get(width);
                widthCounters.put(width, count + 1);
            } else {
                widthCounters.put(width, 0);
            }

            if (heightCounters.containsKey(height)) {
                final int count = heightCounters.get(height);
                heightCounters.put(height, count + 1);
            } else {
                heightCounters.put(height, 0);
            }
        }

        // Calculate most common width/height dimensions.
        Map.Entry<Integer, Integer> width = null;
        Map.Entry<Integer, Integer> height = null;

        for (final Map.Entry<Integer, Integer> entry : widthCounters.entrySet()) {
            if (width == null) {
                width = entry;
            }

            if (width.getValue() < entry.getValue()) {
                width = entry;
            }
        }

        for (final Map.Entry<Integer, Integer> entry : heightCounters.entrySet()) {
            if (height == null) {
                height = entry;
            }

            if (height.getValue() < entry.getValue()) {
                height = entry;
            }
        }

        // Create base dimensions.
        final int baseWidth = (width == null ? 1 : width.getKey());
        final int baseHeight = (height == null ? 1 : height.getKey());
        baseDimensions = new Dimension(baseWidth, baseHeight);

        /*
         * Search for any characters which have dimensions that are not divisible by the most common width
         * and height.
         */
        final List<FontCharacter> invalidCharacters = new ArrayList<>();

        for (final Map.Entry<Character, FontCharacter> entry : fontCharacters.entrySet()) {
            final FontCharacter fontCharacter = entry.getValue();
            final int fcWidth = fontCharacter.getWidth();
            final int fcHeight = fontCharacter.getHeight();
            boolean isValid = true;


            if (fcWidth < baseWidth) {
                LogManager.getLogger().error("Font Error:");
                LogManager.getLogger().error("\n\t" + fontCharacter);
                LogManager.getLogger().error("\n\tThe character's width is less than the font's base width of " + baseWidth + ".");
                isValid = false;
            }

            if (fcHeight < baseHeight) {
                LogManager.getLogger().error("Font Error:");
                LogManager.getLogger().error("\n\t" + fontCharacter);
                LogManager.getLogger().error("\n\tThe character's height is less than the font's base height of " + baseHeight + ".");
                isValid = false;
            }

            if (fcWidth % baseWidth != 0) {
                LogManager.getLogger().error("Font Error:");
                LogManager.getLogger().error("\n\t" + fontCharacter);
                LogManager.getLogger().error("\n\tThe character's width is not divisible by the font's base width " + baseWidth + ".");
                isValid = false;
            }

            if (fcHeight % baseHeight != 0) {
                LogManager.getLogger().error("Font Error:");
                LogManager.getLogger().error("\n\t" + fontCharacter);
                LogManager.getLogger().error("\n\tThe character's height is not divisible by the font's base height " + baseHeight + ".");
                isValid = false;
            }

            if (isValid == false) {
                invalidCharacters.add(fontCharacter);
            }
        }

        invalidCharacters.forEach(fontCharacters::remove);
    }

    public void resize(final double scaleWidth, final double scaleHeight) {
        for (final Map.Entry<Character, FontCharacter> entry : fontCharacters.entrySet()) {
            entry.getValue().resizeImage(scaleWidth, scaleHeight);
        }
    }

    /**
     * Determines if a character is supported by the font.
     *
     * A character is supported if there exists an image
     * for it.
     *
     * @param character
     *         The character.
     *
     * @return
     *         Whether or not the character is supported.
     */
    public boolean isCharacterSupported(final char character) {
        return fontCharacters.containsKey(character);
    }

    /**
     * Retrieves the image associated with a character.
     *
     * @param character
     *         The character.
     *
     * @return
     *         The image.
     */
    public BufferedImage getCharacterImage(final char character) {
        return fontCharacters.get(character).getImage();
    }

    /**
     * Retrieves the most common width of a character cell.
     *
     * @return
     *         The most common width of a character cell.
     */
    public int getWidth() {
        return baseDimensions.width;
    }

    /**
     * Retrieves the most common height of a character cell.
     *
     * @return
     *         The most common height of a character cell.
     */
    public int getHeight() {
        return baseDimensions.height;
    }
}
