package com.valkryst.VTerminal;

import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

public class AsciiCharacterTest {
    @Test
    public void testConstructor_withAllAsciiCharacters() {
        for (byte i = 0 ; i < Byte.MAX_VALUE ; i++) {
            new AsciiCharacter((char) i);
        }
    }

    @Test
    public void testToString() {
        final AsciiCharacter character = new AsciiCharacter('A');
        final String actual_toString = character.toString();
        final String expected_toString = "Character:\n" +
                                         "\tCharacter: 'A'\n" +
                                         "\tBackground Color: " + character.getBackgroundColor() + "\n" +
                                         "\tForeground Color: " + character.getForegroundColor() + "\n";

        Assert.assertEquals(expected_toString, actual_toString);
    }

    @Test
    public void testEquals_withSelf() {
        final AsciiCharacter character = new AsciiCharacter('A');

        Assert.assertTrue(character.equals(character));
    }

    @Test
    public void testEquals_withEqualCharacters() {
        final AsciiCharacter characterA = new AsciiCharacter('A');
        final AsciiCharacter characterB = new AsciiCharacter('A');

        Assert.assertTrue(characterA.equals(characterB));
    }

    @Test
    public void testEquals_withNonEqualCharacters() {
        final AsciiCharacter characterA = new AsciiCharacter('A');
        final AsciiCharacter characterB = new AsciiCharacter('B');

        Assert.assertFalse(characterA.equals(characterB));
    }

    @Test
    public void testInvertColors() {
        final AsciiCharacter characterA = new AsciiCharacter('A');
        characterA.setBackgroundColor(Color.BLACK);
        characterA.setForegroundColor(Color.WHITE);

        characterA.invertColors();

        Assert.assertEquals(characterA.getBackgroundColor(), Color.WHITE);
        Assert.assertEquals(characterA.getForegroundColor(), Color.BLACK);
    }

    @Test
    public void testSetBackgroundColor() {
        final AsciiCharacter characterA = new AsciiCharacter('A');
        characterA.setBackgroundColor(Color.RED);

        Assert.assertEquals(characterA.getBackgroundColor(), Color.RED);
    }

    @Test
    public void testSetBackgroundColor_withNullColor() {
        final AsciiCharacter characterA = new AsciiCharacter('A');
        characterA.setBackgroundColor(Color.RED);
        characterA.setBackgroundColor(null);

        Assert.assertEquals(characterA.getBackgroundColor(), Color.RED);
    }

    @Test
    public void testSetForegroundColor() {
        final AsciiCharacter characterA = new AsciiCharacter('A');
        characterA.setForegroundColor(Color.RED);

        Assert.assertEquals(characterA.getForegroundColor(), Color.RED);
    }

    @Test
    public void testSetForegroundColor_withNullColor() {
        final AsciiCharacter characterA = new AsciiCharacter('A');
        characterA.setForegroundColor(Color.RED);
        characterA.setForegroundColor(null);

        Assert.assertEquals(characterA.getForegroundColor(), Color.RED);
    }

    @Test
    public void testSetUnderlineThickness() {
        final AsciiCharacter character = new AsciiCharacter('A');
        character.getBoundingBox().setSize(2, 7);

        character.setUnderlineThickness(6);
        Assert.assertEquals(6, character.getUnderlineThickness());
    }

    @Test
    public void testSetUnderlineThickness_withThicknessGreaterThanBoundingBoxHeight() {
        final AsciiCharacter character = new AsciiCharacter('A');
        character.getBoundingBox().setSize(2, 2);

        int originalThickness = character.getUnderlineThickness();

        character.setUnderlineThickness((int) character.getBoundingBox().getHeight() + 1);
        Assert.assertEquals(originalThickness, character.getUnderlineThickness());
    }

    @Test
    public void testSetUnderlineThickness_withThicknessEqualToZero() {
        final AsciiCharacter character = new AsciiCharacter('A');
        character.getBoundingBox().setSize(2, 2);

        character.setUnderlineThickness(0);
        Assert.assertEquals(1, character.getUnderlineThickness());
    }

    @Test
    public void testSetUnderlineThickness_withThicknessLessThanZero() {
        final AsciiCharacter character = new AsciiCharacter('A');
        character.getBoundingBox().setSize(2, 2);

        character.setUnderlineThickness(-1);
        Assert.assertEquals(1, character.getUnderlineThickness());
    }
}
