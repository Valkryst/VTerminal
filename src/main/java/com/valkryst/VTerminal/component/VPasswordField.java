package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;

import javax.swing.*;
import java.awt.*;

/** A {@link JPasswordField} which uses the {@link VTerminalLookAndFeel} and its rendering hints. */
public class VPasswordField extends JPasswordField {
	/** See {@link JPasswordField#JPasswordField()}. */
	public VPasswordField() {
		super();
	}

	/** See {@link JPasswordField#JPasswordField(String)}. */
	public VPasswordField(final String text) {
		super(text);
	}

	/** See {@link JPasswordField#JPasswordField(int)}. */
	public VPasswordField(final int columns) {
		super(columns);
	}

	/** See {@link JPasswordField#JPasswordField(String, int)}. */
	public VPasswordField(final String text, final int columns) {
		super(text, columns);
	}

	@Override
	protected void paintComponent(final Graphics graphics) {
		super.paintComponent(VTerminalLookAndFeel.setRenderingHints(graphics));
	}
}
