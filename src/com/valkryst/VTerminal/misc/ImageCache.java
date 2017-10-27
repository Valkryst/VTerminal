package com.valkryst.VTerminal.misc;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiTile;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.shader.Shader;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.VolatileImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ToString
public final class ImageCache {
    /** The cache. */
    private final Cache<Integer, VolatileImage> cachedImages;

    /** The font of the character images. */
    @Getter private final Font font;

    /** The shaders to run on each image. */
    private final List<Shader> shaders = new ArrayList<>();

    /**
     * Constructs a new ImageCache.
     *
     * @param font
     *         The font.
     *
     * @throws NullPointerException
     *         If the font is null.
     */
    public ImageCache(final @NonNull Font font) {
        this(font, 3);
    }

    /**
     * Constructs a new ImageCache.
     *
     * @param font
     *         The font.
     *
     * @param duration
     *        The number of minutes, after the most recent access, that a cached
     *        image will be removed from the cache.
     *
     * @throws NullPointerException
     *         If the font is null.
     *
     * @throws IllegalArgumentException
     *        If the duration is below 1.
     */
    public ImageCache(final @NonNull Font font, final int duration) {
        if (duration < 1) {
            throw new IllegalArgumentException("The duration cannot be below 1.");
        }

        this.font = font;
        cachedImages = Caffeine.newBuilder()
                              .initialCapacity(5_000)
                              .expireAfterAccess(duration, TimeUnit.MINUTES)
                              .build();
    }

    /**
     * Constructs a new ImageCache.
     *
     * @param font
     *         The font.
     *
     * @param cache
     *        The cache.
     *
     * @throws NullPointerException
     *         If the font or cache is null.
     */
    public ImageCache(final @NonNull Font font, final @NonNull Cache<Integer, VolatileImage> cache) {
        this.font = font;
        this.cachedImages = cache;
    }

    /**
     * Retrieves a character image from the cache.
     *
     * If no image could be found, then one is created, inserted into the cache,
     * and then returned.
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
    public VolatileImage retrieveFromCache(final @NonNull AsciiCharacter character) {
        final int hash = character.getCacheHash();

        VolatileImage image = cachedImages.getIfPresent(hash);

        if (image == null || image.contentsLost()) {
            image = loadIntoCache(character);
        }

        return image;
    }

    /** @return The total number of cached images. */
    public long totalCachedImages() {
        return cachedImages.estimatedSize();
    }

    /**
     * Loads a character into the cache.
     *
     * @param character
     *         The character.
     *
     * @return
     *         The resulting character image.
     */
    public VolatileImage loadIntoCache(final @NonNull AsciiCharacter character) {
        BufferedImage bufferedImage;
        bufferedImage = applyColorSwap(character, font);
        bufferedImage = applyFlips(character, bufferedImage);

        for (final Shader shader : shaders) {
            bufferedImage = shader.run(bufferedImage);
        }

        final VolatileImage result = convertToVolatileImage(bufferedImage);
        cachedImages.put(character.getCacheHash(), result);

        return result;
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
    public static BufferedImage applyColorSwap(final @NonNull AsciiCharacter character, final @NonNull Font font) {
        BufferedImage image;

        try {
            image = cloneImage(font.getCharacterImage(character.getCharacter()));
        } catch (final NullPointerException e) {
            image = new BufferedImage(font.getWidth(), font.getHeight(), BufferedImage.TYPE_INT_ARGB);
            character.setBackgroundColor(new Color(0xFFFF00FF));
            character.setForegroundColor(new Color(0xFFFF00FF));
            return image;
        }


        final int backgroundRGB = character.getBackgroundColor().getRGB();
        final int backgroundA = (backgroundRGB >> 24) & 0xFF;
        final int backgroundR = (backgroundRGB >> 16) & 0xFF;
        final int backgroundG = (backgroundRGB >> 8) & 0xFF;
        final int backgroundB = backgroundRGB & 0xFF;

        final int foregroundRGB = character.getForegroundColor().getRGB();
        final int foregroundA = (foregroundRGB >> 24) & 0xFF;
        final int foregroundR = (foregroundRGB >> 16) & 0xFF;
        final int foregroundG = (foregroundRGB >> 8) & 0xFF;
        final int foregroundB = foregroundRGB & 0xFF;

        final boolean isTile = character instanceof AsciiTile;

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);
                int alpha = (pixel >> 24) & 0xFF;
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = (pixel) & 0xFF;

                boolean isTransparent = alpha == 0;
                isTransparent &= red == 0;
                isTransparent &= green == 0;
                isTransparent &= blue == 0;

                if (isTransparent) {
                    image.setRGB(x, y, backgroundRGB);
                } else if (isTile == false) {
                    if (alpha == 255) {
                        image.setRGB(x, y, foregroundRGB);
                    } else {
                        // Blend the fore/background colors using alpha blending
                        int alphaBlend = backgroundA * (255 - foregroundA) + foregroundA;
                        int redBlend = backgroundR * (255 - foregroundA) + (foregroundR * foregroundA);
                        int greenBlend = backgroundG * (255 - foregroundA) + (foregroundG * foregroundA);
                        int blueBlend = backgroundB * (255 - foregroundA) + (foregroundB * foregroundA);

                        alphaBlend = alphaBlend & 0xFF;
                        redBlend = redBlend & 0xFF;
                        greenBlend = greenBlend & 0xFF;
                        blueBlend = blueBlend & 0xFF;

                        final int blendedRGBA = (alphaBlend << 24) + (redBlend << 16) + (greenBlend << 8) + blueBlend;
                        image.setRGB(x, y, blendedRGBA);
                    }
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
     * @param image
     *        The character image.
     *
     * @return
     *        The flipped character image.
     */
    private static BufferedImage applyFlips(final @NonNull AsciiCharacter character, final @NonNull BufferedImage image) {
        final boolean isFlippedHorizontally = character.isFlippedHorizontally();
        final boolean isFlippedVertically = character.isFlippedVertically();

        if (isFlippedHorizontally || isFlippedVertically) {
            final double scaleX = isFlippedHorizontally ? -1 : 1;
            final double scaleY = isFlippedVertically ? -1 : 1;
            final double translateX = isFlippedHorizontally ? -image.getWidth() : 0;
            final double translateY = isFlippedVertically ? -image.getHeight() : 0;

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
        final BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        final Graphics g = newImage.getGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    /**
     * Converts a BufferedImage into a VolatileImage,
     *
     * @param source
     *        The BufferedImage.
     *
     * @return
     *        The VolatileImage.
     */
    private static VolatileImage convertToVolatileImage(final BufferedImage source) {
        final GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final GraphicsDevice graphicsDevice = graphicsEnvironment.getDefaultScreenDevice();
        final GraphicsConfiguration graphicsConfiguration = graphicsDevice.getDefaultConfiguration();

        final VolatileImage destination = graphicsConfiguration.createCompatibleVolatileImage(source.getWidth(), source.getHeight(), source.getTransparency());

        final Graphics2D g2d = destination.createGraphics();
        g2d.setComposite(AlphaComposite.Src);
        g2d.drawImage(source, 0, 0, null);
        g2d.dispose();

        return destination;
    }

    /**
     * Adds a shader to the set of shaders.
     *
     * The order in which shaders are added determines the order in which the
     * shaders are run.
     *
     * Shaders cannot be removed once added.
     *
     * @param shader
     *          The shader to add.
     */
    public void addShader(final @NonNull Shader shader) {
        shaders.add(shader);
    }

    /**
     * Removes the first occurrence of the specified shader from this set of shaders.
     *
     * If a shader is removed, then the cache is cleared. This is an extremely
     * expensive operation, so it is advised that you do not use this function under
     * normal circumstances.
     *
     * @param shader
     *          The shader to remove.
     */
    public void removeShader(final @NonNull Shader shader) {
        shaders.remove(shader);
        cachedImages.invalidateAll();
    }
}
