package com.valkryst.VTerminal.component;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.builder.component.TextAreaBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TextAreaTest {
    private TextArea textArea;

    @Before
    public void testInitializeButton() {
        final TextAreaBuilder textAreaBuilder = new TextAreaBuilder();
        textAreaBuilder.setRadio(new Radio<>());
        textAreaBuilder.setWidth(8);
        textAreaBuilder.setHeight(3);
        textArea = textAreaBuilder.build();
    }

    @Test
    public void testSetText_twoParams_withValidText() {
        textArea.setText(0, "TestingA");
        textArea.setText(1, "TestingB");
        textArea.setText(2, "TestingC");

        Assert.assertEquals("TestingA", textArea.getText()[0]);
        Assert.assertEquals("TestingB", textArea.getText()[1]);
        Assert.assertEquals("TestingC", textArea.getText()[2]);
    }

    @Test(expected=NullPointerException.class)
    public void testSetText_twoParams_withNullText() {
        textArea.setText(0, null);
    }

    @Test
    public void testSetText_twoParams_withEmptyText() {
        textArea.setText(0, "TestingA");
        textArea.setText(1, "TestingB");
        textArea.setText(2, "TestingC");

        Assert.assertEquals("TestingA", textArea.getText()[0]);
        Assert.assertEquals("TestingB", textArea.getText()[1]);
        Assert.assertEquals("TestingC", textArea.getText()[2]);

        textArea.setText(0, "");

        Assert.assertEquals("TestingA", textArea.getText()[0]);
        Assert.assertEquals("TestingB", textArea.getText()[1]);
        Assert.assertEquals("TestingC", textArea.getText()[2]);
    }

    @Test
    public void testGetText() {
        textArea.setText(0, "TestingA");
        textArea.setText(1, "TestingB");
        textArea.setText(2, "TestingC");

        Assert.assertEquals(4, textArea.getText().length);
        Assert.assertEquals("TestingA", textArea.getText()[0]);
        Assert.assertEquals("TestingB", textArea.getText()[1]);
        Assert.assertEquals("TestingC", textArea.getText()[2]);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testClearText_oneParam_withNegativeIndex() {
        textArea.clearText(-1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testClearText_oneParam_withIndexExceedingHeight() {
        textArea.clearText(textArea.getHeight() + 1);
    }

    @Test
    public void testClearText_oneParam_withValidIndex() {
        textArea.setText(0, "TestingA");
        textArea.setText(1, "TestingB");
        textArea.setText(2, "TestingC");

        Assert.assertEquals("TestingA", textArea.getText()[0]);
        Assert.assertEquals("TestingB", textArea.getText()[1]);
        Assert.assertEquals("TestingC", textArea.getText()[2]);

        textArea.clearText(0);

        Assert.assertEquals("        ", textArea.getText()[0]);
        Assert.assertEquals("TestingB", textArea.getText()[1]);
        Assert.assertEquals("TestingC", textArea.getText()[2]);
    }

    @Test
    public void testClearText() {
        textArea.setText(0, "TestingA");
        textArea.setText(1, "TestingB");
        textArea.setText(2, "TestingC");

        Assert.assertEquals("TestingA", textArea.getText()[0]);
        Assert.assertEquals("TestingB", textArea.getText()[1]);
        Assert.assertEquals("TestingC", textArea.getText()[2]);

        textArea.clearText();

        Assert.assertEquals("        ", textArea.getText()[0]);
        Assert.assertEquals("        ", textArea.getText()[1]);
        Assert.assertEquals("        ", textArea.getText()[2]);
    }
}
