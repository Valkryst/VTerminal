package com.valkryst.VTerminal.AsciiStringTest;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.misc.IntRange;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class InvertColorsTest {
    private AsciiString string;

    @Before
    public void initializeString() {
        string = new AsciiString("ABCDEFGHJIKLMNOP");
        string.setBackgroundColor(Color.BLACK);
        string.setForegroundColor(Color.WHITE);
    }

    @Test
    public void toAllCharacters() {
        string.invertColors();

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.WHITE, character.getBackgroundColor());
            Assert.assertEquals(Color.BLACK, character.getForegroundColor());
        }
    }

    @Test
    public void toRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.invertColors(range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.WHITE, character.getBackgroundColor());
            Assert.assertEquals(Color.BLACK, character.getForegroundColor());
        }
    }

    @Test(expected=NullPointerException.class)
    public void toRange_withNullRange() {
        string.invertColors(null);
    }
}
