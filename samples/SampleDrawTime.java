

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class SampleDrawTime {
    public static void main(final String[] args) throws InterruptedException, IOException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);

        final Screen screen = new Screen(font);
        screen.addCanvasToJFrame();

        List<Long> measurements = new ArrayList<>();

        for(int i = 1 ; i != 10_001 ; i++) {
            final int colorVal = ThreadLocalRandom.current().nextInt(45, 56 + (i / 500));

            // Random colors, chars, flips, and underlines
            final Color foregroundColor = new Color(colorVal, 155, 255, 255);
            final Color backgroundColor = new Color(colorVal, colorVal, colorVal, 255);
            Tile tile;

            for (int y = 0 ; y < screen.getHeight() ; y++) {
                for (int x = 0 ; x < screen.getWidth() ; x++) {
                    tile = screen.getTileAt(x, y);

                    tile.setBackgroundColor(backgroundColor);
                    tile.setForegroundColor(foregroundColor);

                    tile.setCharacter(((char)ThreadLocalRandom.current().nextInt(45, 126)));
                    tile.setFlippedVertically(ThreadLocalRandom.current().nextBoolean());
                    tile.setFlippedHorizontally(ThreadLocalRandom.current().nextBoolean());
                    tile.setUnderlined(ThreadLocalRandom.current().nextBoolean());
                    tile.setHidden(ThreadLocalRandom.current().nextBoolean());
                }
            }

            // Draw and deal with calculations:
            final long timeBeforeDraw = System.nanoTime();

            screen.draw();

            final long timeDifference = System.nanoTime() - timeBeforeDraw;
            measurements.add(timeDifference);

            if (i % 100 == 0) {
                double averageDrawTime = measurements.stream().mapToLong(Long::longValue).sum() / (double) i;
                averageDrawTime /= 1_000_000;

                System.out.println(
                    String.format("Avg Draw Time: %f ms\t\tAvg FPS: %f\t\tTotal Measurements: %d\t\tCached Images: %d",
                            averageDrawTime,
                            1000 / averageDrawTime,
                            i,
                            screen.getImageCache().totalCachedImages())
                );
            }

            Thread.sleep(16);
        }

        // Remove the bottom and top 10% (outliers) of results:
        Collections.sort(measurements);
        final long middleElement = measurements.get(measurements.size() / 2);
        final double lowestElement = middleElement * 0.90;
        final double highestElement = middleElement + (middleElement * 0.90);

        measurements = measurements.stream().filter(val -> val >= lowestElement && val <= highestElement).collect(Collectors.toList());
        double averageDrawTime = measurements.stream().mapToLong(Long::longValue).sum() / (double) measurements.size();
        averageDrawTime /= 1_000_000;

        System.out.println("\nFINAL RESULTS:");
        System.out.println(
                String.format("Avg Draw Time: %f ms\t\tAvg FPS: %f\t\tTotal Measurements: %d\t\tCached Images: %d",
                        averageDrawTime,
                        1000 / averageDrawTime,
                        measurements.size(),
                        screen.getImageCache().totalCachedImages())
        );

        System.exit(0);
    }
}
