package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;

import javax.swing.*;
import java.awt.*;

public class VPasswordField extends JPasswordField {
	public VPasswordField() {
		super();
	}

	public VPasswordField(final String text) {
		super(text);
	}

	public VPasswordField(final int columns) {
		super(columns);
	}

	public VPasswordField(final String text, final int columns) {
		super(text, columns);
	}

	@Override
	protected void paintComponent(final Graphics graphics) {
		super.paintComponent(VTerminalLookAndFeel.setRenderingHints(graphics));
	}
}
