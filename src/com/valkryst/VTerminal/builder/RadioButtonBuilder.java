package com.valkryst.VTerminal.builder;

import com.valkryst.VJSON.VJSONParser;
import com.valkryst.VTerminal.component.RadioButton;
import com.valkryst.VTerminal.component.RadioButtonGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class RadioButtonBuilder extends ButtonBuilder implements VJSONParser {
    /** The character to display when the radio button is not checked. */
    private char emptyButtonChar;
    /** The character to display when the radio button is checked. */
    private char checkedButtonChar;

    /** The radio button group that the radio button will belong to. */
    private RadioButtonGroup group;

    @Override
    public RadioButton build() {
        checkState();

        super.setDimensions(super.getText().length() + 2, 1);

        super.setText(emptyButtonChar + " " + super.getText());

        final RadioButton radioButton = new RadioButton(this);
        group.addRadioButton(radioButton);

        return radioButton;
    }

    @Override
    protected void checkState() throws NullPointerException {
        super.checkState();

        if (group == null) {
            group = new RadioButtonGroup();
        }
    }

    @Override
    public void parse(final JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }

        super.parse(jsonObject);

        final Character emptyButtonChar = getChar(jsonObject, "emptyButtonChar");
        final Character checkedButtonChar = getChar(jsonObject, "checkedButtonChar");
        // todo Add isChecked

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

        emptyButtonChar = '○';
        checkedButtonChar = '◉';

        group = null;
    }
}
