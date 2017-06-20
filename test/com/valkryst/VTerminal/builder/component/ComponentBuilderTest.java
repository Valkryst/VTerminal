package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.Button;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComponentBuilderTest {
    private ComponentBuilder<Button, ButtonBuilder> componentBuilder;

    @Before
    public void initalizeBuilder() {
        componentBuilder = new ComponentBuilder<>();
    }

    @Test
    public void testConstructor() {
        new ComponentBuilder<>();
    }

    @Test
    public void testBuild() {
        Assert.assertNull(componentBuilder.build());
    }

    @Test
    public void testReset() {
        componentBuilder.setColumnIndex(5);
        componentBuilder.setRowIndex(6);

        // Note that the panel variable isn't checked as it
        // would require the creation of a Panel which would
        // need to visibly open. I'm unsure if Travis CI can
        // do GUI-related tests.

        componentBuilder.reset();

        Assert.assertEquals(0, componentBuilder.getColumnIndex());
        Assert.assertEquals(0, componentBuilder.getRowIndex());
    }

    @Test
    public void testSetColumnIndex() {
        componentBuilder.setColumnIndex(6);
        Assert.assertEquals(6, componentBuilder.getColumnIndex());
    }

    @Test
    public void testSetColumnIndex_withIndexEqualToZero() {
        componentBuilder.setColumnIndex(0);
        Assert.assertEquals(0, componentBuilder.getColumnIndex());
    }

    @Test
    public void testSetColumnIndex_withIndexLessThanZero() {
        componentBuilder.setColumnIndex(-1);
        Assert.assertEquals(0, componentBuilder.getColumnIndex());
    }

    @Test
    public void testSetRowIndex() {
        componentBuilder.setRowIndex(6);
        Assert.assertEquals(6, componentBuilder.getRowIndex());
    }

    @Test
    public void testSetRowIndex_withIndexEqualToZero() {
        componentBuilder.setRowIndex(0);
        Assert.assertEquals(0, componentBuilder.getRowIndex());
    }

    @Test
    public void testSetRowIndex_withIndexLessThanZero() {
        componentBuilder.setRowIndex(-1);
        Assert.assertEquals(0, componentBuilder.getRowIndex());
    }
}
