package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.CheckBox;
import lombok.*;
import org.json.simple.JSONObject;

import java.awt.Color;

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

        super.width = text.length() + 2;
        super.height = 1;

        text = emptyBoxChar + " " + text;

        return new CheckBox(this);
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        text = "";

        emptyBoxChar = '☐';
        checkedBoxChar = '☑';

        isChecked = false;

        super.setBackgroundColor_pressed(super.getBackgroundColor_normal());
        super.setForegroundColor_pressed(new Color(0xFFFFD72D, true));
    }

    @Override
    public void parse(final @NonNull JSONObject jsonObject) {
        reset();
        super.parse(jsonObject);


        final String text = getString(jsonObject, "text");

        final Character emptyBoxChar = getChar(jsonObject, "emptyBoxChar");
        final Character checkedBoxChar = getChar(jsonObject, "checkedBoxChar");

        final Boolean isChecked = (Boolean) jsonObject.get("isChecked");


        if (text != null) {
            this.text = text;
        }


        if (emptyBoxChar != null) {
            this.emptyBoxChar = emptyBoxChar;
        }

        if (checkedBoxChar != null) {
            this.checkedBoxChar = checkedBoxChar;
        }


        if (isChecked != null) {
            this.isChecked = isChecked;
        }
    }
}
