package com.valkryst.VTerminal.TileGridTest;

import com.valkryst.VTerminal.TileGrid;
import org.junit.Assert;
import org.junit.Test;

public class SetXPositionTest {
    @Test
    public void testSetXPosition_withValidParam() {
        final TileGrid grid = StaticGrid.TILE_GRID;
        grid.setXPosition(5);

        Assert.assertEquals(5, grid.getXPosition());

        // Reset position.
        grid.setXPosition(0);
    }
}
