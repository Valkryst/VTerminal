package com.valkryst.VTerminal.plaf;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.Graphics;

public class VTabbedPaneUI extends BasicTabbedPaneUI {
	/** See {@link BasicTabbedPaneUI#createUI(JComponent)}. */
	public static ComponentUI createUI(final JComponent component) {
		VTerminalLookAndFeel.getInstance().setComponentFont(component);
		return new VTabbedPaneUI();
	}

	@Override
	public void paint(Graphics g, final JComponent c) {
		g = VTerminalLookAndFeel.resolveGraphics(g, c);
		super.paint(g, c);
	}
}
