package com.valkryst.AsciiPanel;

import lombok.Getter;

public class AsciiCursor {
    /** The panel that the cursor is displayed on. */
    private final AsciiPanel panel;
    /** The index of the column that the cursor is on. */
    @Getter private int columnIndex = 0;
    /** The index of the row that the cursor is on. */
    @Getter private int rowIndex = 0;

    /**
     * Constructs a new AsciiCursor.
     *
     * @param panel
     *         The panel that the cursor is displayed on.
     */
    public AsciiCursor(final AsciiPanel panel) throws NullPointerException {
        if (panel == null) {
            throw new NullPointerException("You must specify a panel.");
        }

        this.panel = panel;
    }

    @Override
    public String toString() {
        String s = "AsciiCursor:\n";
        s += "\tColumn Index: " + columnIndex + "\n";
        s += "\tRow Index: " + rowIndex + "\n";
        return s;
    }

    /**
     * Sets the column index specifying which column the cursor is at.
     *
     * Does nothing if the new position is invalid.
     *
     * @param columnIndex
     *         The new column index.
     */
    public void setColumn(final int columnIndex) {
        if (panel.isPositionValid(columnIndex, rowIndex)) {
            this.columnIndex = columnIndex;
        }
    }

    /**
     * Sets the row index specifying which row the cursor is at.
     *
     * Does nothing if the new position is invalid.
     *
     * @param rowIndex
     *         The new row index.
     */
    public void setRow(final int rowIndex) {
        if (panel.isPositionValid(columnIndex, rowIndex)) {
            this.rowIndex = rowIndex;
        }
    }

    /**
     * Sets the column and row indices specifying where the cursor is at.
     *
     * Does nothing if the new position is invalid.
     *
     * @param columnIndex
     *         The new x-axis (column) coordinate of the cursor.
     *
     * @param rowIndex
     *         The new y-axis (row) coordinate of the cursor.
     */
    public void setPosition(final int columnIndex, final int rowIndex) {
        if (panel.isPositionValid(columnIndex, rowIndex)) {
            this.columnIndex = columnIndex;
            this.rowIndex = rowIndex;
        }
    }
}
