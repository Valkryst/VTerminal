package com.valkryst.VTerminal.plaf;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;

public class VProgressBarUI extends BasicProgressBarUI {
	/** See {@link BasicProgressBarUI#createUI(JComponent)}. */
	public static ComponentUI createUI(final JComponent component) {
		final var color = UIManager.getColor("ProgressBar.foreground");
		if (color != null) {
			UIManager.put("ProgressBar.border", BorderFactory.createLineBorder(color));
		}

		return new VProgressBarUI();
	}

	@Override
	public Dimension getMaximumSize(final JComponent component) {
		final var laf = VTerminalLookAndFeel.getInstance();
		return laf.clampDimensionToTileMultiples(super.getMaximumSize(component));
	}

	@Override
	public Dimension getMinimumSize(final JComponent component) {
		final var laf = VTerminalLookAndFeel.getInstance();
		return laf.clampDimensionToTileMultiples(super.getMinimumSize(component));
	}

	@Override
	public Dimension getPreferredSize(final JComponent component) {
		final var laf = VTerminalLookAndFeel.getInstance();
		return laf.clampDimensionToTileMultiples(super.getPreferredSize(component));
	}
}
