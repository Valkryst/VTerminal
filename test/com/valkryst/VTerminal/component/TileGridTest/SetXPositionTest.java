package com.valkryst.VTerminal.component.TileGridTest;

import com.valkryst.VTerminal.component.TileGrid;
import org.junit.Assert;
import org.junit.Test;

public class SetXPositionTest {
    @Test
    public void testSetXPosition_withValidParam() {
        final TileGrid grid = StaticGrid.TILE_GRID;
        grid.setXPosition(5);

        Assert.assertEquals(5, grid.getXPosition());
        Assert.assertTrue(grid.requiresRedraw());

        // Reset position.
        grid.setXPosition(0);
    }

    @Test
    public void testSetXPosition_withXBelowZero() {
        final TileGrid grid = StaticGrid.TILE_GRID;
        grid.setXPosition(-1);

        Assert.assertEquals(0, grid.getXPosition());
        Assert.assertFalse(grid.requiresRedraw());

        // Reset position.
        grid.setXPosition(0);
    }
}
