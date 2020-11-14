package com.valkryst.VTerminal.plaf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

public class VPasswordFieldUITest {
	public VPasswordFieldUITest() throws UnsupportedLookAndFeelException {
		final var laf = VTerminalLookAndFeel.getInstance();
		UIManager.setLookAndFeel(laf);
	}

	@Test
	public void canGetMaximumSize() {
		final var component = new JPasswordField();

		final var laf = VTerminalLookAndFeel.getInstance();
		final var expectedHeight = laf.getTileHeight();
		final var expectedSize = new Dimension(2147483640, expectedHeight);
		Assertions.assertEquals(expectedSize, component.getMaximumSize());
	}

	@Test
	public void canGetMinimumSize() {
		final var component = new JPasswordField();

		final var laf = VTerminalLookAndFeel.getInstance();
		final var expectedWidth = laf.getTileWidth();
		final var expectedHeight = laf.getTileHeight();
		final var expectedSize = new Dimension(expectedWidth, expectedHeight);
		Assertions.assertEquals(expectedSize, component.getMinimumSize());
	}

	@Test
	public void canGetPreferredSize() {
		final var component = new JPasswordField();
		final var laf = VTerminalLookAndFeel.getInstance();
		final var expectedSize = new Dimension(laf.getTileWidth(), laf.getTileHeight());
		Assertions.assertEquals(expectedSize, component.getPreferredSize());
	}
}
