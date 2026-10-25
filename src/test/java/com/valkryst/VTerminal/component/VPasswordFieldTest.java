package com.valkryst.VTerminal.component;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VPasswordFieldTest {
	@Test
	public void canCreateWithNoArgs() {
		Assertions.assertNotNull(new VPasswordField());
	}

	@Test
	public void canCreateWithText() {
		final var field = new VPasswordField("secret");
		Assertions.assertEquals("secret", new String(field.getPassword()));
	}

	@Test
	public void canCreateWithColumns() {
		final var field = new VPasswordField(10);
		Assertions.assertEquals(10, field.getColumns());
	}

	@Test
	public void canCreateWithTextAndColumns() {
		final var field = new VPasswordField("secret", 10);
		Assertions.assertEquals("secret", new String(field.getPassword()));
		Assertions.assertEquals(10, field.getColumns());
	}
}
