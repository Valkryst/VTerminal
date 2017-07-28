package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.TextArea;
import lombok.Getter;

import java.awt.Color;
import java.util.regex.Pattern;

public class TextAreaBuilder extends ComponentBuilder<TextArea, TextAreaBuilder> {
    /** The width of the text area, in characters. */
    @Getter private int width;
    /** The height of the text area, in characters. */
    @Getter private int height;

    /** The maximum number of characters that the field can contain along the x-axis. */
    @Getter private int maxHorizontalCharacters;
    /** The maximum number of characters that the field can contain along the y-axis. */
    @Getter private int maxVerticalCharacters;

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
    /** Whether or not the UP ARROW key can be used to move the caret one index up. */
    @Getter private boolean upArrowKeyEnabled;
    /** Whether or not the DOWN ARROW key can be used to move the caret one index up. */
    @Getter private boolean downArrowKeyEnabled;
    /** Whether or not the ENTER key can be used to advance the caret to the first position of the next line. */
    @Getter private boolean enterKeyEnabled;
    /** Whether or not the BACK SPACE key can be used to erase the character before the caret and move the caret backwards. */
    @Getter private boolean backSpaceKeyEnabled;

    /** The pattern used to determine which typed characters can be entered into the field. */
    @Getter private Pattern allowedCharacterPattern;

    @Override
    public TextArea build() {
        checkState();

        final TextArea textArea = new TextArea(this);
        textArea.registerEventHandlers(super.panel);
        super.panel.addComponent(textArea);

        return textArea;
    }

    /**
     * Checks the current state of the builder.
     *
     * @throws NullPointerException
     *          If the radio is null.
     */
    protected void checkState() throws NullPointerException {
        super.checkState();

        if (maxHorizontalCharacters < width) {
            maxHorizontalCharacters = width;
        }

        if (maxVerticalCharacters < height) {
            maxVerticalCharacters = height;
        }

        if (radio == null) {
            radio = panel.getRadio();

            if (radio == null) {
                throw new NullPointerException("The text area must have a radio to transmit to.");
            }
        }
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        width = 4;
        height = 4;
        maxHorizontalCharacters = 4;
        maxVerticalCharacters = 4;

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
        upArrowKeyEnabled = true;
        downArrowKeyEnabled = true;
        enterKeyEnabled = true;
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
    public TextAreaBuilder setWidth(final int width) {
        if (width >= 1) {
            this.width = width;
        }

        return this;
    }

    /**
     * Sets the height of the text field, in characters.
     *
     * @param height
     *         The new height.
     *
     * @return
     *         This.
     */
    public TextAreaBuilder setHeight(final int height) {
        if (height >= 1) {
            this.height = height;
        }

        return this;
    }

    /**
     * Sets the maximum amount of characters that the text area
     * can contain on the x-axis.
     *
     * @param maxHorizontalCharacters
     *        The maximum number of characters on the x-axis.
     *
     * @return
     *        This.
     */
    public TextAreaBuilder setMaxHorizontalCharacters(final int maxHorizontalCharacters) {
        if (maxHorizontalCharacters >= 1) {
            this.maxHorizontalCharacters = maxHorizontalCharacters;
        }

        return this;
    }

    /**
     * Sets the maximum amount of characters that the text area
     * can contain on the y-axis.
     *
     * @param maxVerticalCharacters
     *        The maximum number of characters on the y-axis.
     *
     * @return
     *        This.
     */
    public TextAreaBuilder setMaxVerticalCharacters(final int maxVerticalCharacters) {
        if (maxVerticalCharacters >= 1) {
            this.maxVerticalCharacters = maxVerticalCharacters;
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
    public TextAreaBuilder setCaretForegroundColor(final Color caretForegroundColor) {
        if (caretForegroundColor != null) {
            this.caretForegroundColor = caretForegroundColor;
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
    public TextAreaBuilder setCaretBackgroundColor(final Color caretBackgroundColor) {
        if (caretBackgroundColor != null) {
            this.caretBackgroundColor = caretBackgroundColor;
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
    public TextAreaBuilder setForegroundColor(final Color foregroundColor) {
        if (foregroundColor != null) {
            this.foregroundColor = foregroundColor;
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
    public TextAreaBuilder setBackgroundColor(final Color backgroundColor) {
        if (backgroundColor != null) {
            this.backgroundColor = backgroundColor;
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
    public TextAreaBuilder setHomeKeyEnabled(final boolean homeKeyEnabled) {
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
    public TextAreaBuilder setEndKeyEnabled(final boolean endKeyEnabled) {
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
    public TextAreaBuilder setDeleteKeyEnabled(final boolean deleteKeyEnabled) {
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
    public TextAreaBuilder setLeftArrowKeyEnabled(final boolean leftArrowKeyEnabled) {
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
    public TextAreaBuilder setRightArrowKeyEnabled(final boolean rightArrowKeyEnabled) {
        this.rightArrowKeyEnabled = rightArrowKeyEnabled;
        return this;
    }

    /**
     * Enables or disables use of the up arrow key.
     *
     * @param upArrowKeyEnabled
     *         Whether the up key is enabled or disabled.
     *
     * @return
     *         This.
     */
    public TextAreaBuilder setUpArrowKeyEnabled(final boolean upArrowKeyEnabled) {
        this.upArrowKeyEnabled = upArrowKeyEnabled;
        return this;
    }

    /**
     * Enables or disables use of the down arrow key.
     *
     * @param downArrowKeyEnabled
     *         Whether the down key is enabled or disabled.
     *
     * @return
     *         This.
     */
    public TextAreaBuilder setDownArrowKeyEnabled(final boolean downArrowKeyEnabled) {
        this.downArrowKeyEnabled = downArrowKeyEnabled;
        return this;
    }

    /**
     * Enables or disables use of the enter key.
     *
     * @param enterKeyEnabled
     *         Whether the enter key is enabled or disabled.
     *
     * @return
     *         This.
     */
    public TextAreaBuilder setEnterKeyEnabled(final boolean enterKeyEnabled) {
        this.enterKeyEnabled = enterKeyEnabled;
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
    public TextAreaBuilder setBackSpaceKeyEnabled(final boolean backSpaceKeyEnabled) {
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
    public TextAreaBuilder setAllowedCharacterPattern(final Pattern allowedCharacterPattern) {
        if (allowedCharacterPattern != null) {
            this.allowedCharacterPattern = allowedCharacterPattern;
        }

        return this;
    }
}
