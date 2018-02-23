package com.valkryst.VTerminal.AsciiTileTest;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.GraphicTile;
import org.junit.Assert;
import org.junit.Test;

import java.awt.Color;

public class ConstructorTest {
    @Test
    public void testConstructor_withValidInput() {
        new GraphicTile('?');
    }

    @Test(expected=NullPointerException.class)
    public void testConstructor_withNullCharacter() {
        new GraphicTile(null);
    }

    @Test
    public void testConstructor_withValidCharacter() {
        final Tile character = new Tile('a');
        character.setHidden(true);
        character.setBackgroundColor(Color.BLUE);
        character.setForegroundColor(Color.RED);
        character.setUnderlined(true);
        character.setUnderlineThickness(1);

        final GraphicTile tile = new GraphicTile(character);
        Assert.assertEquals(true, tile.isHidden());
        Assert.assertEquals(Color.BLUE, tile.getBackgroundColor());
        Assert.assertEquals(Color.RED, tile.getForegroundColor());
        Assert.assertEquals(true, tile.isUnderlined());
        Assert.assertEquals(1, tile.getUnderlineThickness());
    }
}
