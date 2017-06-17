package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.font.Font;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Screen extends Component {
    /** The components displayed on the screen. */
    private final ArrayList<Component> components = new ArrayList<>();

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
    public Screen(final int columnIndex, final int rowIndex, final int width, final int height) {
        super(columnIndex, rowIndex, width, height);
    }

    @Override
    public void draw(final Screen screen) {
        throw new UnsupportedOperationException("A Screen must be drawn using the draw(canvas, font) method.");
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
    public void draw(final JPanel canvas, final Font font) {
        final Graphics gc = canvas.getGraphics();

        // Draw components onto the screen:
        components.forEach(component -> component.draw(this));

        // Draw the screen onto the canvas:
        for (int row = 0 ; row < height ; row++) {
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
    public boolean clear(final char character) {
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
    public boolean clear(final char character, final int columnIndex, final int rowIndex, final int width, final int height) {
        boolean canProceed = isPositionValid(columnIndex, rowIndex);
        canProceed &= isPositionValid(width, height);

        if (canProceed) {
            for (int column = columnIndex ; column < width ; column++) {
                for (int row = rowIndex ; row < height ; row++) {
                    canProceed &= write(character, column, row);
                }
            }
        }

        return canProceed;
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
    public boolean write(final char character, final int columnIndex, final int rowIndex) {
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
        if (isPositionValid(columnIndex, rowIndex) == false) {
            return false;
        }

        boolean writesSuccessful = true;
        final AsciiCharacter[] characters = string.getCharacters();

        for (int i = 0 ; i < characters.length && i < super.getWidth() ; i++) {
            writesSuccessful &= write(characters[i], columnIndex + i, rowIndex);
        }

        return writesSuccessful;
    }

    /**
     * Adds a component to the AsciiScreen.
     *
     * @param component
     *          The component.
     */
    public void addComponent(final Component component) {
        if (component == null) {
            return;
        }

        if (components.contains(component)) {
            return;
        }

        components.add(component);
    }

    /**
     * Removes a component from the AsciiScreen.
     *
     * @param component
     *          The component.
     */
    public void removeComponent(final Component component) {
        components.remove(component);
    }
}