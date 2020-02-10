package com.valkryst.VTerminal.palette.base;

import lombok.Getter;
import org.json.JSONObject;

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
     */
    public RadioButtonPalette(JSONObject json) {
        if (json == null) {
            json = new JSONObject(Palette.DEFAULT_PALETTE_FILE_PATH);
        }

        json = json.getJSONObject("Radio Button");

        var sectionJson = json.getJSONObject("Default");
        var backgroundJson = sectionJson.getJSONObject("Background");
        var foregroundJson = sectionJson.getJSONObject("Foreground");
        setBackground(super.getColor(backgroundJson));
        setForeground(super.getColor(foregroundJson));

        sectionJson = json.getJSONObject("Hover");
        backgroundJson = sectionJson.getJSONObject("Background");
        foregroundJson = sectionJson.getJSONObject("Foreground");
        setBackgroundHover(super.getColor(backgroundJson));
        setForegroundHover(super.getColor(foregroundJson));

        sectionJson = json.getJSONObject("Pressed");
        backgroundJson = sectionJson.getJSONObject("Background");
        foregroundJson = sectionJson.getJSONObject("Foreground");
        setBackgroundPressed(super.getColor(backgroundJson));
        setForegroundPressed(super.getColor(foregroundJson));
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
