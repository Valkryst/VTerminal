package com.valkryst.VTerminal.builder.component;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.valkryst.VRadio.Radio;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.awt.Color;
import java.util.regex.Pattern;

@RunWith(JUnitQuickcheck.class)
public class TextFieldBuilderTest {
    private TextFieldBuilder builder;

    @Before
    public void initializeBuilder() {
        builder = new TextFieldBuilder();
    }

    @Test(expected=IllegalStateException.class)
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

    @Property
    public void testSetWidth_withValidWidth(@InRange(minInt=1) final int width) {
        builder.setWidth(width);
        Assert.assertEquals(width, builder.getWidth());
    }

    @Property
    public void testSetWidth_withInvalidWidth(@InRange(maxInt=0) final int width) {
        builder.setWidth(width);
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

    @Property
    public void testSetHomeKeyEnabled(final boolean isEnabled) {
        builder.setHomeKeyEnabled(isEnabled);
        Assert.assertEquals(isEnabled, builder.isHomeKeyEnabled());
    }

    @Property
    public void testSetEndKeyEnabled(final boolean isEnabled) {
        builder.setEndKeyEnabled(isEnabled);
        Assert.assertEquals(isEnabled, builder.isEndKeyEnabled());
    }

    @Property
    public void testSetDeleteKeyEnabled(final boolean isEnabled) {
        builder.setDeleteKeyEnabled(isEnabled);
        Assert.assertEquals(isEnabled, builder.isDeleteKeyEnabled());
    }

    @Property
    public void testSetLeftArrowKeyEnabled(final boolean isEnabled) {
        builder.setLeftArrowKeyEnabled(isEnabled);
        Assert.assertEquals(isEnabled, builder.isLeftArrowKeyEnabled());
    }

    @Property
    public void testSetRightArrowKeyEnabled(final boolean isEnabled) {
        builder.setRightArrowKeyEnabled(isEnabled);
        Assert.assertEquals(isEnabled, builder.isRightArrowKeyEnabled());
    }

    @Property
    public void testSetBackSpaceKeyEnabled(final boolean isEnabled) {
        builder.setBackSpaceKeyEnabled(isEnabled);
        Assert.assertEquals(isEnabled, builder.isBackSpaceKeyEnabled());
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
