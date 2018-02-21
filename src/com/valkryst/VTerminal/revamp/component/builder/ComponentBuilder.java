package com.valkryst.VTerminal.revamp.component.builder;

import com.valkryst.VTerminal.revamp.component.component.Component;
import com.valkryst.VTerminal.revamp.component.palette.ColorPalette;
import lombok.Data;
import lombok.NonNull;

import java.awt.Dimension;
import java.awt.Point;

@Data
public class ComponentBuilder<C extends Component> {
    /** The position of the component within it's parent. */
    @NonNull private Point position = new Point(0, 0);

    /** The dimensions of the component. */
    @NonNull private Dimension dimensions = new Dimension(1, 1);

    /** The color palette to color the label with. */
    @NonNull private ColorPalette colorPalette;

    /** Constructs a new ComponentBuilder. */
    public ComponentBuilder() {
        reset();
    }

    /**
     * Uses the builder to construct a new component.
     *
     * @return
     *         The new component.
     *
     * @throws IllegalStateException
     *          If something is wrong with the builder's state.
     */
    public C build() {
        checkState();
        return null;
    }

    /**
     * Checks the current state of the builder.
     *
     * @throws IllegalArgumentException
     *          If the column or row indices are less than zero.
     *
     * @throws NullPointerException
     *          If the panel is null.
     */
    protected void checkState() throws NullPointerException {
        if (dimensions.width < 1) {
            throw new IllegalArgumentException("You must specify a width of 1 or greater.");
        }

        if (dimensions.height < 1) {
            throw new IllegalArgumentException("You must specify a height of 1 or greater.");
        }
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        position.setLocation(0, 0);
        dimensions.setSize(1, 1);
        colorPalette = new ColorPalette();
    }
}
