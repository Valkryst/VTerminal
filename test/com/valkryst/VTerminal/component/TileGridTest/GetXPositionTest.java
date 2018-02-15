package com.valkryst.VTerminal.component.TileGridTest;

import com.valkryst.VTerminal.component.TileGrid;
import org.junit.Assert;
import org.junit.Test;

public class GetXPositionTest {
    @Test
    public void testGetXPosition() {
        final TileGrid grid = StaticGrid.TILE_GRID;
        Assert.assertEquals(0, grid.getXPosition());
    }

    @Test
    public void testGetXPosition_afterChangingPosition() {
        final TileGrid grid = StaticGrid.TILE_GRID;
        grid.setXPosition(5);
        Assert.assertEquals(5, grid.getXPosition());
        Assert.assertEquals(0, grid.getYPosition());

        // Reset position.
        grid.setXPosition(0);
    }
}
