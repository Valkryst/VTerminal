package com.valkryst.VTerminal.builder;

import com.valkryst.VTerminal.component.CheckBox;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class CheckBoxBuilder extends ButtonBuilder {
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
}
