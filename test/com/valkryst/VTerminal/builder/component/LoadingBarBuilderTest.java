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

@RunWith(JUnitQuickcheck.class)
public class LoadingBarBuilderTest {
    private LoadingBarBuilder builder;

    @Before
    public void initializeBuilder() {
        builder = new LoadingBarBuilder();
    }

    @Test(expected=IllegalStateException.class)
    public void testCheckState_withNoPanelSet() {
        builder.checkState();
    }

    @Test
    public void testReset() {
        final Radio<String> radio = new Radio<>();

        builder.setWidth(66);
        builder.setHeight(66);

        builder.setRadio(radio);

        builder.setIncompleteCharacter('?');
        builder.setCompleteCharacter('?');

        builder.setBackgroundColor_incomplete(Color.ORANGE);
        builder.setForegroundColor_incomplete(Color.ORANGE);

        builder.setBackgroundColor_complete(Color.ORANGE);
        builder.setForegroundColor_complete(Color.ORANGE);

        builder.reset();

        Assert.assertEquals(10, builder.getWidth());
        Assert.assertEquals(1, builder.getHeight());

        Assert.assertEquals(null, builder.getRadio());

        Assert.assertEquals('█', builder.getIncompleteCharacter());
        Assert.assertEquals('█', builder.getCompleteCharacter());

        Assert.assertEquals(new Color(0x366C9F), builder.getBackgroundColor_incomplete());
        Assert.assertEquals(Color.RED, builder.getForegroundColor_incomplete());

        Assert.assertEquals(new Color(0x366C9F), builder.getBackgroundColor_complete());
        Assert.assertEquals(Color.GREEN, builder.getForegroundColor_complete());
    }

    @Property
    public void testSetWidth_withValidInput(@InRange(minInt=1) final int width) {
        builder.setWidth(width);
        Assert.assertEquals(width, builder.getWidth());
    }

    @Property
    public void testSetWidth_withWidthInvalidInput(@InRange(maxInt=0) final int width) {
        builder.setWidth(10);
        builder.setWidth(width);
        Assert.assertEquals(10, builder.getWidth());
    }

    @Property
    public void testSetHeight_withValidHeight(@InRange(minInt=1) final int height) {
        builder.setHeight(height);
        Assert.assertEquals(height, builder.getHeight());
    }

    @Property
    public void testSetHeight_withInvalidHeight(@InRange(maxInt=0) final int height) {
        builder.setHeight(10);
        builder.setHeight(height);
        Assert.assertEquals(10, builder.getHeight());
    }

    @Property
    public void testSetIncompleteCharacter(final char character) {
        builder.setIncompleteCharacter(character);
        Assert.assertEquals(character, builder.getIncompleteCharacter());
    }

    @Property
    public void testSetCompleteCharacter(final char character) {
        builder.setCompleteCharacter(character);
        Assert.assertEquals(character, builder.getCompleteCharacter());
    }

    @Test
    public void testSetBackgroundColor_incomplete_withValidColor() {
        builder.setBackgroundColor_incomplete(Color.ORANGE);
        Assert.assertEquals(Color.ORANGE, builder.getBackgroundColor_incomplete());
    }

    @Test
    public void testSetBackgroundColor_incomplete_withNullColor() {
        builder.setBackgroundColor_incomplete(Color.ORANGE);
        builder.setBackgroundColor_incomplete(null);
        Assert.assertEquals(Color.ORANGE, builder.getBackgroundColor_incomplete());
    }

    @Test
    public void testSetForegroundColor_incomplete_withValidColor() {
        builder.setForegroundColor_incomplete(Color.ORANGE);
        Assert.assertEquals(Color.ORANGE, builder.getForegroundColor_incomplete());
    }

    @Test
    public void testSetForegroundColor_incomplete_withNullColor() {
        builder.setForegroundColor_incomplete(Color.ORANGE);
        builder.setForegroundColor_incomplete(null);
        Assert.assertEquals(Color.ORANGE, builder.getForegroundColor_incomplete());
    }

    @Test
    public void testSetBackgroundColor_complete_withValidColor() {
        builder.setBackgroundColor_complete(Color.ORANGE);
        Assert.assertEquals(Color.ORANGE, builder.getBackgroundColor_complete());
    }

    @Test
    public void testSetBackgroundColor_complete_withNullColor() {
        builder.setBackgroundColor_complete(Color.ORANGE);
        builder.setBackgroundColor_complete(null);
        Assert.assertEquals(Color.ORANGE, builder.getBackgroundColor_complete());
    }

    @Test
    public void testSetForegroundColor_complete_withValidColor() {
        builder.setForegroundColor_complete(Color.ORANGE);
        Assert.assertEquals(Color.ORANGE, builder.getForegroundColor_complete());
    }

    @Test
    public void testSetForegroundColor_complete_withNullColor() {
        builder.setForegroundColor_complete(Color.ORANGE);
        builder.setForegroundColor_complete(null);
        Assert.assertEquals(Color.ORANGE, builder.getForegroundColor_complete());
    }
}
