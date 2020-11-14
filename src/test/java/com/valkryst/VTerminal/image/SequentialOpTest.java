package com.valkryst.VTerminal.image;

import com.jhlabs.image.GaussianFilter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.ImagingOpException;

public class SequentialOpTest {
	@Test
	public void canCreateSequentialOpWithoutOps() {
		new SequentialOp();
	}

	@Test
	public void canCreateSequentialOpWithOps() {
		new SequentialOp(new GaussianFilter());
	}

	@Test
	public void canCreateSequentialOpWithNullOps() {
		new SequentialOp((BufferedImageOp) null);
		new SequentialOp(null, new GaussianFilter());
		new SequentialOp(new GaussianFilter(), null);
		new SequentialOp(new GaussianFilter(), null, new GaussianFilter());
	}

	@Test
	public void cannotFilterWithNullSource() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			final var op = new SequentialOp(new GaussianFilter());
			op.filter(null, null);
		});
	}

	@Test
	public void canFilterWithDestination() {
		final var image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		final var destination = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		final var op = new SequentialOp(new GaussianFilter());
		final var result = op.filter(image, null);
		Assertions.assertNotSame(image, result);
		Assertions.assertNotSame(destination, result);
		Assertions.assertEquals(10, result.getWidth());
		Assertions.assertEquals(10, result.getHeight());
		Assertions.assertEquals(BufferedImage.TYPE_INT_ARGB, result.getType());
	}

	@Test
	public void canFilterWithNullDestination() {
		final var image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		final var op = new SequentialOp(new GaussianFilter());
		final var result = op.filter(image, null);
		Assertions.assertNotSame(image, result);
		Assertions.assertEquals(10, result.getWidth());
		Assertions.assertEquals(10, result.getHeight());
		Assertions.assertEquals(BufferedImage.TYPE_INT_ARGB, result.getType());
	}

	@Test
	public void canFilterWithDestinationAndNoBufferedImageOps() {
		final var image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		final var destination = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		final var result = new SequentialOp().filter(image, null);
		Assertions.assertNotSame(image, result);
		Assertions.assertNotSame(destination, result);
		Assertions.assertEquals(10, result.getWidth());
		Assertions.assertEquals(10, result.getHeight());
		Assertions.assertEquals(BufferedImage.TYPE_INT_ARGB, result.getType());
	}

	@Test
	public void canFilterWithNullDestinationAndNoBufferedImageOps() {
		final var image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		final var result = new SequentialOp().filter(image, null);
		Assertions.assertNotSame(image, result);
		Assertions.assertEquals(10, result.getWidth());
		Assertions.assertEquals(10, result.getHeight());
		Assertions.assertEquals(BufferedImage.TYPE_INT_ARGB, result.getType());
	}

	@Test
	public void cannotFilterWithOpThatChangesTheResultingImageWidth() {
		final var image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		final var op = new SequentialOp(new BufferedImageOp() {
			@Override
			public BufferedImage filter(final BufferedImage src, final BufferedImage dest) {
				return new BufferedImage(5, 10, BufferedImage.TYPE_INT_ARGB);
			}

			@Override
			public Rectangle2D getBounds2D(final BufferedImage src) {
				return null;
			}

			@Override
			public BufferedImage createCompatibleDestImage(final BufferedImage src, final ColorModel destCM) {
				return null;
			}

			@Override
			public Point2D getPoint2D(final Point2D srcPt, final Point2D dstPt) {
				return null;
			}

			@Override
			public RenderingHints getRenderingHints() {
				return null;
			}
		});
		Assertions.assertThrows(ImagingOpException.class, () -> {
			op.filter(image, null);
		});
	}

	@Test
	public void cannotFilterWithOpThatChangesTheResultingImageHeight() {
		final var image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		final var op = new SequentialOp(new BufferedImageOp() {
			@Override
			public BufferedImage filter(final BufferedImage src, final BufferedImage dest) {
				return new BufferedImage(10, 5, BufferedImage.TYPE_INT_ARGB);
			}

			@Override
			public Rectangle2D getBounds2D(final BufferedImage src) {
				return null;
			}

			@Override
			public BufferedImage createCompatibleDestImage(final BufferedImage src, final ColorModel destCM) {
				return null;
			}

			@Override
			public Point2D getPoint2D(final Point2D srcPt, final Point2D dstPt) {
				return null;
			}

			@Override
			public RenderingHints getRenderingHints() {
				return null;
			}
		});
		Assertions.assertThrows(ImagingOpException.class, () -> {
			op.filter(image, null);
		});
	}

	@Test
	public void canGetBounds2D() {
		final var image = new BufferedImage(10, 20, BufferedImage.TYPE_INT_ARGB);
		final var bounds = new SequentialOp().getBounds2D(image);
		Assertions.assertEquals(0, bounds.getX());
		Assertions.assertEquals(0, bounds.getY());
		Assertions.assertEquals(10, bounds.getWidth());
		Assertions.assertEquals(20, bounds.getHeight());
	}

	@Test
	public void canCreateCompatibleDestImageWithColorModel() {
		final var image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		final var result = new SequentialOp().createCompatibleDestImage(image, image.getColorModel());
		Assertions.assertNotSame(image, result);
		Assertions.assertEquals(10, result.getWidth());
		Assertions.assertEquals(10, result.getHeight());
		Assertions.assertEquals(BufferedImage.TYPE_INT_ARGB, result.getType());
		Assertions.assertEquals(image.getColorModel(), result.getColorModel());
	}

	@Test
	public void canCreateCompatibleDestImageWithoutColorModel() {
		final var image = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		final var result = new SequentialOp().createCompatibleDestImage(image, null);
		Assertions.assertNotSame(image, result);
		Assertions.assertEquals(10, result.getWidth());
		Assertions.assertEquals(10, result.getHeight());
		Assertions.assertEquals(BufferedImage.TYPE_INT_ARGB, result.getType());
		Assertions.assertEquals(image.getColorModel(), result.getColorModel());
	}

	@Test
	public void canGetPoint2D() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new SequentialOp().getPoint2D(null, null);
		});
	}

	@Test
	public void canGetRenderingHints() {
		Assertions.assertThrows(UnsupportedOperationException.class, () -> {
			new SequentialOp().getRenderingHints();
		});
	}
}
