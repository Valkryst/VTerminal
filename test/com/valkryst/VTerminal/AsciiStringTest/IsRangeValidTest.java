package com.valkryst.VTerminal.AsciiStringTest;

import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.misc.IntRange;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class IsRangeValidTest {
    private final String testString = "ABCDEFGHJIKLMNOP";
    private AsciiString string;

    @Before
    public void initializeString() {
        string = new AsciiString(testString);
        string.setBackgroundAndForegroundColor(Color.BLACK, Color.WHITE);
    }

    @Test
    public void withValidInput() {
        final AsciiString string = new AsciiString(testString);

        for (int i = 0 ; i < string.getCharacters().length ; i++) {
            final IntRange range = new IntRange(0, i);
            Assert.assertTrue(string.isRangeValid(range));
        }
    }

    @Test
    public void withNullRange() {
        Assert.assertFalse(string.isRangeValid(null));
    }

    @Test
    public void withBeginLessThanZero() {
        for (int i = -1 ; i > -11 ; i--) {
            final IntRange range = new IntRange(i, 3);
            Assert.assertFalse(string.isRangeValid(range));
        }
    }

    @Test
    public void withEndGreaterThanStringLength() {
        for (int i = 1 ; i < 10 ; i++) {
            final int end = string.getCharacters().length + i;

            final IntRange range = new IntRange(0, end);
            Assert.assertFalse(string.isRangeValid(range));
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void withBeginGreaterThanEnd() {
        final IntRange range = new IntRange(3, 1);
        Assert.assertFalse(string.isRangeValid(range));
    }

    @Test(expected=IllegalArgumentException.class)
    public void withNegativeEnd() {
        final IntRange range = new IntRange(0, -3);
        Assert.assertFalse(string.isRangeValid(range));
    }

    @Test
    public void withBeginAndEndEqual() {
        final IntRange range = new IntRange(0, 0);
        Assert.assertTrue(string.isRangeValid(range));
    }
}
