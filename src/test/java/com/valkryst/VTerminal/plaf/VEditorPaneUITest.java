package com.valkryst.VTerminal.plaf;

import com.valkryst.VTerminal.component.VEditorPane;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class VEditorPaneUITest {
	public VEditorPaneUITest() throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(VTerminalLookAndFeel.getInstance());
	}

	@Test
	public void canGetMaximumSize() {
		final var component = new VEditorPane("text/plain", "Hello");

		final var laf = VTerminalLookAndFeel.getInstance();
		final var size = component.getMaximumSize();
		Assertions.assertEquals(0, size.width % laf.getTileWidth());
		Assertions.assertEquals(0, size.height % laf.getTileHeight());
	}

	@Test
	public void canGetMinimumSize() {
		final var component = new VEditorPane("text/plain", "Hello");

		final var laf = VTerminalLookAndFeel.getInstance();
		final var size = component.getMinimumSize();
		Assertions.assertEquals(0, size.width % laf.getTileWidth());
		Assertions.assertEquals(0, size.height % laf.getTileHeight());
	}

	@Test
	public void canGetPreferredSize() {
		final var component = new VEditorPane("text/plain", "Hello");

		final var laf = VTerminalLookAndFeel.getInstance();
		final var size = component.getPreferredSize();
		Assertions.assertEquals(0, size.width % laf.getTileWidth());
		Assertions.assertEquals(0, size.height % laf.getTileHeight());
	}
}
