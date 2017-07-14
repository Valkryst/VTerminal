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
}
