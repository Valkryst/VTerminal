package com.valkryst.VTerminal.builder.component;

import com.valkryst.VJSON.VJSONLoader;
import com.valkryst.VTerminal.component.TextArea;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.io.IOException;
import java.util.regex.Pattern;

public class TextAreaBuilderTest {
    private TextAreaBuilder builder;
    private final Pattern pattern = Pattern.compile("f");

    @Before
    public void setVars() {
        builder = new TextAreaBuilder();

        builder.setMaxHorizontalCharacters(11);
        builder.setMaxVerticalCharacters(22);

        builder.setCaretBackgroundColor(Color.RED);
        builder.setCaretForegroundColor(Color.BLUE);

        builder.setBackgroundColor(Color.GREEN);
        builder.setForegroundColor(Color.PINK);

        builder.setEditable(false);

        builder.setHomeKeyEnabled(false);
        builder.setEndKeyEnabled(false);
        builder.setPageUpKeyEnabled(false);
        builder.setPageDownKeyEnabled(false);
        builder.setDeleteKeyEnabled(false);
        builder.setLeftArrowKeyEnabled(false);
        builder.setRightArrowKeyEnabled(false);
        builder.setUpArrowKeyEnabled(false);
        builder.setDownArrowKeyEnabled(false);
        builder.setBackSpaceKeyEnabled(false);
        builder.setTabKeyEnabled(false);

        builder.setTabSize(33);

        builder.setAllowedCharacterPattern(pattern);
    }

    @Test
    public void testBuild_withValidState() {
        final TextArea textArea = builder.build();

        Assert.assertEquals(textArea.getMaxHorizontalCharacters(), 11);
        Assert.assertEquals(textArea.getMaxVerticalCharacters(), 22);

        Assert.assertEquals(textArea.getCaretBackgroundColor(), Color.RED);
        Assert.assertEquals(textArea.getCaretForegroundColor(), Color.BLUE);

        Assert.assertEquals(textArea.getBackgroundColor(), Color.GREEN);
        Assert.assertEquals(textArea.getForegroundColor(), Color.PINK);

        Assert.assertFalse(textArea.isEditable());

        Assert.assertFalse(textArea.isHomeKeyEnabled());
        Assert.assertFalse(textArea.isEndKeyEnabled());
        Assert.assertFalse(textArea.isPageUpKeyEnabled());
        Assert.assertFalse(textArea.isPageDownKeyEnabled());
        Assert.assertFalse(textArea.isDeleteKeyEnabled());
        Assert.assertFalse(textArea.isLeftArrowKeyEnabled());
        Assert.assertFalse(textArea.isRightArrowKeyEnabled());
        Assert.assertFalse(textArea.isUpArrowKeyEnabled());
        Assert.assertFalse(textArea.isDownArrowKeyEnabled());
        Assert.assertFalse(textArea.isBackSpaceKeyEnabled());
        Assert.assertFalse(textArea.isTabKeyEnabled());

        Assert.assertEquals(textArea.getTabSize(), 33);

        Assert.assertEquals(textArea.getAllowedCharacterPattern(), pattern);
    }

    @Test
    public void testReset() {
        // Ensure all vars are set
        Assert.assertEquals(builder.getMaxHorizontalCharacters(), 11);
        Assert.assertEquals(builder.getMaxVerticalCharacters(), 22);

        Assert.assertEquals(builder.getCaretBackgroundColor(), Color.RED);
        Assert.assertEquals(builder.getCaretForegroundColor(), Color.BLUE);

        Assert.assertEquals(builder.getBackgroundColor(), Color.GREEN);
        Assert.assertEquals(builder.getForegroundColor(), Color.PINK);

        Assert.assertFalse(builder.isEditable());

        Assert.assertFalse(builder.isHomeKeyEnabled());
        Assert.assertFalse(builder.isEndKeyEnabled());
        Assert.assertFalse(builder.isPageUpKeyEnabled());
        Assert.assertFalse(builder.isPageDownKeyEnabled());
        Assert.assertFalse(builder.isDeleteKeyEnabled());
        Assert.assertFalse(builder.isLeftArrowKeyEnabled());
        Assert.assertFalse(builder.isRightArrowKeyEnabled());
        Assert.assertFalse(builder.isUpArrowKeyEnabled());
        Assert.assertFalse(builder.isDownArrowKeyEnabled());
        Assert.assertFalse(builder.isBackSpaceKeyEnabled());
        Assert.assertFalse(builder.isTabKeyEnabled());

        Assert.assertEquals(builder.getTabSize(), 33);

        Assert.assertEquals(builder.getAllowedCharacterPattern(), pattern);

        // Reset
        builder.reset();

        // Ensure all vars are reset
        Assert.assertNotEquals(builder.getMaxHorizontalCharacters(), 11);
        Assert.assertNotEquals(builder.getMaxVerticalCharacters(), 22);

        Assert.assertNotEquals(builder.getCaretBackgroundColor(), Color.RED);
        Assert.assertNotEquals(builder.getCaretForegroundColor(), Color.BLUE);

        Assert.assertNotEquals(builder.getBackgroundColor(), Color.GREEN);
        Assert.assertNotEquals(builder.getForegroundColor(), Color.PINK);

        Assert.assertTrue(builder.isEditable());

        Assert.assertTrue(builder.isHomeKeyEnabled());
        Assert.assertTrue(builder.isEndKeyEnabled());
        Assert.assertTrue(builder.isPageUpKeyEnabled());
        Assert.assertTrue(builder.isPageDownKeyEnabled());
        Assert.assertTrue(builder.isDeleteKeyEnabled());
        Assert.assertTrue(builder.isLeftArrowKeyEnabled());
        Assert.assertTrue(builder.isRightArrowKeyEnabled());
        Assert.assertTrue(builder.isUpArrowKeyEnabled());
        Assert.assertTrue(builder.isDownArrowKeyEnabled());
        Assert.assertTrue(builder.isBackSpaceKeyEnabled());
        Assert.assertTrue(builder.isTabKeyEnabled());

        Assert.assertNotEquals(builder.getTabSize(), 33);

        Assert.assertNotEquals(builder.getAllowedCharacterPattern(), pattern);
    }

    @Test
    public void testParse() throws IOException, ParseException {
        final TextAreaBuilder builder = new TextAreaBuilder();
        VJSONLoader.loadFromJSON(builder, System.getProperty("user.dir") + "/res_test/components/TextArea.json");

        Assert.assertEquals(builder.getMaxHorizontalCharacters(), 11);
        Assert.assertEquals(builder.getMaxVerticalCharacters(), 22);

        Assert.assertEquals(builder.getCaretBackgroundColor(), Color.RED);
        Assert.assertEquals(builder.getCaretForegroundColor(), Color.BLUE);

        Assert.assertEquals(builder.getBackgroundColor(), Color.GREEN);
        Assert.assertEquals(builder.getForegroundColor(), Color.PINK);

        Assert.assertFalse(builder.isEditable());

        Assert.assertFalse(builder.isHomeKeyEnabled());
        Assert.assertFalse(builder.isEndKeyEnabled());
        Assert.assertFalse(builder.isPageUpKeyEnabled());
        Assert.assertFalse(builder.isPageDownKeyEnabled());
        Assert.assertFalse(builder.isDeleteKeyEnabled());
        Assert.assertFalse(builder.isLeftArrowKeyEnabled());
        Assert.assertFalse(builder.isRightArrowKeyEnabled());
        Assert.assertFalse(builder.isUpArrowKeyEnabled());
        Assert.assertFalse(builder.isDownArrowKeyEnabled());
        Assert.assertFalse(builder.isBackSpaceKeyEnabled());
        Assert.assertFalse(builder.isTabKeyEnabled());

        Assert.assertEquals(builder.getTabSize(), 33);

        Assert.assertEquals(builder.getAllowedCharacterPattern().toString(), pattern.toString());
    }
}
