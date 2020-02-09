package com.valkryst.VTerminal.builder;

import com.valkryst.VTerminal.component.TextArea;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.json.JSONObject;

import java.util.regex.Pattern;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class TextAreaBuilder extends ComponentBuilder<TextArea> {
    private final static Pattern DEFAULT_ALLOWED_CHARACTER_PATTERN = Pattern.compile("^[a-zA-z0-9$-/:-?{-~!\"^_`\\[\\]@# ]$");

    /** Whether or not the TextArea can be edited. */
    private boolean editable;

    /** The pattern used to determine which typed characters can be entered into the field. */
    private Pattern allowedCharacterPattern;

    /** Whether the special listener code for the Enter key is enabled. */
    private boolean isEnterKeyEnabled = true;

    /** Whether the special listener code for the Backspace key is enabled. */
    private boolean isBackspaceKeyEnabled = true;

    /** Whether the special listener code for the Delete key is enabled. */
    private boolean isDeleteKeyEnabled = true;

    /** Whether the special listener code for the Home key is enabled. */
    private boolean isHomeKeyEnabled = true;

    /** Whether the special listener code for the End key is enabled. */
    private boolean isEndKeyEnabled = true;

    /** Whether the special listener code for the Page Up key is enabled. */
    private boolean isPageUpKeyEnabled = true;

    /** Whether the special listener code for the Page Down key is enabled. */
    private boolean isPageDownKeyEnabled = true;

    /** Whether the special listener code for the Up Arrow key is enabled. */
    private boolean isUpArrowKeyEnabled = true;

    /** Whether the special listener code for the Down Arrow key is enabled. */
    private boolean isDownArrowKeyEnabled = true;

    /** Whether the special listener code for the Left Arrow key is enabled. */
    private boolean isLeftArrowKeyEnabled = true;

    /** Whether the special listener code for the Right Arrow key is enabled. */
    private boolean isRightArrowKeyEnabled = true;

    /**
     * Constructs a new TextAreaBuilder and initializes it using the JSON representation of a text area
     * component.
     *
     * @param json
     *          The JSON representation of a text area component.
     */
    public TextAreaBuilder(final JSONObject json) {
        super(json);

        if (json == null) {
            return;
        }

        final Boolean editable = json.getBoolean("Editable");
        final Boolean isEnterKeyEnabled = json.getBoolean("Enter Key Enabled");
        final Boolean isBackspaceKeyEnabled = json.getBoolean("Backspace Key Enabled");
        final Boolean isDeleteKeyEnabled = json.getBoolean("Delete Key Enabled");
        final Boolean isHomeKeyEnabled = json.getBoolean("Home Key Enabled");
        final Boolean isEndKeyEnabled = json.getBoolean("End Key Enabled");
        final Boolean isPageUpKeyEnabled = json.getBoolean("Page Up Key Enabled");
        final Boolean isPageDownKeyEnabled = json.getBoolean("Page Down Key Enabled");
        final Boolean isUpArrowKeyEnabled = json.getBoolean("Up Arrow Key Enabled");
        final Boolean isDownArrowKeyEnabled = json.getBoolean("Down Arrow Key Enabled");
        final Boolean isLeftArrowKeyEnabled = json.getBoolean("Left Arrow Key Enabled");
        final Boolean isRightArrowKeyEnabled = json.getBoolean("Right Arrow Key Enabled");

        this.editable = (editable == null ? true : editable);
        this.isEnterKeyEnabled = (isEnterKeyEnabled == null ? true : isEnterKeyEnabled);
        this.isBackspaceKeyEnabled = (isBackspaceKeyEnabled == null ? true : isBackspaceKeyEnabled);
        this.isDeleteKeyEnabled = (isDeleteKeyEnabled == null ? true : isDeleteKeyEnabled);
        this.isHomeKeyEnabled = (isHomeKeyEnabled == null ? true : isHomeKeyEnabled);
        this.isEndKeyEnabled = (isEndKeyEnabled == null ? true : isEndKeyEnabled);
        this.isPageUpKeyEnabled = (isPageUpKeyEnabled == null ? true : isPageUpKeyEnabled);
        this.isPageDownKeyEnabled = (isPageDownKeyEnabled == null ? true : isPageDownKeyEnabled);
        this.isUpArrowKeyEnabled = (isUpArrowKeyEnabled == null ? true : isUpArrowKeyEnabled);
        this.isDownArrowKeyEnabled = (isDownArrowKeyEnabled == null ? true : isDownArrowKeyEnabled);
        this.isLeftArrowKeyEnabled = (isLeftArrowKeyEnabled == null ? true : isLeftArrowKeyEnabled);
        this.isRightArrowKeyEnabled = (isRightArrowKeyEnabled == null ? true : isRightArrowKeyEnabled);
    }

    @Override
    public TextArea build() {
        checkState();
        return new TextArea(this);
    }

    @Override
    protected void checkState() {
        super.checkState();

        if (allowedCharacterPattern == null) {
            allowedCharacterPattern = DEFAULT_ALLOWED_CHARACTER_PATTERN;
        }
    }

    @Override
    public void reset() {
        super.reset();

        super.setDimensions(4, 4);

        editable = true;

        allowedCharacterPattern = DEFAULT_ALLOWED_CHARACTER_PATTERN;

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
}