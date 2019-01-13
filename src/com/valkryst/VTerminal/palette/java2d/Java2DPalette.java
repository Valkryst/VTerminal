package com.valkryst.VTerminal.palette.java2d;

import com.valkryst.VJSON.VJSON;
import com.valkryst.VTerminal.palette.base.Palette;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;

public final class Java2DPalette extends Palette<Color> {
    /**
     * Constructs a Java2DPalette using the default palette.
     *
     * @throws IllegalArgumentException
     *          If the default file path is empty.
     *          If the default file path does not end with ".json".
     */
    public Java2DPalette() throws IOException, ParseException {
        this(DEFAULT_PALETTE_FILE_PATH);
    }

    /**
     * Constructs a Java2DPalette using the JSON representation of a palette loaded from a JSON file either
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
     *
     * @throws ParseException
     *          If there's an error when parsing the JSON.
     */
    public Java2DPalette(String filePath) throws IOException, ParseException {
        if (filePath == null || filePath.isEmpty()) {
            filePath = DEFAULT_PALETTE_FILE_PATH;
        }

        final JSONObject colorPaletteJson = VJSON.loadJson(filePath);
        super.buttonPalette = new Java2DButtonPalette(colorPaletteJson);
        super.radioButtonPalette = new Java2DRadioButtonPalette(colorPaletteJson);
        super.checkBoxPalette = new Java2DCheckBoxPalette(colorPaletteJson);
        super.labelPalette = new Java2DLabelPalette(colorPaletteJson);
        super.layerPalette = new Java2DLayerPalette(colorPaletteJson);
        super.progressBarPalette = new Java2DProgressBarPalette(colorPaletteJson);
        super.textAreaPalette = new Java2DTextAreaPalette(colorPaletteJson);

        final JSONObject defaultJson = (JSONObject) colorPaletteJson.get("Default");
        if (defaultJson != null) {
            setDefaultBackground(super.getColor((JSONObject) defaultJson.get("Background")));
            setDefaultForeground(super.getColor((JSONObject) defaultJson.get("Foreground")));
        } else {

            setDefaultBackground(Color.MAGENTA.getRGB());
            setDefaultForeground(Color.MAGENTA.getRGB());
        }
    }

    @Override
    public void setDefaultBackground(final int rgba) {
        super.defaultBackground = new Color(rgba, true);
    }

    @Override
    public void setDefaultForeground(final int rgba) {
        super.defaultForeground = new Color(rgba, true);
    }

    @Override
    public Java2DButtonPalette getButtonPalette() {
        return (Java2DButtonPalette) super.buttonPalette;
    }

    @Override
    public Java2DRadioButtonPalette getRadioButtonPalette() {
        return (Java2DRadioButtonPalette) super.radioButtonPalette;
    }

    @Override
    public Java2DCheckBoxPalette getCheckBoxPalette() {
        return (Java2DCheckBoxPalette) super.checkBoxPalette;
    }

    @Override
    public Java2DLabelPalette getLabelPalette() {
        return (Java2DLabelPalette) super.labelPalette;
    }

    @Override
    public Java2DLayerPalette getLayerPalette() {
        return (Java2DLayerPalette) super.layerPalette;
    }

    @Override
    public Java2DProgressBarPalette getProgressBarPalette() {
        return (Java2DProgressBarPalette) super.progressBarPalette;
    }

    @Override
    public Java2DTextAreaPalette getTextAreaPalette() {
        return (Java2DTextAreaPalette) super.textAreaPalette;
    }
}
