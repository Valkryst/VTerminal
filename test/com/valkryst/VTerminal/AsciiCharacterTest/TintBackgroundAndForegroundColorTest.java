package com.valkryst.VTerminal.AsciiCharacterTest;

import com.valkryst.VTerminal.AsciiCharacter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class TintBackgroundAndForegroundColorTest {
    private AsciiCharacter character;

    @Before
    public void initializeCharacter() {
        character = new AsciiCharacter('A');
    }

    @Test
    public void withNoTint() {
        character.setBackgroundColor(Color.RED);
        character.setForegroundColor(Color.BLUE);
        character.tintBackgroundAndForegroundColor(0.0);

        Assert.assertEquals(Color.RED, character.getBackgroundColor());
        Assert.assertEquals(Color.BLUE, character.getForegroundColor());
    }

    @Test
    public void withFullTint() {
        character.setBackgroundColor(Color.RED);
        character.setForegroundColor(Color.BLUE);
        character.tintBackgroundAndForegroundColor(1.0);

        Assert.assertEquals(Color.WHITE, character.getBackgroundColor());
        Assert.assertEquals(Color.WHITE, character.getForegroundColor());
    }

    @Test
    public void withPartialTint() {
        character.setBackgroundColor(Color.RED);
        character.setForegroundColor(Color.BLUE);
        character.tintBackgroundAndForegroundColor(0.5);

        Assert.assertNotEquals(Color.RED, character.getBackgroundColor());
        Assert.assertNotEquals(Color.WHITE, character.getBackgroundColor());

        Assert.assertNotEquals(Color.BLUE, character.getForegroundColor());
        Assert.assertNotEquals(Color.WHITE, character.getForegroundColor());
    }
}
