package com.valkryst.VTerminal.revamp.TileGridTest;

import com.valkryst.VTerminal.TileGrid;
import org.junit.Assert;
import org.junit.Test;

import java.awt.Dimension;
import java.awt.Point;

public class ConstructorTest {
    @Test
    public void testConstructor_noPoint_withValidParams() {
        final TileGrid tileGrid = new TileGrid(new Dimension(10, 10));
        Assert.assertEquals(tileGrid.getWidth(), 10);
        Assert.assertEquals(tileGrid.getHeight(), 10);
        Assert.assertEquals(tileGrid.getXPosition(), 0);
        Assert.assertEquals(tileGrid.getYPosition(), 0);
    }

    @Test
    public void testConstructor_noPoint_withWidthLessThanOne() {
        final TileGrid tileGrid = new TileGrid(new Dimension(0, 10));
        Assert.assertEquals(tileGrid.getWidth(), 1);
        Assert.assertEquals(tileGrid.getHeight(), 10);
        Assert.assertEquals(tileGrid.getXPosition(), 0);
        Assert.assertEquals(tileGrid.getYPosition(), 0);
    }

    @Test
    public void testConstructor_noPoint_withHeightLessThanOne() {
        final TileGrid tileGrid = new TileGrid(new Dimension(10, 0));
        Assert.assertEquals(tileGrid.getWidth(), 10);
        Assert.assertEquals(tileGrid.getHeight(), 1);
        Assert.assertEquals(tileGrid.getXPosition(), 0);
        Assert.assertEquals(tileGrid.getYPosition(), 0);
    }

    @Test
    public void testConstructor_point_withValidParams() {
        final TileGrid tileGrid = new TileGrid(new Dimension(10, 10), new Point(5, 5));
        Assert.assertEquals(tileGrid.getWidth(), 10);
        Assert.assertEquals(tileGrid.getHeight(), 10);
        Assert.assertEquals(tileGrid.getXPosition(), 5);
        Assert.assertEquals(tileGrid.getYPosition(), 5);
    }

    @Test
    public void testConstructor_point_withXPositionLessThanZero() {
        final TileGrid tileGrid = new TileGrid(new Dimension(10, 10), new Point(-1, 5));
        Assert.assertEquals(tileGrid.getWidth(), 10);
        Assert.assertEquals(tileGrid.getHeight(), 10);
        Assert.assertEquals(tileGrid.getXPosition(), 0);
        Assert.assertEquals(tileGrid.getYPosition(), 5);
    }

    @Test
    public void testConstructor_point_withYPositionLessThanZero() {
        final TileGrid tileGrid = new TileGrid(new Dimension(10, 10), new Point(5, -1));
        Assert.assertEquals(tileGrid.getWidth(), 10);
        Assert.assertEquals(tileGrid.getHeight(), 10);
        Assert.assertEquals(tileGrid.getXPosition(), 5);
        Assert.assertEquals(tileGrid.getYPosition(), 0);
    }
}
