package com.valkryst.VTerminal.palette;

import lombok.NonNull;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Palette {
	// Prevent users from instantiating the class.
	private Palette() {}

	/**
	 * Loads a collection of color properties from an input stream and applies
	 * them as UIManager defaults.
	 *
	 * @param inputStream An input stream.
	 * @throws IOException If an error occurred while reading from the input stream.
	 */
	public static void loadAndRegisterProperties(final @NonNull InputStream inputStream) throws IOException {
		registerProperties(loadProperties(inputStream));
	}

	/**
	 * Load the input stream as a properties file.
	 *
	 * @param inputStream An input stream.
	 * @return The properties.
	 * @throws IOException If an error occurred when reading from the input stream.
	 */
	public static Properties loadProperties(final @NonNull InputStream inputStream) throws IOException {
		final var properties = new Properties();
		properties.load(inputStream);
		return properties;
	}

	/**
	 * Register a set of color properties as UIManager defaults.
	 *
	 * @param properties A set of properties.
	 */
	public static void registerProperties(final @NonNull Properties properties) {
		for (final var entry : properties.entrySet()) {
			final var key = (String) entry.getKey();
			final var value = Color.decode((String) entry.getValue());
			UIManager.put(key, value);
		}
	}
}
