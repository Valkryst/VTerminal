package com.valkryst.VTerminal.builder;

import com.valkryst.VTerminal.component.TextArea;
import lombok.*;
import org.json.simple.JSONObject;

import java.util.regex.Pattern;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class TextAreaBuilder extends ComponentBuilder<TextArea> {
    /** Whether or not the TextArea can be edited. */
    private boolean editable;

    /**
     * The pattern used to determine which typed characters can be entered into
     * the field.
     */
    @NonNull private Pattern allowedCharacterPattern;

    /** Whether the special listener code for the Enter key is enabled. */
    @Getter @Setter private boolean isEnterKeyEnabled = true;

    /** Whether the special listener code for the Backspace key is enabled. */
    @Getter @Setter private boolean isBackspaceKeyEnabled = true;

    /** Whether the special listener code for the Delete key is enabled. */
    @Getter @Setter private boolean isDeleteKeyEnabled = true;

    /** Whether the special listener code for the Home key is enabled. */
    @Getter @Setter private boolean isHomeKeyEnabled = true;

    /** Whether the special listener code for the End key is enabled. */
    @Getter @Setter private boolean isEndKeyEnabled = true;

    /** Whether the special listener code for the Page Up key is enabled. */
    @Getter @Setter private boolean isPageUpKeyEnabled = true;

    /** Whether the special listener code for the Page Down key is enabled. */
    @Getter @Setter private boolean isPageDownKeyEnabled = true;

    /** Whether the special listener code for the Up Arrow key is enabled. */
    @Getter @Setter private boolean isUpArrowKeyEnabled = true;

    /** Whether the special listener code for the Down Arrow key is enabled. */
    @Getter @Setter private boolean isDownArrowKeyEnabled = true;

    /** Whether the special listener code for the Left Arrow key is enabled. */
    @Getter @Setter private boolean isLeftArrowKeyEnabled = true;

    /** Whether the special listener code for the Right Arrow key is enabled. */
    @Getter @Setter private boolean isRightArrowKeyEnabled = true;

    @Override
    public TextArea build() {
        checkState();
        return new TextArea(this);
    }

    @Override
    public void reset() {
        super.reset();

        super.setDimensions(4, 4);

        editable = true;

        allowedCharacterPattern = Pattern.compile("^[a-zA-z0-9$-/:-?{-~!\"^_`\\[\\]@# ]$");

        isEnterKeyEnabled = true;
        isBackspaceKeyEnabled = true;
        isDeleteKeyEnabled = true;
        isHomeKeyEnabled = true;
        isEndKeyEnabled = true;
        isPageUpKeyEnabled = true;
        isPageDownKeyEnabled = true;
        isUpArrowKeyEnabled = true;
        isDownArrowKeyEnabled = true;
        isLeftArrowKeyEnabled = true;
        isRightArrowKeyEnabled = true;
    }

    @Override
    public void parse(final  @NonNull JSONObject jsonObject) {
        reset();
        super.parse(jsonObject);

        final Boolean editable = (Boolean) jsonObject.get("editable");

        final String allowedCharacterPattern = (String) jsonObject.get("allowedCharacterPattern");


        if (editable != null) {
            this.editable = editable;
        }


        if (allowedCharacterPattern != null) {
            this.allowedCharacterPattern = Pattern.compile(allowedCharacterPattern);
        }
    }
}