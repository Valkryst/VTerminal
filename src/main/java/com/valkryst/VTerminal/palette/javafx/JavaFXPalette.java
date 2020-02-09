package com.valkryst.VTerminal.palette.javafx;

import com.valkryst.VTerminal.palette.base.Palette;
import javafx.scene.paint.Color;
import org.json.JSONObject;

import java.io.IOException;

public class JavaFXPalette extends Palette<Color> {
    /**
     * Constructs a JavaFXPalette using the default palette.
     *
     * @throws IllegalArgumentException
     *          If the default file path is empty.
     *          If the default file path does not end with ".json".
     */
    public JavaFXPalette() throws IOException {
        this(DEFAULT_PALETTE_FILE_PATH);
    }

    /**
     * Constructs a JavaFXPalette using the JSON representation of a palette loaded from a JSON file either
     * within the JAR or on the local filesystem.
     *
     * @param filePath
     *          The path of the JSON file.
     *
     * @throws IllegalArgumentException
     *          If the file path is empty.
     *          If the file path does not end with ".json".
     *
     * @throws IOException
     *          If an IO error occurs when opening, reading, or closing the palette file.
     */
    public JavaFXPalette(String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            filePath = DEFAULT_PALETTE_FILE_PATH;
        }

        final JSONObject colorPaletteJson = new JSONObject(filePath);
        super.buttonPalette = new JavaFXButtonPalette(colorPaletteJson);
        super.radioButtonPalette = new JavaFXRadioButtonPalette(colorPaletteJson);
        super.checkBoxPalette = new JavaFXCheckBoxPalette(colorPaletteJson);
        super.labelPalette = new JavaFXLabelPalette(colorPaletteJson);
        super.layerPalette = new JavaFXLayerPalette(colorPaletteJson);
        super.progressBarPalette = new JavaFXProgressBarPalette(colorPaletteJson);
        super.textAreaPalette = new JavaFXTextAreaPalette(colorPaletteJson);

        final JSONObject defaultJson = (JSONObject) colorPaletteJson.get("Default");
        if (defaultJson != null) {
            setDefaultBackground(super.getColor((JSONObject) defaultJson.get("Background")));
            setDefaultForeground(super.getColor((JSONObject) defaultJson.get("Foreground")));
        } else {

            setDefaultBackground(java.awt.Color.MAGENTA.getRGB());
            setDefaultForeground(java.awt.Color.MAGENTA.getRGB());
        }
    }

    /**
     * Converts an RGBA color value into a JavaFX Color object.
     *
     * @param rgba
     *          The RGBA color value.
     *
     * @return
     *          The JavaFX Color object.
     */
    static Color convertRGBAToJavaFXColor(final int rgba) {
        final double alpha = ((rgba >> 24) & 0xFF) / 255.0;
        final int red = (rgba >> 16) & 0xFF;
        final int green = (rgba >> 8) & 0xFF;
        final int blue = rgba & 0xFF;
        return Color.rgb(red, green, blue, alpha);
    }

    @Override
    public void setDefaultBackground(final int rgba) {
        super.defaultBackground = JavaFXPalette.convertRGBAToJavaFXColor(rgba);
    }

    @Override
    public void setDefaultForeground(final int rgba) {
        super.defaultForeground = JavaFXPalette.convertRGBAToJavaFXColor(rgba);
    }

    @Override
    public JavaFXButtonPalette getButtonPalette() {
        return (JavaFXButtonPalette) super.buttonPalette;
    }

    @Override
    public JavaFXRadioButtonPalette getRadioButtonPalette() {
        return (JavaFXRadioButtonPalette) super.radioButtonPalette;
    }

    @Override
    public JavaFXCheckBoxPalette getCheckBoxPalette() {
        return (JavaFXCheckBoxPalette) super.checkBoxPalette;
    }

    @Override
    public JavaFXLabelPalette getLabelPalette() {
        return (JavaFXLabelPalette) super.labelPalette;
    }

    @Override
    public JavaFXLayerPalette getLayerPalette() {
        return (JavaFXLayerPalette) super.layerPalette;
    }

    @Override
    public JavaFXProgressBarPalette getProgressBarPalette() {
        return (JavaFXProgressBarPalette) super.progressBarPalette;
    }

    @Override
    public JavaFXTextAreaPalette getTextAreaPalette() {
        return (JavaFXTextAreaPalette) super.textAreaPalette;
    }
}
