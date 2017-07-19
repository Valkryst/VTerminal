package com.valkryst.VTerminal;

import com.valkryst.VRadio.Radio;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

public class AsciiCharacterTest {
    private AsciiCharacter characterA;
    private AsciiCharacter characterB;

    @Before
    public void initializeCharacters() {
        characterA = new AsciiCharacter('A');
        characterB = new AsciiCharacter('B');
    }

    @Test
    public void testConstructor_withAllAsciiCharacters() {
        for (byte i = 0 ; i < Byte.MAX_VALUE ; i++) {
            new AsciiCharacter((char) i);
        }
    }

    @Test
    public void testToString() {
        Assert.assertTrue(characterA.toString().length() > 0);
    }

    ////////////////////////////////////// Equals Tests

    @Test
    public void testEquals_withSelf() {
        Assert.assertTrue(characterA.equals(characterA));
    }

    @Test
    public void testEquals_withEqualCharacters() {
        Assert.assertTrue(characterA.equals(characterB));
    }

    @Test
    public void testEquals_withNonEqualCharacters() {
        Assert.assertFalse(characterA.equals(characterB));
    }

    @Test
    public void testEquals_withNonAsciiCharacterObject() {
        Assert.assertFalse(characterA.equals(666));
    }

    @Test
    public void testEquals_withNonEqualBackgroundColors() {
        characterA.setBackgroundColor(Color.BLUE);
        characterB.setBackgroundColor(Color.RED);

        Assert.assertFalse(characterA.equals(characterB));
    }

    @Test
    public void testEquals_withNonEqualForegroundColors() {
        characterA.setForegroundColor(Color.BLUE);
        characterB.setForegroundColor(Color.RED);

        Assert.assertFalse(characterA.equals(characterB));
    }

    @Test
    public void testHashCode_withEqualCharacters() {
        Assert.assertEquals(characterA.hashCode(), characterB.hashCode());
    }

    @Test
    public void testHashCode_withNonEqualCharacters() {
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
    public void testResumeBlinkEffect_withPausedBlinkEffect() {
        characterA.enableBlinkEffect((short) 666, new Radio<>());
        characterA.pauseBlinkEffect();
        characterA.resumeBlinkEffect();
    }

    @Test
    public void testResumeBlinkEffect_withNonPausedBlinkEffect() {
        characterA.enableBlinkEffect((short) 666, new Radio<>());
        characterA.resumeBlinkEffect();
    }

    @Test
    public void testResumeBlinkEffect_withNullBlinkTimer() {
        characterA.resumeBlinkEffect();
    }

    @Test
    public void testPauseBlinkEffect_withPausedBlinkEffect() {
        characterA.enableBlinkEffect((short) 666, new Radio<>());
        characterA.pauseBlinkEffect();
        characterA.pauseBlinkEffect();
    }

    @Test
    public void testPauseBlinkEffect_withNonPausedBlinkEffect() {
        characterA.enableBlinkEffect((short) 666, new Radio<>());
        characterA.pauseBlinkEffect();
    }

    @Test
    public void testPauseBlinkEffect_withNullBlinkEffect() {
        characterA.pauseBlinkEffect();
    }

    @Test
    public void testDisableBlinkEffect() {
        characterA.enableBlinkEffect((short) 666, new Radio<>());
        characterA.disableBlinkEffect();
    }

    @Test
    public void testDisableBlinkEffect_withNullBlinkEffect() {
        characterA.disableBlinkEffect();
    }

    @Test
    public void testInvertColors() {
        characterA.setBackgroundColor(Color.BLACK);
        characterA.setForegroundColor(Color.WHITE);

        characterA.invertColors();

        Assert.assertEquals(characterA.getBackgroundColor(), Color.WHITE);
        Assert.assertEquals(characterA.getForegroundColor(), Color.BLACK);
    }

    @Test
    public void testTintBackgroundColor() {
        characterA.setBackgroundColor(Color.RED);
        characterA.tintBackgroundColor(1.0);
        Assert.assertEquals(Color.WHITE, characterA.getBackgroundColor());
    }

    @Test
    public void testTintForegroundColor() {
        characterA.setForegroundColor(Color.RED);
        characterA.tintForegroundColor(1.0);
        Assert.assertEquals(Color.WHITE, characterA.getForegroundColor());
    }

    @Test
    public void testSetBackgroundColor() {
        characterA.setBackgroundColor(Color.RED);
        Assert.assertEquals(characterA.getBackgroundColor(), Color.RED);
    }

    @Test(expected=NullPointerException.class)
    public void testSetBackgroundColor_withNullColor() {
        characterA.setBackgroundColor(Color.RED);
        characterA.setBackgroundColor(null);
    }

    @Test
    public void testSetForegroundColor() {
        characterA.setForegroundColor(Color.RED);
        Assert.assertEquals(characterA.getForegroundColor(), Color.RED);
    }

    @Test(expected=NullPointerException.class)
    public void testSetForegroundColor_withNullColor() {
        characterA.setForegroundColor(Color.RED);
        characterA.setForegroundColor(null);
    }

    @Test
    public void testSetUnderlineThickness() {
        characterA.getBoundingBox().setSize(2, 7);
        characterA.setUnderlineThickness(6);
        Assert.assertEquals(6, characterA.getUnderlineThickness());
    }

    @Test
    public void testSetUnderlineThickness_withThicknessGreaterThanBoundingBoxHeight() {
        characterA.getBoundingBox().setSize(2, 2);

        int originalThickness = characterA.getUnderlineThickness();

        characterA.setUnderlineThickness((int) characterA.getBoundingBox().getHeight() + 1);
        Assert.assertEquals(originalThickness, characterA.getUnderlineThickness());
    }

    @Test
    public void testSetUnderlineThickness_withThicknessEqualToZero() {
        characterA.getBoundingBox().setSize(2, 2);

        characterA.setUnderlineThickness(0);
        Assert.assertEquals(1, characterA.getUnderlineThickness());
    }

    @Test
    public void testSetUnderlineThickness_withThicknessLessThanZero() {
        characterA.getBoundingBox().setSize(2, 2);

        characterA.setUnderlineThickness(-1);
        Assert.assertEquals(1, characterA.getUnderlineThickness());
    }
}
