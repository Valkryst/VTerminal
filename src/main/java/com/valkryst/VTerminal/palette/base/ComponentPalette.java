package com.valkryst.VTerminal.palette.base;

import com.valkryst.VTerminal.misc.ColorFunctions;
import org.json.JSONArray;
import org.json.JSONException;
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

        // Load either an RGBA or Hex color:
        Color color;
        try {
            color = readRGBAColor(json.getJSONArray("RGBA"));
        } catch (final JSONException e) {
            color = Color.decode("#" + json.getString("Hex"));
        }

        // Apply Tint/Shade:
        try {
            final Double tint = json.getDouble("Tint");
            color = ColorFunctions.tint(color, tint);
        } catch (final JSONException e) {}

        try {
            final Double shade = json.getDouble("Shade");
            color = ColorFunctions.shade(color, shade);
        } catch (final JSONException e) {}

        return color.getRGB();
    }

    private Color readRGBAColor(final JSONArray json) {
        return new Color(json.getInt(0), json.getInt(1), json.getInt(2), json.getInt(3));
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
