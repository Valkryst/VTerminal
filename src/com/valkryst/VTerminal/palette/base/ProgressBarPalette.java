package com.valkryst.VTerminal.palette.base;

import com.valkryst.VJSON.VJSON;
import lombok.Getter;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

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
     *
     * @throws FileNotFoundException
     *          If the default color palette file cannot be found within the Jar or on the filesystem.
     *
     * @throws IOException
     *          If an IO error occurs when opening, reading, or closing the default color palette file.
     *
     * @throws ParseException
     *          If there's an error when parsing the JSON.
     */
    public ProgressBarPalette(JSONObject json) throws IOException, ParseException {
        if (json == null) {
            json = VJSON.loadJson("Palettes/Default.json");
        }

        final JSONObject buttonJson = (JSONObject) json.get("Progress Bar");
        if (buttonJson == null) {
            setBackground(Color.MAGENTA.getRGB());
            setForeground(Color.MAGENTA.getRGB());
            setBackgroundIncomplete(Color.MAGENTA.getRGB());
            setForegroundIncomplete(Color.MAGENTA.getRGB());
            return;
        }

        // Load Normal Colors
        JSONObject stateJson = (JSONObject) buttonJson.get("Default");

        if (stateJson != null) {
            setBackground(super.getColor((JSONObject) stateJson.get("Background")));
            setForeground(super.getColor((JSONObject) stateJson.get("Foreground")));
        } else {
            stateJson = (JSONObject) buttonJson.get("Complete");

            if (stateJson != null) {
                setBackground(super.getColor((JSONObject) stateJson.get("Background")));
                setForeground(super.getColor((JSONObject) stateJson.get("Foreground")));
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
