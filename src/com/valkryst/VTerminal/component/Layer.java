package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.palette.ColorPalette;
import lombok.NonNull;
import lombok.ToString;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@ToString
public class Layer extends Component {
    /** The components on the layer. */
    private final List<Component> components = new ArrayList<>(0);

    /** The lock used to control access to the components. */
    private final ReentrantReadWriteLock componentsLock = new ReentrantReadWriteLock();

    /**
     * Constructs a new Layer at position (0, 0) with the default color palette.
     *
     * @param dimensions
     *          The dimensions of the layer.
     */
    public Layer(final @NonNull Dimension dimensions) {
        this(dimensions, null, null);
    }

    /**
     * Constructs a new Layer with the default color palette.
     *
     * @param dimensions
     *          The dimensions of the layer.
     *
     * @param position
     *          The position of the layer within it's parent.
     */
    public Layer(final @NonNull Dimension dimensions, final Point position) {
        this(dimensions, position, null);
    }

    /**
     * Constructs a new Layer.
     *
     * @param dimensions
     *          The dimensions of the layer.
     *
     * @param position
     *          The position of the layer within it's parent.
     *
     *          If null, then the position (0, 0) is used.
     *
     * @param colorPalette
     *          The color palette to color the layer with.
     *
     *          If null, then the default color palette is used.
     */
    public Layer(final @NonNull Dimension dimensions, final Point position, ColorPalette colorPalette) {
        super(dimensions, (position == null ? new Point(0, 0) : position));

        if (colorPalette == null) {
            colorPalette = new ColorPalette();
        }

        for (int y = 0 ; y < super.tiles.getHeight() ; y++) {
            for (int x = 0 ; x < super.tiles.getWidth() ; x++) {
                final Tile tile = super.tiles.getTileAt(x, y);
                tile.setBackgroundColor(colorPalette.getLayer_defaultBackground());
                tile.setForegroundColor(colorPalette.getLayer_defaultForeground());
            }
        }
    }

    /**
     * Adds a component to the layer.
     *
     * @param component
     *          The component.
     */
    public void addComponent(final Component component) {
        if (component == null) {
            return;
        }

        if (component.equals(this)) {
            return;
        }

        if (component instanceof Layer) {
            return;
        }

        // Add the component
        componentsLock.writeLock().lock();
        super.tiles.addChild(component.getTiles());
        components.add(component);
        componentsLock.writeLock().unlock();

        // Add the component's event listeners
        super.eventListeners.addAll(component.getEventListeners());
    }

    /**
     * Removes a component from the layer.
     *
     * @param component
     *          The component.
     */
    public void removeComponent(final Component component) {
        if (component == null) {
            return;
        }

        // Remove the component
        componentsLock.writeLock().lock();
        super.tiles.removeChild(component.getTiles());
        components.remove(component);
        componentsLock.writeLock().unlock();

        // Unset the component's redraw function
        component.setRedrawFunction(() -> {});

        // Remove the component's event listeners
        for (final EventListener listener : component.getEventListeners()) {
            super.eventListeners.remove(listener);
        }
    }

    /** Removes all components from the layer. */
    public void removeAllComponents() {
        componentsLock.writeLock().lock();

        for (final Component component : components) {
            // Remove the component
            super.tiles.removeChild(component.getTiles());
            components.remove(component);

            // Remove the component's event listeners
            for (final EventListener listener : component.getEventListeners()) {
                super.eventListeners.remove(listener);
            }
        }

        componentsLock.writeLock().unlock();
    }

    /**
     * Retrieves an unmodifiable list of the layer's components.
     *
     * @return
     *          An unmodifiable list of the layer's components.
     */
    public List<Component> getComponents() {
        return Collections.unmodifiableList(components);
    }
}
