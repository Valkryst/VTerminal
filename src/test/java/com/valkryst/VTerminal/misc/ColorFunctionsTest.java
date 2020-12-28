package com.valkryst.VTerminal.misc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class ColorFunctionsTest {
    @Test
    public void canShadeColor() {
    	final var original = new Color[] {
			new Color(255, 255, 255, 255),
			new Color(255, 255, 255, 255),
			new Color(255, 255, 255, 255),
			new Color(255, 255, 255, 255),
			new Color(255, 255, 255, 255)
		};
		final var shadeFactor = new double[] {
			1.0,
			0.75,
			0.5,
			0.25,
			0.0
		};
    	final var expected = new Color[] {
			new Color(0, 0, 0, 255),
			new Color(63, 63, 63, 255),
			new Color(127, 127, 127, 255),
			new Color(191, 191, 191, 255),
			new Color(255, 255, 255, 255)
		};

		for (int i = 0 ; i < original.length ; i++) {
			final var result = ColorFunctions.shade(original[i], shadeFactor[i]);
			Assertions.assertEquals(expected[i], result);
		}
	}

	@Test
	public void canShadeColorWhenFactorIsAboveOne() {
		final Color color = new Color(255, 255, 255);
		final Color shadedColor = ColorFunctions.shade(color, 2);
		Assertions.assertEquals(Color.BLACK, shadedColor);
	}

	@Test
	public void canShadeColorWhenFactorIsBelowZero() {
		final Color color = new Color(255, 255, 255);
		final Color shadedColor = ColorFunctions.shade(color, 0);
		Assertions.assertEquals(color, shadedColor);
	}

	@Test
	public void cannotShadeNullColor() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			ColorFunctions.shade(null, 0.0);
		});
	}

	@Test
	public void canTintColor() {
		final var original = new Color[] {
			new Color(0, 0, 0, 255),
			new Color(0, 0, 0, 255),
			new Color(0, 0, 0, 255),
			new Color(0, 0, 0, 255),
			new Color(0, 0, 0, 255)
		};
		final var shadeFactor = new double[] {
			1.0,
			0.75,
			0.5,
			0.25,
			0.0
		};
		final var expected = new Color[] {
			new Color(255, 255, 255, 255),
			new Color(191, 191, 191, 255),
			new Color(127, 127, 127, 255),
			new Color(63, 63, 63, 255),
			new Color(0, 0, 0, 255)
		};

		for (int i = 0 ; i < original.length ; i++) {
			final var result = ColorFunctions.tint(original[i], shadeFactor[i]);
			Assertions.assertEquals(expected[i], result);
		}
	}

	@Test
	public void canTintColorWhenFactorIsAboveOne() {
		final Color color = new Color(255, 255, 255);
		final Color shadedColor = ColorFunctions.tint(color, 2);
		Assertions.assertEquals(Color.WHITE, shadedColor);
	}

	@Test
	public void canTintColorWhenFactorIsBelowZero() {
		final Color color = new Color(255, 255, 255);
		final Color shadedColor = ColorFunctions.tint(color, 0);
		Assertions.assertEquals(color, shadedColor);
	}

	@Test
	public void cannotTintNullColor() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			ColorFunctions.tint(null, 0.0);
		});
	}

	@Test
	public void canBlendAlphaWithColorObjects() {
		final Color source = new Color(0, 255, 0, 100);
		final Color destination = new Color(255, 0, 0, 255);

		final Color result = ColorFunctions.alphaBlend(source, destination);
		final Color expected = new Color(101, 156, 0, 255);

		Assertions.assertEquals(expected, result);
	}

	@Test
	public void canBlendAlphaWithInts() {
		final Color source = new Color(0, 255, 0, 100);
		final Color destination = new Color(255, 0, 0, 255);

		final int result = ColorFunctions.alphaBlend(source.getRGB(), destination.getRGB());
		final Color expected = new Color(101, 156, 0, 255);

		Assertions.assertEquals(expected, new Color(result));
	}

	@Test
	public void cannotBlendAlphaWithNullSource() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			ColorFunctions.alphaBlend(null, Color.BLACK);
		});
	}

	@Test
	public void cannotBlendAlphaWithNullDestination() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			ColorFunctions.alphaBlend(Color.BLACK, null);
		});
	}
}
