package com.valkryst.VTerminal.palette.java2d;

import com.valkryst.VTerminal.palette.base.LayerPalette;
import org.json.JSONObject;

import java.awt.*;

public final class Java2DLayerPalette extends LayerPalette<Color> {
    /**
     * Constructs a Java2DLayerPalette using the JSON representation of a color
     * palette.
     *
     * @see LayerPalette#LayerPalette(JSONObject)
     *
     * @param json
     *          The JSON.
     */
    Java2DLayerPalette(final JSONObject json) {
        super(json);
    }

    @Override
    public void setBackground(final int rgba) {
        super.background = new Color(rgba, true);
    }

    @Override
    public void setForeground(final int rgba) {
        super.foreground = new Color(rgba, true);
    }
}
