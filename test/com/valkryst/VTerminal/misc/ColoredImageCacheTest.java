package com.valkryst.VTerminal.misc;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import org.junit.Assert;
import org.junit.Test;

import java.awt.Image;
import java.io.IOException;
import java.net.URISyntaxException;

public class ColoredImageCacheTest {
    private final Font font;
    private final Font otherFont;

    public ColoredImageCacheTest() throws IOException, URISyntaxException {
        font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt", 1);
        otherFont = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt", 2);
    }

    @Test
    public void testConstructor_oneParam_withValidFont() {
        final ColoredImageCache cache = new ColoredImageCache(font);
        Assert.assertEquals(font, cache.getFont());
    }

    @Test(expected=NullPointerException.class)
    public void testConstructor_oneParam_withNullFont() {
        new ColoredImageCache(null);
    }

    @Test
    public void testRetrieveFromCache_withValidInput() {
        final ColoredImageCache cache = new ColoredImageCache(font);
        final Image image = cache.retrieveFromCache(new AsciiCharacter('A'));
        Assert.assertNotNull(image);
    }

    @Test
    public void testRetrieveFromCache_withInvalidInput() {
        final ColoredImageCache cache = new ColoredImageCache(font);
        final Image image = cache.retrieveFromCache(new AsciiCharacter('\u1F5E'));
        Assert.assertNotNull(image);
    }

    @Test(expected=NullPointerException.class)
    public void testRetrieveFromCache_withNullCharacter() {
        final ColoredImageCache cache = new ColoredImageCache(font);
        cache.retrieveFromCache(null);
    }
}
