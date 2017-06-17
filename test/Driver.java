import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;

import java.io.IOException;
import java.net.URISyntaxException;

public class Driver {

    public static void main(String[] args) throws IOException {
        try {
            final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt");
            final Panel panel = new PanelBuilder().setAsciiFont(font)
                                                  .setWidthInCharacters(24)
                                                  .setHeightInCharacters(24)
                                                  .build();

            for (int i = 0 ; i < 24 ; i++) {
                panel.getCurrentScreen().write('A', i, i);

                panel.getCurrentScreen().write((char)(48 + (i%10)), i + 1, i);
            }
            panel.draw();
        } catch (final IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
}