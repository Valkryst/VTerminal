package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.CheckBox;
import lombok.*;
import org.json.simple.JSONObject;

import java.awt.Color;

@EqualsAndHashCode(callSuper=true)
@ToString
public class CheckBoxBuilder extends ButtonBuilder {
    /** The text of the label to display to the right of the check box. */
    @Getter @Setter @NonNull private String text;

    /** The character to display when the check box is not checked. */
    @Getter @Setter private char emptyBoxChar;
    /** The character to display when the check box is checked. */
    @Getter @Setter private char checkedBoxChar;

    /** Whether or not the check box is checked. */
    @Getter @Setter private boolean isChecked;

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
    public void parseJSON(final @NonNull JSONObject jsonObject) {
        reset();
        super.parseJSON(jsonObject);


        final String text = (String) jsonObject.get("text");

        final Character emptyBoxChar = (Character) jsonObject.get("emptyBoxChar");
        final Character checkedBoxChar = (Character) jsonObject.get("checkedBoxChar");

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
