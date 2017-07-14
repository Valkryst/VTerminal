package com.valkryst.VTerminal.misc;

import java.awt.Color;
import java.awt.Transparency;

public class ColorFunctions {
    /**
     * Ensures that a color supports transparency.
     *
     * @param color
     *         The color.
     *
     * @return
     *         Either the input color, if it supports transparency,
     *         or a new copy of the input color which supports
     *         transparency.
     */
    public static Color enforceTransparentColor(final Color color) {
        if (color == null) {
            return null;
        }

        if (color.getTransparency() != Transparency.TRANSLUCENT) {
            return new Color(color.getRGB(), true);
        }

        return color;
    }

    /**
     * Shades a color by some factor, where a higher factor results
     * in a darker shade.
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
     */
    public static Color shade(final Color color, double shadeFactor) {
        if (shadeFactor > 1.0) {
            shadeFactor = 1.0;
        }

        if (shadeFactor < 0.0) {
            shadeFactor = 0.0;
        }

        int a = color.getAlpha();
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        r *= (1 - shadeFactor);
        g *= (1 - shadeFactor);
        b *= (1 - shadeFactor);

        return new Color(r, g, b, a);
    }

    /**
     * Tints a color by some factor, where a higher factor results in
     * a lighter tint.
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
     */
    public static Color tint(final Color color, double tintFactor) {
        if (tintFactor > 1.0) {
            tintFactor = 1.0;
        }

        if (tintFactor < 0.0) {
            tintFactor = 0.0;
        }

        int a = color.getAlpha();
        int r = color.getRed();
        int g = color.getGreen();
        int b = color.getBlue();

        r += (255 - r) * tintFactor;
        g += (255 - g) * tintFactor;
        b += (255 - b) * tintFactor;

        return new Color(r, g, b, a);
    }
}
