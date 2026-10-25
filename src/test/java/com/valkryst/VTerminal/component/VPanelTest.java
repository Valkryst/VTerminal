package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.image.SequentialOp;
import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

public class VPanelTest {
	public VPanelTest() throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(VTerminalLookAndFeel.getInstance());
	}
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
	public void canReset() throws NoSuchFieldException, IllegalAccessException {
		final var panel = new VPanel(2, 2);
		for (int y = 0 ; y < panel.getHeightInTiles() ; y++) {
			for (int x = 0 ; x < panel.getWidthInTiles() ; x++) {
				panel.setBackgroundAt(x, y, Color.MAGENTA);
				panel.setCodePointAt(x, y, '~');
				panel.setForegroundAt(x, y, Color.GREEN);
				panel.setSequentialImageOpAt(x, y, new SequentialOp());
			}
		}

		final var backgroundColorsField = panel.getClass().getDeclaredField("backgroundColors");
		backgroundColorsField.setAccessible(true);
		final var backgroundColors = (Color[][]) backgroundColorsField.get(panel);

		final var codePointsField = panel.getClass().getDeclaredField("codePoints");
		codePointsField.setAccessible(true);
		final var codePoints = (int[][]) codePointsField.get(panel);

		final var foregroundColorsField = panel.getClass().getDeclaredField("foregroundColors");
		foregroundColorsField.setAccessible(true);
		final var foregroundColors = (Color[][]) foregroundColorsField.get(panel);

		final var sequentialImageOpsField = panel.getClass().getDeclaredField("sequentialImageOps");
		sequentialImageOpsField.setAccessible(true);
		final var sequentialImageOps = (SequentialOp[][]) sequentialImageOpsField.get(panel);


		panel.reset();
		for (int y = 0 ; y < panel.getHeightInTiles() ; y++) {
			for (int x = 0 ; x < panel.getWidthInTiles() ; x++) {
				Assertions.assertNotEquals(Color.MAGENTA, backgroundColors[y][x]);
				Assertions.assertNotEquals('~', codePoints[y][x]);
				Assertions.assertNotEquals(Color.GREEN, foregroundColors[y][x]);
				Assertions.assertNull(sequentialImageOps[y][x]);
			}
		}
	}

	@Test
	public void canResetBackgroundColors() throws NoSuchFieldException, IllegalAccessException {
		final var panel = new VPanel(2, 2);
		for (int y = 0 ; y < panel.getHeightInTiles() ; y++) {
			for (int x = 0 ; x < panel.getWidthInTiles() ; x++) {
				panel.setBackgroundAt(x, y, Color.MAGENTA);
			}
		}

		final var backgroundColorsField = panel.getClass().getDeclaredField("backgroundColors");
		backgroundColorsField.setAccessible(true);
		final var backgroundColors = (Color[][]) backgroundColorsField.get(panel);


		panel.resetBackgroundColors();
		for (int y = 0 ; y < panel.getHeightInTiles() ; y++) {
			for (int x = 0 ; x < panel.getWidthInTiles() ; x++) {
				Assertions.assertNotEquals(Color.MAGENTA, backgroundColors[y][x]);
			}
		}
	}

	@Test
	public void canResetCodePoints() throws NoSuchFieldException, IllegalAccessException {
		final var panel = new VPanel(2, 2);
		for (int y = 0 ; y < panel.getHeightInTiles() ; y++) {
			for (int x = 0 ; x < panel.getWidthInTiles() ; x++) {
				panel.setCodePointAt(x, y, '~');
			}
		}

		final var codePointsField = panel.getClass().getDeclaredField("codePoints");
		codePointsField.setAccessible(true);
		final var codePoints = (int[][]) codePointsField.get(panel);


		panel.resetCodePoints();
		for (int y = 0 ; y < panel.getHeightInTiles() ; y++) {
			for (int x = 0 ; x < panel.getWidthInTiles() ; x++) {
				Assertions.assertNotEquals('~', codePoints[y][x]);
			}
		}
	}

	@Test
	public void canResetForegroundColors() throws NoSuchFieldException, IllegalAccessException {
		final var panel = new VPanel(2, 2);
		for (int y = 0 ; y < panel.getHeightInTiles() ; y++) {
			for (int x = 0 ; x < panel.getWidthInTiles() ; x++) {
				panel.setForegroundAt(x, y, Color.MAGENTA);
			}
		}

		final var foregroundColorsField = panel.getClass().getDeclaredField("foregroundColors");
		foregroundColorsField.setAccessible(true);
		final var foregroundColors = (Color[][]) foregroundColorsField.get(panel);


		panel.resetForegroundColors();
		for (int y = 0 ; y < panel.getHeightInTiles() ; y++) {
			for (int x = 0 ; x < panel.getWidthInTiles() ; x++) {
				Assertions.assertNotEquals(Color.MAGENTA, foregroundColors[y][x]);
			}
		}
	}

	@Test
	public void canResetSequentialImageOps() throws NoSuchFieldException, IllegalAccessException {
		final var panel = new VPanel(2, 2);
		for (int y = 0 ; y < panel.getHeightInTiles() ; y++) {
			for (int x = 0 ; x < panel.getWidthInTiles() ; x++) {
				panel.setSequentialImageOpAt(x, y, new SequentialOp());
			}
		}

		final var sequentialImageOpsField = panel.getClass().getDeclaredField("sequentialImageOps");
		sequentialImageOpsField.setAccessible(true);
		final var sequentialImageOps = (SequentialOp[][]) sequentialImageOpsField.get(panel);


		panel.resetSequentialImageOps();
		for (int y = 0 ; y < panel.getHeightInTiles() ; y++) {
			for (int x = 0 ; x < panel.getWidthInTiles() ; x++) {
				Assertions.assertNull(sequentialImageOps[y][x]);
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

	@Test
	public void canGetWidthInTiles() {
		final var panel = new VPanel(7, 3);
		Assertions.assertEquals(7, panel.getWidthInTiles());
	}

	@Test
	public void canGetHeightInTiles() {
		final var panel = new VPanel(7, 3);
		Assertions.assertEquals(3, panel.getHeightInTiles());
	}

	@Test
	public void canGetPreferredScrollableViewportSize() {
		final var panel = new VPanel(5, 4);
		final var laf = VTerminalLookAndFeel.getInstance();
		final var expected = new Dimension(5 * laf.getTileWidth(), 4 * laf.getTileHeight());
		Assertions.assertEquals(expected, panel.getPreferredScrollableViewportSize());
	}

	@Test
	public void canGetScrollableUnitIncrementHorizontal() {
		final var panel = new VPanel(2, 2);
		final var laf = VTerminalLookAndFeel.getInstance();
		Assertions.assertEquals(laf.getTileWidth(), panel.getScrollableUnitIncrement(new Rectangle(), SwingConstants.HORIZONTAL, 1));
	}

	@Test
	public void canGetScrollableUnitIncrementVertical() {
		final var panel = new VPanel(2, 2);
		final var laf = VTerminalLookAndFeel.getInstance();
		Assertions.assertEquals(laf.getTileHeight(), panel.getScrollableUnitIncrement(new Rectangle(), SwingConstants.VERTICAL, 1));
	}

	@Test
	public void canGetScrollableUnitIncrementWithUnknownOrientation() {
		final var panel = new VPanel(2, 2);
		Assertions.assertEquals(0, panel.getScrollableUnitIncrement(new Rectangle(), -1, 1));
	}

	@Test
	public void canGetScrollableBlockIncrement() {
		final var panel = new VPanel(2, 2);
		final var laf = VTerminalLookAndFeel.getInstance();
		Assertions.assertEquals(laf.getTileWidth(), panel.getScrollableBlockIncrement(new Rectangle(), SwingConstants.HORIZONTAL, 1));
	}

	@Test
	public void doesNotTrackViewportWidth() {
		Assertions.assertFalse(new VPanel(2, 2).getScrollableTracksViewportWidth());
	}

	@Test
	public void doesNotTrackViewportHeight() {
		Assertions.assertFalse(new VPanel(2, 2).getScrollableTracksViewportHeight());
	}
}
