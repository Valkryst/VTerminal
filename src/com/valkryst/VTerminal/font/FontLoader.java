package com.valkryst.VTerminal.font;

import lombok.NonNull;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class FontLoader {
    // Prevent users from creating an instance.
    private FontLoader() {}

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
     * @throws NullPointerException
     *         If the sprite sheet or character data paths are null.
     *
     * @throws IllegalArgumentException
     *         If the sprite sheet or character data paths are empty.
     *
     * @throws IOException
     *         If an IOException occurs while loading the font.
     */
    public static Font loadFont(final @NonNull String spriteSheetPath, final @NonNull String characterDataPath, final double scale) throws IOException {
        if (spriteSheetPath.isEmpty()) {
            throw new IllegalArgumentException("The sprite sheet path cannot be empty.");
        }

        if (characterDataPath.isEmpty()) {
            throw new IllegalArgumentException("The character data path cannot be empty.");
        }

        return loadFont(new FileInputStream(spriteSheetPath), new FileInputStream(characterDataPath), scale);
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
     * @throws NullPointerException
     *         If the sprite sheet or character data paths are null.
     *
     * @throws IllegalArgumentException
     *         If the sprite sheet or character data paths are empty.
     *
     * @throws IOException
     *         If an IOException occurs while loading the font.
     */
    public static Font loadFontFromJar(final @NonNull String spriteSheetPath, final @NonNull String characterDataPath, final double scale) throws IOException {
        if (spriteSheetPath.isEmpty()) {
            throw new IllegalArgumentException("The sprite sheet path cannot be empty.");
        }

        if (characterDataPath.isEmpty()) {
            throw new IllegalArgumentException("The character data path cannot be empty.");
        }

        final ClassLoader classLoader = FontLoader.class.getClassLoader();

        final InputStream spriteSheetStream = classLoader.getResourceAsStream(spriteSheetPath);
        final InputStream characterDataStream = classLoader.getResourceAsStream(characterDataPath);

        return loadFont(spriteSheetStream, characterDataStream, scale);
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
     * @throws NullPointerException
     *         If the sprite sheet or character data streams are null.
     *
     * @throws IOException
     *         If an IOException occurs while loading the font.
     */
    public static Font loadFont(final @NonNull InputStream spriteSheet, final @NonNull InputStream characterData, double scale) throws IOException {
        if (scale <= 0) {
            scale = 1;
        }

        final BufferedImage image = loadSpriteSheet(spriteSheet);
        final List<String> data = loadCharacterData(characterData);

        return new Font(processFontData(image, data), scale);
    }

    /**
     * Processes a font sprite sheet and character data into a usable HashMap of character images.
     *
     * @param spriteSheet
     *         The sprite sheet.
     *
     * @param characterData
     *         The character data.
     *
     * @return
     *         The HashMap of character sprites.
     *
     * @throws NullPointerException
     *         If the sprite sheet or character data is null.
     */
    private static HashMap<Integer, FontCharacter> processFontData(final @NonNull BufferedImage spriteSheet, final @NonNull List<String> characterData) {
        final HashMap<Integer, FontCharacter> hashMap = new HashMap<>(characterData.size());

        for (final String string : characterData) {
            if (string.isEmpty() == false) {
                final Scanner scanner = new Scanner(string);
                final int character = scanner.nextInt();

                final int x = scanner.nextInt();
                final int y = scanner.nextInt();
                final int width = scanner.nextByte();
                final int height = scanner.nextByte();
                final BufferedImage image = spriteSheet.getSubimage(x, y, width, height);

                hashMap.put(character, new FontCharacter(character, image));
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
     * @throws NullPointerException
     *         If the input stream is null.
     *
     * @throws IOException
     *         If an IOException occurs while loading the sprite sheet.
     */
    private static BufferedImage loadSpriteSheet(final @NonNull InputStream inputStream) throws IOException {
        final BufferedImage loadedImage = ImageIO.read(inputStream);
        inputStream.close();

        try {
            final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            final GraphicsDevice gd = ge.getDefaultScreenDevice();
            final GraphicsConfiguration gc = gd.getDefaultConfiguration();

            final BufferedImage convertedImage = gc.createCompatibleImage(loadedImage.getWidth(), loadedImage.getHeight(), loadedImage.getTransparency());

            final Graphics2D g2d = convertedImage.createGraphics();
            g2d.drawImage(loadedImage, 0, 0, null);
            g2d.dispose();
            return convertedImage;
        } catch(final HeadlessException e) {
            // Occurs when running FontLoader unit tests on Travis CI.
            // Probably because there's no screen/graphics device.
            return loadedImage;
        }
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
     * @throws NullPointerException
     *         If the input stream is null.
     *
     * @throws IOException
     *         If an IOException occurs while loading the character data.
     */
    private static List<String> loadCharacterData(final @NonNull InputStream inputStream) throws IOException {
        // Load lines
        final InputStreamReader isr = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        final BufferedReader br = new BufferedReader(isr);
        List<String> lines = br.lines().collect(Collectors.toList());

        // Remove unnecessary data:
        final Pattern pattern = Pattern.compile("info.*|common.*|page.*|chars.*|char id=\\d\\d\\d\\d\\d\\d.*|char id=[7-9]\\d\\d\\d\\d.*|char id=6[6-9]\\d\\d\\d.*|char id=65[6-9]\\d\\d.*|char id=655[4-9]\\d.*|char id=6553[6-9].*| xoff.*|char id=|x=|y=|width=|height=");
        lines.replaceAll(string -> pattern.matcher(string).replaceAll(""));
        lines.removeIf(String::isEmpty);

        inputStream.close();
        return lines;
    }
}
