package com.valkryst.VTerminal.component;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.builder.component.ProgressBarBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProgressBarTest {
    private ProgressBar bar;

    @Before
    public void initializeBar() {
        final ProgressBarBuilder builder = new ProgressBarBuilder();
        builder.setRadio(new Radio<>());
        bar = builder.build();
    }

    @Test
    public void testSetPercentComplete_withPercentBelow0() {
        bar.setPercentComplete(-10);
        Assert.assertEquals(0, bar.getPercentComplete());
    }

    @Test
    public void testSetPercentComplete_withPercentAbove100() {
        bar.setPercentComplete(1000);
        Assert.assertEquals(100, bar.getPercentComplete());
    }

    @Test
    public void testSetPercentComplete_withValidPercent() {
        bar.setPercentComplete(50);
        Assert.assertEquals(50, bar.getPercentComplete());

        final int numberOfCompleteChars = (int) (bar.getWidth() * (bar.getPercentComplete() / 100f));

        final ProgressBarBuilder builder = new ProgressBarBuilder();
        final AsciiCharacter[] characters = bar.getString(0).getCharacters();

        for (int x = 0 ; x < characters.length ; x++) {
            if (x < numberOfCompleteChars) {
                Assert.assertEquals(builder.getBackgroundColor_complete(), characters[x].getBackgroundColor());
                Assert.assertEquals(builder.getForegroundColor_complete(), characters[x].getForegroundColor());
            } else {
                Assert.assertEquals(builder.getBackgroundColor_incomplete(), characters[x].getBackgroundColor());
                Assert.assertEquals(builder.getForegroundColor_incomplete(), characters[x].getForegroundColor());
            }
        }
    }
}
