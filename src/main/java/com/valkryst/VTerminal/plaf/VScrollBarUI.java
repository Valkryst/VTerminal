package com.valkryst.VTerminal.plaf;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;

public class VScrollBarUI extends BasicScrollBarUI {
	/** See {@link BasicScrollBarUI#createUI(JComponent)}. */
	public static ComponentUI createUI(final JComponent component) {
		component.setBorder(BorderFactory.createEmptyBorder());
		component.setInputMap(JComponent.WHEN_FOCUSED, (InputMap) UIManager.get("ScrollBar.ancestorInputMap"));
		return new VScrollBarUI();
	}

	@Override
	protected void paintThumb(final Graphics graphics, final JComponent component, final Rectangle thumbBounds) {
		if (thumbBounds.isEmpty() || !component.isEnabled()) {
			return;
		}

		final var graphics2D = (Graphics2D) graphics.create();
		graphics2D.setColor(super.scrollbar.getForeground());
		graphics2D.drawRect(0, 0, super.scrollbar.getWidth(), super.scrollbar.getHeight());

		/*
		 * In order to draw the thumb without overlapping the borders of the
		 * scroll bar, we need to determine whether the thumb is for scrolling
		 * from North to South or from West to East.
		 *
		 * This does not cover the case where the width and height are equal,
		 * because the width/height are based on the tile width/height from the
		 * VFont loaded into the VTerminalLookAndFeel.
		 *
		 * It is possible for the tile width/height to be equal and this code
		 * will eventually need to be updated.
		 *
		 * todo Test and update code for the case where the width/height are
		 *      equal.
		 */
		graphics2D.setColor(super.thumbColor);

		if (thumbBounds.width > thumbBounds.height) {
			graphics2D.translate(thumbBounds.x, thumbBounds.y + 1);
		} else {
			graphics2D.translate(thumbBounds.x + 1, thumbBounds.y);
		}
		graphics2D.fillRect(0, 0, thumbBounds.width, thumbBounds.height);

		graphics2D.dispose();
	}

	@Override
	protected JButton createDecreaseButton(final int orientation) {
		return new VBasicArrowButton(orientation);
	}

	@Override
	protected JButton createIncreaseButton(final int orientation) {
		return new VBasicArrowButton(orientation);
	}
}
