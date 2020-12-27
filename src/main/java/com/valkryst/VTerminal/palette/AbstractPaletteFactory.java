package com.valkryst.VTerminal.palette;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.valkryst.VTerminal.palette.base.Palette;
import com.valkryst.VTerminal.palette.java2d.Java2DPalette;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class AbstractPaletteFactory {
    /** The singleton instance. */
    private final static AbstractPaletteFactory INSTANCE = new AbstractPaletteFactory();

    /** A cache of recently loaded palettes. */
    private final static Cache<Integer, Palette> palettes = Caffeine.newBuilder().initialCapacity(1).expireAfterAccess(10, TimeUnit.MINUTES).build();

    /**
     * Constructs a new Palette for the specified renderer using the default palette.
     *
     * @param rendererType
     *          The type of renderer to create a palette for.
     *
     * @return
     *          The palette.
     *
     * @throws IllegalArgumentException
     *          If the default file path is empty.
     *          If the default file path does not end with ".json".
     *          If the renderer type is not supported.
     *
     * @throws IOException
     *          If an IO error occurs when opening, reading, or closing the palette file.
     */
    public Palette createPalette(final RendererType rendererType) throws IOException {
        return createPalette(rendererType, "");
    }

    /**
     * Constructs a new Palette for the specified renderer using the specified JSON palette.
     *
     * @param rendererType
     *          The type of renderer to create a palette for.
     *
     * @param filePath
     *          The path of the JSON file.
     *
     * @return
     *          The palette.
     *
     * @throws IllegalArgumentException
     *          If the file path is empty.
     *          If the file path does not end with ".json".
     *          If the renderer type is not supported.
     *
     * @throws IOException
     *          If an IO error occurs when opening, reading, or closing the palette file.
     */
    public Palette createPalette(final RendererType rendererType, final String filePath) throws IOException {
        final int paletteHash = Objects.hash(rendererType, filePath);

        // Check for cached palette.
        Palette palette = palettes.getIfPresent(paletteHash);

        if (palette != null) {
            return palette;
        }

        // Create new palette.
        switch (rendererType) {
            case JAVA2D: {
                palette = new Java2DPalette(filePath);
                break;
            }
            default: {
                throw new IllegalArgumentException("Palettes for the " + rendererType.name() + " renderer are not supported.");
            }
        }

        palette.validate();

        palettes.put(paletteHash, palette);
        return palette;
    }

    /**
     * Retrieves the singleton instance.
     *
     * @return
     *          The singleton instance.
     */
    public static AbstractPaletteFactory getInstance() {
        return INSTANCE;
    }
}
