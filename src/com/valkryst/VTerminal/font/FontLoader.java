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
     * Loads a font from within the Jar.
     *
     * @param folderPath
     *          The path to the folder containing the PNG and FNT files.
     *
     * @param scale
     *          The amount to scale the font by.
     *
     * @return
     *          The font.
     *
     * @throws NullPointerException
     *          If the font folder path, PNG path, or FNT path is null.
     *
     * @throws IllegalArgumentException
     *          If the font folder path is empty.
     *          If the PNG or FNT paths are empty.
     *
     * @throws IOException
     *          If an IOException occurs while loading the PNG image.
     *          If an IOException occurs while loading the FNT file lines.
     */
    public static Font loadFontFromJar(@NonNull String folderPath, final double scale) throws IOException {
        if (folderPath.isEmpty()) {
            throw new IllegalArgumentException("The folder path cannot be empty.");
        }

        if (folderPath.endsWith("/") == false && folderPath.endsWith("\\") == false) {
            folderPath += "/";
        }

        // Load the font
        return loadFontFromJar(folderPath + "bitmap.png", folderPath + "data.fnt", scale);
    }

    /**
     * Loads a font from the file system.
     *
     * @param pngFilePath
     *          The path to the PNG file.
     *
     * @param fntFilePath
     *          The path to the FNT file.
     *
     * @param scale
     *          The amount to scale the font by.
     *
     * @return
     *          The font.
     *
     * @throws NullPointerException
     *          If the PNG or FNT paths are null.
     *
     * @throws IllegalArgumentException
     *          If the PNG or FNT paths are empty.
     *
     * @throws IOException
     *          If an IOException occurs while loading the PNG image.
     *          If an IOException occurs while loading the FNT file lines.
     */
    public static Font loadFont(final @NonNull String pngFilePath, final @NonNull String fntFilePath, final double scale) throws IOException {
        if (pngFilePath.isEmpty()) {
            throw new IllegalArgumentException("The PNG path cannot be empty.");
        }

        if (fntFilePath.isEmpty()) {
            throw new IllegalArgumentException("The FNT path cannot be empty.");
        }

        return loadFont(new FileInputStream(pngFilePath), new FileInputStream(fntFilePath), scale);
    }

    /**
     * Loads a font from within the Jar.
     *
     * @param pngFilePath
     *          The path to the PNG file.
     *
     * @param fntFilePath
     *          The path to the FNT file.
     *
     * @param scale
     *          The amount to scale the font by.
     *
     * @return
     *          The font.
     *
     * @throws NullPointerException
     *          If the PNG or FNT paths are null.
     *
     * @throws IllegalArgumentException
     *          If the PNG or FNT paths are empty.
     *
     * @throws IOException
     *          If an IOException occurs while loading the PNG image.
     *          If an IOException occurs while loading the FNT file lines.
     */
    public static Font loadFontFromJar(final @NonNull String pngFilePath, final @NonNull String fntFilePath, final double scale) throws IOException {
        if (pngFilePath.isEmpty()) {
            throw new IllegalArgumentException("The PNG path cannot be empty.");
        }

        if (fntFilePath.isEmpty()) {
            throw new IllegalArgumentException("The FNT path cannot be empty.");
        }

        final ClassLoader classLoader = FontLoader.class.getClassLoader();

        final InputStream spriteSheetStream = classLoader.getResourceAsStream(pngFilePath);
        final InputStream characterDataStream = classLoader.getResourceAsStream(fntFilePath);

        return loadFont(spriteSheetStream, characterDataStream, scale);
    }

    /**
     * Loads a font from the file system.
     *
     * @param pngStream
     *          The input stream of the PNG file.
     *
     * @param fntStream
     *          The input stream of the FNT file.
     *
     * @param scale
     *          The amount to scale the font by.
     *
     * @return
     *          The font.
     *
     * @throws NullPointerException
     *          If the PNG or FNT streams are null.
     *
     * @throws IOException
     *          If an IOException occurs while loading the PNG image.
     *          If an IOException occurs while loading the FNT file lines.
     */
    public static Font loadFont(final @NonNull InputStream pngStream, final @NonNull InputStream fntStream, double scale) throws IOException {
        if (scale <= 0) {
            scale = 1;
        }

        final BufferedImage image = loadSpriteSheet(pngStream);
        final List<String> data = loadCharacterData(fntStream);

        return new Font(processFontData(image, data), scale);
    }

    /**
     * Processes a font sprite sheet and character data into a usable HashMap of character images.
     *
     * @param pngImage
     *          The PNG image.
     *
     * @param fntFileLines
     *          The lines of the FNT file.
     *
     * @return
     *          The HashMap of character sprites.
     *
     * @throws NullPointerException
     *          If the PNG image or FNT file lines are null.
     */
    private static HashMap<Integer, FontCharacter> processFontData(final @NonNull BufferedImage pngImage, final @NonNull List<String> fntFileLines) {
        final HashMap<Integer, FontCharacter> hashMap = new HashMap<>(fntFileLines.size());

        for (final String string : fntFileLines) {
            if (string.isEmpty() == false) {
                final Scanner scanner = new Scanner(string);
                final int character = scanner.nextInt();

                final int x = scanner.nextInt();
                final int y = scanner.nextInt();
                final int width = scanner.nextByte();
                final int height = scanner.nextByte();
                final BufferedImage image = pngImage.getSubimage(x, y, width, height);

                hashMap.put(character, new FontCharacter(character, image));
            }
        }

        return hashMap;
    }

    /**
     * Loads sprite sheet from an input stream.
     *
     * @param pngStream
     *          The input stream of the PNG file.
     *
     * @return
     *          The PNG image.
     *
     * @throws NullPointerException
     *          If the input stream is null.
     *
     * @throws IOException
     *          If an IOException occurs while loading the PNG image.
     */
    private static BufferedImage loadSpriteSheet(final @NonNull InputStream pngStream) throws IOException {
        final BufferedImage loadedImage = ImageIO.read(pngStream);
        pngStream.close();

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
     * @param fntStream
     *          The input stream of the FNT file.
     *
     * @return
     *          The FNT file lines.
     *
     * @throws NullPointerException
     *          If the input stream is null.
     *
     * @throws IOException
     *          If an IOException occurs while loading the FNT file lines.
     */
    private static List<String> loadCharacterData(final @NonNull InputStream fntStream) throws IOException {
        // Load lines
        final InputStreamReader isr = new InputStreamReader(fntStream, StandardCharsets.UTF_8);
        final BufferedReader br = new BufferedReader(isr);
        final List<String> lines = br.lines().collect(Collectors.toList());
        fntStream.close();

        // Remove Unnecessary Data
        final Pattern miscPattern = Pattern.compile("info.*|common.*|page.*|chars.*|char id=\\d\\d\\d\\d\\d\\d.*|char id=[7-9]\\d\\d\\d\\d.*|char id=6[6-9]\\d\\d\\d.*|char id=65[6-9]\\d\\d.*|char id=655[4-9]\\d.*|char id=6553[6-9].*| xoff.*|char id=|x=|y=|width=|height=");
        lines.replaceAll(string -> miscPattern.matcher(string).replaceAll(""));
        lines.removeIf(String::isEmpty);

        // Remove Kerning Data
        lines.removeIf(s -> s.subSequence(0, 7).equals("kerning"));

        return lines;
    }
}
