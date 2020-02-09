package com.valkryst.VTerminal.TileGridTest;

import com.valkryst.VTerminal.GraphicTile;
import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.TileGrid;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Dimension;

public class ConvertToGraphicTileGridTest {
    private TileGrid parentGrid;

    @Before
    public void initGrid() {
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
        parentGrid = new TileGrid(new Dimension(6, 6));

        for (int y = 0 ; y < parentGrid.getHeight() ; y++) {
            final Tile[] row = parentGrid.getRow(y);

            for (int x = 0 ; x < parentGrid.getWidth() ; x++) {
                row[x].setCharacter((char) (x + 65));
            }
        }
    }

    @Test
    public void convertToGraphicTileGridTest() {
        for (int y = 0 ; y < parentGrid.getHeight() ; y++) {
            for (int x = 0 ; x < parentGrid.getWidth() ; x++) {
                Assert.assertFalse(parentGrid.getTileAt(x, y) instanceof GraphicTile);
            }
        }

        parentGrid.convertToGraphicTileGrid();

        for (int y = 0 ; y < parentGrid.getHeight() ; y++) {
            for (int x = 0 ; x < parentGrid.getWidth() ; x++) {
                Assert.assertTrue(parentGrid.getTileAt(x, y) instanceof GraphicTile);
            }
        }
    }
}
