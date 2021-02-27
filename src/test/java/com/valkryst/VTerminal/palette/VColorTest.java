package com.valkryst.VTerminal.palette;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.color.ColorSpace;

public class VColorTest {
	@Test
	public void canConstructWithAColor() {
		final var color = new VColor(Color.RED);
		Assertions.assertEquals(Color.RED, color);
	}

	@Test
	public void cannotConstructWithANullColor() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			new VColor(null);
		});
	}

	@Test
	public void canConstructWithSeparateRGBIntComponents() {
		final var color = new VColor(10, 20, 30);
		Assertions.assertEquals(255, color.getAlpha());
		Assertions.assertEquals(10, color.getRed());
		Assertions.assertEquals(20, color.getGreen());
		Assertions.assertEquals(30, color.getBlue());
	}

	@Test
	public void canConstructWithSeparateRGBAIntComponents() {
		final var color = new VColor(10, 20, 30, 40);
		Assertions.assertEquals(40, color.getAlpha());
		Assertions.assertEquals(10, color.getRed());
		Assertions.assertEquals(20, color.getGreen());
		Assertions.assertEquals(30, color.getBlue());
	}

	@Test
	public void canConstructWithCombinedRGBComponents() {
		final var color = new VColor(660510);
		Assertions.assertEquals(255, color.getAlpha());
		Assertions.assertEquals(10, color.getRed());
		Assertions.assertEquals(20, color.getGreen());
		Assertions.assertEquals(30, color.getBlue());
	}

	@Test
	public void canConstructWithCombinedRGBAComponents() {
		var color = new VColor(671749150, true);
		Assertions.assertEquals(40, color.getAlpha());
		Assertions.assertEquals(10, color.getRed());
		Assertions.assertEquals(20, color.getGreen());
		Assertions.assertEquals(30, color.getBlue());

		color = new VColor(671749150, false);
		Assertions.assertEquals(255, color.getAlpha());
		Assertions.assertEquals(10, color.getRed());
		Assertions.assertEquals(20, color.getGreen());
		Assertions.assertEquals(30, color.getBlue());
	}

	@Test
	public void canConstructWithSeparateRGBFloatComponents() {
		final var color = new VColor(0.5f, 0.6f, 0.7f);
		Assertions.assertEquals(255, color.getAlpha());
		Assertions.assertEquals(128, color.getRed());
		Assertions.assertEquals(153, color.getGreen());
		Assertions.assertEquals(179, color.getBlue());
	}

	@Test
	public void canConstructWithSeparateRGBAFloatComponents() {
		final var color = new VColor(0.5f, 0.6f, 0.7f, 0.8f);
		Assertions.assertEquals(204, color.getAlpha());
		Assertions.assertEquals(128, color.getRed());
		Assertions.assertEquals(153, color.getGreen());
		Assertions.assertEquals(179, color.getBlue());
	}

	@Test
	public void canConstructWithColorSpaceAndComponents() {
		final var colorSpace = ColorSpace.getInstance(ColorSpace.CS_sRGB);
		final var components = new float[] { 0.5f, 0.6f, 0.7f };
		final var color = new VColor(colorSpace, components, 0.8f);
		Assertions.assertEquals(204, color.getAlpha());
		Assertions.assertEquals(127, color.getRed());
		Assertions.assertEquals(153, color.getGreen());
		Assertions.assertEquals(178, color.getBlue());
	}

	@Test
	public void cannotConstructWithColorSpaceAndComponentsWhenColorSpaceIsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			final var components = new float[] { 0.5f, 0.6f, 0.7f };
			new VColor(null, components, 0.8f);
		});
	}

	@Test
	public void cannotConstructWithColorSpaceAndComponentsWhenComponentsAreNull() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			final var colorSpace = ColorSpace.getInstance(ColorSpace.CS_sRGB);
			new VColor(colorSpace, null, 0.8f);
		});
	}

	@Test
	public void canShadeColor() {
		final var expected = new Color[] {
			new Color(0, 0, 0, 255),
			new Color(63, 63, 63, 255),
			new Color(127, 127, 127, 255),
			new Color(191, 191, 191, 255),
			new Color(255, 255, 255, 255)
		};

		for (int i = 0 ; i < expected.length ; i++) {
			final double amount = 1.0 - (i * 0.25);
			final var color = new VColor(255, 255, 255, 255);
			final var shadedColor = color.shade(amount);
			Assertions.assertNotSame(color, shadedColor);
			Assertions.assertEquals(expected[i], shadedColor);
		}
	}

	@Test
	public void canShadeColorWhenAmountIsAboveOne() {
		final var color = new VColor(Color.WHITE);
		Assertions.assertEquals(Color.BLACK, color.shade(2));
	}

	@Test
	public void canShadeColorWhenAmountIsBelowZero() {
		final var color = new VColor(255, 255, 255);
		Assertions.assertEquals(color, color.shade(-1));
	}

	@Test
	public void canTintColor() {
		final var expected = new Color[] {
			new Color(255, 255, 255, 255),
			new Color(191, 191, 191, 255),
			new Color(127, 127, 127, 255),
			new Color(63, 63, 63, 255),
			new Color(0, 0, 0, 255)
		};

		for (int i = 0 ; i < expected.length ; i++) {
			final double amount = 1.0 - (i * 0.25);
			final var color = new VColor(0, 0, 0, 255);
			final var tintedColor = color.tint(amount);
			Assertions.assertNotSame(color, tintedColor);
			Assertions.assertEquals(expected[i], tintedColor);
		}
	}

	@Test
	public void canTintColorWhenAmountIsAboveOne() {
		final var color = new VColor(Color.BLACK);
		Assertions.assertEquals(Color.WHITE, color.tint(2));
	}

	@Test
	public void canTintColorWhenAmountIsBelowZero() {
		final var color = new VColor(255, 255, 255);
		Assertions.assertEquals(color, color.tint(-1));
	}
}
