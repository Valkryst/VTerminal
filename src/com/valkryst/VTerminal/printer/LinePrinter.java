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
     * @param fromX
     *         The x-axis (column) coordinate of the start point of the line.
     *
     * @param fromY
     *         The y-axis (row) coordinate of the start point of the line.
     *
     * @param toX
     *         The x-axis (column) coordinate of the end point of the line.
     *
     * @param toY
     *         The y-axis (row) coordinate of the end point of the line.
     *
     * @throws NullPointerException
     *         If the panel is null.
     */
    public void print(final @NonNull Panel panel, final int fromX, final int fromY, final int toX, final int toY) {
        print(panel.getScreen(), fromX, fromY, toX, toY);
    }


    /**
     * Prints a line on a screen.
     *
     * @param screen
     *         The screen.
     *
     * @param fromX
     *         The x-axis (column) coordinate of the start point of the line.
     *
     * @param fromY
     *         The y-axis (row) coordinate of the start point of the line.
     *
     * @param toX
     *         The x-axis (column) coordinate of the end point of the line.
     *
     * @param toY
     *         The y-axis (row) coordinate of the end point of the line.
     *
     * @throws NullPointerException
     *         If the panel is null.
     */
    public void print(final @NonNull Screen screen, int fromX, int fromY, final int toX, final int toY) {
        printLine(screen, fromX, fromY, toX, toY);
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

            print(screen, previous.x, previous.y, current.x, current.y);
        }
    }

    /**
     * Prints a line on a screen.
     *
     * @param screen
     *        The screen.
     *
     * @param fromX
     *         The x-axis (column) coordinate of the start point of the line.
     *
     * @param fromY
     *         The y-axis (row) coordinate of the start point of the line.
     *
     * @param toX
     *         The x-axis (column) coordinate of the end point of the line.
     *
     * @param toY
     *         The y-axis (row) coordinate of the end point of the line.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    private void printLine(final @NonNull Screen screen, final int fromX, final int fromY, final int toX, final int toY) {
        for (final Point point : ShapeAlgorithms.getLine(fromX, fromY, toX, toY)) {
            screen.write(printChar, point);
        }
    }
}
