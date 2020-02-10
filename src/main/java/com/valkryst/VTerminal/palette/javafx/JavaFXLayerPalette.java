package com.valkryst.VTerminal.palette.javafx;

import com.valkryst.VTerminal.palette.base.LayerPalette;
import javafx.scene.paint.Color;
import org.json.JSONObject;

public final class JavaFXLayerPalette extends LayerPalette<Color> {
    /**
     * Constructs a JavaFXLayerPalette using the JSON representation of a color
     * palette.
     * 
     * @see LayerPalette#LayerPalette(JSONObject)
     *
     * @param json
     *          The JSON.
     */
    JavaFXLayerPalette(final JSONObject json) {
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
