package com.valkryst.VTerminal.font;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ToString
public class Font {
    /** The sprite-images of every character provided by the Font with a completely transparent background. */
    private final HashMap<Character, BufferedImage> characterImages;

    /** The width of the font. */
    @Getter private final int width;
    /** The height of the font. */
    @Getter private final int height;

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
    public Font(final @NonNull HashMap<Character, BufferedImage> characterImages, int scale) throws IOException {
        this.characterImages = characterImages;

        width = characterImages.get('X').getWidth() * scale;
        height = characterImages.get('X').getHeight() * scale;

        if (scale > 0) {
            for (final Map.Entry<Character, BufferedImage> entry : characterImages.entrySet()) {
                final BufferedImage scaledImage = scaleImage(entry.getValue(), scale);
                characterImages.put(entry.getKey(), scaledImage);
            }
        }
    }

    /**
     * Scales an image.
     *
     * @param image
     *         The image.
     *
     * @param scale
     *         The amount to scale by.
     *
     * @return
     *         The scaled image.
     */
    private BufferedImage scaleImage(final @NonNull BufferedImage image, final int scale) {
        Objects.requireNonNull(image);

        final AffineTransform tx = AffineTransform.getScaleInstance(scale, scale);
        final AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(image, null);
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
}
