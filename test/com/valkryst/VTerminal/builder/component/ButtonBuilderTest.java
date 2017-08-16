package com.valkryst.VTerminal.builder.component;

import com.valkryst.VRadio.Radio;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;


public class ButtonBuilderTest {
    private ButtonBuilder builder;

    @Before
    public void initializeBuilder() {
        builder = new ButtonBuilder();
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

        Assert.assertEquals(new Color(45, 45, 45), builder.getBackgroundColor_normal());
        Assert.assertEquals(new Color(45, 190, 255), builder.getForegroundColor_normal());

        Assert.assertEquals(new Color(45, 255, 99), builder.getBackgroundColor_hover());
        Assert.assertEquals(new Color(22, 127, 49), builder.getForegroundColor_hover());

        Assert.assertEquals(new Color(33, 191, 74), builder.getBackgroundColor_pressed());
        Assert.assertEquals(new Color(16, 95, 36), builder.getForegroundColor_pressed());
    }
}
