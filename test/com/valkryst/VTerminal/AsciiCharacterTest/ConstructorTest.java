package com.valkryst.VTerminal.AsciiCharacterTest;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiTile;
import org.junit.Assert;
import org.junit.Test;

import java.awt.Color;

public class ConstructorTest {
    @Test
    public void withAllCharacters() {
        for (byte i = 0 ; i < Byte.MAX_VALUE ; i++) {
            new AsciiCharacter((char) i);
        }
    }

    @Test(expected=NullPointerException.class)
    public void withNullAsciiTile() {
        new AsciiCharacter(null);
    }

    @Test
    public void withValidAsciiTile() {
        final AsciiTile tile = new AsciiTile('a');
        tile.setHidden(true);
        tile.setBackgroundColor(Color.BLUE);
        tile.setForegroundColor(Color.RED);
        tile.getBoundingBox().setSize(66, 67);
        tile.getBoundingBox().setLocation(68, 69);
        tile.setUnderlined(true);
        tile.setUnderlineThickness(1);
        tile.setFlippedHorizontally(true);
        tile.setFlippedVertically(true);

        final AsciiCharacter character = new AsciiCharacter(tile);
        Assert.assertEquals(true, character.isHidden());
        Assert.assertEquals(Color.BLUE, character.getBackgroundColor());
        Assert.assertEquals(Color.RED, character.getForegroundColor());
        Assert.assertEquals(66, character.getBoundingBox().width);
        Assert.assertEquals(67, character.getBoundingBox().height);
        Assert.assertEquals(68, character.getBoundingBox().x);
        Assert.assertEquals(69, character.getBoundingBox().y);
        Assert.assertEquals(true, character.isUnderlined());
        Assert.assertEquals(1, character.getUnderlineThickness());
    }
}
