package com.valkryst.VTerminal.builder.component;

import com.valkryst.VJSON.VJSONLoader;
import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.component.Button;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.io.IOException;

public class ButtonBuilderTest {
    private ButtonBuilder builder;
    private final Runnable function = () -> System.out.println("Test");

    @Before
    public void setVars() {
        builder = new ButtonBuilder();

        builder.setText("1234");

        builder.setBackgroundColor_normal(Color.RED);
        builder.setForegroundColor_normal(Color.BLUE);

        builder.setBackgroundColor_hover(Color.GREEN);
        builder.setForegroundColor_hover(Color.CYAN);

        builder.setBackgroundColor_pressed(Color.GRAY);
        builder.setForegroundColor_pressed(Color.PINK);

        builder.setOnClickFunction(function);
    }

    @Test
    public void testBuild_withValidState() {
        final Button button = builder.build();

        String text = "";

        for (final AsciiCharacter character : button.getString(0).getCharacters()) {
            if (character.getCharacter() != ' ') {
                text += character.getCharacter();
            } else {
                break;
            }
        }

        Assert.assertEquals(text, "1234");

        Assert.assertEquals(button.getBackgroundColor_normal(), Color.RED);
        Assert.assertEquals(button.getForegroundColor_normal(), Color.BLUE);

        Assert.assertEquals(button.getBackgroundColor_hover(), Color.GREEN);
        Assert.assertEquals(button.getForegroundColor_hover(), Color.CYAN);

        Assert.assertEquals(button.getBackgroundColor_pressed(), Color.GRAY);
        Assert.assertEquals(button.getForegroundColor_pressed(), Color.PINK);

        Assert.assertEquals(button.getOnClickFunction(), function);
    }

    @Test
    public void testReset() {
        // Ensure all vars are set
        Assert.assertEquals(builder.getText(), "1234");

        Assert.assertEquals(builder.getBackgroundColor_normal(), Color.RED);
        Assert.assertEquals(builder.getForegroundColor_normal(), Color.BLUE);

        Assert.assertEquals(builder.getBackgroundColor_hover(), Color.GREEN);
        Assert.assertEquals(builder.getForegroundColor_hover(), Color.CYAN);

        Assert.assertEquals(builder.getBackgroundColor_pressed(), Color.GRAY);
        Assert.assertEquals(builder.getForegroundColor_pressed(), Color.PINK);

        Assert.assertEquals(builder.getOnClickFunction(), function);

        // Reset
        builder.reset();

        // Ensure all vars are reset
        Assert.assertNotEquals(builder.getText(), "1234");

        Assert.assertNotEquals(builder.getBackgroundColor_normal(), Color.RED);
        Assert.assertNotEquals(builder.getForegroundColor_hover(), Color.BLUE);

        Assert.assertNotEquals(builder.getBackgroundColor_hover(), Color.GREEN);
        Assert.assertNotEquals(builder.getForegroundColor_hover(), Color.CYAN);

        Assert.assertNotEquals(builder.getBackgroundColor_pressed(), Color.GRAY);
        Assert.assertNotEquals(builder.getForegroundColor_pressed(), Color.PINK);

        Assert.assertNotEquals(builder.getOnClickFunction(), function);
    }

    @Test
    public void testParse() throws IOException, ParseException {
        final ButtonBuilder builder = new ButtonBuilder();
        VJSONLoader.loadFromJSON(builder, System.getProperty("user.dir") + "/res_test/components/Button.json");

        Assert.assertEquals(builder.getText(), "1234");

        Assert.assertEquals(builder.getBackgroundColor_normal(), Color.RED);
        Assert.assertEquals(builder.getForegroundColor_normal(), Color.BLUE);

        Assert.assertEquals(builder.getBackgroundColor_hover(), Color.GREEN);
        Assert.assertEquals(builder.getForegroundColor_hover(), Color.CYAN);

        Assert.assertEquals(builder.getBackgroundColor_pressed(), Color.GRAY);
        Assert.assertEquals(builder.getForegroundColor_pressed(), Color.PINK);
    }
}
