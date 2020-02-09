package com.valkryst.VTerminal.palette.base;

import lombok.Getter;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

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
     * Constructs a ProgressBarPalette using the JSON representation of a color palette. If the given JSON
     * object is null, then the default color palette is used.
     *
     * @param json
     *          The JSON.
     */
    public ProgressBarPalette(JSONObject json) {
        if (json == null) {
            json = new JSONObject("Palettes/Default.json");
        }

        final JSONObject buttonJson = json.getJSONObject("Progress Bar");
        if (buttonJson == null) {
            setBackground(Color.MAGENTA.getRGB());
            setForeground(Color.MAGENTA.getRGB());
            setBackgroundIncomplete(Color.MAGENTA.getRGB());
            setForegroundIncomplete(Color.MAGENTA.getRGB());
            return;
        }

        // Load Normal Colors
        JSONObject stateJson;

        try {
            stateJson = buttonJson.getJSONObject("Default");
            setBackground(super.getColor(stateJson.getJSONObject("Background")));
            setForeground(super.getColor(stateJson.getJSONObject("Foreground")));
        } catch (final JSONException e) {
            stateJson = buttonJson.getJSONObject("Complete");

            if (stateJson != null) {
                setBackground(super.getColor(stateJson.getJSONObject("Background")));
                setForeground(super.getColor(stateJson.getJSONObject("Foreground")));
            } else {
                setBackground(Color.MAGENTA.getRGB());
                setForeground(Color.MAGENTA.getRGB());
            }
        }

        // Load Incomplete Colors
        stateJson = (JSONObject) buttonJson.get("Incomplete");

        if (stateJson != null) {
            setBackgroundIncomplete(super.getColor((JSONObject) stateJson.get("Background")));
            setForegroundIncomplete(super.getColor((JSONObject) stateJson.get("Foreground")));
        } else {
            setBackgroundIncomplete(Color.MAGENTA.getRGB());
            setForegroundIncomplete(Color.MAGENTA.getRGB());
        }
    }

    public abstract void setBackgroundIncomplete(final int rgba);

    public abstract void setForegroundIncomplete(final int rgba);
}
