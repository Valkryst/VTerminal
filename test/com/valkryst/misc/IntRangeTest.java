package com.valkryst.misc;

import com.valkryst.AsciiPanel.misc.IntRange;
import org.junit.Assert;
import org.junit.Test;

public class IntRangeTest {
    @Test
    public void createIntRangeWithNegativeBeginValue() {
        for (int i = -10 ; i < -1 ; i++) {
            final IntRange range = new IntRange(i, 0);
            Assert.assertEquals(i, range.getBegin());
        }
    }

    @Test
    public void createIntRangeWithZeroBeginValue() {
        final IntRange range = new IntRange(0, 0);
        Assert.assertEquals(0, range.getBegin());
    }

    @Test
    public void createIntRangeWithPositiveBeginValue() {
        for (int i = 0 ; i < 10 ; i++) {
            final IntRange range = new IntRange(i, 10);
            Assert.assertEquals(i, range.getBegin());
        }
    }

    @Test
    public void createIntRangeWithNegativeEndValue() {
        for (int i = -10 ; i < -1 ; i++) {
            final IntRange range = new IntRange(0, i);
            Assert.assertEquals(i, range.getEnd());
        }
    }

    @Test
    public void createIntRangeWithZeroEndValue() {
        final IntRange range = new IntRange(0, 0);
        Assert.assertEquals(0, range.getEnd());
    }

    @Test
    public void createIntRangeWithPositiveEndValue() {
        for (int i = 0 ; i < 10 ; i++) {
            final IntRange range = new IntRange(0, i);
            Assert.assertEquals(i, range.getEnd());
        }
    }

    @Test
    public void createIntRangeWithBeginGreaterThanEnd() {
        final IntRange range = new IntRange(11, 10);
        Assert.assertEquals(range.getBegin(), range.getEnd());
    }

    @Test
    public void clampValuesToRangeWithBeginBelowMinimum() {
        final IntRange range = new IntRange(-10, 0);
        range.clampValuesToRange(0, 10);
        Assert.assertEquals(0, range.getBegin());
    }

    @Test
    public void clampValuesToRangeWithBeginAtMinimum() {
        final IntRange range = new IntRange(0, 0);
        range.clampValuesToRange(0, 10);
        Assert.assertEquals(0, range.getBegin());
    }

    @Test
    public void clampValuesToRangeWithBeginAtMaximum() {
        final IntRange range = new IntRange(10, 10);
        range.clampValuesToRange(0, 10);
        Assert.assertEquals(10, range.getBegin());
    }

    @Test
    public void clampValuesToRangeWithBeginAboveMaximum() {
        final IntRange range = new IntRange(11, 10);
        range.clampValuesToRange(0, 10);
        Assert.assertEquals(10, range.getBegin());
    }

    @Test
    public void clampValuesToRangeWithEndBelowMinimum() {
        final IntRange range = new IntRange(0, -10);
        range.clampValuesToRange(0, 10);
        Assert.assertEquals(0, range.getEnd());
    }

    @Test
    public void clampValuesToRangeWithEndAtMinimum() {
        final IntRange range = new IntRange(0, 0);
        range.clampValuesToRange(0, 10);
        Assert.assertEquals(0, range.getEnd());
    }

    @Test
    public void clampValuesToRangeWithEndAtMaximum() {
        final IntRange range = new IntRange(0, 10);
        range.clampValuesToRange(0, 10);
        Assert.assertEquals(10, range.getEnd());
    }

    @Test
    public void clampValuesToRangeWithEndAboveMaximum() {
        final IntRange range = new IntRange(0, 11);
        range.clampValuesToRange(0, 10);
        Assert.assertEquals(10, range.getEnd());
    }

    @Test
    public void clampValuesToRangeWithBeginGreaterThanEnd() {
        final IntRange range = new IntRange(11, 10);
        range.clampValuesToRange(0, 10);
        Assert.assertEquals(range.getBegin(), range.getEnd());
    }
}
