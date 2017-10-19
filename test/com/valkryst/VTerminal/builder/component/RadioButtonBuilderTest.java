package com.valkryst.VTerminal.builder.component;

import com.valkryst.VJSON.VJSONLoader;
import com.valkryst.VTerminal.component.RadioButtonGroup;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class RadioButtonBuilderTest {
    private RadioButtonBuilder builder;
    private final RadioButtonGroup group = new RadioButtonGroup();

    @Before
    public void setVars() {
        builder = new RadioButtonBuilder();
        builder.setText("1234");
        builder.setEmptyButtonChar('/');
        builder.setCheckedButtonChar('?');
        builder.setGroup(group);
    }

    @Test
    public void testReset() {
        // Ensure all vars are set
        Assert.assertEquals(builder.getText(), "1234");
        Assert.assertEquals(builder.getEmptyButtonChar(), '/');
        Assert.assertEquals(builder.getCheckedButtonChar(), '?');
        Assert.assertEquals(builder.getGroup(), group);

        // Reset
        builder.reset();

        // Ensure all vars are reset
        Assert.assertNotEquals(builder.getText(), "1234");
        Assert.assertNotEquals(builder.getEmptyButtonChar(), '/');
        Assert.assertNotEquals(builder.getCheckedButtonChar(), '?');
        Assert.assertNotEquals(builder.getGroup(), group);
    }

    @Test
    public void testParse() throws IOException, ParseException {
        final RadioButtonBuilder builder = new RadioButtonBuilder();
        VJSONLoader.loadFromJSON(builder, System.getProperty("user.dir") + "/res_test/components/RadioButton.json");

        Assert.assertEquals(builder.getText(), "1234");
        Assert.assertEquals(builder.getEmptyButtonChar(), '/');
        Assert.assertEquals(builder.getCheckedButtonChar(), '?');
    }
}
