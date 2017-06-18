package com.valkryst.VTerminal;

import com.valkryst.VTerminal.misc.IntRange;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class AsciiStringTest {
    private final String testString = "ABCDEFGHJIKLMNOP";
    private AsciiString string;

    @Before
    public void initializeString() {
        string = new AsciiString(testString);
        string.setBackgroundAndForegroundColor(Color.BLACK, Color.WHITE);
    }

    @Test
    public void testConstructor_lengthConstructor() {
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

    @Test
    public void testConstructor_lengthConstructor_withNegativeLength() {
        final AsciiString string = new AsciiString(-1);
        Assert.assertEquals(0, string.getCharacters().length);
    }

    @Test
    public void testConstructor_stringConstructor() {
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
    public void testConstructor_stringConstructor_withEmptyString() {
        final AsciiString string = new AsciiString("");
        Assert.assertEquals(0, string.getCharacters().length);
    }

    @Test
    public void testConstructor_stringConstructor_withNullString() {
        final AsciiString string = new AsciiString(null);
        Assert.assertEquals(0, string.getCharacters().length);
    }

    @Test
    public void testToString() {
        final String actual_toString = string.toString();
        final String expected_toString = testString;

        Assert.assertEquals(expected_toString, actual_toString);
    }

    @Test
    public void testIsRangeValid() {
        final AsciiString string = new AsciiString(testString);

        for (int i = 0 ; i < string.getCharacters().length ; i++) {
            final IntRange range = new IntRange(0, i);
            Assert.assertTrue(string.isRangeValid(range));
        }
    }

    @Test
    public void testIsRangeValid_withNegativeBegin() {
        final IntRange range = new IntRange(-1, 3);
        Assert.assertFalse(string.isRangeValid(range));
    }

    @Test
    public void testIsRangeValid_withNegativeEnd() {
        final IntRange range = new IntRange(0, -3);
        Assert.assertFalse(string.isRangeValid(range));
    }

    @Test
    public void testIsRangeValid_withBeginAndEndEqual() {
        final IntRange range = new IntRange(0, 0);
        Assert.assertTrue(string.isRangeValid(range));
    }

    @Test
    public void testIsRangeValid_withEndLargerThanStringLength() {
        final IntRange range = new IntRange(0, string.getCharacters().length + 1);
        Assert.assertFalse(string.isRangeValid(range));
    }

    @Test
    public void testIsRangeValid_withNullRange() {
        Assert.assertFalse(string.isRangeValid(null));
    }

    @Test
    public void testSetCharacter_objectChar() {
        final AsciiCharacter newChar = new AsciiCharacter('Z');
        string.setCharacter(0, newChar);

        // Using == compares object references:
        Assert.assertTrue(newChar == string.getCharacters()[0]);
    }

    @Test
    public void testSetCharacter_objectChar_withNegativeColumnIndex() {
        string.setCharacter(-1, new AsciiCharacter('Z'));
        Assert.assertEquals('A', string.getCharacters()[0].getCharacter());
    }

    @Test
    public void testSetCharacter_objectChar_withColumnIndexLargerThanStringLength() {
        string.setCharacter(string.getCharacters().length + 1, new AsciiCharacter('Z'));
        Assert.assertEquals('A', string.getCharacters()[0].getCharacter());
    }

    @Test
    public void testSetCharacter_objectChar_withNullCharacter() {
        string.setCharacter(0, null);
        Assert.assertEquals('A', string.getCharacters()[0].getCharacter());
    }

    @Test
    public void testSetCharacter_primitiveChar() {
        string.setCharacter(0, 'Z');
        Assert.assertEquals('Z', string.getCharacters()[0].getCharacter());
    }

    @Test
    public void testSetCharacter_primitiveChar_withNegativeColumnIndex() {
        string.setCharacter(-1, 'Z');
        Assert.assertEquals('A', string.getCharacters()[0].getCharacter());
    }

    @Test
    public void testSetCharacter_primitiveChar_withColumnIndexLargerThanStringLength() {
        string.setCharacter(string.getCharacters().length + 1, 'Z');
        Assert.assertEquals('A', string.getCharacters()[0].getCharacter());
    }

    @Test
    public void testSetCharacters() {
        for (int i  = 0 ; i < string.getCharacters().length ; i++) {
            string = new AsciiString(testString);

            final IntRange range = new IntRange(0, i);
            string.setCharacters(range, 'Z');

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

    @Test
    public void testSetCharacters_withNullRange() {
        string.setCharacters(null, 'Z');

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertNotEquals('Z', character.getCharacter());
        }
    }

    @Test
    public void testSetCharacters_withInvalidRange() {
        string.setCharacters(new IntRange(-1, string.getCharacters().length), 'Z');

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals('Z', character.getCharacter());
        }
    }

    @Test
    public void testSetAllCharacters() {
        string.setAllCharacters('Z');

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals('Z', character.getCharacter());
        }
    }

    @Test
    public void testApplyColorGradient_toBackgroundOfAllCharacters() {
        string.applyColorGradient(Color.RED, Color.BLUE, true);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertNotEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test
    public void testApplyColorGradient_toForegroundOfAllCharacters() {
        string.applyColorGradient(Color.RED, Color.BLUE, false);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertNotEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test
    public void testApplyColorGradient_toBackgroundOfRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);

        string.applyColorGradient(range, Color.RED, Color.BLUE, true);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertNotEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test
    public void testApplyColorGradient_toForegroundOfRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);

        string.applyColorGradient(range, Color.RED, Color.BLUE, false);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertNotEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test
    public void testApplyColorGradient_toRange_withNullRange() {
        string.applyColorGradient(null, Color.RED, Color.BLUE, true);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test
    public void testApplyColorGradient_toRange_withInvalidRange() {
        final IntRange range = new IntRange(-1, string.getCharacters().length);

        string.applyColorGradient(range, Color.RED, Color.BLUE, true);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertNotEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testApplyColorGradient_toRange_withNullColorFrom() {
        final IntRange range = new IntRange(0, string.getCharacters().length);

        string.applyColorGradient(range, null, Color.BLUE, true);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testApplyColorGradient_toRange_withNullColorTo() {
        final IntRange range = new IntRange(0, string.getCharacters().length);

        string.applyColorGradient(range, Color.RED, null, true);
    }

    @Test
    public void testInvertColors_toAllCharacters() {
        string.invertColors();

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.WHITE, character.getBackgroundColor());
            Assert.assertEquals(Color.BLACK, character.getForegroundColor());
        }
    }

    @Test
    public void testInvertColors_toRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.invertColors(range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.WHITE, character.getBackgroundColor());
            Assert.assertEquals(Color.BLACK, character.getForegroundColor());
        }
    }

    @Test
    public void testInvertColors_toRange_withNullRange() {
        string.invertColors(null);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test
    public void testInvertColors_toRange_withInvalidRange() {
        final IntRange range = new IntRange(-1, string.getCharacters().length);
        string.invertColors(range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.WHITE, character.getBackgroundColor());
            Assert.assertEquals(Color.BLACK, character.getForegroundColor());
        }
    }
}
