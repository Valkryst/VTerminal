package com.valkryst.AsciiPanel.component;

import com.valkryst.AsciiPanel.AsciiCharacter;
import com.valkryst.AsciiPanel.AsciiString;
import com.valkryst.AsciiPanel.font.AsciiFont;
import lombok.Getter;

import java.awt.*;
import java.util.ArrayList;


public class AsciiScreen extends AsciiComponent {
    /** The components displayed on the screen. */
    @Getter private final ArrayList<AsciiComponent> components = new ArrayList<>();

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
        super(columnIndex, rowIndex, width, height);
    }

    @Override
    public void draw(final AsciiScreen screen) {
        throw new UnsupportedOperationException("An AsciiScreen must be drawn using the draw(canvas, font) method.");
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
        final Graphics gc = canvas.getGraphics();

        // Draw components onto the screen:
        components.forEach(component -> component.draw(this));

        // Draw the screen onto the canvas:
        for (int row = 0 ; row < strings.length ; row++) {
            strings[row].draw(gc, font, row);
        }
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
        return clear(character, 0, 0, super.getWidth(), super.getHeight());
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
            strings[rowIndex].setCharacter(columnIndex, character);
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

        for (int i = 0 ; i < characters.length && i < super.getWidth() ; i++) {
            writesSuccessful &= write(characters[i], columnIndex + i, rowIndex);
        }

        return writesSuccessful;
    }
}