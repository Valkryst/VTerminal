package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.builder.component.LoadingBarBuilder;
import com.valkryst.VTerminal.misc.IntRange;
import lombok.Getter;

import java.awt.Color;
import java.util.Objects;

public class LoadingBar extends Component {
    /** The percent complete. */
    @Getter private int percentComplete = 0;

    /** The character that represents an incomplete cell. */
    @Getter private char incompleteCharacter;
    /** The character that represents a complete cell. */
    @Getter private char completeCharacter;

    /** The background color for incomplete cells. */
    @Getter private Color backgroundColor_incomplete;
    /** The foreground color for incomplete cells. */
    @Getter private Color foregroundColor_incomplete;

    /** The background color for complete cells. */
    @Getter private Color backgroundColor_complete;
    /** The foreground color for complete cells. */
    @Getter private Color foregroundColor_complete;

    /**
     * Constructs a new LoadingBar.
     *
     * @param builder
     *        The builder to use.
     *
     * @throws NullPointerException
     *         If the builder is null.
     */
    public LoadingBar(final LoadingBarBuilder builder) {
        super(builder.getColumnIndex(), builder.getRowIndex(), builder.getWidth(), builder.getHeight());

        Objects.requireNonNull(builder);

        super.setRadio(builder.getRadio());

        incompleteCharacter = builder.getIncompleteCharacter();
        completeCharacter = builder.getCompleteCharacter();

        backgroundColor_incomplete = builder.getBackgroundColor_incomplete();
        foregroundColor_incomplete = builder.getForegroundColor_incomplete();

        backgroundColor_complete = builder.getBackgroundColor_complete();
        foregroundColor_complete = builder.getForegroundColor_complete();

        // Set all chars to incomplete state:
        for (final AsciiString string : super.getStrings()) {
            string.setAllCharacters(incompleteCharacter);
            string.setBackgroundColor(backgroundColor_incomplete);
            string.setForegroundColor(foregroundColor_incomplete);
        }
    }

    @Override
    public boolean equals(final Object otherObj) {
        if (otherObj instanceof LoadingBar == false) {
            return false;
        }

        if (otherObj == this) {
            return true;
        }

        final LoadingBar otherBar = (LoadingBar) otherObj;
        boolean isEqual = super.equals(otherObj);
        isEqual &= Objects.equals(percentComplete, otherBar.getPercentComplete());
        isEqual &= Objects.equals(incompleteCharacter, otherBar.getIncompleteCharacter());
        isEqual &= Objects.equals(completeCharacter, otherBar.getCompleteCharacter());
        isEqual &= Objects.equals(backgroundColor_incomplete, otherBar.getBackgroundColor_incomplete());
        isEqual &= Objects.equals(foregroundColor_incomplete, otherBar.getForegroundColor_incomplete());
        isEqual &= Objects.equals(backgroundColor_complete, otherBar.getBackgroundColor_complete());
        isEqual &= Objects.equals(foregroundColor_complete, otherBar.getForegroundColor_complete());
        return isEqual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), percentComplete, incompleteCharacter, completeCharacter,
                            backgroundColor_incomplete, foregroundColor_incomplete, backgroundColor_complete,
                            foregroundColor_complete);
    }

    /**
     * Sets the new percent complete and redraws the loading bar
     * to reflect the changes.
     *
     * @param percentComplete
     *        The new percent complete.
     */
    public void setPercentComplete(int percentComplete) {
        if (percentComplete < 0) {
            percentComplete = 0;
        }

        if (percentComplete > 100) {
            percentComplete = 100;
        }

        final int numberOfCompleteChars = (int) (super.getWidth() * (percentComplete / 100f));
        final IntRange range = new IntRange(0, numberOfCompleteChars);

        for (final AsciiString string : super.getStrings()) {
            string.setBackgroundColor(backgroundColor_incomplete);
            string.setForegroundColor(foregroundColor_incomplete);
            string.setAllCharacters(incompleteCharacter);

            string.setBackgroundColor(backgroundColor_complete, range);
            string.setForegroundColor(foregroundColor_complete, range);
            string.setCharacters(completeCharacter, range);
        }

        this.percentComplete = percentComplete;
        super.transmitDraw();
    }
}
