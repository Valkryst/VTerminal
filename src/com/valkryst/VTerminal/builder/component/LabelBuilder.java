package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.Label;
import lombok.Getter;

import java.awt.Color;

public class LabelBuilder extends ComponentBuilder<Label, LabelBuilder> {
    /** The text to display on the label. */
    @Getter private String text;

    /** The background color for when the label. */
    @Getter private Color backgroundColor;
    /** The foreground color for when the label. */
    @Getter private Color foregroundColor;

    @Override
    public Label build() {
        checkState();

        final Label label = new Label(this);
        super.panel.addComponent(label);

        return label;
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        text = "";

        backgroundColor = new Color(0xFF366C9F, true);
        foregroundColor = new Color(0xFFFFCF0F, true);
    }

    /**
     * Sets the text.
     *
     * @param text
     *        The new text.
     *
     * @return
     *        This.
     */
    public LabelBuilder setText(final String text) {
        if (text != null) {
            this.text = text;
        }

        return this;
    }

    /**
     * Sets the background color.
     *
     * @param backgroundColor
     *        The new background color.
     *
     * @return
     *        This.
     */
    public LabelBuilder setBackgroundColor(final Color backgroundColor) {
        if (backgroundColor != null) {
            this.backgroundColor = backgroundColor;
        }

        return this;
    }

    /**
     * Sets the foreground color.
     *
     * @param foregroundColor
     *        The new foreground color.
     *
     * @return
     *        This.
     */
    public LabelBuilder setForegroundColor(final Color foregroundColor) {
        if (foregroundColor != null) {
            this.foregroundColor = foregroundColor;
        }

        return this;
    }
}
