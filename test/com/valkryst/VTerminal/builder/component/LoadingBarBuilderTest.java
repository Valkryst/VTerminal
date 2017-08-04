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
}
