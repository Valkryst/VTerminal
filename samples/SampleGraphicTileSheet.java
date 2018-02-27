import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;

import java.io.IOException;

public class SampleGraphicTileSheet {
    public static void main(final String[] args) throws IOException {
        final Font font = FontLoader.loadFontFromJar("Tiles/Nevanda Nethack/bitmap.png", "Tiles/Nevanda Nethack/data.fnt", 1);

        final Screen screen = new Screen(50, 22, font);

        char counter = 0;

        screen.getTiles().convertToGraphicTileGrid();

        for (int y = 0 ; y < screen.getHeight() ; y++) {
            for (int x = 0 ; x < screen.getWidth() ; x++) {
                for (char i = counter ; i < Character.MAX_VALUE ; i++) {
                    if (font.isCharacterSupported(i)) {
                        screen.getTileAt(x, y).setCharacter(counter);
                        counter++;
                        break;
                    }

                    counter++;
                }
            }
        }

        screen.addCanvasToJFrame();
    }
}
