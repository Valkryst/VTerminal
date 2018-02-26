package com.valkryst.VTerminal.font;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class FontLoaderTest {
    private final String spriteSheetPath = "Fonts/DejaVu Sans Mono/20pt/bitmap.png";
    private final String characterDataPath = "Fonts/DejaVu Sans Mono/20pt/data.fnt";

    @Test(expected=FileNotFoundException.class)
    public void loadFont_strings() throws IOException {
        FontLoader.loadFont(spriteSheetPath, characterDataPath, 1);
    }


    @Test(expected=FileNotFoundException.class)
    public void loadFont_strings_withScaleBelowOne() throws IOException {
        FontLoader.loadFont(spriteSheetPath, characterDataPath, 0);
    }

    @Test(expected=NullPointerException.class)
    public void loadFont_strings_withNullSpriteSheetPath() throws IOException {
        FontLoader.loadFont(null, characterDataPath, 1);
    }

    @Test(expected=NullPointerException.class)
    public void loadFont_strings_withNullCharacterDataPath() throws IOException {
        FontLoader.loadFont(spriteSheetPath, null, 1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void loadFont_strings_withEmptySpriteSheetPath() throws IOException {
        FontLoader.loadFont("", characterDataPath, 1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void loadFont_strings_withEmptyCharacterDataPath() throws IOException {
        FontLoader.loadFont(spriteSheetPath, "", 1);
    }

    @Test
    public void loadFont_streams() throws IOException {
        final ClassLoader classLoader = FontLoader.class.getClassLoader();

        final InputStream spriteSheetStream = classLoader.getResourceAsStream(spriteSheetPath);
        final InputStream characterDataStream = classLoader.getResourceAsStream(characterDataPath);

        FontLoader.loadFont(spriteSheetStream, characterDataStream, 1);
    }

    @Test
    public void loadFont_streams_withScaleBelowOne() throws IOException {
        final ClassLoader classLoader = FontLoader.class.getClassLoader();

        final InputStream spriteSheetStream = classLoader.getResourceAsStream(spriteSheetPath);
        final InputStream characterDataStream = classLoader.getResourceAsStream(characterDataPath);

        FontLoader.loadFont(spriteSheetStream, characterDataStream, 0);
    }

    @Test(expected=NullPointerException.class)
    public void loadFont_streams_withNullSpriteSheetInputStream() throws IOException {
        final ClassLoader classLoader = FontLoader.class.getClassLoader();

        final InputStream characterDataStream = classLoader.getResourceAsStream(characterDataPath);

        FontLoader.loadFont(null, characterDataStream, 1);
    }

    @Test(expected=NullPointerException.class)
    public void loadFont_streams_withNullCharacterDataInputStream() throws IOException {
        final ClassLoader classLoader = FontLoader.class.getClassLoader();

        final InputStream spriteSheetStream = classLoader.getResourceAsStream(spriteSheetPath);

        FontLoader.loadFont(spriteSheetStream, null, 1);
    }

    @Test
    public void loadFontFromJar() throws IOException {
        FontLoader.loadFontFromJar(spriteSheetPath, characterDataPath, 1);
    }

    @Test(expected=NullPointerException.class)
    public void loadFontFromJar_withNullSpriteSheetPath() throws IOException {
        FontLoader.loadFontFromJar(null, characterDataPath, 1);
    }

    @Test(expected=NullPointerException.class)
    public void loadFontFromJar_withNullCharacterDataPath() throws IOException {
        FontLoader.loadFontFromJar(spriteSheetPath, null, 1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void loadFontFromJar_withEmptySpriteSheetPath() throws IOException {
        FontLoader.loadFontFromJar("", characterDataPath, 1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void loadFontFromJar_withEmptyCharacterDataPath() throws IOException {
        FontLoader.loadFontFromJar(spriteSheetPath, "", 1);
    }
}
