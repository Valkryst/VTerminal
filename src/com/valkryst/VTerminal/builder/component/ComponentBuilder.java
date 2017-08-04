package com.valkryst.VTerminal.builder.component;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.component.Component;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

public class ComponentBuilder<C extends Component> {
    /** The x-axis (column) coordinate of the top-left character. */
    @Getter @Setter private int columnIndex;
    /** The y-axis (row) coordinate of the top-left character. */
    @Getter @Setter private int rowIndex;

    /** The panel on which the button is to be placed. */
    @Getter @Setter @NonNull private Panel panel;

    /** The radio to transmit events to. */
    @Getter @Setter @NonNull private Radio<String> radio;

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
        if (columnIndex < 0) {
            throw new IllegalArgumentException("The column index cannot be less than zero.");
        }

        if (rowIndex < 0) {
            throw new IllegalArgumentException("The row index cannot be less than zero.");
        }

        if (panel == null) {
            throw new NullPointerException("The component must have a panel to be placed on.");
        }
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        columnIndex = 0;
        rowIndex = 0;
    }

        panel = null;

        radio = null;
    }
}
