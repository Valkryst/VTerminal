package com.valkryst.VTerminal.samples;

import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;

import java.io.IOException;
import java.net.URISyntaxException;

public class SampleTileSheet {
    public static void main(final String[] args) throws IOException, URISyntaxException {
        final Font font = FontLoader.loadFontFromJar("Tiles/Nevanda Nethack/bitmap.png", "Tiles/Nevanda Nethack/data.fnt", 1);

        final PanelBuilder builder = new PanelBuilder();
        builder.setFont(font);
        builder.setWidthInCharacters(50);
        builder.setHeightInCharacters(22);

        final Panel panel = builder.build();

        char counter = 0;

        for (int y = 0 ; y < panel.getHeightInCharacters() ; y++) {
            final AsciiString string = panel.getScreen().getString(y);

            for (int x = 0 ; x < panel.getWidthInCharacters() ; x++) {
                string.setCharacter(x, counter);
                counter++;
            }
        }

        panel.getScreen().convertAsciiCharactersToAsciiTiles();

        panel.draw();
    }
}
