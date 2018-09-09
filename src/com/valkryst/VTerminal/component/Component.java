package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.TileGrid;
import com.valkryst.VTerminal.palette.ColorPalette;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Component {
    /** The ID of the component. Not guaranteed to be unique. */
    @Getter @Setter private String id = UUID.randomUUID().toString();

    /** The tiles. */
    @Getter protected final TileGrid tiles;

    /** The origin point of the bounding box. */
    @Getter private final Point boundingBoxOrigin;

    /** The color palette. */
    @Getter protected ColorPalette colorPalette;

    /** The event listeners. */
    final List<EventListener> eventListeners = new LinkedList<>();

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
     *          If the dimensions or point is null.
     */
    public Component(final @NonNull Dimension dimensions, final @NonNull Point position) {
        tiles = new TileGrid(dimensions, position);
        boundingBoxOrigin = new Point(tiles.getXPosition(), tiles.getYPosition());
    }

    /**
     * Creates the event listeners.
     *
     * @param parentScreen
     *          The parent screen.
     *
     * @throws NullPointerException
     *          If the screen is null.
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
    public boolean intersects(final Point point) {
        if (point == null) {
            return false;
        }

        boolean intersects = (point.x >= boundingBoxOrigin.x);
        intersects &= (point.x < (tiles.getWidth() + boundingBoxOrigin.x));
        intersects &= (point.y >= boundingBoxOrigin.y);
        intersects &= (point.y < (tiles.getHeight() + boundingBoxOrigin.y));
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
     * Retrieves a tile from the component's TileGrid.
     *
     * @param x
     *          The x-axis coordinate of the tile.
     *
     * @param y
     *          The y-axis coordinate of the tile.
     *
     * @return
     *          The tile, or null if the coordinates are outside the bounds of the component.
     */
    public Tile getTileAt(final int x, final int y) {
        return tiles.getTileAt(x, y);
    }

    /**
     * Changes the bounding box origin.
     *
     * @param x
     *          The x-axis coordinate of the origin.
     *
     * @param y
     *          The y-axis coordinate of the origin..
     */
    public void setBoundingBoxOrigin(final int x, final int y) {
        boundingBoxOrigin.setLocation(x, y);
    }

    /**
     * Changes the color palette of the component.
     *
     * @param colorPalette
     *          The new palette.
     *
     * @param redraw
     *          Whether to call the redraw function after changing the color palette.
     */
    public void setColorPalette(final ColorPalette colorPalette, final boolean redraw) {
        if (colorPalette == null) {
            return;
        }

        this.colorPalette = colorPalette;

        final Color backgroundColor = colorPalette.getDefaultBackground();
        final Color foregroundColor = colorPalette.getDefaultForeground();

        for (int y = 0 ; y < tiles.getHeight() ; y++) {
            for (int x = 0 ; x < tiles.getWidth() ; x++) {
                final Tile tile = tiles.getTileAt(x, y);
                tile.setBackgroundColor(backgroundColor);
                tile.setForegroundColor(foregroundColor);
            }
        }

        if (redraw) {
            redrawFunction.run();
        }
    }
}
