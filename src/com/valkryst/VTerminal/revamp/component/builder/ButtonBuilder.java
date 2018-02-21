package com.valkryst.VTerminal.revamp.component.builder;

import com.valkryst.VTerminal.revamp.component.component.Button;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class ButtonBuilder extends ComponentBuilder<Button> {
    /** The text to display on the button. */
    @NonNull private String text;

    /** The function to run when the button is clicked. */
    private Runnable onClickFunction;

    @Override
    public Button build() {
        checkState();

        super.getDimensions().setSize(text.length(), 1);

        return new Button(this);
    }

    @Override
    public void reset() {
        super.reset();

        text = "";

        onClickFunction = () -> {};
    }

    /**
     * Sets the text for the button to display.
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
