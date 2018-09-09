package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.builder.LabelBuilder;
import com.valkryst.VTerminal.palette.ColorPalette;
import lombok.NonNull;
import lombok.ToString;

import java.awt.*;

@ToString
public class Label extends Component {
    /**
     * Constructs a new Label.
     *
     * @param builder
     *          The builder to use.
     *
     * @throws NullPointerException
     *          If the builder is null.
     */
    public Label(final @NonNull LabelBuilder builder) {
        super(builder.getDimensions(), builder.getPosition());

        final ColorPalette colorPalette = builder.getColorPalette();

        final char[] text = builder.getText().toCharArray();
        final Tile[] tiles = super.tiles.getRow(0);

        for (int x = 0 ; x < tiles.length ; x++) {
            tiles[x].setCharacter(text[x]);

            tiles[x].setBackgroundColor(colorPalette.getLabel_defaultBackground());
            tiles[x].setForegroundColor(colorPalette.getLabel_defaultForeground());
        }
    }

    @Override
    public void setColorPalette(final ColorPalette colorPalette) {
        if (colorPalette == null) {
            return;
        }

        this.colorPalette = colorPalette;

        final Color backgroundColor = colorPalette.getLabel_defaultBackground();
        final Color foregroundColor = colorPalette.getLabel_defaultForeground();

        for (int y = 0 ; y < tiles.getHeight() ; y++) {
            for (int x = 0 ; x < tiles.getWidth() ; x++) {
                final Tile tile = tiles.getTileAt(x, y);
                tile.setBackgroundColor(backgroundColor);
                tile.setForegroundColor(foregroundColor);
            }
        }

        redrawFunction.run();
    }
}
