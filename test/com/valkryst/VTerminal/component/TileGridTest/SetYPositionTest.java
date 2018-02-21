package com.valkryst.VTerminal.component.TileGridTest;

import com.valkryst.VTerminal.revamp.component.TileGrid;
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
