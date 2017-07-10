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

    @Test
    public void testSetCharacters_withNullRange() {
        string.setCharacters('Z', null);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertNotEquals('Z', character.getCharacter());
        }
    }

    @Test
    public void testSetCharacters_withInvalidRange() {
        string.setCharacters('Z', new IntRange(-1, string.getCharacters().length));

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

    @Test
    public void testSetBackgroundColor_toAllCharacters() {
        string.setBackgroundColor(Color.RED);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.RED, character.getBackgroundColor());
            Assert.assertEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test
    public void testSetForegroundColor_toAllCharacters() {
        string.setForegroundColor(Color.RED);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertEquals(Color.RED, character.getForegroundColor());
        }
    }

    @Test
    public void testSetBackgroundAndForegroundColor_toAllCharacters() {
        string.setBackgroundAndForegroundColor(Color.RED, Color.BLUE);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.RED, character.getBackgroundColor());
            Assert.assertEquals(Color.BLUE, character.getForegroundColor());
        }
    }

    @Test
    public void testSetBackgroundColor_toRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.setBackgroundColor(Color.RED, range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.RED, character.getBackgroundColor());
            Assert.assertEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test
    public void testSetBackgroundColor_toRange_withNullColor() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.setBackgroundColor(null, range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test
    public void testSetBackgroundColor_toRange_withNullRange() {
        string.setBackgroundColor(Color.RED, null);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test
    public void testSetBackgroundColor_toRange_withInvalidRange() {
        final IntRange range = new IntRange(-1, string.getCharacters().length);
        string.setBackgroundColor(Color.RED, range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.RED, character.getBackgroundColor());
            Assert.assertEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test
    public void testSetForegroundColor_toRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.setForegroundColor(Color.RED, range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertEquals(Color.RED, character.getForegroundColor());
        }
    }

    @Test
    public void testSetForegroundColor_toRange_withNullColor() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.setForegroundColor(null, range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test
    public void testSetForegroundColor_toRange_withNullRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.setForegroundColor(null, range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test
    public void testSetForegroundColor_toRange_withInvalidRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.setForegroundColor(Color.RED, range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertEquals(Color.RED, character.getForegroundColor());
        }
    }

    @Test
    public void testSetBackgroundAndForegroundColor_toRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.setBackgroundAndForegroundColor(Color.RED, Color.BLUE, range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.RED, character.getBackgroundColor());
            Assert.assertEquals(Color.BLUE, character.getForegroundColor());
        }
    }

    @Test
    public void testSetBackgroundAndForegroundColor_toRange_withNullBackgroundColor() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.setBackgroundAndForegroundColor(null, Color.BLUE, range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test
    public void testSetBackgroundAndForegroundColor_toRange_withNullForegroundColor() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.setBackgroundAndForegroundColor(Color.RED, null, range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test
    public void testSetBackgroundAndForegroundColor_toRange_withNullRange() {
        string.setBackgroundAndForegroundColor(Color.RED, Color.BLUE, null);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test
    public void testSetBackgroundAndForegroundColor_toRange_withInvalidRange() {
        final IntRange range = new IntRange(-1, string.getCharacters().length);
        string.setBackgroundAndForegroundColor(Color.RED, Color.BLUE, range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.RED, character.getBackgroundColor());
            Assert.assertEquals(Color.BLUE, character.getForegroundColor());
        }
    }

    @Test
    public void testSetHidden_toAllCharacters_toHidden() {
        string.setHidden(true);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertTrue(character.isHidden());
        }
    }

    @Test
    public void testSetHidden_toAllCharacters_toNotHidden() {
        string.setHidden(false);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertFalse(character.isHidden());
        }
    }

    @Test
    public void testFlipCharactersHorizontally_toAllCharacters() {
        string.flipCharactersHorizontally();

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertTrue(character.isFlippedHorizontally());
        }
    }

    @Test
    public void testUnFlipCharactersHorizontally_toAllCharacters() {
        string.flipCharactersHorizontally();
        string.unFlipCharactersHorizontally();

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertFalse(character.isFlippedHorizontally());
        }
    }

    @Test
    public void testFlipCharactersVertically_toAllCharacters() {
        string.flipCharactersVertically();

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertTrue(character.isFlippedVertically());
        }
    }

    @Test
    public void testUnFlipCharactersVertically_toAllCharacters() {
        string.flipCharactersVertically();
        string.unFlipCharactersVertically();

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertFalse(character.isFlippedVertically());
        }
    }

    @Test
    public void testFlipCharactersHorizontallyAndVertically_toAllCharacters() {
        string.flipCharactersHorizontallyAndVertically();

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertTrue(character.isFlippedHorizontally());
            Assert.assertTrue(character.isFlippedVertically());
        }
    }

    @Test
    public void testUnFlipCharactersHorizontallyAndVertically_toAllCharacters() {
        string.flipCharactersHorizontallyAndVertically();
        string.unFlipCharactersHorizontallyAndVertically();

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertFalse(character.isFlippedHorizontally());
            Assert.assertFalse(character.isFlippedVertically());
        }
    }

    @Test
    public void testFlipCharactersHorizontally_toRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.flipCharactersHorizontally(range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertTrue(character.isFlippedHorizontally());
        }
    }

    @Test
    public void testFlipCharactersHorizontally_toRange_withNullRange() {
        string.flipCharactersHorizontally(null);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertFalse(character.isFlippedHorizontally());
        }
    }

    @Test
    public void testFlipCharactersHorizontally_toRange_withInvalidRange() {
        final IntRange range = new IntRange(-1, string.getCharacters().length);
        string.flipCharactersHorizontally(range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertTrue(character.isFlippedHorizontally());
        }
    }

    @Test
    public void testUnFlipCharactersHorizontally_toRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.flipCharactersHorizontally(range);
        string.unFlipCharactersHorizontally(range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertFalse(character.isFlippedHorizontally());
        }
    }

    @Test
    public void testUnFlipCharactersHorizontally_toRange_withNullRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.flipCharactersHorizontally(range);
        string.unFlipCharactersHorizontally(null);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertTrue(character.isFlippedHorizontally());
        }
    }

    @Test
    public void testUnFlipCharactersHorizontally_toRange_withInvalidRange() {
        final IntRange range = new IntRange(-1, string.getCharacters().length);
        string.flipCharactersHorizontally(range);
        string.unFlipCharactersHorizontally(range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertFalse(character.isFlippedHorizontally());
        }
    }

    @Test
    public void testFlipCharactersVertically_toRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.flipCharactersVertically(range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertTrue(character.isFlippedVertically());
        }
    }

    @Test
    public void testFlipCharactersVertically_toRange_withNullRange() {
        string.flipCharactersVertically(null);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertFalse(character.isFlippedVertically());
        }
    }

    @Test
    public void testFlipCharactersVertically_toRange_withInvalidRange() {
        final IntRange range = new IntRange(-1, string.getCharacters().length);
        string.flipCharactersVertically(range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertTrue(character.isFlippedVertically());
        }
    }

    @Test
    public void testUnFlipCharactersVertically_toRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.flipCharactersVertically(range);
        string.unFlipCharactersVertically(range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertFalse(character.isFlippedVertically());
        }
    }

    @Test
    public void testUnFlipCharactersVertically_toRange_withNullRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.flipCharactersVertically(range);
        string.unFlipCharactersVertically(null);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertTrue(character.isFlippedVertically());
        }
    }

    @Test
    public void testUnFlipCharactersVertically_toRange_withInvalidRange() {
        final IntRange range = new IntRange(-1, string.getCharacters().length);
        string.flipCharactersVertically(range);
        string.unFlipCharactersVertically(range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertFalse(character.isFlippedVertically());
        }
    }

    @Test
    public void testFlipCharactersHorizontallyAndVertically_toRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.flipCharactersHorizontallyAndVertically(range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertTrue(character.isFlippedHorizontally());
            Assert.assertTrue(character.isFlippedVertically());
        }
    }

    @Test
    public void testFlipCharactersHorizontallyAndVertically_toRange_withNullRange() {
        string.flipCharactersHorizontallyAndVertically(null);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertFalse(character.isFlippedHorizontally());
            Assert.assertFalse(character.isFlippedVertically());
        }
    }

    @Test
    public void testFlipCharactersHorizontallyAndVertically_toRange_withInvalidRange() {
        final IntRange range = new IntRange(-1, string.getCharacters().length);
        string.flipCharactersHorizontallyAndVertically(range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertTrue(character.isFlippedHorizontally());
            Assert.assertTrue(character.isFlippedVertically());
        }
    }

    @Test
    public void testUnFlipCharactersHorizontallyAndVertically_toRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.flipCharactersHorizontallyAndVertically(range);
        string.unFlipCharactersHorizontallyAndVertically(range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertFalse(character.isFlippedHorizontally());
            Assert.assertFalse(character.isFlippedVertically());
        }
    }

    @Test
    public void testUnFlipCharactersHorizontallyAndVertically_toRange_withNullRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.flipCharactersHorizontallyAndVertically(range);
        string.unFlipCharactersHorizontallyAndVertically(null);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertTrue(character.isFlippedHorizontally());
            Assert.assertTrue(character.isFlippedVertically());
        }
    }

    @Test
    public void testUnFlipCharactersHorizontallyAndVertically_toRange_withInvalidRange() {
        final IntRange range = new IntRange(-1, string.getCharacters().length);
        string.flipCharactersHorizontallyAndVertically(range);
        string.unFlipCharactersHorizontallyAndVertically(range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertFalse(character.isFlippedHorizontally());
            Assert.assertFalse(character.isFlippedVertically());
        }
    }

    @Test
    public void testUnderLineCharacters_toAllCharacters() {
        string.underlineCharacters();

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertTrue(character.isUnderlined());
        }
    }

    @Test
    public void testUnUnderLineCharacters_toAllCharacters() {
        string.underlineCharacters();
        string.unUnderlineCharacters();

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertFalse(character.isUnderlined());
        }
    }

    @Test
    public void testUnderLineCharacters_toRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.underlineCharacters(range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertTrue(character.isUnderlined());
        }
    }

    @Test
    public void testUnderLineCharacters_toRange_withNullRange() {
        string.underlineCharacters(null);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertFalse(character.isUnderlined());
        }
    }

    @Test
    public void testUnderLineCharacters_toRange_withInvalidRange() {
        final IntRange range = new IntRange(-1, string.getCharacters().length);
        string.underlineCharacters(range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertTrue(character.isUnderlined());
        }
    }

    @Test
    public void testUnUnderLineCharacters_toRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.underlineCharacters(range);
        string.unUnderlineCharacters(range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertFalse(character.isUnderlined());
        }
    }

    @Test
    public void testUnUnderLineCharacters_toRange_withNullRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.underlineCharacters(range);
        string.unUnderlineCharacters(null);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertTrue(character.isUnderlined());
        }
    }

    @Test
    public void testUnUnderLineCharacters_toRange_withInvalidRange() {
        final IntRange range = new IntRange(-1, string.getCharacters().length);
        string.underlineCharacters(range);
        string.unUnderlineCharacters(range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertFalse(character.isUnderlined());
        }
    }
}
