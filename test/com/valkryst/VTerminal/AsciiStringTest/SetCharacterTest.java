package com.valkryst.VTerminal.AsciiStringTest;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class SetCharacterTest {
    private final String testString = "ABCDEFGHJIKLMNOP";
    private AsciiString string;

    @Before
    public void initializeString() {
        string = new AsciiString(testString);
        string.setBackgroundAndForegroundColor(Color.BLACK, Color.WHITE);
    }

    @Test
    public void objectChar_withValidInput() {
        final AsciiCharacter newChar = new AsciiCharacter('Z');
        string.setCharacter(0, newChar);

        // Using == compares object references:
        Assert.assertTrue(newChar == string.getCharacters()[0]);
    }

    @Test(expected=NullPointerException.class)
    public void objectChar_withNullCharacter() {
        string.setCharacter(0, null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void objectChar_withNegativeColumn() {
        string.setCharacter(-1, new AsciiCharacter('Z'));
    }

    @Test(expected=IllegalArgumentException.class)
    public void objectChar_withColumnGreaterThanStringLength() {
        string.setCharacter(string.length() + 1, new AsciiCharacter('Z'));
    }

    @Test
    public void primitiveChar_withValidInput() {
        string.setCharacter(0, 'Z');
        Assert.assertEquals('Z', string.getCharacters()[0].getCharacter());
    }

    @Test(expected=IllegalArgumentException.class)
    public void primitiveChar_withNegativeColumn() {
        string.setCharacter(-1, 'Z');
        Assert.assertEquals('A', string.getCharacters()[0].getCharacter());
    }

    @Test(expected=IllegalArgumentException.class)
    public void primitiveChar_withColumnGreaterThanStringLength() {
        string.setCharacter(string.getCharacters().length + 1, 'Z');
    }
}
