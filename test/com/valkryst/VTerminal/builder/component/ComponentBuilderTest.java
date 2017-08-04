package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.Button;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ComponentBuilderTest {
    private ComponentBuilder<Button> builder;

    @Before
    public void initializeBuilder() {
        builder = new ComponentBuilder<>();
    }

    @Test
    public void testConstructor() {
        new ComponentBuilder<>();
    }

    @Test
    public void testBuild() {
        builder.build();
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
}
