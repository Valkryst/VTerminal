package com.valkryst.VTerminal.plaf;

import com.jhlabs.image.GaussianFilter;
import com.valkryst.VTerminal.image.SequentialOp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

public class VTerminalLookAndFeelTest {
	private final int tileWidth;
	private final int tileHeight;

	public VTerminalLookAndFeelTest() throws NoSuchFieldException, IllegalAccessException {
		final var laf = VTerminalLookAndFeel.getInstance();
		tileWidth = laf.getTileWidth();
		tileHeight = laf.getTileHeight();
		nullifySingleton();
	}

	@AfterEach
	public void nullifySingleton() throws NoSuchFieldException, IllegalAccessException {
		final var laf = VTerminalLookAndFeel.getInstance();

		final var singletonField = VTerminalLookAndFeel.class.getDeclaredField("instance");
		singletonField.setAccessible(true);
		singletonField.set(laf, null);
	}

	@Test
	public void canClampDimensionWhenDimensionIsNull() {
		final var laf = VTerminalLookAndFeel.getInstance();
		final var dimension = laf.clampDimensionToTileMultiples(null);
		Assertions.assertEquals(tileWidth, dimension.getWidth());
		Assertions.assertEquals(tileHeight, dimension.getHeight());
	}

	@Test
	public void canClampDimensionHeightToHigherMultiple() {
		final var laf = VTerminalLookAndFeel.getInstance();

		final int height = (int) (tileHeight + (tileHeight / 2.0) + 1);
		var dimension = new Dimension(tileWidth, height);
		dimension = laf.clampDimensionToTileMultiples(dimension);

		Assertions.assertEquals(tileHeight * 2, dimension.height);
		Assertions.assertEquals(tileWidth, dimension.width);
	}

	@Test
	public void canClampDimensionHeightToHigherMultipleWhenHeightIsNearMaxInt() {
		final var laf = VTerminalLookAndFeel.getInstance();

		var dimension = new Dimension(tileWidth, Integer.MAX_VALUE);
		dimension = laf.clampDimensionToTileMultiples(dimension);

		Assertions.assertEquals(2147483645, dimension.height);
		Assertions.assertEquals(tileWidth, dimension.width);
	}

	@Test
	public void canClampDimensionHeightToLowerMultiple() {
		final var laf = VTerminalLookAndFeel.getInstance();

		final int height = (int) (tileHeight + (tileHeight / 2.0) - 1);
		var dimension = new Dimension(tileWidth, height);
		dimension = laf.clampDimensionToTileMultiples(dimension);

		Assertions.assertEquals(tileHeight, dimension.height);
		Assertions.assertEquals(tileWidth, dimension.width);
	}

	@Test
	public void canClampDimensionHeightToLowerMultipleWhenHeightIsBelowTileHeight() {
		final var laf = VTerminalLookAndFeel.getInstance();

		var dimension = new Dimension(tileWidth, tileHeight - 1);
		dimension = laf.clampDimensionToTileMultiples(dimension);

		Assertions.assertEquals(tileHeight, dimension.height);
		Assertions.assertEquals(tileWidth, dimension.width);
	}

	@Test
	public void canClampHeightToHigherMultipleWhenClampingDistancesAreEqual() {
		/*
		 * The dimension object will internally alter the width/height given to
		 * it by calling Math.ceil. As we want to test the case where the lower
		 * and higher multiples are at equal distances from the given height,
		 * we need to mock the object.
		 */
		final double height = tileHeight + (tileHeight / 2.0);
		var dimension = Mockito.mock(Dimension.class);
		Mockito.when(dimension.getWidth()).thenReturn((double) tileWidth);
		Mockito.when(dimension.getHeight()).thenReturn(height);

		final var laf = VTerminalLookAndFeel.getInstance();
		dimension = laf.clampDimensionToTileMultiples(dimension);

		Assertions.assertEquals(tileHeight * 2, dimension.height);
		Assertions.assertEquals(tileWidth, dimension.width);
	}

	@Test
	public void canClampDimensionWidthToHigherMultiple() {
		final var laf = VTerminalLookAndFeel.getInstance();

		final int width = (int) (tileWidth + (tileWidth / 2.0) + 1);
		var dimension = new Dimension(width, tileHeight);
		dimension = laf.clampDimensionToTileMultiples(dimension);

		Assertions.assertEquals(tileHeight, dimension.height);
		Assertions.assertEquals(tileWidth * 2, dimension.width);
	}

	@Test
	public void canClampDimensionWidthToHigherMultipleWhenWidthIsNearMaxInt() {
		final var laf = VTerminalLookAndFeel.getInstance();

		var dimension = new Dimension(Integer.MAX_VALUE, tileHeight);
		dimension = laf.clampDimensionToTileMultiples(dimension);

		Assertions.assertEquals(tileHeight, dimension.height);
		Assertions.assertEquals(2147483640, dimension.width);
	}

	@Test
	public void canClampDimensionWidthToLowerMultiple() {
		final var laf = VTerminalLookAndFeel.getInstance();

		final int width = (int) (tileWidth + (tileWidth / 2.0) - 1);
		var dimension = new Dimension(width, tileHeight);
		dimension = laf.clampDimensionToTileMultiples(dimension);

		Assertions.assertEquals(tileHeight, dimension.height);
		Assertions.assertEquals(tileWidth, dimension.width);
	}

	@Test
	public void canClampDimensionWidthToLowerMultipleWhenWidthIsBelowTileWidth() {
		final var laf = VTerminalLookAndFeel.getInstance();

		var dimension = new Dimension(tileWidth - 1, tileHeight);
		dimension = laf.clampDimensionToTileMultiples(dimension);

		Assertions.assertEquals(tileHeight, dimension.height);
		Assertions.assertEquals(tileWidth, dimension.width);
	}

	@Test
	public void canClampWidthToHigherMultipleWhenClampingDistancesAreEqual() {
		/*
		 * The dimension object will internally alter the width/height given to
		 * it by calling Math.ceil. As we want to test the case where the lower
		 * and higher multiples are at equal distances from the given height,
		 * we need to mock the object.
		 */
		final double width = tileWidth + (tileWidth / 2.0);
		var dimension = Mockito.mock(Dimension.class);
		Mockito.when(dimension.getWidth()).thenReturn(width);
		Mockito.when(dimension.getHeight()).thenReturn((double) tileHeight);

		final var laf = VTerminalLookAndFeel.getInstance();
		dimension = laf.clampDimensionToTileMultiples(dimension);

		Assertions.assertEquals(tileHeight, dimension.height);
		Assertions.assertEquals(tileWidth * 2, dimension.width);
	}

	@Test
	public void canGenerateImageWithoutSequentialOp() {
		final var laf = VTerminalLookAndFeel.getInstance();
		final var image = laf.generateImage('A', Color.MAGENTA, null);
		Assertions.assertNotNull(image);
		Assertions.assertTrue(image.getWidth(null) >= 1);
		Assertions.assertTrue(image.getHeight(null) >= 1);
	}

	@Test
	public void canGenerateImageWithSequentialOp() {
		final var laf = VTerminalLookAndFeel.getInstance();
		var image = laf.generateImage('A', Color.MAGENTA, new SequentialOp());
		Assertions.assertNotNull(image);
		Assertions.assertTrue(image.getWidth(null) >= 1);
		Assertions.assertTrue(image.getHeight(null) >= 1);

		image = laf.generateImage('A', Color.MAGENTA, new SequentialOp(new GaussianFilter()));
		Assertions.assertNotNull(image);
		Assertions.assertTrue(image.getWidth(null) >= 1);
		Assertions.assertTrue(image.getHeight(null) >= 1);
	}

	@Test
	public void canRetrieveDescription() {
		final var laf = VTerminalLookAndFeel.getInstance();
		Assertions.assertEquals("The VTerminal look and feel.", laf.getDescription());
	}

	@Test
	public void canRetrieveID() {
		final var laf = VTerminalLookAndFeel.getInstance();
		Assertions.assertEquals("VTerminal", laf.getID());
	}

	@Test
	public void canRetrieveInstanceWithNoParameters() {
		final var laf = VTerminalLookAndFeel.getInstance();
		Assertions.assertNotNull(laf);
	}

	@Test
	public void canRetrieveInstanceWithNoParametersWhenInstanceIsAlreadyInitialized() {
		Assertions.assertNotNull(VTerminalLookAndFeel.getInstance());
		Assertions.assertNotNull(VTerminalLookAndFeel.getInstance());
	}

	@Test
	public void canRetrieveWithParametersWhenInstanceIsAlreadyInitialized() throws IOException, FontFormatException {
		final var inputStream = VTerminalLookAndFeelTest.class.getResourceAsStream("/Fonts/DejaVuSansMono.ttf");
		Assertions.assertNotNull(VTerminalLookAndFeel.getInstance(inputStream, 16));
		Assertions.assertNotNull(VTerminalLookAndFeel.getInstance(inputStream, 16));
	}

	@Test
	public void canRetrieveInstanceWithParameters() throws IOException, FontFormatException {
		final var inputStream = VTerminalLookAndFeelTest.class.getResourceAsStream("/Fonts/DejaVuSansMono.ttf");
		final var laf = VTerminalLookAndFeel.getInstance(inputStream, 16);
		Assertions.assertNotNull(laf);
	}

	@Test
	public void cannotRetrieveInstanceIfFontInputStreamIsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			VTerminalLookAndFeel.getInstance(null, 16);
		});
	}

	@Test
	public void cannotRetrieveInstanceIfFontInputStreamPointsToNonExistentFile() {
		Assertions.assertThrows(IOException.class, () -> {
			final var inputStream = new FileInputStream("fake_file.test");
			VTerminalLookAndFeel.getInstance(inputStream, 16);
		});
	}

	@Test
	public void canRetrieveInstanceIfPointSizeIsLessThan10() throws IOException, FontFormatException {
		final var inputStream = VTerminalLookAndFeelTest.class.getResourceAsStream("/Fonts/DejaVuSansMono.ttf");
		final var instance = VTerminalLookAndFeel.getInstance(inputStream, 9);
		Assertions.assertNotNull(instance);
	}

	@Test
	public void canRetrieveName() {
		final var laf = VTerminalLookAndFeel.getInstance();
		Assertions.assertEquals("VTerminal Look & Feel", laf.getName());
	}

	@Test
	public void canRetrieveTileWidth() {
		final var laf = VTerminalLookAndFeel.getInstance();
		Assertions.assertTrue(laf.getTileHeight() > 0);
	}

	@Test
	public void canRetrieveTileHeight() {
		final var laf = VTerminalLookAndFeel.getInstance();
		Assertions.assertTrue(laf.getTileWidth() > 0);
	}

	@Test
	public void canSetComponentFont() {
		final var button = new JButton();
		final var originalFont = button.getFont();
		VTerminalLookAndFeel.getInstance().setComponentFont(button);
		Assertions.assertNotEquals(originalFont, button.getFont());
	}

	@Test
	public void cannotSetComponentFontIfComponentIsNull() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			VTerminalLookAndFeel.getInstance().setComponentFont(null);
		});
	}

	@Test
	public void reportsAsNonNativeLookAndFeel() {
		final var laf = VTerminalLookAndFeel.getInstance();
		Assertions.assertFalse(laf.isNativeLookAndFeel());
	}

	@Test
	public void reportsAsSupportedLookAndFeel() {
		final var laf = VTerminalLookAndFeel.getInstance();
		Assertions.assertTrue(laf.isSupportedLookAndFeel());
	}
}
