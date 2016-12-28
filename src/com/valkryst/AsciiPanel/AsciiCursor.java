package com.valkryst.AsciiPanel;

import lombok.Getter;
import lombok.Setter;

public class AsciiCursor {
    /** The panel that the cursor is displayed on. */
    private final AsciiPanel panel;
    /** The index of the column that the cursor is on. */
    @Getter @Setter private int columnIndex = 0;
    /** The index of the row that the cursor is on. */
    @Getter @Setter private int rowIndex = 0;

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
        final StringBuilder stringBuilder = new StringBuilder("AsciiCursor:\n");
        stringBuilder.append("\tColumn Index: ").append(columnIndex).append("\n");
        stringBuilder.append("\tRow Index: ").append(rowIndex).append("\n");
        return stringBuilder.toString();
    }
}
