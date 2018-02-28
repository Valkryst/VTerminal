package printer;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.printer.EllipsePrinter;

import java.awt.Point;
import java.io.IOException;

public class SampleEllipsePrinter {
    public static void main(final String[] args) throws IOException {
        final Screen screen = new Screen();


        final EllipsePrinter printer = new EllipsePrinter();
        printer.setWidth(6);
        printer.setHeight(8);
        printer.print(screen.getTiles(), new Point(10, 10));

        printer.printFilled(screen.getTiles(), new Point(10, 40));


        screen.addCanvasToFrame();
    }
}
