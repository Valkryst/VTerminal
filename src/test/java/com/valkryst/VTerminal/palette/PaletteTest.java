package com.valkryst.VTerminal.palette;

import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

public class PaletteTest {
	public Path createPropertiesFile(final FileSystem fileSystem) throws IOException {
		final var path = fileSystem.getPath("test.properties");

		try (
			final var fileOutputStream = Files.newOutputStream(path);
			final var printWriter = new PrintWriter(fileOutputStream);
		) {
			printWriter.write("ColorA=#AABBCC\n");
			printWriter.write("ColorB=#BBCCDD\n");
			printWriter.write("ColorC=#CCDDEE\n");
			printWriter.flush();
		}

		return path;
	}

	@Test
	public void canLoadAndRegisterProperties() throws IOException {
		final var fileSystem = Jimfs.newFileSystem(Configuration.windows());
		final var path = createPropertiesFile(fileSystem);
		Palette.loadAndRegisterProperties(Files.newInputStream(path));

		Assertions.assertEquals(new Color(170, 187, 204), UIManager.getColor("ColorA"));
		Assertions.assertEquals(new Color(187, 204, 221), UIManager.getColor("ColorB"));
		Assertions.assertEquals(new Color(204, 221, 238), UIManager.getColor("ColorC"));

		fileSystem.close();
	}

	@Test
	public void cannotLoadAndRegisterPropertiesFromANullInputStream() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			Palette.loadAndRegisterProperties(null);
		});
	}

	@Test
	public void canLoadProperties() throws IOException {
		final var fileSystem = Jimfs.newFileSystem(Configuration.windows());
		final var path = createPropertiesFile(fileSystem);
		final var properties = Palette.loadProperties(Files.newInputStream(path));

		Assertions.assertEquals(3, properties.entrySet().size());
		Assertions.assertTrue(properties.containsKey("ColorA"));
		Assertions.assertTrue(properties.containsKey("ColorB"));
		Assertions.assertTrue(properties.containsKey("ColorC"));
		Assertions.assertEquals("#AABBCC", properties.getProperty("ColorA"));
		Assertions.assertEquals("#BBCCDD", properties.getProperty("ColorB"));
		Assertions.assertEquals("#CCDDEE", properties.getProperty("ColorC"));

		fileSystem.close();
	}

	@Test
	public void cannotLoadPropertiesFromANullInputStream() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			Palette.loadProperties(null);
		});
	}

	@Test
	public void canRegisterProperties() throws IOException {
		final var fileSystem = Jimfs.newFileSystem(Configuration.windows());
		final var path = createPropertiesFile(fileSystem);
		final var properties = Palette.loadProperties(Files.newInputStream(path));
		Palette.registerProperties(properties);

		Assertions.assertEquals(new Color(170, 187, 204), UIManager.getColor("ColorA"));
		Assertions.assertEquals(new Color(187, 204, 221), UIManager.getColor("ColorB"));
		Assertions.assertEquals(new Color(204, 221, 238), UIManager.getColor("ColorC"));

		fileSystem.close();
	}

	@Test
	public void cannotRegisterNullProperties() {
		Assertions.assertThrows(NullPointerException.class, () -> {
			Palette.registerProperties(null);
		});
	}
}
