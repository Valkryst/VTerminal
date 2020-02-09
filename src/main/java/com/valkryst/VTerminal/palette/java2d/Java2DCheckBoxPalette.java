package com.valkryst.VTerminal.palette.java2d;

import com.valkryst.VTerminal.palette.base.CheckBoxPalette;
import org.json.JSONObject;

import java.awt.*;

public final class Java2DCheckBoxPalette extends CheckBoxPalette<Color> {
    /**
     * Constructs a Java2DCheckBoxPalette using the JSON representation of a color palette. If the given JSON
     * object is null, then the default color palette is used.
     *
     * @param json
     *          The JSON.
     */
    Java2DCheckBoxPalette(final JSONObject json) {
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
    public void setBackgroundHover(final int rgba) {
        super.backgroundHover = new Color(rgba, true);
    }

    @Override
    public void setForegroundHover(final int rgba) {
        super.foregroundHover = new Color(rgba, true);
    }

    @Override
    public void setBackgroundPressed(final int rgba) {
        super.backgroundPressed = new Color(rgba, true);
    }

    @Override
    public void setForegroundPressed(final int rgba) {
        super.foregroundPressed = new Color(rgba, true);
    }
}
