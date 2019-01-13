package com.valkryst.VTerminal.palette.base;

import com.valkryst.VJSON.VJSON;
import com.valkryst.VTerminal.misc.ColorFunctions;
import lombok.Getter;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.Objects;

public abstract class Palette<COLOR> {
    /** Path to the default palette. */
    protected static final String DEFAULT_PALETTE_FILE_PATH = "Palettes/Default.json";

    /** The default background color. */
    @Getter protected COLOR defaultBackground;
    /** The default foreground color. */
    @Getter protected COLOR defaultForeground;

    /** Palette for button components. */
    @Getter protected ButtonPalette buttonPalette;
    /** Palette for radio button components. */
    @Getter protected RadioButtonPalette radioButtonPalette;
    /** Palette for check box components. */
    @Getter protected CheckBoxPalette checkBoxPalette;
    /** Palette for label components. */
    @Getter protected LabelPalette labelPalette;
    /** Palette for layer components. */
    @Getter protected LayerPalette layerPalette;
    /** Palette for progress bar components. */
    @Getter protected ProgressBarPalette progressBarPalette;
    /** Palette for text area components. */
    @Getter protected TextAreaPalette textAreaPalette;

    /**
     * Retrieves a color value from a JSON object.
     *
     * @param json
     *          The JSON object.
     *
     * @return
     *          The color.
     */
    protected int getColor(final JSONObject json) {
        if (json == null) {
            return Color.MAGENTA.getRGB();
        }

        // Load either an RGBA or Hex color:
        Color color = VJSON.getRGBAColor(json, "RGBA");

        if (color == null) {
            color = VJSON.getHexColor(json, "Hex");
        }

        // Apply Tint/Shade:
        final Double tint = VJSON.getDouble(json, "Tint");
        final Double shade = VJSON.getDouble(json, "Shade");

        if (tint != null) {
            color = ColorFunctions.tint(color, tint);
        }

        if (shade != null) {
            color = ColorFunctions.shade(color, shade);
        }

        return color.getRGB();
    }

    /** Checks if each palette has been loaded. */
    public void validate() {
        Objects.requireNonNull(buttonPalette);
        Objects.requireNonNull(radioButtonPalette);
        Objects.requireNonNull(checkBoxPalette);
        Objects.requireNonNull(labelPalette);
        Objects.requireNonNull(layerPalette);
        Objects.requireNonNull(progressBarPalette);
        Objects.requireNonNull(textAreaPalette);
    }

    /**
     * Sets the default background color.
     *
     * @param rgba
     *          The new color.
     */
    public abstract void setDefaultBackground(final int rgba);

    /**
     * Sets the default foreground color.
     *
     * @param rgba
     *          The new color.
     */
    public abstract void setDefaultForeground(final int rgba);
}
