package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.TextField;
import lombok.*;

import java.awt.Color;
import java.util.regex.Pattern;

@EqualsAndHashCode
@ToString
public class TextFieldBuilder extends ComponentBuilder<TextField> {
    /** The width of the text field, in characters. */
    @Getter @Setter private int width;

    /** The maximum number of characters that the text field can contain. */
    @Getter @Setter private int maxCharacters;

    /** The foreground color of the caret. */
    @Getter @Setter @NonNull private Color caretForegroundColor;
    /** The background color of the caret. */
    @Getter @Setter @NonNull private Color caretBackgroundColor;

    /** The foreground color of non-caret characters. */
    @Getter @Setter @NonNull private Color foregroundColor;
    /** The background color of non-caret characters. */
    @Getter @Setter @NonNull private Color backgroundColor;

    /** Whether or not the HOME key can be used to move the caret to the first index of the field. */
    @Getter @Setter private boolean homeKeyEnabled;
    /** Whether or not the END key can be used to move the caret to the last index of the field. */
    @Getter @Setter private boolean endKeyEnabled;
    /** Whether or not the DELETE key can be used to erase the character that the caret is on. */
    @Getter @Setter private boolean deleteKeyEnabled;
    /** Whether or not the LEFT ARROW key can be used to move the caret one index to the left. */
    @Getter @Setter private boolean leftArrowKeyEnabled;
    /** Whether or not the RIGHT ARROW key can be used to move the caret one index to the right. */
    @Getter @Setter private boolean rightArrowKeyEnabled;
    /** Whether or not the BACK SPACE key can be used to erase the character before the caret and move the caret backwards. */
    @Getter @Setter private boolean backSpaceKeyEnabled;

    /** The pattern used to determine which typed characters can be entered into the field. */
    @Getter @Setter @NonNull private Pattern allowedCharacterPattern;

    @Override
    public TextField build() {
        checkState();
        return new TextField(this);
    }

    /**
     * Checks the current state of the builder.
     *
     * @throws IllegalArgumentException
     *          If the width or max characters is less than one.
     */
    protected void checkState() throws NullPointerException {
        super.checkState();

        if (width < 1) {
            throw new IllegalArgumentException("The width cannot be less than one.");
        }

        if (maxCharacters < 1) {
            throw new IllegalArgumentException("The maximum characters cannot be less than one.");
        }

        if (maxCharacters < width) {
            maxCharacters = width;
        }
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        width = 4;
        maxCharacters = 4;

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
}
