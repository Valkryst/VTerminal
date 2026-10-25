package com.valkryst.VTerminal.plaf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class VTabbedPaneUITest {
	public VTabbedPaneUITest() throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(VTerminalLookAndFeel.getInstance());
	}

	@Test
	public void canCreateUI() {
		final var component = new JTabbedPane();
		Assertions.assertNotNull(VTabbedPaneUI.createUI(component));
	}

	@Test
	public void createUISetsFont() {
		final var component = new JTabbedPane();
		VTabbedPaneUI.createUI(component);
		final var laf = VTerminalLookAndFeel.getInstance();
		Assertions.assertEquals(laf.vFont.getFont(), component.getFont());
	}
}
