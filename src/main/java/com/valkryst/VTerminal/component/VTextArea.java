package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;

import javax.swing.*;
import javax.swing.text.Document;
import java.awt.*;

public class VTextArea extends JTextArea {
	public VTextArea(final String text) {
		super(text);
	}

	public VTextArea(final int rows, final int columns) {
		super(rows, columns);
	}

	public VTextArea(final String text, final int rows, final int columns) {
		super(text, rows, columns);
	}

	public VTextArea(final Document document) {
		super(document);
	}

	public VTextArea(final Document document, final String text, final int rows, final int columns) {
		super(document, text, rows, columns);
	}

	@Override
	protected void paintComponent(final Graphics graphics) {
		super.paintComponent(VTerminalLookAndFeel.setRenderingHints(graphics));
	}
}
