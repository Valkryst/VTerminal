package com.valkryst.VTerminal.builder;

import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
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
    */
}
