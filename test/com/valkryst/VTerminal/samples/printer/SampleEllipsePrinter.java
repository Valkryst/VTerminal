package com.valkryst.VTerminal.samples.printer;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.printer.EllipsePrinter;

import java.io.IOException;
import java.net.URISyntaxException;

public class SampleEllipsePrinter {
    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);

        final PanelBuilder builder = new PanelBuilder();
        builder.setFont(font);

        final Panel panel = builder.build();

        Thread.sleep(100);

        final EllipsePrinter printer = new EllipsePrinter();
        printer.setWidth(6);
        printer.setHeight(8);
        printer.print(panel, 10, 10);

        printer.printFilled(panel, 10, 40);

        panel.draw();
    }
}
