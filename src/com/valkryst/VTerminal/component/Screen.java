package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.misc.ColoredImageCache;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.LinkedHashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper=true)
@ToString
public class Screen extends Component {
    /** The non-layer components displayed on the screen. */
    private final Set<Component> components = new LinkedHashSet<>();

    /** The layer components displayed on the screen. */
    private final Set<Layer> layerComponents = new LinkedHashSet<>();

    /** The screen components displayed on the screen. */
    private final Set<Screen> screenComponents = new LinkedHashSet<>();

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
    public void draw(final @NonNull Screen screen) {
        throw new UnsupportedOperationException("A Screen must be drawn using the draw(canvas, font) method.");
    }

    /**
     * Draws the screen onto the specified graphics context..
     *
     * @param gc
     *         The graphics context to draw with.
     *
     * @param imageCache
     *         The image cache to retrieve character images from.
     *
     * @throws NullPointerException
     *         If the gc or image cache is null.
     */
    public void draw(final @NonNull Graphics2D gc, final @NonNull ColoredImageCache imageCache) {
        // Draw non-layer components onto the screen:
        components.forEach(component -> component.draw(this));

        // Draw the screen onto the canvas:
        for (int row = 0 ; row < getHeight() ; row++) {
            super.getString(row).draw(gc, imageCache, row);
        }

        // Draw layer components onto the screen:
        layerComponents.forEach(layer -> {
            layer.setAllCharactersToBeRedrawn();
            layer.draw(gc, imageCache);
        });

        // Draw screen components onto the screen:
        screenComponents.forEach(screen -> {
            screen.setAllCharactersToBeRedrawn();
            screen.draw(gc, imageCache);
        });
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
     * @throws NullPointerException
     *         If the character is null.
     */
    public void write(final @NonNull AsciiCharacter character, final int columnIndex, final int rowIndex) {
        if (isPositionValid(columnIndex, rowIndex)) {
            super.getString(rowIndex).setCharacter(columnIndex, character);
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
     *
     * @throws NullPointerException
     *         If the string is null.
     */
    public void write(final @NonNull AsciiString string, final int columnIndex, final int rowIndex) {
        if (isPositionValid(columnIndex, rowIndex)) {
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
     *
     * @throws NullPointerException
     *         If the string is null.
     */
    public void write(final @NonNull String string, final int columnIndex, final int rowIndex) {
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
     *
     * @throws NullPointerException
     *         If the image cache is null.
     */
    public BufferedImage screenshot(final @NonNull ColoredImageCache imageCache) {
        final Font font = imageCache.getFont();
        final int width = this.getWidth() * font.getWidth();
        final int height = this.getHeight() * font.getHeight();

        final BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
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
     *
     * @throws NullPointerException
     *         If the color is null.
     */
    public void setBackgroundColor(final @NonNull Color color) {
        for (final AsciiString string : getStrings()) {
            string.setBackgroundColor(color);
        }
    }

    /**
     * Sets the foreground color of all characters.
     *
     * @param color
     *         The new foreground color.
     *
     * @throws NullPointerException
     *         If the color is null.
     */
    public void setForegroundColor(final @NonNull Color color) {
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
     *
     * @throws NullPointerException
     *         If the background or foreground color is null.
     */
    public void setBackgroundAndForegroundColor(final @NonNull Color background, final @NonNull Color foreground) {
        for (final AsciiString string : getStrings()) {
            string.setBackgroundColor(background);
            string.setForegroundColor(foreground);
        }
    }

    /**
     * Adds a component to the screen.
     *
     * @param component
     *          The component.
     *
     * @throws NullPointerException
     *         If the component is null.
     *
     * @throws IllegalArgumentException
     *         If the component is this.
     */
    public void addComponent(final @NonNull Component component) {
        if (component == this) {
            throw new IllegalArgumentException("A screen cannot be added to itself.");
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
     * Adds one or more components to the screen.
     *
     * @param components
     *        The components.
     *
     * @throws NullPointerException
     *         If the components are null.
     */
    public void addComponents(final @NonNull Component ... components) {
        for (int i = 0 ; i < components.length ; i++) {
            addComponent(components[i]);
        }
    }

    /**
     * Removes a component from the screen.
     *
     * @param component
     *          The component.
     *
     * @throws NullPointerException
     *         If the component is null.
     *
     * @throws IllegalArgumentException
     *         If the component is this.
     */
    public void removeComponent(final @NonNull Component component) {
        if (component == this) {
            throw new IllegalArgumentException("A screen cannot be removed from itself.");
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
     * Removes one or more components from the screen.
     *
     * @param components
     *        The components.
     *
     * @throws NullPointerException
     *         If the components are null.
     */
    public void removeComponents(final @NonNull Component ... components) {
        for (int i = 0 ; i < components.length ; i++) {
            removeComponent(components[i]);
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
     *
     * @throws NullPointerException
     *         If the component is null.
     */
    public boolean containsComponent(final @NonNull Component component) {
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
     *
     * @throws NullPointerException
     *         If the component is null.
     */
    public boolean recursiveContainsComponent(final @NonNull Component component) {
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