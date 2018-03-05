package com.valkryst.VTerminal.builder;

import com.valkryst.VTerminal.component.TextArea;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.json.simple.JSONObject;

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

        if (maxHorizontalCharacters < super.getWidth()) {
            maxHorizontalCharacters = super.getWidth();
        }

        if (maxVerticalCharacters < super.getHeight()) {
            maxVerticalCharacters = super.getHeight();
        }
    }

    @Override
    public void reset() {
        super.reset();

        super.setDimensions(4, 4);
        maxHorizontalCharacters = 4;
        maxVerticalCharacters = 4;

        editable = true;

        allowedCharacterPattern = Pattern.compile("^[a-zA-z0-9$-/:-?{-~!\"^_`\\[\\]@# ]$");
    }

    @Override
    public void parse(final  @NonNull JSONObject jsonObject) {
        reset();
        super.parse(jsonObject);

        final Integer maxHorizontalCharacters = getInteger(jsonObject, "maxHorizontalCharacters");
        final Integer maxVerticalCharacters = getInteger(jsonObject, "maxVerticalCharacters");

        final Boolean editable = (Boolean) jsonObject.get("editable");

        final String allowedCharacterPattern = (String) jsonObject.get("allowedCharacterPattern");


        if (maxHorizontalCharacters != null) {
            this.maxHorizontalCharacters = maxHorizontalCharacters;
        }

        if (maxVerticalCharacters != null) {
            this.maxVerticalCharacters = maxVerticalCharacters;
        }



        if (editable != null) {
            this.editable = editable;
        }


        if (allowedCharacterPattern != null) {
            this.allowedCharacterPattern = Pattern.compile(allowedCharacterPattern);
        }
    }
}