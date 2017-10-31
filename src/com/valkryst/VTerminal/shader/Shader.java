package com.valkryst.VTerminal.shader;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.misc.ImageCache;
import lombok.NonNull;

import java.awt.Color;
import java.awt.image.BufferedImage;

public interface Shader {
    /**
     * Runs the shader on a character image.
     *
     * @param image
     *          The character image.
     *
     * @param character
     *          The character.
     *
     * @return
     *          The processed character image.
     *
     * @throws NullPointerException
     *           If the image or character is null.
     */
    BufferedImage run(final @NonNull BufferedImage image, final @NonNull AsciiCharacter character);

    /**
     * Returns a copy of the shader.
     *
     * @return
     *
     */
    Shader copy();

    /**
     * Swaps two colors on an image.
     *
     * @param image
     *          The image.
     *
     * @param oldColor
     *          The color to swap out.
     *
     * @param newColor
     *          The color to swap in.
     *
     * @return
     *          The result image.
     *
     * @throws NullPointerException
     *           If the image or either color is null.
     */
    default BufferedImage swapColor(final @NonNull BufferedImage image, final @NonNull Color oldColor, final @NonNull Color newColor) {
        final BufferedImage result = ImageCache.cloneImage(image);

        final int newRGB = newColor.getRGB();
        final int oldRGB = oldColor.getRGB();

        for (int y = 0; y < result.getHeight(); y++) {
            for (int x = 0; x < result.getWidth(); x++) {
                int pixel = result.getRGB(x, y);

                if (pixel == oldRGB) {
                    result.setRGB(x, y, newRGB);
                }
            }
        }

        return result;
    }
}
