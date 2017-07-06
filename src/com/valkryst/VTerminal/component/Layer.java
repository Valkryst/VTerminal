package com.valkryst.VTerminal.component;

public class Layer extends Component {
    /**
     * Constructs a new AsciiComponent.
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
    public Layer(int columnIndex, int rowIndex, int width, int height) {
        super(columnIndex, rowIndex, width, height);
    }
}
