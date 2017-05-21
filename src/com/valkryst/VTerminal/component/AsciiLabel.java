package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.builder.component.AsciiLabelBuilder;
import lombok.Getter;

import java.awt.*;

public class AsciiLabel extends AsciiComponent {
    /** The background color for when the label. */
    @Getter private Color backgroundColor;
    /** The foreground color for when the label. */
    @Getter private Color foregroundColor;

    /**
     * Constructs a new AsciiLabel.
     *
     * @param builder
     *         The builder to use.
     */
    public AsciiLabel(final AsciiLabelBuilder builder) {
        super(builder.getColumnIndex(), builder.getRowIndex(), builder.getText().length(), 1);

        this.backgroundColor = builder.getBackgroundColor();
        this.foregroundColor = builder.getForegroundColor();

        // Set the label's text:
        final char[] text = builder.getText().toCharArray();
        final AsciiString string = super.strings[0];

        for (int column = 0 ; column < text.length ; column++) {
            string.setCharacter(column, text[column]);
        }

        string.setBackgroundAndForegroundColor(backgroundColor, foregroundColor);
    }
}
