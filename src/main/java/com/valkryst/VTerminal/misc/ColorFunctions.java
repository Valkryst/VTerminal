package com.valkryst.VTerminal.misc;

import lombok.NonNull;

import java.awt.*;

public final class ColorFunctions {
	// Prevent users from creating an instance.
	private ColorFunctions() {}

	/**
	 * Shades a color by some factor, where a higher factor results in a darker
	 * shade.
	 *
	 * @param color A color
	 * @param shadeFactor The amount to shade by, from 0.0 to 1.0.
	 * @return The shaded color.
	 */
	public static Color shade(final @NonNull Color color, double shadeFactor) {
		if (shadeFactor > 1.0) {
			shadeFactor = 1.0;
		}

		if (shadeFactor < 0.0) {
			shadeFactor = 0.0;
		}

		final int a = color.getAlpha();
		double r = color.getRed();
		double g = color.getGreen();
		double b = color.getBlue();

		r *= (1.0 - shadeFactor);
		g *= (1.0 - shadeFactor);
		b *= (1.0 - shadeFactor);

		return new Color((int) r, (int) g, (int) b, a);
	}

	/**
	 * Tints a color by some factor, where a higher factor results in a lighter
	 * tint.
	 *
	 * @param color A color.
	 * @param tintFactor The amount to tint by, from 0.0 to 1.0.
	 * @return The tinted color.
	 */
	public static Color tint(final @NonNull Color color, double tintFactor) {
		if (tintFactor > 1.0) {
			tintFactor = 1.0;
		}

		if (tintFactor < 0.0) {
			tintFactor = 0.0;
		}

		final int a = color.getAlpha();
		double r = color.getRed();
		double g = color.getGreen();
		double b = color.getBlue();

		r += ((255.0 - r) * tintFactor);
		g += ((255.0 - g) * tintFactor);
		b += ((255.0 - b) * tintFactor);

		return new Color((int) r, (int) g, (int) b, a);
	}

	/**
	 * Blends two colors using the alpha blend algorithm.
	 *
	 * @param source The source color being blended onto the destination color.
	 * @param destination The destination color.
	 * @return The blended color.
	 */
	public static Color alphaBlend(final @NonNull Color source, final @NonNull Color destination) {
		return new Color(alphaBlend(source.getRGB(), destination.getRGB()));
	}

	/**
	 * Blends two RGBA values using the alpha blend algorithm.
	 *
	 * @param sourceRGBA The source RGBA being blended onto the destination RGBA.
	 * @param destinationRGBA The destination RGBA.
	 * @return The blended RGBA value.
	 */
	public static int alphaBlend(final int sourceRGBA, final int destinationRGBA) {
		final int destinationA = (destinationRGBA >> 24) & 0xFF;
		final int destinationR = (destinationRGBA >> 16) & 0xFF;
		final int destinationG = (destinationRGBA >> 8) & 0xFF;
		final int destinationB = destinationRGBA & 0xFF;

		final int sourceA = (sourceRGBA >> 24) & 0xFF;
		final int sourceR = (sourceRGBA >> 16) & 0xFF;
		final int sourceG = (sourceRGBA >> 8) & 0xFF;
		final int sourceB = sourceRGBA & 0xFF;

		int alphaBlend = destinationA * (255 - sourceA) + sourceA;
		int redBlend = destinationR * (255 - sourceA) + (sourceR * sourceA);
		int greenBlend = destinationG * (255 - sourceA) + (sourceG * sourceA);
		int blueBlend = destinationB * (255 - sourceA) + (sourceB * sourceA);

		alphaBlend &= 0xFF;
		redBlend &= 0xFF;
		greenBlend &= 0xFF;
		blueBlend &= 0xFF;

		return (alphaBlend << 24) + (redBlend << 16) + (greenBlend << 8) + blueBlend;
	}
}
