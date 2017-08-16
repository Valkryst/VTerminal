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

    @Test
    public void testReset() {
        builder.setText("Testing");
        builder.setBackgroundColor(Color.RED);
        builder.setForegroundColor(Color.RED);

        builder.reset();

        Assert.assertEquals("", builder.getText());
        Assert.assertEquals(new Color(45, 45, 45), builder.getBackgroundColor());
        Assert.assertEquals(new Color(249, 202, 0), builder.getForegroundColor());
    }
}
