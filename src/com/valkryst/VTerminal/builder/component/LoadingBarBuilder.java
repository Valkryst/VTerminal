package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.LoadingBar;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.awt.Color;

public class LoadingBarBuilder extends ComponentBuilder<LoadingBar> {
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
    public LoadingBar build() {
        checkState();

        final LoadingBar loadingBar = new LoadingBar(this);
        super.getPanel().addComponent(loadingBar);

        return loadingBar;
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        width = 10;
        height = 1;

        incompleteCharacter = '█';
        completeCharacter = '█';

        backgroundColor_incomplete = new Color(0xFF366C9F, true);
        foregroundColor_incomplete = new Color(255, 0, 0, 255);

        backgroundColor_complete = new Color(0xFF366C9F, true);
        foregroundColor_complete = new Color(0, 255, 0, 255);
    }
}
