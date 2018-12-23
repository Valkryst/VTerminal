package com.valkryst.VTerminal;

import com.valkryst.VTerminal.misc.ImageCache;
import lombok.NonNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class TileGrid {
    /** An empty array of tiles. */
    private final static Tile[] EMPTY_ARRAY = new Tile[0];
    /** An empty 2D array of tiles. */
    private final static Tile[][] EMPTY_2D_ARRAY = new Tile[0][0];

    /** The position of the grid within it's parent. */
    private final Point position;

    /** A grid of tiles. */
    private final Tile[][] tiles;

    /** The child grids that reside on the grid. */
    private final List<TileGrid> childGrids = new ArrayList<>();

    /** The lock used to control access to the children. */
    private final ReentrantReadWriteLock childLock = new ReentrantReadWriteLock();

    /**
     * Constructs a new TileGrid.
     *
     * @param dimensions
     *          The dimensions of the grid.
     *
     * @throws NullPointerException
     *         If the dimensions is null.
     */
    public TileGrid(final @NonNull Dimension dimensions) {
        if (dimensions.width < 1) {
            dimensions.width = 1;
        }

        if (dimensions.height < 1) {
            dimensions.height = 1;
        }

        position = new Point(0, 0);
        tiles = new Tile[dimensions.height][dimensions.width];

        for (int y = 0 ; y < tiles.length ; y++) {
            tiles[y] = new Tile[dimensions.width];

            for (int x = 0 ; x < tiles[0].length ; x++) {
                tiles[y][x] = new Tile(' ');
            }
        }
    }

    /**
     * Constructs a new TileGrid.
     *
     * @param dimensions
     *          The dimensions of the grid.
     *
     * @param position
     *          The position of the grid within it's parent.
     *
     * @throws NullPointerException
     *         If the dimensions or position is null.
     */
    public TileGrid(final @NonNull Dimension dimensions, final @NonNull Point position) {
        this(dimensions);

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

    /**
     * Draws a specific tile to the screen.
     *
     * If there are any child grids which have tiles overlapping the position of the tile to draw, then those
     * tiles are drawn as well.
     *
     * @param gc
     *         The graphics context to draw with.
     *
     * @param imageCache
     *         The image cache to retrieve tile images from.
     *
     * @param tileX
     *          The x-axis position of the tile, on this grid, to draw.
     *
     * @param tileY
     *          The y-axis position of the tile, on this grid, to draw.
     *
     * @param drawX
     *          The x-axis position, on the screen, to draw the tile at.
     *
     * @param drawY
     *          The y-axis position, on the screen, to draw the tile at.
     */
    public void drawTile(final Graphics2D gc, final ImageCache imageCache, final int tileX, final int tileY, final int drawX, final int drawY) {
        if (gc == null || imageCache == null) {
            return;
        }

        // Draw the tile if it exists on this grid:
        if (tileX < 0 || tileX >= tiles[0].length) {
            return;
        }

        if (tileY < 0 || tileY >= tiles.length) {
            return;
        }

        final Tile tile = tiles[tileY][tileX];
        tile.draw(gc, imageCache, drawX, drawY);

        // Because the tile exists on this grid, it may exist on the child grids:
        childLock.readLock().lock();

        for (final TileGrid childGrid : childGrids) {
            final int tempX = tileX - childGrid.getXPosition();
            final int tempY = tileY - childGrid.getYPosition();

            childGrid.drawTile(gc, imageCache, tempX, tempY, drawX, drawY);
        }

        childLock.readLock().unlock();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        for (final Tile[] tileRow : tiles) {
            for (final Tile tile : tileRow) {
                sb.append(tile.getCharacter());
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Converts this TileGrid and all of it's children to use GraphicTiles,
     * rather than regular Tiles.
     */
    public void convertToGraphicTileGrid() {
        for (int y = 0 ; y < tiles.length ; y++) {
            for (int x = 0 ; x < tiles[0].length ; x++) {
                if (tiles[y][x] instanceof GraphicTile == false) {
                    tiles[y][x] = new GraphicTile(tiles[y][x]);
                }
            }
        }

        childLock.readLock().lock();

        for (final TileGrid child : childGrids) {
            child.convertToGraphicTileGrid();
        }

        childLock.readLock().unlock();
    }

    /**
     * Converts this TileGrid and all of it's children to use Tiles, rather
     * than GraphicTiles.
     */
    public void convertToTileGrid() {
        for (int y = 0 ; y < tiles.length ; y++) {
            for (int x = 0 ; x < tiles[0].length ; x++) {
                if (tiles[y][x] instanceof GraphicTile) {
                    tiles[y][x] = new Tile(tiles[y][x]);
                }
            }
        }

        childLock.readLock().lock();

        for (final TileGrid child : childGrids) {
            child.convertToTileGrid();
        }

        childLock.readLock().unlock();
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

        childLock.writeLock().lock();

        childGrids.add(child);

        childLock.writeLock().unlock();
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

        childLock.writeLock().lock();

        if (existingChild == null || ! childGrids.contains(existingChild)) {
            childLock.writeLock().unlock();
            addChild(newChild);
            return;
        }

        childGrids.add(childGrids.indexOf(existingChild) + 1, newChild);

        childLock.writeLock().unlock();
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

        childLock.writeLock().lock();

        if (existingChild == null || ! childGrids.contains(existingChild)) {
            childLock.writeLock().unlock();
            addChild(newChild);
            return;
        }

        childGrids.add(childGrids.indexOf(existingChild), newChild);

        childLock.writeLock().unlock();
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

        childLock.writeLock().lock();

        // Reset the tiles on which the child may have been drawn.
        final int startY = child.getYPosition();
        final int endY = Math.min(startY + child.getHeight(), tiles.length);

        final int startX = child.getXPosition();
        final int endX = Math.min(startX + child.getWidth(), tiles[0].length);

        for (int y = startY ; y < endY ; y++) {
            for (int x = startX ; x < endX ; x++) {
                if (y < tiles.length && x < tiles[0].length) {
                    tiles[y][x].reset();
                }
            }
        }

        // Remove the child.
        childGrids.remove(child);

        childLock.writeLock().unlock();
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
        if (child == null) {
            return false;
        }

        childLock.readLock().lock();
        final boolean containsChild = childGrids.contains(child);
        childLock.readLock().unlock();

        return containsChild;

    }

    /**
     * Calculates the hash value of a specific tile.
     *
     * If there are any child grids which have tiles overlapping the position of the tile to calculate the hash
     * for, then the hashes of those tiles are added to the hash of the tile.
     *
     * @param tileX
     *          The x-axis position of the tile, on this grid, to calculate the hash for.
     *
     * @param tileY
     *          The y-axis position of the tile, on this grid, to calculate the hash for.
     *
     * @return
     *          The hash.
     */
    public long getPositionHash(final int tileX, final int tileY) {
        // Return nothing if the tile doesn't exist on this grid.
        if (tileX < 0 || tileX >= tiles[0].length) {
            return 0;
        }

        if (tileY < 0 || tileY >= tiles.length) {
            return 0;
        }

        // Because the tile exists on this grid, it may exist on the child grids, so we need to add the hash of
        // the tile from this grid to the hashes of the tiles from the child grids.
        long hash = tiles[tileY][tileX].getCacheHash();

        childLock.readLock().lock();

        for (final TileGrid childGrid : childGrids) {
            final int tempX = tileX - childGrid.getXPosition();
            final int tempY = tileY - childGrid.getYPosition();

            hash += childGrid.getPositionHash(tempX, tempY);
        }

        childLock.readLock().unlock();

        return hash;
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
    public Tile[] getRow(final int index) {
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
    public Tile[] getColumn(final int columnIndex) {
        if (columnIndex >= tiles[0].length || columnIndex < 0) {
            return EMPTY_ARRAY;
        }

        final Tile[] columnTiles = new Tile[tiles.length];

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
    public Tile[] getRowSubset(final int rowIndex, final int columnIndex, final int length) {
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

        final int endColumn = columnIndex + length;

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
    public Tile[] getColumnSubset(final int rowIndex, final int columnIndex, final int length) {
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

        final int endRow = (rowIndex + length) >= tiles.length ? tiles.length : (rowIndex + length);

        final Tile[] columnTiles = getColumn(columnIndex);
        final Tile[] resultTiles = new Tile[endRow];

        System.arraycopy(columnTiles, rowIndex, resultTiles, 0, endRow - rowIndex);

        return resultTiles;
    }

    /**
     * Retrieves a rectangular subset of tiles from the grid. The tiles are
     * ordered first by row, then by column. The point (5x, 10y) would be
     * at 'subset[10][5]'.
     *
     * If the width or height are less than 1, then an empty array is returned.
     *
     * If the start row or column is greater than the width/height of the grid,
     * then an empty array is returned.
     *
     * @param startRow
     *          The index of the column to begin the subset at.
     *
     * @param startColumn
     *          The index of the row to begin the subset at.
     *
     * @param width
     *          The width of the subset to retrieve.
     *
     * @param height
     *          The height of the subset to retrieve.
     *
     * @return
     *          The subset.
     */
    public Tile[][] getRectangularSubset(int startRow, int startColumn, final int width, final int height) {
        int endColumn = width + startColumn;
        int endRow = height + startRow;

        // Don't allow the use of negative coordinates.
        if (startRow < 0 || startColumn < 0) {
            startRow = 0;
        }

        if (startColumn < 0) {
            startColumn = 0;
        }

        // Don't allow the dimensions to be below 1 tile.
        if (width < 1 || height < 1) {
            return EMPTY_2D_ARRAY;
        }

        // Don't allow the starting row value to be beyond the grid's height.
        if (startRow > tiles.length) {
            return EMPTY_2D_ARRAY;
        }

        // Don't allow the starting column value to be beyond the grid's width.
        if (startColumn > tiles[0].length) {
            return EMPTY_2D_ARRAY;
        }

        // Don't allow the ending row value to be beyond the grid's height.
        if (endRow > tiles.length) {
            endRow = tiles.length;
        }

        // Don't allow the ending column value to be beyond the grid's width.
        if (endColumn > tiles[0].length) {
            endColumn = tiles[0].length;
        }

        // Create array.
        final Tile[][] resultTiles = new Tile[endRow - startRow][endColumn - startColumn];

        for (int y = startRow ; y < endRow ; y++) {
            System.arraycopy(tiles[y], startColumn, resultTiles[y - startRow], 0, endColumn - startColumn);
        }

        return resultTiles;
    }

    /**
     * Retrieves the total number of child grids.
     *
     * @return
     *          The total number of child grids.
     */
    public int getTotalChildren() {
        childLock.readLock().lock();
        final int total = childGrids.size();
        childLock.readLock().unlock();

        return total;
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
    public Tile getTileAt(final int x, final int y) {
        if (x < 0 || x >= tiles[0].length) {
            return null;
        }

        if (y < 0 || y >= tiles.length) {
            return null;
        }

        return tiles[y][x];
    }

    /**
     * Retrieves a tile from the grid.
     *
     * @param position
     *          The x/y-axis coordinates of the tile to retrieve.
     *
     * @return
     *          The tile, or null if the coordinates are outside the bounds
     *          of the grid.
     */
    public Tile getTileAt(final Point position) {
        if (position == null) {
            return null;
        }

        return getTileAt(position.x, position.y);
    }

    /**
     * Sets the new x/y-axis coordinates of the grid within it's parent.
     *
     * @param x
     *          The new x-axis coordinate.
     *
     * @param y
     *          The new y-axis coordinate.
     */
    public void setPosition(final int x, final int y) {
        position.setLocation(x, y);
    }

    /**
     * Sets the new x/y-axis coordinates of the grid within it's parent.
     *
     * @param position
     *          The new x/y-axis coordinates.
     */
    public void setPosition(final Point position) {
        this.position.setLocation(position);
    }

    /**
     * Sets the new x-axis coordinate of the grid within it's parent.
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
     * @param y
     *          The new coordinate.
     */
    public void setYPosition(final int y) {
        position.y = y;
    }
}
