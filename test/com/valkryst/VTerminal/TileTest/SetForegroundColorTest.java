package com.valkryst.VTerminal.TileTest;

import com.valkryst.VTerminal.Tile;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class SetForegroundColorTest {
    private Tile character;

    @Before
    public void initializeCharacter() {
        character = new Tile('A');
    }

    @Test
    public void withValidInput() {
        character.setForegroundColor(Color.RED);
        Assert.assertEquals(character.getForegroundColor(), Color.RED);
    }

    @Test
    public void withColorThatIsTheSameAsTheExistingForegroundColor() {
        character.setForegroundColor(character.getForegroundColor());
    }
}
