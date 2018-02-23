package com.valkryst.VTerminal.revamp.TileGridTest;

import com.valkryst.VTerminal.AsciiCharacter;
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
            final AsciiCharacter[] row = StaticGrid.TILE_GRID.getRow(y);
            Assert.assertEquals(row.length, length);
        }
    }
}
