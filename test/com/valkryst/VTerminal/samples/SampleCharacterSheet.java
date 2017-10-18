package com.valkryst.VTerminal.samples;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;

import java.io.IOException;
import java.net.URISyntaxException;

public class SampleCharacterSheet {
    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/16pt/bitmap.png", "Fonts/DejaVu Sans Mono/16pt/data.fnt", 1);

        final PanelBuilder builder = new PanelBuilder();
        builder.setFont(font);
        builder.setWidthInCharacters(100);
        builder.setHeightInCharacters(40);

        final Panel panel = builder.build();

        Thread.sleep(50);

        char counter = 0;

        for (int y = 0 ; y < panel.getHeightInCharacters() ; y++) {
            final AsciiString string = panel.getScreen().getString(y);
            final AsciiCharacter[] characters = string.getCharacters();

            for (int x = 0 ; x < panel.getWidthInCharacters() ; x++) {
                for (char i = counter ; i < Character.MAX_VALUE ; i++) {
                    if (font.isCharacterSupported(i)) {
                        characters[x].setCharacter(counter);
                        counter++;
                        break;
                    }

                    counter++;
                }
            }
        }

        panel.draw();
    }
}
