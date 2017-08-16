package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.ProgressBar;
import lombok.*;

import java.awt.Color;

@EqualsAndHashCode(callSuper=true)
@ToString
public class ProgressBarBuilder extends ComponentBuilder<ProgressBar> {
    /** The width of the loading bar, in characters. */
    @Getter @Setter private int width;
    /** The height of the loading bar, in characters. */
    @Getter @Setter private int height;

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

        width = 10;
        height = 1;

        incompleteCharacter = '█';
        completeCharacter = '█';

        backgroundColor_incomplete = new Color(45, 45, 45, 255);
        foregroundColor_incomplete = new Color(0xFFFF2D55, true);

        backgroundColor_complete = backgroundColor_incomplete;
        foregroundColor_complete = new Color(0xFF2DFF6E, true);
    }
}
