package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.TileGrid;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Collections;
import java.util.EventListener;
import java.util.LinkedList;
import java.util.List;

public class Component {
    /** The tiles. */
    @Getter protected final TileGrid tiles;

    /** The event listeners. */
    protected final List<EventListener> eventListeners = new LinkedList<>();

    /** The function used to redraw the parent of the component. */
    @Setter @NonNull protected Runnable redrawFunction = () -> {};

    /**
     * Constructs a new Component.
     *
     * @param dimensions
     *          The dimensions of the component
     *
     * @param position
     *          The position of the component within it's parent.
     *
     * @throws NullPointerException
     *         If the dimensions or point is null.
     */
    public Component(final @NonNull Dimension dimensions, final @NonNull Point position) {
        tiles = new TileGrid(dimensions, position);
    }

    /**
     * Creates the event listeners.
     *
     * @param parentScreen
     *          The parent screen.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    public void createEventListeners(final @NonNull Screen parentScreen) {}

    /**
     * Determines whether or not a point intersects this component.
     *
     * @param point
     *          The tile-based point position.
     *
     * @return
     *          Whether or not the point intersects this component.
     */
    protected boolean intersects(final Point point) {
        if (point == null) {
            return false;
        }

        boolean intersects = point.x >= tiles.getXPosition();
        intersects &= point.x < (tiles.getWidth() + tiles.getXPosition());
        intersects &= point.y >= tiles.getYPosition();
        intersects &= point.y < (tiles.getHeight() + tiles.getYPosition());
        return intersects;
    }

    /**
     * Retrieves an unmodifiable version of the event listeners list.
     *
     * @return
     *          An unmodifiable version of the event listeners list.
     */
    public List<EventListener> getEventListeners() {
        return Collections.unmodifiableList(eventListeners);
    }

    /**
     * Retrieves a tile from the screen.
     *
     * @param x
     *          The x-axis coordinate of the tile to retrieve.
     *
     * @param y
     *          The y-axis coordinate of the tile to retrieve.
     *
     * @return
     *          The tile, or null if the coordinates are outside the bounds
     *          of the component.
     */
    public Tile getTileAt(final int x, final int y) {
        return tiles.getTileAt(x, y);
    }

    /**
     * Retrieves a tile from the screen.
     *
     * @param position
     *          The x/y-axis coordinates of the tile to retrieve.
     *
     * @return
     *          The tile, or null if the coordinates are outside the bounds
     *          of the component.
     */
    public Tile getTileAt(final Point position) {
        return tiles.getTileAt(position);
    }
}
