package com.valkryst.VTerminal.samples.printer;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.printer.EllipsePrinter;

import java.awt.Point;
import java.io.IOException;
import java.net.URISyntaxException;

public class SampleEllipsePrinter {
    public static void main(final String[] args) throws IOException, URISyntaxException {
        final Panel panel = new PanelBuilder().build();

        final EllipsePrinter printer = new EllipsePrinter();
        printer.setWidth(6);
        printer.setHeight(8);
        printer.print(panel, new Point(10, 10));

        printer.printFilled(panel, new Point(10, 40));

        panel.draw();
    }
}
