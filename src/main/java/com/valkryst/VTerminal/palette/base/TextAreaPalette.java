package com.valkryst.VTerminal.palette.base;

import lombok.Getter;
import org.json.JSONObject;

import java.awt.*;

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
            json = new JSONObject("Palettes/Default.json");
        }

        final JSONObject labelJson = (JSONObject) json.get("Text Area");
        if (labelJson == null) {
            setBackground(Color.MAGENTA.getRGB());
            setForeground(Color.MAGENTA.getRGB());
            setBackgroundCaret(Color.MAGENTA.getRGB());
            setForegroundCaret(Color.MAGENTA.getRGB());
            return;
        }

        // Load Default Colors
        JSONObject stateJson = (JSONObject) labelJson.get("Default");

        if (stateJson != null) {
            setBackground(super.getColor((JSONObject) stateJson.get("Background")));
            setForeground(super.getColor((JSONObject) stateJson.get("Foreground")));
        } else {
            setBackground(Color.MAGENTA.getRGB());
            setForeground(Color.MAGENTA.getRGB());
            return;
        }

        // Load Caret Colors
        stateJson = (JSONObject) labelJson.get("Caret");

        if (stateJson != null) {
            setBackgroundCaret(super.getColor((JSONObject) stateJson.get("Background")));
            setForegroundCaret(super.getColor((JSONObject) stateJson.get("Foreground")));
        } else {
            setBackgroundCaret(Color.MAGENTA.getRGB());
            setForegroundCaret(Color.MAGENTA.getRGB());
        }
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
