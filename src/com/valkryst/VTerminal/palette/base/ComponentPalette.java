package com.valkryst.VTerminal.palette.base;

import com.valkryst.VJSON.VJSON;
import com.valkryst.VTerminal.misc.ColorFunctions;
import org.json.simple.JSONObject;

import java.awt.*;

public abstract class ComponentPalette {
    /**
     * Retrieves a color value from a JSON object.
     *
     * @param json
     *          The JSON object.
     *
     * @return
     *          The color.
     */
    int getColor(final JSONObject json) {
        if (json == null) {
            return Color.MAGENTA.getRGB();
        }

        // Load either an RGBA or Hex color:
        Color color = VJSON.getRGBAColor(json, "RGBA");

        if (color == null) {
            color = VJSON.getHexColor(json, "Hex");
        }

        // Apply Tint/Shade:
        final Double tint = VJSON.getDouble(json, "Tint");
        final Double shade = VJSON.getDouble(json, "Shade");

        if (tint != null) {
            color = ColorFunctions.tint(color, tint);
        }

        if (shade != null) {
            color = ColorFunctions.shade(color, shade);
        }

        return color.getRGB();
    }

    /**
     * Sets the background color.
     *
     * @param rgba
     *          The new color.
     */
    public abstract void setBackground(final int rgba);

    /**
     * Sets the foreground color
     *
     * @param rgba
     *          The new color.
     */
    public abstract void setForeground(final int rgba);
}
