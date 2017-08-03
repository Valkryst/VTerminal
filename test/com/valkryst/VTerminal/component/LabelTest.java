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

    @Test
    public void testEquals_withNonLabelObject() {
        final LabelBuilder builder = new LabelBuilder();
        builder.setText("Testing");
        builder.setBackgroundColor(Color.RED);
        builder.setForegroundColor(Color.BLUE);
        builder.setRadio(radio);

        final Label label = new Label(builder);

        Assert.assertNotEquals(1234, label);
    }

    @Test
    public void testEquals_withSelf() {
        final LabelBuilder builder = new LabelBuilder();
        builder.setText("Testing");
        builder.setBackgroundColor(Color.RED);
        builder.setForegroundColor(Color.BLUE);
        builder.setRadio(radio);

        final Label label = new Label(builder);

        Assert.assertEquals(label, label);
    }

    @Test
    public void testEquals_withEqualLabel() {
        final LabelBuilder builder = new LabelBuilder();
        builder.setText("Testing");
        builder.setBackgroundColor(Color.RED);
        builder.setForegroundColor(Color.BLUE);
        builder.setRadio(radio);

        final Label labelA = new Label(builder);
        final Label labelB = new Label(builder);

        Assert.assertEquals(labelA, labelB);
    }

    @Test
    public void testEquals_withNonEqualLabel() {
        final LabelBuilder builder = new LabelBuilder();
        builder.setText("Testing");
        builder.setBackgroundColor(Color.RED);
        builder.setForegroundColor(Color.BLUE);
        builder.setRadio(radio);

        final Label labelA = new Label(builder);

        builder.setForegroundColor(Color.GREEN);
        final Label labelB = new Label(builder);

        Assert.assertNotEquals(labelA, labelB);
    }

    /* Uncomment when issues fixed.
    @Test
    public void testHashCode_withEqualLabel() {
        final LabelBuilder builder = new LabelBuilder();
        builder.setText("Testing");
        builder.setBackgroundColor(Color.RED);
        builder.setForegroundColor(Color.BLUE);
        builder.setRadio(radio);

        final Label labelA = new Label(builder);
        final Label labelB = new Label(builder);

        Assert.assertEquals(labelA.hashCode(), labelB.hashCode());
    }
    */

    @Test
    public void testHashCode_withNonEqualLabel() {
        final LabelBuilder builder = new LabelBuilder();
        builder.setText("Testing");
        builder.setBackgroundColor(Color.RED);
        builder.setForegroundColor(Color.BLUE);
        builder.setRadio(radio);

        final Label labelA = new Label(builder);

        builder.setForegroundColor(Color.GREEN);
        final Label labelB = new Label(builder);

        Assert.assertNotEquals(labelA.hashCode(), labelB.hashCode());
    }
}
