package com.valkryst.VTerminal.revamp.component.builder;

import com.valkryst.VTerminal.revamp.component.component.Label;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class LabelBuilder extends ComponentBuilder<Label> {
    /** The text to display on the label. */
    @NonNull private String text;

    @Override
    public Label build() {
        checkState();

        super.getDimensions().setSize(text.length(), 1);

        return new Label(this);
    }

    @Override
    public void reset() {
        super.reset();

        text = "";
    }

    /**
     * Sets the text for the label to display.
     *
     * @param text
     *          The text.
     */
    public void setText(final String text) {
        this.text = text;

        if (text != null && text.isEmpty() == false) {
            super.getDimensions().setSize(text.length(), 1);
        }
    }
}