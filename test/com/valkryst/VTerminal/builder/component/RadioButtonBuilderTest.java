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

    @Test(expected=IllegalStateException.class)
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

        Assert.assertEquals(new Color(0x366C9F), builder.getBackgroundColor_normal());
        Assert.assertEquals(new Color(0x66CCFF), builder.getForegroundColor_normal());

        Assert.assertEquals(new Color(0x71AB14), builder.getBackgroundColor_hover());
        Assert.assertEquals(new Color(0x99E000), builder.getForegroundColor_hover());

        Assert.assertEquals(new Color(0x366C9F), builder.getBackgroundColor_checked());
        Assert.assertEquals(new Color(0xFFFF66), builder.getForegroundColor_checked());
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
    public void setEmptyButtonChar() {
        builder.setEmptyButtonChar('?');
        Assert.assertEquals('?', builder.getEmptyButtonChar());
    }

    @Test
    public void setCheckedButtonChar() {
        builder.setCheckedButtonChar('?');
        Assert.assertEquals('?', builder.getCheckedButtonChar());
    }

    @Test
    public void setGroup_withValidGroup() {
        final RadioButtonGroup group = new RadioButtonGroup();
        builder.setGroup(group);
        Assert.assertEquals(group, builder.getGroup());
    }

    @Test
    public void setGroup_withNullGroup() {
        final RadioButtonGroup group = new RadioButtonGroup();
        builder.setGroup(group);
        builder.setGroup(null);
        Assert.assertEquals(group, builder.getGroup());
    }

    @Test
    public void setBackgroundColor_normal_withValidColor() {
        builder.setBackgroundColor_normal(Color.RED);
        Assert.assertEquals(Color.RED, builder.getBackgroundColor_normal());
    }

    @Test
    public void setBackgroundColor_normal_withNullColor() {
        builder.setBackgroundColor_normal(Color.RED);
        builder.setBackgroundColor_normal(null);
        Assert.assertEquals(Color.RED, builder.getBackgroundColor_normal());
    }

    @Test
    public void setForegroundColor_normal_withValidColor() {
        builder.setForegroundColor_normal(Color.RED);
        Assert.assertEquals(Color.RED, builder.getForegroundColor_normal());
    }

    @Test
    public void setForegroundColor_normal_withNullColor() {
        builder.setForegroundColor_normal(Color.RED);
        builder.setForegroundColor_normal(null);
        Assert.assertEquals(Color.RED, builder.getForegroundColor_normal());
    }

    @Test
    public void setBackgroundColor_hover_withValidColor() {
        builder.setBackgroundColor_hover(Color.RED);
        Assert.assertEquals(Color.RED, builder.getBackgroundColor_hover());
    }

    @Test
    public void setBackgroundColor_hover_withNullColor() {
        builder.setBackgroundColor_hover(Color.RED);
        builder.setBackgroundColor_hover(null);
        Assert.assertEquals(Color.RED, builder.getBackgroundColor_hover());
    }

    @Test
    public void setForegroundColor_hover_withValidColor() {
        builder.setForegroundColor_hover(Color.RED);
        Assert.assertEquals(Color.RED, builder.getForegroundColor_hover());
    }

    @Test
    public void setForegroundColor_hover_withNullColor() {
        builder.setForegroundColor_hover(Color.RED);
        builder.setForegroundColor_hover(null);
        Assert.assertEquals(Color.RED, builder.getForegroundColor_hover());
    }

    @Test
    public void setBackgroundColor_checked_withValidColor() {
        builder.setBackgroundColor_checked(Color.RED);
        Assert.assertEquals(Color.RED, builder.getBackgroundColor_checked());
    }

    @Test
    public void setBackgroundColor_checked_withNullColor() {
        builder.setBackgroundColor_checked(Color.RED);
        builder.setBackgroundColor_checked(null);
        Assert.assertEquals(Color.RED, builder.getBackgroundColor_checked());
    }

    @Test
    public void setForegroundColor_checked_withValidColor() {
        builder.setForegroundColor_checked(Color.RED);
        Assert.assertEquals(Color.RED, builder.getForegroundColor_checked());
    }

    @Test
    public void setForegroundColor_checked_withNullColor() {
        builder.setForegroundColor_checked(Color.RED);
        builder.setForegroundColor_checked(null);
        Assert.assertEquals(Color.RED, builder.getForegroundColor_checked());
    }
}
