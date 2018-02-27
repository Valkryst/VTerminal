package com.valkryst.VTerminal.TileGridTest;

import com.valkryst.VTerminal.TileGrid;
import org.junit.Assert;
import org.junit.Test;

public class GetYPositionTest {
    @Test
    public void testGetYPosition() {
        final TileGrid grid = StaticGrid.TILE_GRID;
        Assert.assertEquals(0, grid.getYPosition());
    }

    @Test
    public void testGetYPosition_afterChangingPosition() {
        final TileGrid grid = StaticGrid.TILE_GRID;
        grid.setYPosition(5);
        Assert.assertEquals(0, grid.getXPosition());
        Assert.assertEquals(5, grid.getYPosition());

        // Reset position.
        grid.setYPosition(0);
    }
}