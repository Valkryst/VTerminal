package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.image.SequentialOp;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
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
			new VPanel(0, 1);
		});
	}

	@Test
	public void cannotCreatePanelWithNonPositiveHeight() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new VPanel(1, 0);
		});
	}

	@Test
	public void canSetBackgroundColor() throws NoSuchFieldException, IllegalAccessException {
		final var panel = new VPanel(2, 2);
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
	public void canSetBackgroundColorWithNullColor() throws NoSuchFieldException, IllegalAccessException {
		final var panel = new VPanel(2, 2);
		panel.setBackground(Color.MAGENTA);

		final var backgroundColorsField = panel.getClass().getDeclaredField("backgroundColors");
		backgroundColorsField.setAccessible(true);
		final var backgroundColors = (Color[][]) backgroundColorsField.get(panel);

		panel.setBackground(null);
		final var defaultColor = UIManager.getColor("Panel.background");
		for (int y = 0 ; y < panel.getHeightInTiles() ; y ++) {
			for (int x = 0 ; x < panel.getWidthInTiles() ; x++) {
				Assertions.assertEquals(defaultColor, backgroundColors[y][x]);

			}
		}
	}

	@Test
	public void canSetBackgroundColorAtLocation() throws NoSuchFieldException, IllegalAccessException {
		final var panel = new VPanel(1, 1);
		panel.setBackgroundAt(0, 0, Color.MAGENTA);

		final var backgroundColorsField = panel.getClass().getDeclaredField("backgroundColors");
		backgroundColorsField.setAccessible(true);
		final var backgroundColors = (Color[][]) backgroundColorsField.get(panel);

		Assertions.assertEquals(Color.MAGENTA, backgroundColors[0][0]);
	}

	@Test
	public void canSetBackgroundColorAtLocationWithNullColor() throws NoSuchFieldException, IllegalAccessException {
		final var panel = new VPanel(1, 1);
		panel.setBackgroundAt(0, 0, Color.MAGENTA);

		final var backgroundColorsField = panel.getClass().getDeclaredField("backgroundColors");
		backgroundColorsField.setAccessible(true);
		final var backgroundColors = (Color[][]) backgroundColorsField.get(panel);

		panel.setBackgroundAt(0, 0, null);
		Assertions.assertEquals(UIManager.getColor("Panel.background"), backgroundColors[0][0]);
	}

	@Test
	public void canSetBackgroundColorAtLocationWithOutOfBoundsLocation() {
		Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			new VPanel(1, 1).setBackgroundAt(-1, -1, Color.MAGENTA);
		});
	}

	@Test
	public void canSetCodePointAtLocation() throws NoSuchFieldException, IllegalAccessException {
		final var panel = new VPanel(1, 1);
		panel.setCodePointAt(0, 0, '~');

		final var codePointsField = panel.getClass().getDeclaredField("codePoints");
		codePointsField.setAccessible(true);
		final var codePoints = (int[][]) codePointsField.get(panel);

		Assertions.assertEquals('~', codePoints[0][0]);
	}

	@Test
	public void canSetCodePointAtOutOfBoundsLocation() {
		Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			new VPanel(1, 1).setCodePointAt(-1, -1, '~');
		});
	}

	@Test
	public void canSetForegroundColor() throws NoSuchFieldException, IllegalAccessException {
		final var panel = new VPanel(2, 2);
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

	@Test
	public void canSetForegroundColorWithNullColor() throws NoSuchFieldException, IllegalAccessException {
		final var panel = new VPanel(2, 2);
		panel.setForeground(Color.MAGENTA);

		final var foregroundColorsField = panel.getClass().getDeclaredField("foregroundColors");
		foregroundColorsField.setAccessible(true);
		final var foregroundColors = (Color[][]) foregroundColorsField.get(panel);

		panel.setForeground(null);
		final var defaultColor = UIManager.getColor("Panel.foreground");
		for (int y = 0 ; y < panel.getHeightInTiles() ; y ++) {
			for (int x = 0 ; x < panel.getWidthInTiles() ; x++) {
				Assertions.assertEquals(defaultColor, foregroundColors[y][x]);

			}
		}
	}

	@Test
	public void canSetForegroundColorAtLocation() throws NoSuchFieldException, IllegalAccessException {
		final var panel = new VPanel(1, 1);
		panel.setForegroundAt(0, 0, Color.MAGENTA);

		final var foregroundColorsField = panel.getClass().getDeclaredField("foregroundColors");
		foregroundColorsField.setAccessible(true);
		final var foregroundColors = (Color[][]) foregroundColorsField.get(panel);

		Assertions.assertEquals(Color.MAGENTA, foregroundColors[0][0]);
	}

	@Test
	public void canSetForegroundColorAtLocationWithNullColor() throws NoSuchFieldException, IllegalAccessException {
		final var panel = new VPanel(1, 1);
		panel.setForegroundAt(0, 0, Color.MAGENTA);

		final var foregroundColorsField = panel.getClass().getDeclaredField("foregroundColors");
		foregroundColorsField.setAccessible(true);
		final var foregroundColors = (Color[][]) foregroundColorsField.get(panel);

		panel.setForegroundAt(0, 0, null);
		Assertions.assertEquals(UIManager.getColor("Panel.foreground"), foregroundColors[0][0]);
	}

	@Test
	public void canSetForegroundColorAtLocationWithOutOfBoundsLocation() {
		Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
			new VPanel(1, 1).setForegroundAt(-1, -1, Color.MAGENTA);
		});
	}
}
