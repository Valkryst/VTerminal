package com.valkryst.VTerminal.AsciiCharacterTest;

import com.valkryst.VTerminal.AsciiCharacter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SetUnderlineThicknessTest {
    private AsciiCharacter character;

    @Before
    public void initializeCharacter() {
        character = new AsciiCharacter('A');
        character.getBoundingBox().setSize(2, 2);
    }

    @Test
    public void withThicknessWithinBoundingBox() {
        character.setUnderlineThickness(2);
        Assert.assertEquals(2, character.getUnderlineThickness());
    }

    @Test
    public void withThicknessGreaterThanBoundingBoxHeight() {
        int originalThickness = character.getUnderlineThickness();

        character.setUnderlineThickness((int) character.getBoundingBox().getHeight() + 1);
        Assert.assertEquals(originalThickness, character.getUnderlineThickness());
    }

    @Test
    public void withThicknessLessThanOrEqualToZero() {
        for (int i = 0 ; i > -10 ; i--) {
            character.setUnderlineThickness(i);
            Assert.assertEquals(1, character.getUnderlineThickness());
        }
    }
}
