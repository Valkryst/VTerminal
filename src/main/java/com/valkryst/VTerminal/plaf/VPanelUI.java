package com.valkryst.VTerminal.plaf;

import com.valkryst.VTerminal.component.VPanel;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPanelUI;
import java.awt.*;

public class VPanelUI extends BasicPanelUI {
	/**
	 * Returns a new instance of {@code VPanelUI}.
	 *
	 * @param component A component.
	 *
	 * @return A new instance of {@code VPanelUI}
	 */
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
			final var laf = VTerminalLookAndFeel.getInstance();
			final var panel = (VPanel) component;
			final var height = laf.getTileHeight() * panel.getHeightInTiles();
			final var width = laf.getTileWidth() * panel.getWidthInTiles();
			return new Dimension(width, height);
		}

		return super.getPreferredSize(component);
	}
}
