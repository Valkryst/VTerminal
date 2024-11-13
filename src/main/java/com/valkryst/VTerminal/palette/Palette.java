package com.valkryst.VTerminal.palette;

import lombok.NonNull;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Palette {
	/** Private constructor, to prevent instantiation of this class. */
	private Palette() {}

	/**
	 * Loads a set of colour {@link Properties} from an {@link InputStream} and registers them as {@link UIManager}
	 * defaults.
	 *
	 * @param inputStream {@link InputStream} to read from.
	 * @throws IOException If an error occurs while reading from the {@link InputStream}.
	 */
	public static void loadAndRegisterProperties(final @NonNull InputStream inputStream) throws IOException {
		registerProperties(loadProperties(inputStream));
	}

	/**
	 * Loads a {@link Properties} object from an {@link InputStream}.
	 *
	 * @param inputStream {@link InputStream} to read from.
	 * @return Loaded {@link Properties} object.
	 * @throws IOException If an error occurs while reading from the {@link InputStream}.
	 */
	public static Properties loadProperties(final @NonNull InputStream inputStream) throws IOException {
		final var properties = new Properties();
		properties.load(inputStream);
		return properties;
	}

	/**
	 * Register a set of colour {@link Properties} as {@link UIManager} defaults.
	 *
	 * @param properties {@link Properties} containing colour values.
	 */
	public static void registerProperties(final @NonNull Properties properties) {
		for (final var entry : properties.entrySet()) {
			UIManager.put(
				entry.getKey(),
				Color.decode((String) entry.getValue())
			);
		}
	}
}
