package com.valkryst.VTerminal.plaf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

public class VLabelUITest {
	public VLabelUITest() throws UnsupportedLookAndFeelException {
		final var laf = VTerminalLookAndFeel.getInstance();
		UIManager.setLookAndFeel(laf);
	}

	@Test
	public void canGetMaximumSize() {
		final var component = new JLabel("Test");
		final var laf = VTerminalLookAndFeel.getInstance();
		final var expectedSize = new Dimension(laf.getTileWidth() * 4, laf.getTileHeight());
		Assertions.assertEquals(expectedSize, component.getMaximumSize());
	}

	@Test
	public void canGetMinimumSize() {
		final var component = new JLabel("Test");
		Assertions.assertEquals(component.getMaximumSize(), component.getMinimumSize());
	}

	@Test
	public void canGetPreferredSize() {
		final var component = new JLabel("Test");
		Assertions.assertEquals(component.getMaximumSize(), component.getPreferredSize());
	}
}
