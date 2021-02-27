package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class VEditorPane extends JEditorPane {
	public VEditorPane() {
		super();
	}

	public VEditorPane(final URL initialPage) throws IOException {
		super(initialPage);
	}

	public VEditorPane(final String url) throws IOException {
		super(url);
	}

	public VEditorPane(final String type, final String text) {
		super(type, text);
	}

	@Override
	protected void paintComponent(final Graphics graphics) {
		super.paintComponent(VTerminalLookAndFeel.setRenderingHints(graphics));
	}
}
