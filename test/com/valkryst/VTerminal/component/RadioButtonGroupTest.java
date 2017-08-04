package com.valkryst.VTerminal.component;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.builder.component.RadioButtonBuilder;
import org.junit.Assert;
import org.junit.Test;

public class RadioButtonGroupTest {
    /* Uncomment when issues resolved.
    @Test
    public void testHashCode_withEqualGroup() {
        final RadioButtonBuilder builder = new RadioButtonBuilder();
        builder.setRadio(new Radio<>());
        builder.setText("Testing");

        final RadioButtonGroup groupA = new RadioButtonGroup();
        builder.setGroup(groupA);
        builder.build();


        final RadioButtonGroup groupB = new RadioButtonGroup();
        builder.setGroup(groupB);
        builder.build();

        Assert.assertEquals(groupA.hashCode(), groupB.hashCode());
    }*/

    @Test
    public void testHashCode_withNonEqualGroup() {
        final RadioButtonBuilder builder = new RadioButtonBuilder();
        builder.setRadio(new Radio<>());
        builder.setText("Testing");

        final RadioButtonGroup groupA = new RadioButtonGroup();
        builder.setGroup(groupA);
        builder.build();


        final RadioButtonGroup groupB = new RadioButtonGroup();
        builder.setGroup(groupB);
        builder.setText("Testing2");
        builder.build();

        Assert.assertNotEquals(groupA.hashCode(), groupB.hashCode());
    }

    @Test(expected=NullPointerException.class)
    public void testSetCheckedButton_withNullButton() {
        new RadioButtonGroup().setCheckedButton(null);
    }

    @Test
    public void testSetCheckedButton_withValidButton() {
        final RadioButtonBuilder builder = new RadioButtonBuilder();
        builder.setRadio(new Radio<>());

        final RadioButtonGroup group = new RadioButtonGroup();
        builder.setGroup(group);


        final RadioButton buttonA = builder.setText("Testing").build();
        final RadioButton buttonB = builder.setText("Testing2").build();

        Assert.assertTrue(buttonA.isChecked());
        Assert.assertFalse(buttonB.isChecked());

        group.setCheckedButton(buttonB);

        Assert.assertFalse(buttonA.isChecked());
        Assert.assertTrue(buttonB.isChecked());
    }

    @Test(expected=NullPointerException.class)
    public void testAddRadioButton_withNullButton() {
        new RadioButtonGroup().addRadioButton(null);
    }

    @Test
    public void testAddRadioButton_withValidButton() {
        final RadioButtonBuilder builder = new RadioButtonBuilder();
        builder.setRadio(new Radio<>());
        builder.setText("Testing");

        final RadioButtonGroup groupA = new RadioButtonGroup();
        builder.setGroup(groupA);
        builder.build();
    }

    @Test(expected=NullPointerException.class)
    public void testRemoveRadioButton_withNullButton() {
        new RadioButtonGroup().removeRadioButton(null);
    }

    @Test
    public void testRemoveRadioButton_withValidButton() {
        final RadioButtonBuilder builder = new RadioButtonBuilder();
        builder.setRadio(new Radio<>());
        builder.setText("Testing");

        final RadioButtonGroup groupA = new RadioButtonGroup();
        builder.setGroup(groupA);

        groupA.removeRadioButton(builder.build());
    }
}
