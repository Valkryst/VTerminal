package com.valkryst.VTerminal.builder.component;

import com.valkryst.VJSON.VJSONLoader;
import com.valkryst.VTerminal.component.TextField;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.io.IOException;
import java.util.regex.Pattern;

public class TextFieldBuilderTest {
    private TextFieldBuilder builder;
    private final Pattern pattern = Pattern.compile("f");

    @Before
    public void setVars() {
        builder = new TextFieldBuilder();

        builder.setMaxCharacters(11);

        builder.setCaretBackgroundColor(Color.RED);
        builder.setCaretForegroundColor(Color.BLUE);

        builder.setBackgroundColor(Color.GREEN);
        builder.setForegroundColor(Color.PINK);

        builder.setEditable(false);

        builder.setHomeKeyEnabled(false);
        builder.setEndKeyEnabled(false);
        builder.setDeleteKeyEnabled(false);
        builder.setLeftArrowKeyEnabled(false);
        builder.setRightArrowKeyEnabled(false);
        builder.setBackSpaceKeyEnabled(false);

        builder.setAllowedCharacterPattern(pattern);
    }

    @Test
    public void testBuild_withValidState() {
        final TextField textField = builder.build();

        Assert.assertEquals(textField.getMaxCharacters(), 11);

        Assert.assertEquals(textField.getCaretBackgroundColor(), Color.RED);
        Assert.assertEquals(textField.getCaretForegroundColor(), Color.BLUE);

        Assert.assertEquals(textField.getBackgroundColor(), Color.GREEN);
        Assert.assertEquals(textField.getForegroundColor(), Color.PINK);

        Assert.assertFalse(textField.isEditable());

        Assert.assertFalse(textField.isHomeKeyEnabled());
        Assert.assertFalse(textField.isEndKeyEnabled());
        Assert.assertFalse(textField.isDeleteKeyEnabled());
        Assert.assertFalse(textField.isLeftArrowKeyEnabled());
        Assert.assertFalse(textField.isRightArrowKeyEnabled());
        Assert.assertFalse(textField.isBackSpaceKeyEnabled());

        Assert.assertEquals(textField.getAllowedCharacterPattern(), pattern);
    }

    @Test
    public void testReset() {
        // Ensure all vars are set
        Assert.assertEquals(builder.getMaxCharacters(), 11);

        Assert.assertEquals(builder.getCaretBackgroundColor(), Color.RED);
        Assert.assertEquals(builder.getCaretForegroundColor(), Color.BLUE);

        Assert.assertEquals(builder.getBackgroundColor(), Color.GREEN);
        Assert.assertEquals(builder.getForegroundColor(), Color.PINK);

        Assert.assertFalse(builder.isEditable());

        Assert.assertFalse(builder.isHomeKeyEnabled());
        Assert.assertFalse(builder.isEndKeyEnabled());
        Assert.assertFalse(builder.isDeleteKeyEnabled());
        Assert.assertFalse(builder.isLeftArrowKeyEnabled());
        Assert.assertFalse(builder.isRightArrowKeyEnabled());
        Assert.assertFalse(builder.isBackSpaceKeyEnabled());

        Assert.assertEquals(builder.getAllowedCharacterPattern(), pattern);

        // Reset
        builder.reset();

        // Ensure all vars are reset
        Assert.assertNotEquals(builder.getMaxCharacters(), 11);

        Assert.assertNotEquals(builder.getCaretBackgroundColor(), Color.RED);
        Assert.assertNotEquals(builder.getCaretForegroundColor(), Color.BLUE);

        Assert.assertNotEquals(builder.getBackgroundColor(), Color.GREEN);
        Assert.assertNotEquals(builder.getForegroundColor(), Color.PINK);

        Assert.assertTrue(builder.isEditable());

        Assert.assertTrue(builder.isHomeKeyEnabled());
        Assert.assertTrue(builder.isEndKeyEnabled());
        Assert.assertTrue(builder.isDeleteKeyEnabled());
        Assert.assertTrue(builder.isLeftArrowKeyEnabled());
        Assert.assertTrue(builder.isRightArrowKeyEnabled());
        Assert.assertTrue(builder.isBackSpaceKeyEnabled());

        Assert.assertNotEquals(builder.getAllowedCharacterPattern(), pattern);
    }

    @Test
    public void testParse() throws IOException, ParseException {
        final TextFieldBuilder builder = new TextFieldBuilder();
        VJSONLoader.loadFromJSON(builder, System.getProperty("user.dir") + "/res_test/components/TextField.json");

        Assert.assertEquals(builder.getMaxCharacters(), 11);

        Assert.assertEquals(builder.getCaretBackgroundColor(), Color.RED);
        Assert.assertEquals(builder.getCaretForegroundColor(), Color.BLUE);

        Assert.assertEquals(builder.getBackgroundColor(), Color.GREEN);
        Assert.assertEquals(builder.getForegroundColor(), Color.PINK);

        Assert.assertFalse(builder.isEditable());

        Assert.assertFalse(builder.isHomeKeyEnabled());
        Assert.assertFalse(builder.isEndKeyEnabled());
        Assert.assertFalse(builder.isDeleteKeyEnabled());
        Assert.assertFalse(builder.isLeftArrowKeyEnabled());
        Assert.assertFalse(builder.isRightArrowKeyEnabled());
        Assert.assertFalse(builder.isBackSpaceKeyEnabled());

        Assert.assertEquals(builder.getAllowedCharacterPattern().toString(), pattern.toString());
    }
}
