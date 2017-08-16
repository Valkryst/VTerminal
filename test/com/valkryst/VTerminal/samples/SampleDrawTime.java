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

public class SampleDrawTime {
    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);

        final PanelBuilder builder = new PanelBuilder();
        builder.setFont(font);

        final Panel panel = builder.build();

        int temp = 45;

        long total = 1;
        long counter = 1;

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
            final long res = System.currentTimeMillis() - bef;
            counter++;
            total += res;

            if (temp == 45) {
                System.out.println("Draw Took:\t" + res + "ms\t\tAvg Is:\t" + (total / counter) + "ms");
            }

            Thread.sleep(32);
        }
    }
}
