package com.valkryst.VTerminal.builder;

import com.valkryst.VJSON.VJSON;
import com.valkryst.VTerminal.component.RadioButton;
import com.valkryst.VTerminal.component.RadioButtonGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class RadioButtonBuilder extends ButtonBuilder {
    /** The character to display when the radio button is not checked. */
    private char emptyButtonChar;
    /** The character to display when the radio button is checked. */
    private char checkedButtonChar;

    /** The radio button group that the radio button will belong to. */
    private RadioButtonGroup group;

    /**
     * Constructs a new RadioButtonBuilder and initializes it using the JSON representation of a radio button
     * component.
     *
     * @param json
     *          The JSON representation of a radio button component.
     */
    public RadioButtonBuilder(final JSONObject json) {
        super(json);

        if (json == null) {
            return;
        }

        final Character emptyButtonChar = VJSON.getChar(json, "Empty Box Character");
        final Character checkedButtonChar = VJSON.getChar(json, "Checked Box Character");

        this.emptyButtonChar = (emptyButtonChar == null ? '○' : emptyButtonChar);
        this.checkedButtonChar = (checkedButtonChar == null ? '◉' : checkedButtonChar);
    }

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
    public void reset() {
        super.reset();

        emptyButtonChar = '○';
        checkedButtonChar = '◉';

        group = null;
    }
}
