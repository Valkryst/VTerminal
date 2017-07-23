package com.valkryst.VTerminal.AsciiStringTest;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.misc.IntRange;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class SetForegroundColorTest {
    private AsciiString string;

    @Before
    public void initializeString() {
        string = new AsciiString("ABCDEFGHJIKLMNOP");
        string.setBackgroundColor(Color.BLACK);
        string.setForegroundColor(Color.WHITE);
    }

    @Test
    public void toAllCharacters() {
        string.setForegroundColor(Color.RED);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertEquals(Color.RED, character.getForegroundColor());
        }
    }

    @Test
    public void toRange_withValidInput() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.setForegroundColor(Color.RED, range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
            Assert.assertEquals(Color.RED, character.getForegroundColor());
        }
    }

    @Test(expected=NullPointerException.class)
    public void toRange_withNullColor() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.setForegroundColor(null, range);
    }
}
