package com.valkryst.VTerminal.palette.base;

import lombok.Getter;
import org.json.JSONObject;

import java.awt.*;

public abstract class LayerPalette<COLOR> extends ComponentPalette {
    /** Background color. */
    @Getter protected COLOR background;
    /** Foreground color. */
    @Getter protected COLOR foreground;

    /**
     * Constructs a LayerPalette using the JSON representation of a color palette. If the given JSON
     * object is null, then the default color palette is used.
     *
     * @param json
     *          The JSON.
     */
    public LayerPalette(JSONObject json) {
        if (json == null) {
            json = new JSONObject("Palettes/Default.json");
        }

        final JSONObject labelJson = (JSONObject) json.get("Layer");
        if (labelJson == null) {
            setBackground(Color.MAGENTA.getRGB());
            setForeground(Color.MAGENTA.getRGB());
            return;
        }

        final JSONObject stateJson = (JSONObject) labelJson.get("Default");
        if (stateJson == null) {
            setBackground(Color.MAGENTA.getRGB());
            setForeground(Color.MAGENTA.getRGB());
            return;
        }

        setBackground(super.getColor((JSONObject) stateJson.get("Background")));
        setForeground(super.getColor((JSONObject) stateJson.get("Foreground")));
    }
}
