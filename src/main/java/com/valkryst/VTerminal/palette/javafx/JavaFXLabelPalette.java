package com.valkryst.VTerminal.palette.javafx;

import com.valkryst.VTerminal.palette.base.LabelPalette;
import javafx.scene.paint.Color;
import org.json.JSONObject;

public final class JavaFXLabelPalette extends LabelPalette<Color> {
    /**
     * Constructs a JavaFXLabelPalette using the JSON representation of a color palette. If the given JSON
     * object is null, then the default color palette is used.
     *
     * @param json
     *          The JSON.
     */
    JavaFXLabelPalette(final JSONObject json) {
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
