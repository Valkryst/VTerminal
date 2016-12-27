package com.valkryst.AsciiPanel;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Getter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AsciiFont {
    /** The font. */
    @Getter private final Font font;
    /** The width of the font. */
    @Getter private final int width;
    /** The height of the font. */
    @Getter private final int height;

    /**
     * Constructs a new AsciiFont.
     *
     * @param font
     *         The font to use.
     */
    public AsciiFont(final Font font) {
        this.font = font;
        width = calculateWidth();
        height = (int) font.getSize();
    }

    /**
     * Constructs a new AsciiFont.
     *
     * @param fontFilename
     *         The path to, and filename of, the font.
     *
     *         Ex:
     *              C:\path\to\font.ttf
     *
     * @param fontSize
     *         The size of the font.
     *
     *         todo Find out whether this size defines the width or height of the font.
     *
     * @throws IOException
     *          If an IOException occurs while loading the font.
     */
    public AsciiFont(final String fontFilename, final int fontSize) throws IOException {
        font = loadFont(fontFilename, fontSize);
        width = calculateWidth();
        height = (int) font.getSize();
    }

    /**
     * Loads the specified font.
     *
     * @param fontFilename
     *         The path to, and filename of, the font.
     *
     *         Ex:
     *              C:\path\to\font.ttf
     *
     * @param fontSize
     *         The size of the font.
     *
     *         todo Find out whether this size defines the width or height of the font.
     *
     * @return
     *         The loaded font.
     *
     * @throws IOException
     *          If an IOException occurs while loading the font.
     */
    private Font loadFont(String fontFilename, int fontSize) throws IOException {
        // Ensure filename & size are in an acceptable format:
        if (fontFilename.isEmpty()) {
            fontFilename = "Fonts/DejaVu/DejaVuSansMono.ttf";
        }

        if (fontSize < 1) {
            fontSize = 1;
        }

        // Attempt to load font, first from the Jar and then from the file system.
        Font fontFromJar = loadFromJar(fontFilename, fontSize);
        Font fontFromFileSystem = loadFromFilesystem(fontFilename, fontSize);

        // Return whichever font was successfully loaded or throw an error if neither could be loaded:
        if (fontFromJar == null && fontFromFileSystem == null) {
            throw new IOException("Unable to load the font at \"" + fontFilename + "\" from the Jar or FileSystem.");
        }

        if (fontFromJar != null) {
            return fontFromJar;
        }

        return fontFromFileSystem;
    }

    /**
     * Loads the specified font from the Jar.
     *
     * @param fontFilename
     *         The path to, and filename of, the font.
     *
     *         Ex:
     *              C:\path\to\font.ttf
     *
     * @param fontSize
     *         The size of the font.
     *
     *         todo Find out whether this size defines the width or height of the font.
     *
     * @return
     *         The loaded font, or null if it could not be loaded.
     */
    private Font loadFromJar(final String fontFilename, final int fontSize) {
        final ClassLoader classLoader = this.getClass().getClassLoader();
        final InputStream inputStream = classLoader.getResourceAsStream(fontFilename);
        return Font.loadFont(inputStream, fontSize);
    }

    /**
     * Loads the specified font from the file system.
     *
     * @param fontFilename
     *         The path to, and filename of, the font.
     *
     *         Ex:
     *              C:\path\to\font.ttf
     *
     * @param fontSize
     *         The size of the font.
     *
     *         todo Find out whether this size defines the width or height of the font.
     *
     * @return
     *         The loaded font, or null if it could not be loaded.
     */
    private Font loadFromFilesystem(final String fontFilename, final int fontSize) {
        try {
            final Path path = Paths.get(fontFilename);
            final InputStream inputStream = Files.newInputStream(path);
            return Font.loadFont(inputStream, fontSize);
        } catch (final IOException e) {
            return null;
        }
    }

    /** @return The width of the font. If the font is monospaced, then the result is correct, else the result is incorrect. */
    private int calculateWidth() {
        // See http://stackoverflow.com/a/41326161/1667096
        final Text text = new Text("@");
        text.setFont(font);
        return (int) text.getLayoutBounds().getWidth();
    }
}
