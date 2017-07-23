package com.valkryst.VTerminal.AsciiCharacterTest;

import com.valkryst.VTerminal.AsciiCharacter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class ShadeBackgroundAndForegroundColorTest {
    private AsciiCharacter character;

    @Before
    public void initializeCharacter() {
        character = new AsciiCharacter('A');
    }

    @Test
    public void withNoShade() {
        character.setBackgroundColor(Color.RED);
        character.setForegroundColor(Color.BLUE);
        character.shadeBackgroundAndForegroundColor(0.0);

        Assert.assertEquals(Color.RED, character.getBackgroundColor());
        Assert.assertEquals(Color.BLUE, character.getForegroundColor());
    }

    @Test
    public void withFullShade() {
        character.setBackgroundColor(Color.RED);
        character.setForegroundColor(Color.BLUE);
        character.shadeBackgroundAndForegroundColor(1.0);

        Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
        Assert.assertEquals(Color.BLACK, character.getForegroundColor());
    }

    @Test
    public void withPartialShade() {
        character.setBackgroundColor(Color.RED);
        character.setForegroundColor(Color.BLUE);
        character.shadeBackgroundAndForegroundColor(0.5);

        Assert.assertNotEquals(Color.RED, character.getBackgroundColor());
        Assert.assertNotEquals(Color.BLACK, character.getBackgroundColor());

        Assert.assertNotEquals(Color.BLUE, character.getForegroundColor());
        Assert.assertNotEquals(Color.BLACK, character.getForegroundColor());
    }
}
