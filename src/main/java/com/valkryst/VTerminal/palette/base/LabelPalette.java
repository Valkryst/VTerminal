package com.valkryst.VTerminal.palette.base;

import lombok.Getter;
import org.json.JSONObject;

public abstract class LabelPalette<COLOR> extends ComponentPalette {
    /** Background color. */
    @Getter protected COLOR background;
    /** Foreground color. */
    @Getter protected COLOR foreground;

    /**
     * Constructs a LabelPalette using the JSON representation of a color
     * palette.
     *
     * If the given JSON object is null, then the default color palette is used.
     *
     * @param json
     *          The JSON.
     */
    public LabelPalette(JSONObject json) {
        if (json == null) {
            json = new JSONObject(Palette.DEFAULT_PALETTE_FILE_PATH);
        }

        json = json.getJSONObject("Label");

        var sectionJson = json.getJSONObject("Default");
        var backgroundJson = sectionJson.getJSONObject("Background");
        var foregroundJson = sectionJson.getJSONObject("Foreground");
        setBackground(super.getColor(backgroundJson));
        setForeground(super.getColor(foregroundJson));
    }
}
