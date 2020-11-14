package com.valkryst.VTerminal.plaf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

public class VCheckBoxUITest {
	public VCheckBoxUITest() throws UnsupportedLookAndFeelException {
		final var laf = VTerminalLookAndFeel.getInstance();
		UIManager.setLookAndFeel(laf);
	}

	@Test
	public void canGetMaximumSize() {
		final var component = new JCheckBox("Test");
		final var laf = VTerminalLookAndFeel.getInstance();
		final var expectedSize = new Dimension(laf.getTileWidth() * 5, laf.getTileHeight());
		Assertions.assertEquals(expectedSize, component.getMaximumSize());
	}

	@Test
	public void canGetMinimumSize() {
		final var component = new JCheckBox("Test");
		Assertions.assertEquals(component.getMaximumSize(), component.getMinimumSize());
	}

	@Test
	public void canGetPreferredSize() {
		final var component = new JCheckBox("Test");
		Assertions.assertEquals(component.getMaximumSize(), component.getPreferredSize());
	}
}
