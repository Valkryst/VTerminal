package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;

/** A {@link JTextArea} which uses the {@link VTerminalLookAndFeel} and its rendering hints. */
public class VTextArea extends JTextArea {
	/** See {@link JTextArea#JTextArea(String)}. */
	public VTextArea(final String text) {
		super(text);
	}

	/** See {@link JTextArea#JTextArea(int, int)}. */
	public VTextArea(final int rows, final int columns) {
		super(rows, columns);
	}

	/** See {@link JTextArea#JTextArea(String, int, int)}. */
	public VTextArea(final String text, final int rows, final int columns) {
		super(text, rows, columns);
	}

	/** See {@link JTextArea#JTextArea(Document)}. */
	public VTextArea(final Document document) {
		super(document);
	}

	/** See {@link JTextArea#JTextArea(Document, String, int, int)}. */
	public VTextArea(final Document document, final String text, final int rows, final int columns) {
		super(document, text, rows, columns);
	}

	@Override
	protected void paintComponent(final Graphics graphics) {
		super.paintComponent(VTerminalLookAndFeel.setRenderingHints(graphics));
	}
}
