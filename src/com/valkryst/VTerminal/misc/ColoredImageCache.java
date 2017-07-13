package com.valkryst.VTerminal.misc;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.font.Font;
import lombok.Getter;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class ColoredImageCache {
    /** The cache. */
    private final LinkedHashMap<AsciiCharacterShell, BufferedImage> cachedImages;

    /** The font of the character images. */
    @Getter private final Font font;

    /**
     * Constructs a new ColoredImageCache.
     *
     * @param font
     *         The font.
     */
    public ColoredImageCache(final Font font) {
        this.font = font;
        cachedImages = new LinkedHashMap<AsciiCharacterShell, BufferedImage>()  {
            private static final long serialVersionUID = 3550239335645856488L;

            protected boolean removeEldestEntry(final Map.Entry<AsciiCharacterShell, BufferedImage> eldest) {
                return this.size() >= 10000;
            }
        };
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
     *         When this value is reached, or exceeded, then the cache
     *         discards the eldest cache entry to make room for a new
     *         entry.
     */
    public ColoredImageCache(final Font font, final int maxCacheSize) {
        this.font = font;
        cachedImages = new LinkedHashMap<AsciiCharacterShell, BufferedImage>() {
            private static final long serialVersionUID = 7940325226870365646L;

            protected boolean removeEldestEntry(final Map.Entry<AsciiCharacterShell, BufferedImage> eldest) {
                return this.size() >= maxCacheSize;
            }
        };
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
     *         The character.
     *
     * @return
     *         The character image.
     */
    public BufferedImage retrieveFromCache(final AsciiCharacter character) {
        final AsciiCharacterShell shell = new AsciiCharacterShell(character, font);
        return cachedImages.computeIfAbsent(shell, s -> applyColorSwap(s, font));
    }

    /**
     * Gets a character image for a character shell and applies the
     * back/foreground colors to it.
     *
     * @param characterShell
     *         The character shell.
     *
     * @param font
     *         The font to retrieve the base character image from.
     *
     * @return
     *         The character image.
     */
    private static BufferedImage applyColorSwap(final AsciiCharacterShell characterShell, final Font font) {
        final BufferedImage image = font.getCharacterImage(characterShell.getCharacter());
        final int backgroundRGB = characterShell.getBackgroundColor().getRGB();
        final int foregroundRGB = characterShell.getForegroundColor().getRGB();

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = image.getRGB(x, y);
                //int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                // Note that all of the Colors that aren't constructed with new Color(rgb, true),
                // or new Color(0, 0, 0, 0), will have an alpha value of 255. Because of this,
                // I'm ignoring the alpha channel.
                // All incoming images are expected to use a white character with alpha'd background,
                // so we can assume that rgb(0,0,0) is alpha and rgb(255,255,255) is white.
                boolean isTransparent = red == 0;
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

    private class AsciiCharacterShell {
        /** The character. */
        @Getter private final char character;
        /** The background color. Defaults to black. */
        @Getter private final Color backgroundColor;
        /** The foreground color. Defaults to white. */
        @Getter private final Color foregroundColor;

        public AsciiCharacterShell(final AsciiCharacter character, final Font font) {
            if (character == null) {
                throw new IllegalArgumentException("The AsciiCharacterShell cannot use a null character");
            }

            if (font == null) {
                throw new IllegalArgumentException("The AsciiCharacterShell cannot have a null font.");
            }

            this.character = character.getCharacter();
            this.backgroundColor = character.getBackgroundColor();
            this.foregroundColor = character.getForegroundColor();
        }

        @Override
        public String toString() {
            String res = "Color Shell:";
            res += "\n\tCharacter:\t'" + character +"'";
            res += "\n\tBackground Color:\t" + backgroundColor;
            res += "\n\tForeground Color:\t" + foregroundColor;

            return res;
        }

        @Override
        public boolean equals(final Object otherObj) {
            if (otherObj instanceof AsciiCharacterShell == false) {
                return false;
            }

            if (otherObj == this) {
                return true;
            }

            final AsciiCharacterShell otherShell = (AsciiCharacterShell) otherObj;
            boolean isEqual = character == otherShell.getCharacter();
            isEqual &= backgroundColor.equals(otherShell.getBackgroundColor());
            isEqual &= foregroundColor.equals(otherShell.getForegroundColor());
            return isEqual;
        }

        @Override
        public int hashCode() {
            return Objects.hash(character, backgroundColor, foregroundColor);
        }
    }
}
