package com.valkryst.VTerminal.font;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.awt.Dimension;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
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
     */
    public Font(final @NonNull HashMap<Character, BufferedImage> characterImages, final double scale) {
        this.characterImages = characterImages;

        final int width = (int) (characterImages.get('X').getWidth() * scale);
        final int height = (int) (characterImages.get('X').getHeight() * scale);
        dimensions = new Dimension(width, height);

        resize(scale, scale);
    }

    /**
     * Resizes the font.
     *
     * You probably shouldn't be calling this function, it exists specifically
     * for when full screen mode is enabled and the font needs to be
     * resized to fit the screen.
     *
     * @param scaleX
     *          The amount to scale the font width by.
     *
     * @param scaleY
     *          The amount to scale the font height by.
     */
    public void resize(final double scaleX, final double scaleY) {
        if (scaleX > 0 && scaleY > 0) {
            final int width = (int) (characterImages.get('X').getWidth() * scaleX);
            final int height = (int) (characterImages.get('X').getHeight() * scaleY);
            dimensions.setSize(width, height);

            final AffineTransform tx = AffineTransform.getScaleInstance(scaleX, scaleY);
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

    /**
     * Retrieves the width of a character cell.
     *
     * @return
     *         The width of a character cell.
     */
    public int getWidth() {
        return dimensions.width;
    }

    /**
     * Retrieves the height of a character cell.
     *
     * @return
     *         The height of a character cell.
     */
    public int getHeight() {
        return dimensions.height;
    }
}
