package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.CheckBox;
import com.valkryst.VTerminal.misc.ColorFunctions;
import com.valkryst.VTerminal.misc.JSONFunctions;
import lombok.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.Color;

@EqualsAndHashCode(callSuper=true)
@ToString
public class CheckBoxBuilder extends ComponentBuilder<CheckBox> {
    /** The text of the label to display to the right of the check box. */
    @Getter @Setter @NonNull private String text;

    /** The character to display when the check box is not checked. */
    @Getter @Setter private char emptyBoxChar;
    /** The character to display when the check box is checked. */
    @Getter @Setter private char checkedBoxChar;

    /** Whether or not the check box is checked. */
    @Getter @Setter private boolean isChecked;

    /** The background color for when the check box is in the normal state. */
    @Getter @Setter @NonNull private Color backgroundColor_normal;
    /** The foreground color for when the check box is in the normal state. */
    @Getter @Setter @NonNull private Color foregroundColor_normal;

    /** The background color for when the check box is in the hover state. */
    @Getter @Setter @NonNull private Color backgroundColor_hover;
    /** The foreground color for when the check box is in the hover state. */
    @Getter @Setter @NonNull private Color foregroundColor_hover;

    /** The background color for when the check box is in the checked state. */
    @Getter @Setter @NonNull private Color backgroundColor_checked;
    /** The foreground color for when the check box is in the checked state. */
    @Getter @Setter @NonNull private Color foregroundColor_checked;

    @Override
    public CheckBox build() {
        checkState();

        super.width = text.length() + 2;
        super.height = 1;

        return new CheckBox(this);
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        text = "";

        emptyBoxChar = '☐';
        checkedBoxChar = '☑';

        isChecked = false;

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

        final Character emptyBoxChar = (Character) jsonObject.get("emptyBoxChar");
        final Character checkedBoxChar = (Character) jsonObject.get("checkedBoxChar");

        final Boolean isChecked = (Boolean) jsonObject.get("isChecked");

        final Color backgroundColor_normal = JSONFunctions.loadColorFromJSON((JSONArray) jsonObject.get("backgroundColor_normal"));
        final Color foregroundColor_normal = JSONFunctions.loadColorFromJSON((JSONArray) jsonObject.get("foregroundColor_normal"));

        final Color backgroundColor_hover = JSONFunctions.loadColorFromJSON((JSONArray) jsonObject.get("backgroundColor_hover"));
        final Color foregroundColor_hover = JSONFunctions.loadColorFromJSON((JSONArray) jsonObject.get("foregroundColor_hover"));

        final Color backgroundColor_checked = JSONFunctions.loadColorFromJSON((JSONArray) jsonObject.get("backgroundColor_checked"));
        final Color foregroundColor_checked = JSONFunctions.loadColorFromJSON((JSONArray) jsonObject.get("foregroundColor_checked"));


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
