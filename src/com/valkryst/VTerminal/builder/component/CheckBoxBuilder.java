package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.CheckBox;
import lombok.*;

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

        backgroundColor_hover = new Color(0xFF71AB14, true);
        foregroundColor_hover = new Color(0xFF99E000, true);

        backgroundColor_checked = backgroundColor_normal;
        foregroundColor_checked = new Color(0xFFFFFF66, true);
    }
}
