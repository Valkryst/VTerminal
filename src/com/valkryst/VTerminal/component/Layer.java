package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.TileGrid;
import com.valkryst.VTerminal.builder.LayerBuilder;
import com.valkryst.VTerminal.palette.ColorPalette;
import lombok.ToString;

import java.util.ArrayList;
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
     * Constructs a new Layer.
     *
     * @param builder
     *        The builder to use.
     */
    public Layer(final LayerBuilder builder) {
        super(builder.getDimensions(), builder.getPosition());

        final ColorPalette colorPalette = builder.getColorPalette();

        for (int y = 0 ; y < super.tiles.getHeight() ; y++) {
            for (int x = 0 ; x < super.tiles.getWidth() ; x++) {
                final Tile tile = super.tiles.getTileAt(x, y);
                tile.setBackgroundColor(colorPalette.getLayer_defaultBackground());
                tile.setForegroundColor(colorPalette.getLayer_defaultForeground());
            }
        }
    }

    @Override
    public void draw(final TileGrid grid) {
        super.draw(grid);

        componentsLock.readLock().lock();
        for (final Component component : components) {
            component.draw(grid);
        }
        componentsLock.readLock().unlock();
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
            components.remove(component);

            // Remove the component's event listeners
            for (final EventListener listener : component.getEventListeners()) {
                super.eventListeners.remove(listener);
            }
        }

        componentsLock.writeLock().unlock();
    }
}
