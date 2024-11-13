package com.valkryst.VTerminal.plaf;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicLabelUI;
import java.awt.*;

public class VLabelUI extends BasicLabelUI {
	/** See {@link BasicLabelUI#createUI(JComponent)}. */
	public static ComponentUI createUI(final JComponent component) {
		return new VLabelUI();
	}

	@Override
	public void paint(final Graphics graphics, final JComponent component) {
		final var laf = VTerminalLookAndFeel.getInstance();
		final var label = (JLabel) component;
		final var text = label.getText();

		graphics.setColor(label.getBackground());
		graphics.fillRect(0, 0, label.getWidth(), label.getHeight());

		if (text != null && !text.isBlank()) {
			final var foregroundColor = label.isEnabled() ? label.getForeground() : UIManager.getColor("Label.disabledForeground");

			final var tileWidth = laf.getTileWidth();

			for (int i = 0; i < text.length(); i++) {
				final var image = laf.generateImage(text.charAt(i), foregroundColor, null);
				graphics.drawImage(image, i * tileWidth, 0, null);
			}
		}
	}

	@Override
	protected void paintDisabledText(final JLabel l, final Graphics g, final String s, final int textX, final int textY) {
	}

	@Override
	protected void paintEnabledText(final JLabel l, final Graphics g, final String s, final int textX, final int textY) {
	}

	@Override
	public Dimension getMaximumSize(final JComponent component) {
		final var laf = VTerminalLookAndFeel.getInstance();
		final int width = ((JLabel) component).getText().length() * laf.getTileWidth();
		return new Dimension(width, laf.getTileHeight());
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
