package com.valkryst.VTerminal;

import com.valkryst.VRadio.Radio;
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
        Assert.assertTrue(character.toString().length() > 0);
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
    public void testEquals_withNonAsciiCharacterObject() {
        final AsciiCharacter character = new AsciiCharacter('A');
        final Integer integer = 666;

        Assert.assertFalse(character.equals(integer));
    }

    @Test
    public void testEquals_withNonEqualBackgroundColors() {
        final AsciiCharacter characterA = new AsciiCharacter('A');
        final AsciiCharacter characterB = new AsciiCharacter('B');

        characterA.setBackgroundColor(Color.BLUE);
        characterB.setBackgroundColor(Color.RED);

        Assert.assertFalse(characterA.equals(characterB));
    }

    @Test
    public void testEquals_withNonEqualForegroundColors() {
        final AsciiCharacter characterA = new AsciiCharacter('A');
        final AsciiCharacter characterB = new AsciiCharacter('B');

        characterA.setForegroundColor(Color.BLUE);
        characterB.setForegroundColor(Color.RED);

        Assert.assertFalse(characterA.equals(characterB));
    }

    @Test
    public void testHashCode_withEqualCharacters() {
        final AsciiCharacter characterA = new AsciiCharacter('A');
        final AsciiCharacter characterB = new AsciiCharacter('A');

        Assert.assertEquals(characterA.hashCode(), characterB.hashCode());
    }

    @Test
    public void testHashCode_withNonEqualCharacters() {
        final AsciiCharacter characterA = new AsciiCharacter('A');
        final AsciiCharacter characterB = new AsciiCharacter('B');

        Assert.assertNotEquals(characterA.hashCode(), characterB.hashCode());
    }

    @Test
    public void testEnableBlinkEffect() {
        final AsciiCharacter character = new AsciiCharacter('A');
        character.enableBlinkEffect((short) 666, new Radio<>());

        Assert.assertEquals(666, character.getMillsBetweenBlinks());
    }

    @Test(expected=NullPointerException.class)
    public void testEnableBlinkEffect_withNullRadio() {
        final AsciiCharacter character = new AsciiCharacter('A');
        character.enableBlinkEffect((short) 1000, null);
    }

    @Test
    public void testEnableBlinkEffect_withMillsBetweenBlinksEqualToZero() {
        final AsciiCharacter character = new AsciiCharacter('A');
        character.enableBlinkEffect((short) 0, new Radio<>());

        Assert.assertEquals(1000, character.getMillsBetweenBlinks());
    }

    @Test
    public void testEnableBlinkEffect_withMillsBetweenBlinksLessThanZero() {
        final AsciiCharacter character = new AsciiCharacter('A');
        character.enableBlinkEffect((short) -1, new Radio<>());

        Assert.assertEquals(1000, character.getMillsBetweenBlinks());
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

    @Test(expected=NullPointerException.class)
    public void testSetBackgroundColor_withNullColor() {
        final AsciiCharacter characterA = new AsciiCharacter('A');
        characterA.setBackgroundColor(Color.RED);
        characterA.setBackgroundColor(null);
    }

    @Test
    public void testSetForegroundColor() {
        final AsciiCharacter characterA = new AsciiCharacter('A');
        characterA.setForegroundColor(Color.RED);

        Assert.assertEquals(characterA.getForegroundColor(), Color.RED);
    }

    @Test(expected=NullPointerException.class)
    public void testSetForegroundColor_withNullColor() {
        final AsciiCharacter characterA = new AsciiCharacter('A');
        characterA.setForegroundColor(Color.RED);
        characterA.setForegroundColor(null);
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
