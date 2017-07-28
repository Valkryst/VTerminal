package com.valkryst.VTerminal.builder.component;

import com.valkryst.VRadio.Radio;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.regex.Pattern;

public class TextAreaBuilderTest {
    private TextAreaBuilder builder;

    @Before
    public void initializeBuilder() {
        builder = new TextAreaBuilder();
    }

    @Test(expected=NullPointerException.class)
    public void testCheckState_withNoPanelSet() {
        builder.checkState();
    }

    @Test
    public void testReset() {
        final Radio<String> radio = new Radio<>();

        builder.setWidth(66);
        builder.setHeight(66);

        builder.setMaxHorizontalCharacters(66);
        builder.setMaxVerticalCharacters(66);

        builder.setRadio(radio);

        builder.setCaretBackgroundColor(Color.RED);
        builder.setCaretForegroundColor(Color.RED);

        builder.setForegroundColor(Color.RED);
        builder.setBackgroundColor(Color.RED);

        builder.setHomeKeyEnabled(false);
        builder.setEndKeyEnabled(false);
        builder.setDeleteKeyEnabled(false);
        builder.setLeftArrowKeyEnabled(false);
        builder.setRightArrowKeyEnabled(false);
        builder.setUpArrowKeyEnabled(false);
        builder.setDownArrowKeyEnabled(false);
        builder.setEnterKeyEnabled(false);
        builder.setBackSpaceKeyEnabled(false);

        builder.setAllowedCharacterPattern(null);

        // Doesn't test the AllowedCharacterPattern

        builder.reset();

        Assert.assertEquals(4, builder.getWidth());
        Assert.assertEquals(4, builder.getHeight());

        Assert.assertEquals(4, builder.getMaxHorizontalCharacters());
        Assert.assertEquals(4, builder.getMaxVerticalCharacters());

        Assert.assertEquals(null, builder.getRadio());

        Assert.assertEquals(new Color(0x21B6A8), builder.getCaretForegroundColor());
        Assert.assertEquals(new Color(0x52F2EA), builder.getCaretBackgroundColor());

        Assert.assertEquals(new Color(0x52F2EA), builder.getForegroundColor());
        Assert.assertEquals(new Color(0x21B6A8), builder.getBackgroundColor());

        Assert.assertTrue(builder.isHomeKeyEnabled());
        Assert.assertTrue(builder.isEndKeyEnabled());
        Assert.assertTrue(builder.isDeleteKeyEnabled());
        Assert.assertTrue(builder.isLeftArrowKeyEnabled());
        Assert.assertTrue(builder.isRightArrowKeyEnabled());
        Assert.assertTrue(builder.isUpArrowKeyEnabled());
        Assert.assertTrue(builder.isDownArrowKeyEnabled());
        Assert.assertTrue(builder.isEnterKeyEnabled());
        Assert.assertTrue(builder.isBackSpaceKeyEnabled());

        Assert.assertNotEquals(null, builder.getAllowedCharacterPattern());
    }

    @Test
    public void testSetWidth_withValueAboveZero() {
        builder.setWidth(66);
        Assert.assertEquals(66, builder.getWidth());
    }

    @Test
    public void testSetWidth_withValueBelowOne() {
        builder.setWidth(0);
        Assert.assertEquals(4, builder.getWidth());
    }

    @Test
    public void testSetHeight_withValueAboveZero() {
        builder.setHeight(66);
        Assert.assertEquals(66, builder.getHeight());
    }

    @Test
    public void testSetHeight_withValueBelowOne() {
        builder.setHeight(0);
        Assert.assertEquals(4, builder.getHeight());
    }

    @Test
    public void testSetMaxHorizontalCharacters_withValueAboveZero() {
        builder.setMaxHorizontalCharacters(66);
        Assert.assertEquals(66, builder.getMaxHorizontalCharacters());
    }

    @Test
    public void testSetMaxHorizontalCharacters_withValueBelowOne() {
        builder.setMaxHorizontalCharacters(0);
        Assert.assertEquals(4, builder.getMaxHorizontalCharacters());
    }

    @Test
    public void testSetMaxVerticalCharacters_withValueAboveZero() {
        builder.setMaxVerticalCharacters(66);
        Assert.assertEquals(66, builder.getMaxVerticalCharacters());
    }

    @Test
    public void testSetMaxVerticalCharacters_withValueBelowOne() {
        builder.setMaxVerticalCharacters(0);
        Assert.assertEquals(4, builder.getMaxVerticalCharacters());
    }

    @Test
    public void testSetCaretForegroundColor_withValidColor() {
        builder.setCaretForegroundColor(Color.RED);
        Assert.assertEquals(Color.RED, builder.getCaretForegroundColor());
    }

    @Test
    public void testSetCaretForegroundColor_withNullColor() {
        builder.setCaretForegroundColor(Color.RED);
        builder.setCaretForegroundColor(null);
        Assert.assertEquals(Color.RED, builder.getCaretForegroundColor());
    }

    @Test
    public void testSetCaretBackgroundColor_withValidColor() {
        builder.setCaretBackgroundColor(Color.RED);
        Assert.assertEquals(Color.RED, builder.getCaretBackgroundColor());
    }

    @Test
    public void testSetCaretBackgroundColor_withNullColor() {
        builder.setCaretBackgroundColor(Color.RED);
        builder.setCaretBackgroundColor(null);
        Assert.assertEquals(Color.RED, builder.getCaretBackgroundColor());
    }

    @Test
    public void testSetForegroundColor_withValidColor() {
        builder.setForegroundColor(Color.RED);
        Assert.assertEquals(Color.RED, builder.getForegroundColor());
    }

    @Test
    public void testSetForegroundColor_withNullColor() {
        builder.setForegroundColor(Color.RED);
        builder.setForegroundColor(null);
        Assert.assertEquals(Color.RED, builder.getForegroundColor());
    }

    @Test
    public void testSetBackgroundColor_withValidColor() {
        builder.setBackgroundColor(Color.RED);
        Assert.assertEquals(Color.RED, builder.getBackgroundColor());
    }

    @Test
    public void testSetBackgroundColor_withNullColor() {
        builder.setBackgroundColor(Color.RED);
        builder.setBackgroundColor(null);
        Assert.assertEquals(Color.RED, builder.getBackgroundColor());
    }

    @Test
    public void testSetHomeKeyEnabled() {
        builder.setHomeKeyEnabled(false);
        Assert.assertFalse(builder.isHomeKeyEnabled());
    }

    @Test
    public void testSetEndKeyEnabled() {
        builder.setEndKeyEnabled(false);
        Assert.assertFalse(builder.isEndKeyEnabled());
    }

    @Test
    public void testSetDeleteKeyEnabled() {
        builder.setDeleteKeyEnabled(false);
        Assert.assertFalse(builder.isDeleteKeyEnabled());
    }

    @Test
    public void testSetLeftArrowKeyEnabled() {
        builder.setLeftArrowKeyEnabled(false);
        Assert.assertFalse(builder.isLeftArrowKeyEnabled());
    }

    @Test
    public void testSetRightArrowKeyEnabled() {
        builder.setRightArrowKeyEnabled(false);
        Assert.assertFalse(builder.isRightArrowKeyEnabled());
    }

    @Test
    public void testSetUpArrowKeyEnabled() {
        builder.setUpArrowKeyEnabled(false);
        Assert.assertFalse(builder.isUpArrowKeyEnabled());
    }

    @Test
    public void testSetDownArrowKeyEnabled() {
        builder.setDownArrowKeyEnabled(false);
        Assert.assertFalse(builder.isDownArrowKeyEnabled());
    }

    @Test
    public void testSetEnterArrowKeyEnabled() {
        builder.setEnterKeyEnabled(false);
        Assert.assertFalse(builder.isEnterKeyEnabled());
    }

    @Test
    public void testSetBackSpaceKeyEnabled() {
        builder.setBackSpaceKeyEnabled(false);
        Assert.assertFalse(builder.isBackSpaceKeyEnabled());
    }

    @Test
    public void testSetAllowedCharacterPattern_withValidPattern() {
        final Pattern pattern = Pattern.compile("asdsdf");
        builder.setAllowedCharacterPattern(pattern);
        Assert.assertEquals(pattern, builder.getAllowedCharacterPattern());
    }

    @Test
    public void testSetAllowedCharacterPattern_withNullPattern() {
        final Pattern pattern = Pattern.compile("asdsdf");
        builder.setAllowedCharacterPattern(pattern);
        builder.setAllowedCharacterPattern(null);
        Assert.assertEquals(pattern, builder.getAllowedCharacterPattern());
    }
}
