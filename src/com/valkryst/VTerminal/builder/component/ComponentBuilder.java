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

    /** The radio to transmit events to. */
    @Getter protected Radio<String> radio;

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

    /** Checks the current state of the builder. */
    protected void checkState() {}

    /** Resets the builder to it's default state. */
    public void reset() {
        columnIndex = 0;
        rowIndex = 0;
    }

    /**
     * Sets the column index.
     *
     * @param columnIndex
     *        The new column index.
     *
     * @return
     *        This.
     */
    public B setColumnIndex(final int columnIndex) {
        if (columnIndex >= 0) {
            this.columnIndex = columnIndex;
        }

        return (B)this;
    }

    /**
     * Sets the row index.
     *
     * @param rowIndex
     *        The new row index.
     *
     * @return
     *        This.
     */
    public B setRowIndex(final int rowIndex) {
        if (rowIndex >= 0) {
            this.rowIndex = rowIndex;
        }

        return (B)this;
    }

    /**
     * Sets the column and row indices.
     *
     * @param columnIndex
     *        The new column index.
     *
     * @param rowIndex
     *        The new row index.
     *
     * @return
     *        This.
     */
    public B setColumnAndRowIndices(final int columnIndex, final int rowIndex) {
        setColumnIndex(columnIndex);
        setRowIndex(rowIndex);
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
