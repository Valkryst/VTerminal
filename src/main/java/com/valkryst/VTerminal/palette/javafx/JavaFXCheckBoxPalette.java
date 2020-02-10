package com.valkryst.VTerminal.palette.javafx;

import com.valkryst.VTerminal.palette.base.CheckBoxPalette;
import javafx.scene.paint.Color;
import org.json.JSONObject;

public class JavaFXCheckBoxPalette extends CheckBoxPalette<Color> {
    /**
     * Constructs a JavaFXCheckBoxPalette using the JSON representation of a
     * color palette.
     *
     * @see CheckBoxPalette#CheckBoxPalette(JSONObject)
     *
     * @param json
     *          The JSON.
     */
    JavaFXCheckBoxPalette(final JSONObject json) {
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
