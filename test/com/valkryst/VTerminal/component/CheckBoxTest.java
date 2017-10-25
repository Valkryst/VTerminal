package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.builder.component.CheckBoxBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CheckBoxTest {
    private CheckBox checkBox;
    private final Runnable function = () -> System.out.println("Test Func");

    @Before
    public void init() {
        final CheckBoxBuilder builder = new CheckBoxBuilder();
        builder.setText("Testing");
        builder.setOnClickFunction(function);
        checkBox = builder.build();
    }

    @Test(expected=NullPointerException.class)
    public void testConstructor_withNullBuilder() {
        new CheckBox(null);
    }

    @Test
    public void testConstructor_withValidBuilder() {
        final CheckBoxBuilder builder = new CheckBoxBuilder();
        builder.setText("Testing");
        builder.setOnClickFunction(function);
        new CheckBox(builder);
    }

    @Test
    public void testSetEmptyBoxCharacter() {
        checkBox.setEmptyBoxCharacter('M');
        Assert.assertEquals('M', checkBox.getString(0).getCharacters()[0].getCharacter());
        checkBox.check();
        Assert.assertNotEquals('M', checkBox.getString(0).getCharacters()[0].getCharacter());
    }

    @Test
    public void testSetCheckedBoxCharacter() {
        checkBox.setCheckedBoxCharacter('M');
        Assert.assertNotEquals('M', checkBox.getString(0).getCharacters()[0].getCharacter());
        checkBox.check();
        Assert.assertEquals('M', checkBox.getString(0).getCharacters()[0].getCharacter());
    }

    @Test
    public void testCheck() {
        checkBox.setEmptyBoxCharacter('M');
        Assert.assertEquals('M', checkBox.getString(0).getCharacters()[0].getCharacter());
        checkBox.check();
        Assert.assertNotEquals('M', checkBox.getString(0).getCharacters()[0].getCharacter());
    }

    @Test
    public void testUncheck() {
        checkBox.setEmptyBoxCharacter('M');
        Assert.assertEquals('M', checkBox.getString(0).getCharacters()[0].getCharacter());
        checkBox.check();
        Assert.assertNotEquals('M', checkBox.getString(0).getCharacters()[0].getCharacter());
        checkBox.uncheck();
        Assert.assertEquals('M', checkBox.getString(0).getCharacters()[0].getCharacter());
    }
}
