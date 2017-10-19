package com.valkryst.VTerminal.builder.component;

import com.valkryst.VJSON.VJSONLoader;
import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.component.Layer;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.io.IOException;

public class LayerBuilderTest {
    private LayerBuilder builder;

    @Before
    public void setVars() {
        builder = new LayerBuilder();
        builder.setBackgroundColor(Color.RED);
        builder.setForegroundColor(Color.BLUE);
    }

    @Test
    public void testBuild_withValidState() {
        final Layer layer = builder.build();

        boolean backgroundColorsCorrect = true;

        for (final AsciiCharacter character : layer.getString(0).getCharacters()) {
            if (character.getBackgroundColor().equals(Color.RED) == false) {
                backgroundColorsCorrect = false;
                break;
            }
        }

        boolean foregroundColorsCorrect = true;

        for (final AsciiCharacter character : layer.getString(0).getCharacters()) {
            if (character.getForegroundColor().equals(Color.BLUE) == false) {
                foregroundColorsCorrect = false;
                break;
            }
        }

        Assert.assertTrue(backgroundColorsCorrect);
        Assert.assertTrue(foregroundColorsCorrect);
    }

    @Test
    public void testReset() {
        // Ensure all vars are set
        Assert.assertEquals(builder.getBackgroundColor(), Color.RED);
        Assert.assertEquals(builder.getForegroundColor(), Color.BLUE);

        // Reset
        builder.reset();

        // Ensure all vars are reset
        Assert.assertNotEquals(builder.getBackgroundColor(), Color.RED);
        Assert.assertNotEquals(builder.getForegroundColor(), Color.BLUE);
    }

    @Test
    public void testParse() throws IOException, ParseException {
        final LayerBuilder builder = new LayerBuilder();
        VJSONLoader.loadFromJSON(builder, System.getProperty("user.dir") + "/res_test/components/Layer.json");

        Assert.assertEquals(builder.getBackgroundColor(), Color.RED);
        Assert.assertEquals(builder.getForegroundColor(), Color.BLUE);
    }
}
