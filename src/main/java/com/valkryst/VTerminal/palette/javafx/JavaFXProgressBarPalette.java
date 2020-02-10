package com.valkryst.VTerminal.palette.javafx;

import com.valkryst.VTerminal.palette.base.ProgressBarPalette;
import javafx.scene.paint.Color;
import org.json.JSONObject;

public final class JavaFXProgressBarPalette extends ProgressBarPalette<Color> {
    /**
     * Constructs a JavaFXProgressBarPalette using the JSON representation of a
     * color palette.
     *
     * @see ProgressBarPalette#ProgressBarPalette(JSONObject)
     *
     * @param json
     *          The JSON.
     */
    JavaFXProgressBarPalette(final JSONObject json) {
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
    public void setBackgroundIncomplete(final int rgba) {
        super.backgroundIncomplete = JavaFXPalette.convertRGBAToJavaFXColor(rgba);
    }

    @Override
    public void setForegroundIncomplete(final int rgba) {
        super.foregroundIncomplete = JavaFXPalette.convertRGBAToJavaFXColor(rgba);
    }
}
