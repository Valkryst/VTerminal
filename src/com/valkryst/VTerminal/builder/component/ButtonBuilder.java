package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.Button;
import com.valkryst.VTerminal.misc.ColorFunctions;
import lombok.Getter;

import java.awt.Color;

public class ButtonBuilder extends ComponentBuilder<Button, ButtonBuilder> {
    /** The text to display on the button. */
    @Getter private String text;

    /** The first character of the button's text. This is used to identify the text as a button. */
    @Getter private char startingCharacter;
    /** The last character of the button's text. This is used to identify the text as a button. */
    @Getter private char endingCharacter;


    /** The background color for when the button is in the normal state. */
    @Getter private Color backgroundColor_normal;
    /** The foreground color for when the button is in the normal state. */
    @Getter private Color foregroundColor_normal;

    /** The background color for when the button is in the hover state. */
    @Getter private Color backgroundColor_hover;
    /** The foreground color for when the button is in the hover state. */
    @Getter private Color foregroundColor_hover;

    /** The background color for when the button is in the pressed state. */
    @Getter private Color backgroundColor_pressed;
    /** The foreground color for when the button is in the pressed state. */
    @Getter private Color foregroundColor_pressed;

    /** The function to run when the button is clicked. */
    @Getter private Runnable onClickFunction;

    @Override
    public Button build() {
        checkState();

        final Button button = new Button(this);
        button.registerEventHandlers(super.panel);
        super.panel.addComponent(button);

        return button;
    }

    /**
     * Checks the current state of the builder.
     *
     * @throws NullPointerException
     *          If the radio is null.
     */
    protected void checkState() throws NullPointerException {
        super.checkState();

        if (radio == null) {
            throw new NullPointerException("The button must have a radio to transmit to.");
        }
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        text = "";

        radio = null;

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

    /**
     * Sets the text.
     *
     * @param text
     *        The new text.
     *
     * @return
     *        This.
     */
    public ButtonBuilder setText(final String text) {
        if (text != null) {
            this.text = text;
        }

        return this;
    }

    /**
     * Sets the starting character.
     *
     * @param startingCharacter
     *        The new starting character.
     *
     * @return
     *        This.
     */
    public ButtonBuilder setStartingCharacter(final char startingCharacter) {
        this.startingCharacter = startingCharacter;
        return this;
    }

    /**
     * Sets the ending character.
     *
     * @param endingCharacter
     *        The new ending character.
     *
     * @return
     *        This.
     */
    public ButtonBuilder setEndingCharacter(final char endingCharacter) {
        this.endingCharacter = endingCharacter;
        return this;
    }

    /**
     * Sets the normal-state's background color.
     *
     * @param backgroundColor_normal
     *        The new background color.
     *
     * @return
     *        This.
     */
    public ButtonBuilder setBackgroundColor_normal(final Color backgroundColor_normal) {
        if (backgroundColor_normal != null) {
            this.backgroundColor_normal = ColorFunctions.enforceTransparentColor(backgroundColor_normal);
        }

        return this;
    }

    /**
     * Sets the normal-state's foreground color.
     *
     * @param foregroundColor_normal
     *        The new foreground color.
     *
     * @return
     *        This.
     */
    public ButtonBuilder setForegroundColor_normal(final Color foregroundColor_normal) {
        if (foregroundColor_normal != null) {
            this.foregroundColor_normal = ColorFunctions.enforceTransparentColor(foregroundColor_normal);
        }

        return this;
    }

    /**
     * Sets the hover-state's background color.
     *
     * @param backgroundColor_hover
     *        The new background color.
     *
     * @return
     *        This.
     */
    public ButtonBuilder setBackgroundColor_hover(final Color backgroundColor_hover) {
        if (backgroundColor_hover != null) {
            this.backgroundColor_hover = ColorFunctions.enforceTransparentColor(backgroundColor_hover);
        }

        return this;
    }

    /**
     * Sets the hover-state's foreground color.
     *
     * @param foregroundColor_hover
     *        The new foreground color.
     *
     * @return
     *        This.
     */
    public ButtonBuilder setForegroundColor_hover(final Color foregroundColor_hover) {
        if (foregroundColor_hover != null) {
            this.foregroundColor_hover = ColorFunctions.enforceTransparentColor(foregroundColor_hover);
        }

        return this;
    }

    /**
     * Sets the pressed-state's background color.
     *
     * @param backgroundColor_pressed
     *        The new background color.
     *
     * @return
     *        This.
     */
    public ButtonBuilder setBackgroundColor_pressed(final Color backgroundColor_pressed) {
        if (backgroundColor_pressed != null) {
            this.backgroundColor_pressed = ColorFunctions.enforceTransparentColor(backgroundColor_pressed);
        }

        return this;
    }

    /**
     * Sets the pressed-state's foreground color.
     *
     * @param foregroundColor_pressed
     *        The new foreground color.
     *
     * @return
     *        This.
     */
    public ButtonBuilder setForegroundColor_pressed(final Color foregroundColor_pressed) {
        if (foregroundColor_pressed != null) {
            this.foregroundColor_pressed = ColorFunctions.enforceTransparentColor(foregroundColor_pressed);
        }

        return this;
    }

    /**
     * Sets the on-click function.
     *
     * @param onClickFunction
     *        The new on-click function.
     *
     * @return
     *        This.
     */
    public ButtonBuilder setOnClickFunction(final Runnable onClickFunction) {
        if (onClickFunction != null) {
            this.onClickFunction = onClickFunction;
        }

        return this;
    }
}
