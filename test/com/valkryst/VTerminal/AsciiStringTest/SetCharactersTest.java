package com.valkryst.VTerminal.AsciiStringTest;

import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.misc.IntRange;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class SetCharactersTest {
    private final String testString = "ABCDEFGHJIKLMNOP";
    private AsciiString string;

    @Before
    public void initializeString() {
        string = new AsciiString(testString);
        string.setBackgroundColor(Color.BLACK);
        string.setForegroundColor(Color.WHITE);
    }

    @Test
    public void withValidInput() {
        for (int i  = 0 ; i < string.getCharacters().length ; i++) {
            string = new AsciiString(testString);

            final IntRange range = new IntRange(0, i);
            string.setCharacters('Z', range);

            // Ensure that all characters in the range are set to 'Z' and that
            // all other characters were unchanged.
            for (int j = 0 ; j < string.getCharacters().length ; j++) {
                if (j < i) {
                    Assert.assertEquals('Z', string.getCharacters()[j].getCharacter());
                } else {
                    Assert.assertNotEquals('Z', string.getCharacters()[j].getCharacter());
                }
            }
        }
    }
}
