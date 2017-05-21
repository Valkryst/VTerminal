package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.AsciiPanel;
import com.valkryst.VTerminal.component.AsciiComponent;
import lombok.Getter;

public class ComponentBuilder <C extends AsciiComponent, B extends ComponentBuilder<C, B>> {
    /** The x-axis (column) coordinate of the top-left character. */
    @Getter protected int columnIndex;
    /** The y-axis (row) coordinate of the top-left character. */
    @Getter protected int rowIndex;

    /** The panel on which the button is to be placed. */
    @Getter protected AsciiPanel panel;

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
        return null;
    }

    /**
     * Checks the current state of the builder.
     *
     * @throws IllegalStateException
     *          If something is wrong with the builder's state.
     */
    protected void checkState() throws IllegalStateException {
        if (panel == null) {
            throw new IllegalStateException("The component must have a panel to be placed on.");
        }
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        columnIndex = 0;
        rowIndex = 0;

        panel = null;
    }

    public B setColumnIndex(final int columnIndex) {
        if (columnIndex >= 0) {
            this.columnIndex = columnIndex;
        }

        return (B)this;
    }

    public B setRowIndex(final int rowIndex) {
        if (rowIndex >= 0) {
            this.rowIndex = rowIndex;
        }

        return (B)this;
    }

    public B setPanel(final AsciiPanel panel) {
        if (panel != null) {
            this.panel = panel;
        }

        return (B)this;
    }
}
