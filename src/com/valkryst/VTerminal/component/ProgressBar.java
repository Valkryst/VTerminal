package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.builder.ProgressBarBuilder;
import com.valkryst.VTerminal.palette.ColorPalette;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.awt.*;

@ToString
public class ProgressBar extends Component {
    /** The percent complete. */
    @Getter private int percentComplete = 0;

    /** The character that represents an incomplete cell. */
    @Getter private char incompleteCharacter;
    /** The character that represents a complete cell. */
    @Getter private char completeCharacter;

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
        super(builder.getDimensions(), builder.getPosition());

        colorPalette = builder.getColorPalette();

        incompleteCharacter = builder.getIncompleteCharacter();
        completeCharacter = builder.getCompleteCharacter();

        // Set all chars to incomplete state:
        for (final Tile tile : super.tiles.getRow(0)) {
            tile.setCharacter(incompleteCharacter);
            tile.setBackgroundColor(colorPalette.getProgressBar_incompleteBackground());
            tile.setForegroundColor(colorPalette.getProgressBar_incompleteForeground());
        }
    }

    @Override
    public void setColorPalette(final ColorPalette colorPalette, final boolean redraw) {
        if (colorPalette == null) {
            return;
        }

        this.colorPalette = colorPalette;

        // Determine the colors to color the tiles with.
        final Color backgroundColor;
        final Color foregroundColor;

        if (percentComplete < 100) {
            backgroundColor = colorPalette.getProgressBar_incompleteBackground();
            foregroundColor = colorPalette.getProgressBar_incompleteForeground();
        } else {
            backgroundColor = colorPalette.getProgressBar_completeBackground();
            foregroundColor = colorPalette.getProgressBar_completeForeground();
        }


        // Color All Tiles
        for (int y = 0 ; y < tiles.getHeight() ; y++) {
            for (int x = 0 ; x < tiles.getWidth() ; x++) {
                final Tile tile = tiles.getTileAt(x, y);
                tile.setBackgroundColor(backgroundColor);
                tile.setForegroundColor(foregroundColor);
            }
        }

        if (redraw) {
            redrawFunction.run();
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

        final int numberOfCompleteChars = (int) (super.tiles.getWidth() * (percentComplete / 100f));
        Tile[] tileRow;

        for (int y = 0 ; y < super.tiles.getHeight() ; y++) {
            tileRow = super.tiles.getRow(y);

            for (int x = 0 ; x < super.tiles.getWidth() ; x++) {
                if (x <= numberOfCompleteChars) {
                    tileRow[x].setCharacter(completeCharacter);
                    tileRow[x].setBackgroundColor(colorPalette.getProgressBar_completeBackground());
                    tileRow[x].setForegroundColor(colorPalette.getProgressBar_completeForeground());
                } else {
                    tileRow[x].setCharacter(incompleteCharacter);
                    tileRow[x].setBackgroundColor(colorPalette.getProgressBar_incompleteBackground());
                    tileRow[x].setForegroundColor(colorPalette.getProgressBar_incompleteForeground());
                }
            }
        }

        this.percentComplete = percentComplete;
        super.redrawFunction.run();
    }
}
