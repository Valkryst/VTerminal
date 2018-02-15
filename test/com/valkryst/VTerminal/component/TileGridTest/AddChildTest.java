package com.valkryst.VTerminal.component.TileGridTest;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.component.TileGrid;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddChildTest {
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
        parentGrid = new TileGrid(6, 6);

        for (int y = 0 ; y < parentGrid.getHeight() ; y++) {
            final AsciiCharacter[] row = parentGrid.getRow(y);

            for (int x = 0 ; x < parentGrid.getWidth() ; x++) {
                row[x].setCharacter((char) (x + 65));
            }
        }
    }

    @Test
    public void testAddChild_withValidChild() {
        final TileGrid grid = new TileGrid(1, 1);
        parentGrid.addChild(grid);
        Assert.assertTrue(parentGrid.containsChild(grid));
    }

    @Test
    public void testAddChild_withNullChild() {
        parentGrid.addChild(null);
    }
}
