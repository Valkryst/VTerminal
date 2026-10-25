package com.valkryst.VTerminal.plaf;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;

public class VScrollPaneUITest {
	public VScrollPaneUITest() throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(VTerminalLookAndFeel.getInstance());
	}

	@Test
	public void createUISetsHorizontalScrollBarNotFocusable() {
		final var scrollPane = new JScrollPane();
		VScrollPaneUI.createUI(scrollPane);
		Assertions.assertFalse(scrollPane.getHorizontalScrollBar().isFocusable());
	}

	@Test
	public void createUISetsVerticalScrollBarNotFocusable() {
		final var scrollPane = new JScrollPane();
		VScrollPaneUI.createUI(scrollPane);
		Assertions.assertFalse(scrollPane.getVerticalScrollBar().isFocusable());
	}

	@Test
	public void createUISetsHorizontalScrollBarUnitIncrement() {
		final var scrollPane = new JScrollPane();
		VScrollPaneUI.createUI(scrollPane);
		final var laf = VTerminalLookAndFeel.getInstance();
		Assertions.assertEquals(laf.getTileWidth(), scrollPane.getHorizontalScrollBar().getUnitIncrement());
	}

	@Test
	public void createUISetsVerticalScrollBarUnitIncrement() {
		final var scrollPane = new JScrollPane();
		VScrollPaneUI.createUI(scrollPane);
		final var laf = VTerminalLookAndFeel.getInstance();
		Assertions.assertEquals(laf.getTileHeight(), scrollPane.getVerticalScrollBar().getUnitIncrement());
	}
}
