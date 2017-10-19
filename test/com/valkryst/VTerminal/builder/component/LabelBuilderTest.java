package com.valkryst.VTerminal.builder.component;

import com.valkryst.VJSON.VJSONLoader;
import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.component.Label;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.io.IOException;

public class LabelBuilderTest {
    private LabelBuilder builder;

    @Before
    public void setVars() {
        builder = new LabelBuilder();
        builder.setText("1234");
        builder.setUnderlined(true);
        builder.setBackgroundColor(Color.RED);
        builder.setForegroundColor(Color.BLUE);
    }

    @Test
    public void testBuild_withValidState() {
        final Label label = builder.build();

        String text = "";

        for (final AsciiCharacter character : label.getString(0).getCharacters()) {
            if (character.getCharacter() != ' ') {
                text += character.getCharacter();
            } else {
                break;
            }
        }

        boolean isUnderlined = true;

        for (final AsciiCharacter character : label.getString(0).getCharacters()) {
            if (character.isUnderlined() == false) {
                isUnderlined = false;
                break;
            }
        }

        Assert.assertEquals(text, "1234");
        Assert.assertTrue(isUnderlined);
        Assert.assertEquals(label.getBackgroundColor(), Color.RED);
        Assert.assertEquals(label.getForegroundColor(), Color.BLUE);
    }

    @Test
    public void testReset() {
        // Ensure all vars are set
        Assert.assertEquals(builder.getText(), "1234");
        Assert.assertTrue(builder.isUnderlined());
        Assert.assertEquals(builder.getBackgroundColor(), Color.RED);
        Assert.assertEquals(builder.getForegroundColor(), Color.BLUE);

        // Reset
        builder.reset();

        // Ensure all vars are reset
        Assert.assertNotEquals(builder.getText(), "1234");
        Assert.assertFalse(builder.isUnderlined());
        Assert.assertNotEquals(builder.getBackgroundColor(), Color.RED);
        Assert.assertNotEquals(builder.getForegroundColor(), Color.BLUE);
    }

    @Test
    public void testParse() throws IOException, ParseException {
        final LabelBuilder builder = new LabelBuilder();
        VJSONLoader.loadFromJSON(builder, System.getProperty("user.dir") + "/res_test/components/Label.json");

        Assert.assertEquals(builder.getText(), "1234");
        Assert.assertTrue(builder.isUnderlined());
        Assert.assertEquals(builder.getBackgroundColor(), Color.RED);
        Assert.assertEquals(builder.getForegroundColor(), Color.BLUE);
    }
}
