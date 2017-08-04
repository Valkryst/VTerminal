package com.valkryst.VTerminal.builder.component;

import com.valkryst.VRadio.Radio;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.regex.Pattern;

public class TextFieldBuilderTest {
    private TextFieldBuilder builder;

    @Before
    public void initializeBuilder() {
        builder = new TextFieldBuilder();
    }

    @Test(expected=NullPointerException.class)
    public void testCheckState_withNoPanelSet() {
        builder.checkState();
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

        final Pattern pattern = Pattern.compile("asdfgdgf");
        builder.setAllowedCharacterPattern(pattern);

        // Doesn't test the AllowedCharacterPattern

        builder.reset();

        Assert.assertEquals(4, builder.getWidth());

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

        Assert.assertNotEquals(pattern, builder.getAllowedCharacterPattern());
    }
}
