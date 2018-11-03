import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.Tile;

import java.awt.*;
import java.io.IOException;

public class Rerender_Basic_WorstCase {
    public static void main(final String[] args) throws IOException {
        final Screen screen = new Screen(GlobalVariables.COLUMNS, GlobalVariables.ROWS);

        if (GlobalVariables.RUN_TESTS_WINDOWED) {
            screen.addCanvasToFrame();
        } else {
            screen.addCanvasToFullScreenFrame(GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice());
        }

        /*
         * In order to test the worst-case scenario, each cell needs to have a unique back/foreground color
         * and character combination, so new tiles have to be generated for each cell on each draw call.
         *
         * This forces the render process to be as slow as possible.
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

        for (int i = 0 ; i < GlobalVariables.MAX_DRAW_CALLS ; i++) {
            for (int y = 0; y < GlobalVariables.ROWS; y++) {
                for (int x = 0; x < GlobalVariables.COLUMNS; x++) {
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
                }
            }

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
                System.out.println("\tAverage Draw Time = " + (avgSum / avgTotalSamples) + "ms");
                System.out.println("\tAverage Draw Time Per Cell = " + ((avgSum / avgTotalSamples) / GlobalVariables.TOTAL_CELLS) + "ms");
            }
        }
    }
}
