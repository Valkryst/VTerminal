package com.valkryst.VTerminal.plaf;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicToolTipUI;
import java.awt.*;

public class VToolTipUI extends BasicToolTipUI {
	/**
	 * Returns a new instance of {@code VToolTipUI}.
	 *
	 * @param component A component.
	 *
	 * @return A new instance of {@code VToolTipUI}
	 */
	public static ComponentUI createUI(final JComponent component) {
		VTerminalLookAndFeel.getInstance().setComponentFont(component);
		return new VToolTipUI();
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
