package com.valkryst.VTerminal.plaf;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicScrollPaneUI;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VScrollPaneUI extends BasicScrollPaneUI {
	/** See {@link BasicScrollPaneUI#createUI(JComponent)}. */
	public static ComponentUI createUI(final JComponent component) {
		final var scrollPane = (JScrollPane) component;
		final var horizontalScrollBar = scrollPane.getHorizontalScrollBar();
		final var verticalScrollBar = scrollPane.getVerticalScrollBar();

		horizontalScrollBar.setFocusable(false);
		verticalScrollBar.setFocusable(false);

		scrollPane.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(final KeyEvent e) {
			if (!scrollPane.hasFocus()) {
				return;
			}

			switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
				case KeyEvent.VK_HOME: {
					horizontalScrollBar.setValue(horizontalScrollBar.getValue() - horizontalScrollBar.getUnitIncrement());
					break;
				}
				case KeyEvent.VK_RIGHT:
				case KeyEvent.VK_END: {
					horizontalScrollBar.setValue(horizontalScrollBar.getValue() + horizontalScrollBar.getUnitIncrement());
					break;
				}
			}
			}
		});

		final var color = UIManager.getColor("ScrollPane.foreground");
		if (color != null) {
			UIManager.put("ScrollPane.border", BorderFactory.createLineBorder(color));
		}

		final var laf = VTerminalLookAndFeel.getInstance();
		horizontalScrollBar.setUnitIncrement(laf.getTileWidth());
		verticalScrollBar.setUnitIncrement(laf.getTileHeight());

		return new VScrollPaneUI();
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
