package com.valkryst.VTerminal.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.text.PlainDocument;

public class VTextFieldTest {
	@Test
	public void canCreateWithNoArgs() {
		Assertions.assertNotNull(new VTextField());
	}

	@Test
	public void canCreateWithText() {
		final var field = new VTextField("Hello");
		Assertions.assertEquals("Hello", field.getText());
	}

	@Test
	public void canCreateWithColumns() {
		final var field = new VTextField(10);
		Assertions.assertEquals(10, field.getColumns());
	}

	@Test
	public void canCreateWithTextAndColumns() {
		final var field = new VTextField("Hello", 10);
		Assertions.assertEquals("Hello", field.getText());
		Assertions.assertEquals(10, field.getColumns());
	}

	@Test
	public void canCreateWithDocumentTextAndColumns() {
		final var doc = new PlainDocument();
		final var field = new VTextField(doc, "Hello", 10);
		Assertions.assertSame(doc, field.getDocument());
		Assertions.assertEquals(10, field.getColumns());
	}
}
