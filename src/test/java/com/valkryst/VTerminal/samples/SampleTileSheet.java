package com.valkryst.VTerminal.samples;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;

import java.io.IOException;

public class SampleTileSheet {
    public static void main(final String[] args) throws IOException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/", 1);

        final Screen screen = new Screen(100, 33, font);

        char counter = 0;
        Tile tile;

        for (int y = 0 ; y < screen.getHeight() ; y++) {
            for (int x = 0 ; x < screen.getWidth(); x++) {
                tile = screen.getTileAt(x, y);

                for (char i = counter ; i < Character.MAX_VALUE ; i++) {
                    if (font.isCharacterSupported(i)) {
                        tile.setCharacter(counter);
                        counter++;
                        break;
                    }

                    counter++;
                }
            }
        }

        screen.addCanvasToFrame();
    }
}
