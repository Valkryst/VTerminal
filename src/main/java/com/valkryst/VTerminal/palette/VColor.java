package com.valkryst.VTerminal.palette;

import lombok.NonNull;

import java.awt.*;
import java.awt.color.ColorSpace;

public class VColor extends Color {
	/**
	 * Constructs a new instance of {@code VColor}, copying the RGBA components
	 * of a given color.
	 *
	 * @param color A color.
	 */
	public VColor(final @NonNull Color color) {
		super(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
	}

	/**
	 * Constructs a new instance of {@code VColor}.
	 *
	 * @param red The red component.
	 * @param green The green component
	 * @param blue The blue component.
	 * @throws IllegalArgumentException
	 * 		If {@code red}, {@code green}, {@code blue}, or {@code alpha} are
	 * 		outside the range 0 to 255.
	 */
	public VColor(final int red, final int green, final int blue) {
		super(red, green, blue);
	}

	/**
	 * Constructs a new instance of {@code VColor}.
	 *
	 * @param red The red component.
	 * @param green The green component
	 * @param blue The blue component.
	 * @param alpha The alpha component.
	 * @throws IllegalArgumentException
	 * 		If {@code red}, {@code green}, {@code blue}, or {@code alpha} are
	 * 		outside the range 0 to 255.
	 */
	public VColor(final int red, final int green, final int blue, final int alpha) {
		super(red, green, blue, alpha);
	}

	/**
	 * Constructs a new instance of {@code VColor}.
	 *
	 * @param rgb The combined RGB components.
	 */
	public VColor(final int rgb) {
		super(rgb);
	}

	/**
	 * Constructs a new instance of {@code VColor}.
	 *
	 * @param rgba The combined RGBA components.
	 * @param hasAlpha {@code true} if the alpha bits are valid;
	 *                 {@code false} otherwise.
	 */
	public VColor(final int rgba, final boolean hasAlpha) {
		super(rgba, hasAlpha);
	}

	/**
	 * Constructs a new instance of {@code VColor}.
	 *
	 * @param red The red component.
	 * @param green The green component.
	 * @param blue The blue component.
	 * @throws IllegalArgumentException
	 * 		If {@code red}, {@code green}, or {@code blue} are outside the range
	 * 		0.0 to 1.0.
	 */
	public VColor(final float red, final float green, final float blue) {
		super(red, green, blue);
	}

	/**
	 * Constructs a new instance of {@code VColor}.
	 *
	 * @param red The red component.
	 * @param green The green component.
	 * @param blue The blue component.
	 * @param alpha The alpha component.
	 * @throws IllegalArgumentException
	 * 		If {@code red}, {@code green}, {@code blue}, or {@code alpha} are
	 * 		outside the range 0.0 to 1.0.
	 */
	public VColor(final float red, final float green, final float blue, final float alpha) {
		super(red, green, blue, alpha);
	}

	/**
	 * Constructs a new instance of {@code VColor}.
	 *
	 * @param colorSpace
	 * 		The {@code ColorSpace} to be used to interpret the components.
	 * @param components
	 * 		An arbitrary number of color components that are compatible with the
	 * 		{@code ColorSpace}
	 * @param alpha The alpha component.
	 * @throws IllegalArgumentException
	 * 		If any of the {@code components}, including the {@code alpha}
	 * 		component, are outside the range 0.0 to 1.0.
	 */
	public VColor(final @NonNull ColorSpace colorSpace, final float[] components, final float alpha) {
		super(colorSpace, components, alpha);
	}

	/**
	 * Creates a shaded copy of this color, where a higher amount results in a
	 * darker color.
	 *
	 * @param amount An amount to shade by.
	 * @return The shaded color.
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
	 * Creates a tinted copy of this color, where a higher amount results in a
	 * lighter color.
	 *
	 * @param amount An amount to tint by.
	 * @return The shaded color.
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
