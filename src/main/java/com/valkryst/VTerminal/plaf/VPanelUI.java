package com.valkryst.VTerminal.plaf;

import com.valkryst.VTerminal.component.VPanel;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;

public class VPanelUI extends BasicPanelUI {
	/** See {@link BasicPanelUI#createUI(JComponent)}. */
	public static ComponentUI createUI(final JComponent component) {
		return new VPanelUI();
	}

	@Override
	public Dimension getMaximumSize(final JComponent component) {
		if (component instanceof VPanel) {
			return getPreferredSize(component);
		}

		return super.getMaximumSize(component);
	}

	@Override
	public Dimension getMinimumSize(final JComponent component) {
		if (component instanceof VPanel) {
			return getPreferredSize(component);
		}

		return super.getMinimumSize(component);
	}

	@Override
	public Dimension getPreferredSize(final JComponent component) {
		if (component instanceof VPanel) {
			final var panel = (VPanel) component;
			final var laf = VTerminalLookAndFeel.getInstance();
			final var height = laf.getTileHeight() * panel.getHeightInTiles();
			final var width = laf.getTileWidth() * panel.getWidthInTiles();
			return new Dimension(width, height);
		}

		return super.getPreferredSize(component);
	}
}
