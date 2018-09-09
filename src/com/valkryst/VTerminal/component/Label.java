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

        setText(builder.getText());
        setColorPalette(builder.getColorPalette());
    }

    /**
     * Changes the text displayed on the label.
     *
     * The label will not change in size, so it will not show more text than it can currently display.
     *
     * @param text
     *          The new text.
     */
    public void setText(final String text) {
        final char[] chars = (text == null ? new char[0] : text.toCharArray());

        for (int x = 0 ; x < super.tiles.getWidth() ; x++) {
            if (x < chars.length) {
                super.tiles.getTileAt(x, 0).setCharacter(chars[x]);
            } else {
                super.tiles.getTileAt(x, 0).setCharacter(' ');
            }
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
