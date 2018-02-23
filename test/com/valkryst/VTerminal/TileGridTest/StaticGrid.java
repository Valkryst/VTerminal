package com.valkryst.VTerminal.TileGridTest;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.TileGrid;

import java.awt.Dimension;

public class StaticGrid {
    public final static TileGrid TILE_GRID;

    static {
        /*
         * The grid looks like this:
         *
         * ABCDEF
         * ABCDEF
         * ABCDEF
         * ABCDEF
         * ABCDEF
         * ABCDEF
         */
        TILE_GRID = new TileGrid(new Dimension(6, 6));

        for (int y = 0 ; y < TILE_GRID.getHeight() ; y++) {
            final AsciiCharacter[] row = TILE_GRID.getRow(y);

            for (int x = 0 ; x < TILE_GRID.getWidth() ; x++) {
                row[x].setCharacter((char) (x + 65));
            }
        }
    }
}
