package com.valkryst.VTerminal.palette.java2d;

import com.valkryst.VTerminal.palette.base.Palette;
import org.json.JSONObject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public final class Java2DPalette extends Palette<Color> {
    /**
     * Constructs a Java2DPalette using the default palette.
     *
     * @throws IllegalArgumentException
     *          If the default file path is empty.
     *          If the default file path does not end with ".json".
     */
    public Java2DPalette() throws IOException {
        this(DEFAULT_PALETTE_FILE_PATH);
    }

    /**
     * Constructs a Java2DPalette using the JSON representation of a palette
     * loaded from a JSON file either within the JAR.
     *
     * @param filePath
     *          The path of the JSON file.
     *
     * @throws IllegalArgumentException
     *          If the file path is empty.
     *          If the file path does not end with ".json".
     */
	public Java2DPalette(String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            filePath = DEFAULT_PALETTE_FILE_PATH;
        }

        final var file = Java2DPalette.class.getClassLoader().getResource(filePath).getFile();
        final var sb = new StringBuilder();

        try (final var br = new BufferedReader(new FileReader(file))) {
			String line;
        	while ((line = br.readLine()) != null) {
        		sb.append(line);
			}
		}

        final JSONObject colorPaletteJson = new JSONObject(sb.toString());
        super.buttonPalette = new Java2DButtonPalette(colorPaletteJson);
        super.radioButtonPalette = new Java2DRadioButtonPalette(colorPaletteJson);
        super.checkBoxPalette = new Java2DCheckBoxPalette(colorPaletteJson);
        super.labelPalette = new Java2DLabelPalette(colorPaletteJson);
        super.layerPalette = new Java2DLayerPalette(colorPaletteJson);
        super.progressBarPalette = new Java2DProgressBarPalette(colorPaletteJson);
        super.textAreaPalette = new Java2DTextAreaPalette(colorPaletteJson);

        final JSONObject defaultJson = colorPaletteJson.getJSONObject("Default");
        setDefaultBackground(super.getColor(defaultJson.getJSONObject("Background")));
        setDefaultForeground(super.getColor(defaultJson.getJSONObject("Foreground")));
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
