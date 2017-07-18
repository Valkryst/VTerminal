package com.valkryst.VTerminal.builder.component;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.awt.Color;

@RunWith(JUnitQuickcheck.class)
public class LabelBuilderTest {
    private LabelBuilder builder;

    @Before
    public void initalizeBuilder() {
        builder = new LabelBuilder();
    }

    @Test(expected=IllegalStateException.class)
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

    @Property
    public void setText_withValidText(final String text) {
        builder.setText(text);
        Assert.assertEquals(text, builder.getText());
    }

    @Property
    public void setText_withNullText(final String text) {
        builder.setText(text);
        builder.setText(null);
        Assert.assertEquals(text, builder.getText());
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
