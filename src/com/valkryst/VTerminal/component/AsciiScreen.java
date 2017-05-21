package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiPanel;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.font.AsciiFont;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;


public class AsciiScreen extends AsciiComponent {
    /** The components displayed on the screen. */
    private final ArrayList<AsciiComponent> components = new ArrayList<>();

    @Getter private int topRowIndex = 0;
    @Setter private AsciiPanel parentPanel;

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
        for (int row = topRowIndex ; row < topRowIndex + parentPanel.getHeightInCharacters() ; row++) {
            strings[row].draw(gc, font, row - topRowIndex);
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
        return clear(character, 0, 0, super.getWidth() - 1, super.getHeight() - 1);
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
    public void addComponent(final AsciiComponent component) {
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
    public void removeComponent(final AsciiComponent component) {
        components.remove(component);
    }

    public void setTopRowIndex(final int topRowIndex) {
        if (topRowIndex < 0) {
            this.topRowIndex = 0;
        } else if (super.strings.length < super.height) {
            this.topRowIndex = 0;
        } else if (topRowIndex + parentPanel.getHeightInCharacters() <= super.strings.length) {
            this.topRowIndex = topRowIndex;
        } else {
            this.topRowIndex = super.strings.length - parentPanel.getHeightInCharacters();
        }

        for (int row = 0 ; row < super.strings.length ; row++) {
            boolean showRow = row >= topRowIndex;
            showRow &= row < topRowIndex + parentPanel.getHeightInCharacters();

            System.out.println((showRow ? "Showing:\t" : "Hiding:\t") + "Row #" + row);
            strings[row].setHidden(! showRow);

            if (! showRow) {
                strings[row].pauseBlinkEffect();
            } else {
                strings[row].resumeBlinkEffect();
            }
        }
    }
}