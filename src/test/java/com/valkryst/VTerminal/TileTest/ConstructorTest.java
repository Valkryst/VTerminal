package com.valkryst.VTerminal.TileTest;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.GraphicTile;
import org.junit.Assert;
import org.junit.Test;

import java.awt.Color;

public class ConstructorTest {
    @Test
    public void withAllCharacters() {
        for (byte i = 0 ; i < Byte.MAX_VALUE ; i++) {
            new Tile((char) i);
        }
    }

    @Test
    public void withValidAsciiTile() {
        final GraphicTile tile = new GraphicTile('a');
        tile.setHidden(true);
        tile.setBackgroundColor(Color.BLUE);
        tile.setForegroundColor(Color.RED);
        tile.setUnderlined(true);
        tile.setUnderlineThickness(1);
        tile.setFlippedHorizontally(true);
        tile.setFlippedVertically(true);

        final Tile character = new Tile(tile);
        Assert.assertTrue(character.isHidden());
        Assert.assertEquals(Color.BLUE, character.getBackgroundColor());
        Assert.assertEquals(Color.RED, character.getForegroundColor());
        Assert.assertTrue(character.isUnderlined());
        Assert.assertEquals(1, character.getUnderlineThickness());
    }
}
