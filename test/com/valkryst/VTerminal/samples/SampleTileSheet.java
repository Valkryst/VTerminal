package com.valkryst.VTerminal.samples;

import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;

import java.awt.Color;
import java.io.IOException;
import java.net.URISyntaxException;

public class SampleTileSheet {
    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException {
        final Font font = FontLoader.loadFontFromJar("Tiles/Nevanda Nethack/bitmap.png", "Tiles/Nevanda Nethack/data.fnt", 1);

        final PanelBuilder builder = new PanelBuilder();
        builder.setFont(font);
        builder.setWidthInCharacters(50);
        builder.setHeightInCharacters(22);

        final Panel panel = builder.build();
        panel.getScreen().setBackgroundColor(new Color(255, 0, 255));

        Thread.sleep(50);

        char counter = 32;

        /* This is the original way to get tiles on the screen:

        for (int y = 0 ; y < panel.getHeightInCharacters() ; y++) {
            final AsciiString string = panel.getScreen().getString(y);

            for (int x = 0 ; x < panel.getWidthInCharacters() ; x++) {
                string.setCharacter(x, new AsciiTile(counter));
                counter++;
            }
        }
        */
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
