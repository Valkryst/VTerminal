package com.valkryst.VTerminal.palette.java2d;

import com.valkryst.VTerminal.palette.base.LabelPalette;
import org.json.JSONObject;

import java.awt.*;

public final class Java2DLabelPalette extends LabelPalette<Color> {
    /**
     * Constructs a Java2DLabelPalette using the JSON representation of a color
     * palette.
     * 
     * @see LabelPalette#LabelPalette(JSONObject)
     *
     * @param json
     *          The JSON.
     */
    Java2DLabelPalette(final JSONObject json) {
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
