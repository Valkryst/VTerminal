package com.valkryst.VTerminal.performance_tests;

import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;

import java.io.IOException;

class GlobalVariables {
    /** Whether to run the test in windowed or fullscreen mode. */
    static final boolean RUN_TESTS_WINDOWED = true;

    /** The number of rows to display. */
    static final int ROWS = 40;
    /** The number of columns to display. */
    static final int COLUMNS = 150;
    /** The total number of cells displayed. */
    static final int TOTAL_CELLS = ROWS * COLUMNS;
    /** The font to use. */
    static Font font;

    /** The amount of draw calls to make before ending the test. */
    static final int MAX_DRAW_CALLS = 1000;

    /** The number of draw calls to make before displaying the latest test information. */
    static final int CALCULATIONS_BEFORE_PRINT = 10;

    static {
        try {
            font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/", 1);
        } catch (final IOException e) {
            e.printStackTrace();
        }

        printInformation();
    }

    private static void printInformation() {
        System.out.println("Globals");
        System.out.println("\tRows = " + ROWS);
        System.out.println("\tColumns = " + COLUMNS);
        System.out.println("\tTotal Cells = " + (ROWS * COLUMNS));
        System.out.println("\n");
    }
}
