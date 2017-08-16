package com.valkryst.VTerminal.samples;

import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;

import java.awt.Color;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.ThreadLocalRandom;

public class SampleFPS {
    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);

        final PanelBuilder builder = new PanelBuilder();
        builder.setFont(font);

        final Panel panel = builder.build();

        double smoothing = 0.9; // larger=more smoothing
        double measurement = 0.0;

        int temp = 45;

        while(true) {
            panel.getScreen().clear((char)temp);
            panel.getScreen().setForegroundColor(new Color(255, 155, temp, 255));
            panel.getScreen().setBackgroundColor(new Color(temp, 155, 255, 255));

            temp++;

            if (temp > 55) {
                temp = 45;
            }

            // Random Flips & Underlines:
            for (final AsciiString string : panel.getScreen().getStrings()) {
                string.setFlippedVertically(ThreadLocalRandom.current().nextBoolean());
                string.setFlippedHorizontally(ThreadLocalRandom.current().nextBoolean());
                string.setUnderlined(ThreadLocalRandom.current().nextBoolean());
            }

            final long bef = System.currentTimeMillis();
            panel.draw();

            final double current = 1000 / Math.max((System.currentTimeMillis() - bef), 1);
            measurement = (measurement * smoothing) + (current * (1.0-smoothing));

            if (temp == 45) {
                System.out.println("Estimated FPS based on Past Render Times:\t" + measurement);
            }
            Thread.sleep(100);
        }
    }
}
