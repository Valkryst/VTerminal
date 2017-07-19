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
            string.checkRangeValidity(range);
        }
    }

    @Test(expected=NullPointerException.class)
    public void withNullRange() {
        string.checkRangeValidity(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void withBeginLessThanZero() {
        final IntRange range = new IntRange(-1, 3);
        string.checkRangeValidity(range);
    }

    @Test(expected=IllegalArgumentException.class)
    public void withEndGreaterThanStringLength() {
        final int end = string.getCharacters().length + 1;

        final IntRange range = new IntRange(0, end);
        string.checkRangeValidity(range);
    }

    @Test(expected=IllegalArgumentException.class)
    public void withBeginGreaterThanEnd() {
        final IntRange range = new IntRange(3, 1);
        string.checkRangeValidity(range);
    }

    @Test(expected=IllegalArgumentException.class)
    public void withNegativeEnd() {
        final IntRange range = new IntRange(0, -3);
        string.checkRangeValidity(range);
    }

    @Test
    public void withBeginAndEndEqual() {
        final IntRange range = new IntRange(0, 0);
        string.checkRangeValidity(range);
    }
}
