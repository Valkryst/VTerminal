package com.valkryst.VTerminal.samples.component;

import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.builder.component.ScreenBuilder;
import com.valkryst.VTerminal.component.Layer;
import com.valkryst.VTerminal.component.ProgressBar;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.printer.RectanglePrinter;
import com.valkryst.VTerminal.printer.RectangleType;
import org.json.simple.parser.ParseException;

import javax.swing.Timer;
import java.awt.Color;
import java.io.IOException;
import java.net.URISyntaxException;

public class SampleComponentsFromJSON {
    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException, ParseException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);

        final PanelBuilder builder = new PanelBuilder();
        builder.setFont(font);

        final Panel panel = builder.build();

        Thread.sleep(100);

        final ScreenBuilder screenBuilder = new ScreenBuilder();
        screenBuilder.setRadio(panel.getRadio());
        screenBuilder.loadFromJSON(System.getProperty("user.dir") + "/res_test/Sample Screen.json");

        panel.swapScreen(screenBuilder.build());

        // Add button function:
        panel.getScreen().getButtonByID("Click Me Button").setOnClickFunction(() -> {
            System.out.println("Clicked!");
        });

        // Border
        final RectanglePrinter printer = new RectanglePrinter();
        printer.setRectangleType(RectangleType.HEAVY);
        printer.setWidth(80);
        printer.setHeight(24);
        printer.print(panel, 0, 0);

        printer.setWidth(24);
        printer.print(panel, 0, 0);

        printer.setWidth(48);
        printer.print(panel, 0, 0);


        // Loading Bar
        final ProgressBar progressBar = panel.getScreen().getProgressBarByID("Progress Bar");

        final Timer timer = new Timer(1000, e -> {
            int pct = progressBar.getPercentComplete();

            if (pct < 100) {
                pct += 5;
            } else {
                pct = 0;
            }

            progressBar.setPercentComplete(pct);
        });
        timer.start();


        final Layer layerA = new Layer(50, 2, 23, 10);
        for (final AsciiString string : layerA.getStrings()) {
            string.setBackgroundColor(new Color(255, 0, 0, 255));
        }

        final Layer layerB = new Layer(50, 8, 23, 10);
        for (final AsciiString string : layerB.getStrings()) {
            string.setBackgroundColor(new Color(0, 0, 255, 155));
        }

        panel.addComponents(layerA, layerB);


        panel.draw();
    }
}
