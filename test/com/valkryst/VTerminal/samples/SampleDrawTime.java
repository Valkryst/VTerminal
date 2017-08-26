package com.valkryst.VTerminal.samples;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;

import java.awt.Color;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SampleDrawTime {
    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);

        final PanelBuilder builder = new PanelBuilder();
        builder.setFont(font);

        final Panel panel = builder.build();

        List<Long> measurements = new ArrayList<>();

        for(int i = 1 ; i != 10_001 ; i++) {
            final int colorVal = ThreadLocalRandom.current().nextInt(45, 56 + (i / 500));
            panel.getScreen().setForegroundColor(new Color(colorVal, 155, 255, 255));
            panel.getScreen().setBackgroundColor(new Color(colorVal, colorVal, colorVal, 255));


            // Random Characters, Flips, and Underlines:
            for (final AsciiString string : panel.getScreen().getStrings()) {
                for (final AsciiCharacter character : string.getCharacters()) {
                    character.setCharacter(((char)ThreadLocalRandom.current().nextInt(45, 126)));
                    character.setFlippedVertically(ThreadLocalRandom.current().nextBoolean());
                    character.setFlippedHorizontally(ThreadLocalRandom.current().nextBoolean());
                    character.setUnderlined(ThreadLocalRandom.current().nextBoolean());
                    character.setHidden(ThreadLocalRandom.current().nextBoolean());
                }
            }

            // Draw and deal with calculations:
            final long timeBeforeDraw = System.nanoTime();

            panel.draw();

            final long timeAfterDraw = System.nanoTime();
            final long timeDifference = timeAfterDraw - timeBeforeDraw;

            if (i > 1000) {
                measurements.add(timeDifference);

                if (i % 50 == 0) {
                    double averageDrawTime = measurements.stream().mapToLong(Long::longValue).sum() / (double) i;
                    averageDrawTime /= 1_000_000;

                    System.out.println(
                            String.format("Avg Draw Time: %f ms\t\tAvg FPS: %f\t\tTotal Measurements: %d\t\tCached Images: %d",
                                    averageDrawTime,
                                    1000 / averageDrawTime,
                                    i,
                                    panel.getImageCache().totalCachedImages())
                    );
                }
            } else {
                if (i % 50 == 0) {
                    System.out.println("Ignoring first " + i + "/1000 draw calls.");
                }
            }

            Thread.sleep(16);
        }

        // Remove the bottom and top 10% (outliers) of results:
        double averageDrawTime = measurements.stream().mapToLong(Long::longValue).sum() / (double) measurements.size();
        averageDrawTime /= 1_000_000;

        System.out.println("\nFINAL RESULTS:");
        System.out.println(
                String.format("Avg Draw Time: %f ms\t\tAvg FPS: %f\t\tTotal Measurements: %d\t\tCached Images: %d",
                        averageDrawTime,
                        1000 / averageDrawTime,
                        measurements.size(),
                        panel.getImageCache().totalCachedImages())
        );

        System.exit(0);
    }
}
