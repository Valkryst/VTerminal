package com.valkryst.VTerminal.AsciiCharacterTest;

import com.valkryst.VTerminal.AsciiCharacter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class ShadeForegroundColorTest {
    private AsciiCharacter character;

    @Before
    public void initializeCharacter() {
        character = new AsciiCharacter('A');
    }

    @Test
    public void withNoShade() {
        character.setForegroundColor(Color.RED);
        character.shadeForegroundColor(0.0);

        Assert.assertEquals(Color.RED, character.getForegroundColor());
    }

    @Test
    public void withFullShade() {
        character.setForegroundColor(Color.RED);
        character.shadeForegroundColor(1.0);

        Assert.assertEquals(Color.BLACK, character.getForegroundColor());
    }

    @Test
    public void withPartialShade() {
        character.setForegroundColor(Color.RED);
        character.shadeForegroundColor(0.5);

        Assert.assertNotEquals(Color.RED, character.getForegroundColor());
        Assert.assertNotEquals(Color.BLACK, character.getForegroundColor());
    }
}
