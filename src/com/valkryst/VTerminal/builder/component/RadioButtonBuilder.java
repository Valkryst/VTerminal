package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.RadioButton;
import com.valkryst.VTerminal.component.RadioButtonGroup;
import com.valkryst.VTerminal.misc.ColorFunctions;
import lombok.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.Color;

@EqualsAndHashCode(callSuper=true)
@ToString
public class RadioButtonBuilder extends ComponentBuilder<RadioButton> {
    /** The text of the label to display to the right of the radio button. */
    @Getter @Setter @NonNull private String text;

    /** The character to display when the radio button is not checked. */
    @Getter @Setter private char emptyButtonChar;
    /** The character to display when the radio button is checked. */
    @Getter @Setter private char checkedButtonChar;

    /** The radio button group that the radio button will belong to. */
    @Getter @Setter @NonNull private RadioButtonGroup group;

    /** The background color for when the radio button is in the normal state. */
    @Getter @Setter @NonNull private Color backgroundColor_normal;
    /** The foreground color for when the radio button is in the normal state. */
    @Getter @Setter @NonNull private Color foregroundColor_normal;

    /** The background color for when the radio button is in the hover state. */
    @Getter @Setter @NonNull private Color backgroundColor_hover;
    /** The foreground color for when the radio button is in the hover state. */
    @Getter @Setter @NonNull private Color foregroundColor_hover;

    /** The background color for when the radio button is in the checked state. */
    @Getter @Setter @NonNull private Color backgroundColor_checked;
    /** The foreground color for when the radio button is in the checked state. */
    @Getter @Setter @NonNull private Color foregroundColor_checked;

    @Override
    public RadioButton build() {
        checkState();

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

        backgroundColor_normal = new Color(45, 45, 45, 255);
        foregroundColor_normal = new Color(0xFF2DBEFF, true);

        backgroundColor_hover = new Color(0xFF2DFF63, true);
        foregroundColor_hover = ColorFunctions.shade(backgroundColor_hover, 0.5);

        backgroundColor_checked = backgroundColor_normal;
        foregroundColor_checked = new Color(0xFFFFD72D, true);
    }

    @Override
    public void parseJSON(final @NonNull JSONObject jsonObject) {
        reset();
        super.parseJSON(jsonObject);


        final String text = (String) jsonObject.get("text");

        final Character emptyButtonChar = (Character) jsonObject.get("emptyButtonChar");
        final Character checkedButtonChar = (Character) jsonObject.get("checkedButtonChar");

        final Color backgroundColor_normal = super.loadColorFromJSON((JSONArray) jsonObject.get("backgroundColor_normal"));
        final Color foregroundColor_normal = super.loadColorFromJSON((JSONArray) jsonObject.get("foregroundColor_normal"));

        final Color backgroundColor_hover = super.loadColorFromJSON((JSONArray) jsonObject.get("backgroundColor_hover"));
        final Color foregroundColor_hover = super.loadColorFromJSON((JSONArray) jsonObject.get("foregroundColor_hover"));

        final Color backgroundColor_checked = super.loadColorFromJSON((JSONArray) jsonObject.get("backgroundColor_checked"));
        final Color foregroundColor_checked = super.loadColorFromJSON((JSONArray) jsonObject.get("foregroundColor_checked"));


        if (text != null) {
            this.text = text;
        }



        if (emptyButtonChar != null) {
            this.emptyButtonChar = emptyButtonChar;
        }

        if (checkedButtonChar != null) {
            this.checkedButtonChar = checkedButtonChar;
        }



        if (backgroundColor_normal != null) {
            this.backgroundColor_normal = backgroundColor_normal;
        }

        if (foregroundColor_normal != null) {
            this.foregroundColor_normal = foregroundColor_normal;
        }



        if (backgroundColor_hover != null) {
            this.backgroundColor_hover = backgroundColor_hover;
        }

        if (foregroundColor_hover != null) {
            this.foregroundColor_hover = foregroundColor_hover;
        }



        if (backgroundColor_checked != null) {
            this.backgroundColor_checked = backgroundColor_checked;
        }

        if (foregroundColor_checked != null) {
            this.foregroundColor_checked = foregroundColor_checked;
        }
    }
}
