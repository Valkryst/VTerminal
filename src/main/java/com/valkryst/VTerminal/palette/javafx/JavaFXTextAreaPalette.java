package com.valkryst.VTerminal.palette.javafx;

import com.valkryst.VTerminal.palette.base.TextAreaPalette;
import javafx.scene.paint.Color;
import org.json.JSONObject;

public final class JavaFXTextAreaPalette extends TextAreaPalette<Color> {
    /**
     * Constructs a JavaFXTextAreaPalette using the JSON representation of a
     * color palette.
     *
     * @see TextAreaPalette#TextAreaPalette(JSONObject)
     *
     * @param json
     *          The JSON.
     */
    JavaFXTextAreaPalette(final JSONObject json) {
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
    public void setBackgroundCaret(final int rgba) {
        super.backgroundCaret = JavaFXPalette.convertRGBAToJavaFXColor(rgba);
    }

    @Override
    public void setForegroundCaret(final int rgba) {
        super.foregroundCaret = JavaFXPalette.convertRGBAToJavaFXColor(rgba);
    }
}
