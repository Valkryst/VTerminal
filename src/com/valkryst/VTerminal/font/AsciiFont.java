package com.valkryst.VTerminal.font;

import lombok.Getter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class AsciiFont {
    @Getter private final BufferedImage bitmapFont;

    @Getter private final HashMap<Character, BufferedImage> characterImages;


    /** The width of the font. */
    @Getter private final int width;
    /** The height of the font. */
    @Getter private final int height;


    public AsciiFont(final String bitmapFilename, final String dataFilename) throws IOException {
        if (bitmapFilename == null || bitmapFilename.isEmpty()) {
            throw new IOException("You cannot use a null or empty filename.");
        }

        if (dataFilename == null || dataFilename.isEmpty()) {
            throw new IOException("You cannot use a null or empty filename.");
        }

        bitmapFont = loadBitmap(bitmapFilename);
        characterImages = loadCharacterData(dataFilename);

        width = characterImages.get('X').getWidth();
        height = characterImages.get('X').getHeight();
    }


    private BufferedImage loadBitmap(final String fontFilename) throws IOException {
        // Attempt to load font, first from the Jar and then from the file system.
        try {
            return loadBitmapFromJar(fontFilename);
        } catch (final IllegalArgumentException | IOException e) {
            throw new IOException("Unable to load the font at \"" + fontFilename + "\" from the Jar or FileSystem." +
                                  "\n\n" + e.getMessage());
        }
    }

    private BufferedImage loadBitmapFromJar(final String filename) throws IllegalArgumentException, IOException {
        final ClassLoader classLoader = this.getClass().getClassLoader();
        final InputStream inputStream = classLoader.getResourceAsStream(filename);
        return ImageIO.read(inputStream);
    }

    private HashMap<Character, BufferedImage> loadCharacterData(final String filename) throws IOException {
        final HashMap<Character, BufferedImage> hashMap = new HashMap<>();

        List<String> linesToLoadWith;

        // Attempt to load characters, first from the Jar and then from the file system.
        try {
            linesToLoadWith = loadCharacterDataFromJar(filename);
        } catch (final IOException | NullPointerException | URISyntaxException e) {
            throw new IOException("Unable to load character data at \"" + filename + "\" from the Jar or FileSystem." +
                                  "\n\n" + e.getMessage() );
        }

        if (linesToLoadWith.size() == 0) {
            throw new IllegalStateException("No character data was loaded. Is " + filename  );
        }

        for (final String string : linesToLoadWith) {
            if (string.isEmpty() == false) {
                final Scanner scanner = new Scanner(string);
                final char character = (char) scanner.nextInt();

                final int x = scanner.nextInt();
                final int y = scanner.nextInt();
                final int width = scanner.nextInt();
                final int height = scanner.nextInt();
                final BufferedImage image = bitmapFont.getSubimage(x, y, width, height);

                hashMap.put(character, image);
            }
        }

        return hashMap;
    }

    private List<String> loadCharacterDataFromJar(final String filename) throws IOException, NullPointerException, URISyntaxException {
        final ClassLoader classLoader = this.getClass().getClassLoader();
        final URI fileURI = classLoader.getResource(filename).toURI();
        return Files.readAllLines(Paths.get(fileURI));
    }
}
