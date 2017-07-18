package com.valkryst.VTerminal.component;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Optional;

@RunWith(JUnitQuickcheck.class)
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

    @Property
    public void testConstructor_withValidColumn(@InRange(minInt=0,maxInt=10000) final int column) {
        new Component(column, 0, width, height);
    }

    @Property
    public void testConstructor_withInvalidColumn(@InRange(minInt=-10000,maxInt=-1) final int column) {
        try {
            new Component(column, 0, width, height);
            Assert.fail();
        } catch (final IllegalArgumentException e) {}
    }

    @Property
    public void testConstructor_withValidRow(@InRange(minInt=0,maxInt=10000) final int row) {
        new Component(0, row, width, height);
    }

    @Property
    public void testConstructor_withInvalidRow(@InRange(minInt=-10000,maxInt=-1) final int row) {
        try{
            new Component(0, row, width, height);
            Assert.fail();
        } catch (final IllegalArgumentException e) {}
    }

    @Property
    public void testConstructor_withValidWidth(@InRange(minInt=1,maxInt=10000) final int width) {
        new Component(0, 0, width, height);
    }

    @Property
    public void testConstructor_withInvalidWidth(@InRange(minInt=-10000,maxInt=0) final int width) {
        try {
            new Component(0, 0, width, height);
            Assert.fail();
        } catch (final IllegalArgumentException e) {}
    }

    @Property
    public void testConstructor_withValidHeight(@InRange(minInt=1,maxInt=10000) final int height) {
        new Component(0, 0, width, height);
    }

    @Property
    public void testConstructor_withInvalidHeight(@InRange(minInt=-10000,maxInt=0) final int height) {
        try {
            new Component(0, 0, width, height);
            Assert.fail();
        } catch (final IllegalArgumentException e) {}
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

    @Test(expected=NullPointerException.class)
    public void testIntersects_withComponent_withNullParam() {
        final Component component = new Component(0, 0, width, height);
        component.intersects(null);
    }

    @Test
    public void testIntersects_withCoords_withIntersectingCoords() {
        final Component component = new Component(0, 0, width, height);

        for (int x = 0 ; x < width - 1 ; x++) {
            for (int y = 0; y < height - 1 ; y++) {
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

    @Property
    public void testGetCharacterAt_withValidPosition(final char character) {
        final Component component = new Component(0, 0, width, height);
        component.getString(5).getCharacters()[6].setCharacter(character);

        final Optional<AsciiCharacter> res = component.getCharacterAt(6, 5);

        Assert.assertTrue(res.isPresent());
        Assert.assertEquals(character, res.get().getCharacter());
    }

    @Test
    public void testGetCharacterAt_withInvalidPosition() {
        final Component component = new Component(0, 0, width, height);

        final Optional<AsciiCharacter> res = component.getCharacterAt(50, 50);

        Assert.assertFalse(res.isPresent());
    }

    @Property
    public void testSetColumnIndex_withNegativeIndex(@InRange(minInt=-10000,maxInt=-1) final int column) {
        final Component component = new Component(0, 0, width, height);
        component.setColumnIndex(column);

        Assert.assertEquals(0, component.getColumnIndex());
    }

    @Property
    public void testSetRowIndex_withNegativeIndex(@InRange(minInt=-10000,maxInt=-1) final int row) {
        final Component component = new Component(0, 0, width, height);
        component.setRowIndex(-1);

        Assert.assertEquals(0, component.getRowIndex());
    }

    @Property
    public void testSetWidth_withNegativeWidth(@InRange(minInt=-10000,maxInt=-1) final int width) {
        try {
            final Component component = new Component(0, 0, width, height);
            component.setWidth(this.width);
            Assert.fail();
        } catch(final IllegalArgumentException e) {}
    }

    @Test
    public void testSetWidth_withWidthLessThanColumnIndex() {
        final Component component = new Component(0, 0, width, height);
        component.setWidth(component.getColumnIndex() - 1);

        Assert.assertEquals(width, component.getWidth());
    }

    @Property
    public void testSetHeight_withNegativeHeight(@InRange(minInt=-10000,maxInt=-1) final int height) {
        try {
            final Component component = new Component(0, 0, width, height);
            component.setHeight(this.height);
            Assert.fail();
        } catch(final IllegalArgumentException e) {}
    }

    @Test
    public void testSetHeight_withHeightLessThanRowIndex() {
        final Component component = new Component(0, 0, width, height);
        component.setHeight(component.getRowIndex() - 1);

        Assert.assertEquals(height, component.getHeight());
    }
}
