import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.Tile;

import java.awt.*;
import java.io.IOException;

public class Rerender_Basic_AverageCase_10 {
    public static void main(final String[] args) throws IOException {
        final Screen screen = new Screen(GlobalVariables.COLUMNS, GlobalVariables.ROWS);

        if (GlobalVariables.RUN_TESTS_WINDOWED) {
            screen.addCanvasToFrame();
        } else {
            screen.addCanvasToFullScreenFrame(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
        }

        /*
         * In an average-case scenario, there are few new tiles that need to be generated. The majority of the
         * tiles do not change at-all. To test this scenario, we want to keep 90% of the screen unchanged while
         * generating a few new tiles which are reused to draw the rest of the screen.
         *
         * For this test, we will ensure that 10% of the cells change and 90% stay the same.
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

        final int unchangedTiles = (int) (GlobalVariables.TOTAL_CELLS * 0.90);
        int unchangedTilesCounter = 0;

        for (int i = 0 ; i < GlobalVariables.MAX_DRAW_CALLS ; i++) {
            for (int y = 0; y < GlobalVariables.ROWS; y++) {
                for (int x = 0; x < GlobalVariables.COLUMNS; x++) {
                    if (unchangedTilesCounter == unchangedTiles) {
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

                        if (backgroundRGB == 5) {
                            backgroundRGB = 0;
                        }

                        if (foregroundRGB == Integer.MAX_VALUE - 5) {
                            foregroundRGB = Integer.MAX_VALUE;
                        }
                    } else {
                        // Always use the same char for similar tiles, so we can ensure it's in the cache.
                        final Tile tile = screen.getTileAt(x, y);
                        tile.setCharacter('X');
                        tile.setBackgroundColor(Color.BLACK);
                        tile.setForegroundColor(Color.WHITE);

                        unchangedTilesCounter++;
                    }
                }
            }

            // Reset Similar Tiles Counter
            unchangedTilesCounter = 0;

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
                System.out.println("\tUnchanged Tiles = " + unchangedTiles + "/" + GlobalVariables.TOTAL_CELLS + " AKA " + (unchangedTiles /  (double) GlobalVariables.TOTAL_CELLS) + "%");
                System.out.println("\tAverage Draw Time = " + (avgSum / avgTotalSamples) + "ms");
                System.out.println("\tAverage Draw Time Per Cell = " + ((avgSum / avgTotalSamples) / GlobalVariables.TOTAL_CELLS) + "ms");
            }
        }
    }
}
