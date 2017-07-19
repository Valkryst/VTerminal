package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.builder.component.LabelBuilder;
import lombok.Getter;

import java.awt.Color;
import java.util.Objects;

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
    public Label(final LabelBuilder builder) {
        super(builder.getColumnIndex(), builder.getRowIndex(), builder.getText().length(), 1);

        Objects.requireNonNull(builder);

        this.backgroundColor = builder.getBackgroundColor();
        this.foregroundColor = builder.getForegroundColor();

        // Set the label's text:
        final char[] text = builder.getText().toCharArray();
        final AsciiString string = super.getString(0);

        for (int column = 0 ; column < text.length ; column++) {
            string.setCharacter(column, text[column]);
        }

        string.setBackgroundColor(backgroundColor);
        string.setForegroundColor(foregroundColor);
    }

    @Override
    public boolean equals(final Object otherObj) {
        if (otherObj instanceof Label == false) {
            return false;
        }

        if (otherObj == this) {
            return true;
        }

        final Label otherLabel = (Label) otherObj;
        boolean isEqual = super.equals(otherObj);
        isEqual &= Objects.equals(backgroundColor, otherLabel.getBackgroundColor());
        isEqual &= Objects.equals(foregroundColor, otherLabel.getForegroundColor());
        return isEqual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), backgroundColor, foregroundColor);
    }
}
