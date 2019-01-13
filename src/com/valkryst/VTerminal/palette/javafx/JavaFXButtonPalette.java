package com.valkryst.VTerminal.palette.javafx;

import com.valkryst.VTerminal.palette.base.ButtonPalette;
import javafx.scene.paint.Color;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;

public class JavaFXButtonPalette extends ButtonPalette<Color> {
    /**
     * Constructs a JavaFXButtonPalette using the JSON representation of a color palette. If the given JSON
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
    JavaFXButtonPalette(final JSONObject json) throws IOException, ParseException {
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

    @Override
    public void setBackgroundHover(final int rgba) {
        super.backgroundHover = JavaFXPalette.convertRGBAToJavaFXColor(rgba);
    }

    @Override
    public void setForegroundHover(final int rgba) {
        super.foregroundHover = JavaFXPalette.convertRGBAToJavaFXColor(rgba);
    }

    @Override
    public void setBackgroundPressed(final int rgba) {
        super.backgroundPressed = JavaFXPalette.convertRGBAToJavaFXColor(rgba);
    }

    @Override
    public void setForegroundPressed(final int rgba) {
        super.foregroundPressed = JavaFXPalette.convertRGBAToJavaFXColor(rgba);
    }
}
