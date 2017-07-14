package com.valkryst.VTerminal.samples;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.printer.RectanglePrinter;
import com.valkryst.VTerminal.printer.RectangleType;

import java.io.IOException;
import java.net.URISyntaxException;

public class SampleRectanglePrinter {
    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException {
        System.setProperty("sun.java2d.opengl", "true");
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);
        final Panel panel = new PanelBuilder().setFont(font).build();

        Thread.sleep(100);

        final RectanglePrinter printer = new RectanglePrinter();
        printer.setRectangleType(RectangleType.HEAVY);
        printer.setWidth(10);
        printer.setHeight(10);

        printer.print(panel, 0, 0);
        printer.print(panel, 0, 1);
        printer.print(panel, 1, 0);
        printer.print(panel, 1, 1);
        printer.print(panel, 3, 10);


        panel.draw();
    }
}
