package com.valkryst.VTerminal.font;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class FontTest {
    private final Font font;

    public FontTest() throws IOException {
        font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt", 1);
    }

    @Test
    public void testConstructor_withZeroScale() throws IOException {
        FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt", 0);
        Assert.assertTrue(true);
    }

    @Test
    public void testIsCharacterSupported_withSupportedCharacter() {
        Assert.assertTrue(font.isCharacterSupported('?'));
    }

    @Test
    public void testIsCharacterSupported_withNonSupportedCharacter() {
        Assert.assertFalse(font.isCharacterSupported('\u1F5E'));
    }

    @Test
    public void testGetCharacterImage_withAllSymbolAlphabeticAndNumberAsciiCharacters() {
        for (byte i = 33 ; i < 127 ; i++) {
            Assert.assertNotNull(font.getCharacterImage((char) i));
        }
    }
}
