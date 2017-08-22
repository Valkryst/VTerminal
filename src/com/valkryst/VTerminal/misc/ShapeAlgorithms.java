package com.valkryst.VTerminal.misc;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public final class ShapeAlgorithms {
    // Prevent users from creating an instance.
    private ShapeAlgorithms() {}

    /**
     * Constructs a list, containing the outline, of an ellipse's points
     * by using the Bresenham algorithm,
     *
     * @param x
     *        The x-axis (column) coordinate of the top-left character.
     *
     * @param y
     *        The y-axis (row) coordinate of the top-left character.
     *
     * @param width
     *        The width.
     *
     * @param height
     *        The height.
     *
     * @return
     *        The list of points.
     */
    public static List<Point> getEllipse(final int x, final int y, final int width, final int height) {
        final List<Point> points = new ArrayList<>();

        int a2 = width * width;
        int b2 = height * height;
        int fa2 = 4 * a2;
        int fb2 = 4 * b2;

        int dx = 0;
        int dy = height;
        int sigma = 2 * b2 + a2 * (1 - 2 * height);

        while (b2 * dx <= a2 * dy) {
            points.add(new Point(x + dx, y + dy));
            points.add(new Point(x - dx, y + dy));
            points.add(new Point(x + dx, y - dy));
            points.add(new Point(x - dx, y - dy));

            if (sigma >= 0) {
                sigma += fa2 * (1 - dy);
                dy--;
            }

            sigma += b2 * ((4 * dx) + 6);
            dx++;
        }


        dx = width;
        dy = 0;
        sigma = 2 * a2 + b2 * (1 - 2 * width);

        while (a2 * dy <= b2 * dx) {
            points.add(new Point(x + dx, y + dy));
            points.add(new Point(x - dx, y + dy));
            points.add(new Point(x + dx, y - dy));
            points.add(new Point(x - dx, y - dy));

            if (sigma >= 0) {
                sigma += fb2 * (1 - dx);
                dx--;
            }

            sigma += a2 * ((4 * dy) + 6);
            dy++;
        }

        return points;
    }

    /**
     * Constructs a list, containing the path, of a line's points by
     * using the Bresenham algorithm,
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
     * @return
     *        The list of points.
     */
    public static List<Point> getLine(int fromX, int fromY, final int toX, final int toY) {
        // Faster algorithm for vertical line:
        if (fromX == toX) {
            return getVerticalLine(fromX, fromY, toY);
        }

        // Faster algorithm for horizontal line:
        if (fromY == toY) {
            return getHorizontalLine(fromX, fromY, toX);
        }

        final List<Point> points = new ArrayList<>();

        // delta of exact value and rounded value of the dependant variable
        int d = 0;

        int dy = Math.abs(toY - fromY);
        int dx = Math.abs(toX - fromX);

        int dy2 = (dy << 1); // slope scaling factors to avoid floating
        int dx2 = (dx << 1); // point

        int ix = fromX < toX ? 1 : -1; // increment direction
        int iy = fromY < toY ? 1 : -1;

        while (true) {
            points.add(new Point(fromX, fromY));

            if (dy <= dx) {
                if (fromX == toX) {
                    break;
                }

                fromX += ix;
                d += dy2;

                if (d > dx) {
                    fromY += iy;
                    d -= dx2;
                }
            } else {
                if (fromY == toY) {
                    break;
                }

                fromY += iy;
                d += dx2;

                if (d > dy) {
                    fromX += ix;
                    d -= dy2;
                }
            }
        }

        return points;
    }

    /**
     * Constructs a list, containing the path, of a horizontal line's points,
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
     * @return
     *        The list of points.
     */
    public static List<Point> getHorizontalLine(final int fromX, final int fromY, final int toX) {
        final List<Point> points = new ArrayList<>();

        if (fromX < toX) {
            for (int x = fromX; x < toX; x++) {
                points.add(new Point(x, fromY));
            }
        } else {
            for (int x = toX; x < fromX; x++) {
                points.add(new Point(x, fromY));
            }
        }

        return points;
    }

    /**
     * Constructs a list, containing the path, of a vertical line's points,
     *
     * @param fromX
     *         The x-axis (column) coordinate of the start point of the line.
     *
     * @param fromY
     *         The y-axis (row) coordinate of the start point of the line.
     *
     * @param toY
     *         The y-axis (row) coordinate of the end point of the line.
     *
     * @return
     *        The list of points.
     */
    public static List<Point> getVerticalLine(final int fromX, final int fromY, final int toY) {
        final List<Point> points = new ArrayList<>();

        if (fromY < toY) {
            for (int y = fromY; y < toY; y++) {
                points.add(new Point(fromX, y));
            }
        } else {
            for (int y = toY; y < fromY; y++) {
                points.add(new Point(fromX, y));
            }
        }

        return points;
    }

    /**
     * Constructs a list, containing the outline, of a rectangle's points,
     *
     * @param x
     *         The x-axis (column) coordinate of the top-left character.
     *
     * @param y
     *         The y-axis (row) coordinate of the top-left character.
     *
     * @param width
     *        The width.
     *
     * @param height
     *        The height.
     *
     * @return
     *        The list of points.
     */
    public static List<Point> getRectangle(final int x, final int y, final int width, final int height) {
        final List<Point> points = new ArrayList<>();

        final int lastRow = y + height - 1;
        final int lastColumn = x + width - 1;

        // Corners:
        points.add(new Point(x, y));
        points.add(new Point(lastColumn, y));
        points.add(new Point(x, lastRow));
        points.add(new Point(lastColumn, lastRow));

        // Left/Right Sides:
        for (int i = 1 ; i < height - 1 ; i++) {
            points.add(new Point(x, y + i));
            points.add(new Point(lastColumn, y + i));
        }

        // Top/Bottom Sides:
        for (int i = 1 ; i < width - 1 ; i++) {
            points.add(new Point(x + i, y));
            points.add(new Point(x + i, lastRow));
        }

        return points;
    }
}
