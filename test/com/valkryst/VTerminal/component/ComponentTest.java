package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.AsciiString;
import org.junit.Assert;
import org.junit.Test;

public class ComponentTest {
    private final int width = 48;
    private final int height = 48;

    @Test
    public void testConstructor() {
        final Component component = new Component(0, 0, width, height);

        // Ensure component is at correct position with correct width/height:
        Assert.assertEquals(0, component.getColumnIndex());
        Assert.assertEquals(0, component.getRowIndex());
        Assert.assertEquals(width, component.getWidth());
        Assert.assertEquals(height, component.getHeight());

        // Ensure component's bounding box is at correct position with correct width/height:
        Assert.assertEquals(0, component.getBoundingBox().getLocation().getX(), 0.001);
        Assert.assertEquals(0, component.getBoundingBox().getLocation().getY(), 0.001);
        Assert.assertEquals(width, component.getBoundingBox().getBounds().getWidth(), 0.001);
        Assert.assertEquals(height, component.getBoundingBox().getBounds().getHeight(), 0.001);

        // Ensure component has enough strings and that strings are of the correct width:
        Assert.assertEquals(height, component.getStrings().length);

        for (final AsciiString string : component.getStrings()) {
            Assert.assertEquals(width, string.getCharacters().length);
        }
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConstructor_withNegativeColumnIndex() {
        new Component(-1, 0, width, height);
    }

    @Test
    public void testConstructor_withZeroColumnIndex() {
        new Component(0, 0, width, height);
    }

    @Test
    public void testConstructor_withPositiveColumnIndex() {
        new Component(1, 0, width, height);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConstructor_withNegativeRowIndex() {
        new Component(0, -1, width, height);
    }

    @Test
    public void testConstructor_withZeroRowIndex() {
        new Component(0, 0, width, height);
    }

    @Test
    public void testConstructor_withRowColumnIndex() {
        new Component(0, 1, width, height);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConstructor_withNegativeWidth() {
        new Component(0, 0, -1, height);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConstructor_withNegativeHeight() {
        new Component(0, 0, width, -1);
    }
}
