package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;

public class VTextField extends JTextField {
	public VTextField() {
		super();
	}

	public VTextField(final String text) {
		super(text);
	}

	public VTextField(final int columns) {
		super(columns);
	}

	public VTextField(final String text, final int columns) {
		super(text, columns);
	}

	public VTextField(final Document document, final String text, final int column) {
		super(document, text, column);
	}

	@Override
	protected void paintComponent(final Graphics graphics) {
		super.paintComponent(VTerminalLookAndFeel.setRenderingHints(graphics));
	}
}
