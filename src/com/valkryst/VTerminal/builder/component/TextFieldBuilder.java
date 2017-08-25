package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.TextField;
import com.valkryst.VTerminal.misc.JSONFunctions;
import lombok.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.Color;
import java.util.regex.Pattern;

@EqualsAndHashCode(callSuper=true)
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

    /** Whether or not the field can be edited. */
    @Getter @Setter private boolean editable;

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
    public void parseJSON(final JSONObject jsonObject) {
        reset();
        super.parseJSON(jsonObject);


        final Integer width = JSONFunctions.getIntElement(jsonObject, "width");
        final Integer maxCharacters = JSONFunctions.getIntElement(jsonObject, "maxCharacters");

        final Color caretForegroundColor = super.loadColorFromJSON((JSONArray) jsonObject.get("caretForegroundColor"));
        final Color caretBackgroundColor = super.loadColorFromJSON((JSONArray) jsonObject.get("caretBackgroundColor"));

        final Color foregroundColor = super.loadColorFromJSON((JSONArray) jsonObject.get("foregroundColor"));
        final Color backgroundColor = super.loadColorFromJSON((JSONArray) jsonObject.get("backgroundColor"));

        final Boolean editable = (Boolean) jsonObject.get("editable");

        final Boolean homeKeyEnabled = (Boolean) jsonObject.get("homeKeyEnabled");
        final Boolean endKeyEnabled = (Boolean) jsonObject.get("endKeyEnabled");
        final Boolean deleteKeyEnabled = (Boolean) jsonObject.get("deleteKeyEnabled");
        final Boolean leftArrowKeyEnabled = (Boolean) jsonObject.get("leftArrowKeyEnabled");
        final Boolean rightArrowKeyEnabled = (Boolean) jsonObject.get("rightArrowKeyEnabled");
        final Boolean backSpaceKeyEnabled = (Boolean) jsonObject.get("backSpaceKeyEnabled");

        final String allowedCharacterPattern = (String) jsonObject.get("allowedCharacterPattern");


        if (width != null) {
            this.width = width;
        }

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
