package com.valkryst.VTerminal.printer;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.component.Screen;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.awt.Point;

@EqualsAndHashCode
@ToString
public class TrianglePrinter {
    /** The character to print the triangle with. */
    @Getter private char printChar = '█';

    /** The line printer used to print the triangle. */
    private final LinePrinter linePrinter = new LinePrinter();

    /** Constructs a new TrianglePrinter. */
    public TrianglePrinter() {
        linePrinter.setPrintChar(printChar);
    }

    /**
     * Prints a triangle on the screen of a panel using a set of points.
     *
     * The algorithm requires exactly three points. If there are more
     * than three points, the extra points are ignored.
     *
     * @param panel
     *        The panel.
     *
     * @param points
     *        The points.
     *
     * @throws NullPointerException
     *         If the screen or points array is null.
     */
    public void print(final @NonNull Panel panel, final @NonNull Point[] points) {
        print(panel.getScreen(), points);
    }

    /**
     * Prints a triangle on a screen using a set of points.
     *
     * The algorithm requires exactly three points. If there are more
     * than three points, the extra points are ignored.
     *
     * @param screen
     *        The screen.
     *
     * @param points
     *        The points.
     *
     * @throws IllegalArgumentException
     *         If there are fewer than three points.
     *
     * @throws NullPointerException
     *         If the screen or points array is null.
     */
    public void print(final @NonNull Screen screen, @NonNull Point[] points) {
        if (points.length < 3) {
            throw new IllegalArgumentException("A triangle requires three points to be drawn.");
        }

        points = new Point[]{points[0], points[1], points[2], points[0]};
        linePrinter.print(screen, points);
    }

    public void setPrintChar(final char printChar) {
        this.printChar = printChar;
        linePrinter.setPrintChar(printChar);
    }
}
