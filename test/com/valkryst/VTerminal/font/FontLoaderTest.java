package com.valkryst.VTerminal.font;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RunWith(value=BlockJUnit4ClassRunner.class)
public class FontLoaderTest {
    private final String spriteSheetPath = System.getProperty("user.dir") + "/res/Fonts/DejaVu Sans Mono/20pt/bitmap.png";
    private final String characterDataPath = System.getProperty("user.dir") + "/res/Fonts/DejaVu Sans Mono/20pt/data.fnt";
    private final String spriteSheetPath_inJar = "Fonts/DejaVu Sans Mono/20pt/bitmap.png";
    private final String characterDataPath_inJar = "Fonts/DejaVu Sans Mono/20pt/data.fnt";

    @Test
    public void loadFont_withStrings() throws IOException {
        FontLoader.loadFont(spriteSheetPath, characterDataPath, 1);
    }

    @Test(expected=FileNotFoundException.class)
    public void loadFont_withStrings_withEmptySpriteSheetPath() throws IOException {
        FontLoader.loadFont("", characterDataPath, 1);
    }

    @Test(expected=NullPointerException.class)
    public void loadFont_withStrings_withNullSpriteSheetPath() throws IOException {
        FontLoader.loadFont(null, characterDataPath, 1);
    }

    @Test(expected=AccessDeniedException.class)
    public void loadFont_withStrings_withEmptyCharacterDataPath() throws IOException {
        FontLoader.loadFont(spriteSheetPath, "", 1);
    }

    @Test(expected=NullPointerException.class)
    public void loadFont_withStrings_withNullCharacterDataPath() throws IOException {
        FontLoader.loadFont(spriteSheetPath, null, 1);
    }

    @Test
    public void loadFont_withStreamAndPath() throws IOException {
        final InputStream inputStream = new FileInputStream(spriteSheetPath);
        final Path path = Paths.get(characterDataPath);

        FontLoader.loadFont(inputStream, path, 1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void loadFont_withStreamAndPath_withNullStream() throws IOException {
        final Path path = Paths.get(characterDataPath);

        FontLoader.loadFont(null, path, 1);
    }

    @Test(expected=NullPointerException.class)
    public void loadFont_withStreamAndPath_withNullPath() throws IOException {
        final InputStream inputStream = new FileInputStream(spriteSheetPath);

        FontLoader.loadFont(inputStream, null, 1);
    }

    @Test
    public void loadFontFromJar_withStrings() throws IOException, URISyntaxException {
        FontLoader.loadFontFromJar(spriteSheetPath_inJar, characterDataPath_inJar, 1);
    }

    @Test(expected=NullPointerException.class)
    public void loadFontFromJar_withStrings_withEmptySpriteSheetPath() throws IOException, URISyntaxException {
        FontLoader.loadFontFromJar("", characterDataPath_inJar, 1);
    }

    @Test(expected=NullPointerException.class)
    public void loadFontFromJar_withStrings_withNullSpriteSheetPath() throws IOException, URISyntaxException {
        FontLoader.loadFontFromJar(null, characterDataPath_inJar, 1);
    }

    @Test(expected=AccessDeniedException.class)
    public void loadFontFromJar_withStrings_withEmptyCharacterDataPath() throws IOException, URISyntaxException {
        FontLoader.loadFontFromJar(spriteSheetPath_inJar, "", 1);
    }

    @Test(expected=NullPointerException.class)
    public void loadFontFromJar_withStrings_withNullCharacterDataPath() throws IOException, URISyntaxException {
        FontLoader.loadFontFromJar(spriteSheetPath_inJar, null, 1);
    }
}
