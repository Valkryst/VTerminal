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
import java.util.concurrent.TimeUnit;

public class SequentialOp implements BufferedImageOp {
	private final List<BufferedImageOp> bufferedImageOps = new ArrayList<>();

	private final Cache<Integer, BufferedImage> filteredImageCache;

	/**
	 * Constructs a new instance of {@code SequentialOp}.
	 *
	 * @param ops One or more operations to add to the sequence.
	 */
	public SequentialOp(final @NonNull BufferedImageOp ... ops) {
		bufferedImageOps.addAll(Arrays.asList(ops));

		filteredImageCache = Caffeine.newBuilder()
								 	 .initialCapacity(0)
									 .maximumSize(2_500)
									 .expireAfterAccess(5, TimeUnit.MINUTES)
									 .build();
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
		destination = filteredImageCache.getIfPresent(Objects.hash(filteredImageCache));

		if (destination == null) {
			destination = createCompatibleDestImage(source, null);
		} else {
			return destination;
		}

		for (final BufferedImageOp imageOp : bufferedImageOps) {
			var temp = createCompatibleDestImage(destination, null);
			temp = imageOp.filter(destination, temp);

			if (source.getWidth() != temp.getWidth()) {
				throw new ImagingOpException("BufferedImageOps of a SequentialOp mustn't change the image dimensions, but + " + imageOp.getClass().getSimpleName() + " changed the width.");
			} else if (source.getHeight() != temp.getHeight()) {
				throw new ImagingOpException("BufferedImageOps of a SequentialOp mustn't change the image dimensions, but + " + imageOp.getClass().getSimpleName() + " changed the height.");
			}

			destination = temp;
		}

		return destination;
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
