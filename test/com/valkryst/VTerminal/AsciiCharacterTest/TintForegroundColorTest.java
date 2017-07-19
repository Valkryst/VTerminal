package com.valkryst.VTerminal.AsciiCharacterTest;

import com.valkryst.VTerminal.AsciiCharacter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class TintForegroundColorTest {
    private AsciiCharacter character;

    @Before
    public void initializeCharacter() {
        character = new AsciiCharacter('A');
    }

    @Test
    public void withNoTint() {
        character.setForegroundColor(Color.RED);
        character.tintForegroundColor(0.0);

        Assert.assertEquals(Color.RED, character.getForegroundColor());
    }

    @Test
    public void withFullTint() {
        character.setForegroundColor(Color.RED);
        character.tintForegroundColor(1.0);

        Assert.assertEquals(Color.WHITE, character.getForegroundColor());
    }

    @Test
    public void withPartialTint() {
        character.setForegroundColor(Color.RED);
        character.tintForegroundColor(0.5);

        Assert.assertNotEquals(Color.RED, character.getForegroundColor());
        Assert.assertNotEquals(Color.WHITE, character.getForegroundColor());
    }
}
