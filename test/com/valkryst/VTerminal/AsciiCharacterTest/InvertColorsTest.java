package com.valkryst.VTerminal.AsciiCharacterTest;

import com.valkryst.VTerminal.AsciiCharacter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class InvertColorsTest {
    private AsciiCharacter character;

    @Before
    public void initializeCharacter() {
        character = new AsciiCharacter('A');
    }

    @Test
    public void testInvertColors() {
        character.setBackgroundColor(Color.BLACK);
        character.setForegroundColor(Color.WHITE);

        character.invertColors();

        Assert.assertEquals(character.getBackgroundColor(), Color.WHITE);
        Assert.assertEquals(character.getForegroundColor(), Color.BLACK);
    }
}
