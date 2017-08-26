package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.builder.component.ScreenBuilder;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.misc.ImageCache;
import org.junit.Test;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;

public class LayerTest {
    private final Font font;

    public LayerTest() throws IOException, URISyntaxException {
        font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt", 1);
    }

    @Test
    public void testConstructor() {
        new Layer(0, 0, 1, 1);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testDraw_unsupported() {
        final ScreenBuilder screenBuilder = new ScreenBuilder();
        screenBuilder.setColumnIndex(0);
        screenBuilder.setRowIndex(0);
        screenBuilder.setWidth(1);
        screenBuilder.setHeight(1);

        final Screen screen = screenBuilder.build();
        new Layer(0, 0, 1, 1).draw(screen);
    }

    @Test(expected=NullPointerException.class)
    public void testDraw_withNullGraphics() {
        final Layer layer = new Layer(0, 0,1, 1);
        layer.draw(null, new ImageCache(font));
    }

    @Test(expected=NullPointerException.class)
    public void testDraw_withNullCache() {
        final int width = font.getWidth();
        final int height = font.getHeight();
        final BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        final Layer layer = new Layer(0, 0,1, 1);
        layer.draw((Graphics2D) temp.getGraphics(), null);
    }

    @Test
    public void testDraw_withValidInputs() {
        final int width = font.getWidth();
        final int height = font.getHeight();
        final BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        final Layer layer = new Layer(0, 0,1, 1);
        layer.draw((Graphics2D) temp.getGraphics(), new ImageCache(font));
    }
}
