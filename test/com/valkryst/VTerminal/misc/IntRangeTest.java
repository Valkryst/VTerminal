package com.valkryst.VTerminal.misc;

import org.junit.Assert;
import org.junit.Test;

public class IntRangeTest {
    @Test
    public void testConstructor() {
        final IntRange range = new IntRange(1, 2);
        Assert.assertEquals(1, range.getBegin());
        Assert.assertEquals(2, range.getEnd());
    }

    @Test
    public void testConstructor_withNegativeBegin() {
        final IntRange range = new IntRange(-1, 2);
        Assert.assertEquals(-1, range.getBegin());
        Assert.assertEquals(2, range.getEnd());
    }

    @Test
    public void testConstructor_withNegativeEnd() {
        final IntRange range = new IntRange(-3, -2);
        Assert.assertEquals(-3, range.getBegin());
        Assert.assertEquals(-2, range.getEnd());
    }

    @Test
    public void testConstructor_withBeginGreaterThanEnd() {
        final IntRange range = new IntRange(2, 1);
        Assert.assertEquals(1, range.getBegin());
        Assert.assertEquals(1, range.getEnd());
    }

    @Test
    public void testClampValuesToRange() {
        final IntRange range = new IntRange(1, 200);
        range.clampValuesToRange(1, 50);
        Assert.assertEquals(1, range.getBegin());
        Assert.assertEquals(50, range.getEnd());
    }

    @Test
    public void testClampValuesToRange_withBeginLessThanClampBegin() {
        final IntRange range = new IntRange(1, 200);
        range.clampValuesToRange(10, 200);
        Assert.assertEquals(10, range.getBegin());
        Assert.assertEquals(200, range.getEnd());
    }

    @Test
    public void testClampValuesToRange_withBeginEqualToClampBegin() {
        final IntRange range = new IntRange(1, 200);
        range.clampValuesToRange(1, 200);
        Assert.assertEquals(1, range.getBegin());
        Assert.assertEquals(200, range.getEnd());
    }

    @Test
    public void testClampValuesToRange_withBeginBetweenClampBeginAndEnd() {
        final IntRange range = new IntRange(100, 200);
        range.clampValuesToRange(1, 200);
        Assert.assertEquals(100, range.getBegin());
        Assert.assertEquals(200, range.getEnd());
    }

    @Test
    public void testClampValuesToRange_withBeginEqualToClampEnd() {
        final IntRange range = new IntRange(200, 200);
        range.clampValuesToRange(1, 200);
        Assert.assertEquals(200, range.getBegin());
        Assert.assertEquals(200, range.getEnd());
    }

    @Test
    public void testClampValuesToRange_withBeginGreaterThanClampEnd() {
        final IntRange range = new IntRange(201, 200);
        range.clampValuesToRange(1, 200);
        Assert.assertEquals(200, range.getBegin());
        Assert.assertEquals(200, range.getEnd());
    }

    @Test
    public void testClampValuesToRange_withEndLessThanClampBegin() {
        final IntRange range = new IntRange(1, -1);
        range.clampValuesToRange(1, 200);
        Assert.assertEquals(1, range.getBegin());
        Assert.assertEquals(1, range.getEnd());
    }

    @Test
    public void testClampValuesToRange_withEndEqualToClampBegin() {
        final IntRange range = new IntRange(1, 1);
        range.clampValuesToRange(1, 200);
        Assert.assertEquals(1, range.getBegin());
        Assert.assertEquals(1, range.getEnd());
    }

    @Test
    public void testClampValuesToRange_withEndEqualToClampEnd() {
        final IntRange range = new IntRange(200, 200);
        range.clampValuesToRange(1, 200);
        Assert.assertEquals(200, range.getBegin());
        Assert.assertEquals(200, range.getEnd());
    }

    @Test
    public void testClampValuesToRange_withEndBetweenClampBeginAndEnd() {
        final IntRange range = new IntRange(1, 100);
        range.clampValuesToRange(1, 200);
        Assert.assertEquals(1, range.getBegin());
        Assert.assertEquals(100, range.getEnd());
    }

    @Test
    public void testClampValuesToRange_withBeginGreaterThanEnd() {
        final IntRange range = new IntRange(1, 100);
        range.clampValuesToRange(5, 4);
        Assert.assertEquals(4, range.getBegin());
        Assert.assertEquals(4, range.getEnd());
    }
}
