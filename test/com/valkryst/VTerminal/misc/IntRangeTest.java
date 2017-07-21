package com.valkryst.VTerminal.misc;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class IntRangeTest {
    private IntRange range;

    @Before
    public void initializeRange() {
        range = new IntRange(1, 200);
    }

    @Test
    public void testConstructor() {
        final IntRange range = new IntRange(1, 2);
        Assert.assertEquals(1, range.getStart());
        Assert.assertEquals(2, range.getEnd());
    }

    @Test
    public void testConstructor_withNegativeBegin() {
        final IntRange range = new IntRange(-1, 2);
        Assert.assertEquals(-1, range.getStart());
        Assert.assertEquals(2, range.getEnd());
    }

    @Test
    public void testConstructor_withNegativeEnd() {
        final IntRange range = new IntRange(-3, -2);
        Assert.assertEquals(-3, range.getStart());
        Assert.assertEquals(-2, range.getEnd());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_withBeginGreaterThanEnd() {
        new IntRange(2, 1);
    }

    @Test
    public void testToString() {
        Assert.assertFalse(new IntRange(1, 2).toString().isEmpty());
    }

    @Test
    public void testEquals_withSelf() {
        Assert.assertEquals(range, range);
    }

    @Test
    public void testEquals_withEqualRanges() {
        final IntRange rangeB = new IntRange(1, 200);
        Assert.assertEquals(range, rangeB);
    }

    @Test
    public void testEquals_withNonIntRangeObject() {
        Assert.assertNotEquals(range, 7);
    }

    @Test
    public void testEquals_withNonEqualBegin() {
        final IntRange rangeB = new IntRange(6, 200);
        Assert.assertNotEquals(range, rangeB);
    }

    @Test
    public void testEquals_withNonEqualEnd() {
        final IntRange rangeB = new IntRange(1, 300);
        Assert.assertNotEquals(range, rangeB);
    }

    @Test
    public void testHashCode_withEqualRanges() {
        final IntRange rangeB = new IntRange(1, 200);
        Assert.assertEquals(range.hashCode(), rangeB.hashCode());
    }

    @Test
    public void testHashCode_withNonEqualRanges() {
        final IntRange rangeB = new IntRange(6, 200);
        Assert.assertNotEquals(range.hashCode(), rangeB.hashCode());
    }
}