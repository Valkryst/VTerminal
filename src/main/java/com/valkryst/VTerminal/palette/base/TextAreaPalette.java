package com.valkryst.VTerminal.palette.base;

import lombok.Getter;
import org.json.JSONObject;

public abstract class TextAreaPalette<COLOR> extends ComponentPalette {
    /** Background color of the text. */
    @Getter protected COLOR background;
    /** Foreground color of the text. */
    @Getter protected COLOR foreground;
    /** Background color of the caret. */
    @Getter protected COLOR backgroundCaret;
    /** Foreground color of the caret. */
    @Getter protected COLOR foregroundCaret;

    /**
     * Constructs a TextAreaPalette using the JSON representation of a color palette. If the given JSON
     * object is null, then the default color palette is used.
     *
     * @param json
     *          The JSON.
     */
    public TextAreaPalette(JSONObject json) {
        if (json == null) {
            json = new JSONObject(Palette.DEFAULT_PALETTE_FILE_PATH);
        }

        json = json.getJSONObject("Text Area");

        var sectionJson = json.getJSONObject("Default");
        var backgroundJson = sectionJson.getJSONObject("Background");
        var foregroundJson = sectionJson.getJSONObject("Foreground");
        setBackground(super.getColor(backgroundJson));
        setForeground(super.getColor(foregroundJson));

        sectionJson = json.getJSONObject("Caret");
        backgroundJson = sectionJson.getJSONObject("Background");
        foregroundJson = sectionJson.getJSONObject("Foreground");
        setBackgroundCaret(super.getColor(backgroundJson));
        setForegroundCaret(super.getColor(foregroundJson));
    }

    /**
     * Sets the background color of the caret.
     *
     * @param rgba
     *          The new color.
     */
    public abstract void setBackgroundCaret(final int rgba);

    /**
     * Sets the foreground color of the caret.
     *
     * @param rgba
     *          The new color.
     */
    public abstract void setForegroundCaret(final int rgba);
}
