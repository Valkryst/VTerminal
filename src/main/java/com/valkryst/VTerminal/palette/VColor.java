package com.valkryst.VTerminal.palette;

import lombok.NonNull;

import java.awt.*;
import java.awt.color.ColorSpace;

/** An extension of the {@link Color} class, which provides additional functionality. */
public class VColor extends Color {
	/**
	 * Constructs a new {@link VColor}, copying the RGBA components of a given {@link Color}.
	 *
	 * @param color {@link Color} to copy the RGBA components from.
	 */
	public VColor(final @NonNull Color color) {
		super(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	/** See {@link Color#Color(int, int, int)}. */
	public VColor(final int red, final int green, final int blue) {
		super(red, green, blue);
	}

	/** See {@link Color#Color(int, int, int, int)}. */
	public VColor(final int red, final int green, final int blue, final int alpha) {
		super(red, green, blue, alpha);
	}

	/** See {@link Color#Color(int)}. */
	public VColor(final int rgb) {
		super(rgb);
	}

	/** See {@link Color#Color(int, boolean)}. */
	public VColor(final int rgba, final boolean hasAlpha) {
		super(rgba, hasAlpha);
	}

	/** See {@link Color#Color(float, float, float)}. */
	public VColor(final float red, final float green, final float blue) {
		super(red, green, blue);
	}

	/** See {@link Color#Color(float, float, float, float)}. */
	public VColor(final float red, final float green, final float blue, final float alpha) {
		super(red, green, blue, alpha);
	}

	/**
	 * Constructs a new {@link VColor}.
	 *
	 * @param colorSpace {@link ColorSpace} to be used to interpret the components.
	 * @param components An arbitrary number of color components that are compatible with the {@code ColorSpace}.
	 * @param alpha The alpha component.
	 * @throws IllegalArgumentException
	 * 		If any of the {@code components}, including the {@code alpha} component, are outside the range 0.0 to 1.0.
	 */
	public VColor(final @NonNull ColorSpace colorSpace, final float[] components, final float alpha) {
		super(colorSpace, components, alpha);
	}

	/**
	 * Constructs a shaded copy of this {@link VColor}, where a higher amount results in a darker color.
	 *
	 * @param amount An amount to shade by.
	 * @return A new {@link VColor}, with the specified amount of shading applied.
	 */
	public VColor shade(final double amount) {
		if (amount <= 0.0) {
			return new VColor(this);
		} else if (amount >= 1.0) {
			return new VColor(0, 0, 0, super.getAlpha());
		}

		final int a = super.getAlpha();
		final int r = (int) (super.getRed() * (1.0 - amount));
		final int g = (int) (super.getGreen() * (1.0 - amount));
		final int b = (int) (super.getBlue() * (1.0 - amount));

		return new VColor(r, g, b, a);
	}

	/**
	 * Creates a tinted copy of this {@link VColor}, where a higher amount results in a lighter color.
	 *
	 * @param amount An amount to tint by.
	 * @return A new {@link VColor}, with the specified amount of tinting applied.
	 */
	public VColor tint(final double amount) {
		if (amount <= 0.0) {
			return new VColor(this);
		} else if (amount > 1.0) {
			return new VColor(255, 255, 255, super.getAlpha());
		}

		final int a = super.getAlpha();
		final int r = (int) ((255 - super.getRed()) * amount);
		final int g = (int) ((255 - super.getGreen()) * amount);
		final int b = (int) ((255 - super.getBlue()) * amount);

		return new VColor(r, g, b, a);
	}
}
