package com.valkryst.VTerminal.samples;

import com.valkryst.VTerminal.AsciiCharacter;
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

        for(int i = 1 ; i != 4_001 ; i++) {
            panel.getScreen().clear((char)temp);
            panel.getScreen().setForegroundColor(new Color(temp, 155, 255, 255));
            panel.getScreen().setBackgroundColor(new Color(temp, temp, temp, 255));

            temp++;

            if (temp > 55) {
                temp = 45;
            }

            // Random Flips & Underlines:
            for (final AsciiString string : panel.getScreen().getStrings()) {
                for (final AsciiCharacter character : string.getCharacters()) {
                    character.setFlippedVertically(ThreadLocalRandom.current().nextBoolean());
                    character.setFlippedHorizontally(ThreadLocalRandom.current().nextBoolean());
                    character.setUnderlined(ThreadLocalRandom.current().nextBoolean());
                }
            }

            // Draw and deal with calculations:
            final long timeBeforeDraw = System.nanoTime();

            panel.draw();

            final long timeAfterDraw = System.nanoTime();
            final long timeDifference = timeAfterDraw - timeBeforeDraw;
            measurementsTotal = measurementsTotal.add(BigInteger.valueOf(timeDifference));

            if (i % 50 == 0) {
                double averageDrawTime = measurementsTotal.divide(BigInteger.valueOf(i)).doubleValue();
                averageDrawTime /= 1_000_000;

                System.out.println(
                    String.format("Avg Draw Time: %f ms\t\tAvg FPS: %f\t\tTotal Measurements: %d",
                            averageDrawTime,
                            1000 / averageDrawTime,
                            i)
                );
            }

            Thread.sleep(16);
        }

        System.exit(0);
    }
}
