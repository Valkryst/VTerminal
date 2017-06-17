package com.valkryst.VTerminal.font;

import lombok.Getter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

public class Font {
    /** The sprite-images of every character provided by the Font. */
    @Getter private final HashMap<Character, BufferedImage> characterImages;

    /** The width of the font. */
    @Getter private final int width;
    /** The height of the font. */
    @Getter private final int height;

    public Font(final HashMap<Character, BufferedImage> characterImages) throws IOException {
        this.characterImages = characterImages;

        width = characterImages.get('X').getWidth();
        height = characterImages.get('X').getHeight();
    }
}
