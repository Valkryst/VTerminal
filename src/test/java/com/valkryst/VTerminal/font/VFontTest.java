package com.valkryst.VTerminal.font;

import com.jhlabs.image.GaussianFilter;
import com.valkryst.VTerminal.image.SequentialOp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;

public class VFontTest {
	private static VFont font;

	@BeforeAll
	static void setUpFont() throws IOException, FontFormatException {
		final var inputStream = VFontTest.class.getResourceAsStream("/Fonts/DejaVuSansMono.ttf");
		font = new VFont(inputStream, 16);
	}

	@Test
	public void cannotCreateWithNullInputStream() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			new VFont(null, 16);
		});
	}

	@Test
	public void canGetFont() {
		Assertions.assertNotNull(font.getFont());
	}

	@Test
	public void canGetMaxTileWidth() {
		Assertions.assertTrue(font.getMaxTileWidth() > 0);
	}

	@Test
	public void canGetMaxTileHeight() {
		Assertions.assertTrue(font.getMaxTileHeight() > 0);
	}

	@Test
	public void canGenerateImageForRegularCodePoint() {
		final var image = font.generateImage('A', Color.WHITE, null);
		Assertions.assertNotNull(image);
		Assertions.assertTrue(image.getWidth(null) >= 1);
		Assertions.assertTrue(image.getHeight(null) >= 1);
	}

	@Test
	public void canGenerateNullImageForSpaceCodePoint() {
		Assertions.assertNull(font.generateImage(' ', Color.WHITE, null));
	}

	@Test
	public void canGenerateNullImageForNewlineCodePoint() {
		Assertions.assertNull(font.generateImage('\n', Color.WHITE, null));
	}

	@Test
	public void canGenerateNullImageForTabCodePoint() {
		Assertions.assertNull(font.generateImage('\t', Color.WHITE, null));
	}

	@Test
	public void cannotGenerateImageForInvalidCodePoint() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			font.generateImage(Character.MAX_CODE_POINT + 1, Color.WHITE, null);
		});
	}

	@Test
	public void canGenerateImageWithSequentialOp() {
		final var image = font.generateImage('A', Color.WHITE, new SequentialOp(new GaussianFilter()));
		Assertions.assertNotNull(image);
		Assertions.assertTrue(image.getWidth(null) >= 1);
		Assertions.assertTrue(image.getHeight(null) >= 1);
	}

	@Test
	public void canReturnCachedImageOnSubsequentCalls() {
		final var first = font.generateImage('Z', Color.RED, null);
		final var second = font.generateImage('Z', Color.RED, null);
		Assertions.assertNotNull(first);
		Assertions.assertNotNull(second);
		Assertions.assertSame(first, second);
	}

	@Test
	public void canGenerateDifferentImagesForDifferentColors() {
		final var redImage = font.generateImage('B', Color.RED, null);
		final var blueImage = font.generateImage('B', Color.BLUE, null);
		Assertions.assertNotNull(redImage);
		Assertions.assertNotNull(blueImage);
		Assertions.assertNotSame(redImage, blueImage);
	}

	@Test
	public void canGenerateDifferentImagesForDifferentCodePoints() {
		final var imageA = font.generateImage('A', Color.WHITE, null);
		final var imageB = font.generateImage('B', Color.WHITE, null);
		Assertions.assertNotNull(imageA);
		Assertions.assertNotNull(imageB);
		Assertions.assertNotSame(imageA, imageB);
	}

	@Test
	public void canGenerateImageForBoxDrawingCharacter() {
		final var image = font.generateImage('─', Color.WHITE, null);
		Assertions.assertNotNull(image);
	}

	@Test
	public void canGenerateImageWithEmptySequentialOp() {
		final var image = font.generateImage('C', Color.GREEN, new SequentialOp());
		Assertions.assertNotNull(image);
	}
}
