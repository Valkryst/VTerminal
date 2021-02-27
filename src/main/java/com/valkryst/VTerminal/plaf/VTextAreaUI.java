package com.valkryst.VTerminal.plaf;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTextAreaUI;
import java.awt.*;

public class VTextAreaUI extends BasicTextAreaUI {
	/**
	 * Returns a new instance of {@code VTextAreaUI}.
	 *
	 * @param component A component.
	 *
	 * @return A new instance of {@code VTextAreaUI}
	 */
	public static ComponentUI createUI(final JComponent component) {
		VTerminalLookAndFeel.getInstance().setComponentFont(component);
		component.setInputMap(JComponent.WHEN_FOCUSED, (InputMap) UIManager.get("FormattedTextField.focusInputMap"));
		return new VTextAreaUI();
	}

	@Override
	public Dimension getMaximumSize(final JComponent component) {
		final var laf = VTerminalLookAndFeel.getInstance();
		final var textArea = (JTextArea) component;
		final int width = textArea.getColumns() * laf.getTileWidth();
		final int height = textArea.getRows() * laf.getTileHeight();
		return new Dimension(width, height);
	}

	@Override
	public Dimension getMinimumSize(final JComponent component) {
		return getMaximumSize(component);
	}

	@Override
	public Dimension getPreferredSize(final JComponent component) {
		return getMaximumSize(component);
	}
}
