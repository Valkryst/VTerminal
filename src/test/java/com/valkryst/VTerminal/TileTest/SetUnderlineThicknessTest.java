package com.valkryst.VTerminal.TileTest;

import com.valkryst.VTerminal.Tile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SetUnderlineThicknessTest {
    private Tile character;

    @Before
    public void initializeCharacter() {
        character = new Tile('A');
    }

    @Test
    public void withThicknessWithinBoundingBox() {
        character.setUnderlineThickness(2);
        Assert.assertEquals(2, character.getUnderlineThickness());
    }

    @Test
    public void withThicknessLessThanOrEqualToZero() {
        for (int i = 0 ; i > -10 ; i--) {
            character.setUnderlineThickness(i);
            Assert.assertEquals(1, character.getUnderlineThickness());
        }
    }
}
