package com.valkryst.VTerminal.AsciiStringTest;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.misc.IntRange;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class SetUnderlinedTest {
    private AsciiString string;

    @Before
    public void initializeString() {
        string = new AsciiString("ABCDEFGHJIKLMNOP");
        string.setBackgroundColor(Color.BLACK);
        string.setForegroundColor(Color.WHITE);
    }

    @Test
    public void toAllCharacters() {
        string.setUnderlined(true);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertTrue(character.isUnderlined());
        }
    }

    @Test
    public void toRange() {
        final IntRange range = new IntRange(0, string.getCharacters().length);
        string.setUnderlined(range, true);

        for (final AsciiCharacter character : string.getCharacters()) {
            Assert.assertTrue(character.isUnderlined());
        }
    }
}
