package com.valkryst.VTerminal.builder.component;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class LabelBuilderTest {
    private LabelBuilder builder;

    @Before
    public void initalizeBuilder() {
        builder = new LabelBuilder();
    }

    @Test(expected=NullPointerException.class)
    public void testCheckState_withNoPanelSet() {
        builder.checkState();
    }

    @Test
    public void testReset() {
        builder.setText("Testing");
        builder.setBackgroundColor(Color.RED);
        builder.setForegroundColor(Color.RED);

        builder.reset();

        Assert.assertEquals("", builder.getText());
        Assert.assertEquals(new Color(0x366C9F), builder.getBackgroundColor());
        Assert.assertEquals(new Color(0xFFCF0F), builder.getForegroundColor());
    }

    @Test
    public void setText_withValidText() {
        builder.setText("Testing");
        Assert.assertEquals("Testing", builder.getText());
    }

    @Test
    public void setText_withNullText() {
        builder.setText("Testing");
        builder.setText(null);
        Assert.assertEquals("Testing", builder.getText());
    }

    @Test
    public void setBackgroundColor_withValidColor() {
        builder.setBackgroundColor(Color.RED);
        Assert.assertEquals(Color.RED, builder.getBackgroundColor());
    }

    @Test
    public void setBackgroundColor_checked_withNullColor() {
        builder.setBackgroundColor(Color.RED);
        builder.setBackgroundColor(null);
        Assert.assertEquals(Color.RED, builder.getBackgroundColor());
    }

    @Test
    public void setForegroundColor_checked_withValidColor() {
        builder.setForegroundColor(Color.RED);
        Assert.assertEquals(Color.RED, builder.getForegroundColor());
    }

    @Test
    public void setForegroundColor_checked_withNullColor() {
        builder.setForegroundColor(Color.RED);
        builder.setForegroundColor(null);
        Assert.assertEquals(Color.RED, builder.getForegroundColor());
    }
}
