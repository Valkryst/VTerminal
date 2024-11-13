package com.valkryst.VTerminal.plaf;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextPaneUI;
import java.awt.*;

public class VTextPaneUI extends BasicTextPaneUI {
	/** See {@link BasicTextPaneUI#createUI(JComponent)}. */
	public static ComponentUI createUI(final JComponent component) {
		VTerminalLookAndFeel.getInstance().setComponentFont(component);
		component.setInputMap(JComponent.WHEN_FOCUSED, (InputMap) UIManager.get("FormattedTextField.focusInputMap"));
		return new VTextPaneUI();
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
