package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.builder.component.ProgressBarBuilder;
import com.valkryst.VTerminal.misc.IntRange;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.awt.Color;

@EqualsAndHashCode(callSuper=true)
@ToString
public class ProgressBar extends Component {
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
    public ProgressBar(final @NonNull ProgressBarBuilder builder) {
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
            string.setBackgroundColor(backgroundColor_incomplete);
            string.setForegroundColor(foregroundColor_incomplete);
        }
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
