package com.valkryst.VTerminal.plaf;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicRadioButtonUI;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VRadioButtonUI extends BasicRadioButtonUI {
	/**
	 * Returns a new instance of {@code VRadioButtonUI}.
	 *
	 * @param component A component.
	 *
	 * @return A new instance of {@code VRadioButtonUI}
	 */
	public static ComponentUI createUI(final JComponent component) {
		component.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(final KeyEvent e) {
			if (!component.hasFocus()) {
				return;
			}

			switch (e.getKeyCode()) {
				case KeyEvent.VK_ENTER, KeyEvent.VK_SPACE -> {
					final var checkBox = (JRadioButton) component;
					checkBox.setSelected(!checkBox.isSelected());
				}
			}
			}
		});
		return new VRadioButtonUI();
	}

	@Override
	public synchronized void paint(final Graphics graphics, final JComponent component) {
		final var laf = VTerminalLookAndFeel.getInstance();
		final var button = (JRadioButton) component;
		final var model = button.getModel();
		final var text = button.getText();

		var foregroundColor = (button.isEnabled() ? button.getForeground() : UIManager.getColor("RadioButton.disabledText"));
		var backgroundColor = button.getBackground();

		if (model.isArmed() || model.isPressed()) {
			final var temp = foregroundColor;
			foregroundColor = backgroundColor;
			backgroundColor = temp;
		}

		graphics.setColor(backgroundColor);
		graphics.fillRect(0, 0, button.getWidth(), button.getHeight());

		// Draw Checked/Unchecked Symbol
		// todo Improve symbol drawing code to look better with high (i.e. 32) font point sizes.
		final int tileWidth = laf.getTileWidth();
		final int tileHeight = laf.getTileHeight();

		final int x = 1;
		final int y = (int) (tileHeight / 4.0);
		final int diameter = (int) ((tileWidth - (x * 2) / 2.0));

		graphics.setColor(foregroundColor);
		graphics.drawOval(x, y, diameter, diameter);

		if (button.isSelected()) {
			graphics.fillOval(x + 1, y + 1, diameter - 2, diameter - 2);
		}

		// Draw Text
		if (text != null && !text.isEmpty() && !text.isBlank()) {
			for (int i = 0; i < text.length(); i++) {
				final var image = laf.generateImage(text.charAt(i), foregroundColor, null);

				// Account for the custom selection character by adding to x.
				graphics.drawImage(image, (i + 1) * tileWidth, 0, null);
			}
		}

		// Draw Focus Border
		if (button.hasFocus() && button.isFocusPainted()) {
			graphics.setColor(UIManager.getColor("RadioButton.focus"));
			BasicGraphicsUtils.drawDashedRect(graphics, 0, 0, component.getWidth(), component.getHeight());
		}
	}

	@Override
	public Dimension getMaximumSize(final JComponent component) {
		final var laf = VTerminalLookAndFeel.getInstance();
		int width = ((JRadioButton) component).getText().length() * laf.getTileWidth();
		width += laf.getTileWidth(); // Account for the custom selection character.
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
