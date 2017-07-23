package com.valkryst.VTerminal.AsciiCharacterTest;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.AsciiCharacter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EnableBlinkEffectTest {
    private AsciiCharacter character;

    @Before
    public void initializeCharacter() {
        character = new AsciiCharacter('A');
    }

    @Test
    public void withValidInput() {
        character.enableBlinkEffect((short) 666, new Radio<>());

        Assert.assertEquals(666, character.getMillsBetweenBlinks());
    }

    @Test(expected=NullPointerException.class)
    public void withNullRadio() {
        character.enableBlinkEffect((short) 1000, null);
    }

    @Test
    public void withMillsBetweenBlinksEqualToZero() {
        character.enableBlinkEffect((short) 0, new Radio<>());

        Assert.assertEquals(1000, character.getMillsBetweenBlinks());
    }

    @Test
    public void withMillsBetweenBlinksLessThanZero() {
        character.enableBlinkEffect((short) -1, new Radio<>());

        Assert.assertEquals(1000, character.getMillsBetweenBlinks());
    }
}
