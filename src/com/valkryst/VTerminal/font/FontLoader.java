package com.valkryst.VTerminal.font;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class FontLoader {
    /**
     * Loads a font from the file system.
     *
     * @param spriteSheetPath
     *         The path to the sprite sheet.
     *
     * @param characterDataPath
     *         The path to the character data.
     *
     * @param scale
     *         The amount to scale the font by.
     *
     * @return
     *         The font.
     *
     * @throws IOException
     *         If an IOException occurs while loading the font.
     *
     * @throws URISyntaxException
     *         If a URISyntaxException occurs while loading the font.
     */
    public static Font loadFont(final String spriteSheetPath, final String characterDataPath, final int scale) throws IOException {
        return loadFont(new FileInputStream(spriteSheetPath), Paths.get(characterDataPath), scale);
    }

    /**
     * Loads a font from the file system.
     *
     * @param spriteSheet
     *         The input stream to the sprite sheet.
     *
     * @param characterData
     *         The path to the character data.
     *
     * @param scale
     *         The amount to scale the font by.
     *
     * @return
     *         The font.
     *
     * @throws IOException
     *         If an IOException occurs while loading the font.
     *
     * @throws URISyntaxException
     *         If a URISyntaxException occurs while loading the font.
     */
    public static Font loadFont(final InputStream spriteSheet, final Path characterData, final int scale) throws IOException {
        final BufferedImage image = loadSpriteSheet(spriteSheet);
        final List<String> data = loadCharacterData(characterData);

        return new Font(processFontData(image, data), scale);
    }

    /**
     * Loads a font from within the Jar.
     *
     * @param spriteSheetPath
     *         The path to the sprite sheet.
     *
     * @param characterDataPath
     *         The path to the character data.
     *
     * @param scale
     *         The amount to scale the font by.
     *
     * @return
     *         The font.
     *
     * @throws IOException
     *         If an IOException occurs while loading the font.
     *
     * @throws URISyntaxException
     *         If a URISyntaxException occurs while loading the font.
     */
    public static Font loadFontFromJar(final String spriteSheetPath, final String characterDataPath, final int scale) throws IOException, URISyntaxException {
        final ClassLoader classLoader = FontLoader.class.getClassLoader();

        final URI spriteSheetURI = classLoader.getResource(spriteSheetPath).toURI();
        final URI characterDataURI = classLoader.getResource(characterDataPath).toURI();

        return loadFont(spriteSheetURI.toURL().openStream(), Paths.get(characterDataURI), scale);
    }

    /**
     * Processes a font sprite sheet and character data into a usable HashMap of character sprites.
     *
     * @param spriteSheet
     *         The sprite sheet.
     *
     * @param characterData
     *         The character data.
     *
     * @return
     *         The HashMap of character sprites.
     */
    private static HashMap<Character, BufferedImage> processFontData(final BufferedImage spriteSheet, final List<String> characterData) {
        final HashMap<Character, BufferedImage> hashMap = new HashMap<>(characterData.size());

        for (final String string : characterData) {
            if (string.isEmpty() == false) {
                final Scanner scanner = new Scanner(string);
                final char character = (char) scanner.nextInt();

                final int x = scanner.nextInt();
                final int y = scanner.nextInt();
                final int width = scanner.nextInt();
                final int height = scanner.nextInt();
                final BufferedImage image = spriteSheet.getSubimage(x, y, width, height);

                hashMap.put(character, image);
            }
        }

        return hashMap;
    }

    /**
     * Loads sprite sheet from an input stream.
     *
     * @param inputStream
     *         The input stream.
     *
     * @return
     *         The sprite sheet.
     *
     * @throws IOException
     *         If an IOException occurs while loading the sprite sheet.
     */
    private static BufferedImage loadSpriteSheet(final InputStream inputStream) throws IOException {
        return ImageIO.read(inputStream);
    }

    /**
     * Loads character data from a path.
     *
     * @param path
     *         The path.
     *
     * @return
     *         The character data.
     *
     * @throws IOException
     *         If an IOException occurs while loading the character data.
     */
    private static List<String> loadCharacterData(final Path path) throws IOException {
        return Files.readAllLines(path);
    }
}
