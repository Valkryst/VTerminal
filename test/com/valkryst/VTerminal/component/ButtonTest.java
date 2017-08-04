package com.valkryst.VTerminal.component;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.builder.component.ButtonBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

public class ButtonTest {
    private Button button;

    @Before
    public void testInitializeButton() {
        final ButtonBuilder buttonBuilder = new ButtonBuilder();
        buttonBuilder.setText("Testing");
        buttonBuilder.setRadio(new Radio<>());
        button = buttonBuilder.build();
    }

    @Test
    public void testSetStartingCharacter() {
        button.setStartingCharacter('?');

        Assert.assertEquals('?', button.getStartingCharacter());
        Assert.assertNotEquals('?', button.getEndingCharacter());
    }

    @Test
    public void testSetEndingCharacter() {
        button.setEndingCharacter('?');

        Assert.assertNotEquals('?', button.getStartingCharacter());
        Assert.assertEquals('?', button.getEndingCharacter());
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

    @Test
    public void testSetBackgroundColor_pressed() {
        button.setBackgroundColor_pressed(Color.RED);
        Assert.assertEquals(Color.RED, button.getBackgroundColor_pressed());
    }

    @Test(expected=NullPointerException.class)
    public void testSetBackgroundColor_pressed_withNullColor() {
        button.setBackgroundColor_pressed(null);
    }

    @Test
    public void testSetForegroundColor_pressed() {
        button.setForegroundColor_pressed(Color.RED);
        Assert.assertEquals(Color.RED, button.getForegroundColor_pressed());
    }

    @Test(expected=NullPointerException.class)
    public void testSetForegroundColor_pressed_withNullColor() {
        button.setForegroundColor_pressed(null);
    }
}
