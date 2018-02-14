package com.valkryst.VTerminal.component.TileGridTest;

import com.valkryst.VTerminal.component.TileGrid;
import org.junit.Assert;
import org.junit.Test;

public class SetYPositionTest {
    @Test
    public void testSetYPosition_withValidParam() {
        final TileGrid grid = StaticGrid.TILE_GRID;
        grid.setYPosition(5);

        Assert.assertEquals(5, grid.getYPosition());
        Assert.assertTrue(grid.requiresRedraw());

        // Reset position.
        grid.setYPosition(0);
    }

    @Test
    public void testSetYPosition_withYBelowZero() {
        final TileGrid grid = StaticGrid.TILE_GRID;
        grid.setYPosition(-1);

        Assert.assertEquals(0, grid.getYPosition());
        Assert.assertTrue(grid.requiresRedraw());

        // Reset position.
        grid.setYPosition(0);
    }
}
