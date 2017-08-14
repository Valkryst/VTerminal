package com.valkryst.VTerminal.AsciiTileTest;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiTile;
import org.junit.Assert;
import org.junit.Test;

import java.awt.Color;

public class ConstructorTest {
    @Test
    public void testConstructor_withValidInput() {
        new AsciiTile('?');
    }

    @Test(expected=NullPointerException.class)
    public void testConstructor_withNullCharacter() {
        new AsciiTile(null);
    }

    @Test
    public void testConstructor_withValidCharacter() {
        final AsciiCharacter character = new AsciiCharacter('a');
        character.setHidden(true);
        character.setBackgroundColor(Color.BLUE);
        character.setForegroundColor(Color.RED);
        character.getBoundingBox().setSize(66, 67);
        character.getBoundingBox().setLocation(68, 69);
        character.setUnderlined(true);
        character.setUnderlineThickness(1);
        character.setFlippedHorizontally(true);
        character.setFlippedVertically(true);

        final AsciiTile tile = new AsciiTile(character);
        Assert.assertEquals(true, tile.isHidden());
        Assert.assertEquals(Color.BLUE, tile.getBackgroundColor());
        Assert.assertEquals(Color.RED, tile.getForegroundColor());
        Assert.assertEquals(66, tile.getBoundingBox().width);
        Assert.assertEquals(67, tile.getBoundingBox().height);
        Assert.assertEquals(68, tile.getBoundingBox().x);
        Assert.assertEquals(69, tile.getBoundingBox().y);
        Assert.assertEquals(true, tile.isUnderlined());
        Assert.assertEquals(1, tile.getUnderlineThickness());
        Assert.assertEquals(true, tile.isFlippedHorizontally());
        Assert.assertEquals(true, tile.isFlippedVertically());
    }
}
