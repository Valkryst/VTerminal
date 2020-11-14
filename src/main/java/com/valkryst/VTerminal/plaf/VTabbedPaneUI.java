package com.valkryst.VTerminal.plaf;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class VTabbedPaneUI extends BasicTabbedPaneUI {
	/**
	 * Returns a new instance of {@code VTabbedPaneUI}.
	 *
	 * @param component A component.
	 *
	 * @return A new instance of {@code VTabbedPaneUI}
	 */
	public static ComponentUI createUI(final JComponent component) {
		VTerminalLookAndFeel.getInstance().setComponentFont(component);
		return new VTabbedPaneUI();
	}
}
