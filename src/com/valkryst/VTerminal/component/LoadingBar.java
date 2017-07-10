package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.builder.component.LoadingBarBuilder;
import com.valkryst.VTerminal.misc.IntRange;
import lombok.Getter;

import java.awt.*;

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
     */
    public LoadingBar(final LoadingBarBuilder builder) {
        super(builder.getColumnIndex(), builder.getRowIndex(), builder.getWidth(), builder.getHeight());

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
            string.setBackgroundAndForegroundColor(backgroundColor_incomplete, foregroundColor_incomplete);
        }
    }

    /**
     * Sets the new percent complete and redraws the loading bar
     * to reflect the changes.
     *
     * @param percentComplete
     *        The new percent complete.
     */
    public void setPercentComplete(final int percentComplete) {
        boolean isWithinRange = percentComplete >= 0;
        isWithinRange &= percentComplete <= 100;

        if (isWithinRange == false || this.percentComplete == percentComplete) {
            return;
        }

        final boolean increased = this.percentComplete < percentComplete;

        final int totalCompleteChars_prev = (int) (getWidth() * (percentComplete / 100f));
        final int totalCompleteChars_curr = (int) (getWidth() * (this.percentComplete / 100f));

        for (final AsciiString string : super.getStrings()) {
            IntRange range;

            if (increased) {
                range = new IntRange(totalCompleteChars_curr, totalCompleteChars_prev);
                string.setCharacters(completeCharacter, range);
            } else {
                range = new IntRange(totalCompleteChars_prev, totalCompleteChars_curr);
                string.setCharacters(incompleteCharacter, range);
            }

            string.setBackgroundColor(backgroundColor_complete, range);
            string.setForegroundColor(foregroundColor_complete, range);
        }

        this.percentComplete = percentComplete;
        super.getRadio().transmit("DRAW");
    }
}
