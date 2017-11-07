package com.valkryst.VTerminal.component;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.AsciiTile;
import com.valkryst.VTerminal.builder.component.ComponentBuilder;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.shader.Shader;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;

@ToString
public class Component {
    /** The ID. Not guaranteed to be unique. */
    @Getter private String id;

    /** The x/y-axis (column/row) coordinates of the top-left character. */
    @Getter private final Point position;

    /** The width/height, in characters. */
    @Getter final Dimension dimensions;

    /** Whether or not the component is currently the target of the user's input. */
    @Getter protected boolean isFocused = false;

    /** The bounding box. */
    @Getter private Rectangle boundingBox = new Rectangle();

    /** The strings representing the character-rows of the component. */
    @Getter private AsciiString[] strings;

    /** The radio to transmit events to. */
    @Getter protected Radio<String> radio;

    /** The event listeners. */
    @Getter private final Set<EventListener> eventListeners = new HashSet<>();

    /** The font being used to draw the component. */
    @Getter @Setter private Font font;

    /**
     * Constructs a new AsciiComponent.
     *
     * @param builder
     *         The builder to use.
     *
     * @throws NullPointerException
     *         If the builder is null.
     */
    public Component(final @NonNull ComponentBuilder builder) {
        this.id = builder.getId();
        position = new Point(builder.getColumnIndex(), builder.getRowIndex());
        dimensions = new Dimension(builder.getWidth(), builder.getHeight());

        boundingBox.setLocation(position);
        boundingBox.setSize(dimensions.width, dimensions.height);

        strings = new AsciiString[dimensions.height];

        for (int row = 0 ; row < dimensions.height ; row++) {
            strings[row] = new AsciiString(dimensions.width);
        }

        this.radio = new Radio<>();

        createEventListeners();
    }

    /** Creates all required event listeners for the component. */
    protected void createEventListeners() {
        if (eventListeners.size() > 0) {
            return;
        }

        final MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    isFocused = intersects(e);
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
        };

        eventListeners.add(mouseListener);
    }

    /**
     * Draws the component on the specified screen.
     *
     * @param screen
     *         The screen to draw on.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    public void draw(final @NonNull Screen screen) {
        for (int row = 0 ; row < strings.length ; row++) {
            screen.write(strings[row], new Point(position.x, position.y + row));
        }
    }

    /** Transmits a "DRAW" event to the assigned Radio. */
    public void transmitDraw() {
        radio.transmit("DRAW");
    }

    /** Converts every AsciiCharacter of every AsciiString into an AsciiTile. */
    public void convertAsciiCharactersToAsciiTiles() {
        for (final AsciiString string : strings) {
            final AsciiCharacter[] characters = string.getCharacters();

            for (int i = 0 ; i < characters.length ; i++) {
                string.setCharacter(i, new AsciiTile(characters[i]));
            }
        }
    }

    /** Converts every AsciiTile of every AsciiString into an AsciiCharacter. */
    public void convertAsciiTilesToAsciiCharacters() {
        for (final AsciiString string : strings) {
            final AsciiCharacter[] characters = string.getCharacters();

            for (int i = 0 ; i < characters.length ; i++) {
                if (characters[i] instanceof AsciiTile) {
                    string.setCharacter(i, new AsciiCharacter(characters[i]));
                }
            }
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
     *
     * @throws NullPointerException
     *         If the other component is null.
     */
    public boolean intersects(final @NonNull Component otherComponent) {
        return boundingBox.intersects(otherComponent.getBoundingBox());

    }

    /**
     * Determines if the specified point intersects with this component.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates.
     *
     * @return
     *         Whether or not the point intersects with this component.
     */
    public boolean intersects(final Point position) {
        boolean intersects = position.x >= boundingBox.x;
        intersects &= position.x < (boundingBox.getWidth() + boundingBox.x);
        intersects &= position.y >= boundingBox.y;
        intersects &= position.y < (boundingBox.getHeight() + boundingBox.y);

        return intersects;
    }

    /**
     * Determines whether or not the specified mouse event is at a point that
     * intersects this component.
     *
     * @param event
     *         The event.
     *
     * @return
     *         Whether or not the mouse event is at a point that intersects this component.
     *
     * @throws NullPointerException
     *         If the event is null.
     */
    public boolean intersects(final @NonNull MouseEvent event) {
        final int mouseX = event.getX() / font.getWidth();
        final int mouseY = event.getY() / font.getHeight();
        return intersects(new Point(mouseX, mouseY));
    }

    /**
     * Determines whether or not the specified position is within the bounds of
     * the component.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates.
     *
     * @return
     *         Whether or not the specified position is within the bounds of the
     *         component.
     */
    public boolean isPositionValid(final Point position) {
        if (position.y < 0 || position.y > boundingBox.getHeight() - 1) {
            return false;
        }

        if (position.x < 0 || position.x > boundingBox.getWidth() - 1) {
            return false;
        }

        return true;
    }

    /**
     * Enables the blink effect for every character.
     *
     * @param millsBetweenBlinks
     *         The amount of time, in milliseconds, before the blink effect can
     */
    public void enableBlinkEffect(final short millsBetweenBlinks) {
        for (final AsciiString s: strings) {
            for (final AsciiCharacter c : s.getCharacters()) {
                c.enableBlinkEffect(millsBetweenBlinks, radio);
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
     * Adds one or more shaders to every character of the component.
     *
     * @param shaders
     *          The shaders.
     */
    public void addShaders(final @NonNull Shader... shaders) {
        for (final AsciiString string : strings) {
            string.addShaders(shaders);
        }
    }

    /**
     * Removes one or more shaders from every character of the component.
     *
     * @param shaders
     *          The shaders.
     */
    public void removeShaders(final @NonNull Shader ... shaders) {
        for (final AsciiString string : strings) {
            string.removeShaders(shaders);
        }
    }

    /**
     * Retrieves the string corresponding to a row.
     *
     * @param rowIndex
     *        The row index.
     *
     * @return
     *        The string.
     */
    public AsciiString getString(final int rowIndex) {
        return strings[rowIndex];
    }

    /**
     * Retrieves the AsciiCharacter at a specific position.
     *
     * @param position
     *        The x/y-axis (column/row) coordinates of the position.
     *
     * @return
     *        The AsciiCharacter.
     *
     * @throws IllegalArgumentException
     *         If the position is invalid.
     */
    public AsciiCharacter getCharacterAt(final Point position) {
        if (isPositionValid(position)) {
            return strings[position.y].getCharacters()[position.x];
        }

        throw new IllegalArgumentException("The position (" + position.x + " columnIndex, " + position.y + " rowIndex) is invalid.");
    }

    /**
     * Sets a new position.
     *
     * @param position
     *          The new position.
     */
    public void setPosition(final Point position) {
        if (position.x < 0) {
            throw new IllegalArgumentException("The x-axis position cannot be < 0.");
        }

        if (position.y < 0) {
            throw new IllegalArgumentException("The y-axis position cannot be < 0.");
        }

        this.position.setLocation(position);
        this.boundingBox.setLocation(position);
    }

    /**
     * Retrieves the width of the component.
     *
     * @return
     *         The width.
     */
    public int getWidth() {
        return dimensions.width;
    }

    /**
     * Retrieves the height of the component.
     *
     * @return
     *         The height.
     */
    public int getHeight() {
        return dimensions.height;
    }
}