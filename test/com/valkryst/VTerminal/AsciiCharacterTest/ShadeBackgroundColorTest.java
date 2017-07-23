package com.valkryst.VTerminal.AsciiCharacterTest;

import com.valkryst.VTerminal.AsciiCharacter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class ShadeBackgroundColorTest {
    private AsciiCharacter character;

    @Before
    public void initializeCharacter() {
        character = new AsciiCharacter('A');
    }

    @Test
    public void withNoShade() {
        character.setBackgroundColor(Color.RED);
        character.shadeBackgroundColor(0.0);

        Assert.assertEquals(Color.RED, character.getBackgroundColor());
    }

    @Test
    public void withFullShade() {
        character.setBackgroundColor(Color.RED);
        character.shadeBackgroundColor(1.0);

        Assert.assertEquals(Color.BLACK, character.getBackgroundColor());
    }

    @Test
    public void withPartialShade() {
        character.setBackgroundColor(Color.RED);
        character.shadeBackgroundColor(0.5);

        Assert.assertNotEquals(Color.RED, character.getBackgroundColor());
        Assert.assertNotEquals(Color.BLACK, character.getBackgroundColor());
    }
}
