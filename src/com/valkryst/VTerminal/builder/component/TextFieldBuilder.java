package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.TextField;
import com.valkryst.VTerminal.misc.ColorFunctions;
import lombok.Getter;

import java.awt.Color;
import java.util.regex.Pattern;

public class TextFieldBuilder extends ComponentBuilder<TextField, TextFieldBuilder> {
    /** The width of the text field, in characters. */
    @Getter private int width;

    /** The maximum number of characters that the text field can contain. */
    @Getter private int maxCharacters;

    /** The foreground color of the caret. */
    @Getter private Color caretForegroundColor;
    /** The background color of the caret. */
    @Getter private Color caretBackgroundColor;

    /** The foreground color of non-caret characters. */
    @Getter private Color foregroundColor;
    /** The background color of non-caret characters. */
    @Getter private Color backgroundColor;

    /** Whether or not the HOME key can be used to move the caret to the first index of the field. */
    @Getter private boolean homeKeyEnabled;
    /** Whether or not the END key can be used to move the caret to the last index of the field. */
    @Getter private boolean endKeyEnabled;
    /** Whether or not the DELETE key can be used to erase the character that the caret is on. */
    @Getter private boolean deleteKeyEnabled;
    /** Whether or not the LEFT ARROW key can be used to move the caret one index to the left. */
    @Getter private boolean leftArrowKeyEnabled;
    /** Whether or not the RIGHT ARROW key can be used to move the caret one index to the right. */
    @Getter private boolean rightArrowKeyEnabled;
    /** Whether or not the BACK SPACE key can be used to erase the character before the caret and move the caret backwards. */
    @Getter private boolean backSpaceKeyEnabled;

    /** The pattern used to determine which typed characters can be entered into the field. */
    @Getter private Pattern allowedCharacterPattern;

    @Override
    public TextField build() throws IllegalStateException {
        checkState();

        final TextField textField = new TextField(this);
        textField.registerEventHandlers(super.panel);
        super.panel.addComponent(textField);

        return textField;
    }

    /**
     * Checks the current state of the builder.
     *
     * @throws IllegalStateException
     *          If something is wrong with the builder's state.
     */
    protected void checkState() throws IllegalStateException {
        super.checkState();

        if (maxCharacters < width) {
            maxCharacters = width;
        }

        if (radio == null) {
            radio = panel.getRadio();

            if (radio == null) {
                throw new IllegalStateException("The text field must have a radio to transmit to.");
            }
        }
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        width = 4;

        radio = null;

        caretForegroundColor = new Color(0xFF21B6A8, true);
        caretBackgroundColor = new Color(0xFF52F2EA, true);

        foregroundColor = new Color(0xFF52F2EA, true);
        backgroundColor = new Color(0xFF21B6A8, true);

        homeKeyEnabled = true;
        endKeyEnabled = true;
        deleteKeyEnabled = true;
        leftArrowKeyEnabled = true;
        rightArrowKeyEnabled = true;
        backSpaceKeyEnabled = true;

        allowedCharacterPattern = Pattern.compile("^[a-zA-z0-9$-/:-?{-~!\"^_`\\[\\]@# ]$");
    }

    /**
     * Sets the width of the text field, in characters.
     *
     * @param width
     *         The new width.
     *
     * @return
     *         This.
     */
    public TextFieldBuilder setWidth(final int width) {
        if (width >= 1) {
            this.width = width;
        }

        return this;
    }

    /**
     * Sets the maximum amount of characters that the text field
     * can contain.
     *
     * @param maxCharacters
     *        The maximum number of characters.
     *
     * @return
     *        This.
     */
    public TextFieldBuilder setMaxCharacters(final int maxCharacters) {
        if (maxCharacters >= 1) {
            this.maxCharacters = maxCharacters;
        }

        return this;
    }

    /**
     * Sets the foreground color of the caret.
     *
     * @param caretForegroundColor
     *         The color.
     *
     * @return
     *         This.
     */
    public TextFieldBuilder setCaretForegroundColor(final Color caretForegroundColor) {
        if (caretForegroundColor != null) {
            this.caretForegroundColor = ColorFunctions.enforceTransparentColor(caretForegroundColor);
        }

        return this;
    }

    /**
     * Sets the background color of the caret.
     *
     * @param caretBackgroundColor
     *         The color.
     *
     * @return
     *         This.
     */
    public TextFieldBuilder setCaretBackgroundColor(final Color caretBackgroundColor) {
        if (caretBackgroundColor != null) {
            this.caretBackgroundColor = ColorFunctions.enforceTransparentColor(caretBackgroundColor);
        }

        return this;
    }

    /**
     * Sets the foreground color of non-caret characters.
     *
     * @param foregroundColor
     *         The color.
     *
     * @return
     *         This.
     */
    public TextFieldBuilder setForegroundColor(final Color foregroundColor) {
        if (foregroundColor != null) {
            this.foregroundColor = ColorFunctions.enforceTransparentColor(foregroundColor);
        }

        return this;
    }

    /**
     * Sets the background color of non-caret characters.
     *
     * @param backgroundColor
     *         The color.
     *
     * @return
     *         This.
     */
    public TextFieldBuilder setBackgroundColor(final Color backgroundColor) {
        if (backgroundColor != null) {
            this.backgroundColor = ColorFunctions.enforceTransparentColor(backgroundColor);
        }

        return this;
    }

    /**
     * Enables or disables use of the home key.
     *
     * @param homeKeyEnabled
     *         Whether the home key is enabled or disabled.
     *
     * @return
     *         This.
     */
    public TextFieldBuilder setHomeKeyEnabled(final boolean homeKeyEnabled) {
        this.homeKeyEnabled = homeKeyEnabled;
        return this;
    }

    /**
     * Enables or disables use of the end key.
     *
     * @param endKeyEnabled
     *         Whether the end key is enabled or disabled.
     *
     * @return
     *         This.
     */
    public TextFieldBuilder setEndKeyEnabled(final boolean endKeyEnabled) {
        this.endKeyEnabled = endKeyEnabled;
        return this;
    }

    /**
     * Enables or disables use of the delete key.
     *
     * @param deleteKeyEnabled
     *         Whether the delete key is enabled or disabled.
     *
     * @return
     *         This.
     */
    public TextFieldBuilder setDeleteKeyEnabled(final boolean deleteKeyEnabled) {
        this.deleteKeyEnabled = deleteKeyEnabled;
        return this;
    }

    /**
     * Enables or disables use of the left arrow key.
     *
     * @param leftArrowKeyEnabled
     *         Whether the left arrow key is enabled or disabled.
     *
     * @return
     *         This.
     */
    public TextFieldBuilder setLeftArrowKeyEnabled(final boolean leftArrowKeyEnabled) {
        this.leftArrowKeyEnabled = leftArrowKeyEnabled;
        return this;
    }

    /**
     * Enables or disables use of the right arrow key.
     *
     * @param rightArrowKeyEnabled
     *         Whether the right arrow key is enabled or disabled.
     *
     * @return
     *         This.
     */
    public TextFieldBuilder setRightArrowKeyEnabled(final boolean rightArrowKeyEnabled) {
        this.rightArrowKeyEnabled = rightArrowKeyEnabled;
        return this;
    }

    /**
     * Enables or disables use of the backspace key.
     *
     * @param backSpaceKeyEnabled
     *         Whether the backspace key is enabled or disabled.
     *
     * @return
     *         This.
     */
    public TextFieldBuilder setBackSpaceKeyEnabled(final boolean backSpaceKeyEnabled) {
        this.backSpaceKeyEnabled = backSpaceKeyEnabled;
        return this;
    }

    /**
     * Sets the pattern used to determine which typed characters can be entered into the field.
     *
     * @param allowedCharacterPattern
     *         The pattern.
     *
     * @return
     *         This.
     */
    public TextFieldBuilder setAllowedCharacterPattern(final Pattern allowedCharacterPattern) {
        if (allowedCharacterPattern != null) {
            this.allowedCharacterPattern = allowedCharacterPattern;
        }

        return this;
    }
}
