package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.font.Font;
import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class Screen extends Component {
    /** The components displayed on the screen. */
    @Getter private final ArrayList<Component> components = new ArrayList<>();

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
     * @param gc
     *         The graphics context to draw with.
     *
     * @param font
     *         The font to draw with.
     */
    public void draw(final Graphics2D gc, final Font font) {
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
    public void clear(final char character) {
        clear(character, 0, 0, super.getWidth(), super.getHeight());
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
     */
    public void clear(final char character, final int columnIndex, final int rowIndex, int width, int height) {
        boolean canProceed = isPositionValid(columnIndex, rowIndex);
        canProceed &= width >= 0;
        canProceed &= height >= 0;

        if (canProceed) {
            width += columnIndex;
            height += rowIndex;

            for (int column = columnIndex ; column < width ; column++) {
                for (int row = rowIndex ; row < height ; row++) {
                    write(character, column, row);
                }
            }
        }
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
        canProceed &= character != null;

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
     */
    public void write(final char character, final int columnIndex, final int rowIndex) {
        if (isPositionValid(columnIndex, rowIndex)) {
            strings[rowIndex].setCharacter(columnIndex, character);
        }
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
     */
    public void write(final AsciiString string, final int columnIndex, final int rowIndex) {
        boolean canProceed = isPositionValid(columnIndex, rowIndex);
        canProceed &= string != null;

        if (canProceed) {
            final AsciiCharacter[] characters = string.getCharacters();

            for (int i = 0; i < characters.length && i < super.getWidth(); i++) {
                write(characters[i], columnIndex + i, rowIndex);
            }
        }
    }

    /**
     * Draws the screen onto an image.
     *
     * This calls the draw function, so the screen may look a
     * little different if there are blink effects or new updates
     * to characters that haven't yet been drawn.
     *
     * This is an expensive operation as it essentially creates
     * an in-memory screen and draws each AsciiCharacter onto
     * that screen.
     *
     * @param asciiFont
     *        The font to render the screen with.
     *
     * @return
     *        An image of the screen.
     */
    public BufferedImage screenshot(final Font asciiFont) {
        final BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        final Graphics2D gc = img.createGraphics();

        for (final AsciiString string : strings) {
            string.setAllCharactersToBeRedrawn();
        }

        draw(gc, asciiFont);
        gc.dispose();

        return img;
    }

    /**
     * Sets the background color of all characters.
     *
     * @param color
     *         The new background color.
     */
    public void setBackgroundColor(final Color color) {
        if (color == null) {
            return;
        }

        for (final AsciiString string : strings) {
            string.setBackgroundColor(color);
        }
    }

    /**
     * Sets the foreground color of all characters.
     *
     * @param color
     *         The new foreground color.
     */
    public void setForegroundColor(final Color color) {
        if (color == null) {
            return;
        }

        for (final AsciiString string : strings) {
            string.setForegroundColor(color);
        }
    }

    /**
     * Sets the background and foreground color of all characters.
     *
     * @param background
     *         The new background color.
     *
     * @param foreground
     *         The new foreground color.
     */
    public void setBackgroundAndForegroundColor(final Color background, final Color foreground) {
        if (background == null || foreground == null) {
            return;
        }

        for (final AsciiString string : strings) {
            string.setBackgroundAndForegroundColor(background, foreground);
        }
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

        if (component == this) {
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
        if (component == null) {
            return;
        }

        if (component == this) {
            return;
        }

        components.remove(component);
    }
}