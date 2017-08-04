package com.valkryst.VTerminal.AsciiStringTest;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.misc.IntRange;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class TintColorsTest {
    private AsciiString string;

    @Before
    public void initializeString() {
        string = new AsciiString("ABCDEFGHJIKLMNOP");
        string.setBackgroundColor(Color.BLACK);
        string.setForegroundColor(Color.BLACK);
    }

    @Test
    public void toBackgroundOfAllCharacters() {
        string.tintColors(1.0, true);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.WHITE, character.getBackgroundColor());
            Assert.assertEquals(Color.BLACK, character.getForegroundColor());
        }
    }

    @Test
    public void toForegroundOfAllCharacters() {
        string.tintColors(1.0, false);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test
    public void toBackgroundOfRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);

        string.tintColors(range, 1.0, true);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.WHITE, character.getBackgroundColor());
            Assert.assertEquals(Color.BLACK, character.getForegroundColor());
        }
    }

    @Test
    public void toForegroundOfRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);

        string.tintColors(range, 1.0, false);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test(expected=NullPointerException.class)
    public void toRange_withNullRange() {
        string.tintColors(null, 1.0, false);
    }
}
