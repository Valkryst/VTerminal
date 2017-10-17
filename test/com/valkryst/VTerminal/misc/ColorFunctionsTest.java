package com.valkryst.VTerminal.misc;

import org.junit.Assert;
import org.junit.Test;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;

public class ColorFunctionsTest {
    @Test(expected=NullPointerException.class)
    public void testShade_withNullColor() {
        ColorFunctions.shade(null, 1);
    }

    @Test
    public void testShade_withAboveOneShadeFactor() {
        final Color color = new Color(255, 255, 255);
        final Color shadedColor = ColorFunctions.shade(color, 2);
        Assert.assertEquals(Color.BLACK, shadedColor);
    }

    @Test
    public void testShade_withBelowZeroShadeFactor() {
        final Color color = new Color(255, 255, 255);
        final Color shadedColor = ColorFunctions.shade(color, -1);
        Assert.assertEquals(color, shadedColor);
    }

    @Test
    public void testShade_withZeroShadeFactor() {
        final Color color = new Color(255, 255, 255);
        final Color shadedColor = ColorFunctions.shade(color, 0);
        Assert.assertEquals(color, shadedColor);
    }

    @Test
    public void testShade_withNonZeroAndNonOneShadeFactor() {
        float randVal = 0;

        while (randVal == 0 || randVal == 1) {
            randVal = ThreadLocalRandom.current().nextFloat();
        }

        final Color color = new Color(255, 255, 255);
        final Color shadedColor = ColorFunctions.shade(color, randVal);
        Assert.assertNotEquals(color, shadedColor);
    }

    @Test
    public void testShade_withOneShadeFactor() {
        final Color color = new Color(255, 255, 255);
        final Color shadedColor = ColorFunctions.shade(color, 1);
        Assert.assertEquals(Color.BLACK, shadedColor);
    }

    @Test(expected=NullPointerException.class)
    public void testTint_withNullColor() {
        ColorFunctions.shade(null, 1);
    }

    @Test
    public void testTint_withAboveOneTintFactor() {
        final Color color = new Color(255, 255, 255);
        final Color tintedColor = ColorFunctions.tint(color, 2);
        Assert.assertEquals(Color.WHITE, tintedColor);
    }

    @Test
    public void testTint_withBelowZeroTintFactor() {
        final Color color = new Color(0, 0, 0);
        final Color tintedColor = ColorFunctions.tint(color, -1);
        Assert.assertEquals(Color.BLACK, tintedColor);
    }

    @Test
    public void testTint_withZeroTintFactor() {
        final Color color = new Color(0, 0, 0);
        final Color tintedColor = ColorFunctions.tint(color, 0);
        Assert.assertEquals(color, tintedColor);
    }

    @Test
    public void testTint_withNonZeroAndNonOneTintFactor() {
        float randVal = 0;

        while (randVal == 0 || randVal == 1) {
            randVal = ThreadLocalRandom.current().nextFloat();
        }

        final Color color = new Color(0, 0, 0);
        final Color tintedColor = ColorFunctions.tint(color, randVal);
        Assert.assertNotEquals(color, tintedColor);
    }

    @Test
    public void testTint_withOneTintFactor() {
        final Color color = new Color(0, 0, 0, 255);
        final Color tintedColor = ColorFunctions.tint(color, 1);
        Assert.assertEquals(Color.WHITE, tintedColor);
    }

    @Test(expected=NullPointerException.class)
    public void testAlphaBlend_colorParams_withNullSource() {
        final Color destination = new Color(0, 0, 0);
        final Color result = ColorFunctions.alphaBlend(null, destination);
    }

    @Test(expected=NullPointerException.class)
    public void testAlphaBlend_colorParams_withNullDestination() {
        final Color source = new Color(255, 255, 255, 255);
        final Color result = ColorFunctions.alphaBlend(source, null);
    }

    @Test
    public void testAlphaBlend_colorParams_withValidColors() {
        final Color source = new Color(0, 255, 0, 100);
        final Color destination = new Color(255, 0, 0, 255);

        final Color result = ColorFunctions.alphaBlend(source, destination);
        final Color expected = new Color(101, 156, 0, 255);

        Assert.assertEquals(expected, result);
    }

    @Test
    public void testAlphaBlend_intParams_withValidColors() {
        final Color source = new Color(0, 255, 0, 100);
        final Color destination = new Color(255, 0, 0, 255);

        final int result = ColorFunctions.alphaBlend(source.getRGB(), destination.getRGB());
        final Color expected = new Color(101, 156, 0, 255);

        Assert.assertEquals(expected, new Color(result));
    }
}
