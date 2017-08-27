package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.ProgressBar;
import com.valkryst.VTerminal.misc.JSONFunctions;
import lombok.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.Color;

@EqualsAndHashCode(callSuper=true)
@ToString
public class ProgressBarBuilder extends ComponentBuilder<ProgressBar> {
    /** The character that represents an incomplete cell. */
    @Getter @Setter private char incompleteCharacter;
    /** The character that represents a complete cell. */
    @Getter @Setter private char completeCharacter;

    /** The background color for incomplete cells. */
    @Getter @Setter @NonNull private Color backgroundColor_incomplete;
    /** The foreground color for incomplete cells. */
    @Getter @Setter @NonNull private Color foregroundColor_incomplete;

    /** The background color for complete cells. */
    @Getter @Setter @NonNull private Color backgroundColor_complete;
    /** The foreground color for complete cells. */
    @Getter @Setter @NonNull private Color foregroundColor_complete;

    @Override
    public ProgressBar build() {
        checkState();
        return new ProgressBar(this);
    }

    /** Resets the builder to it's default state. */
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
    public void parseJSON(final @NonNull JSONObject jsonObject) {
        reset();
        super.parseJSON(jsonObject);


        final Character incompleteCharacter = (Character) jsonObject.get("incompleteCharacter");
        final Character completeCharacter = (Character) jsonObject.get("completeCharacter");

        final Color backgroundColor_incomplete = JSONFunctions.loadColorFromJSON((JSONArray) jsonObject.get("backgroundColor_incomplete"));
        final Color foregroundColor_incomplete = JSONFunctions.loadColorFromJSON((JSONArray) jsonObject.get("foregroundColor_incomplete"));

        final Color backgroundColor_complete = JSONFunctions.loadColorFromJSON((JSONArray) jsonObject.get("backgroundColor_complete"));
        final Color foregroundColor_complete = JSONFunctions.loadColorFromJSON((JSONArray) jsonObject.get("foregroundColor_complete"));


        if (incompleteCharacter != null) {
            this.incompleteCharacter = incompleteCharacter;
        }

        if (completeCharacter != null) {
            this.completeCharacter = completeCharacter;
        }



        if (backgroundColor_incomplete != null) {
            this.backgroundColor_incomplete = backgroundColor_incomplete;
        }

        if (foregroundColor_incomplete != null) {
            this.foregroundColor_incomplete = foregroundColor_incomplete;
        }



        if (backgroundColor_complete != null) {
            this.backgroundColor_complete = backgroundColor_complete;
        }

        if (foregroundColor_complete != null) {
            this.foregroundColor_complete = foregroundColor_complete;
        }
    }
}
