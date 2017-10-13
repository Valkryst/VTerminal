package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.TextField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.Color;
import java.util.regex.Pattern;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class TextFieldBuilder extends ComponentBuilder<TextField> {
    /** The maximum number of characters that the text field can contain. */
    private int maxCharacters;

    /** The foreground color of the caret. */
    @NonNull private Color caretForegroundColor;
    /** The background color of the caret. */
    @NonNull private Color caretBackgroundColor;

    /** The foreground color of non-caret characters. */
    @NonNull private Color foregroundColor;
    /** The background color of non-caret characters. */
    @NonNull private Color backgroundColor;

    /** Whether or not the field can be edited. */
    private boolean editable;

    /**
     * Whether or not the HOME key can be used to move the caret to the first
     * index of the field.
     */
    private boolean homeKeyEnabled;
    /**
     * Whether or not the END key can be used to move the caret to the last index
     * of the field.
     */
    private boolean endKeyEnabled;
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
     * Whether or not the BACK SPACE key can be used to erase the character
     * before the caret and move the caret backwards.
     */
    private boolean backSpaceKeyEnabled;

    /** The pattern used to determine which typed characters can be entered into
     * the field.
     */
    @NonNull private Pattern allowedCharacterPattern;

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

        super.width = 4;
        maxCharacters = 4;

        caretForegroundColor = new Color(0xFF8E999E, true);
        caretBackgroundColor = new Color(0xFF68D0FF, true);

        foregroundColor = caretBackgroundColor;
        backgroundColor = caretForegroundColor;

        editable = true;

        homeKeyEnabled = true;
        endKeyEnabled = true;
        deleteKeyEnabled = true;
        leftArrowKeyEnabled = true;
        rightArrowKeyEnabled = true;
        backSpaceKeyEnabled = true;

        allowedCharacterPattern = Pattern.compile("^[a-zA-z0-9$-/:-?{-~!\"^_`\\[\\]@# ]$");
    }

    @Override
    public void parse(final @NonNull JSONObject jsonObject) {
        reset();
        super.parse(jsonObject);

        final Integer maxCharacters = getInteger(jsonObject, "maxCharacters");

        final Color caretForegroundColor = loadColorFromJSON((JSONArray) jsonObject.get("caretForegroundColor"));
        final Color caretBackgroundColor = loadColorFromJSON((JSONArray) jsonObject.get("caretBackgroundColor"));

        final Color foregroundColor = loadColorFromJSON((JSONArray) jsonObject.get("foregroundColor"));
        final Color backgroundColor = loadColorFromJSON((JSONArray) jsonObject.get("backgroundColor"));

        final Boolean editable = (Boolean) jsonObject.get("editable");

        final Boolean homeKeyEnabled = (Boolean) jsonObject.get("homeKeyEnabled");
        final Boolean endKeyEnabled = (Boolean) jsonObject.get("endKeyEnabled");
        final Boolean deleteKeyEnabled = (Boolean) jsonObject.get("deleteKeyEnabled");
        final Boolean leftArrowKeyEnabled = (Boolean) jsonObject.get("leftArrowKeyEnabled");
        final Boolean rightArrowKeyEnabled = (Boolean) jsonObject.get("rightArrowKeyEnabled");
        final Boolean backSpaceKeyEnabled = (Boolean) jsonObject.get("backSpaceKeyEnabled");

        final String allowedCharacterPattern = (String) jsonObject.get("allowedCharacterPattern");


        if (maxCharacters != null) {
            this.maxCharacters = maxCharacters;
        }


        if (caretForegroundColor != null) {
            this.caretForegroundColor = caretForegroundColor;
        }

        if (caretBackgroundColor != null) {
            this.caretBackgroundColor = caretBackgroundColor;
        }


        if (foregroundColor != null) {
            this.foregroundColor = foregroundColor;
        }


        if (editable != null) {
            this.editable = editable;
        }


        if (homeKeyEnabled != null) {
            this.homeKeyEnabled = homeKeyEnabled;
        }

        if (endKeyEnabled != null) {
            this.endKeyEnabled = endKeyEnabled;
        }

        if (deleteKeyEnabled != null) {
            this.deleteKeyEnabled = deleteKeyEnabled;
        }

        if (leftArrowKeyEnabled != null) {
            this.leftArrowKeyEnabled = leftArrowKeyEnabled;
        }

        if (rightArrowKeyEnabled != null) {
            this.rightArrowKeyEnabled = rightArrowKeyEnabled;
        }

        if (backSpaceKeyEnabled != null) {
            this.backgroundColor = backgroundColor;
        }


        if (allowedCharacterPattern != null) {
            this.allowedCharacterPattern = Pattern.compile(allowedCharacterPattern);
        }
    }
}
