package com.valkryst.VTerminal.TileGridTest;

import com.valkryst.VTerminal.Tile;
import org.junit.Assert;
import org.junit.Test;

public class GetWidthTest {
    @Test
    public void testGetWidth_ensureValueIsAsExpected() {
        final int length = StaticGrid.TILE_GRID.getRow(0).length;
        Assert.assertEquals(6, length);
    }

    @Test
    public void testGetWidth_ensureAllRowsAreOfEqualLength() {
        final int length = StaticGrid.TILE_GRID.getRow(0).length;

        for (int y = 0 ; y < StaticGrid.TILE_GRID.getHeight() ; y++) {
            final Tile[] row = StaticGrid.TILE_GRID.getRow(y);
            Assert.assertEquals(row.length, length);
        }
    }
}
