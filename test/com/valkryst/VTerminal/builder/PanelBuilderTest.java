package com.valkryst.VTerminal.builder;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.valkryst.VTerminal.component.Screen;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.URISyntaxException;

@RunWith(JUnitQuickcheck.class)
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

    @Test
    public void testSetJFrame_returnValue() {
        Assert.assertTrue(builder == builder.setJFrame(new JFrame()));
    }

    @Test
    public void testSetJFrame_withNullJFrame() {
        builder.setJFrame(new JFrame());
        builder.setJFrame(null);

        Assert.assertEquals(null, builder.getFrame());
    }
    */

    @Property
    public void testSetWidthInCharacters(@InRange(minInt = 1) final int width) {
        builder.setWidthInCharacters(width);
        Assert.assertEquals(width, builder.getWidthInCharacters());
    }

    @Property(trials=5)
    public void testSetWidthInCharacters_returnValue(@InRange(minInt = 1) final int width) {
        Assert.assertTrue(builder == builder.setWidthInCharacters(width));
    }

    @Property(trials=5)
    public void testSetWidthInCharacters_withWidthBelowOne(@InRange(maxInt = 0) final int width) {
        builder.setWidthInCharacters(width);
        Assert.assertEquals(1, builder.getWidthInCharacters());
    }

    @Property(trials=5)
    public void testSetHeightInCharacters(@InRange(minInt = 0) final int height) {
        builder.setHeightInCharacters(height);
        Assert.assertEquals(height, builder.getHeightInCharacters());
    }

    @Test
    public void testSetHeightInCharacters_returnValue() {
        Assert.assertTrue(builder == builder.setHeightInCharacters(10));
    }

    @Property(trials=5)
    public void testSetHeightInCharacters_withHeightBelowOne(@InRange(maxInt = 0) final int height) {
        builder.setHeightInCharacters(height);
        Assert.assertEquals(1, builder.getHeightInCharacters());
    }

    @Test
    public void testSetAsciiFont() {
        builder.setFont(font);

        Assert.assertEquals(font, builder.getFont());
    }

    @Test
    public void testSetAsciiFont_returnValue() {
        Assert.assertTrue(builder == builder.setFont(font));
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
    public void testSetCurrentScreen_returnValue() {
        Assert.assertEquals(builder, builder.setScreen(new Screen(0, 0, 1, 1)));
    }

    @Test
    public void testSetCurrentScreen_withNullScreen() {
        builder.setScreen(null);

        Assert.assertEquals(null, builder.getScreen());
    }
}
