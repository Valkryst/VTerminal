package com.valkryst.AsciiPanel.component;

import com.valkryst.AsciiPanel.AsciiString;
import lombok.Getter;

import java.awt.*;

public class AsciiLabel extends AsciiComponent {
    /** The background color for when the label is in the normal state. */
    @Getter private Color backgroundColor_normal = Color.RED;
    /** The foreground color for when the label is in the normal state. */
    @Getter private Color foregroundColor_normal = Color.WHITE;

    /**
     * Constructs a new AsciiLabel.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate of the top-left character.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate of the top-left character.
     *
     * @param text
     *         The text to display on the label.
     */
    public AsciiLabel(final int columnIndex, final int rowIndex, final String text) {
        super(columnIndex, rowIndex, text.length(), 1);

        // Set the label's text:
        final AsciiString string = super.strings[0];

        for (int column = 0 ; column < text.length() ; column++) {
            string.setCharacter(column, text.charAt(column));
        }

        string.setBackgroundAndForegroundColor(backgroundColor_normal, foregroundColor_normal);
    }
}
