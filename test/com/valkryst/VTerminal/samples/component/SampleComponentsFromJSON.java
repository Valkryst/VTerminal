package com.valkryst.VTerminal.samples.component;

import com.valkryst.VJSON.VJSONLoader;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.builder.component.ScreenBuilder;
import com.valkryst.VTerminal.component.ProgressBar;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import org.json.simple.parser.ParseException;

import javax.swing.Timer;
import java.io.IOException;
import java.net.URISyntaxException;

public class SampleComponentsFromJSON {
    public static void main(final String[] args) throws IOException, URISyntaxException, ParseException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);

        final PanelBuilder builder = new PanelBuilder();
        builder.setFont(font);

        final Panel panel = builder.build();

        final ScreenBuilder screenBuilder = new ScreenBuilder();
        VJSONLoader.loadFromJSON(screenBuilder, System.getProperty("user.dir") + "/res_test/Sample Screen.json");

        panel.swapScreen(screenBuilder.build());

        // Setup Button Functionality:
        panel.getScreen().getButtonByID("Click Me Button").setOnClickFunction(() -> {
            System.out.println("Clicked!");
        });


        // Setup Loading Bar Functionality:
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


        panel.draw();
    }
}
