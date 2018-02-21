package com.valkryst.VTerminal.revamp.component.component;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.revamp.component.builder.LabelBuilder;
import com.valkryst.VTerminal.revamp.component.palette.ColorPalette;
import lombok.NonNull;
import lombok.ToString;

@ToString
public class Label extends Component {
    /**
     * Constructs a new Label.
     *
     * @param builder
     *         The builder to use.
     *
     * @throws NullPointerException
     *         If the builder is null.
     */
    public Label(final @NonNull LabelBuilder builder) {
        super(builder.getDimensions(), builder.getPosition());

        final ColorPalette colorPalette = builder.getColorPalette();

        final char[] text = builder.getText().toCharArray();
        final AsciiCharacter[] tiles = super.tiles.getRow(0);

        for (int x = 0 ; x < tiles.length ; x++) {
            tiles[x].setCharacter(text[x]);

            tiles[x].setBackgroundColor(colorPalette.getDefaultBackground());
            tiles[x].setForegroundColor(colorPalette.getDefaultForeground());
        }
    }
}
