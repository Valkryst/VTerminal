package com.valkryst.AsciiPanel;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AsciiScreen {
    /** The x-axis (column) coordinate of the top-left character. */
    @Getter private int columnIndex;
    /** The y-axis (row) coordinate of the top-left character. */
    @Getter private int rowIndex;

    /** The width, in characters. */
    @Getter private int width;
    /** The height, in characters. */
    @Getter private int height;

    /** The bounding box. */
    @Getter private Rectangle boundingBox = new Rectangle();

    /** The strings representing the character-rows of the screen. */
    @Getter private AsciiString[] strings;

    /**
     * Constructs a new AsciiScreen.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate of the top-left character.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate of the top-left character.
     *
     * @param width
     *         Thw width, in characters.
     *
     * @param height
     *         The height, in characters.
     */
    public AsciiScreen(final int columnIndex, final int rowIndex, final int width, final int height) {
        if (columnIndex < 0) {
            throw new IllegalArgumentException("You must specify a columnIndex of 0 or greater.");
        }

        if (rowIndex < 0) {
            throw new IllegalArgumentException("You must specify a rowIndex of 0 or greater.");
        }

        if (width < 1) {
            throw new IllegalArgumentException("You must specify a width of 1 or greater.");
        }

        if (height < 1) {
            throw new IllegalArgumentException("You must specify a height of 1 or greater.");
        }

        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
        this.width = width;
        this.height = height;
    }

    /**
     * Draws the screen onto the specified canvas using the specified font.
     *
     * @param canvas
     *         The canvas to draw on.
     *
     * @param font
     *         The font to draw with.
     */
    public void draw(final Canvas canvas, final AsciiFont font) {
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFont(font.getFont());

        for (int row = 0 ; row < strings.length ; row++) {
            strings[row].draw(gc, font, row);
        }

    }

    /**
     * Determines whether or not the specified position is within the bounds of the component.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate.
     *
     * @return
     *         Whether or not the specified position is within the bounds of the component.
     */
    protected boolean isPositionValid(final int columnIndex, final int rowIndex) {
        if (rowIndex < 0 || rowIndex >= height) {
            final Logger logger = LogManager.getLogger();
            logger.error("The specified column of " + columnIndex + " exceeds the maximum height of " + height + ".");
            return false;
        }

        if (columnIndex < 0 || columnIndex >= width) {
            final Logger logger = LogManager.getLogger();
            logger.error("The specified row of " + rowIndex + " exceeds the maximum width of " + width + ".");
            return false;
        }

        return true;
    }

    /**
     * Clears the entire screen.
     *
     * @param character
     *         The character to replace every character on the screen with.
     *
     * @return
     *         If all characters within the screen were cleared.
     */
    public boolean clear(final AsciiCharacter character) {
        return clear(character, 0, 0, width, height);
    }

    /**
     * Clears the specified section of the screen.
     *
     * Does nothing if the (columnIndex, rowIndex) or (width, height) pairs point to invalid positions.
     *
     * @param character
     *         The character to replace all characters being cleared with.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate of the cell to clear.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate of the cell to clear.
     *
     * @param width
     *         The width of the area to clear.
     *
     * @param height
     *         The height of the area to clear.
     *
     * @return
     *         If all characters within the specified area were cleared.
     */
    public boolean clear(final AsciiCharacter character, final int columnIndex, final int rowIndex, final int width, final int height) {
        boolean canProceed = isPositionValid(columnIndex, rowIndex);
        canProceed &= isPositionValid(width, height);

        boolean clearsSuccessful = true;

        if (canProceed) {
            for (int column = columnIndex ; column < width ; column++) {
                for (int row = rowIndex ; row < height ; row++) {
                    clearsSuccessful &= write(character, column, row);
                }
            }
        }

        return canProceed & clearsSuccessful;
    }

    /**
     * Write the specified character to the specified position.
     *
     * @param character
     *         The character.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate to write to.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate to write to.
     *
     * @return
     *         If the write was successful.
     */
    public boolean write(final AsciiCharacter character, final int columnIndex, final int rowIndex) {
        boolean canProceed = isPositionValid(columnIndex, rowIndex);

        if (canProceed) {
            strings[rowIndex].replaceCharacter(columnIndex, character);
        }

        return canProceed;
    }

    /**
     * Write a string to the specified position.
     *
     * Does nothing if the (columnIndex, rowIndex) points to invalid position.
     *
     * @param string
     *         The string.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate to begin writing from.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate to begin writing from.
     *
     * @return
     *         If all writes were successful.
     */
    public boolean write(final AsciiString string, final int columnIndex, final int rowIndex) {
        if (isPositionValid(rowIndex, columnIndex) == false) {
            return false;
        }

        boolean writesSuccessful = true;
        final AsciiCharacter[] characters = string.getCharacters();

        for (int i = 0 ; i < characters.length && i < width ; i++) {
            writesSuccessful &= write(characters[i], columnIndex + i, rowIndex);
        }

        return writesSuccessful;
    }

    /**
     * Sets a new value for the columnIndex.
     *
     * Does nothing if the specified columnIndex is < 0.
     *
     * @param columnIndex
     *         The new x-axis (column) coordinate of the top-left character of the component.
     *
     * @return
     *         Whether or not the new value was set.
     */
    public boolean setColumnIndex(final int columnIndex) {
        if (columnIndex < 0) {
            return false;
        }

        this.columnIndex = columnIndex;
        boundingBox.setX(columnIndex);
        return true;
    }

    /**
     * Sets a new value for the rowIndex.
     *
     * Does nothing if the specified rowIndex is < 0.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate of the top-left character of the component.
     *
     * @return
     *         Whether or not the new value was set.
     */
    public boolean setRowIndex(final int rowIndex) {
        if (rowIndex < 0) {
            return false;
        }

        this.rowIndex = rowIndex;
        boundingBox.setY(rowIndex);
        return true;
    }

    /**
     * Sets a new value for the width.
     *
     * Does nothing if the specified width is < 0 or < columnIndex.
     *
     * @param width
     *         The new width, in characters, of the component.
     *
     * @return
     *         Whether or not the new value was set.
     */
    public boolean setWidth(final int width) {
        if (width < 0 || width < columnIndex) {
            return false;
        }

        this.width = width;
        boundingBox.setWidth(width);
        return true;
    }

    /**
     * Sets a new value for the height.
     *
     * Does nothing if the specified height is < 0 or < rowIndex.
     *
     * @param height
     *         The new height, in characters, of the component.
     *
     * @return
     *         Whether or not the new value was set.
     */
    public boolean setHeight(final int height) {
        if (height < 0 || height < rowIndex) {
            return false;
        }

        this.height = height;
        boundingBox.setHeight(height);
        return true;
    }
}
