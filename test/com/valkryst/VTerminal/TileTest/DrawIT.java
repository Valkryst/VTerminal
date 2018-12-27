package com.valkryst.VTerminal.TileTest;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.misc.ImageCache;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class DrawIT {
    private Tile character;
    private Font font;

    public DrawIT() throws IOException {
        font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt", 1);
    }

    @Before
    public void initializeCharacter() {
        character = new Tile('?');
        character.setBackgroundColor(Color.BLACK);
        character.setForegroundColor(Color.WHITE);
    }

    @Test
    public void testWithValidInputs_withDefaultSettings() {
        final int width = font.getWidth();
        final int height = font.getHeight();
        final BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        character.draw((Graphics2D) temp.getGraphics(), new ImageCache(font), 0, 0);
    }

    @Test
    public void testWithValidInputs_withHiddenChar() {
        final int width = font.getWidth();
        final int height = font.getHeight();
        final BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        character.setHidden(true);
        character.draw((Graphics2D) temp.getGraphics(), new ImageCache(font), 0, 0);
    }

    @Test
    public void testWithValidInputs_withHorizontalFlip() {
        final int width = font.getWidth();
        final int height = font.getHeight();
        final BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        character.setFlippedHorizontally(true);
        character.draw((Graphics2D) temp.getGraphics(), new ImageCache(font), 0, 0);
    }

    @Test
    public void testWithValidInputs_withVerticalFlip() {
        final int width = font.getWidth();
        final int height = font.getHeight();
        final BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        character.setFlippedVertically(true);
        character.draw((Graphics2D) temp.getGraphics(), new ImageCache(font), 0, 0);
    }

    @Test
    public void testWithValidInputs_withUnderline() {
        final int width = font.getWidth();
        final int height = font.getHeight();
        final BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        character.setUnderlined(true);
        character.draw((Graphics2D) temp.getGraphics(), new ImageCache(font), 0, 0);
    }
}
