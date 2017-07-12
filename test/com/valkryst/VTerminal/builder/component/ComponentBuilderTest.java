package com.valkryst.VTerminal.builder.component;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.component.Button;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComponentBuilderTest {
    private ComponentBuilder<Button, ButtonBuilder> builder;

    @Before
    public void initalizeBuilder() {
        builder = new ComponentBuilder<>();
    }

    @Test
    public void testConstructor() {
        new ComponentBuilder<>();
    }

    @Test(expected=IllegalStateException.class)
    public void testBuild() {
        builder.build();
    }

    @Test(expected=IllegalStateException.class)
    public void testCheckState_withNoPanelSet() {
        builder.checkState();
    }

    @Test
    public void testReset() {
        builder.setColumnIndex(5);
        builder.setRowIndex(6);

        // Note that the panel variable isn't checked as it
        // would require the creation of a Panel which would
        // need to visibly open. I'm unsure if Travis CI can
        // do GUI-related tests.

        builder.reset();

        Assert.assertEquals(0, builder.getColumnIndex());
        Assert.assertEquals(0, builder.getRowIndex());
    }

    @Test
    public void testSetColumnIndex() {
        builder.setColumnIndex(6);
        Assert.assertEquals(6, builder.getColumnIndex());
    }

    @Test
    public void testSetColumnIndex_withIndexEqualToZero() {
        builder.setColumnIndex(0);
        Assert.assertEquals(0, builder.getColumnIndex());
    }

    @Test
    public void testSetColumnIndex_withIndexLessThanZero() {
        builder.setColumnIndex(-1);
        Assert.assertEquals(0, builder.getColumnIndex());
    }

    @Test
    public void testSetRowIndex() {
        builder.setRowIndex(6);
        Assert.assertEquals(6, builder.getRowIndex());
    }

    @Test
    public void testSetRowIndex_withIndexEqualToZero() {
        builder.setRowIndex(0);
        Assert.assertEquals(0, builder.getRowIndex());
    }

    @Test
    public void testSetRowIndex_withIndexLessThanZero() {
        builder.setRowIndex(-1);
        Assert.assertEquals(0, builder.getRowIndex());
    }

    @Test
    public void testSetRadio_radio() {
        final Radio<String> radio = new Radio<>();

        builder.setRadio(radio);
        Assert.assertEquals(radio, builder.getRadio());
    }

    @Test
    public void testSetRadio_radio_withNullRadio() {
        final Radio<String> radio = new Radio<>();

        builder.setRadio(radio);
        builder.setRadio((Radio<String>)null);
        Assert.assertEquals(radio, builder.getRadio());
    }

    @Test
    public void testSetRadio_panel_withNullPanel() {
        final Radio<String> radio = new Radio<>();

        builder.setRadio(radio);
        builder.setRadio((Panel) null);
        Assert.assertEquals(radio, builder.getRadio());
    }
}
