package com.valkryst.VTerminal.builder.component;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.regex.Pattern;

public class TextFieldBuilderTest {
    private TextFieldBuilder builder;

    @Before
    public void initalizeBuilder() {
        builder = new TextFieldBuilder();
    }

    @Test
    public void testReset() {
        final Radio<String> radio = new Radio<>();

        builder.setWidth(66);

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
        builder.setBackSpaceKeyEnabled(false);

        // Doesn't test the AllowedCharacterPattern

        builder.reset();

        Assert.assertEquals(1, builder.getWidth());

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
        Assert.assertTrue(builder.isBackSpaceKeyEnabled());
    }

    @Test
    public void testSetWidth_withWidthAboveZero() {
        builder.setWidth(66);
        Assert.assertEquals(66, builder.getWidth());
    }

    @Test
    public void testSetWidth_withWidthBelowOne() {
        builder.setWidth(0);
        Assert.assertEquals(1, builder.getWidth());
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
