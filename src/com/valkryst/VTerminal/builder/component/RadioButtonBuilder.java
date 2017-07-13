package com.valkryst.VTerminal.builder.component;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.component.RadioButton;
import com.valkryst.VTerminal.component.RadioButtonGroup;
import lombok.Getter;

import java.awt.Color;

public class RadioButtonBuilder extends ComponentBuilder<RadioButton, RadioButtonBuilder> {
    /** The radio to transmit events to. */
    @Getter private Radio<String> radio;

    /** The text of the label to display to the right of the radio button. */
    @Getter private String text;

    @Getter private char emptyButtonChar;
    @Getter private char checkedButtonChar;

    /** The radio button group that the radio button will belong to. */
    @Getter private RadioButtonGroup group;

    /** The background color for when the radio button is in the normal state. */
    @Getter private Color backgroundColor_normal;
    /** The foreground color for when the radio button is in the normal state. */
    @Getter private Color foregroundColor_normal;

    /** The background color for when the radio button is in the hover state. */
    @Getter private Color backgroundColor_hover;
    /** The foreground color for when the radio button is in the hover state. */
    @Getter private Color foregroundColor_hover;

    /** The background color for when the radio button is in the checked state. */
    @Getter private Color backgroundColor_checked;
    /** The foreground color for when the radio button is in the checked state. */
    @Getter private Color foregroundColor_checked;

    @Override
    public RadioButton build() throws IllegalStateException {
        checkState();

        final RadioButton radioButton = new RadioButton(this);
        radioButton.registerEventHandlers(super.panel);
        group.addRadioButton(radioButton);
        super.panel.addComponent(radioButton);

        return radioButton;
    }

    /**
     * Checks the current state of the builder.
     *
     * @throws IllegalStateException
     *          If something is wrong with the builder's state.
     */
    protected void checkState() throws IllegalStateException {
        super.checkState();

        if (radio == null) {
            throw new IllegalStateException("The button must have a radio to transmit to.");
        }

        if (group == null) {
            throw new IllegalStateException("The button must belong to a radio button group.");
        }
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        text = "";

        radio = null;

        emptyButtonChar = '○';
        checkedButtonChar = '◉';

        group = null;

        backgroundColor_normal = new Color(0x366C9F, true);
        foregroundColor_normal = new Color(0x66CCFF, true);

        backgroundColor_hover = new Color(0x71AB14, true);
        foregroundColor_hover = new Color(0x99E000, true);

        backgroundColor_checked = new Color(0x366C9F, true);
        foregroundColor_checked = new Color(0xFFFF66, true);
    }

    public RadioButtonBuilder setText(final String text) {
        if (text != null) {
            this.text = text;
        }

        return this;
    }

    public RadioButtonBuilder setEmptyButtonChar(final char emptyButtonChar) {
        this.emptyButtonChar = emptyButtonChar;
        return this;
    }

    public RadioButtonBuilder setCheckedButtonChar(final char checkedButtonChar) {
        this.checkedButtonChar = checkedButtonChar;
        return this;
    }

    public RadioButtonBuilder setGroup(final RadioButtonGroup group) {
        if (group != null) {
            this.group = group;
        }

        return this;
    }

    public RadioButtonBuilder setBackgroundColor_normal(final Color backgroundColor_normal) {
        if (backgroundColor_normal != null) {
            this.backgroundColor_normal = super.enforceTransparentColor(backgroundColor_normal);
        }

        return this;
    }

    public RadioButtonBuilder setForegroundColor_normal(final Color foregroundColor_normal) {
        if (foregroundColor_normal != null) {
            this.foregroundColor_normal = super.enforceTransparentColor(foregroundColor_normal);
        }

        return this;
    }

    public RadioButtonBuilder setBackgroundColor_hover(final Color backgroundColor_hover) {
        if (backgroundColor_hover != null) {
            this.backgroundColor_hover = super.enforceTransparentColor(backgroundColor_hover);
        }

        return this;
    }

    public RadioButtonBuilder setForegroundColor_hover(final Color foregroundColor_hover) {
        if (foregroundColor_hover != null) {
            this.foregroundColor_hover = super.enforceTransparentColor(foregroundColor_hover);
        }

        return this;
    }

    public RadioButtonBuilder setBackgroundColor_checked(final Color backgroundColor_checked) {
        if (backgroundColor_checked != null) {
            this.backgroundColor_checked = super.enforceTransparentColor(backgroundColor_checked);
        }

        return this;
    }

    public RadioButtonBuilder setForegroundColor_checked(final Color foregroundColor_checked) {
        if (foregroundColor_checked != null) {
            this.foregroundColor_checked = super.enforceTransparentColor(foregroundColor_checked);
        }

        return this;
    }
}
