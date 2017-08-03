package com.valkryst.VTerminal.AsciiStringTest;

import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.misc.ColoredImageCache;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;

public class DrawTest {
    private AsciiString string;
    private Font font;

    public DrawTest() throws IOException, URISyntaxException {
        font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt", 1);
    }

    @Before
    public void initializeString() {
        string = new AsciiString("ABCDEFGHJIKLMNOP");
        string.setBackgroundColor(Color.BLACK);
        string.setForegroundColor(Color.WHITE);
    }

    @Test(expected=NullPointerException.class)
    public void testWithNullGC() {
        string.draw(null, new ColoredImageCache(font), 0);
    }

    @Test(expected=NullPointerException.class)
    public void testWithNullImageCache() {
        final int width = font.getWidth() * string.getCharacters().length;
        final int height = font.getHeight();
        final BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);


        string.draw((Graphics2D) temp.getGraphics(), null, 0);
    }

    @Test
    public void testWithRowBelowZero() {
        final int width = font.getWidth() * string.getCharacters().length;
        final int height = font.getHeight();
        final BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        string.draw((Graphics2D) temp.getGraphics(), new ColoredImageCache(font), -1);
    }

    @Test
    public void testWithRowAtZero() {
        final int width = font.getWidth() * string.getCharacters().length;
        final int height = font.getHeight();
        final BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        string.draw((Graphics2D) temp.getGraphics(), new ColoredImageCache(font), 0);
    }
}
