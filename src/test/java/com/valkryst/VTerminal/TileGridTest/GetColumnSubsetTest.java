package com.valkryst.VTerminal.TileGridTest;

import com.valkryst.VTerminal.Tile;
import org.junit.Assert;
import org.junit.Test;

public class GetColumnSubsetTest {
    @Test
    public void testGetColumnSubset_withValidParams() {
        final Tile[] columnSubset = StaticGrid.TILE_GRID.getColumnSubset(0, 1, 4);
        Assert.assertEquals(4, columnSubset.length);
        Assert.assertEquals(columnSubset[0].getCharacter(), 'B');
        Assert.assertEquals(columnSubset[1].getCharacter(), 'B');
        Assert.assertEquals(columnSubset[2].getCharacter(), 'B');
        Assert.assertEquals(columnSubset[3].getCharacter(), 'B');
    }

    @Test
    public void testGetColumnSubset_withLengthExceedingGridHeight() {
        int gridHeight = StaticGrid.TILE_GRID.getHeight() + 10;
        final Tile[] columnSubset = StaticGrid.TILE_GRID.getColumnSubset(0, 1, gridHeight);
        Assert.assertEquals(6, columnSubset.length);
        Assert.assertEquals(columnSubset[0].getCharacter(), 'B');
        Assert.assertEquals(columnSubset[1].getCharacter(), 'B');
        Assert.assertEquals(columnSubset[2].getCharacter(), 'B');
        Assert.assertEquals(columnSubset[3].getCharacter(), 'B');
        Assert.assertEquals(columnSubset[4].getCharacter(), 'B');
        Assert.assertEquals(columnSubset[5].getCharacter(), 'B');
    }

    @Test
    public void testGetColumnSubset_withRowIndexLessThanZero() {
        final Tile[] columnSubset = StaticGrid.TILE_GRID.getColumnSubset(-1, 1, 4);
        Assert.assertEquals(columnSubset.length, 0);
    }

    @Test
    public void testGetColumnSubset_withColumnIndexLessThanZero() {
        final Tile[] columnSubset = StaticGrid.TILE_GRID.getColumnSubset(0, -1, 4);
        Assert.assertEquals(columnSubset.length, 0);
    }

    @Test
    public void testGetColumnSubset_withLengthLessThanOne() {
        final Tile[] columnSubset = StaticGrid.TILE_GRID.getColumnSubset(0, 1, 0);
        Assert.assertEquals(columnSubset.length, 0);
    }

    @Test
    public void testGetColumnSubset_withRowIndexExceedingGridHeight() {
        int gridHeight = StaticGrid.TILE_GRID.getHeight() + 1;
        final Tile[] columnSubset = StaticGrid.TILE_GRID.getColumnSubset(gridHeight, 1, 0);
        Assert.assertEquals(columnSubset.length, 0);
    }

    @Test
    public void testGetColumnSubset_withColumnIndexExceedingGridWidth() {
        int gridWidth = StaticGrid.TILE_GRID.getWidth() + 1;
        final Tile[] columnSubset = StaticGrid.TILE_GRID.getColumnSubset(0, gridWidth, 0);
        Assert.assertEquals(columnSubset.length, 0);
    }
}
