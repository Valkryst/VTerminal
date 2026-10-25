package com.valkryst.VTerminal.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.text.PlainDocument;

public class VTextAreaTest {
	@Test
	public void canCreateWithText() {
		final var area = new VTextArea("Hello");
		Assertions.assertEquals("Hello", area.getText());
	}

	@Test
	public void canCreateWithRowsAndColumns() {
		final var area = new VTextArea(4, 10);
		Assertions.assertEquals(4, area.getRows());
		Assertions.assertEquals(10, area.getColumns());
	}

	@Test
	public void canCreateWithTextRowsAndColumns() {
		final var area = new VTextArea("Hello", 4, 10);
		Assertions.assertEquals("Hello", area.getText());
		Assertions.assertEquals(4, area.getRows());
		Assertions.assertEquals(10, area.getColumns());
	}

	@Test
	public void canCreateWithDocument() {
		final var doc = new PlainDocument();
		final var area = new VTextArea(doc);
		Assertions.assertSame(doc, area.getDocument());
	}

	@Test
	public void canCreateWithDocumentTextRowsAndColumns() {
		final var doc = new PlainDocument();
		final var area = new VTextArea(doc, "Hello", 4, 10);
		Assertions.assertSame(doc, area.getDocument());
		Assertions.assertEquals(4, area.getRows());
		Assertions.assertEquals(10, area.getColumns());
	}
}
