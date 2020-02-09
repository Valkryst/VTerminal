package com.valkryst.VTerminal.palette.javafx;

import com.valkryst.VTerminal.palette.base.RadioButtonPalette;
import javafx.scene.paint.Color;
import org.json.JSONObject;

public final class JavaFXRadioButtonPalette extends RadioButtonPalette<Color> {
    /**
     * Constructs a JavaFXRadioButtonPalette using the JSON representation of a color palette. If the given
     * JSON object is null, then the default color palette is used.
     *
     * @param json
     *          The JSON.
     */
    JavaFXRadioButtonPalette(final JSONObject json) {
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
