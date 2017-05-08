package com.valkryst;

import com.valkryst.AsciiPanel.AsciiPanel;
import com.valkryst.AsciiPanel.font.AsciiFont;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class AsciiPanelTest {
    private final AsciiFont font;

    public AsciiPanelTest() throws IOException {
        font = new AsciiFont("Fonts/DejaVu Sans Mono/16pt/bitmap.png", "Fonts/DejaVu Sans Mono/16pt/data.fnt");
    }

    @Test
    public void constructPanelWithNegativeWidth() {
        final AsciiPanel panel = new AsciiPanel(-1, 10, font);
        Assert.assertEquals(1, panel.getWidthInCharacters());
    }

    @Test
    public void constructPanelWithZeroWidth() {
        final AsciiPanel panel = new AsciiPanel(0, 10, font);
        Assert.assertEquals(1, panel.getWidthInCharacters());
    }

    @Test
    public void constructPanelWithPositiveWidth() {
        for (int i = 0 ; i < 100 ; i++) {
            final AsciiPanel panel = new AsciiPanel(i, 10, font);
            Assert.assertEquals(1, panel.getWidthInCharacters());
        }
    }

    @Test
    public void constructPanelWithNegativeHeight() {
        final AsciiPanel panel = new AsciiPanel(1, -1, font);
        Assert.assertEquals(1, panel.getHeightInCharacters());
    }

    @Test
    public void constructPanelWithZeroHeight() {
        final AsciiPanel panel = new AsciiPanel(1, 0, font);
        Assert.assertEquals(1, panel.getHeightInCharacters());
    }

    @Test
    public void constructPanelWithPositiveHeight() {
        for (int i = 0 ; i < 100 ; i++) {
            final AsciiPanel panel = new AsciiPanel(1, i, font);
            Assert.assertEquals(1, panel.getHeightInCharacters());
        }
    }

    @Test(expected=NullPointerException.class)
    public void constructPanelWithNullFont() {
        new AsciiPanel(1, 1, null);
    }

    @Test
    public void constructPanelCorrectly() {
        new AsciiPanel(1, 1, font);
    }

    @Test
    public void isPositionValidWithNegativeColumnIndex() {
        final AsciiPanel panel = new AsciiPanel(10, 10, font);
        Assert.assertFalse(panel.isPositionValid(-1, 1));
    }

    @Test
    public void isPositionValidWithValidColumnIndices() {
        final AsciiPanel panel = new AsciiPanel(10, 10, font);

        for (int i = 0 ; i < panel.getWidthInCharacters() ; i++) {
            Assert.assertTrue(panel.isPositionValid(i, 1));
        }
    }

    @Test
    public void isPositionValidWithNegativeRowIndex() {
        final AsciiPanel panel = new AsciiPanel(10, 10, font);
        Assert.assertFalse(panel.isPositionValid(1, -1));
    }

    @Test
    public void isPositionValidWithValidRowIndices() {
        final AsciiPanel panel = new AsciiPanel(10, 10, font);

        for (int i = 0 ; i < panel.getWidthInCharacters() ; i++) {
            Assert.assertTrue(panel.isPositionValid(1, i));
        }
    }
}
