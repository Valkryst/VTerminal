package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.RadioButton;
import com.valkryst.VTerminal.component.RadioButtonGroup;
import lombok.*;
import org.json.simple.JSONObject;

import java.awt.Color;

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

    @Override
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
    public void parse(final @NonNull JSONObject jsonObject) {
        reset();
        super.parse(jsonObject);


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
