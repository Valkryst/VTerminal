package com.valkryst.VTerminal.AsciiStringTest;

import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.misc.IntRange;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.Arrays;

public class SetCharacterRangeToBeRedrawnTest {
    private final String testString = "ABCDEFGHJIKLMNOP";
    private AsciiString string;

    @Before
    public void initializeString() {
        string = new AsciiString(testString);
        string.setBackgroundColor(Color.BLACK);
        string.setForegroundColor(Color.WHITE);
    }

    @Test(expected=NullPointerException.class)
    public void withNullRange() {
        Arrays.fill(string.getCharactersToBeRedrawn(), false);

        for (final boolean val : string.getCharactersToBeRedrawn()) {
            Assert.assertFalse(val);
        }

        string.setCharacterRangeToBeRedrawn(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void withInvalidRange() {
        Arrays.fill(string.getCharactersToBeRedrawn(), false);

        for (final boolean val : string.getCharactersToBeRedrawn()) {
            Assert.assertFalse(val);
        }

        final IntRange range = new IntRange(-1, string.getCharacters().length);
        string.setCharacterRangeToBeRedrawn(range);

        for (final boolean val : string.getCharactersToBeRedrawn()) {
            Assert.assertTrue(val);
        }
    }


    @Test
    public void withValidRange() {
        Arrays.fill(string.getCharactersToBeRedrawn(), false);

        for (final boolean val : string.getCharactersToBeRedrawn()) {
            Assert.assertFalse(val);
        }

        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.setCharacterRangeToBeRedrawn(range);

        for (final boolean val : string.getCharactersToBeRedrawn()) {
            Assert.assertTrue(val);
        }
    }
}
