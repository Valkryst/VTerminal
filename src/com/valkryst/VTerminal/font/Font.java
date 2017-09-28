package com.valkryst.VTerminal.font;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ToString
public class Font {
    /**
     * The sprite-images of every character provided by the Font with a completely
     * transparent background.
     */
    private final HashMap<Character, BufferedImage> characterImages;

    /** The width/height of the font. */
    @Getter private final Dimension dimensions;

    /**
     * Constructs a new Font.
     *
     * @param characterImages
     *        The base character images.
     *
     * @param scale
     *        The amount to scale each character image by.
     *
     * @throws NullPointerException
     *         If the characterImages is null.
     *
     * @throws IOException
     *         If an I/O error occurs.
     */
    public Font(final @NonNull HashMap<Character, BufferedImage> characterImages, final int scale) throws IOException {
        this.characterImages = characterImages;

        final int width = characterImages.get('X').getWidth() * scale;
        final int height = characterImages.get('X').getHeight() * scale;
        dimensions = new Dimension(width, height);

        if (scale > 0) {
            final AffineTransform tx = AffineTransform.getScaleInstance(scale, scale);
            final AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

            for (final Map.Entry<Character, BufferedImage> entry : characterImages.entrySet()) {
                characterImages.put(entry.getKey(), op.filter(entry.getValue(), null));
            }
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
        return characterImages.containsKey(character);
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
        return characterImages.get(character);
    }

    public int getWidth() {
        return dimensions.width;
    }

    public int getHeight() {
        return dimensions.height;
    }
}
