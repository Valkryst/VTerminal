package com.valkryst.VTerminal.revamp.TileGridTest;

import com.valkryst.VTerminal.AsciiCharacter;
import org.junit.Assert;
import org.junit.Test;

public class GetRowTest {
    @Test
    public void testGetRow_withValidIndices() {
        for (int y = 0 ; y < StaticGrid.TILE_GRID.getHeight() ; y++) {
            final AsciiCharacter[] row = StaticGrid.TILE_GRID.getRow(y);

            Assert.assertEquals(row[0].getCharacter(), 'A');
            Assert.assertEquals(row[1].getCharacter(), 'B');
            Assert.assertEquals(row[2].getCharacter(), 'C');
            Assert.assertEquals(row[3].getCharacter(), 'D');
            Assert.assertEquals(row[4].getCharacter(), 'E');
            Assert.assertEquals(row[5].getCharacter(), 'F');
        }
    }

    @Test
    public void testGetRow_withNegativeIndex() {
        final AsciiCharacter[] row = StaticGrid.TILE_GRID.getRow(-1);
        Assert.assertEquals(row.length, 0);
    }

    @Test
    public void testGetRow_withIndexExceedingGridHeight() {
        int index = StaticGrid.TILE_GRID.getHeight() + 1;
        final AsciiCharacter[] row = StaticGrid.TILE_GRID.getRow(index);
        Assert.assertEquals(row.length, 0);
    }
}
