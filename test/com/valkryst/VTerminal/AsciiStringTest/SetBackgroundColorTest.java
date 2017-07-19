package com.valkryst.VTerminal.AsciiStringTest;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.misc.IntRange;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class SetBackgroundColorTest {
    private AsciiString string;

    @Before
    public void initializeString() {
        string = new AsciiString("ABCDEFGHJIKLMNOP");
        string.setBackgroundColor(Color.BLACK);
        string.setForegroundColor(Color.WHITE);
    }

    @Test
    public void toAllCharacters() {
        string.setBackgroundColor(Color.RED);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.RED, character.getBackgroundColor());
            Assert.assertEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test
    public void toRange_withValidInput() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.setBackgroundColor(Color.RED, range);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertEquals(Color.RED, character.getBackgroundColor());
            Assert.assertEquals(Color.WHITE, character.getForegroundColor());
        }
    }

    @Test(expected=NullPointerException.class)
    public void toRange_withNullColor() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.setBackgroundColor(null, range);
    }

    @Test(expected=NullPointerException.class)
    public void toRange_withNullRange() {
        string.setBackgroundColor(Color.RED, null);
    }
}
