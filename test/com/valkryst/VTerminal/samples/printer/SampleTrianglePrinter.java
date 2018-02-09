package com.valkryst.VTerminal.samples.printer;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.printer.TrianglePrinter;

import java.awt.Point;
import java.io.IOException;
import java.net.URISyntaxException;

public class SampleTrianglePrinter {
    public static void main(final String[] args) throws IOException, URISyntaxException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);

        final PanelBuilder builder = new PanelBuilder();
        builder.setFont(font);

        final Panel panel = builder.build();

        final Point[] points = new Point[] {
                new Point(20, 2),
                new Point(2, 20),
                new Point(2, 10)
        };

        final TrianglePrinter printer = new TrianglePrinter();
        printer.print(panel, points);

        panel.draw();
    }
}
