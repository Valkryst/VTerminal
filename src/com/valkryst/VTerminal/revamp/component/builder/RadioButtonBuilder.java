package com.valkryst.VTerminal.revamp.component.builder;

import com.valkryst.VTerminal.revamp.component.component.RadioButton;
import com.valkryst.VTerminal.revamp.component.component.RadioButtonGroup;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class RadioButtonBuilder extends ButtonBuilder {
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

        super.getDimensions().setSize(text.length() + 2, 1);

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
    public void reset() {
        super.reset();

        text = "";

        emptyButtonChar = '○';
        checkedButtonChar = '◉';

        group = null;
    }
}
