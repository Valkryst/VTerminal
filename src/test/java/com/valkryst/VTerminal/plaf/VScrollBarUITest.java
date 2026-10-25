package com.valkryst.VTerminal.plaf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class VScrollBarUITest {
	public VScrollBarUITest() throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(VTerminalLookAndFeel.getInstance());
	}

	@Test
	public void createDecreaseButtonReturnsVBasicArrowButton() {
		final var ui = new TestableVScrollBarUI();
		final var button = ui.testCreateDecreaseButton(SwingConstants.NORTH);
		Assertions.assertInstanceOf(VBasicArrowButton.class, button);
	}

	@Test
	public void createIncreaseButtonReturnsVBasicArrowButton() {
		final var ui = new TestableVScrollBarUI();
		final var button = ui.testCreateIncreaseButton(SwingConstants.SOUTH);
		Assertions.assertInstanceOf(VBasicArrowButton.class, button);
	}

	private static class TestableVScrollBarUI extends VScrollBarUI {
		public JButton testCreateDecreaseButton(final int orientation) {
			return createDecreaseButton(orientation);
		}

		public JButton testCreateIncreaseButton(final int orientation) {
			return createIncreaseButton(orientation);
		}
	}
}
