package printer;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.printer.TrianglePrinter;

import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.net.URISyntaxException;

public class SampleTrianglePrinter {
    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt", 1);

        final Dimension dimensions = new Dimension(60, 26);
        final Screen screen = new Screen(dimensions, font);


        final Point[] points = new Point[] {
                new Point(20, 2),
                new Point(2, 20),
                new Point(2, 10)
        };

        final TrianglePrinter printer = new TrianglePrinter();
        printer.print(screen.getTiles(), points);


        screen.addCanvasToJFrame();
    }
}
