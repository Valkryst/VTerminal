package com.valkryst.VTerminal.builder;

import com.valkryst.VJSON.VJSONParser;
import com.valkryst.VTerminal.component.CheckBox;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

@Data
@NoArgsConstructor
public class CheckBoxBuilder extends ButtonBuilder implements VJSONParser {
    /** The character to display when the check box is not checked. */
    private char emptyBoxChar;
    /** The character to display when the check box is checked. */
    private char checkedBoxChar;

    /** Whether or not the check box is checked. */
    private boolean isChecked;

    @Override
    public CheckBox build() {
        checkState();

        super.setDimensions(super.getText().length() + 2, 1);

        super.setText(emptyBoxChar + " " + super.getText());

        return new CheckBox(this);
    }

    @Override
    public void reset() {
        super.reset();

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

        final Character emptyBoxChar = getChar(jsonObject, "emptyBoxChar");
        final Character checkedBoxChar = getChar(jsonObject, "checkedBoxChar");
        // todo Add isChecked

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
