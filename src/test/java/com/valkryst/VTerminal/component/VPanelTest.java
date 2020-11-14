package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.image.SequentialOp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.awt.*;

public class VPanelTest {
	@Test
	public void canCreatePanel() throws NoSuchFieldException, IllegalAccessException {
		final int panelWidth = 10;
		final int panelHeight = 15;

		final var panel = new VPanel(panelWidth, panelHeight);
		Assertions.assertNotNull(panel);

		final var panelClass = panel.getClass();
		final var codePointsField = panelClass.getDeclaredField("codePoints");
		final var backgroundColorsField = panelClass.getDeclaredField("backgroundColors");
		final var foregroundColorsField = panelClass.getDeclaredField("foregroundColors");
		final var sequentialImageOpsField = panelClass.getDeclaredField("sequentialImageOps");

		codePointsField.setAccessible(true);
		backgroundColorsField.setAccessible(true);
		foregroundColorsField.setAccessible(true);
		sequentialImageOpsField.setAccessible(true);

		final var codePoints = (int[][]) codePointsField.get(panel);
		Assertions.assertEquals(panelHeight, codePoints.length);
		Assertions.assertEquals(panelWidth, codePoints[0].length);

		final var backgroundColors = (Color[][]) backgroundColorsField.get(panel);
		Assertions.assertEquals(panelHeight, backgroundColors.length);
		Assertions.assertEquals(panelWidth, backgroundColors[0].length);

		final var foregroundColors = (Color[][]) foregroundColorsField.get(panel);
		Assertions.assertEquals(panelHeight, foregroundColors.length);
		Assertions.assertEquals(panelWidth, foregroundColors[0].length);

		final var sequentialImageOps = (SequentialOp[][]) sequentialImageOpsField.get(panel);
		Assertions.assertEquals(panelHeight, sequentialImageOps.length);
		Assertions.assertEquals(panelWidth, sequentialImageOps[0].length);

		final var panelBackgroundColor = panel.getBackground();
		final var panelForegroundColor = panel.getForeground();
		for (int y = 0 ; y < panelHeight ; y++) {
			for (int x = 0 ; x < panelWidth ; x++) {
				Assertions.assertEquals(' ', codePoints[y][x]);
				Assertions.assertEquals(panelBackgroundColor, backgroundColors[y][x]);
				Assertions.assertEquals(panelForegroundColor, foregroundColors[y][x]);
				Assertions.assertNull(sequentialImageOps[y][x]);
			}
		}
	}

	@Test
	public void cannotCreatePanelWithNonPositiveWidth() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new VPanel(0, 10);
		});
	}

	@Test
	public void cannotCreatePanelWithNonPositiveHeight() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new VPanel(10, 0);
		});
	}

	@Test
	public void canSetBackgroundColor() throws NoSuchFieldException, IllegalAccessException {
		final var panel = new VPanel(10, 10);
		panel.setBackground(Color.MAGENTA);

		final var backgroundColorsField = panel.getClass().getDeclaredField("backgroundColors");
		backgroundColorsField.setAccessible(true);
		final var backgroundColors = (Color[][]) backgroundColorsField.get(panel);

		for (int y = 0 ; y < panel.getHeightInTiles() ; y ++) {
			for (int x = 0 ; x < panel.getWidthInTiles() ; x++) {
				Assertions.assertEquals(Color.MAGENTA, backgroundColors[y][x]);

			}
		}
	}

	@Test
	public void canSetForegroundColor() throws NoSuchFieldException, IllegalAccessException {
		final var panel = new VPanel(10, 10);
		panel.setForeground(Color.MAGENTA);

		final var foregroundColorsField = panel.getClass().getDeclaredField("foregroundColors");
		foregroundColorsField.setAccessible(true);
		final var foregroundColors = (Color[][]) foregroundColorsField.get(panel);

		for (int y = 0 ; y < panel.getHeightInTiles() ; y ++) {
			for (int x = 0 ; x < panel.getWidthInTiles() ; x++) {
				Assertions.assertEquals(Color.MAGENTA, foregroundColors[y][x]);
			}
		}
	}
}
