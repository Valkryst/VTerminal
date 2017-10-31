package com.valkryst.VTerminal.AsciiStringTest;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.misc.IntRange;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class ApplyTintGradientTest {
    private AsciiString string;

    @Before
    public void initializeString() {
        string = new AsciiString("ABCDEFGHJIKLMNOP");
        string.setBackgroundColor(Color.BLACK);
        string.setForegroundColor(Color.WHITE);
    }

    @Test
    public void toBackgroundOfAllCharacters_withValidInput() {
        string.applyTintGradient(true);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertNotEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test
    public void toForegroundOfAllCharacters_withValidInput() {
        string.applyTintGradient(  false);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertNotEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test
    public void toBackgroundOfRange_withValidInput() {
        final IntRange range = new IntRange(0, string.getCharacters().length);

        string.applyTintGradient(range,  true);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertNotEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test
    public void toForegroundOfRange_withValidInput() {
        final IntRange range = new IntRange(0, string.getCharacters().length);

        string.applyTintGradient(range,   false);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertNotEquals(Color.WHITE, character.getForegroundColor());
        }
    }
}
