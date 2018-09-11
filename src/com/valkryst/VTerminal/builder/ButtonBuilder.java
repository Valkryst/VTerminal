package com.valkryst.VTerminal.builder;

import com.valkryst.VJSON.VJSONParser;
import com.valkryst.VTerminal.component.Button;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

@Data
@NoArgsConstructor
public class ButtonBuilder extends ComponentBuilder<Button> implements VJSONParser {
    /** The text to display on the button. */
    private String text;

    /** The function to run when the button is clicked. */
    private Runnable onClickFunction;

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

    @Override
    public void parse(final JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }

        super.parse(jsonObject);

        final String text = getString(jsonObject, "text");

        if (text == null) {
            throw new NullPointerException("The 'text' value was not found.");
        } else {
            this.text = text;
        }
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
