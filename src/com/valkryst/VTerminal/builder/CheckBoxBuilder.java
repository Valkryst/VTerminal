package com.valkryst.VTerminal.builder;

import com.valkryst.VJSON.VJSONParser;
import com.valkryst.VTerminal.component.CheckBox;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.json.simple.JSONObject;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class CheckBoxBuilder extends ButtonBuilder implements VJSONParser {
    /** The text of the label to display to the right of the check box. */
    @NonNull private String text;

    /** The character to display when the check box is not checked. */
    private char emptyBoxChar;
    /** The character to display when the check box is checked. */
    private char checkedBoxChar;

    /** Whether or not the check box is checked. */
    private boolean isChecked;

    @Override
    public CheckBox build() {
        checkState();

        super.setDimensions(text.length() + 2, 1);

        text = emptyBoxChar + " " + text;

        return new CheckBox(this);
    }

    @Override
    public void reset() {
        super.reset();

        text = "";

        emptyBoxChar = '☐';
        checkedBoxChar = '☑';

        isChecked = false;
    }

    @Override
    public void parse(final JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }

        super.parse(jsonObject);

        final String text = getString(jsonObject, "text");
        final Character emptyBoxChar = getChar(jsonObject, "emptyBoxChar");
        final Character checkedBoxChar = getChar(jsonObject, "checkedBoxChar");
        // todo Add isChecked

        if (text == null) {
            throw new NullPointerException("The 'text' value was not found.");
        } else {
            this.text = text;
        }

        if (emptyBoxChar == null) {
            throw new NullPointerException("The 'emptyBoxChar' value was not found.");
        } else {
            this.emptyBoxChar = emptyBoxChar;
        }

        if (checkedBoxChar == null) {
            throw new NullPointerException("The 'checkedBoxChar' value was not found.");
        } else {
            this.checkedBoxChar = checkedBoxChar;
        }
    }
}
