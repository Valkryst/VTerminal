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

        final Pattern pattern = Pattern.compile("asdfgdgf");
        builder.setAllowedCharacterPattern(pattern);

        // Doesn't test the AllowedCharacterPattern

        builder.reset();

        Assert.assertEquals(4, builder.getWidth());
        Assert.assertEquals(4, builder.getHeight());

        Assert.assertEquals(4, builder.getMaxHorizontalCharacters());
        Assert.assertEquals(4, builder.getMaxVerticalCharacters());

        Assert.assertEquals(null, builder.getRadio());

        Assert.assertEquals(new Color(142, 153, 158), builder.getCaretForegroundColor());
        Assert.assertEquals(new Color(104, 208, 255), builder.getCaretBackgroundColor());

        Assert.assertEquals(new Color(104, 208, 255), builder.getForegroundColor());
        Assert.assertEquals(new Color(142, 153, 158), builder.getBackgroundColor());

        Assert.assertTrue(builder.isHomeKeyEnabled());
        Assert.assertTrue(builder.isEndKeyEnabled());
        Assert.assertTrue(builder.isDeleteKeyEnabled());
        Assert.assertTrue(builder.isLeftArrowKeyEnabled());
        Assert.assertTrue(builder.isRightArrowKeyEnabled());
        Assert.assertTrue(builder.isUpArrowKeyEnabled());
        Assert.assertTrue(builder.isDownArrowKeyEnabled());
        Assert.assertTrue(builder.isEnterKeyEnabled());
        Assert.assertTrue(builder.isBackSpaceKeyEnabled());

        Assert.assertNotEquals(pattern, builder.getAllowedCharacterPattern());
    }
}
