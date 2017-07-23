package com.valkryst.VTerminal.AsciiStringTest;

import com.valkryst.VTerminal.AsciiString;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.Arrays;

public class SetAllCharactersToBeRedrawnTest {
    private final String testString = "ABCDEFGHJIKLMNOP";
    private AsciiString string;

    @Before
    public void initializeString() {
        string = new AsciiString(testString);
        string.setBackgroundColor(Color.BLACK);
        string.setForegroundColor(Color.WHITE);
    }
    @Test
    public void testSetAllCharactersToBeRedrawn() {
        Arrays.fill(string.getCharactersToBeRedrawn(), false);

        for (final boolean val : string.getCharactersToBeRedrawn()) {
            Assert.assertFalse(val);
        }

        string.setAllCharactersToBeRedrawn();

        for (final boolean val : string.getCharactersToBeRedrawn()) {
            Assert.assertTrue(val);
        }
    }
}
