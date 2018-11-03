import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.Tile;

import java.awt.*;
import java.io.IOException;

public class Rerender_Basic_BadCase_75 {
    public static void main(final String[] args) throws IOException {
        final Screen screen = new Screen(GlobalVariables.COLUMNS, GlobalVariables.ROWS);

        if (GlobalVariables.RUN_TESTS_WINDOWED) {
            screen.addCanvasToFrame();
        } else {
            screen.addCanvasToFullScreenFrame(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
        }

        /*
         * In a bad-case scenario, there are many new tiles that need to be generated and only a few tiles
         * which do not need to be generated. To test this scenario, we want to reuse some existing tiles and
         * generate some new tiles.
         *
         * The tile cache should kick-in when reusing an existing tile, so performance should be better than the
         * worst case. The more tiles, which already exist in the cache, that exist, the better the performance
         * will be.
         *
         * For this test, we will ensure that 75% of the cells change and 25% stay the same.
         *
         * ---
         *
         * Because we can't guarantee that the test font has more than 128 unique characters
         * (ASCII limitation), we'll stick to the characters 0-127.
         *
         */
        double avgSum = 0;
        double avgTotalSamples = 0;

        byte charCounter = 0;

        int backgroundRGB = 0;
        int foregroundRGB = Integer.MAX_VALUE;

        final int similarTiles = (int) (GlobalVariables.TOTAL_CELLS * 0.25);
        int similarTilesCounter = 0;

        for (int i = 0 ; i < GlobalVariables.MAX_DRAW_CALLS ; i++) {
            for (int y = 0; y < GlobalVariables.ROWS; y++) {
                for (int x = 0; x < GlobalVariables.COLUMNS; x++) {
                    if (similarTilesCounter == similarTiles) {
                        final Tile tile = screen.getTileAt(x, y);
                        tile.setCharacter((char) charCounter);
                        tile.setBackgroundColor(new Color(backgroundRGB));
                        tile.setForegroundColor(new Color(foregroundRGB));

                        // Move to the next character.
                        charCounter++;

                        if (charCounter == Byte.MAX_VALUE) {
                            charCounter = 0;
                        }

                        // Move to the next back/foreground colors.
                        backgroundRGB++;
                        foregroundRGB--;

                        if (backgroundRGB == Integer.MAX_VALUE) {
                            backgroundRGB = 0;
                        }

                        if (foregroundRGB == 0) {
                            foregroundRGB = Integer.MAX_VALUE;
                        }
                    } else {
                        // Always use the same char for similar tiles, so we can ensure it's in the cache.
                        final Tile tile = screen.getTileAt(x, y);
                        tile.setCharacter((char) charCounter);
                        tile.setBackgroundColor(Color.BLACK);
                        tile.setForegroundColor(Color.WHITE);

                        similarTilesCounter++;
                    }
                }
            }

            // Reset Similar Tiles Counter
            similarTilesCounter = 0;

            // Measure & Calculate Draw Time
            final long beforeMs = System.currentTimeMillis();
            try {
                screen.draw();
            } catch (final IllegalStateException e) {
                break;
            }
            final long afterMs = System.currentTimeMillis();

            avgSum += afterMs - beforeMs;
            avgTotalSamples++;

            // Display Draw Time
            if (avgTotalSamples % GlobalVariables.CALCULATIONS_BEFORE_PRINT == 0) {
                System.out.println("Test Information");
                System.out.println("\tTotal Samples (Calls to Draw) = " + avgTotalSamples);
                System.out.println("\tSimilar Tiles = " + similarTiles + "/" + GlobalVariables.TOTAL_CELLS + " AKA " + (similarTiles /  (double) GlobalVariables.TOTAL_CELLS) + "%");
                System.out.println("\tAverage Draw Time = " + (avgSum / avgTotalSamples) + "ms");
                System.out.println("\tAverage Draw Time Per Cell = " + ((avgSum / avgTotalSamples) / GlobalVariables.TOTAL_CELLS) + "ms");
            }
        }
    }
}
