package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VRadio.Radio;
import lombok.Getter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Optional;

public class Component {
    /** The x-axis (column) coordinate of the top-left character. */
    @Getter private int columnIndex;
    /** The y-axis (row) coordinate of the top-left character. */
    @Getter private int rowIndex;

    /** The width, in characters. */
    @Getter private int width;
    /** The height, in characters. */
    @Getter private int height;

    /** Whether or not the component is currently the target of the user's input. */
    @Getter private boolean isFocused = false;

    /** The bounding box. */
    @Getter private Rectangle boundingBox = new Rectangle();

    /** The strings representing the character-rows of the component. */
    @Getter private AsciiString[] strings;

    @Getter private Radio<String> radio;

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
     *         The width, in characters.
     *
     * @param height
     *         The height, in characters.
     */
    public Component(final int columnIndex, final int rowIndex, final int width, final int height) {
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

        boundingBox.setLocation(columnIndex, rowIndex);
        boundingBox.setSize(width, height);

        strings = new AsciiString[height];

        for (int row = 0 ; row < height ; row++) {
            strings[row] = new AsciiString(width);
        }
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof Component == false) {
            return false;
        }

        // Left out a check for isFocused since two components could be
        // virtually identical other than their focus.
        // Left out a check for radio.
        final Component otherComp = (Component) other;
        boolean isEqual = columnIndex == otherComp.getColumnIndex();
        isEqual &= rowIndex == otherComp.getRowIndex();
        isEqual &= width == otherComp.getWidth();
        isEqual &= height == otherComp.getHeight();
        isEqual &= boundingBox.equals(otherComp.getBoundingBox());
        isEqual &= Arrays.equals(strings, otherComp.getStrings());

        return isEqual;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Component:");
        sb.append("\n\tColumn Index:\t").append(columnIndex);
        sb.append("\n\tRow Index:\t").append(rowIndex);
        sb.append("\n\tWidth:\t").append(width);
        sb.append("\n\tHeight:\t").append(height);
        sb.append("\n\tIs Focused:\t").append(isFocused);
        sb.append("\n\tBounding Box:\t" + boundingBox);
        sb.append("\n\tStrings:\n");

        for (final AsciiString string : strings) {
            for (final AsciiCharacter character : string.getCharacters()) {
                sb.append(character.getCharacter());
            }
            sb.append("\n\t\t");
        }

        sb.append("\n\tRadio:\t" + radio);

        return sb.toString();
    }

    /**
     * Registers events, required by the component, with the specified panel.
     *
     * @param panel
     *         The panel to register events with.
     */
    public void registerEventHandlers(final Panel panel) {
        final Font font = panel.getAsciiFont();
        final int fontWidth = font.getWidth();
        final int fontHeight = font.getHeight();

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    isFocused = intersects(e, fontWidth, fontHeight);
                }
            }

            @Override
            public void mousePressed(final MouseEvent e) {}

            @Override
            public void mouseReleased(final MouseEvent e) {}

            @Override
            public void mouseEntered(final MouseEvent e) {}

            @Override
            public void mouseExited(final MouseEvent e) {}
        });
    }

    /**
     * Draws the component on the specified screen.
     *
     * @param screen
     *         The screen to draw on.
     */
    public void draw(final Screen screen) {
        for (int row = 0 ; row < strings.length ; row++) {
            screen.write(strings[row], columnIndex, rowIndex + row);
        }
    }

    /** Attempts to transmit a "DRAW" event to the assigned Radio. */
    public void transmitDraw() {
        if (radio != null) {
            radio.transmit("DRAW");
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
    public boolean intersects(final Component otherComponent) {
        return otherComponent != null && boundingBox.intersects(otherComponent.getBoundingBox());

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
        intersects &= pointX < (boundingBox.getWidth() + columnIndex);
        intersects &= pointY >= rowIndex;
        intersects &= pointY < (boundingBox.getHeight() + rowIndex);

        return intersects;
    }

    /**
     * Determines whether or not the specified mouse event is at a point that intersects this component.
     *
     * @param event
     *         The event.
     *
     * @param fontWidth
     *         The width of the font being used to draw the component's characters.
     *
     * @param fontHeight
     *         The height of the font being used to draw the component's characters.
     *
     * @return
     *         Whether or not the mouse event is at a point that intersects this component.
     */
    public boolean intersects(final MouseEvent event, final int fontWidth, final int fontHeight) {
        final int mouseX = event.getX() / fontWidth;
        final int mouseY = event.getY() / fontHeight;
        return intersects(mouseX, mouseY);
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
    public boolean isPositionValid(final int columnIndex, final int rowIndex) {
        if (rowIndex < 0 || rowIndex > boundingBox.getHeight() - 1) {
            return false;
        }

        if (columnIndex < 0 || columnIndex > boundingBox.getWidth() - 1) {
            return false;
        }

        return true;
    }

    /**
     * Enables the blink effect for every character.
     *
     * @param millsBetweenBlinks
     *         The amount of time, in milliseconds, before the blink effect can occur.
     *
     * @param radio
     *         The Radio to transmit a DRAW event to whenever a blink occurs.
     */
    public void enableBlinkEffect(final short millsBetweenBlinks, final Radio<String> radio) {
        for (final AsciiString s: strings) {
            for (final AsciiCharacter c : s.getCharacters()) {
                c.enableBlinkEffect(millsBetweenBlinks, radio);
            }
        }
    }

    /** Resumes the blink effect for every character. */
    public void resumeBlinkEffect() {
        for (final AsciiString s: strings) {
            for (final AsciiCharacter c : s.getCharacters()) {
                c.resumeBlinkEffect();
            }
        }
    }

    /** Pauses the blink effect for every character. */
    public void pauseBlinkEffect() {
        for (final AsciiString s: strings) {
            for (final AsciiCharacter c : s.getCharacters()) {
                c.pauseBlinkEffect();
            }
        }
    }

    /** Disables the blink effect for every character. */
    public void disableBlinkEffect() {
        for (final AsciiString s: strings) {
            for (final AsciiCharacter c : s.getCharacters()) {
                c.disableBlinkEffect();
            }
        }
    }

    public void setAllCharactersToBeRedrawn() {
        for (final AsciiString string : strings) {
            string.setAllCharactersToBeRedrawn();
        }
    }

    /**
     * Retrieves the AsciiCharacter at a specific location.
     *
     * @param columnIndex
     *        The x-axis (column) coordinate of the location.
     *
     * @param rowIndex
     *        The y-axis (row) coordinate of the location.
     *
     * @return
     *        The AsciiCharacter at the specified location or nothing
     *        if the location is invalid.
     */
    public Optional<AsciiCharacter> getCharacterAt(final int columnIndex, final int rowIndex) {
        if (isPositionValid(columnIndex, rowIndex)) {
            return Optional.of(strings[rowIndex].getCharacters()[columnIndex]);
        }

        return Optional.empty();
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
    public void setColumnIndex(final int columnIndex) {
        if (columnIndex >= 0) {
            this.columnIndex = columnIndex;
            boundingBox.setLocation(columnIndex, rowIndex);
            setAllCharactersToBeRedrawn();
        }
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
    public void setRowIndex(final int rowIndex) {
        if (rowIndex >= 0) {
            this.rowIndex = rowIndex;
            boundingBox.setLocation(columnIndex, rowIndex);
            setAllCharactersToBeRedrawn();
        }
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
    public void setWidth(final int width) {
        if (width < 0 || width < columnIndex) {
            return;
        }

        this.width = width;
        boundingBox.setSize(width, height);
        setAllCharactersToBeRedrawn();
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
    public void setHeight(final int height) {
        if (height < 0 || height < rowIndex) {
            return;
        }

        this.height = height;
        boundingBox.setSize(width, height);
        setAllCharactersToBeRedrawn();
    }

    /**
     * Sets a new radio.
     *
     * @param radio
     *         The new radio.
     */
    public void setRadio(final Radio<String> radio) {
        if (radio != null) {
            this.radio = radio;
        }
    }
}
