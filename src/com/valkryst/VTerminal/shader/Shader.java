package com.valkryst.VTerminal.shader;

import com.valkryst.VTerminal.misc.ImageCache;
import lombok.NonNull;

import java.awt.Color;
import java.awt.image.BufferedImage;

public interface Shader {
    /**
     * Runs the shader on an image.
     *
     * @param image
     *          The image.
     *
     * @return
     *          The processed image.
     *
     * @throws NullPointerException
     *           If the image is null.
     */
    BufferedImage run(final @NonNull BufferedImage image);

    /**
     * Returns a copy of the shader.
     *
     * @return
     *          A copy of the shader.
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
     *           If the image is null.
     */
    default BufferedImage swapColor(final @NonNull BufferedImage image, final Color oldColor, final Color newColor) {
        final BufferedImage result = ImageCache.cloneImage(image);

        if (oldColor == null || newColor == null) {
            return result;
        }

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
