package com.valkryst.VTerminal.misc;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiTile;
import com.valkryst.VTerminal.font.Font;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.concurrent.TimeUnit;

@ToString
public final class ColoredImageCache {
    /** The cache. */
    private final Cache<Integer, BufferedImage> cachedImages;

    /** The font of the character images. */
    @Getter private final Font font;

    /**
     * Constructs a new ColoredImageCache.
     *
     * @param font
     *         The font.
     *
     * @throws NullPointerException
     *         If the font is null.
     */
    public ColoredImageCache(final @NonNull Font font) {
        this.font = font;
        cachedImages = Caffeine.newBuilder()
                               .initialCapacity(256)
                               .maximumSize(10_000)
                               .expireAfterAccess(3, TimeUnit.MINUTES)
                               .build();
    }

    /**
     * Constructs a new ColoredImageCache.
     *
     * @param font
     *         The font.
     *
     * @param maxCacheSize
     *         The maximum number of images to save in the cache.
     *
     * @throws NullPointerException
     *         If the font is null.
     */
    public ColoredImageCache(final @NonNull Font font, final int maxCacheSize) {
        this.font = font;
        cachedImages = Caffeine.newBuilder()
                               .initialCapacity(256)
                               .maximumSize(maxCacheSize)
                               .expireAfterAccess(3, TimeUnit.MINUTES)
                               .build();
    }

    /**
     * Retrieves a character image from the cache.
     *
     * If no image could be found, then one is created, inserted into
     * the cache, and then returned.
     *
     * @param character
     *        The character.
     *
     * @return
     *        The character image.
     *
     * @throws NullPointerException
     *         If the character is null.
     */
    public BufferedImage retrieveFromCache(final @NonNull AsciiCharacter character) {
        final int hash = character.getCacheHash();

        BufferedImage image = cachedImages.getIfPresent(hash);

        if (image == null) {
            image = applyColorSwap(character, font);
            image = applyFlips(character, font, image);
            cachedImages.put(hash, image);
        }

        return image;
    }

    /**
     * Gets a character image for a character and applies the back/foreground
     * colors to it.
     *
     * @param character
     *        The character.
     *
     * @param font
     *        The font to retrieve the base character image from.
     *
     * @return
     *        The character image.
     *
     * @throws NullPointerException
     *         If the character or font are null.
     */
    private static BufferedImage applyColorSwap(final @NonNull AsciiCharacter character, final @NonNull Font font) {
        BufferedImage image;

        try {
            image = cloneImage(font.getCharacterImage(character.getCharacter()));
        } catch (final NullPointerException e) {
            System.err.println("Couldn't display '" + character.getCharacter() + "', represented by the decimal #"
                               + (int) character.getCharacter() +".");

            System.err.println("When this error occurs, it means that the font being used does not contain a sprite"
                               + " for the character being used.");

            System.err.println("Defaulting to the '?' sprite.\n");

            image = cloneImage(font.getCharacterImage('?'));
        }


        final int backgroundRGB = character.getBackgroundColor().getRGB();
        final int foregroundRGB = character.getForegroundColor().getRGB();
        final boolean isTile = character instanceof AsciiTile;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                boolean isTransparent = alpha != 255;
                isTransparent &= red == 0;
                isTransparent &= green == 0;
                isTransparent &= blue == 0;

                if (isTransparent) {
                    image.setRGB(x, y, backgroundRGB);
                } else if (isTile == false) {
                    image.setRGB(x, y, foregroundRGB);
                }
            }
        }

        return image;
    }

    /**
     * Performs a vertical and/or horizontal flip on a character's image.
     *
     * @param character
     *        The character.
     *
     * @param font
     *        The font to retrieve the base character image from.
     *
     * @param image
     *        The character image.
     *
     * @return
     *        The flipped character image.
     */
    private static BufferedImage applyFlips(final @NonNull AsciiCharacter character, final @NonNull Font font, final @NonNull BufferedImage image) {
        final boolean isFlippedHorizontally = character.isFlippedHorizontally();
        final boolean isFlippedVertically = character.isFlippedVertically();

        if (isFlippedHorizontally || isFlippedVertically) {
            final double scaleX = isFlippedHorizontally ? -1 : 1;
            final double scaleY = isFlippedVertically ? -1 : 1;
            final double translateX = isFlippedHorizontally ? -font.getWidth() : 0;
            final double translateY = isFlippedVertically ? -font.getHeight() : 0;

            final AffineTransform tx = AffineTransform.getScaleInstance(scaleX, scaleY);
            tx.translate(translateX, translateY);

            final BufferedImageOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            return op.filter(image, null);
        }

        return image;
    }

    /**
     * Makes a clone of an image.
     *
     * @param image
     *        The image.
     *
     * @return
     *        The clone image.
     *
     * @throws NullPointerException
     *         If the image is null.
     */
    private static BufferedImage cloneImage(final @NonNull BufferedImage image) {
        final BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        final Graphics g = newImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }
}
