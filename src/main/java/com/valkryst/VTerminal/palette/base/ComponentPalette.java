package com.valkryst.VTerminal.palette.base;

import com.valkryst.VTerminal.misc.ColorFunctions;
import org.json.JSONObject;

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

        // Load the color.
        Color color;
        if (json.has("RGBA")) {
            final var array = json.getJSONArray("RGBA");
            final var red = array.getInt(0);
            final var green = array.getInt(1);
            final var blue = array.getInt(2);
            final var alpha = array.getInt(3);
            color = new Color(red, green, blue, alpha);
        } else if (json.has("Hex")) {
            final var hex = json.getString("Hex");
            color = Color.decode("#" + hex);
        } else {
            // todo Display an error in the console.
            color = Color.MAGENTA;
        }

        // Load the tint/shade.
        if (json.has("Tint")) {
            color = ColorFunctions.tint(color, json.getDouble("Tint"));
        }

        if (json.has("Shade")) {
            color = ColorFunctions.shade(color, json.getDouble("Shade"));
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
