package com.valkryst.VTerminal.TileGridTest;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.TileGrid;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Dimension;
import java.awt.Point;

public class CopyOntoTest {
    private TileGrid parentGrid;

    @Before
    public void initGrid() {
        /*
         * The grid looks like this:
         *
         * AB
         * AB
         */
        parentGrid = new TileGrid(new Dimension(2, 2));

        for (int y = 0 ; y < parentGrid.getHeight() ; y++) {
            final Tile[] row = parentGrid.getRow(y);

            for (int x = 0 ; x < parentGrid.getWidth() ; x++) {
                row[x].setCharacter((char) (x + 65));
            }
        }
    }

    @Test
    public void testCopyOnto_withGridsOfMatchingSize() {
        final TileGrid newGrid = new TileGrid(new Dimension(2, 2), new Point(0, 0));

        Assert.assertEquals(parentGrid.getTileAt(0, 0).getCharacter(), 'A');
        Assert.assertEquals(parentGrid.getTileAt(1, 0).getCharacter(), 'B');
        Assert.assertEquals(parentGrid.getTileAt(0, 1).getCharacter(), 'A');
        Assert.assertEquals(parentGrid.getTileAt(1, 1).getCharacter(), 'B');

        Assert.assertEquals(newGrid.getTileAt(0, 0).getCharacter(), ' ');
        Assert.assertEquals(newGrid.getTileAt(1, 0).getCharacter(), ' ');
        Assert.assertEquals(newGrid.getTileAt(0, 1).getCharacter(), ' ');
        Assert.assertEquals(newGrid.getTileAt(1, 1).getCharacter(), ' ');

        parentGrid.copyOnto(newGrid);

        Assert.assertEquals(parentGrid.getTileAt(0, 0).getCharacter(), 'A');
        Assert.assertEquals(parentGrid.getTileAt(1, 0).getCharacter(), 'B');
        Assert.assertEquals(parentGrid.getTileAt(0, 1).getCharacter(), 'A');
        Assert.assertEquals(parentGrid.getTileAt(1, 1).getCharacter(), 'B');

        Assert.assertEquals(newGrid.getTileAt(0, 0).getCharacter(), 'A');
        Assert.assertEquals(newGrid.getTileAt(1, 0).getCharacter(), 'B');
        Assert.assertEquals(newGrid.getTileAt(0, 1).getCharacter(), 'A');
        Assert.assertEquals(newGrid.getTileAt(1, 1).getCharacter(), 'B');
    }

    @Test
    public void testCopyOnto_withDestinationOfSmallerSize() {
        final TileGrid newGrid = new TileGrid(new Dimension(1, 2), new Point(0, 0));

        Assert.assertEquals(parentGrid.getTileAt(0, 0).getCharacter(), 'A');
        Assert.assertEquals(parentGrid.getTileAt(1, 0).getCharacter(), 'B');
        Assert.assertEquals(parentGrid.getTileAt(0, 1).getCharacter(), 'A');
        Assert.assertEquals(parentGrid.getTileAt(1, 1).getCharacter(), 'B');

        Assert.assertEquals(newGrid.getTileAt(0, 0).getCharacter(), ' ');
        Assert.assertEquals(newGrid.getTileAt(0, 1).getCharacter(), ' ');

        parentGrid.copyOnto(newGrid);

        Assert.assertEquals(parentGrid.getTileAt(0, 0).getCharacter(), 'A');
        Assert.assertEquals(parentGrid.getTileAt(1, 0).getCharacter(), 'B');
        Assert.assertEquals(parentGrid.getTileAt(0, 1).getCharacter(), 'A');
        Assert.assertEquals(parentGrid.getTileAt(1, 1).getCharacter(), 'B');

        Assert.assertEquals(newGrid.getTileAt(0, 0).getCharacter(), 'A');
        Assert.assertEquals(newGrid.getTileAt(0, 1).getCharacter(), 'A');
    }

    @Test
    public void testCopyOnto_whereSourceHasChild() {
        final TileGrid childGrid = new TileGrid(new Dimension(1, 1), new Point(0, 0));
        childGrid.getTileAt(0, 0).setCharacter('Z');
        parentGrid.addChild(childGrid);

        final TileGrid newGrid = new TileGrid(new Dimension(2, 2), new Point(0, 0));

        Assert.assertEquals(parentGrid.getTileAt(0, 0).getCharacter(), 'Z');
        Assert.assertEquals(parentGrid.getTileAt(1, 0).getCharacter(), 'B');
        Assert.assertEquals(parentGrid.getTileAt(0, 1).getCharacter(), 'A');
        Assert.assertEquals(parentGrid.getTileAt(1, 1).getCharacter(), 'B');

        Assert.assertEquals(newGrid.getTileAt(0, 0).getCharacter(), ' ');
        Assert.assertEquals(newGrid.getTileAt(1, 0).getCharacter(), ' ');
        Assert.assertEquals(newGrid.getTileAt(0, 1).getCharacter(), ' ');
        Assert.assertEquals(newGrid.getTileAt(1, 1).getCharacter(), ' ');

        parentGrid.copyOnto(newGrid);

        Assert.assertEquals(parentGrid.getTileAt(0, 0).getCharacter(), 'Z');
        Assert.assertEquals(parentGrid.getTileAt(1, 0).getCharacter(), 'B');
        Assert.assertEquals(parentGrid.getTileAt(0, 1).getCharacter(), 'A');
        Assert.assertEquals(parentGrid.getTileAt(1, 1).getCharacter(), 'B');

        Assert.assertEquals(newGrid.getTileAt(0, 0).getCharacter(), 'Z');
        Assert.assertEquals(newGrid.getTileAt(1, 0).getCharacter(), 'B');
        Assert.assertEquals(newGrid.getTileAt(0, 1).getCharacter(), 'A');
        Assert.assertEquals(newGrid.getTileAt(1, 1).getCharacter(), 'B');
    }

    @Test
    public void testCopyOnto_withNullGrid() {
        Assert.assertEquals(parentGrid.getTileAt(0, 0).getCharacter(), 'A');
        Assert.assertEquals(parentGrid.getTileAt(1, 0).getCharacter(), 'B');
        Assert.assertEquals(parentGrid.getTileAt(0, 1).getCharacter(), 'A');
        Assert.assertEquals(parentGrid.getTileAt(1, 1).getCharacter(), 'B');

        parentGrid.copyOnto(null);

        Assert.assertEquals(parentGrid.getTileAt(0, 0).getCharacter(), 'A');
        Assert.assertEquals(parentGrid.getTileAt(1, 0).getCharacter(), 'B');
        Assert.assertEquals(parentGrid.getTileAt(0, 1).getCharacter(), 'A');
        Assert.assertEquals(parentGrid.getTileAt(1, 1).getCharacter(), 'B');
    }
}
