package com.valkryst.VTerminal.plaf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.swing.*;

public class VBasicArrowButtonTest {
	public VBasicArrowButtonTest() throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(VTerminalLookAndFeel.getInstance());
	}

	@ParameterizedTest
	@ValueSource(ints = {SwingConstants.NORTH, SwingConstants.SOUTH, SwingConstants.EAST, SwingConstants.WEST})
	public void canCreateWithDirection(final int direction) {
		Assertions.assertNotNull(new VBasicArrowButton(direction));
	}

	@Test
	public void setsBackgroundFromUIManager() {
		final var button = new VBasicArrowButton(SwingConstants.NORTH);
		Assertions.assertEquals(UIManager.getColor("ScrollBar.background"), button.getBackground());
	}

	@Test
	public void setsForegroundFromUIManager() {
		final var button = new VBasicArrowButton(SwingConstants.NORTH);
		Assertions.assertEquals(UIManager.getColor("ScrollBar.foreground"), button.getForeground());
	}
}
