package com.valkryst.VTerminal.builder;

import com.valkryst.VJSON.VJSONParser;
import com.valkryst.VTerminal.component.RadioButton;
import com.valkryst.VTerminal.component.RadioButtonGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.json.simple.JSONObject;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class RadioButtonBuilder extends ButtonBuilder implements VJSONParser {
    /** The text of the label to display to the right of the radio button. */
    @NonNull private String text;

    /** The character to display when the radio button is not checked. */
    private char emptyButtonChar;
    /** The character to display when the radio button is checked. */
    private char checkedButtonChar;

    /** The radio button group that the radio button will belong to. */
    @NonNull private RadioButtonGroup group;

    @Override
    public RadioButton build() {
        checkState();

        super.setDimensions(text.length() + 2, 1);

        text = emptyButtonChar + " " + text;

        final RadioButton radioButton = new RadioButton(this);
        group.addRadioButton(radioButton);

        return radioButton;
    }

    /**
     * Checks the current state of the builder.
     *
     * @throws NullPointerException
     *          If the radio or group are null.
     */
    protected void checkState() throws NullPointerException {
        super.checkState();

        if (group == null) {
            throw new NullPointerException("The button must belong to a radio button group.");
        }
    }

    @Override
    public void parse(final @NonNull JSONObject jsonObject) {
        super.parse(jsonObject);

        final String text = getString(jsonObject, "text");
        final Character emptyButtonChar = getChar(jsonObject, "emptyButtonChar");
        final Character checkedButtonChar = getChar(jsonObject, "checkedButtonChar");
        // todo Add isChecked

        if (text == null) {
            throw new NullPointerException("The 'text' value was not found.");
        } else {
            this.text = text;
        }

        if (emptyButtonChar == null) {
            throw new NullPointerException("The 'emptyButtonChar' value was not found.");
        } else {
            this.emptyButtonChar = emptyButtonChar;
        }

        if (checkedButtonChar == null) {
            throw new NullPointerException("The 'checkedButtonChar' value was not found.");
        } else {
            this.checkedButtonChar = checkedButtonChar;
        }
    }

    @Override
    public void reset() {
        super.reset();

        text = "";

        emptyButtonChar = '○';
        checkedButtonChar = '◉';

        group = null;
    }
}
