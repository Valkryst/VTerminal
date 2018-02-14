package com.valkryst.VTerminal.component.TileGridTest;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.component.TileGrid;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AddChildBeforeTest {
    public  TileGrid parentGrid;

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
    public void testAddChildBefore_withValidParams() {
        final TileGrid gridA = new TileGrid(1, 1);
        final TileGrid gridB = new TileGrid(1, 1);

        parentGrid.addChild(gridA);
        parentGrid.addChildBefore(gridB, gridA);

        Assert.assertTrue(parentGrid.containsChild(gridA));
        Assert.assertTrue(parentGrid.containsChild(gridB));
    }

    @Test
    public void testAddChildBefore_withNullNewChild() {
        final TileGrid gridA = new TileGrid(1, 1);

        parentGrid.addChild(gridA);
        parentGrid.addChildBefore(null, gridA);

        Assert.assertTrue(parentGrid.containsChild(gridA));
    }

    @Test
    public void testAddChildBefore_withNullExistingChild() {
        final TileGrid gridA = new TileGrid(1, 1);

        parentGrid.addChildBefore(gridA, null);

        Assert.assertTrue(parentGrid.containsChild(gridA));
    }

    @Test
    public void testAddChildBefore_whereExistingChildNotAChildOfParent() {
        final TileGrid gridA = new TileGrid(1, 1);
        final TileGrid gridB = new TileGrid(1, 1);

        parentGrid.addChildBefore(gridB, gridA);

        Assert.assertFalse(parentGrid.containsChild(gridA));
        Assert.assertTrue(parentGrid.containsChild(gridB));
    }
}
