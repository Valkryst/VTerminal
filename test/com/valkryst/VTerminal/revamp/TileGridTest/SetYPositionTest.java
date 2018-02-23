package com.valkryst.VTerminal.revamp.TileGridTest;

import com.valkryst.VTerminal.TileGrid;
import org.junit.Assert;
import org.junit.Test;

public class SetYPositionTest {
    @Test
    public void testSetYPosition_withValidParam() {
        final TileGrid grid = StaticGrid.TILE_GRID;
        grid.setYPosition(5);

        Assert.assertEquals(5, grid.getYPosition());

        // Reset position.
        grid.setYPosition(0);
    }
}
