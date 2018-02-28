package printer;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.printer.TrianglePrinter;

import java.awt.Point;
import java.io.IOException;

public class SampleTrianglePrinter {
    public static void main(final String[] args) throws IOException {
        final Screen screen = new Screen();


        final Point[] points = new Point[] {
                new Point(20, 2),
                new Point(2, 20),
                new Point(2, 10)
        };

        final TrianglePrinter printer = new TrianglePrinter();
        printer.print(screen.getTiles(), points);


        screen.addCanvasToFrame();
    }
}
