package com.valkryst.VTerminal.misc;

import lombok.NonNull;

import java.awt.Color;

public final class ColorFunctions {
    // Prevent users from creating an instance.
    private ColorFunctions() {}

    /**
     * Shades a color by some factor, where a higher factor results in a darker
     * shade.
     *
     * @param color
     *        The color.
     *
     * @param shadeFactor
     *        The factor.
     *
     *        Values should range from 0.0 to 1.0.
     *
     * @return
     *        The shaded color.
     *
     * @throws NullPointerException
     *         If the color is null.
     */
    public static Color shade(final @NonNull Color color, double shadeFactor) {
        if (shadeFactor > 1.0) {
            shadeFactor = 1.0;
        }

        if (shadeFactor < 0.0) {
            shadeFactor = 0.0;
        }

        final int a = color.getAlpha();
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        r *= (1 - shadeFactor);
        g *= (1 - shadeFactor);
        b *= (1 - shadeFactor);

        return new Color(r, g, b, a);
    }

    /**
     * Tints a color by some factor, where a higher factor results in a lighter
     * tint.
     *
     * @param color
     *        the color.
     *
     * @param tintFactor
     *        The factor.
     *
     *        Values should range from 0.0 to 1.0.
     *
     * @return
     *        The tinted color.
     *
     * @throws NullPointerException
     *         If the color is null.
     */
    public static Color tint(final @NonNull Color color, double tintFactor) {
        if (tintFactor > 1.0) {
            tintFactor = 1.0;
        }

        if (tintFactor < 0.0) {
            tintFactor = 0.0;
        }

        final int a = color.getAlpha();
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        r += (255 - r) * tintFactor;
        g += (255 - g) * tintFactor;
        b += (255 - b) * tintFactor;

        return new Color(r, g, b, a);
    }

    /**
     * Blends two colors using the alpha blend algorithm.
     *
     * @param source
     *         The source color being blended onto the destination color.
     *
     * @param destination
     *         The destination color.
     *
     * @return
     *         The blended color.
     *
     * @throws NullPointerException
     *         If either color is null.
     */
    public static Color alphaBlend(final @NonNull Color source, final @NonNull Color destination) {
        return new Color(alphaBlend(source.getRGB(), destination.getRGB()));
    }

    /**
     * Blends two RGBA values using the alpha blend algorithm.
     *
     * @param sourceRGBA
     *         The source RGBA being blended onto the destination RGBA.
     *
     * @param destinationRGBA
     *         The destination RGBA.
     *
     * @return
     *         The blended RGBA value.
     */
    public static int alphaBlend(final int sourceRGBA, final int destinationRGBA) {
        if (sourceRGBA < 0) {
            throw new IllegalArgumentException("The source color value cannot be negative.");
        }

        final int destinationA = (destinationRGBA >> 24) & 0xFF;
        final int destinationR = (destinationRGBA >> 16) & 0xFF;
        final int destinationG = (destinationRGBA >> 8) & 0xFF;
        final int destinationB = destinationRGBA & 0xFF;

        final int sourceA = (sourceRGBA >> 24) & 0xFF;
        final int sourceR = (sourceRGBA >> 16) & 0xFF;
        final int sourceG = (sourceRGBA >> 8) & 0xFF;
        final int sourceB = sourceRGBA & 0xFF;

        int alphaBlend = destinationA * (255 - sourceA) + sourceA;
        int redBlend = destinationR * (255 - sourceA) + (sourceR * sourceA);
        int greenBlend = destinationG * (255 - sourceA) + (sourceG * sourceA);
        int blueBlend = destinationB * (255 - sourceA) + (sourceB * sourceA);

        alphaBlend &= 0xFF;
        redBlend &= 0xFF;
        greenBlend &= 0xFF;
        blueBlend &= 0xFF;

        return (alphaBlend << 24) + (redBlend << 16) + (greenBlend << 8) + blueBlend;
    }
}
