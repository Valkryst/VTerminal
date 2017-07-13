package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.CheckBox;
import com.valkryst.VTerminal.misc.ColorFunctions;
import lombok.Getter;

import java.awt.Color;

public class CheckBoxBuilder extends ComponentBuilder<CheckBox, CheckBoxBuilder> {
    /** The text of the label to display to the right of the check box. */
    @Getter private String text;

    /** The character to display when the check box is not checked. */
    @Getter private char emptyBoxChar;
    /** The character to display when the check box is checked. */
    @Getter private char checkedBoxChar;

    /** Whether or not the check box is checked. */
    @Getter private boolean isChecked;

    /** The background color for when the check box is in the normal state. */
    @Getter private Color backgroundColor_normal;
    /** The foreground color for when the check box is in the normal state. */
    @Getter private Color foregroundColor_normal;

    /** The background color for when the check box is in the hover state. */
    @Getter private Color backgroundColor_hover;
    /** The foreground color for when the check box is in the hover state. */
    @Getter private Color foregroundColor_hover;

    /** The background color for when the check box is in the checked state. */
    @Getter private Color backgroundColor_checked;
    /** The foreground color for when the check box is in the checked state. */
    @Getter private Color foregroundColor_checked;

    @Override
    public CheckBox build() throws IllegalStateException {
        checkState();

        final CheckBox checkBox = new CheckBox(this);
        checkBox.registerEventHandlers(super.panel);
        super.panel.addComponent(checkBox);

        return checkBox;
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
            throw new IllegalStateException("The box must have a radio to transmit to.");
        }
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        text = "";

        radio = null;

        emptyBoxChar = '☐';
        checkedBoxChar = '☑';

        isChecked = false;

        backgroundColor_normal = new Color(0x366C9F, true);
        foregroundColor_normal = new Color(0x66CCFF, true);

        backgroundColor_hover = new Color(0x71AB14, true);
        foregroundColor_hover = new Color(0x99E000, true);

        backgroundColor_checked = new Color(0x366C9F, true);
        foregroundColor_checked = new Color(0xFFFF66, true);
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
    public CheckBoxBuilder setText(final String text) {
        if (text != null) {
            this.text = text;
        }

        return this;
    }

    /**
     * Sets the empty box character.
     *
     * @param emptyBoxChar
     *        The new empty box character.
     *
     * @return
     *        This.
     */
    public CheckBoxBuilder setEmptyBoxChar(final char emptyBoxChar) {
        this.emptyBoxChar = emptyBoxChar;
        return this;
    }

    /**
     * Sets the checked box character.
     *
     * @param checkedBoxChar
     *        The new checked box character.
     *
     * @return
     *        This.
     */
    public CheckBoxBuilder setCheckedBoxChar(final char checkedBoxChar) {
        this.checkedBoxChar = checkedBoxChar;
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
    public CheckBoxBuilder setBackgroundColor_normal(final Color backgroundColor_normal) {
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
    public CheckBoxBuilder setForegroundColor_normal(final Color foregroundColor_normal) {
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
    public CheckBoxBuilder setBackgroundColor_hover(final Color backgroundColor_hover) {
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
    public CheckBoxBuilder setForegroundColor_hover(final Color foregroundColor_hover) {
        if (foregroundColor_hover != null) {
            this.foregroundColor_hover = ColorFunctions.enforceTransparentColor(foregroundColor_hover);
        }

        return this;
    }

    /**
     * Sets the checked-state's background color.
     *
     * @param backgroundColor_checked
     *        The new background color.
     *
     * @return
     *        This.
     */
    public CheckBoxBuilder setBackgroundColor_checked(final Color backgroundColor_checked) {
        if (backgroundColor_checked != null) {
            this.backgroundColor_checked = ColorFunctions.enforceTransparentColor(backgroundColor_checked);
        }

        return this;
    }

    /**
     * Sets the checked-state's foreground color.
     *
     * @param foregroundColor_checked
     *        The new foreground color.
     *
     * @return
     *        This.
     */
    public CheckBoxBuilder setForegroundColor_checked(final Color foregroundColor_checked) {
        if (foregroundColor_checked != null) {
            this.foregroundColor_checked = ColorFunctions.enforceTransparentColor(foregroundColor_checked);
        }

        return this;
    }
}
