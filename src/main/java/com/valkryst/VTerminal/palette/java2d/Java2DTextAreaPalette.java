package com.valkryst.VTerminal.palette.java2d;

import com.valkryst.VTerminal.palette.base.TextAreaPalette;
import org.json.JSONObject;

import java.awt.*;

public final class Java2DTextAreaPalette extends TextAreaPalette<Color> {
    /**
     * Constructs a Java2DTextAreaPalette using the JSON representation of a color palette. If the given JSON
     * object is null, then the default color palette is used.
     *
     * @param json
     *          The JSON.
     */
    Java2DTextAreaPalette(final JSONObject json) {
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

    @Override
    public void setBackgroundCaret(final int rgba) {
        super.backgroundCaret = new Color(rgba, true);
    }

    @Override
    public void setForegroundCaret(final int rgba) {
        super.foregroundCaret = new Color(rgba, true);
    }
}
