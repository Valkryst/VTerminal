package com.valkryst.VTerminal.plaf;

import com.valkryst.VTerminal.component.VPanel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

public class VPanelUITest {
	public VPanelUITest() throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(VTerminalLookAndFeel.getInstance());
	}

	@Test
	public void canGetMaximumSizeForVPanel() {
		final var panel = new VPanel(5, 3);

		final var laf = VTerminalLookAndFeel.getInstance();
		final var expected = new Dimension(5 * laf.getTileWidth(), 3 * laf.getTileHeight());
		Assertions.assertEquals(expected, panel.getMaximumSize());
	}

	@Test
	public void canGetMinimumSizeForVPanel() {
		final var panel = new VPanel(5, 3);

		final var laf = VTerminalLookAndFeel.getInstance();
		final var expected = new Dimension(5 * laf.getTileWidth(), 3 * laf.getTileHeight());
		Assertions.assertEquals(expected, panel.getMinimumSize());
	}

	@Test
	public void canGetPreferredSizeForVPanel() {
		final var panel = new VPanel(5, 3);

		final var laf = VTerminalLookAndFeel.getInstance();
		final var expected = new Dimension(5 * laf.getTileWidth(), 3 * laf.getTileHeight());
		Assertions.assertEquals(expected, panel.getPreferredSize());
	}

}
