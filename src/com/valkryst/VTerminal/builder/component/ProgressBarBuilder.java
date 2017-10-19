package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.ProgressBar;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.Color;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class ProgressBarBuilder extends ComponentBuilder<ProgressBar> {
    /** The character that represents an incomplete cell. */
    private char incompleteCharacter;
    /** The character that represents a complete cell. */
    private char completeCharacter;

    /** The background color for incomplete cells. */
    @NonNull private Color backgroundColor_incomplete;
    /** The foreground color for incomplete cells. */
    @NonNull private Color foregroundColor_incomplete;

    /** The background color for complete cells. */
    @NonNull private Color backgroundColor_complete;
    /** The foreground color for complete cells. */
    @NonNull private Color foregroundColor_complete;

    @Override
    public ProgressBar build() {
        checkState();
        return new ProgressBar(this);
    }

    @Override
    public void reset() {
        super.reset();

        super.width = 10;
        super.height = 1;

        incompleteCharacter = '█';
        completeCharacter = '█';

        backgroundColor_incomplete = new Color(45, 45, 45, 255);
        foregroundColor_incomplete = new Color(0xFFFF2D55, true);

        backgroundColor_complete = backgroundColor_incomplete;
        foregroundColor_complete = new Color(0xFF2DFF6E, true);
    }

    @Override
    public void parse(final @NonNull JSONObject jsonObject) {
        reset();
        super.parse(jsonObject);


        final Character incompleteCharacter = (Character) jsonObject.get("incompleteCharacter");
        final Character completeCharacter = (Character) jsonObject.get("completeCharacter");


        if (incompleteCharacter != null) {
            this.incompleteCharacter = incompleteCharacter;
        }

        if (completeCharacter != null) {
            this.completeCharacter = completeCharacter;
        }


        try {
            this.backgroundColor_incomplete = getColor((JSONArray) jsonObject.get("backgroundColor_incomplete"));
        } catch (final NullPointerException ignored) {}

        try {
            this.foregroundColor_incomplete = getColor((JSONArray) jsonObject.get("foregroundColor_incomplete"));
        } catch (final NullPointerException ignored) {}


        try {
            this.backgroundColor_complete = getColor((JSONArray) jsonObject.get("backgroundColor_complete"));
        } catch (final NullPointerException ignored) {}

        try {
            this.foregroundColor_complete = getColor((JSONArray) jsonObject.get("foregroundColor_complete"));
        } catch (final NullPointerException ignored) {}
    }
}
