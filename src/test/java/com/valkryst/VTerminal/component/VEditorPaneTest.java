package com.valkryst.VTerminal.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VEditorPaneTest {
	@Test
	public void canCreateWithNoArgs() {
		Assertions.assertNotNull(new VEditorPane());
	}

	@Test
	public void canCreateWithTypeAndText() {
		final var pane = new VEditorPane("text/plain", "Hello, World!");
		Assertions.assertNotNull(pane);
		Assertions.assertEquals("Hello, World!", pane.getText());
	}

	@Test
	public void canCreateWithHtmlTypeAndText() {
		final var pane = new VEditorPane("text/html", "<b>bold</b>");
		Assertions.assertNotNull(pane);
	}
}
