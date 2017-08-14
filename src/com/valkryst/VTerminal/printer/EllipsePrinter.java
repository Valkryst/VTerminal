package com.valkryst.VTerminal.printer;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.component.Screen;
import com.valkryst.VTerminal.misc.ShapeAlgorithms;
import lombok.*;

import java.awt.Point;

@EqualsAndHashCode
@ToString
public class EllipsePrinter {
    /** The width of the ellipse to print. */
    @Getter private int width = 2;
    /** The height of the ellipse to print. */
    @Getter private int height = 2;

    /** The character to print the ellipse with. */
    @Getter @Setter private char printChar = '█';

    /**
     * Prints an ellipse on the screen of a panel.
     *
     * @param panel
     *         The panel.
     *
     * @param row
     *         The y-axis (row) coordinate of the top-left character.
     *
     * @param column
     *         The x-axis (column) coordinate of the top-left character.
     *
     * @throws NullPointerException
     *         If the panel is null.
     */
    public void print(final @NonNull Panel panel, final int row, final int column) {
        print(panel.getScreen(), row, column);
    }

    /**
     * Prints an ellipse on a screen.
     *
     * @param screen
     *         The screen.
     *
     * @param row
     *         The y-axis (row) coordinate of the top-left character.
     *
     * @param column
     *         The x-axis (column) coordinate of the top-left character.
     *
     * @throws NullPointerException
     *         If the panel is screen.
     */
    public void print(final @NonNull Screen screen, final int row, final int column) {
        for (final Point point : ShapeAlgorithms.getEllipse(column, row, width, height)) {
            screen.write(printChar, point.x, point.y);
        }
    }

    /**
     * Sets the width.
     *
     * @param width
     *         The new width.
     *
     * @return
     *         This.
     */
    public EllipsePrinter setWidth(final int width) {
        if (width > 0) {
            this.width = width;
        }

        return this;
    }

    /**
     * Sets the height.
     *
     * @param height
     *         The new height.
     *
     * @return
     *         This.
     */
    public EllipsePrinter setHeight(final int height) {
        if (height > 0) {
            this.height = height;
        }

        return this;
    }
}
