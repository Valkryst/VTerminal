package com.valkryst.VTerminal.plaf;

import javax.swing.*;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.*;

public class VBasicArrowButton extends BasicArrowButton {
	/**
	 * Constructs a {@link VBasicArrowButton}.
	 *
	 * @param direction See {@link BasicArrowButton#BasicArrowButton(int)}.
	 */
	public VBasicArrowButton(final int direction) {
		super(direction);
		super.setBackground(UIManager.getColor("ScrollBar.background"));
		super.setForeground(UIManager.getColor("ScrollBar.foreground"));
	}

	@Override
	public void paint(final Graphics graphics) {
		int width = super.getWidth();
		int height = super.getHeight();

		final var isPressed = super.getModel().isPressed();

		graphics.setColor(UIManager.getColor("ScrollBar.track"));
		graphics.fillRect(0, 0, super.getWidth(), super.getHeight());

		graphics.setColor(isPressed ? super.getForeground() : super.getBackground());
		switch (super.getDirection()) {
			case SwingConstants.NORTH: {
				graphics.fillRect(0, 0, width, height - 2);
				graphics.setColor(super.getForeground());
				graphics.drawLine(0, 0, 0, height);
				graphics.drawLine(1, height - 2, width - 1, height - 2);
				break;
			}
			case SwingConstants.SOUTH: {
				graphics.fillRect(0, 2, width, height - 2);
				graphics.setColor(super.getForeground());
				graphics.drawLine(0, 0, 0, height);
				graphics.drawLine(1, 1, width - 1, 1);
				graphics.drawLine(1, height - 1, width - 1, height - 1);
				break;
			}
			case SwingConstants.WEST: {
				graphics.fillRect(0, 0, width - 2, height);
				graphics.setColor(super.getForeground());
				graphics.drawLine(0, 0, width, 0);
				graphics.drawLine(width - 2, 0, width - 2, height - 1);
				break;
			}
			case SwingConstants.EAST: {
				graphics.fillRect(2, 0, width, height);
				graphics.setColor(super.getForeground());
				graphics.drawLine(0, 0, width, 0);
				graphics.drawLine(1, 0, 1, height - 1);
				graphics.drawLine(width - 1, 0, width - 1, height - 1);
				break;
			}
		}

		// If there's no room to draw arrow, bail
		if (height < 5 || width < 5) {
			return;
		}

		if (isPressed) {
			graphics.translate(1, 1);
		}

		// Draw the arrow
		int size = Math.min((height - 4) / 3, (width - 4) / 3);
		size = Math.max(size, 2);
		paintTriangle(graphics, (width - size) / 2, (height - size) / 2,
				size, direction, super.isEnabled());

		// Reset the Graphics back to its original settings
		if (isPressed) {
			graphics.translate(-1, -1);
		}
	}

	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}

	@Override
	public Dimension getPreferredSize() {
		final var laf = VTerminalLookAndFeel.getInstance();

		switch (super.getDirection()) {
			case SwingConstants.EAST:
			case SwingConstants.WEST: {
				return new Dimension(laf.getTileWidth() * 2, laf.getTileHeight());
			}
			default: {
				return new Dimension(laf.getTileWidth(), laf.getTileHeight());
			}
		}
	}
}
