package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.builder.ProgressBarBuilder;
import com.valkryst.VTerminal.palette.java2d.Java2DPalette;
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

        setPalette(builder.getPalette(), false);

        incompleteCharacter = builder.getIncompleteCharacter();
        completeCharacter = builder.getCompleteCharacter();

        // Set all chars to incomplete state:
        for (final Tile tile : super.tiles.getRow(0)) {
            tile.setCharacter(incompleteCharacter);
            tile.setBackgroundColor(palette.getProgressBarPalette().getBackgroundIncomplete());
            tile.setForegroundColor(palette.getProgressBarPalette().getForegroundIncomplete());
        }
    }

    @Override
    public void setPalette(final Java2DPalette palette, final boolean redraw) {
        if (palette == null) {
            return;
        }

        this.palette = palette;

        // Determine the colors to color the tiles with.
        final Color backgroundColor;
        final Color foregroundColor;

        if (percentComplete < 100) {
            backgroundColor = palette.getProgressBarPalette().getBackgroundIncomplete();
            foregroundColor = palette.getProgressBarPalette().getForegroundIncomplete();
        } else {
            backgroundColor = palette.getProgressBarPalette().getBackground();
            foregroundColor = palette.getProgressBarPalette().getForeground();
        }


        // Color All Tiles
        for (int y = 0 ; y < tiles.getHeight() ; y++) {
            for (int x = 0 ; x < tiles.getWidth() ; x++) {
                final Tile tile = tiles.getTileAt(x, y);

                if (tile != null) {
                    tile.setBackgroundColor(backgroundColor);
                    tile.setForegroundColor(foregroundColor);
                }
            }
        }

        if (redraw) {
            try {
                redrawFunction.run();
            } catch (final IllegalStateException ignored) {
                /*
                 * If we set the color palette before the screen is displayed, then it'll throw...
                 *
                 *      IllegalStateException: Component must have a valid peer
                 *
                 * We can just ignore it in this case, because the screen will be drawn when it is displayed for
                 * the first time.
                 */
            }
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
                    tileRow[x].setBackgroundColor(palette.getProgressBarPalette().getBackground());
                    tileRow[x].setForegroundColor(palette.getProgressBarPalette().getForeground());
                } else {
                    tileRow[x].setCharacter(incompleteCharacter);
                    tileRow[x].setBackgroundColor(palette.getProgressBarPalette().getBackgroundIncomplete());
                    tileRow[x].setForegroundColor(palette.getProgressBarPalette().getForegroundIncomplete());
                }
            }
        }

        this.percentComplete = percentComplete;
        super.redrawFunction.run();
    }
}
