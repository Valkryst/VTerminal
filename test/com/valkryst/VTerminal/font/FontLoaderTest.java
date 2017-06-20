package com.valkryst.VTerminal.font;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class FontLoaderTest {
    @Test
    public void loadFontFromJar() throws IOException, URISyntaxException {
        FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt", 1);
    }
}
