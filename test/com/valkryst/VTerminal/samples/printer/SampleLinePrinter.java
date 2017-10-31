package com.valkryst.VTerminal.samples.printer;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.misc.ShapeAlgorithms;
import com.valkryst.VTerminal.printer.LinePrinter;

import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class SampleLinePrinter {
    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);

        final PanelBuilder builder = new PanelBuilder();
        builder.setFont(font);

        final Panel panel = builder.build();

        Thread.sleep(100);


        final List<Point> circlePoints = ShapeAlgorithms.getEllipse(new Point(10, 10), new Dimension(5, 5));
        final LinePrinter printer = new LinePrinter();

        for (final Point point : circlePoints) {
            printer.print(panel, new Point(10, 10), point);
        }

        panel.draw();
    }
}
