package com.valkryst.VTerminal;

import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;

import javax.swing.JFrame;
import java.io.IOException;
import java.net.URISyntaxException;

public class SampleCharacterSheet {
    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);

        final Screen screen = new Screen(100, 33, font);
        final JFrame frame = screen.addCanvasToJFrame();
        frame.setVisible(true);

        char counter = 0;
        AsciiCharacter tile;

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

        screen.draw();
    }
}
