package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.Label;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.awt.Color;

public class LabelBuilder extends ComponentBuilder<Label> {
    /** The text to display on the label. */
    @Getter @Setter @NonNull private String text;

    /** The background color for when the label. */
    @Getter @Setter @NonNull private Color backgroundColor;
    /** The foreground color for when the label. */
    @Getter @Setter @NonNull private Color foregroundColor;

    @Override
    public Label build() {
        checkState();

        final Label label = new Label(this);
        super.getPanel().addComponent(label);

        return label;
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        text = "";

        backgroundColor = new Color(0xFF366C9F, true);
        foregroundColor = new Color(0xFFFFCF0F, true);
    }
}
