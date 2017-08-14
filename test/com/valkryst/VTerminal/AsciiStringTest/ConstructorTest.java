package com.valkryst.VTerminal.AsciiStringTest;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class ConstructorTest {
    private final String testString = "ABCDEFGHJIKLMNOP";
    private AsciiString string;

    @Before
    public void initializeString() {
        string = new AsciiString(testString);
        string.setBackgroundColor(Color.BLACK);
        string.setForegroundColor(Color.WHITE);
    }

    @Test
    public void lengthConstructor_withValidInput() {
        for (int i = 0 ; i < 10 ; i++) {
            final AsciiString string = new AsciiString(i);

            // Ensure that the correct number of characters are initialized:
            Assert.assertEquals(i, string.getCharacters().length);

            // Ensure all characters are initialized properly:
            for (final AsciiCharacter character: string.getCharacters()) {
                Assert.assertEquals(' ', character.getCharacter());
            }

            // Ensure that no two characters are initialized as the same object:
            for (int a = 0 ; a < i ; a++) {
                for (int b = a + 1 ; b < i ; b++) {
                    final AsciiCharacter charA = string.getCharacters()[a];
                    final AsciiCharacter charB = string.getCharacters()[b];

                    // Using == compares object references:
                    Assert.assertFalse(charA == charB);
                }
            }
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void lengthConstructor_withNegativeLength() {
        new AsciiString(-1);
    }

    @Test
    public void stringConstructor_withValidInput() {
        for (int i = 0 ; i < 10 ; i++) {
            final AsciiString string = new AsciiString(testString.substring(0, i));

            // Ensure that the correct number of characters are initialized:
            Assert.assertEquals(i, string.getCharacters().length);

            // Ensure all characters are initialized properly:
            for (int a = 0 ; a < string.getCharacters().length ; a++) {
                final char expected = testString.charAt(a);
                final char actual = string.getCharacters()[a].getCharacter();

                Assert.assertEquals(expected, actual);
            }

            // Ensure that no two characters are initialized as the same object:
            for (int a = 0 ; a < i ; a++) {
                for (int b = a + 1 ; b < i ; b++) {
                    final AsciiCharacter charA = string.getCharacters()[a];
                    final AsciiCharacter charB = string.getCharacters()[b];

                    // Using == compares object references:
                    Assert.assertFalse(charA == charB);
                }
            }
        }
    }

    @Test
    public void stringConstructor_withEmptyString() {
        final AsciiString string = new AsciiString("");
        Assert.assertEquals(0, string.getCharacters().length);
    }

    @Test(expected=NullPointerException.class)
    public void stringConstructor_withNullString() {
        new AsciiString(null);
    }
}
