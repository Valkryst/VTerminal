package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;

import javax.swing.*;
import javax.swing.text.StyledDocument;
import java.awt.*;

/** A {@link JTextPane} which uses the {@link VTerminalLookAndFeel} and its rendering hints. */
public class VTextPane extends JTextPane {
	/** See {@link JTextPane#JTextPane()}. */
	public VTextPane() {
		super();
	}

	/** See {@link JTextPane#JTextPane(StyledDocument)}. */
	public VTextPane(final StyledDocument document) {
		super(document);
	}

	@Override
	protected void paintComponent(final Graphics graphics) {
		super.paintComponent(VTerminalLookAndFeel.setRenderingHints(graphics));
	}
}
