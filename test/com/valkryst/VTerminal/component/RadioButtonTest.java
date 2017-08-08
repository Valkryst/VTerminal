package com.valkryst.VTerminal.component;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.builder.component.RadioButtonBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class RadioButtonTest {
    private RadioButton button;

    @Before
    public void testInitializeButton() {
        final RadioButtonBuilder buttonBuilder = new RadioButtonBuilder();
        buttonBuilder.setText("Testing");
        buttonBuilder.setRadio(new Radio<>());
        buttonBuilder.setGroup(new RadioButtonGroup());
        button = buttonBuilder.build();
    }

    @Test
    public void testSetEmptyBoxCharacter() {
        button.setEmptyBoxCharacter('?');

        Assert.assertEquals('?', button.getEmptyBoxChar());
        Assert.assertNotEquals('?', button.getCheckedBoxChar());
    }

    @Test
    public void testSetCheckedBoxCharacter() {
        button.setCheckedBoxChar('?');

        Assert.assertNotEquals('?', button.getCheckedBoxChar());
        Assert.assertEquals('?', button.getEmptyBoxChar());
    }

    @Test
    public void testSetBackgroundColor_normal() {
        button.setBackgroundColor_normal(Color.RED);
        Assert.assertEquals(Color.RED, button.getBackgroundColor_normal());
    }

    @Test(expected=NullPointerException.class)
    public void testSetBackgroundColor_normal_withNullColor() {
        button.setBackgroundColor_normal(null);
    }

    @Test
    public void testSetForegroundColor_normal() {
        button.setForegroundColor_normal(Color.RED);
        Assert.assertEquals(Color.RED, button.getForegroundColor_normal());
    }

    @Test(expected=NullPointerException.class)
    public void testSetForegroundColor_normal_withNullColor() {
        button.setForegroundColor_normal(null);
    }

    @Test
    public void testSetBackgroundColor_hover() {
        button.setBackgroundColor_hover(Color.RED);
        Assert.assertEquals(Color.RED, button.getBackgroundColor_hover());
    }

    @Test(expected=NullPointerException.class)
    public void testSetBackgroundColor_hover_withNullColor() {
        button.setBackgroundColor_hover(null);
    }

    @Test
    public void testSetForegroundColor_hover() {
        button.setForegroundColor_hover(Color.RED);
        Assert.assertEquals(Color.RED, button.getForegroundColor_hover());
    }

    @Test(expected=NullPointerException.class)
    public void testSetForegroundColor_hover_withNullColor() {
        button.setForegroundColor_hover(null);
    }
}
