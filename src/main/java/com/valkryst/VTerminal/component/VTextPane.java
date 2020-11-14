package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;

import javax.swing.*;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class VTextPane extends JTextPane {
	public VTextPane() {
		super();
	}

	public VTextPane(final StyledDocument document) {
		super(document);
	}

	@Override
	protected void paintComponent(final Graphics graphics) {
		super.paintComponent(VTerminalLookAndFeel.setRenderingHints(graphics));
	}
}
