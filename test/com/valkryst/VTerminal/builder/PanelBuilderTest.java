package com.valkryst.VTerminal.builder;

import com.valkryst.VTerminal.component.Screen;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class PanelBuilderTest {
    private final Font font;
    private final PanelBuilder builder = new PanelBuilder();

    public PanelBuilderTest() throws IOException, URISyntaxException {
        font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt", 1);
    }

    @Before
    public void resetBuilder() {
        builder.reset();
    }

    @Test
    public void testConstructor() {
        new PanelBuilder();
    }

    /*
    @Test
    public void testReset() {
        final PanelBuilder builderDefault = new PanelBuilder();
        final PanelBuilder builderAltered = new PanelBuilder();

        builderAltered.setJFrame(new JFrame())
                      .setWidthInCharacters(1)
                      .setHeightInCharacters(2)
                      .setFont(font)
                      .setScreen(new Screen(0, 0, 1, 1));

        Assert.assertNotEquals(builderDefault.getFrame(), builderAltered.getFrame());
        Assert.assertNotEquals(builderDefault.getWidthInCharacters(), builderAltered.getWidthInCharacters());
        Assert.assertNotEquals(builderDefault.getHeightInCharacters(), builderAltered.getHeightInCharacters());
        Assert.assertNotEquals(builderDefault.getFont(), builderAltered.getFont());
        Assert.assertNotEquals(builderDefault.getScreen(), builderAltered.getScreen());
    }

    @Test
    public void testSetJFrame() {
        builder.setJFrame(new JFrame());

        Assert.assertNotEquals(null, builder.getFrame());
    }
    */

    @Test
    public void testSetJFrame_withNullJFrame() {
        builder.setFrame(null);

        Assert.assertEquals(null, builder.getFrame());
    }

    @Test
    public void testSetWidthInCharacters() {
        builder.setWidthInCharacters(10);

        Assert.assertEquals(10, builder.getWidthInCharacters());
    }

    @Test
    public void testSetWidthInCharacters_withWidthBelowOne() {
        for (int i = 0 ; i < 10 ; i++) {
            builder.setWidthInCharacters(-i);

            Assert.assertEquals(1, builder.getWidthInCharacters());
        }
    }

    @Test
    public void testSetHeightInCharacters() {
        builder.setHeightInCharacters(10);

        Assert.assertEquals(10, builder.getHeightInCharacters());
    }

    @Test
    public void testSetHeightInCharacters_withHeightBelowOne() {
        for (int i = 0 ; i < 10 ; i++) {
            builder.setHeightInCharacters(-i);

            Assert.assertEquals(1, builder.getHeightInCharacters());
        }
    }

    @Test
    public void testSetAsciiFont() {
        builder.setFont(font);

        Assert.assertEquals(font, builder.getFont());
    }

    @Test
    public void testSetAsciiFont_withNullFont() {
        builder.setFont(font);
        builder.setFont(null);

        Assert.assertEquals(font, builder.getFont());
    }

    @Test
    public void testSetCurrentScreen() {
        builder.setScreen(new Screen(0, 0, 1, 1));

        Assert.assertNotEquals(null, builder.getScreen());
    }

    @Test
    public void testSetCurrentScreen_withNullScreen() {
        builder.setScreen(null);

        Assert.assertEquals(null, builder.getScreen());
    }
}
