package com.valkryst.VTerminal.palette.base;

import com.valkryst.VJSON.VJSON;
import lombok.Getter;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class RadioButtonPalette<COLOR> extends ComponentPalette {
    /** Background color, for the normal state. */
    @Getter protected COLOR background;
    /** Foreground color, for the normal state. */
    @Getter protected COLOR foreground;
    /** Background color, for the hover state */
    @Getter protected COLOR backgroundHover;
    /** Foreground color, for the hover state. */
    @Getter protected COLOR foregroundHover;
    /** Background color, for the pressed state. */
    @Getter protected COLOR backgroundPressed;
    /** Foreground color, for the pressed state. */
    @Getter protected COLOR foregroundPressed;

    /**
     * Constructs a RadioButtonPalette using the JSON representation of a color palette. If the given
     * JSON object is null, then the default color palette is used.
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
    public RadioButtonPalette(JSONObject json) throws IOException, ParseException {
        if (json == null) {
            json = VJSON.loadJson("Palettes/Default.json");
        }

        final JSONObject buttonJson = (JSONObject) json.get("Radio Button");
        if (buttonJson == null) {
            setBackground(Color.MAGENTA.getRGB());
            setForeground(Color.MAGENTA.getRGB());
            setBackgroundHover(Color.MAGENTA.getRGB());
            setForegroundHover(Color.MAGENTA.getRGB());
            setBackgroundPressed(Color.MAGENTA.getRGB());
            setForegroundPressed(Color.MAGENTA.getRGB());
            return;
        }

        // Load Default Colors
        JSONObject stateJson = (JSONObject) buttonJson.get("Default");

        if (stateJson != null) {
            setBackground(super.getColor((JSONObject) stateJson.get("Background")));
            setForeground(super.getColor((JSONObject) stateJson.get("Foreground")));
        } else {
            setBackground(Color.MAGENTA.getRGB());
            setForeground(Color.MAGENTA.getRGB());
        }

        // Load Hover Colors
        stateJson = (JSONObject) buttonJson.get("Hover");

        if (stateJson != null) {
            setBackgroundHover(super.getColor((JSONObject) stateJson.get("Background")));
            setForegroundHover(super.getColor((JSONObject) stateJson.get("Foreground")));
        } else {
            setBackgroundHover(Color.MAGENTA.getRGB());
            setForegroundHover(Color.MAGENTA.getRGB());
        }

        // Load Pressed Colors
        stateJson = (JSONObject) buttonJson.get("Pressed");

        if (stateJson != null) {
            setBackgroundPressed(super.getColor((JSONObject) stateJson.get("Background")));
            setForegroundPressed(super.getColor((JSONObject) stateJson.get("Foreground")));
        } else {
            setBackgroundPressed(Color.MAGENTA.getRGB());
            setForegroundPressed(Color.MAGENTA.getRGB());
        }
    }

    /**
     * Sets the background color, for the hover state.
     *
     * @param rgba
     *          The new color.
     */
    public abstract void setBackgroundHover(final int rgba);

    /**
     * Sets the foreground color, for the hover state.
     *
     * @param rgba
     *          The new color.
     */
    public abstract void setForegroundHover(final int rgba);

    /**
     * Sets the background color, for the pressed state.
     *
     * @param rgba
     *          The new color.
     */
    public abstract void setBackgroundPressed(final int rgba);

    /**
     * Sets the foreground color, for the pressed state.
     *
     * @param rgba
     *          The new color.
     */
    public abstract void setForegroundPressed(final int rgba);
}
