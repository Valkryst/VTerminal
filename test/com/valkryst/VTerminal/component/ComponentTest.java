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

    @Test
    public void testIntersects_withComponent() {
        final Component componentA = new Component(0, 0, width, height);
        final Component componentB = new Component(0, 0, width, height);

        Assert.assertTrue(componentA.intersects(componentB));
    }

    @Test
    public void testIntersects_withComponent_withSelf() {
        final Component component = new Component(0, 0, width, height);
        Assert.assertTrue(component.intersects(component));
    }

    @Test
    public void testIntersects_withComponent_withNullParam() {
        final Component component = new Component(0, 0, width, height);

        Assert.assertFalse(component.intersects(null));
    }

    @Test
    public void testIntersects_withCoords_withIntersectingCoords() {
        final Component component = new Component(0, 0, width, height);

        for (int x = 0 ; x < width ; x++) {
            for (int y = 0; y < height; y++) {
                Assert.assertTrue(component.intersects(x, y));
            }
        }
    }

    @Test
    public void testIntersects_withCoords_withNonIntersectingCoords() {
        final Component component = new Component(0, 0, width, height);
        Assert.assertFalse(component.intersects(-1, 0));
        Assert.assertFalse(component.intersects(0, -1));
        Assert.assertFalse(component.intersects(-1, -1));
        Assert.assertFalse(component.intersects(width + 1, 0));
        Assert.assertFalse(component.intersects(0, height + 1));
        Assert.assertFalse(component.intersects(width + 1, height + 1));
    }

    @Test
    public void testIsPositionValid() {
        final Component component = new Component(0, 0, width, height);

        for (int x = 0 ; x < width ; x++) {
            for (int y = 0 ; y < height ; y++) {
                Assert.assertTrue(component.isPositionValid(x, y));
            }
        }
    }

    @Test
    public void testIsPositionValid_withInvalidPositions() {
        final Component component = new Component(0, 0, width, height);

        Assert.assertFalse(component.isPositionValid(-1, -1));
        Assert.assertFalse(component.isPositionValid(-1, 0));
        Assert.assertFalse(component.isPositionValid(0, -1));

        Assert.assertFalse(component.isPositionValid(width + 1, height + 1));
        Assert.assertFalse(component.isPositionValid(width, height + 1));
        Assert.assertFalse(component.isPositionValid(width + 1, height));
    }

    @Test
    public void testSetColumnIndex() {
        final Component component = new Component(0, 0, width, height);
        component.setColumnIndex(1);

        Assert.assertEquals(1, component.getColumnIndex());
    }

    @Test
    public void testSetColumnIndex_withNegativeIndex() {
        final Component component = new Component(0, 0, width, height);
        component.setColumnIndex(-1);

        Assert.assertEquals(0, component.getColumnIndex());
    }

    @Test
    public void testSetRowIndex() {
        final Component component = new Component(0, 0, width, height);
        component.setRowIndex(1);

        Assert.assertEquals(1, component.getRowIndex());
    }

    @Test
    public void testSetRowIndex_withNegativeIndex() {
        final Component component = new Component(0, 0, width, height);
        component.setRowIndex(-1);

        Assert.assertEquals(0, component.getRowIndex());
    }
}
