package com.valkryst.VTerminal.plaf;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VButtonUI extends BasicButtonUI {
	/**
	 * Returns a new instance of {@code VButtonUI}.
	 *
	 * @param component A component.
	 *
	 * @return A new instance of {@code VButtonUI}
	 */
	public static ComponentUI createUI(final JComponent component) {
		component.setBorder(BorderFactory.createEmptyBorder());
		component.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(final KeyEvent e) {
				if (!component.hasFocus()) {
					return;
				}

				switch (e.getKeyCode()) {
					case KeyEvent.VK_ENTER, KeyEvent.VK_SPACE -> {
						((JButton) component).doClick();
					}
				}
			}
		});
		return new VButtonUI();
	}

	@Override
	public void paint(final Graphics graphics, final JComponent component) {
		final var laf = VTerminalLookAndFeel.getInstance();
		final var button = (JButton) component;
		final var model = button.getModel();
		var text = button.getText();

		var foregroundColor = button.isEnabled() ? button.getForeground() : UIManager.getColor("Button.disabledText");
		var backgroundColor = button.getBackground();

		if (model.isArmed() || model.isPressed()) {
			final var temp = foregroundColor;
			foregroundColor = backgroundColor;
			backgroundColor = temp;
		}

		graphics.setColor(backgroundColor);
		graphics.fillRect(0, 0, button.getWidth(), button.getHeight());

		if (text != null && !text.isEmpty() && !text.isBlank()) {
			final var tileWidth = laf.getTileWidth();

			for (int i = 0; i < text.length(); i++) {
				final var image = laf.generateImage(text.charAt(i), foregroundColor, null);
				graphics.drawImage(image, i * tileWidth, 0, null);
			}
		}

		// Draw Focus Border
		if (button.hasFocus() && button.isFocusPainted()) {
			graphics.setColor(UIManager.getColor("Button.select")); // todo Use Button.focus and update palette editor to use it as well.
			BasicGraphicsUtils.drawDashedRect(graphics, 0, 0, component.getWidth(), component.getHeight());
		}
	}

	@Override
	public Dimension getMaximumSize(final JComponent component) {
		final var laf = VTerminalLookAndFeel.getInstance();
		final int width = ((JButton) component).getText().length() * laf.getTileWidth();
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
