package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.AsciiTile;

import java.util.Arrays;

public class TileGrid {
    /** An empty array of tiles. */
    private final static AsciiTile[] EMPTY_ARRAY = new AsciiTile[0];

    /** A grid of tiles. */
    private final AsciiTile[][] tiles;

    /**
     * Constructs a new TileGrid.
     *
     * @param width
     *          The width of the grid.
     *
     * @param height
     *          The height of the grid.
     */
    public TileGrid(int width, int height) {
        if (width < 1) {
            width = 1;
        }

        if (height < 1) {
            height = 1;
        }

        tiles = new AsciiTile[height][width];

        for (int y = 0 ; y < tiles.length ; y++) {
            tiles[y] = new AsciiTile[width];

            for (int x = 0 ; x < tiles[0].length ; x++) {
                tiles[y][x] = new AsciiTile(' ');
            }
        }
    }

    /**
     * Retrieves a row of tiles.
     *
     * If the index is less than 0, then an empty array is returned.
     * If the index exceeds the grid's height, then an empty array is returned.
     *
     * @param index
     *          The index of the row.
     *
     * @return
     *          The row of tiles.
     */
    public AsciiTile[] getRow(final int index) {
        if (index >= tiles.length || index < 0) {
            return EMPTY_ARRAY;
        }

        return tiles[index];
    }

    /**
     * Retrieves a column of tiles.
     *
     * If the index is less than 0, then an empty array is returned.
     * If the index exceeds the grid's width, then an empty array is returned.
     *
     * @param columnIndex
     *          The index of the column.
     *
     * @return
     *          The column of tiles.
     */
    public AsciiTile[] getColumn(final int columnIndex) {
        if (columnIndex >= tiles[0].length || columnIndex < 0) {
            return EMPTY_ARRAY;
        }

        final AsciiTile[] columnTiles = new AsciiTile[tiles.length];

        for (int rowIndex = 0 ; rowIndex < tiles.length ; rowIndex++) {
            columnTiles[rowIndex] = tiles[rowIndex][columnIndex];
        }

        return columnTiles;
    }

    /**
     * Retrieves a subset of tiles from a row.
     *
     * If the row or column indices are negative, then an empty array is returned.
     *
     * If the length is less than 1, then an empty array is returned.
     *
     * If the row index exceeds the grid's width, then an empty array is returned.
     *
     * If the column index exceeds the grid's width, then an empty array is returned.
     *
     * @param rowIndex
     *          The index of the row.
     *
     * @param columnIndex
     *          The index of the column to begin the subset at.
     *
     * @param length
     *          The number of columns to retrieve.
     *
     * @return
     *          The subset.
     */
    public AsciiTile[] getRowSubset(final int rowIndex, final int columnIndex, final int length) {
        // Don't allow the use of negative coordinates.
        if (columnIndex < 0 || rowIndex < 0) {
            return EMPTY_ARRAY;
        }

        // Don't allow the use of negative length.
        if (length < 1) {
            return EMPTY_ARRAY;
        }

        // Don't allow the starting row value to be beyond the grid's height.
        if (rowIndex >= tiles.length) {
            return EMPTY_ARRAY;
        }

        // Don't allow the starting column value to be beyond the grid's width.
        if (columnIndex >= tiles[0].length) {
            return EMPTY_ARRAY;
        }

        int endColumn = columnIndex + length;

        if (endColumn > tiles[0].length) {
            return Arrays.copyOfRange(getRow(rowIndex), columnIndex, tiles[0].length);
        } else {
            return Arrays.copyOfRange(getRow(rowIndex), columnIndex, endColumn);
        }
    }

    /**
     * Retrieves a subset of tiles from a column.
     *
     * If the row or column indices are negative, then an empty array is returned.
     *
     * If the length is less than 1, then an empty array is returned.
     *
     * If the row index exceeds the grid's width, then an empty array is returned.
     *
     * If the column index exceeds the grid's width, then an empty array is returned.
     *
     * @param rowIndex
     *          The index of the row to begin the subset at.
     *
     * @param columnIndex
     *          The index of the column.
     *
     * @param length
     *          The number of rows to retrieve.
     *
     * @return
     *          The subset.
     */
    public AsciiTile[] getColumnSubset(final int rowIndex, final int columnIndex, final int length) {
        // Don't allow the use of negative coordinates.
        if (columnIndex < 0 || rowIndex < 0) {
            return EMPTY_ARRAY;
        }

        // Don't allow the use of negative length.
        if (length < 1) {
            return EMPTY_ARRAY;
        }

        // Don't allow the starting row value to be beyond the grid's height.
        if (rowIndex >= tiles.length) {
            return EMPTY_ARRAY;
        }

        // Don't allow the starting column value to be beyond the grid's width.
        if (columnIndex >= tiles[0].length) {
            return EMPTY_ARRAY;
        }

        int endRow = (rowIndex + length) >= tiles.length ? tiles.length : (rowIndex + length);

        final AsciiTile[] columnTiles = getColumn(columnIndex);
        final AsciiTile[] resultTiles = new AsciiTile[endRow];

        System.arraycopy(columnTiles, rowIndex, resultTiles, 0, endRow - rowIndex);

        return resultTiles;
    }

    /**
     * Retrieves the width, also known as the total number of columns, in the grid.
     *
     * @return
     *          The width of the grid.
     */
    public int getWidth() {
        return tiles[0].length;
    }

    /**
     * Retrieves the height, also known as the total number of rows, in the grid.
     *
     * @return
     *          The height of the grid.
     */
    public int getHeight() {
        return tiles.length;
    }
}
