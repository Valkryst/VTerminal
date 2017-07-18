package com.valkryst.VTerminal.builder.component;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.component.Button;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitQuickcheck.class)
public class ComponentBuilderTest {
    private ComponentBuilder<Button, ButtonBuilder> builder;

    @Before
    public void initializeBuilder() {
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

    @Property
    public void testSetColumnIndex_withValidInput(@InRange(minInt = 1) final int column) {
        builder.setColumnIndex(column);
        Assert.assertEquals(column, builder.getColumnIndex());
    }

    @Property
    public void testSetColumnIndex_withInvalidInput(@InRange(maxInt = 0) final int column) {
        builder.setColumnIndex(column);
        Assert.assertEquals(0, builder.getColumnIndex());
    }

    @Property
    public void testSetRowIndex_withValidInput(@InRange(minInt = 1) final int row) {
        builder.setRowIndex(row);
        Assert.assertEquals(row, builder.getRowIndex());
    }

    @Property
    public void testSetRowIndex_withInvalidInput(@InRange(maxInt = 0) final int row) {
        builder.setRowIndex(row);
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
