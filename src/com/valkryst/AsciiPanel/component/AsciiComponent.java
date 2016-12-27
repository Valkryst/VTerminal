package com.valkryst.AsciiPanel.component;

import com.valkryst.AsciiPanel.AsciiScreen;
import com.valkryst.AsciiPanel.AsciiString;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AsciiComponent {
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

    /** The strings representing the character-rows of the component. */
    protected AsciiString[] strings;

    /**
     * Constructs a new AsciiComponent.
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
    public AsciiComponent(final int columnIndex, final int rowIndex, final int width, final int height) {
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

        boundingBox.setX(columnIndex);
        boundingBox.setY(rowIndex);
        boundingBox.setWidth(width);
        boundingBox.setHeight(height);

        strings = new AsciiString[height];

        for (int row = 0 ; row < height ; row++) {
            strings[row] = new AsciiString(width);
        }
    }

    /**
     * Draws the component on the specified screen.
     *
     * @param screen
     *         The screen to draw on.
     */
    public void draw(final AsciiScreen screen) {
        for (int row = 0 ; row < strings.length ; row++) {
            screen.write(strings[row], columnIndex, rowIndex + row);
        }
    }

    /**
     * Determines if the specified component intersects with this component.
     *
     * @param otherComponent
     *         The component to check intersection with.
     *
     * @return
     *         Whether or not the components intersect.
     */
    public boolean intersects(final AsciiComponent otherComponent) {
        return boundingBox.intersects(otherComponent.getBoundingBox().getBoundsInLocal());
    }

    /**
     * Determines if the specified point intersects with this component.
     *
     * @param pointX
     *         The x-axis (column) coordinate.
     *
     * @param pointY
     *         The y-axis (row) coordinate.
     *
     * @return
     *         Whether or not the point intersects with this component.
     */
    public boolean intersects(final int pointX, final int pointY) {
        boolean intersects = pointX >= columnIndex;
        intersects &= pointX < boundingBox.getWidth();
        intersects &= pointY >= rowIndex;
        intersects &= pointY < boundingBox.getHeight();

        return intersects;
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
        if (rowIndex < 0 || rowIndex >= boundingBox.getHeight()) {
            final Logger logger = LogManager.getLogger();
            logger.error("The specified column of " + columnIndex + " exceeds the maximum height of " + boundingBox.getHeight() + ".");
            return false;
        }

        if (columnIndex < 0 || columnIndex >= boundingBox.getWidth()) {
            final Logger logger = LogManager.getLogger();
            logger.error("The specified row of " + rowIndex + " exceeds the maximum width of " + boundingBox.getWidth() + ".");
            return false;
        }

        return true;
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
