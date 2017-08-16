package com.valkryst.VTerminal.samples;

import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;

import java.awt.Color;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.concurrent.ThreadLocalRandom;

public class SampleDrawTime {
    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);

        final PanelBuilder builder = new PanelBuilder();
        builder.setFont(font);

        final Panel panel = builder.build();

        int temp = 45;

        BigInteger measurementsTotal = BigInteger.ZERO;
        long measurementsCounter = 1;

        while(true) {
            panel.getScreen().clear((char)temp);
            panel.getScreen().setForegroundColor(new Color(255, 155, temp, 255));
            panel.getScreen().setBackgroundColor(new Color(temp, temp, temp, 255));

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

            // Draw and deal with calculations:
            final long timeBeforeDraw = System.nanoTime();

            panel.draw();

            final long timeAfterDraw = System.nanoTime();
            final long timeDifference = timeAfterDraw - timeBeforeDraw;
            measurementsTotal = measurementsTotal.add(BigInteger.valueOf(timeDifference));
            measurementsCounter++;

            if (measurementsCounter % 50 == 0) {
                double averageDrawTime = measurementsTotal.divide(BigInteger.valueOf(measurementsCounter)).doubleValue();
                averageDrawTime /= 1_000_000;

                System.out.println(
                    String.format("Avg Draw Time: %f ms\t\tAvg FPS: %f\t\tTotal Measurements: %d",
                            averageDrawTime,
                            1000 / averageDrawTime,
                            measurementsCounter)
                );
            }

            Thread.sleep(16);
        }
    }
}
