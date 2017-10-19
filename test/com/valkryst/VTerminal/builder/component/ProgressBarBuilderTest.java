package com.valkryst.VTerminal.builder.component;

import com.valkryst.VJSON.VJSONLoader;
import com.valkryst.VTerminal.component.ProgressBar;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.io.IOException;

public class ProgressBarBuilderTest {
    private ProgressBarBuilder builder;

    @Before
    public void setVars() {
        builder = new ProgressBarBuilder();

        builder.setIncompleteCharacter('/');
        builder.setCompleteCharacter('?');

        builder.setBackgroundColor_incomplete(Color.RED);
        builder.setForegroundColor_incomplete(Color.BLUE);

        builder.setBackgroundColor_complete(Color.GREEN);
        builder.setForegroundColor_complete(Color.PINK);
    }

    @Test
    public void testBuild_withValidState() {
        final ProgressBar bar = builder.build();

        Assert.assertEquals(bar.getIncompleteCharacter(), '/');
        Assert.assertEquals(bar.getCompleteCharacter(), '?');

        Assert.assertEquals(bar.getBackgroundColor_incomplete(), Color.RED);
        Assert.assertEquals(bar.getForegroundColor_incomplete(), Color.BLUE);

        Assert.assertEquals(bar.getBackgroundColor_complete(), Color.GREEN);
        Assert.assertEquals(bar.getForegroundColor_complete(), Color.PINK);
    }

    @Test
    public void testReset() {
        // Ensure all vars are set
        Assert.assertEquals(builder.getIncompleteCharacter(), '/');
        Assert.assertEquals(builder.getCompleteCharacter(), '?');

        Assert.assertEquals(builder.getBackgroundColor_incomplete(), Color.RED);
        Assert.assertEquals(builder.getForegroundColor_incomplete(), Color.BLUE);

        Assert.assertEquals(builder.getBackgroundColor_complete(), Color.GREEN);
        Assert.assertEquals(builder.getForegroundColor_complete(), Color.PINK);

        // Reset
        builder.reset();

        // Ensure all vars are reset
        Assert.assertNotEquals(builder.getIncompleteCharacter(), '/');
        Assert.assertNotEquals(builder.getCompleteCharacter(), '?');

        Assert.assertNotEquals(builder.getBackgroundColor_incomplete(), Color.RED);
        Assert.assertNotEquals(builder.getForegroundColor_incomplete(), Color.BLUE);

        Assert.assertNotEquals(builder.getBackgroundColor_complete(), Color.GREEN);
        Assert.assertNotEquals(builder.getForegroundColor_complete(), Color.PINK);
    }

    @Test
    public void testParse() throws IOException, ParseException {
        final ProgressBarBuilder builder = new ProgressBarBuilder();
        VJSONLoader.loadFromJSON(builder, System.getProperty("user.dir") + "/res_test/components/ProgressBar.json");

        Assert.assertEquals(builder.getIncompleteCharacter(), '/');
        Assert.assertEquals(builder.getCompleteCharacter(), '?');

        Assert.assertEquals(builder.getBackgroundColor_incomplete(), Color.RED);
        Assert.assertEquals(builder.getForegroundColor_incomplete(), Color.BLUE);

        Assert.assertEquals(builder.getBackgroundColor_complete(), Color.GREEN);
        Assert.assertEquals(builder.getForegroundColor_complete(), Color.PINK);
    }
}
