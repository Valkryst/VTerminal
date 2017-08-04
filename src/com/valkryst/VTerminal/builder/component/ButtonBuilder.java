package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.Button;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.awt.Color;

public class ButtonBuilder extends ComponentBuilder<Button> {
    /** The text to display on the button. */
    @Getter @Setter @NonNull private String text;

    /** The first character of the button's text. This is used to identify the text as a button. */
    @Getter @Setter private char startingCharacter;
    /** The last character of the button's text. This is used to identify the text as a button. */
    @Getter @Setter private char endingCharacter;


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

        final Button button = new Button(this);
        button.registerEventHandlers(super.getPanel());
        super.getPanel().addComponent(button);

        return button;
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        text = "";

        startingCharacter = '<';
        endingCharacter = '>';

        backgroundColor_normal = new Color(0xFF366C9F, true);
        foregroundColor_normal = new Color(0xFF66CCFF, true);

        backgroundColor_hover = new Color(0xFF71AB14, true);
        foregroundColor_hover = new Color(0xFF99E000, true);

        backgroundColor_pressed = new Color(0xFF366C9F, true);
        foregroundColor_pressed = new Color(0xFFFFFF66, true);

        onClickFunction = () -> {};
    }
}
