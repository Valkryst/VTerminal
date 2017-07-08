package com.valkryst.VTerminal.font;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        return loadFont(new FileInputStream(spriteSheetPath), new FileInputStream(characterDataPath), scale);
    }

    /**
     * Loads a font from the file system.
     *
     * @param spriteSheet
     *         The input stream to the sprite sheet.
     *
     * @param characterData
     *         The input stream to the character data.
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
    public static Font loadFont(final InputStream spriteSheet, final InputStream characterData, final int scale) throws IOException {
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

        final InputStream spriteSheetStream = classLoader.getResourceAsStream(spriteSheetPath);
        final InputStream characterDataStream = classLoader.getResourceAsStream(characterDataPath);

        return loadFont(spriteSheetStream, characterDataStream, scale);
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
        final BufferedImage loadedImage = ImageIO.read(inputStream);
        final BufferedImage argbImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        inputStream.close();

        final Graphics2D g2d= argbImage.createGraphics();
        g2d.drawImage(loadedImage, 0, 0, null);
        g2d.dispose();
        return argbImage;
    }

    /**
     * Loads character data from a path.
     *
     * @param inputStream
     *         The input stream.
     *
     * @return
     *         The character data.
     *
     * @throws IOException
     *         If an IOException occurs while loading the character data.
     */
    private static List<String> loadCharacterData(final InputStream inputStream) throws IOException {
        // Load lines
        final InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        final BufferedReader br = new BufferedReader(isr);
        List<String> lines = br.lines().collect(Collectors.toList());

        // Remove unnecessary data:
        final Pattern pattern = Pattern.compile("info.*|common.*|page.*|chars.*|char id=\\d\\d\\d\\d\\d\\d.*|char id=[7-9]\\d\\d\\d\\d.*|char id=6[6-9]\\d\\d\\d.*|char id=65[6-9]\\d\\d.*|char id=655[4-9]\\d.*|char id=6553[6-9].*| xoff.*|char id=|x=|y=|width=|height=");
        lines.replaceAll(string -> pattern.matcher(string).replaceAll(""));
        lines.removeIf(String::isEmpty);

        System.out.println(lines);
        return lines;
    }
}
