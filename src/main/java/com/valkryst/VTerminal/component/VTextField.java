package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;

/** A {@link JTextField} which uses the {@link VTerminalLookAndFeel} and its rendering hints. */
public class VTextField extends JTextField {
	/** See {@link JTextField#JTextField()}. */
	public VTextField() {
		super();
	}

	/** See {@link JTextField#JTextField(String)}. */
	public VTextField(final String text) {
		super(text);
	}

	/** See {@link JTextField#JTextField(int)}. */
	public VTextField(final int columns) {
		super(columns);
	}

	/** See {@link JTextField#JTextField(String, int)}. */
	public VTextField(final String text, final int columns) {
		super(text, columns);
	}

	/** See {@link JTextField#JTextField(Document, String, int)}. */
	public VTextField(final Document document, final String text, final int column) {
		super(document, text, column);
	}

	@Override
	protected void paintComponent(final Graphics graphics) {
		super.paintComponent(VTerminalLookAndFeel.setRenderingHints(graphics));
	}
}
