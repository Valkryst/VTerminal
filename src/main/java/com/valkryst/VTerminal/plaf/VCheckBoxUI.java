package com.valkryst.VTerminal.plaf;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicCheckBoxUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VCheckBoxUI extends BasicCheckBoxUI {
	/**
	 * Returns a new instance of {@code VCheckBoxUI}.
	 *
	 * @param component A component.
	 *
	 * @return A new instance of {@code VCheckBoxUI}
	 */
	public static ComponentUI createUI(final JComponent component) {
		component.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(final KeyEvent e) {
			if (!component.hasFocus()) {
				return;
			}

			switch (e.getKeyCode()) {
				case KeyEvent.VK_ENTER:
				case KeyEvent.VK_SPACE: {
					final var checkBox = (JCheckBox) component;
					checkBox.setSelected(!checkBox.isSelected());
					break;
				}
			}
			}
		});
		return new VCheckBoxUI();
	}

	@Override
	public synchronized void paint(final Graphics graphics, final JComponent component) {
		final var laf = VTerminalLookAndFeel.getInstance();
		final var button = (JCheckBox) component;
		final var model = button.getModel();
		final var text = button.getText();

		var foregroundColor = (button.isEnabled() ? button.getForeground() : UIManager.getColor("CheckBox.disabledText"));
		var backgroundColor = button.getBackground();

		if (model.isArmed() || model.isPressed()) {
			final var temp = foregroundColor;
			foregroundColor = backgroundColor;
			backgroundColor = temp;
		}

		graphics.setColor(backgroundColor);
		graphics.fillRect(0, 0, button.getWidth(), button.getHeight());

		// Draw Checked/Unchecked Symbol
		final int tileWidth = laf.getTileWidth();
		final int tileHeight = laf.getTileHeight();

		final int x = 1;
		final int y = (int) (tileHeight / 4.0);
		final int width = tileWidth - (x * 2);
		final int height = (int) (tileHeight / 2.0);

		graphics.setColor(foregroundColor);
		graphics.drawRect(x, y, width, height);

		if (button.isSelected()) {
			graphics.fillRect(x + 2, y + 2, width - 3, height - 3);
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
			graphics.setColor(UIManager.getColor("CheckBox.focus"));
			BasicGraphicsUtils.drawDashedRect(graphics, 0, 0, component.getWidth(), component.getHeight());
		}
	}

	@Override
	public Dimension getMaximumSize(final JComponent component) {
		final var laf = VTerminalLookAndFeel.getInstance();
		int width = ((JCheckBox) component).getText().length() * laf.getTileWidth();
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
