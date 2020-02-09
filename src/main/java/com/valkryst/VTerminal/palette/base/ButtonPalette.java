package com.valkryst.VTerminal.palette.base;

import lombok.Getter;
import org.json.JSONObject;

import java.awt.*;
import java.io.FileNotFoundException;

public abstract class ButtonPalette<COLOR> extends ComponentPalette {
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
     * Constructs a ButtonPalette using the JSON representation of a color palette. If the given JSON
     * object is null, then the default color palette is used.
     *
     * @param json
     *          The JSON.
     *
     * @throws FileNotFoundException
     *          If the default color palette file cannot be found within the Jar or on the filesystem.
     */
    public ButtonPalette(JSONObject json) {
        if (json == null) {
            json = new JSONObject("Palettes/Default.json");
        }

        final JSONObject buttonJson = (JSONObject) json.get("Button");
        if (buttonJson == null) {
            setBackground(Color.MAGENTA.getRGB());
            setForeground(Color.MAGENTA.getRGB());
            setBackgroundHover(Color.MAGENTA.getRGB());
            setForegroundHover(Color.MAGENTA.getRGB());
            setBackgroundPressed(Color.MAGENTA.getRGB());
            setForegroundPressed(Color.MAGENTA.getRGB());
            return;
        }

        // Load Normal Colors
        JSONObject stateJson = (JSONObject) buttonJson.get("Default");

        if (stateJson != null) {
            setBackground(super.getColor(stateJson.getJSONObject("Background")));
            setForeground(super.getColor(stateJson.getJSONObject("Foreground")));
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
