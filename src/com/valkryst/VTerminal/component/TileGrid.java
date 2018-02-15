package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.misc.ImageCache;
import lombok.NonNull;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileGrid {
    /** An empty array of tiles. */
    private final static AsciiCharacter[] EMPTY_ARRAY = new AsciiCharacter[0];

    /** The position of the grid within it's parent. */
    private final Point position;

    /** A grid of tiles. */
    private final AsciiCharacter[][] tiles;

    /** The child grids that reside on the grid. */
    private final List<TileGrid> childGrids = new ArrayList<>();

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

        position = new Point(0, 0);
        tiles = new AsciiCharacter[height][width];

        for (int y = 0 ; y < tiles.length ; y++) {
            tiles[y] = new AsciiCharacter[width];

            for (int x = 0 ; x < tiles[0].length ; x++) {
                tiles[y][x] = new AsciiCharacter(' ');
            }
        }
    }

    /**
     * Constructs a new TileGrid.
     *
     * @param width
     *          The width of the grid.
     *
     * @param height
     *          The height of the grid.
     *
     * @param position
     *          The position of the grid within it's parent.
     */
    public TileGrid(int width, int height, final Point position) {
        this(width, height);

        if (position == null) {
            return;
        }

        if (position.x < 0) {
            this.position.x = 0;
        } else {
            this.position.x = position.x;
        }

        if (position.y < 0) {
            this.position.y = 0;
        } else {
            this.position.y = position.y;
        }
    }

    public void draw(final @NonNull Graphics2D gc, final @NonNull ImageCache imageCache) {
        // Draw the grid to the screen
        for (int y = 0 ; y < tiles.length ; y++) {
            for (int x = 0; x < tiles[0].length ; x++) {
                tiles[y][x].draw(gc, imageCache, x, y);
            }
        }

        // Draw the child grids to the screen
        for (final TileGrid child : childGrids) {
            child.draw(gc, imageCache);
        }
    }

    /** Resets all of the grid's tiles to their default state. */
    private void resetGridTiles() {
        for (int y = 0 ; y < tiles[0].length ; y++) {
            for (int x = 0; x < tiles.length ; x++) {
                tiles[y][x].reset();
            }
        }
    }

    /**
     * Adds a child grid to the grid, so that it renders before all other
     * children.
     *
     * The child is ignored if it's null.
     *
     * @param child
     *          The child.
     */
    public void addChild(final TileGrid child) {
        if (child == null) {
            return;
        }

        childGrids.add(child);
    }

    /**
     * Adds a child grid to the grid, so that it renders before an existing
     * child in the grid.
     *
     * The new child is ignored if it's null.
     *
     * If the existing child is null, or if it's not in the grid, then the
     * function falls back to the addChlld function.
     *
     * @param newChild
     *          The new child.
     *
     * @param existingChild
     *          The existing child.
     */
    public void addChildAfter(final TileGrid newChild, final TileGrid existingChild) {
        if (newChild == null) {
            return;
        }

        if (existingChild == null || ! childGrids.contains(existingChild)) {
            addChild(newChild);
            return;
        }

        int indexOfExisting = childGrids.indexOf(existingChild);
        childGrids.add(indexOfExisting + 1, newChild);
    }

    /**
     * Adds a child grid to the grid, so that it renders after an existing
     * child in the grid.
     *
     * The child is ignored if it's null.
     *
     * If the existing child is null, of if it's not in the grid, then the
     * function falls back to the addChild function.
     *
     * @param newChild
     *          The new child.
     *
     * @param existingChild
     *          The existing child.
     */
    public void addChildBefore(final TileGrid newChild, final TileGrid existingChild) {
        if (newChild == null) {
            return;
        }

        if (existingChild == null || ! childGrids.contains(existingChild)) {
            addChild(newChild);
            return;
        }

        int indexOfExisting = childGrids.indexOf(existingChild);
        childGrids.add(indexOfExisting, newChild);
    }

    /**
     * Removes a child grid from the grid.
     *
     * The child is ignored if it's null.
     *
     * @param child
     *          The child.
     */
    public void removeChild(final TileGrid child) {
        if (child == null) {
            return;
        }

        childGrids.remove(child);
    }

    /**
     * Determines whether or not the grid contains a specific child.
     *
     * @param child
     *          The child.
     *
     * @return
     *          Whether or not the grid contains the child.
     */
    public boolean containsChild(final TileGrid child) {
        return child != null && childGrids.contains(child);

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
    public AsciiCharacter[] getRow(final int index) {
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
    public AsciiCharacter[] getColumn(final int columnIndex) {
        if (columnIndex >= tiles[0].length || columnIndex < 0) {
            return EMPTY_ARRAY;
        }

        final AsciiCharacter[] columnTiles = new AsciiCharacter[tiles.length];

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
    public AsciiCharacter[] getRowSubset(final int rowIndex, final int columnIndex, final int length) {
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
    public AsciiCharacter[] getColumnSubset(final int rowIndex, final int columnIndex, final int length) {
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

        final AsciiCharacter[] columnTiles = getColumn(columnIndex);
        final AsciiCharacter[] resultTiles = new AsciiCharacter[endRow];

        System.arraycopy(columnTiles, rowIndex, resultTiles, 0, endRow - rowIndex);

        return resultTiles;
    }

    /**
     * Retrieves the x-axis coordinate of the grid within it's parent.
     *
     * @return
     *          The x-axis coordinate of the grid within it's parent.
     */
    public int getXPosition() {
        return position.x;
    }

    /**
     * Retrieves the y-axis coordinate of the grid within it's parent.
     *
     * @return
     *          The y-axis coordinate of the grid within it's parent.
     */
    public int getYPosition() {
        return position.y;
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

    /**
     * Retrieves a tile from the grid.
     *
     * @param x
     *          The x-axis coordinate of the tile to retrieve.
     *
     * @param y
     *          The y-axis coordinate of the tile to retrieve.
     *
     * @return
     *          The tile, or null if the coordinates are outside the bounds
     *          of the grid.
     */
    public AsciiCharacter getTileAt(final int x, final int y) {
        if (x < 0 || x >= tiles[0].length) {
            return null;
        }

        if (y < 0 || y >= tiles.length) {
            return null;
        }

        return tiles[y][x];
    }

    /**
     * Sets the new x-axis coordinate of the grid within it's parent.
     *
     * Negative coordinates are ignored.
     *
     * @param x
     *          The new coordinate.
     */
    public void setXPosition(final int x) {
        position.x = x;
    }

    /**
     * Sets the new y-axis coordinate of the grid within it's parent.
     *
     * Negative coordinates are ignored.
     *
     * @param y
     *          The new coordinate.
     */
    public void setYPosition(final int y) {
        position.y = y;
    }
}
