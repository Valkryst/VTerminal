package com.valkryst.VTerminal.component;

import lombok.ToString;

@ToString
public class Image extends Layer {
    /**
     * Constructs a new CellImage.
     *
     * @param columnIndex
     *        The x-axis (column) coordinate of the top-left character.
     *
     * @param rowIndex
     *        The y-axis (row) coordinate of the top-left character.
     *
     * @param width
     *        The width, in characters.
     *
     * @param height
     *        The height, in characters.
     */
    public Image(int columnIndex, int rowIndex, int width, int height) {
        super(columnIndex, rowIndex, width, height);
    }
}
