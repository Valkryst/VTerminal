package com.valkryst.VTerminal.builder;

import com.valkryst.VJSON.VJSON;
import com.valkryst.VTerminal.component.CheckBox;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class CheckBoxBuilder extends ButtonBuilder {
    /** The character to display when the check box is not checked. */
    private char emptyBoxChar;
    /** The character to display when the check box is checked. */
    private char checkedBoxChar;

    /** Whether or not the check box is checked. */
    private boolean isChecked;

    /**
     * Constructs a new CheckBoxBuilder and initializes it using the JSON representation of a check box
     * component.
     *
     * @param json
     *          The JSON representation of a check box component.
     */
    public CheckBoxBuilder(final JSONObject json) {
        super(json);

        if (json == null) {
            return;
        }

        final Character emptyBoxChar = VJSON.getChar(json, "Empty Box Character");
        final Character checkedBoxChar = VJSON.getChar(json, "Checked Box Character");
        final Boolean isChecked = VJSON.getBoolean(json, "Is Checked");

        this.emptyBoxChar = (emptyBoxChar == null ? '☐' : emptyBoxChar);
        this.checkedBoxChar = (checkedBoxChar == null ? '☑' : checkedBoxChar);
        this.isChecked = (isChecked == null ? false : isChecked);
    }

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

    /*
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
    */
}
