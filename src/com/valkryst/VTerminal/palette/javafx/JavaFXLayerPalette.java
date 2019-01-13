package com.valkryst.VTerminal.palette.javafx;

import com.valkryst.VTerminal.palette.base.LayerPalette;
import javafx.scene.paint.Color;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;

public final class JavaFXLayerPalette extends LayerPalette<Color> {
    /**
     * Constructs a JavaFXLayerPalette using the JSON representation of a color palette. If the given JSON
     * object is null, then the default color palette is used.
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
    JavaFXLayerPalette(final JSONObject json) throws IOException, ParseException {
        super(json);
    }

    @Override
    public void setBackground(final int rgba) {
        super.background = JavaFXPalette.convertRGBAToJavaFXColor(rgba);
    }

    @Override
    public void setForeground(final int rgba) {
        super.foreground = JavaFXPalette.convertRGBAToJavaFXColor(rgba);
    }
}
