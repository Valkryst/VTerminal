package com.valkryst.VTerminal.builder.component;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.component.Component;
import lombok.Getter;

public class ComponentBuilder<C extends Component, B extends ComponentBuilder<C, B>> {
    /** The x-axis (column) coordinate of the top-left character. */
    @Getter protected int columnIndex;
    /** The y-axis (row) coordinate of the top-left character. */
    @Getter protected int rowIndex;

    /** The panel on which the button is to be placed. */
    @Getter protected Panel panel;

    /** The radio to transmit events to. */
    @Getter protected Radio<String> radio;

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

    public B setPanel(final Panel panel) {
        if (panel != null) {
            this.panel = panel;
        }

        return (B)this;
    }
    /**
     * Sets the radio to transmit events to.
     *
     * @param radio
     *         The radio.
     *
     * @return
     *         This.
     */
    public B setRadio(final Radio<String> radio) {
        if (radio != null) {
            this.radio = radio;
        }

        return (B)this;
    }

    /**
     * Sets the radio to transmit to, to the radio of a panel.
     *
     * @param panel
     *         The panel.
     *
     * @return
     *         This.
     */
    public B setRadio(final Panel panel) {
        if (panel != null) {
            setRadio(panel.getRadio());
        }

        return (B)this;
    }
}
