package com.valkryst.VTerminal.builder.component;

import com.valkryst.VRadio.Radio;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class LoadingBarBuilderTest {
    private LoadingBarBuilder builder;

    @Before
    public void initalizeBuilder() {
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

    @Test
    public void testSetWidth_withWidthAboveZero() {
        builder.setWidth(66);
        Assert.assertEquals(66, builder.getWidth());
    }

    @Test
    public void testSetWidth_withWidthBelowOne() {
        builder.setWidth(0);
        Assert.assertEquals(10, builder.getWidth());
    }

    @Test
    public void testSetHeight_withHeightAboveZero() {
        builder.setHeight(66);
        Assert.assertEquals(66, builder.getHeight());
    }

    @Test
    public void testSetHeight_withHeightBelowOne() {
        builder.setHeight(0);
        Assert.assertEquals(1, builder.getHeight());
    }

    @Test
    public void testSetIncompleteCharacter() {
        builder.setIncompleteCharacter('?');
        Assert.assertEquals('?', builder.getIncompleteCharacter());
    }

    @Test
    public void testSetCompleteCharacter() {
        builder.setCompleteCharacter('?');
        Assert.assertEquals('?', builder.getCompleteCharacter());
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
