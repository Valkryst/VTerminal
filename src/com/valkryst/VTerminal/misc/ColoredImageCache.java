package com.valkryst.VTerminal.misc;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.font.Font;
import lombok.Getter;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ColoredImageCache {
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
    public ColoredImageCache(final Font font) {
        Objects.requireNonNull(font);

        this.font = font;
        cachedImages = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(10_000)
                .expireAfterAccess(5, TimeUnit.MINUTES)
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
    public ColoredImageCache(final Font font, final int maxCacheSize) {
        Objects.requireNonNull(font);

        this.font = font;
        cachedImages = Caffeine.newBuilder()
                .initialCapacity(100)
                .maximumSize(maxCacheSize)
                .expireAfterAccess(5, TimeUnit.MINUTES)
                .build();
    }

    @Override
    public int hashCode() {
        return Objects.hash(cachedImages);
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
    public BufferedImage retrieveFromCache(final AsciiCharacter character) {
        Objects.requireNonNull(character);

        final int hashCode = Objects.hash(character.getCharacter(),
                                          character.getBackgroundColor(),
                                          character.getForegroundColor());

        BufferedImage image = cachedImages.getIfPresent(hashCode);

        if (image == null) {
            image = applyColorSwap(character, font);
            cachedImages.put(hashCode, image);
        }

        return image;
    }

    /**
     * Gets a character image for a character  and applies the back/foreground
     * colors to it.
     *
     * @param font
     *        The font to retrieve the base character image from.
     *
     * @return
     *        The character image.
     *
     * @throws NullPointerException
     *         If the character is null.
     */
    private static BufferedImage applyColorSwap(final AsciiCharacter character, final Font font) {
        Objects.requireNonNull(character);

        final BufferedImage image = cloneImage(font.getCharacterImage(character.getCharacter()));
        final int backgroundRGB = character.getBackgroundColor().getRGB();
        final int foregroundRGB = character.getForegroundColor().getRGB();

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
                } else {
                    image.setRGB(x, y, foregroundRGB);
                }
            }
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
    private static BufferedImage cloneImage(final BufferedImage image) {
        Objects.requireNonNull(image);

        final BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        final Graphics g = newImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }
}
