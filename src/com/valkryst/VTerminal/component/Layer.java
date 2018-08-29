package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.palette.ColorPalette;
import lombok.NonNull;
import lombok.Setter;
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

    /** The screen that the layer resides on. */
    @Setter private Screen rootScreen;

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

        final int x = this.getTiles().getXPosition() + component.getTiles().getXPosition();
        final int y = this.getTiles().getYPosition() + component.getTiles().getYPosition();
        component.setBoundingBoxOrigin(x, y);

        if (component instanceof Layer) {
            addChildComponentsOfLayer((Layer) component);
            updateChildBoundingBoxesOfLayer((Layer) component);
        }

        // Add the component
        componentsLock.writeLock().lock();

        super.tiles.addChild(component.getTiles());
        components.add(component);

        componentsLock.writeLock().unlock();

        /*
         * This line is relevant only when adding components to a Layer that's already on a Screen.
         *
         * Before a Layer is first added to a Screen, none of the sub-components will have had their listeners
         * initialized. The initialization is done when the Layer is being added to the Screen.
         */
        if (rootScreen != null) {
            for (final EventListener listener : component.getEventListeners()) {
                rootScreen.addListener(listener);
            }
        }
    }

    /**
     * Recursively adds a layer, all of it's child components, and all of the child components of any layer that
     * is a child to the screen.
     *
     * @param layer
     *          The layer.
     */
    private void addChildComponentsOfLayer(final Layer layer) {
        layer.setRootScreen(rootScreen);

        for (final Component component : layer.getComponents()) {
            if (component instanceof Layer) {
                addChildComponentsOfLayer((Layer) component);
            } else {
                /*
                 * This code is only relevant when adding components to a Layer that's already on a Screen.
                 *
                 * When a Layer is first added to a Screen, the redraw function and event listeners are set up
                 * in the addComponent function of the Screen class.
                 */
                if (rootScreen != null) {
                    // Set the component's redraw function
                    component.setRedrawFunction(() -> rootScreen.draw());

                    // Create and add the component's event listeners
                    component.createEventListeners(rootScreen);

                    for (final EventListener listener : component.getEventListeners()) {
                        rootScreen.addListener(listener);
                    }
                }
            }
        }
    }

    /**
     * Recursively updates the bounding box origin points of a layer, all of it's child components, and all of
     * the child components of any layer that is a child to the screen.
     *
     * @param layer
     *          The layer.
     */
    private void updateChildBoundingBoxesOfLayer(final Layer layer) {
        for (final Component component : layer.getComponents()) {
            final int x = layer.getBoundingBoxOrigin().x + component.getTiles().getXPosition();
            final int y = layer.getBoundingBoxOrigin().y + component.getTiles().getYPosition();
            component.setBoundingBoxOrigin(x, y);

            if (component instanceof Layer) {
                updateChildBoundingBoxesOfLayer((Layer) component);
            }
        }
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
        if (component instanceof Layer) {
            removeLayerListeners((Layer) component);
        } else {
            for (final EventListener listener : component.getEventListeners()) {
                removeListener(listener);
            }
        }

        // Reset all of the tiles where the component used to be.
        final int startX = component.getTiles().getXPosition();
        final int startY = component.getTiles().getYPosition();

        final int endX = startX + component.getTiles().getWidth();
        final int endY = startY + component.getTiles().getHeight();

        for (int y = startY ; y < endY ; y++) {
            for (int x = startX ; x < endX ; x++) {
                final Tile tile = tiles.getTileAt(x, y);
                tile.reset();
                tile.setBackgroundColor(rootScreen.getColorPalette().getDefaultBackground());
                tile.setForegroundColor(rootScreen.getColorPalette().getDefaultForeground());
            }
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
     * Removes an event listener from the root screen.
     *
     * @param listener
     *          The listener.
     */
    private void removeListener(final EventListener listener) {
        if (rootScreen != null) {
            rootScreen.removeListener(listener);
        }
    }

    /**
     * Removes all event listeners, that belong to a layer and it's sub components, from the screen.
     *
     * @param layer
     *          The layer.
     */
    private void removeLayerListeners(final Layer layer) {
        if (layer == null) {
            return;
        }

        for (final EventListener listener : layer.getEventListeners()) {
            removeListener(listener);
        }

        for (final Component component : layer.getComponents()) {
            if (component instanceof Layer) {
                removeLayerListeners((Layer) component);
            } else {
                for (final EventListener listener : component.getEventListeners()) {
                    removeListener(listener);
                }
            }
        }
    }

    /**
     * Retrieves all components which use a specific ID.
     *
     * @param id
     *          The ID to search for.
     *
     * @return
     *          All components using the ID.
     */
    public List<Component> getComponentsByID(final String id) {
        if (id == null || id.isEmpty() || components.size() == 0) {
            return new ArrayList<>(0);
        }

        final List<Component> results = new ArrayList<>(1);

        for (final Component component : components) {
            if (component.getId().equals(id)) {
                results.add(component);
            }
        }

        return results;
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
