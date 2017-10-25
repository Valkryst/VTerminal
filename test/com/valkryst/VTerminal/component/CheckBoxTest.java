package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.builder.component.CheckBoxBuilder;
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
}
