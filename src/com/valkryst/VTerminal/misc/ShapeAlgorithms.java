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
     * Constructs a list, containing the outline and fill, of an
     * ellipse's points.
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
    public static List<Point> getFilledEllipse(final int x, final int y, final int width, final int height) {
        final List<Point> points = getEllipse(x, y, width, height);

        final int xCenter = x + (width / 2);
        final int yCenter = y + (height / 2);

        return recursiveFill(points, xCenter, yCenter);
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
        final List<Point> points = new ArrayList<>();

        int d = 0;

        int dx = Math.abs(toX - fromX);
        int dy = Math.abs(toY - fromY);

        int dx2 = dx << 1;
        int dy2 = dy << 1;

        int ix = fromX < toX ? 1 : -1;
        int iy = fromY < toY ? 1 : -1;

        if (dy <= dx) {
            while(true) {
                points.add(new Point(fromX, fromY));

                if (fromX == toX) {
                    break;
                }

                fromX += ix;
                d += dy2;

                if (d > dx) {
                    fromY += iy;
                    d -= dx2;
                }
            }
        } else {
            while (true) {
                points.add(new Point(fromX, fromY));

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
     * Constructs a list, containing the outline, of a rectangle's
     * points.
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

    /**
     * Constructs a list, containing the outline and fill, of a
     * rectangle's points.
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
    public static List<Point> getFilledRectangle(final int x, final int y, final int width, final int height) {
        final List<Point> points = getRectangle(x, y, width, height);

        for (int xCounter = x + 1 ; xCounter < width - 1 ; xCounter++) {
            for (int yCounter = y + 1 ; yCounter < height - 1 ; yCounter++) {
                points.add(new Point(xCounter, yCounter));
            }
        }

        return points;
    }

    /**
     * Recursively fills an area on the screen bounded by the set of
     * input points.
     *
     * @param points
     *        The border points.
     *
     * @param x
     *         The x-axis (column) coordinate of the current point.
     *
     * @param y
     *         The y-axis (row) coordinate of the current point.
     *
     * @return
     *        The list of filled points.
     */
    public static List<Point> recursiveFill(final List<Point> points, final int x, final int y) {
        boolean pointExists = false;

        for (final Point point : points) {
            if (point.x == x && point.y == y) {
                pointExists = true;
                break;
            }
        }

        if (pointExists == false) {
            points.add(new Point(x, y));

            recursiveFill(points, x + 1, y);
            recursiveFill(points, x - 1, y);
            recursiveFill(points, x, y + 1);
            recursiveFill(points, x, y - 1);
        }

        return points;
    }
}
