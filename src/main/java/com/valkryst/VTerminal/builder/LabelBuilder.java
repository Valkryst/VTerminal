package com.valkryst.VTerminal.builder;

import com.valkryst.VTerminal.component.Label;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.json.JSONObject;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class LabelBuilder extends ComponentBuilder<Label> {
    /** The text to display on the label. */
    @NonNull private String text;

    /**
     * Constructs a new LabelBuilder and initializes it using the JSON representation of a label component.
     *
     * @param json
     *          The JSON representation of a label component.
     */
    public LabelBuilder(final JSONObject json) {
        super(json);

        if (json == null) {
            return;
        }

        final String text = json.getString("Text");
        this.text = (text == null ? "" : text);
    }

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