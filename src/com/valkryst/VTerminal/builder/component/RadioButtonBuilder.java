package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.RadioButton;
import com.valkryst.VTerminal.component.RadioButtonGroup;
import lombok.*;
import org.json.simple.JSONObject;

import java.awt.Color;

@EqualsAndHashCode(callSuper=true)
@ToString
public class RadioButtonBuilder extends ButtonBuilder {
    /** The text of the label to display to the right of the radio button. */
    @Getter @Setter @NonNull private String text;

    /** The character to display when the radio button is not checked. */
    @Getter @Setter private char emptyButtonChar;
    /** The character to display when the radio button is checked. */
    @Getter @Setter private char checkedButtonChar;

    /** The radio button group that the radio button will belong to. */
    @Getter @Setter @NonNull private RadioButtonGroup group;

    @Override
    public RadioButton build() {
        checkState();

        super.width = text.length() + 2;
        super.height = 1;

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

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        text = "";

        emptyButtonChar = '○';
        checkedButtonChar = '◉';

        group = null;

        super.setBackgroundColor_pressed(super.getBackgroundColor_normal());
        super.setForegroundColor_pressed(new Color(0xFFFFD72D, true));
    }

    @Override
    public void parseJSON(final @NonNull JSONObject jsonObject) {
        reset();
        super.parseJSON(jsonObject);


        final String text = (String) jsonObject.get("text");

        final Character emptyButtonChar = (Character) jsonObject.get("emptyButtonChar");
        final Character checkedButtonChar = (Character) jsonObject.get("checkedButtonChar");


        if (text != null) {
            this.text = text;
        }



        if (emptyButtonChar != null) {
            this.emptyButtonChar = emptyButtonChar;
        }

        if (checkedButtonChar != null) {
            this.checkedButtonChar = checkedButtonChar;
        }
    }
}
