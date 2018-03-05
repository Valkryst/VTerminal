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

    @Override
    public void reset() {
        super.reset();

        super.setDimensions(4, 4);

        editable = true;

        allowedCharacterPattern = Pattern.compile("^[a-zA-z0-9$-/:-?{-~!\"^_`\\[\\]@# ]$");
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