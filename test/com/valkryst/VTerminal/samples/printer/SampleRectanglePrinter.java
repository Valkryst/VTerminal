package com.valkryst.VTerminal.samples.printer;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.printer.RectanglePrinter;
import com.valkryst.VTerminal.printer.RectangleType;

import java.awt.Point;
import java.io.IOException;
import java.net.URISyntaxException;

public class SampleRectanglePrinter {
    public static void main(final String[] args) throws IOException, URISyntaxException {
        final Panel panel = new PanelBuilder().build();

        final RectanglePrinter printer = new RectanglePrinter();

        printer.setRectangleType(RectangleType.SIMPLE);
        printer.setTitle("Type: Simple");
        printer.setWidth(48);
        printer.setHeight(24);
        printer.print(panel, new Point(0, 0));

        printer.setRectangleType(RectangleType.THIN);
        printer.setTitle("Type: Thin");
        printer.setWidth(42);
        printer.setHeight(20);
        printer.print(panel, new Point(2, 2));

        printer.setRectangleType(RectangleType.HEAVY);
        printer.setTitle("Type: Heavy");
        printer.setWidth(38);
        printer.setHeight(16);
        printer.print(panel, new Point(4, 4));

        printer.setRectangleType(RectangleType.MIXED_HEAVY_HORIZONTAL);
        printer.setTitle("Type: Mixed Heavy Horizontal");
        printer.setWidth(34);
        printer.setHeight(12);
        printer.print(panel, new Point(6, 6));

        printer.setRectangleType(RectangleType.MIXED_HEAVY_VERTICAL);
        printer.setTitle("Type: Mixed Heavy Vertical");
        printer.setWidth(30);
        printer.setHeight(8);
        printer.print(panel, new Point(8, 8));


        // Right Rect
        printer.setRectangleType(RectangleType.HEAVY);
        printer.setTitle("");
        printer.setWidth(31);
        printer.setHeight(24);
        printer.print(panel, new Point(49, 0));

        // Creates top box in right rect
        printer.setHeight(6);
        printer.print(panel, new Point(49, 0));

        // Creates middle box in right rect
        printer.setHeight(12);
        printer.print(panel, new Point(49, 0));

        // Creates bottom box in right rect
        printer.setHeight(18);
        printer.print(panel, new Point(49, 0));

        // Creates left box in right rect
        printer.setWidth(6);
        printer.setHeight(24);
        printer.print(panel, new Point(49, 0));

        // Creates halves top left box in right rect
        printer.setWidth(3);
        printer.setHeight(6);
        printer.print(panel, new Point(49, 0));

        // Creates box spanning the right boxes in the right rect
        printer.setWidth(8);
        printer.setHeight(18);
        printer.print(panel, new Point(59, 3));

        panel.draw();
    }
}
