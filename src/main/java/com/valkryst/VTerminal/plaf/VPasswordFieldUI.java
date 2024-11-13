package com.valkryst.VTerminal.plaf;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPasswordFieldUI;
import java.awt.*;

public class VPasswordFieldUI extends BasicPasswordFieldUI {
	/** See {@link BasicPasswordFieldUI#createUI(JComponent)}. */
	public static ComponentUI createUI(final JComponent component) {
		VTerminalLookAndFeel.getInstance().setComponentFont(component);
		component.setInputMap(JComponent.WHEN_FOCUSED, (InputMap) UIManager.get("FormattedTextField.focusInputMap"));

		final var color = UIManager.getColor("PasswordField.foreground");
		if (color != null) {
			UIManager.put("PasswordField.border", BorderFactory.createLineBorder(color));
		}

		return new VPasswordFieldUI();
	}

	@Override
	public Dimension getMaximumSize(final JComponent component) {
		final var laf = VTerminalLookAndFeel.getInstance();
		final var size = new Dimension(Integer.MAX_VALUE, laf.getTileHeight());
		return laf.clampDimensionToTileMultiples(size);
	}

	@Override
	public Dimension getMinimumSize(final JComponent component) {
		final var laf = VTerminalLookAndFeel.getInstance();
		return new Dimension(laf.getTileWidth(), laf.getTileHeight());
	}

	@Override
	public Dimension getPreferredSize(final JComponent component) {
		final var laf = VTerminalLookAndFeel.getInstance();
		return laf.clampDimensionToTileMultiples(super.getPreferredSize(component));
	}
}
