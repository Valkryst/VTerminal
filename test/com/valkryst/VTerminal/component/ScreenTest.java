package com.valkryst.VTerminal.component;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.builder.component.ScreenBuilder;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.misc.ImageCache;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Optional;

public class ScreenTest {
    private final Font font;
    private Screen screen;
    private final AsciiCharacter character = new AsciiCharacter('?');
    private final AsciiString string = new AsciiString("?????");

    public ScreenTest() throws IOException, URISyntaxException {
        font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt", 1);
    }

    @Before
    public void initializeScreen() {
        final ScreenBuilder screenBuilder = new ScreenBuilder();
        screenBuilder.setColumnIndex(0);
        screenBuilder.setRowIndex(0);
        screenBuilder.setWidth(5);
        screenBuilder.setHeight(5);
        screenBuilder.setRadio(new Radio<>());
        screen = screenBuilder.build();
    }

    @Test
    public void testConstructor_withValidParams() {
        final ScreenBuilder screenBuilder = new ScreenBuilder();
        screenBuilder.setColumnIndex(4);
        screenBuilder.setRowIndex(6);
        screenBuilder.setWidth(9);
        screenBuilder.setHeight(10);
        screenBuilder.setRadio(new Radio<>());

        final Screen screen = screenBuilder.build();
        Assert.assertEquals(4, screen.getColumnIndex());
        Assert.assertEquals(6, screen.getRowIndex());
        Assert.assertEquals(9, screen.getWidth());
        Assert.assertEquals(10, screen.getHeight());
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testDraw_withScreen() {
        screen.draw(screen);
    }

    @Test(expected=NullPointerException.class)
    public void testDraw_twoParams_withNullGraphicsContext() {
        screen.draw(null, new ImageCache(font));
    }

    @Test(expected=NullPointerException.class)
    public void testDraw_twoParams_withNullImageCache() {
        final BufferedImage image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
        screen.draw((Graphics2D) image.getGraphics(), null);
    }

    @Test
    public void testDraw_twoParams_withValidInputs() {
        final ImageCache cache = new ImageCache(font);

        final int width = font.getWidth() * screen.getWidth();
        final int height = font.getHeight() * screen.getHeight();
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        screen.draw((Graphics2D) image.getGraphics(), cache);
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
            for (int x = 0 ; x < screen.getString(y).length() ; x++) {
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
            for (int x = 0 ; x < screen.getString(y).length() ; x++) {
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
            for (int x = 0 ; x < screen.getString(y).length() ; x++) {
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
            for (int x = 0 ; x < screen.getString(y).length() ; x++) {
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
            for (int x = 0 ; x < screen.getString(y).length() ; x++) {
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

    @Test(expected=NullPointerException.class)
    public void testWrite_charObj_withNullCharacter() {
        screen.write((AsciiCharacter) null, 3, 3);
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

    @Test(expected=NullPointerException.class)
    public void testWrite_stringObj_withNullString() {
        screen.write((AsciiString) null, 0, 0);
    }

    @Test
    public void testWrite_stringObj_withNegativeColumnIndex() {
        screen.write(string, -3, 3);
        Assert.assertNotEquals(string, screen.getString(0));
    }

    @Test
    public void testWrite_stringObj_withNegativeRowIndex() {
        screen.write(string, 3, -3);
        Assert.assertNotEquals(string, screen.getString(0));
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

    @Test(expected=NullPointerException.class)
    public void testSetBackgroundColor_withNullColor() {
        screen.setBackgroundColor(null);
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

    @Test(expected=NullPointerException.class)
    public void testSetForegroundColor_withNullColor() {
        screen.setForegroundColor(null);
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

    @Test(expected=NullPointerException.class)
    public void testSetBackgroundAndForegroundColor_withNullBackgroundColor() {
        screen.setBackgroundAndForegroundColor(null, Color.GREEN);
    }

    @Test(expected=NullPointerException.class)
    public void testSetBackgroundAndForegroundColor_withNullForegroundColor() {
        screen.setBackgroundAndForegroundColor(Color.PINK, null);
    }

    @Test(expected=NullPointerException.class)
    public void testAddComponent_withNullComponent() {
        screen.addComponent(null);
        Assert.assertEquals(0, screen.totalComponents());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testAddComponent_withSelf() {
        screen.addComponent(screen);
    }

    @Test
    public void testAddComponent_withLayer() {
        final Layer otherComponent = new Layer(0, 0, 2, 2);
        screen.addComponent(otherComponent);
        Assert.assertEquals(1, screen.totalComponents());
    }

    @Test
    public void testAddComponent_withComponent() {
        final Component otherComponent = new Component(0, 0, 2, 2);
        screen.addComponent(otherComponent);

        Assert.assertTrue(screen.containsComponent(otherComponent));
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
    public void testRemoveComponent_withComponent() {
        final Component otherComponent = new Component(0, 0, 2, 2);

        screen.addComponent(otherComponent);
        Assert.assertTrue(screen.containsComponent(otherComponent));
        Assert.assertEquals(1, screen.totalComponents());

        screen.removeComponent(otherComponent);
        Assert.assertFalse(screen.containsComponent(otherComponent));
        Assert.assertEquals(0, screen.totalComponents());
    }

    @Test(expected=NullPointerException.class)
    public void testRemoveComponent_withNullComponent() {
        screen.removeComponent(null);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testRemoveComponent_withSelf() {
        screen.removeComponent(screen);
    }

    @Test
    public void testContainsComponent_withLayer() {
        final Layer otherComponent = new Layer(0, 0, 2, 2);
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

    @Test(expected=NullPointerException.class)
    public void testContainsComponent_withNullComponent() {
        screen.containsComponent(null);
    }

    @Test
    public void testContainsComponent_withSelf() {
        Assert.assertFalse(screen.containsComponent(screen));
    }

    @Test(expected=NullPointerException.class)
    public void testRecursiveContainsComponent_withNullComponent() {
        screen.recursiveContainsComponent(null);
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
