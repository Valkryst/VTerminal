package com.valkryst.VTerminal.component.TileGridTest;

import com.valkryst.VTerminal.AsciiCharacter;
import org.junit.Assert;
import org.junit.Test;

public class GetColumnTest {
    @Test
    public void testGetColumn_withValidIndices() {
        for (int x = 0 ; x < StaticGrid.TILE_GRID.getWidth() ; x++) {
            final AsciiCharacter[] column = StaticGrid.TILE_GRID.getColumn(x);

            char character = (char) (x + 65);
            Assert.assertEquals(column[0].getCharacter(), character);
            Assert.assertEquals(column[1].getCharacter(), character);
            Assert.assertEquals(column[2].getCharacter(), character);
            Assert.assertEquals(column[3].getCharacter(), character);
            Assert.assertEquals(column[4].getCharacter(), character);
            Assert.assertEquals(column[5].getCharacter(), character);
        }
    }

    @Test
    public void testGetColumn_withNegativeIndex() {
        final AsciiCharacter[] row = StaticGrid.TILE_GRID.getColumn(-1);
        Assert.assertEquals(row.length, 0);
    }

    @Test
    public void testGetColumn_withIndexExceedingGridWidth() {
        int index = StaticGrid.TILE_GRID.getWidth() + 1;
        final AsciiCharacter[] row = StaticGrid.TILE_GRID.getColumn(index);
        Assert.assertEquals(row.length, 0);
    }
}
