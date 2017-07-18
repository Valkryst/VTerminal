package com.valkryst.VTerminal.builder.component;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.valkryst.VRadio.Radio;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.awt.Color;

@RunWith(JUnitQuickcheck.class)
public class ButtonBuilderTest {
    private ButtonBuilder builder;

    @Before
    public void initalizeBuilder() {
        builder = new ButtonBuilder();
    }

    @Test(expected=IllegalStateException.class)
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

    @Property
    public void testSetTest(final String text) {
        builder.setText(text);
        Assert.assertEquals(text, builder.getText());
    }

    @Property
    public void testSetTest_withNullText(final String text) {
        builder.setText(text);
        builder.setText(null);
        Assert.assertEquals(text, builder.getText());
    }

    @Property
    public void testSetStartingCharacter(final char character) {
        builder.setStartingCharacter(character);
        Assert.assertEquals(character, builder.getStartingCharacter());
    }

    @Property
    public void testSetEndingCharacter(final char character) {
        builder.setEndingCharacter(character);
        Assert.assertEquals(character, builder.getEndingCharacter());
    }

    @Test
    public void testSetBackgroundColor_normal() {
        final Color color = Color.YELLOW;

        builder.setBackgroundColor_normal(color);
        Assert.assertEquals(color, builder.getBackgroundColor_normal());
    }

    @Test
    public void testSetBackgroundColor_normal_withNullColor() {
        final Color color = Color.YELLOW;

        builder.setBackgroundColor_normal(color);
        builder.setBackgroundColor_normal(null);
        Assert.assertEquals(color, builder.getBackgroundColor_normal());
    }

    @Test
    public void testSetForegroundColor_normal() {
        final Color color = Color.YELLOW;

        builder.setForegroundColor_normal(color);
        Assert.assertEquals(color, builder.getForegroundColor_normal());
    }

    @Test
    public void testSetForegroundColor_normal_withNullColor() {
        final Color color = Color.YELLOW;

        builder.setForegroundColor_normal(color);
        builder.setForegroundColor_normal(null);
        Assert.assertEquals(color, builder.getForegroundColor_normal());
    }

    @Test
    public void testSetBackgroundColor_hover() {
        final Color color = Color.YELLOW;

        builder.setBackgroundColor_hover(color);
        Assert.assertEquals(color, builder.getBackgroundColor_hover());
    }

    @Test
    public void testSetBackgroundColor_hover_withNullColor() {
        final Color color = Color.YELLOW;

        builder.setBackgroundColor_hover(color);
        builder.setBackgroundColor_hover(null);
        Assert.assertEquals(color, builder.getBackgroundColor_hover());
    }

    @Test
    public void testSetForegroundColor_hover() {
        final Color color = Color.YELLOW;

        builder.setForegroundColor_hover(color);
        Assert.assertEquals(color, builder.getForegroundColor_hover());
    }

    @Test
    public void testSetForegroundColor_hover_withNullColor() {
        final Color color = Color.YELLOW;

        builder.setForegroundColor_hover(color);
        builder.setForegroundColor_hover(null);
        Assert.assertEquals(color, builder.getForegroundColor_hover());
    }

    @Test
    public void testSetBackgroundColor_pressed() {
        final Color color = Color.YELLOW;

        builder.setBackgroundColor_pressed(color);
        Assert.assertEquals(color, builder.getBackgroundColor_pressed());
    }

    @Test
    public void testSetBackgroundColor_pressed_withNullColor() {
        final Color color = Color.YELLOW;

        builder.setBackgroundColor_pressed(color);
        builder.setBackgroundColor_pressed(null);
        Assert.assertEquals(color, builder.getBackgroundColor_pressed());
    }

    @Test
    public void testSetForegroundColor_pressed() {
        final Color color = Color.YELLOW;

        builder.setBackgroundColor_pressed(color);
        Assert.assertEquals(color, builder.getBackgroundColor_pressed());
    }

    @Test
    public void testSetForegroundColor_pressed_withNullColor() {
        final Color color = Color.YELLOW;

        builder.setBackgroundColor_pressed(color);
        builder.setBackgroundColor_pressed(null);
        Assert.assertEquals(color, builder.getBackgroundColor_pressed());
    }

    @Test
    public void testSetOnClickFunction() {
        final Runnable runnable = () -> System.out.println("Test");

        builder.setOnClickFunction(runnable);
        Assert.assertEquals(runnable, builder.getOnClickFunction());
    }

    @Test
    public void testSetOnClickFunction_withNullFunction() {
        final Runnable runnable = () -> System.out.println("Test");

        builder.setOnClickFunction(runnable);
        builder.setOnClickFunction(null);
        Assert.assertEquals(runnable, builder.getOnClickFunction());
    }
}
