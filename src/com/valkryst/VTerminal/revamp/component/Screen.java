package com.valkryst.VTerminal.revamp.component;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.misc.ImageCache;
import com.valkryst.VTerminal.revamp.component.component.Component;
import lombok.Getter;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Screen {
    /** The canvas on which the screen is drawn. */
    @Getter private final Canvas canvas = new Canvas();

    /** The image cache. */
    private final ImageCache imageCache;

    /** The tiles. */
    private final TileGrid tiles;

    /** The components on the screen. */
    private final List<Component> components = new ArrayList<>(0);

    /** The lock used to control access to the components. */
    private final ReentrantReadWriteLock componentsLock = new ReentrantReadWriteLock();

    /**
     * Constructs a new Screen.
     *
     * @param width
     *          The width, in tiles, of the screen.
     *
     * @param height
     *          The height, in tiles, of the screen.
     *
     * @param imageCache
     *          The image cache.
     *
     * @throws NullPointerException
     *         If the imageCache is null.
     */
    public Screen(final int width, final int height, final @NonNull ImageCache imageCache) {
        this(new Dimension(width, height), imageCache);
    }

    /**
     * Constructs a new Screen.
     *
     * @param dimensions
     *          The dimensions, in tiles, of the screen
     *
     * @param imageCache
     *          The image cache.
     *
     * @throws NullPointerException
     *         If the dimensions or imageCache is null.
     */
    public Screen(final @NonNull Dimension dimensions, final @NonNull ImageCache imageCache) {
        tiles = new TileGrid(dimensions, new Point(0, 0));

        this.imageCache = imageCache;

        // Initialize canvas.
        final int pixelWidth = dimensions.width * imageCache.getFont().getWidth();
        final int pixelHeight = dimensions.height * imageCache.getFont().getHeight();

        canvas.setPreferredSize(new Dimension(pixelWidth, pixelHeight));
    }

    /**
     * Adds the screen's canvas to a JFrame.
     *
     * This does not set the JFrame to visible, so it will not appear on-
     * screen until you do 'JFrame.setVisible(true);'.
     *
     * @return
     *          A JFrame with the canvas on it.
     */
    public JFrame addCanvasToJFrame() {
        final JFrame frame = new JFrame();
        frame.add(canvas);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        return frame;
    }

    /** Draws all of the screen's tiles onto the canvas. */
    public void draw() {
        // Draw components on screen.
        componentsLock.readLock().lock();

        for (final Component component : components) {
            component.draw(this);
        }

        componentsLock.readLock().unlock();

        // Draw screen on canvas.
        final BufferStrategy bs = canvas.getBufferStrategy();

        do {
            do {
                final Graphics2D gc;

                try {
                    gc = (Graphics2D) bs.getDrawGraphics();

                    gc.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
                    gc.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
                    gc.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
                    gc.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

                    // Font characters are pre-rendered images, so no need for AA.
                    gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

                    // No-need for text rendering related options.
                    gc.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
                    gc.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

                    // If alpha is used in the character images, we want computations related to drawing them to be fast.
                    gc.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);

                    // Draw every tile onto the canvas.
                    for (int y = 0 ; y < tiles.getHeight() ; y++) {
                        final int yPosition = tiles.getYPosition() + y;

                        for (int x = 0 ; x < tiles.getWidth() ; x++) {
                            final int xPosition = tiles.getXPosition() + x;

                            tiles.getTileAt(x, y).draw(gc, imageCache, xPosition, yPosition);
                        }
                    }

                    gc.dispose();
                } catch (final NullPointerException | IllegalStateException e) {
                    if (bs == null) {
                        canvas.createBufferStrategy(2);
                        draw();
                        return;
                    }

                    final Logger logger = LogManager.getLogger();
                    logger.error(e);
                }
            } while (bs.contentsRestored()); // Repeat render if drawing buffer contents were restored.

            bs.show();
        } while (bs.contentsLost()); // Repeat render if drawing buffer was lost.
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

        // Add the component
        componentsLock.writeLock().lock();
        components.add(component);
        componentsLock.writeLock().unlock();

        // Add the component's event listeners
        for (final EventListener listener : component.getEventListeners()) {
            addListener(listener);
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

        // Remove the component
        componentsLock.writeLock().lock();
        components.remove(component);
        componentsLock.writeLock().unlock();

        // Remove the component's event listeners
        for (final EventListener listener : component.getEventListeners()) {
            removeListener(listener);
        }
    }

    /** Removes all components from the screen. */
    public void removeAllComponents() {
        componentsLock.writeLock().lock();

        for (final Component component : components) {
            // Remove the component
            components.remove(component);

            // Remove the component's event listeners
            for (final EventListener listener : component.getEventListeners()) {
                removeListener(listener);
            }
        }

        componentsLock.writeLock().unlock();
    }

    /**
     * Adds an event listener to the canvas.
     *
     * @param eventListener
     *        The event listener.
     *
     * @throws IllegalArgumentException
     *        If the event listener isn't supported by this function.
     */
    private void addListener(final EventListener eventListener) {
        if (eventListener instanceof KeyListener) {
            canvas.addKeyListener((KeyListener) eventListener);
            return;
        }

        if (eventListener instanceof MouseInputListener) {
            canvas.addMouseListener((MouseListener) eventListener);
            canvas.addMouseMotionListener((MouseMotionListener) eventListener);
            return;
        }

        if (eventListener instanceof MouseListener) {
            canvas.addMouseListener((MouseListener) eventListener);
            return;
        }

        if (eventListener instanceof MouseMotionListener) {
            canvas.addMouseMotionListener((MouseMotionListener) eventListener);
            return;
        }

        throw new IllegalArgumentException("The " + eventListener.getClass().getSimpleName() + " is not supported.");
    }

    /**
     * Removes an event listener from the canvas.
     *
     * @param eventListener
     *        The event listener.
     *
     * @throws IllegalArgumentException
     *        If the event listener isn't supported by this function.
     */
    private void removeListener(final EventListener eventListener) {
        if (eventListener instanceof KeyListener) {
            canvas.removeKeyListener((KeyListener) eventListener);
            return;
        }

        if (eventListener instanceof MouseInputListener) {
            canvas.removeMouseListener((MouseListener) eventListener);
            canvas.removeMouseMotionListener((MouseMotionListener) eventListener);
            return;
        }

        if (eventListener instanceof MouseListener) {
            canvas.removeMouseListener((MouseListener) eventListener);
            return;
        }

        if (eventListener instanceof MouseMotionListener) {
            canvas.removeMouseMotionListener((MouseMotionListener) eventListener);
            return;
        }

        throw new IllegalArgumentException("The " + eventListener.getClass().getSimpleName() + " is not supported.");
    }

    /**
     * Retrieves the width, also known as the total number of columns, in
     * the component.
     *
     * @return
     *          The width of the grid.
     */
    public int getWidth() {
        return tiles.getWidth();
    }

    /**
     * Retrieves the height, also known as the total number of rows, in
     * the component.
     *
     * @return
     *          The height of the grid.
     */
    public int getHeight() {
        return tiles.getHeight();
    }

    /**
     * Retrieves a tile from the component.
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
    public AsciiCharacter getTileAt(final int x, final int y) {
        return tiles.getTileAt(x, y);
    }
}
