package com.valkryst.VTerminal.palette.base;

import com.valkryst.VJSON.VJSON;
import lombok.Getter;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class LabelPalette<COLOR> extends ComponentPalette {
    /** Background color. */
    @Getter protected COLOR background;
    /** Foreground color. */
    @Getter protected COLOR foreground;

    /**
     * Constructs a LabelPalette using the JSON representation of a color palette. If the given JSON
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
    public LabelPalette(JSONObject json) throws IOException, ParseException {
        if (json == null) {
            json = VJSON.loadJson("Palettes/Default.json");
        }

        final JSONObject labelJson = (JSONObject) json.get("Label");
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
