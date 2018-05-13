package com.valkryst.VTerminal.misc;

import java.awt.Dimension;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

public final class ShapeAlgorithms {
    // Prevent users from creating an instance.
    private ShapeAlgorithms() {}

    /**
     * Constructs a list, containing the outline, of an ellipse's points by using
     * the Bresenham algorithm,
     *
     * @param position
     *        The x/y-axis (column/row) coordinates of the top-left character.
     *
     * @param dimension
     *        The width/height.
     *
     * @return
     *        The list of points.
     */
    public static List<Point> getEllipse(final Point position, final Dimension dimension) {
        final List<Point> points = new LinkedList<>();

        final int x = position.x;
        final int y = position.y;
        final int width = dimension.width;
        final int height = dimension.height;

        final int a2 = width * width;
        final int b2 = height * height;
        final int fa2 = 4 * a2;
        final int fb2 = 4 * b2;

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
     * Constructs a list, containing the outline and fill, of an ellipse's points.
     *
     * @param position
     *        The x/y-axis (column/row) coordinates of the top-left character.
     *
     * @param dimension
     *        The width/height.
     *
     * @return
     *        The list of points.
     */
    public static List<Point> getFilledEllipse(final Point position, final Dimension dimension) {
        final List<Point> points = getEllipse(position, dimension);

        final int xCenter = position.x + (dimension.width / 2);
        final int yCenter = position.y + (dimension.height / 2);
        position.setLocation(xCenter, yCenter);

        return recursiveFill(points, position);
    }

    /**
     * Constructs a list, containing the path, of a line's points by using the
     * Bresenham algorithm,
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
        final List<Point> points = new LinkedList<>();

        int d = 0;

        final int dx = Math.abs(toX - fromX);
        final int dy = Math.abs(toY - fromY);

        final int dx2 = dx << 1;
        final int dy2 = dy << 1;

        final int ix = fromX < toX ? 1 : -1;
        final int iy = fromY < toY ? 1 : -1;

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
     * Constructs a list, containing the outline, of a rectangle's points.
     *
     * @param position
     *        The x/y-axis (column/row) coordinates of the top-left character.
     *
     * @param dimension
     *        The width/height.
     *
     * @return
     *        The list of points.
     */
    public static List<Point> getRectangle(final Point position, final Dimension dimension) {
        final List<Point> points = new LinkedList<>();

        final int x = position.x;
        final int y = position.y;
        final int width = dimension.width;
        final int height = dimension.height;

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
     * Constructs a list containing all of rectangle's points.
     *
     * @param position
     *        The x/y-axis (column/row) coordinates of the top-left character.
     *
     * @param dimension
     *        The width/height.
     *
     * @return
     *        The list of points.
     */
    public static List<Point> getFilledRectangle(final Point position, final Dimension dimension) {
        final List<Point> points = new LinkedList<>();

        for (int xCounter = position.x ; xCounter < dimension.width + position.x ; xCounter++) {
            for (int yCounter = position.y ; yCounter < dimension.height + position.y ; yCounter++) {
                points.add(new Point(xCounter, yCounter));
            }
        }

        return points;
    }

    /**
     * Recursively fills an area on the screen bounded by the set of input points.
     *
     * @param points
     *        The border points.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates of the current point.
     *
     * @return
     *        The list of filled points.
     */
    public static List<Point> recursiveFill(final List<Point> points, final Point position) {
        boolean pointExists = false;
        int x = position.x;
        int y = position.y;

        for (final Point point : points) {
            if (point.x == x && point.y == y) {
                pointExists = true;
                break;
            }
        }

        if (pointExists == false) {
            points.add(new Point(x, y));

            position.setLocation(x + 1, y);
            recursiveFill(points, position);

            position.setLocation(x - 1, y);
            recursiveFill(points, position);

            position.setLocation(x, y + 1);
            recursiveFill(points, position);

            position.setLocation(x, y - 1);
            recursiveFill(points, position);
        }

        return points;
    }
}
