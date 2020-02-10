package com.valkryst.VTerminal.palette.base;

import com.valkryst.VTerminal.misc.ColorFunctions;
import lombok.Getter;
import org.json.JSONObject;

import java.awt.*;
import java.util.Objects;

public abstract class Palette<COLOR> {
    /** Path to the default palette. */
    protected static final String DEFAULT_PALETTE_FILE_PATH = "src/main/resources/Palettes/Default.json";

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

        // Load the color.
        Color color;
        if (json.has("RGBA")) {
            final var array = json.getJSONArray("RGBA");
            final var red = array.getInt(0);
            final var green = array.getInt(1);
            final var blue = array.getInt(2);
            final var alpha = array.getInt(3);
            color = new Color(red, green, blue, alpha);
        } else if (json.has("Hex")) {
            final var hex = json.getString("Hex");
            color = Color.decode(hex);
        } else {
            // todo Display an error in the console.
            color = Color.MAGENTA;
        }

        // Load the tint/shade.
        if (json.has("Tint")) {
            color = ColorFunctions.tint(color, json.getDouble("Tint"));
        }

        if (json.has("Shade")) {
            color = ColorFunctions.shade(color, json.getDouble("Shade"));
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
