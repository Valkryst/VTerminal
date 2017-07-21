package com.valkryst.VTerminal.font;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class FontTest {
    private final Font font;

    public FontTest() throws IOException, URISyntaxException {
        font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt", 1);
    }

    @Test
    public void testConstructor_withZeroScale() throws IOException, URISyntaxException {
        FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt", 0);
        Assert.assertTrue(true);
    }

    @Test
    public void testGetCharacterImage_withAllSymbolAlphabeticAndNumberAsciiCharacters() {
        for (byte i = 33 ; i < 127 ; i++) {
            Assert.assertNotNull(font.getCharacterImage((char) i));
        }
    }

    @Test
    public void testGetWidth() {
        Assert.assertEquals(12, font.getWidth());
    }

    @Test
    public void testGetHeight() {
        Assert.assertEquals(24, font.getHeight());
    }
}
