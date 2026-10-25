package com.valkryst.VTerminal.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.text.DefaultStyledDocument;

public class VTextPaneTest {
	@Test
	public void canCreateWithNoArgs() {
		Assertions.assertNotNull(new VTextPane());
	}

	@Test
	public void canCreateWithDocument() {
		final var doc = new DefaultStyledDocument();
		final var pane = new VTextPane(doc);
		Assertions.assertSame(doc, pane.getStyledDocument());
	}
}
