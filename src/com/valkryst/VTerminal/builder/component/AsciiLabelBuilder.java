package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.AsciiLabel;
import lombok.Getter;

import java.awt.*;

public class AsciiLabelBuilder extends ComponentBuilder<AsciiLabel, AsciiLabelBuilder> {
    /** The text to display on the label. */
    @Getter private String text;

    /** The background color for when the label. */
    @Getter private Color backgroundColor;
    /** The foreground color for when the label. */
    @Getter private Color foregroundColor;

    @Override
    public AsciiLabel build() throws IllegalStateException {
        checkState();

        final AsciiLabel label = new AsciiLabel(this);
        super.panel.addComponent(label);

        return label;
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        text = "";

        backgroundColor = new Color(0x366C9F);
        foregroundColor = new Color(0xFFCF0F);
    }

    public AsciiLabelBuilder setText(final String text) {
        if (text != null) {
            this.text = text;
        }

        return this;
    }

    public AsciiLabelBuilder setBackgroundColor(final Color backgroundColor) {
        if (backgroundColor != null) {
            this.backgroundColor = backgroundColor;
        }

        return this;
    }

    public AsciiLabelBuilder setForegroundColor(final Color foregroundColor) {
        if (foregroundColor != null) {
            this.foregroundColor = foregroundColor;
        }

        return this;
    }
}
