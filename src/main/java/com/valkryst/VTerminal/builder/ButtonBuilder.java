package com.valkryst.VTerminal.builder;

import com.valkryst.VTerminal.component.Button;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class ButtonBuilder extends ComponentBuilder<Button> {
    /** The text to display on the button. */
    private String text;

    /** The function to run when the button is clicked. */
    private Runnable onClickFunction;

    /**
     * Constructs a new ButtonBuilder and initializes it using the JSON representation of a button component.
     *
     * @param json
     *          The JSON representation of a button component.
     */
    public ButtonBuilder(final JSONObject json) {
        super(json);

        if (json == null) {
            return;
        }

        final String text = json.getString("Text");
        this.text = (text == null ? "" : text);
    }

    @Override
    public Button build() {
        checkState();

        super.setDimensions(text.length(), 1);

        return new Button(this);
    }

    @Override
    protected void checkState() {
        super.checkState();

        if (text == null) {
            text = "";
        }

        if (onClickFunction == null) {
            onClickFunction = () -> {};
        }
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
            super.setWidth(text.length());
        }
    }
}
