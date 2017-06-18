package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.radio.Radio;
import lombok.Getter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Component {
    /** The x-axis (column) coordinate of the top-left character. */
    @Getter private int columnIndex;
    /** The y-axis (row) coordinate of the top-left character. */
    @Getter private int rowIndex;

    /** The width, in characters. */
    @Getter protected int width;
    /** The height, in characters. */
    @Getter protected int height;

    /** Whether or not the component is currently the target of the user's input. */
    @Getter private boolean isFocused = false;

    /** The bounding box. */
    @Getter private Rectangle boundingBox = new Rectangle();

    /** The strings representing the character-rows of the component. */
    @Getter protected AsciiString[] strings;

    @Getter protected Radio<String> radio;

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
    protected boolean isPositionValid(final int columnIndex, final int rowIndex) {
        if (rowIndex < 0 || rowIndex > boundingBox.getHeight()) {
            return false;
        }

        if (columnIndex < 0 || columnIndex > boundingBox.getWidth()) {
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
        boundingBox.setLocation(columnIndex, rowIndex);
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
        boundingBox.setLocation(columnIndex, rowIndex);
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
        boundingBox.setSize(width, height);
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
        boundingBox.setSize(width, height);
        return true;
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
