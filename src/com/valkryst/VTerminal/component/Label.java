package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.builder.component.LabelBuilder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.awt.Color;

@ToString
public class Label extends Component {
    /** The background color for when the label. */
    @Getter private Color backgroundColor;
    /** The foreground color for when the label. */
    @Getter private Color foregroundColor;

    /**
     * Constructs a new AsciiLabel.
     *
     * @param builder
     *         The builder to use.
     *
     * @throws NullPointerException
     *         If the builder is null.
     */
    public Label(final @NonNull LabelBuilder builder) {
        super(builder.getColumnIndex(), builder.getRowIndex(), builder.getText().length(), 1);

        this.backgroundColor = builder.getBackgroundColor();
        this.foregroundColor = builder.getForegroundColor();

        // Set the label's text:
        final char[] text = builder.getText().toCharArray();
        final AsciiString string = super.getString(0);

        for (int column = 0 ; column < text.length ; column++) {
            string.setCharacter(column, text[column]);
        }

        if (builder.isUnderlined()) {
            string.setUnderlined(true);
        }

        string.setBackgroundColor(backgroundColor);
        string.setForegroundColor(foregroundColor);
    }
}
