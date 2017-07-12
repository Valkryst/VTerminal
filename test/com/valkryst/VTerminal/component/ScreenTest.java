package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.util.Optional;

public class ScreenTest {
    private Screen screen;
    private final AsciiCharacter character = new AsciiCharacter('?');
    private final AsciiString string = new AsciiString("?????");

    @Before
    public void initalizeScreen() {
        screen = new Screen(0, 0, 5, 5);
    }

    @Test
    public void testConstructor_withValidParams() {
        final Screen screen = new Screen(4, 6, 9, 10);
        Assert.assertEquals(4, screen.getColumnIndex());
        Assert.assertEquals(6, screen.getRowIndex());
        Assert.assertEquals(9, screen.getWidth());
        Assert.assertEquals(10, screen.getHeight());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConstructor_withNegativeColumnIndex() {
        new Screen(-1, 6, 9, 10);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConstructor_withNegativeRowIndex() {
        new Screen(4, -1, 9, 10);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConstructor_withNegativeWidth() {
        new Screen(4, 6, -1, 10);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testConstructor_withNegativeHeight() {
        new Screen(4, 6, 9, -1);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testDraw_screen() {
        screen.draw(screen);
    }

    @Test
    public void testClear_oneParam() {
        screen.clear('?');

        for (final AsciiString string : screen.getStrings()) {
            for (final AsciiCharacter character : string.getCharacters()) {
                Assert.assertEquals('?', character.getCharacter());
            }
        }
    }

    @Test
    public void testClear_multipleParams_withValidParams() {
        screen.clear('?', 2, 2, 2, 2);

        for (int y = 0 ; y < screen.getStrings().length ; y++) {
            for (int x = 0 ; x < screen.getStrings()[y].getCharacters().length ; x++) {
                final Optional<AsciiCharacter> optChar = screen.getCharacterAt(x, y);
                Assert.assertTrue(optChar.isPresent());


                if (x == 2 || x == 3) {
                    if (y == 2 || y == 3) {
                        Assert.assertEquals('?', optChar.get().getCharacter());
                        continue;
                    }
                }

                Assert.assertNotEquals('?', optChar.get().getCharacter());
            }
        }
    }

    @Test
    public void testClear_multipleParams_withNegativeColumnIndex() {
        screen.clear('?', -1, 2, 2, 2);

        for (int y = 0 ; y < screen.getStrings().length ; y++) {
            for (int x = 0 ; x < screen.getStrings()[y].getCharacters().length ; x++) {
                final Optional<AsciiCharacter> optChar = screen.getCharacterAt(x, y);
                Assert.assertTrue(optChar.isPresent());
                Assert.assertNotEquals('?', optChar.get().getCharacter());
            }
        }
    }

    @Test
    public void testClear_multipleParams_withNegativeRowIndex() {
        screen.clear('?', 2, -1, 2, 2);

        for (int y = 0 ; y < screen.getStrings().length ; y++) {
            for (int x = 0 ; x < screen.getStrings()[y].getCharacters().length ; x++) {
                final Optional<AsciiCharacter> optChar = screen.getCharacterAt(x, y);
                Assert.assertTrue(optChar.isPresent());
                Assert.assertNotEquals('?', optChar.get().getCharacter());
            }
        }
    }

    @Test
    public void testClear_multipleParams_withNegativeWidth() {
        screen.clear('?', 2, 2, -1, 2);

        for (int y = 0 ; y < screen.getStrings().length ; y++) {
            for (int x = 0 ; x < screen.getStrings()[y].getCharacters().length ; x++) {
                final Optional<AsciiCharacter> optChar = screen.getCharacterAt(x, y);
                Assert.assertTrue(optChar.isPresent());
                Assert.assertNotEquals('?', optChar.get().getCharacter());
            }
        }
    }

    @Test
    public void testClear_multipleParams_withNegativeHeight() {
        screen.clear('?', 2, 2, 2, -1);

        for (int y = 0 ; y < screen.getStrings().length ; y++) {
            for (int x = 0 ; x < screen.getStrings()[y].getCharacters().length ; x++) {
                final Optional<AsciiCharacter> optChar = screen.getCharacterAt(x, y);
                Assert.assertTrue(optChar.isPresent());
                Assert.assertNotEquals('?', optChar.get().getCharacter());
            }
        }
    }

    @Test
    public void testWrite_charObj_withValidParams() {
        screen.write(character, 3, 3);

        final Optional<AsciiCharacter> optChar = screen.getCharacterAt(3, 3);
        Assert.assertTrue(optChar.isPresent());
        Assert.assertEquals('?', optChar.get().getCharacter());
    }

    @Test
    public void testWrite_charObj_allCharPositions() {
        for (int y = 0 ; y < screen.getHeight() ; y++) {
            for (int x = 0 ; x < screen.getWidth() ; x++) {
                screen.write(character, x, y);
            }
        }

        for (final AsciiString string : screen.getStrings()) {
            for (final AsciiCharacter chr : string.getCharacters()) {
                Assert.assertEquals('?', chr.getCharacter());
            }
        }
    }

    @Test
    public void testWrite_charObj_withNullCharacter() {
        screen.write((AsciiCharacter) null, 3, 3);

        final Optional<AsciiCharacter> optChar = screen.getCharacterAt(3, 3);
        Assert.assertTrue(optChar.isPresent());
        Assert.assertNotEquals('?', optChar.get().getCharacter());
    }

    @Test
    public void testWrite_charObj_withNegativeColumnIndex() {
        screen.write(character, -3, 3);

        final Optional<AsciiCharacter> optChar = screen.getCharacterAt(3, 3);
        Assert.assertTrue(optChar.isPresent());
        Assert.assertNotEquals('?', optChar.get().getCharacter());
    }

    @Test
    public void testWrite_charObj_withNegativeRowIndex() {
        screen.write(character, 3, -3);

        final Optional<AsciiCharacter> optChar = screen.getCharacterAt(3, 3);
        Assert.assertTrue(optChar.isPresent());
        Assert.assertNotEquals('?', optChar.get().getCharacter());
    }

    @Test
    public void testWrite_charPrim_withValidParams() {
        screen.write('?', 3, 3);

        final Optional<AsciiCharacter> optChar = screen.getCharacterAt(3, 3);
        Assert.assertTrue(optChar.isPresent());
        Assert.assertEquals('?', optChar.get().getCharacter());
    }

    @Test
    public void testWrite_charPrim_allCharPositions() {
        for (int y = 0 ; y < screen.getHeight() ; y++) {
            for (int x = 0 ; x < screen.getWidth() ; x++) {
                screen.write('?', x, y);
            }
        }

        for (final AsciiString string : screen.getStrings()) {
            for (final AsciiCharacter character : string.getCharacters()) {
                Assert.assertEquals('?', character.getCharacter());
            }
        }
    }

    @Test
    public void testWrite_charPrim_withNegativeColumnIndex() {
        screen.write('?', -3, 3);

        final Optional<AsciiCharacter> optChar = screen.getCharacterAt(3, 3);
        Assert.assertTrue(optChar.isPresent());
        Assert.assertNotEquals('?', optChar.get().getCharacter());
    }

    @Test
    public void testWrite_charPrim_withNegativeRowIndex() {
        screen.write('?', 3, -3);

        final Optional<AsciiCharacter> optChar = screen.getCharacterAt(3, 3);
        Assert.assertTrue(optChar.isPresent());
        Assert.assertNotEquals('?', optChar.get().getCharacter());
    }

    @Test
    public void testWrite_stringObj_withValidParams() {
        screen.write(string, 0, 0);
        string.setAllCharactersToBeRedrawn();
        Assert.assertEquals(string, screen.getStrings()[0]);
    }

    @Test
    public void testWrite_stringObj_allCharPositions() {
        for (int y = 0 ; y < screen.getHeight() ; y++) {
            screen.write(string, 0, y);
            string.setAllCharactersToBeRedrawn();
            Assert.assertEquals(string, screen.getStrings()[y]);
        }
    }

    @Test
    public void testWrite_stringObj_withNullString() {
        screen.write((AsciiString) null, 0, 0);
        Assert.assertNotEquals(string, screen.getStrings()[0]);
    }

    @Test
    public void testWrite_stringObj_withNegativeColumnIndex() {
        screen.write(string, -3, 3);
        Assert.assertNotEquals(string, screen.getStrings()[0]);
    }

    @Test
    public void testWrite_stringObj_withNegativeRowIndex() {
        screen.write(string, 3, -3);
        Assert.assertNotEquals(string, screen.getStrings()[0]);
    }

    @Test
    public void testSetBackgroundColor_withValidColor() {
        screen.setBackgroundColor(Color.PINK);

        for (final AsciiString string : screen.getStrings()) {
            for (final AsciiCharacter character : string.getCharacters()) {
                Assert.assertEquals(Color.PINK, character.getBackgroundColor());
            }
        }
    }

    @Test
    public void testSetBackgroundColor_withNullColor() {
        screen.setBackgroundColor(null);

        for (final AsciiString string : screen.getStrings()) {
            for (final AsciiCharacter character : string.getCharacters()) {
                Assert.assertNotEquals(Color.PINK, character.getBackgroundColor());
            }
        }
    }

    @Test
    public void testSetForegroundColor_withValidColor() {
        screen.setForegroundColor(Color.PINK);

        for (final AsciiString string : screen.getStrings()) {
            for (final AsciiCharacter character : string.getCharacters()) {
                Assert.assertEquals(Color.PINK, character.getForegroundColor());
            }
        }
    }

    @Test
    public void testSetForegroundColor_withNullColor() {
        screen.setForegroundColor(null);

        for (final AsciiString string : screen.getStrings()) {
            for (final AsciiCharacter character : string.getCharacters()) {
                Assert.assertNotEquals(Color.PINK, character.getForegroundColor());
            }
        }
    }

    @Test
    public void testSetBackgroundAndForegroundColor_withValidColors() {
        screen.setBackgroundAndForegroundColor(Color.PINK, Color.GREEN);

        for (final AsciiString string : screen.getStrings()) {
            for (final AsciiCharacter character : string.getCharacters()) {
                Assert.assertEquals(Color.PINK, character.getBackgroundColor());
                Assert.assertEquals(Color.GREEN, character.getForegroundColor());
            }
        }
    }

    @Test
    public void testSetBackgroundAndForegroundColor_withNullBackgroundColor() {
        screen.setBackgroundAndForegroundColor(null, Color.GREEN);

        for (final AsciiString string : screen.getStrings()) {
            for (final AsciiCharacter character : string.getCharacters()) {
                Assert.assertNotEquals(Color.PINK, character.getBackgroundColor());
                Assert.assertNotEquals(Color.GREEN, character.getForegroundColor());
            }
        }
    }

    @Test
    public void testSetBackgroundAndForegroundColor_withNullForegroundColor() {
        screen.setBackgroundAndForegroundColor(Color.PINK, null);

        for (final AsciiString string : screen.getStrings()) {
            for (final AsciiCharacter character : string.getCharacters()) {
                Assert.assertNotEquals(Color.PINK, character.getBackgroundColor());
                Assert.assertNotEquals(Color.GREEN, character.getForegroundColor());
            }
        }
    }

    @Test
    public void testAddComponent_withNullComponent() {
        screen.addComponent(null);
        Assert.assertEquals(0, screen.totalComponents());
    }

    @Test
    public void testAddComponent_withSelf() {
        screen.addComponent(screen);
        Assert.assertEquals(0, screen.totalComponents());
    }

    @Test
    public void testAddComponent_withLayer() {
        final Layer otherComponent = new Layer(0, 0, 2, 2);
        screen.addComponent(otherComponent);

        Assert.assertTrue(screen.containsComponent(otherComponent));
    }

    @Test
    public void testAddComponent_withScreen() {
        final Screen otherComponent = new Screen(0, 0, 2, 2);
        screen.addComponent(otherComponent);

        Assert.assertTrue(screen.containsComponent(otherComponent));
    }

    @Test
    public void testAddComponent_withComponent() {
        final Component otherComponent = new Component(0, 0, 2, 2);
        screen.addComponent(otherComponent);

        Assert.assertTrue(screen.containsComponent(otherComponent));
    }

    @Test
    public void testAddComponent_withScreenContainingMainScreen() {
        final Screen otherComponent = new Screen(0, 0, 2, 2);
        otherComponent.addComponent(screen);
        screen.addComponent(otherComponent);

        Assert.assertFalse(screen.containsComponent(otherComponent));
    }

    @Test
    public void testAddComponent_addSameComponentTwice() {
        final Layer otherComponent = new Layer(0, 0, 2, 2);
        screen.addComponent(otherComponent);
        screen.addComponent(otherComponent);

        Assert.assertTrue(screen.containsComponent(otherComponent));
        Assert.assertEquals(1, screen.totalComponents());
    }

    @Test
    public void testRemoveComponent_withLayer() {
        final Layer otherComponent = new Layer(0, 0, 2, 2);

        screen.addComponent(otherComponent);
        Assert.assertTrue(screen.containsComponent(otherComponent));
        Assert.assertEquals(1, screen.totalComponents());

        screen.removeComponent(otherComponent);
        Assert.assertFalse(screen.containsComponent(otherComponent));
        Assert.assertEquals(0, screen.totalComponents());
    }

    @Test
    public void testRemoveComponent_withScreen() {
        final Screen otherComponent = new Screen(0, 0, 2, 2);

        screen.addComponent(otherComponent);
        Assert.assertTrue(screen.containsComponent(otherComponent));
        Assert.assertEquals(1, screen.totalComponents());

        screen.removeComponent(otherComponent);
        Assert.assertFalse(screen.containsComponent(otherComponent));
        Assert.assertEquals(0, screen.totalComponents());
    }

    @Test
    public void testRemoveComponent_withComponent() {
        final Component otherComponent = new Component(0, 0, 2, 2);

        screen.addComponent(otherComponent);
        Assert.assertTrue(screen.containsComponent(otherComponent));
        Assert.assertEquals(1, screen.totalComponents());

        screen.removeComponent(otherComponent);
        Assert.assertFalse(screen.containsComponent(otherComponent));
        Assert.assertEquals(0, screen.totalComponents());
    }

    @Test
    public void testRemoveComponent_withNullComponent() {
        final Layer otherComponent = new Layer(0, 0, 2, 2);

        screen.addComponent(otherComponent);
        Assert.assertTrue(screen.containsComponent(otherComponent));
        Assert.assertEquals(1, screen.totalComponents());

        screen.removeComponent(null);
        Assert.assertTrue(screen.containsComponent(otherComponent));
        Assert.assertEquals(1, screen.totalComponents());
    }

    @Test
    public void testRemoveComponent_withSelf() {
        final Layer otherComponent = new Layer(0, 0, 2, 2);

        screen.addComponent(otherComponent);
        Assert.assertTrue(screen.containsComponent(otherComponent));
        Assert.assertEquals(1, screen.totalComponents());

        screen.removeComponent(screen);
        Assert.assertTrue(screen.containsComponent(otherComponent));
        Assert.assertEquals(1, screen.totalComponents());
    }

    @Test
    public void testContainsComponent_withLayer() {
        final Layer otherComponent = new Layer(0, 0, 2, 2);
        screen.addComponent(otherComponent);
        Assert.assertTrue(screen.containsComponent(otherComponent));
    }

    @Test
    public void testContainsComponent_withScreen() {
        final Screen otherComponent = new Screen(0, 0, 2, 2);
        screen.addComponent(otherComponent);
        Assert.assertTrue(screen.containsComponent(otherComponent));
    }

    @Test
    public void testContainsComponent_withComponent() {
        final Component otherComponent = new Component(0, 0, 2, 2);
        screen.addComponent(otherComponent);
        Assert.assertTrue(screen.containsComponent(otherComponent));
    }

    @Test
    public void testContainsComponent_withComponentThatIsntContained() {
        final Layer otherComponent = new Layer(0, 0, 2, 2);
        Assert.assertFalse(screen.containsComponent(otherComponent));
    }

    @Test
    public void testContainsComponent_withNullComponent() {
        Assert.assertFalse(screen.containsComponent(null));
    }

    @Test
    public void testContainsComponent_withSelf() {
        Assert.assertFalse(screen.containsComponent(screen));
    }

    @Test
    public void testRecursiveContainsComponent_withNullComponent() {
        Assert.assertFalse(screen.recursiveContainsComponent(null));
    }

    @Test
    public void testRecursiveContainsComponent_withSelf() {
        Assert.assertFalse(screen.recursiveContainsComponent(screen));
    }

    @Test
    public void testRecursiveContainsComponent_whereSelfContainsComponent() {
        final Component otherComponent = new Component(0, 0, 2, 2);
        screen.addComponent(otherComponent);
        Assert.assertTrue(screen.recursiveContainsComponent(otherComponent));
    }

    @Test
    public void testRecursiveContainsComponent_whereOtherComponentIsScreenThatContainsCallingScreen() {
        final Screen otherComponent = new Screen(0, 0, 2, 2);
        otherComponent.addComponent(screen);
        screen.addComponent(otherComponent);
        Assert.assertTrue(screen.recursiveContainsComponent(otherComponent));
    }

    @Test
    public void testRecursiveContainsComponent_whereSubscreenContainsComponent() {
        final Component otherComponent = new Component(0, 0, 2, 2);
        final Screen otherScreen = new Screen(0, 0, 2, 2);
        otherScreen.addComponent(otherComponent);

        screen.addComponent(otherScreen);

        Assert.assertTrue(screen.recursiveContainsComponent(otherComponent));
    }

    @Test
    public void testRecursiveContainsComponent_withNonContainedComponent() {
        final Component otherComponent = new Component(0, 0, 2, 2);
        Assert.assertFalse(screen.recursiveContainsComponent(otherComponent));
    }

    @Test
    public void testTotalComponents() {
        for (int i = 1 ; i < 10 ; i++) {
            final Component component = new Component(0, 0, 2, 2);
            screen.addComponent(component);
            Assert.assertEquals(i, screen.totalComponents());
        }
    }
}
