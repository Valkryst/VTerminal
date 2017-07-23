package com.valkryst.VTerminal.AsciiCharacterTest;

import com.valkryst.VTerminal.AsciiCharacter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class EqualsTest {
    private AsciiCharacter characterA;
    private AsciiCharacter characterB;

    @Before
    public void initializeCharacters() {
        characterA = new AsciiCharacter('A');
        characterB = new AsciiCharacter('A');
    }

    @Test
    public void withSelf() {
        Assert.assertTrue(characterA.equals(characterA));
    }

    @Test
    public void withEqualCharacters() {
        Assert.assertTrue(characterA.equals(characterB));
    }

    @Test
    public void withNonEqualCharacters() {
        characterB = new AsciiCharacter('B');
        Assert.assertFalse(characterA.equals(characterB));
    }

    @Test
    public void withNonAsciiCharacterObject() {
        Assert.assertFalse(characterA.equals(666));
    }

    @Test
    public void withNonEqualBackgroundColors() {
        characterA.setBackgroundColor(Color.BLUE);
        characterB.setBackgroundColor(Color.RED);

        Assert.assertFalse(characterA.equals(characterB));
    }

    @Test
    public void withNonEqualForegroundColors() {
        characterA.setForegroundColor(Color.BLUE);
        characterB.setForegroundColor(Color.RED);

        Assert.assertFalse(characterA.equals(characterB));
    }
}
