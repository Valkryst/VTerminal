package com.valkryst.VTerminal.builder.component;

import com.valkryst.VJSON.VJSONLoader;
import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.component.CheckBox;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class CheckBoxBuilderTest {
    private CheckBoxBuilder builder;

    @Before
    public void setVars() {
        builder = new CheckBoxBuilder();
        builder.setText("1234");
        builder.setEmptyBoxChar('/');
        builder.setCheckedBoxChar('?');
        builder.setChecked(true);
    }

    @Test
    public void testBuild_withValidState() {
        final CheckBox checkBox = builder.build();

        String text = "";

        for (final AsciiCharacter character : checkBox.getString(0).getCharacters()) {
            text += character.getCharacter();
        }

        Assert.assertEquals(builder.getText(), text);
        Assert.assertEquals(builder.getEmptyBoxChar(), '/');
        Assert.assertEquals(builder.getCheckedBoxChar(), '?');
        Assert.assertTrue(builder.isChecked());
    }

    @Test
    public void testReset() {
        // Ensure all vars are set
        Assert.assertEquals(builder.getText(), "1234");
        Assert.assertEquals(builder.getEmptyBoxChar(), '/');
        Assert.assertEquals(builder.getCheckedBoxChar(), '?');
        Assert.assertTrue(builder.isChecked());

        // Reset
        builder.reset();

        // Ensure all vars are reset
        Assert.assertNotEquals(builder.getText(), "1234");
        Assert.assertNotEquals(builder.getEmptyBoxChar(), '/');
        Assert.assertNotEquals(builder.getCheckedBoxChar(), '?');
        Assert.assertFalse(builder.isChecked());
    }

    @Test
    public void testParse() throws IOException, ParseException {
        final CheckBoxBuilder builder = new CheckBoxBuilder();
        VJSONLoader.loadFromJSON(builder, System.getProperty("user.dir") + "/res_test/components/CheckBox.json");

        Assert.assertEquals(builder.getText(), "1234");
        Assert.assertEquals(builder.getEmptyBoxChar(), '/');
        Assert.assertEquals(builder.getCheckedBoxChar(), '?');
        Assert.assertTrue(builder.isChecked());
    }
}
