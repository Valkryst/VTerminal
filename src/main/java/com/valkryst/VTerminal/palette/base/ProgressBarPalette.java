package com.valkryst.VTerminal.palette.base;

import lombok.Getter;
import org.json.JSONObject;

public abstract class ProgressBarPalette<COLOR> extends ComponentPalette {
    /** Background color, for the complete state. */
    @Getter protected COLOR background;
    /** Foreground color, for the complete state. */
    @Getter protected COLOR foreground;
    /** Background color, for the incomplete state. */
    @Getter protected COLOR backgroundIncomplete;
    /** Foreground color, for the incomplete state. */
    @Getter protected COLOR foregroundIncomplete;

    /**
     * Constructs a ProgressBarPalette using the JSON representation of a color
     * palette.
     *
     * If the given JSON object is null, then the default color palette is used.
     *
     * @param json
     *          The JSON.
     */
    public ProgressBarPalette(JSONObject json) {
        if (json == null) {
            json = new JSONObject(Palette.DEFAULT_PALETTE_FILE_PATH);
        }

        json = json.getJSONObject("Progress Bar");

        var sectionJson = json.getJSONObject(json.has("Complete") ? "Complete" : "Default");
        var backgroundJson = sectionJson.getJSONObject("Background");
        var foregroundJson = sectionJson.getJSONObject("Foreground");
        setBackground(super.getColor(backgroundJson));
        setForeground(super.getColor(foregroundJson));

        sectionJson = json.getJSONObject("Incomplete");
        backgroundJson = sectionJson.getJSONObject("Background");
        foregroundJson = sectionJson.getJSONObject("Foreground");
        setBackgroundIncomplete(super.getColor(backgroundJson));
        setForegroundIncomplete(super.getColor(foregroundJson));
    }

    public abstract void setBackgroundIncomplete(final int rgba);

    public abstract void setForegroundIncomplete(final int rgba);
}
