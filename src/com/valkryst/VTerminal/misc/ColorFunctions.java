package com.valkryst.VTerminal.misc;

import java.awt.Color;
import java.util.Objects;

public final class ColorFunctions {
    // Prevent users from creating an instance.
    private ColorFunctions() {}

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
     *
     * @throws NullPointerException
     *         If the color is null.
     */
    public static Color shade(final Color color, double shadeFactor) {
        Objects.requireNonNull(color);

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
     *
     * @throws NullPointerException
     *         If the color is null.
     */
    public static Color tint(final Color color, double tintFactor) {
        Objects.requireNonNull(color);

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
