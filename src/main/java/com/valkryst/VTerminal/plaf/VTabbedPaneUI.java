package com.valkryst.VTerminal.plaf;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class VTabbedPaneUI extends BasicTabbedPaneUI {
	/** See {@link BasicTabbedPaneUI#createUI(JComponent)}. */
	public static ComponentUI createUI(final JComponent component) {
		VTerminalLookAndFeel.getInstance().setComponentFont(component);
		return new VTabbedPaneUI();
	}
}
