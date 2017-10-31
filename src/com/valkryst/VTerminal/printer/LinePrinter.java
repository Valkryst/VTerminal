package com.valkryst.VTerminal.printer;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.component.Screen;
import com.valkryst.VTerminal.misc.ShapeAlgorithms;
import lombok.*;

import java.awt.Point;

@EqualsAndHashCode
@ToString
public class LinePrinter {
    /** The character to print the line with. */
    @Getter @Setter private char printChar = '█';

    /**
     * Prints a line on the screen of a panel.
     *
     * @param panel
     *         The panel.
     *
     * @param from
     *         The start point of the line.
     *
     * @param to
     *         The end point of the line.
     *
     * @throws NullPointerException
     *         If the panel is null.
     */
    public void print(final @NonNull Panel panel, final Point from, final Point to) {
        print(panel.getScreen(), from, to);
    }


    /**
     * Prints a line on a screen.
     *
     * @param screen
     *         The screen.
     *
     * @param from
     *         The start point of the line.
     *
     * @param to
     *         The end point of the line.
     *
     * @throws NullPointerException
     *         If the panel is null.
     */
    public void print(final @NonNull Screen screen,final Point from, final Point to) {
        printLine(screen, from, to);
    }

    /**
     * Prints one or more lines on the screen of a panel using a set of points.
     *
     * The algorithm requires a minimum of two points.
     *
     * The algorithm draws a line from the first point to the second point,
     * then from the second point to the third point, and so on.
     *
     * @param panel
     *        The panel.
     *
     * @param points
     *        The points.
     *
     * @throws NullPointerException
     *         If the panel or points array is null.
     */
    public void print(final @NonNull Panel panel, final @NonNull Point[] points) {
        print(panel.getScreen(), points);
    }

    /**
     * Prints one or more lines on a screen using a set of points.
     *
     * The algorithm requires a minimum of two points.
     *
     * The algorithm draws a line from the first point to the second point,
     * then from the second point to the third point, and so on.
     *
     * @param screen
     *        The screen.
     *
     * @param points
     *        The points.
     *
     * @throws IllegalArgumentException
     *         If there are fewer than two points.
     *
     * @throws NullPointerException
     *         If the screen or points array is null.
     */
    public void print(final @NonNull Screen screen, final @NonNull Point[] points) {
        if (points.length < 2) {
            throw new IllegalArgumentException("A line requires at-least two points to be drawn.");
        }

        for (int i = 1 ; i < points.length ; i++) {
            final Point previous = points[i - 1];
            final Point current = points[i];

            print(screen, previous, current);
        }
    }

    /**
     * Prints a line on a screen.
     *
     * @param screen
     *        The screen.
     *
     * @param from
     *         The start point of the line.
     *
     * @param to
     *         The end point of the line.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    private void printLine(final @NonNull Screen screen, final Point from, final Point to) {
        for (final Point point : ShapeAlgorithms.getLine(from.x, from.y, to.x, to.y)) {
            screen.write(printChar, point);
        }
    }
}
