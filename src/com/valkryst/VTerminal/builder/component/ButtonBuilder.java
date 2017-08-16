package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.Button;
import lombok.*;

import java.awt.Color;

@EqualsAndHashCode(callSuper=true)
@ToString
public class ButtonBuilder extends ComponentBuilder<Button> {
    /** The text to display on the button. */
    @Getter @Setter @NonNull private String text;

    /** The first character of the button's text. This is used to identify the text as a button. */
    @Getter @Setter private char startingCharacter;
    /** The last character of the button's text. This is used to identify the text as a button. */
    @Getter @Setter private char endingCharacter;

    /** Whether or not the button should use the starting/ending characters. */
    @Getter @Setter private boolean usingStartingAndEndingCharacters;

    /** The background color for when the button is in the normal state. */
    @Getter @Setter @NonNull private Color backgroundColor_normal;
    /** The foreground color for when the button is in the normal state. */
    @Getter @Setter @NonNull private Color foregroundColor_normal;

    /** The background color for when the button is in the hover state. */
    @Getter @Setter @NonNull private Color backgroundColor_hover;
    /** The foreground color for when the button is in the hover state. */
    @Getter @Setter @NonNull private Color foregroundColor_hover;

    /** The background color for when the button is in the pressed state. */
    @Getter @Setter @NonNull private Color backgroundColor_pressed;
    /** The foreground color for when the button is in the pressed state. */
    @Getter @Setter @NonNull private Color foregroundColor_pressed;

    /** The function to run when the button is clicked. */
    @Getter @Setter private Runnable onClickFunction;

    @Override
    public Button build() {
        checkState();
        return new Button(this);
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        text = "";

        startingCharacter = '<';
        endingCharacter = '>';

        usingStartingAndEndingCharacters = true;

        backgroundColor_normal = new Color(45, 45, 45, 255);
        foregroundColor_normal = new Color(45, 155, 255, 255);

        backgroundColor_hover = new Color(0xFF71AB14, true);
        foregroundColor_hover = new Color(0xFF99E000, true);

        backgroundColor_pressed = backgroundColor_normal;
        foregroundColor_pressed = new Color(0xFFFFFF66, true);

        onClickFunction = () -> {};
    }
}
