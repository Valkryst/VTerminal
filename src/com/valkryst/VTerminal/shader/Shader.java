package com.valkryst.VTerminal.shader;

import com.valkryst.VTerminal.misc.ImageCache;
import lombok.NonNull;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public interface Shader {
    /**
     * Runs the shader on an image.
     *
     * @param image
     *          The image.
     *
     * @return
     *          The processed image.
     */
    BufferedImage run(final @NonNull BufferedImage image);

    /**
     * Creates a copy of the shader.
     *
     * @return
     *         A copy of the shader.
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

    /**
     * Guesses the back/foreground colors of a character image.
     *
     * The most common color is assumed to be the background color while the second
     * most common color is assumed to be the foreground color.
     *
     * This is not always correct.
     *
     * @param image
     *          The character image.
     *
     * @return
     *          An array containing the background and foreground colors.
     *          The background color is [0] and the foreground color is [1].
     *
     * @throws java.lang.IllegalStateException
     *          If there is only one color on the image.
     *
     *          If there is more than one color on the image.
     */
    default Color[] getBackgroundAndForegroundRGBColor(final @NonNull BufferedImage image) {
        // Count occurrences of each color:
        final HashMap<Integer, Integer> colorOccurrences = new HashMap<>();

        for (int y = 0 ; y < image.getHeight() ; y++) {
            for (int x = 0 ; x < image.getWidth() ; x++) {
                int currentColor = image.getRGB(x, y);
                int occurrences = colorOccurrences.getOrDefault(currentColor, 0);

                colorOccurrences.put(currentColor, occurrences + 1);
            }
        }

        // If there is only one color, then we're dealing with a blank AsciiCharacter.
        // In this case, the shader does nothing.
        if (colorOccurrences.size() == 1) {
            throw new IllegalStateException("Cannot retrieve a character without it's background if there are less than two colors in the image.");
        }

        // If there are more than two colors, we're not dealing with an
        // AsciiCharacter. In this case, the shader does nothing.
        if (colorOccurrences.size() > 2) {
            throw new IllegalStateException("Cannot retrieve a character without it's background if there are more than two colors in the image.");
        }

        // The most common color is probably the background color and the least
        // common color is probably the foreground color.
        // This isn't always going to be true, but for most characters it will be.
        final Integer[] keySet = colorOccurrences.keySet().toArray(new Integer[colorOccurrences.size()]);
        final Integer[] valueSet = colorOccurrences.values().toArray(new Integer[colorOccurrences.size()]);

        final Color backgroundColor;
        final Color foregroundColor;

        if (valueSet[0] > valueSet[1]) {
            backgroundColor = new Color(keySet[0]);
            foregroundColor = new Color(keySet[1]);
        } else {
            backgroundColor = new Color(keySet[1]);
            foregroundColor = new Color(keySet[0]);
        }

        return new Color[]{backgroundColor, foregroundColor};
    }
}
