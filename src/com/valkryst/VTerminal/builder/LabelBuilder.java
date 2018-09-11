package com.valkryst.VTerminal.builder;

import com.valkryst.VJSON.VJSONParser;
import com.valkryst.VTerminal.component.Label;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.json.simple.JSONObject;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class LabelBuilder extends ComponentBuilder<Label> implements VJSONParser {
    /** The text to display on the label. */
    @NonNull private String text;

    @Override
    public Label build() {
        checkState();

        super.setDimensions(text.length(), 1);

        return new Label(this);
    }

    @Override
    public void reset() {
        super.reset();

        text = "";
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
     * Sets the text for the label to display.
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