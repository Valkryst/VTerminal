package printer;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.printer.EllipsePrinter;

import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.net.URISyntaxException;

public class SampleEllipsePrinter {
    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt", 1);

        final Dimension dimensions = new Dimension(60, 26);
        final Screen screen = new Screen(dimensions, font);


        final EllipsePrinter printer = new EllipsePrinter();
        printer.setWidth(6);
        printer.setHeight(8);
        printer.print(screen.getTiles(), new Point(10, 10));

        printer.printFilled(screen.getTiles(), new Point(10, 40));


        screen.addCanvasToJFrame();
    }
}
