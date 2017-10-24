package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.builder.component.LabelBuilder;
import org.junit.Test;

public class LabelTest {
    @Test(expected=NullPointerException.class)
    public void testConstructor_withNullBuilder() {
        new Label(null);
    }

    @Test
    public void testConstructor_withValidBuilder() {
        final LabelBuilder builder = new LabelBuilder();
        builder.setText("Testing");
        builder.build();
        new Label(builder);
    }
}
