package com.valkryst.VTerminal.image;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.NonNull;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.ImagingOpException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/** A class for applying multiple {@link BufferedImageOp} operations, in sequence, to a {@link BufferedImage}. */
public class SequentialOp implements BufferedImageOp {
	/** A list of {@link BufferedImageOp} operations to apply to the image, in the order they should be applied. */
	private final List<BufferedImageOp> operations = new CopyOnWriteArrayList<>();

	/** A cache of {@link BufferedImage}s that have recently been filtered. */
	private final Cache<Integer, BufferedImage> cache;

	/** Constructs a new {@link SequentialOp}. */
	public SequentialOp() {
		cache = Caffeine.newBuilder()
						.initialCapacity(0)
						.maximumSize(2_500)
						.expireAfterAccess(5, TimeUnit.MINUTES)
						.build();
	}

	/**
	 * Constructs a new {@link SequentialOp}.
	 *
	 * @param operations One or more {@link BufferedImageOp} operations to add to the {@link #operations} sequence.
	 */
	public SequentialOp(final @NonNull BufferedImageOp ... operations) {
		this();
		addOperations(operations);
	}

	/**
	 * <p>Adds a {@link BufferedImageOp} to the sequence of operations.</p>
	 *
	 * <p>This will invalidate the cache.</p>
	 *
	 * @param operations One or more {@link BufferedImageOp} operations to add to the {@link #operations} sequence.
	 */
	public void addOperations(final @NonNull BufferedImageOp ... operations) {
		this.operations.addAll(Arrays.asList(operations));
		cache.invalidateAll();
	}

	@Override
	public BufferedImage createCompatibleDestImage(final @NonNull BufferedImage source, ColorModel destinationColorModel) {
		return new BufferedImage(
			destinationColorModel == null ? source.getColorModel() : destinationColorModel,
			source.copyData(source.getRaster().createCompatibleWritableRaster()),
			source.isAlphaPremultiplied(),
			null
		);
	}

	@Override
	public BufferedImage filter(final @NonNull BufferedImage source, BufferedImage destination) {
		final int hashCode = this.getBufferedImageHashCode(source);

		destination = cache.getIfPresent(hashCode);
		if (destination != null) {
			return destination;
		}

		destination = createCompatibleDestImage(source, null);

		for (final BufferedImageOp imageOp : operations) {
			var temp = createCompatibleDestImage(destination, null);
			temp = imageOp.filter(destination, temp);

			if (source.getWidth() != temp.getWidth()) {
				throw new ImagingOpException("BufferedImageOps of a SequentialOp mustn't change the image dimensions, but + " + imageOp.getClass().getSimpleName() + " changed the width.");
			} else if (source.getHeight() != temp.getHeight()) {
				throw new ImagingOpException("BufferedImageOps of a SequentialOp mustn't change the image dimensions, but + " + imageOp.getClass().getSimpleName() + " changed the height.");
			}

			destination = temp;
		}

		cache.put(hashCode, destination);
		return destination;
	}

	/**
	 * Calculates the hash code of a {@link BufferedImage}.
	 *
	 * @param image {@link BufferedImage} to calculate the hash code of.
	 * @return Calculated hash code of the {@link BufferedImage}.
	 */
	private int getBufferedImageHashCode(final @NonNull BufferedImage image) {
		return Objects.hash(
			Arrays.hashCode(
				image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth())
			),
			image.getColorModel(),
			image.isAlphaPremultiplied()
		);
	}

	@Override
	public Rectangle2D getBounds2D(final @NonNull BufferedImage source) {
		return new Rectangle(0, 0, source.getWidth(), source.getHeight());
	}

	@Override
	public Point2D getPoint2D(final Point2D sourcePoint, final Point2D destinationPoint) {
		throw new UnsupportedOperationException("This function should not be called. It only exists to satisfy the BufferedImageOp interface.");
	}

	@Override
	public RenderingHints getRenderingHints() {
		throw new UnsupportedOperationException("This function should not be called. It only exists to satisfy the BufferedImageOp interface.");
	}
}
