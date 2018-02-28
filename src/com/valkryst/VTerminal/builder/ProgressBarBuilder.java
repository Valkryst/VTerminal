package com.valkryst.VTerminal.builder;

import com.valkryst.VJSON.VJSONParser;
import com.valkryst.VTerminal.component.ProgressBar;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.json.simple.JSONObject;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class ProgressBarBuilder extends ComponentBuilder<ProgressBar> implements VJSONParser {
    /** The character that represents an incomplete cell. */
    private char incompleteCharacter;
    /** The character that represents a complete cell. */
    private char completeCharacter;

    @Override
    public ProgressBar build() {
        checkState();
        return new ProgressBar(this);
    }

    @Override
    public void reset() {
        super.reset();

        super.setDimensions(10, 1);

        incompleteCharacter = '█';
        completeCharacter = '█';
    }

    @Override
    public void parse(final @NonNull JSONObject jsonObject) {
        super.parse(jsonObject);

        final Character incompleteCharacter = getChar(jsonObject, "incompleteCharacter");
        final Character completeCharacter = getChar(jsonObject, "completeCharacter");

        if (incompleteCharacter == null) {
            throw new NullPointerException("The 'incompleteCharacter' value was not found.");
        } else {
            this.incompleteCharacter = incompleteCharacter;
        }

        if (completeCharacter == null) {
            throw new NullPointerException("The 'completeCharacter' value was not found.");
        } else {
            this.completeCharacter = completeCharacter;
        }
    }
}
