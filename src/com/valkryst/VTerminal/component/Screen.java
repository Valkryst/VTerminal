package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.misc.ColoredImageCache;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


public class Screen extends Component {
    /** The non-layer components displayed on the screen. */
    private final Set<Component> components = new HashSet<>();

    /** The layer components displayed on the screen. */
    private final Set<Layer> layerComponents = new HashSet<>();

    /** The screen components displayed on the screen. */
    private final Set<Screen> screenComponents = new HashSet<>();

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
     *         The width, in characters.
     *
     * @param height
     *         The height, in characters.
     */
    public Screen(final int columnIndex, final int rowIndex, final int width, final int height) {
        super(columnIndex, rowIndex, width, height);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), components, layerComponents, screenComponents);
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
     * @param imageCache
     *         The image cache to retrieve character images from.
     */
    public void draw(final Graphics2D gc, final ColoredImageCache imageCache) {
        // Draw non-layer components onto the screen:
        components.forEach(component -> component.draw(this));

        // Draw the screen onto the canvas:
        for (int row = 0 ; row < getHeight() ; row++) {
            super.getString(row).draw(gc, imageCache, row);
        }

        // Draw layer components onto the screen:
        layerComponents.forEach(layer -> layer.draw(gc, imageCache));

        // Draw screen components onto the screen:
        screenComponents.forEach(screen -> screen.draw(gc, imageCache));
    }

    /**
     * Clears the entire screen.
     *
     * @param character
     *         The character to replace every character on the screen with.
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
            super.getString(rowIndex).setCharacter(columnIndex, character);
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
            super.getString(rowIndex).setCharacter(columnIndex, character);
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
    public void write(final String string, final int columnIndex, final int rowIndex) {
        write(new AsciiString(string), columnIndex, rowIndex);
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
     * @param imageCache
     *         The image cache to retrieve character images from.
     *
     * @return
     *        An image of the screen.
     */
    public BufferedImage screenshot(final ColoredImageCache imageCache) {
        final BufferedImage img = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        final Graphics2D gc = img.createGraphics();

        for (final AsciiString string : getStrings()) {
            string.setAllCharactersToBeRedrawn();
        }

        draw(gc, imageCache);
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

        for (final AsciiString string : getStrings()) {
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

        for (final AsciiString string : getStrings()) {
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

        for (final AsciiString string : getStrings()) {
            string.setBackgroundAndForegroundColor(background, foreground);
        }
    }

    /**
     * Adds a component to the screen.
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

        if (component instanceof Layer) {
            component.setScreen(this);
            layerComponents.add((Layer) component);
        } else if (component instanceof  Screen) {
            // Prevent an endless draw-loop by ensuring that
            // a screen cannot be added if it's contained
            // within any of this screen's sub-screens.
            if (recursiveContainsComponent(component) == false) {
                component.setScreen(this);
                screenComponents.add((Screen) component);
            }
        } else {
            component.setScreen(this);
            components.add(component);
        }
    }

    /**
     * Removes a component from the screen.
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

        if (component instanceof Layer) {
            component.setScreen(null);
            layerComponents.remove(component);
        } else if (component instanceof Screen) {
            component.setScreen(null);
            screenComponents.remove(component);
        } else{
            component.setScreen(null);
            components.remove(component);
        }
    }

    /**
     * Determines whether or not the screen contains a specific
     * component.
     *
     * @param component
     *        The component.
     *
     * @return
     *        Whether or not the screen contains the component.
     */
    public boolean containsComponent(final Component component) {
        if (component == null) {
            return false;
        }

        if (component == this) {
            return false;
        }

        if (component instanceof Layer) {
            if (layerComponents.contains(component)) {
                return true;
            }
        }

        if (component instanceof Screen) {
            if (screenComponents.contains(component)) {
                return true;
            }
        }

        if (components.contains(component)) {
            return true;
        }

        return false;
    }

    /**
     * Determines whether or not the screen, or any sub-screen of
     * the screen, contains a specific component.
     *
     * @param component
     *        The component.
     *
     * @return
     *        Whether or not the component is contained within the
     *        screen or any sub-screen.
     */
    public boolean recursiveContainsComponent(final Component component) {
        if (component == null) {
            return false;
        }

        if (component == this) {
            return false;
        }

        if (containsComponent(component)) {
            return true;
        }

        if (component instanceof Screen) {
            if (((Screen) component).containsComponent(this)) {
                return true;
            }
        }

        for (final Screen screen : screenComponents) {
            if (screen.containsComponent(component)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Determines the total number of components.
     *
     * @return
     *        The total number of components.
     */
    public int totalComponents() {
        int sum = components.size();
        sum += layerComponents.size();
        sum += screenComponents.size();

        return sum;
    }
}