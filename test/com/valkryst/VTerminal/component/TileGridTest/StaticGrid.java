package com.valkryst.VTerminal.component.TileGridTest;

import com.valkryst.VTerminal.AsciiTile;
import com.valkryst.VTerminal.component.TileGrid;

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
        TILE_GRID = new TileGrid(6, 6);

        for (int y = 0 ; y < TILE_GRID.getHeight() ; y++) {
            final AsciiTile[] row = TILE_GRID.getRow(y);

            for (int x = 0 ; x < TILE_GRID.getWidth() ; x++) {
                row[x].setCharacter((char) (x + 65));
            }
        }
    }
}
