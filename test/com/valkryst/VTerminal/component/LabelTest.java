package com.valkryst.VTerminal.component;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.builder.component.LabelBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.awt.Color;

public class LabelTest {
    private final Radio<String> radio = new Radio<>();

    @Test
    public void testConstructor_withValidBuilder() {
        final LabelBuilder builder = new LabelBuilder();
        builder.setText("Testing");
        builder.setBackgroundColor(Color.RED);
        builder.setForegroundColor(Color.BLUE);
        builder.setRadio(radio);

        final Label label = new Label(builder);

        String text = "";
        for (final AsciiCharacter character : label.getString(0).getCharacters()) {
            text += character.getCharacter();
        }

        Assert.assertEquals("Testing", text);
        Assert.assertEquals(Color.RED, label.getBackgroundColor());
        Assert.assertEquals(Color.BLUE, label.getForegroundColor());
    }

    @Test(expected=NullPointerException.class)
    public void testConstructor_withNullBuilder() {
        new Label(null);
    }
}
