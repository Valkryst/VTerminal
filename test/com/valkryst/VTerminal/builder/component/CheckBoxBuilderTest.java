package com.valkryst.VTerminal.builder.component;

import com.valkryst.VRadio.Radio;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class CheckBoxBuilderTest {
    private CheckBoxBuilder builder;

    @Before
    public void initalizeBuilder() {
        builder = new CheckBoxBuilder();
    }

    @Test
    public void testReset() {
        final Radio<String> radio = new Radio<>();

        builder.setRadio(radio);

        builder.setText("Test");

        builder.setEmptyBoxChar('Z');
        builder.setCheckedBoxChar('X');

        builder.setBackgroundColor_normal(Color.RED);
        builder.setForegroundColor_normal(Color.RED);

        builder.setBackgroundColor_hover(Color.RED);
        builder.setForegroundColor_hover(Color.RED);

        builder.setBackgroundColor_checked(Color.RED);
        builder.setForegroundColor_checked(Color.RED);

        builder.reset();

        Assert.assertEquals(null, builder.getRadio());

        Assert.assertEquals("", builder.getText());

        Assert.assertEquals('☐', builder.getEmptyBoxChar());
        Assert.assertEquals('☑', builder.getCheckedBoxChar());

        Assert.assertEquals(new Color(45, 45, 45), builder.getBackgroundColor_normal());
        Assert.assertEquals(new Color(45, 190, 255), builder.getForegroundColor_normal());

        Assert.assertEquals(new Color(45, 255, 99), builder.getBackgroundColor_hover());
        Assert.assertEquals(new Color(22, 127, 49), builder.getForegroundColor_hover());

        Assert.assertEquals(new Color(45, 45, 45), builder.getBackgroundColor_checked());
        Assert.assertEquals(new Color(255, 215, 45), builder.getForegroundColor_checked());
    }
}
