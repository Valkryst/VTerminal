package com.valkryst.VTerminal.AsciiCharacterTest;

import com.valkryst.VTerminal.Tile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ToStringTest {
    private Tile character;

    @Before
    public void initializeCharacter() {
        character = new Tile('A');
    }

    @Test
    public void testToString() {
        Assert.assertTrue(character.toString().length() > 0);
    }
}
