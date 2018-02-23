package com.valkryst.VTerminal.builder;

import com.valkryst.VTerminal.component.TextArea;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.regex.Pattern;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class TextAreaBuilder extends ComponentBuilder<TextArea> {
    /**
     * The maximum number of characters that the field can contain along the
     * x-axis.
     */
    private int maxHorizontalCharacters;
    /**
     * The maximum number of characters that the field can contain along the
     * y-axis.
     */
    private int maxVerticalCharacters;

    /** Whether or not the TextArea can be edited. */
    private boolean editable;

    /**
     * Whether or not the HOME key can be used to move the caret to the first
     * index of the current line.
     */
    private boolean homeKeyEnabled;
    /**
     * Whether or not the END key can be used to move the caret to the last index
     * of the current line.
     */
    private boolean endKeyEnabled;
    /**
     * Whether or not the PAGE UP key can be used to move the caret to the first
     * row.
     */
    private boolean pageUpKeyEnabled;
    /**
     * Whether or nor the PAGE DOWN key can be used to move the caret to the last
     * row.
     */
    private boolean pageDownKeyEnabled;
    /**
     * Whether or not the DELETE key can be used to erase the character that the
     * caret is on.
     */
    private boolean deleteKeyEnabled;
    /**
     * Whether or not the LEFT ARROW key can be used to move the caret one index
     * to the left.
     */
    private boolean leftArrowKeyEnabled;
    /**
     * Whether or not the RIGHT ARROW key can be used to move the caret one index
     * to the right.
     */
    private boolean rightArrowKeyEnabled;
    /**
     * Whether or not the UP ARROW key can be used to move the caret one index
     * up.
     */
    private boolean upArrowKeyEnabled;
    /**
     * Whether or not the DOWN ARROW key can be used to move the caret one index
     * up.
     */
    private boolean downArrowKeyEnabled;
    /**
     * Whether or not the ENTER key can be used to advance the caret to the first
     * position of the next line.
     */
    private boolean enterKeyEnabled;
    /**
     * Whether or not the BACK SPACE key can be used to erase the character before
     * the caret and move the caret backwards.
     */
    private boolean backSpaceKeyEnabled;
    /**
     * Whether or not the TAB key can be used to indent by some number of spaces.
     */
    private boolean tabKeyEnabled;

    /** The amount of spaces to insert when the TAB key is pressed. */
    private int tabSize;

    /**
     * The pattern used to determine which typed characters can be entered into
     * the field.
     */
    @NonNull private Pattern allowedCharacterPattern;

    @Override
    public TextArea build() {
        checkState();
        return new TextArea(this);
    }

    /**
     * Checks the current state of the builder.
     *
     * @throws IllegalArgumentException
     *          If the width, height, or maximum horizontal/vertical characters
     *          is less than one.
     */
    protected void checkState() throws NullPointerException {
        super.checkState();

        if (maxHorizontalCharacters < 1) {
            throw new IllegalArgumentException("The maximum horizontal characters cannot be less than one.");
        }

        if (maxVerticalCharacters < 1) {
            throw new IllegalArgumentException("The maximum vertical characters cannot be less than one.");
        }

        if (tabSize < 1) {
            throw new IllegalArgumentException("The tab size cannot be less than one character.");
        }

        if (maxHorizontalCharacters < super.getDimensions().width) {
            maxHorizontalCharacters = super.getDimensions().width;
        }

        if (maxVerticalCharacters < super.getDimensions().height) {
            maxVerticalCharacters = super.getDimensions().height;
        }
    }

    @Override
    public void reset() {
        super.reset();

        super.getDimensions().setSize(4, 4);
        maxHorizontalCharacters = 4;
        maxVerticalCharacters = 4;

        editable = true;

        homeKeyEnabled = true;
        endKeyEnabled = true;
        pageUpKeyEnabled = true;
        pageDownKeyEnabled = true;
        deleteKeyEnabled = true;
        leftArrowKeyEnabled = true;
        rightArrowKeyEnabled = true;
        upArrowKeyEnabled = true;
        downArrowKeyEnabled = true;
        enterKeyEnabled = true;
        backSpaceKeyEnabled = true;
        tabKeyEnabled = true;

        tabSize = 4;

        allowedCharacterPattern = Pattern.compile("^[a-zA-z0-9$-/:-?{-~!\"^_`\\[\\]@# ]$");
    }
}
