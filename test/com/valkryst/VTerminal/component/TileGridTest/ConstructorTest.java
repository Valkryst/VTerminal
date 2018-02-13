package com.valkryst.VTerminal.component.TileGridTest;

import com.valkryst.VTerminal.component.TileGrid;
import org.junit.Assert;
import org.junit.Test;

public class ConstructorTest {
    @Test
    public void testConstructor_withValidParams() {
        final TileGrid tileGrid = new TileGrid(10, 10);
        Assert.assertEquals(tileGrid.getWidth(), 10);
        Assert.assertEquals(tileGrid.getHeight(), 10);
    }

    @Test
    public void testConstructor_withWidthLessThanOne() {
        final TileGrid tileGrid = new TileGrid(0, 10);
        Assert.assertEquals(tileGrid.getWidth(), 1);
        Assert.assertEquals(tileGrid.getHeight(), 10);
    }

    @Test
    public void testConstructor_withHeightLessThanOne() {
        final TileGrid tileGrid = new TileGrid(10, 0);
        Assert.assertEquals(tileGrid.getWidth(), 10);
        Assert.assertEquals(tileGrid.getHeight(), 1);
    }
}
