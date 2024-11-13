package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/** A {@link JEditorPane} which uses the {@link VTerminalLookAndFeel} and its rendering hints. */
public class VEditorPane extends JEditorPane {
	/** See {@link JEditorPane#JEditorPane()}. */
	public VEditorPane() {
		super();
	}

	/** See {@link JEditorPane#JEditorPane(URL)}. */
	public VEditorPane(final URL initialPage) throws IOException {
		super(initialPage);
	}

	/** See {@link JEditorPane#JEditorPane(String)}. */
	public VEditorPane(final String url) throws IOException {
		super(url);
	}

	/** See {@link JEditorPane#JEditorPane(String, String)}. */
	public VEditorPane(final String type, final String text) {
		super(type, text);
	}

	@Override
	protected void paintComponent(final Graphics graphics) {
		super.paintComponent(VTerminalLookAndFeel.setRenderingHints(graphics));
	}
}
