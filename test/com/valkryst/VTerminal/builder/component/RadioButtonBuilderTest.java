package com.valkryst.VTerminal.builder.component;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.component.RadioButtonGroup;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class RadioButtonBuilderTest {
    private RadioButtonBuilder builder;

    @Before
    public void initalizeBuilder() {
        builder = new RadioButtonBuilder();
    }

    @Test(expected=NullPointerException.class)
    public void testCheckState_withNoPanelSet() {
        builder.checkState();
    }

    @Test
    public void testReset() {
        final Radio<String> radio = new Radio<>();
        final RadioButtonGroup group = new RadioButtonGroup();

        builder.setRadio(radio);

        builder.setText("Test");

        builder.setEmptyButtonChar('Z');
        builder.setCheckedButtonChar('X');

        builder.setGroup(group);

        builder.setBackgroundColor_normal(Color.RED);
        builder.setForegroundColor_normal(Color.RED);

        builder.setBackgroundColor_hover(Color.RED);
        builder.setForegroundColor_hover(Color.RED);

        builder.setBackgroundColor_checked(Color.RED);
        builder.setForegroundColor_checked(Color.RED);

        builder.reset();

        Assert.assertEquals(null, builder.getRadio());

        Assert.assertEquals("", builder.getText());

        Assert.assertEquals('○', builder.getEmptyButtonChar());
        Assert.assertEquals('◉', builder.getCheckedButtonChar());

        Assert.assertEquals(null, builder.getGroup());

        Assert.assertEquals(new Color(45, 45, 45), builder.getBackgroundColor_normal());
        Assert.assertEquals(new Color(45, 190, 255), builder.getForegroundColor_normal());

        Assert.assertEquals(new Color(45, 255, 99), builder.getBackgroundColor_hover());
        Assert.assertEquals(new Color(22, 127, 49), builder.getForegroundColor_hover());

        Assert.assertEquals(new Color(45, 45, 45), builder.getBackgroundColor_checked());
        Assert.assertEquals(new Color(255, 215, 45), builder.getForegroundColor_checked());
    }

    @Test(expected=NullPointerException.class)
    public void testCheckState_withNullRadio() {
        builder.build();
    }

    @Test(expected=NullPointerException.class)
    public void testCheckState_withNullGroup() {
        builder.setRadio(new Radio<>());
        builder.build();
    }
}
