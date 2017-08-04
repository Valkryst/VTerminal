package com.valkryst.VTerminal.builder.component;

import com.valkryst.VRadio.Radio;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;


public class ButtonBuilderTest {
    private ButtonBuilder builder;

    @Before
    public void initalizeBuilder() {
        builder = new ButtonBuilder();
    }

    @Test(expected=NullPointerException.class)
    public void testCheckState_withNoPanelSet() {
        builder.checkState();
    }

    @Test
    public void testReset() {
        final Radio<String> radio = new Radio<>();
        final Thread function = new Thread();

        builder.setText("Test Text");

        builder.setRadio(radio);

        builder.setOnClickFunction(function);

        builder.setStartingCharacter('A');
        builder.setEndingCharacter('B');

        builder.setBackgroundColor_normal(Color.RED);
        builder.setForegroundColor_normal(Color.BLUE);

        builder.setBackgroundColor_hover(Color.RED);
        builder.setForegroundColor_hover(Color.BLUE);

        builder.setBackgroundColor_pressed(Color.RED);
        builder.setForegroundColor_pressed(Color.BLUE);

        // Doesn't test the onClickFunction.

        builder.reset();

        Assert.assertEquals("", builder.getText());

        Assert.assertEquals(null, builder.getRadio());

        Assert.assertEquals('<', builder.getStartingCharacter());
        Assert.assertEquals('>', builder.getEndingCharacter());

        Assert.assertEquals(new Color(0x366C9F), builder.getBackgroundColor_normal());
        Assert.assertEquals(new Color(0x66CCFF), builder.getForegroundColor_normal());

        Assert.assertEquals(new Color(0x71AB14), builder.getBackgroundColor_hover());
        Assert.assertEquals(new Color(0x99E000), builder.getForegroundColor_hover());

        Assert.assertEquals(new Color(0x366C9F), builder.getBackgroundColor_pressed());
        Assert.assertEquals(new Color(0xFFFF66), builder.getForegroundColor_pressed());
    }
}
