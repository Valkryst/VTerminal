package com.valkryst.VTerminal.printer;

import com.valkryst.VTerminal.component.Component;
import com.valkryst.VTerminal.misc.ShapeAlgorithms;
import lombok.*;

import java.awt.Dimension;
import java.awt.Point;

@EqualsAndHashCode
@ToString
public class EllipsePrinter {
    /** The width/height of the ellipse to print. */
    @Getter private final Dimension dimensions = new Dimension(2, 2);

    /** The character to print the ellipse with. */
    @Getter @Setter private char printChar = '█';

    /**
     * Prints an ellipse on a component.
     *
     * @param component
     *         The component.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates of the top-left character.
     *
     * @throws NullPointerException
     *         If the component or position is null.
     */
    public void print(final @NonNull Component component, final @NonNull Point position) {
        for (final Point point : ShapeAlgorithms.getEllipse(position, dimensions)) {
            component.getTiles().getTileAt(point).setCharacter(printChar);
        }
    }

    /**
     * Prints a filled ellipse on a component.
     *
     * @param component
     *         The component.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates of the top-left character.
     *
     * @throws NullPointerException
     *         If the component or position is null.
     */
    public void printFilled(final @NonNull Component component, final @NonNull Point position) {
        for (final Point point : ShapeAlgorithms.getFilledEllipse(position, dimensions)) {
            component.getTiles().getTileAt(point).setCharacter(printChar);
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
            dimensions.setSize(width, dimensions.height);
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
            dimensions.setSize(dimensions.width, height);
        }

        return this;
    }
}
