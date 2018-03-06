package com.valkryst.VTerminal.misc;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.component.Layer;
import lombok.NonNull;

import java.awt.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;
import java.util.zip.GZIPInputStream;

public final class REXPaintLoader {
    // Prevent users from creating an instance.
    private REXPaintLoader() {}

    /**
     * Loads a REXPaint file into a set of layers.
     *
     * @param file
     *         The file.
     *
     * @return
     *         The layers.
     *
     * @throws IOException
     *         If an I/O error has occurred.
     */
    public static List<Layer> load(final @NonNull File file) throws IOException {
        if (file.exists() == false) {
            throw new FileNotFoundException("The file \"" + file.getAbsolutePath() + "\" does not exist.");
        }

        final byte[] fileBytes_compressed = Files.readAllBytes(file.toPath());
        final byte[] fileBytes_decompressed = decompress(fileBytes_compressed);

        return loadAsLayers(fileBytes_decompressed);
    }

    /**
     * Decompresses a GZip'd byte array.
     *
     * @param compressedBytes
     *         The compressed byte array.
     *
     * @return
     *         The decompressed byte array.
     *
     * @throws IOException
     *         If an I/O error has occurred.
     */
    private static byte[] decompress(final byte[] compressedBytes) throws IOException {
        final ByteArrayInputStream byteInputStream = new ByteArrayInputStream(compressedBytes);
        final ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(compressedBytes.length);
        final GZIPInputStream gzipInputStream = new GZIPInputStream(byteInputStream);

        byte[] buffer = new byte[1024];

        while (gzipInputStream.available() > 0) {
            final int count = gzipInputStream.read(buffer, 0, 1024);

            if (count > 0) {
                byteOutputStream.write(buffer, 0, count);
            }
        }

        gzipInputStream.close();
        byteOutputStream.close();
        byteInputStream.close();

        return byteOutputStream.toByteArray();
    }

    /**
     * Loads a REXPaint *.xp file into a set of layers.
     *
     * @param bytes
     *         The decompressed *.xp file.
     *
     * @return
     *         The layers.
     */
    private static List<Layer> loadAsLayers(final byte[] bytes) {
        final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);

        final int fileVersion = byteBuffer.getInt(); // Ignore this. It's only used by REXPaint itself.
        final int totalLayers = byteBuffer.getInt();

        final List<Layer> layers = new LinkedList<>();

        for (int i = 0 ; i < totalLayers ; i++) {
            final int width = byteBuffer.getInt();
            final int height = byteBuffer.getInt();

            final Layer layer = new Layer(new Dimension(width, height));

            for (int y = 0 ; y < layer.getTiles().getHeight() ; y++) {
                for (int x = 0 ; x < layer.getTiles().getWidth() ; x++) {
                    final char character = (char) byteBuffer.getInt();

                    final byte foregroundR = byteBuffer.get();
                    final byte foregroundG = byteBuffer.get();
                    final byte foregroundB = byteBuffer.get();

                    final byte backgroundR = byteBuffer.get();
                    final byte backgroundG = byteBuffer.get();
                    final byte backgroundB = byteBuffer.get();

                    final Color foregroundColor = new Color(pack(foregroundR, foregroundG, foregroundB));
                    final Color backgroundColor;
                    final int packedBackgroundColor = pack(backgroundR, backgroundG, backgroundB);

                    // REXPaint uses (255r, 0g, 255b) as a color denoting full-transparency.
                    // After packing the color, it's represented by 0xAARRGGBB, so we check
                    // for (255r, 0g, 255b) by checking for 0x00FF00FF.
                    if (packedBackgroundColor == 0x00FF00FF) {
                        backgroundColor = new Color(packedBackgroundColor & 0xFF000000, true);
                    } else {
                        backgroundColor = new Color(packedBackgroundColor);
                    }

                    final Tile asciiCharacter = layer.getTiles().getTileAt(x, y);
                    asciiCharacter.setForegroundColor(foregroundColor);
                    asciiCharacter.setBackgroundColor(backgroundColor);
                    asciiCharacter.setCharacter(character);
                }
            }

            layers.add(layer);
        }

        return layers;
    }

    /**
     * Packs a set of rgb byte values into a single integer.
     *
     * The Alpha value is set to 0.
     *
     * @param r
     *         The red value.
     *
     * @param g
     *         The green value.
     *
     * @param b
     *         The blue value.
     *
     * @return
     *         The packed integer.
     */
    private static int pack(final byte r, final byte g, final byte b) {
        return ((r & 0xFF) << 16) |
               ((g & 0xFF) << 8) |
               ((b & 0xFF));
    }
}
