package com.valkryst.VTerminal.font;

import lombok.Getter;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Font {
    /** The sprite-images of every character provided by the Font with a completely transparent background. */
    private final HashMap<Character, BufferedImage> characterImages_alphaBackground;
    /** The sprite-images of every character provided by the Font with a black background. */
    private final HashMap<Character, BufferedImage> characterImages_blackBackground = new HashMap<>();

    /** The width of the font. */
    @Getter private final int width;
    /** The height of the font. */
    @Getter private final int height;

    public Font(final HashMap<Character, BufferedImage> characterImages, int scale) throws IOException {
        this.characterImages_alphaBackground = characterImages;

        width = characterImages.get('X').getWidth() * scale;
        height = characterImages.get('X').getHeight() * scale;

        if (scale > 0) {
            for (final Map.Entry<Character, BufferedImage> entry : characterImages.entrySet()) {
                final BufferedImage scaledImage = scaleImage(entry.getValue(), scale);
                characterImages.put(entry.getKey(), scaledImage);
            }
        }

        // Create a set of character images with a black background:final Color newBgColor = Color.BLACK;
        short[] a = new short[256];
        short[] r = new short[256];
        short[] g = new short[256];
        short[] b = new short[256];

        short bga = (byte) (Color.BLACK.getAlpha());
        short bgr = (byte) (Color.BLACK.getRed());
        short bgg = (byte) (Color.BLACK.getGreen());
        short bgb = (byte) (Color.BLACK.getBlue());

        short fga = (byte) (Color.WHITE.getAlpha());
        short fgr = (byte) (Color.WHITE.getRed());
        short fgg = (byte) (Color.WHITE.getGreen());
        short fgb = (byte) (Color.WHITE.getBlue());

        for (int i = 0; i < 256; i++) {
            if (i == 0) {
                a[i] = bga;
                r[i] = bgr;
                g[i] = bgg;
                b[i] = bgb;
            } else {
                a[i] = fga;
                r[i] = fgr;
                g[i] = fgg;
                b[i] = fgb;
            }
        }

        short[][] table = {r, g, b, a};

        final BufferedImageOp op = new LookupOp(new ShortLookupTable(0, table), null);

        for (final Map.Entry<Character, BufferedImage> entry : characterImages.entrySet()) {
            final BufferedImage tmp = entry.getValue();
            final Character character = entry.getKey();
            final BufferedImage image = new BufferedImage(tmp.getWidth(), tmp.getHeight(), tmp.getType());
            op.filter(tmp, image);
            characterImages_blackBackground.put(character, image);
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
    private BufferedImage scaleImage(final BufferedImage image, final int scale) {
        final AffineTransform tx = AffineTransform.getScaleInstance(scale, scale);
        final AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(image, null);
    }

    /**
     * Retrieves the image associated with a character.
     *
     * @param character
     *         The character.
     *
     * @param withBlackBackground
     *         Whether to return an image with a transparent background or not.
     *
     * @return
     *         The image.
     */
    public BufferedImage getCharacterImage(final char character, final boolean withBlackBackground) {
        if (withBlackBackground) {
            return characterImages_blackBackground.get(character);
        } else {
            return characterImages_alphaBackground.get(character);
        }
    }
}
