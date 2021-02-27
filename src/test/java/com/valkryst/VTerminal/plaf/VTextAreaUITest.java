package com.valkryst.VTerminal.plaf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class VTextAreaUITest {
	public VTextAreaUITest() throws UnsupportedLookAndFeelException {
		final var laf = VTerminalLookAndFeel.getInstance();
		UIManager.setLookAndFeel(laf);
	}

	@Test
	public void canGetMaximumSize() {
		final var component = new JTextArea(4, 3);

		final var laf = VTerminalLookAndFeel.getInstance();
		final var size = component.getMaximumSize();
		Assertions.assertEquals(4 * laf.getTileHeight(), size.getHeight());
		Assertions.assertEquals(3 * laf.getTileWidth(), size.getWidth());
	}

	@Test
	public void canGetMinimumSize() {
		final var component = new JTextArea(4, 3);
		Assertions.assertEquals(component.getMaximumSize(), component.getMinimumSize());
	}

	@Test
	public void canGetPreferredSize() {
		final var component = new JTextArea(4, 3);
		Assertions.assertEquals(component.getMaximumSize(), component.getPreferredSize());
	}
}
