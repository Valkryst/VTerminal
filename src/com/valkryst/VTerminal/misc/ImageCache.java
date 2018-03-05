package com.valkryst.VTerminal.misc;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.valkryst.VTerminal.GraphicTile;
import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.shader.Shader;
import com.valkryst.VTerminal.shader.character.CharShader;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.VolatileImage;
import java.awt.image.WritableRaster;
import java.util.concurrent.TimeUnit;

@ToString
public final class ImageCache {
    /** The cache. */
    private final Cache<Integer, VolatileImage> cachedImages;

    /** The font of the character images. */
    @Getter private final Font font;

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
     * Retrieves a tile image from the cache.
     *
     * If no image could be found, then one is created, inserted into the cache,
     * and then returned.
     *
     * @param tile
     *        The tile.
     *
     * @return
     *        The character image.
     *
     * @throws NullPointerException
     *         If the tile is null.
     */
    public VolatileImage retrieve(final @NonNull Tile tile) {
        final int hash = tile.getCacheHash();

        VolatileImage image = cachedImages.getIfPresent(hash);

        if (image == null || image.contentsLost()) {
            image = loadIntoCache(tile);
        }

        return image;
    }

    /**
     * Loads a tile into the cache.
     *
     * @param tile
     *         The tile.
     *
     * @return
     *         The resulting tile image.
     *
     * @throws NullPointerException
     *         If the tile is null.
     */
    public VolatileImage loadIntoCache(final @NonNull Tile tile) {
        BufferedImage bufferedImage;
        bufferedImage = applyColorSwap(tile, font);

        for (final Shader shader : tile.getShaders()) {
            if (shader instanceof CharShader) {
                bufferedImage = ((CharShader) shader).run(bufferedImage, tile);
            } else {
                bufferedImage = shader.run(bufferedImage);
            }
        }

        final VolatileImage result = convertToVolatileImage(bufferedImage);
        cachedImages.put(tile.getCacheHash(), result);

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
    private static BufferedImage applyColorSwap(final @NonNull Tile character, final @NonNull Font font) {
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
        // final int backgroundA = (backgroundRGB >> 24) & 0xFF;
        // final int backgroundR = (backgroundRGB >> 16) & 0xFF;
        // final int backgroundG = (backgroundRGB >> 8) & 0xFF;
        // final int backgroundB = backgroundRGB & 0xFF;

        final int foregroundRGB = character.getForegroundColor().getRGB();
        // final int foregroundA = (foregroundRGB >> 24) & 0xFF;
        final int foregroundR = (foregroundRGB >> 16) & 0xFF;
        final int foregroundG = (foregroundRGB >> 8) & 0xFF;
        final int foregroundB = foregroundRGB & 0xFF;

        final boolean isTile = ! (character instanceof GraphicTile);

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
                    continue;
                }

                if (isTile) {
                    if (alpha == 255) {
                        image.setRGB(x, y, foregroundRGB);
                    } else {
                        final int blendedRGBA = (alpha << 24) + (foregroundR << 16) + (foregroundG << 8) + foregroundB;
                        image.setRGB(x, y, blendedRGBA);
                    }
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
    public static BufferedImage cloneImage(final @NonNull BufferedImage image) {
        final ColorModel colorModel = image.getColorModel();
        final boolean isAlphaPremultiplied = colorModel.isAlphaPremultiplied();
        final WritableRaster writableRaster = image.copyData(null);
        return new BufferedImage(colorModel, writableRaster, isAlphaPremultiplied, null);
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

    /** Invalidates all entries in the cache. */
    public void invalidate() {
        cachedImages.invalidateAll();
        cachedImages.cleanUp();
    }
}
