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
    public static Font loadFont(final String spriteSheetPath, final String characterDataPath) throws IOException {
        return loadFont(new FileInputStream(spriteSheetPath), Paths.get(characterDataPath));
    }

    public static Font loadFont(final InputStream spriteSheet, final Path characterData) throws IOException {
        final BufferedImage image = loadSpriteSheet(spriteSheet);
        final List<String> data = loadCharacterData(characterData);

        return new Font(processFontData(image, data));
    }

    public static Font loadFontFromJar(final String spriteSheetPath, final String characterDataPath) throws IOException, URISyntaxException {
        final ClassLoader classLoader = FontLoader.class.getClassLoader();

        final URI spriteSheetURI = classLoader.getResource(spriteSheetPath).toURI();
        final URI characterDataURI = classLoader.getResource(characterDataPath).toURI();

        return loadFont(spriteSheetURI.toURL().openStream(), Paths.get(characterDataURI));
    }

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

    private static BufferedImage loadSpriteSheet(final InputStream inputStream) throws IOException {
        return ImageIO.read(inputStream);
    }

    private static List<String> loadCharacterData(final Path path) throws IOException {
        return Files.readAllLines(path);
    }
}
