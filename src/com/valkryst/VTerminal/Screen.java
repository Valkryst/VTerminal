package com.valkryst.VTerminal;

import com.valkryst.VTerminal.component.Component;
import com.valkryst.VTerminal.component.Layer;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.misc.ImageCache;
import com.valkryst.VTerminal.palette.ColorPalette;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Screen {
    /** The canvas on which the screen is drawn. */
    @Getter private final Canvas canvas = new Canvas();

    /** The image cache. */
    @Getter private final ImageCache imageCache;

    /** The tiles. */
    @Getter private final TileGrid tiles;

    private final int[][] tileHashes;

    /** The components on the screen. */
    private final List<Component> components = new ArrayList<>(0);

    /** The lock used to control access to the components. */
    private final ReentrantReadWriteLock componentsLock = new ReentrantReadWriteLock();

    /** The last known tile-based position of the mouse. */
    private final Point mousePosition = new Point(0, 0);

    /** The color palette of the Screen. Does not apply to child components. */
    private ColorPalette colorPalette;

    private boolean isInFullScreenExclusiveMode = false;

    /**
     * Constructs a new 80x40 Screen with the 18pt DejaVu Sans Mono font.
     *
     * @throws IOException
     *          If an IOException occurs while loading the font.
     *
     */
    public Screen() throws IOException {
        this(FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt", 1));
    }

    /**
     * Constructs a new Screen with the 18pt DejaVu Sans Mono font.
     *
     * @param width
     *          The width, in tiles, of the screen.
     *
     * @param height
     *          The height, in tiles, of the screen.
     *
     * @throws IOException
     *          If an IOException occurs while loading the font.
     */
    public Screen(final int width, final int height) throws IOException {
        this(width, height, FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt", 1));
    }

    /**
     * Constructs a new 80x40 Screen.
     *
     * @param font
     *          The font.
     */
    public Screen(final @NonNull Font font) {
        this(new Dimension(80, 40), font);
    }

    /**
     * Constructs a new Screen.
     *
     * @param width
     *          The width, in tiles, of the screen.
     *
     * @param height
     *          The height, in tiles, of the screen.
     *
     * @param font
     *          The font.
     *
     * @throws NullPointerException
     *         If the imageCache is null.
     */
    public Screen(final int width, final int height, final @NonNull Font font) {
        this(new Dimension(width, height), font);
    }

    /**
     * Constructs a new Screen.
     *
     * @param dimensions
     *          The dimensions, in tiles, of the screen
     *
     * @param font
     *          The font.
     *
     * @throws NullPointerException
     *         If the dimensions or imageCache is null.
     */
    public Screen(final @NonNull Dimension dimensions, final @NonNull Font font) {
        tiles = new TileGrid(dimensions, new Point(0, 0));
        tileHashes = new int[dimensions.height][dimensions.width];
        setColorPalette(new ColorPalette());

        this.imageCache = new ImageCache(font);

        // Initialize canvas.
        final int pixelWidth = dimensions.width * imageCache.getFont().getWidth();
        final int pixelHeight = dimensions.height * imageCache.getFont().getHeight();

        canvas.setPreferredSize(new Dimension(pixelWidth, pixelHeight));
        canvas.setIgnoreRepaint(true);

        // Add mouse movement listener.
        addListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(final MouseEvent e) {}

            @Override
            public void mouseMoved(final MouseEvent e) {
                final Font font = imageCache.getFont();

                final int mouseX = e.getX() / font.getWidth();
                final int mouseY = e.getY() / font.getHeight();

                mousePosition.setLocation(mouseX, mouseY);
            }
        });
    }

    /**
     * Adds the screen's canvas to a Frame and sets the frame to visible.
     *
     * @return
     *          A frame with the canvas on it.
     */
    public Frame addCanvasToFrame() {
        final Frame frame = new Frame();
        frame.add(canvas);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setIgnoreRepaint(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent e) {
                frame.dispose();
                System.exit(0);
            }
        });
        frame.setBackground(colorPalette.getDefaultBackground());
        frame.setForeground(colorPalette.getDefaultForeground());
        frame.setVisible(true);

        // There's a rare, hard to reproduce, issue where, on the first
        // render of a Screen, it may just display a blank white canvas.
        // This sleep is meant to fix that issue by giving the Canvas time
        // to initialize, or something like that.
        try {
            Thread.sleep(100);
        } catch (final InterruptedException ignored) {}

        draw();

        return frame;
    }

    /**
     * Adds the screen's canvas to a Frame, sets the frame to visible,
     * and enables full-screen exclusive mode on the Frame, for a
     * specific device (monitor).
     *
     * @param device
     *          The device (monitor) to enable full-screen for.
     *
     * @return
     *          A full-screened frame with the canvas on it.
     *
     * @throws NullPointerException
     *          If the device is null.
     */
    public Frame addCanvasToFullScreenFrame(final @NonNull GraphicsDevice device) {
        if (! device.isFullScreenSupported()) {
            LogManager.getLogger().error("Full screen is not supported for the device '" + device.getIDstring() + "'.");
            addCanvasToFrame();
        }

        // Resize the font, so that it fills the screen properly.
        final DisplayMode displayMode = device.getDisplayMode();

        final double scaleX = displayMode.getWidth() / canvas.getPreferredSize().getWidth();
        final double scaleY = displayMode.getHeight() / canvas.getPreferredSize().getHeight();
        imageCache.getFont().resize(scaleX, scaleY);
        imageCache.invalidate();

        // Resize the canvas, so that it has enough room to fit the resized font.
        final int pixelWidth = tiles.getWidth() * imageCache.getFont().getWidth();
        final int pixelHeight = tiles.getHeight() * imageCache.getFont().getHeight();

        canvas.setPreferredSize(new Dimension(pixelWidth, pixelHeight));

        // Create the frame.
        final Frame frame = new Frame();
        frame.setUndecorated(true);
        frame.setBackground(colorPalette.getDefaultBackground());
        frame.setForeground(colorPalette.getDefaultForeground());
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent e) {
                device.setFullScreenWindow(null);
                frame.dispose();
                System.exit(0);
            }
        });
        frame.add(canvas);
        frame.setResizable(false);
        frame.setIgnoreRepaint(true);
        frame.setVisible(true);

        device.setFullScreenWindow(frame);
        isInFullScreenExclusiveMode = true;

        // There's a rare, hard to reproduce, issue where, on the first
        // render of a Screen, it may just display a blank white canvas.
        // This sleep is meant to fix that issue by giving the Canvas time
        // to initialize, or something like that.
        try {
            Thread.sleep(100);
        } catch (final InterruptedException ignored) {}

        draw();

        return frame;
    }

    /** Draws all of the screen's tiles onto the canvas. */
    public void draw() {
        // Draw components on screen.
        componentsLock.readLock().lock();

        for (final Component component : components) {
            component.draw(tiles);
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

                    // Draw every tile, whose hash has changed, onto the canvas.
                    for (int y = 0; y < tiles.getHeight(); y++) {
                        final int yPosition = tiles.getYPosition() + y;

                        for (int x = 0; x < tiles.getWidth(); x++) {
                            final int xPosition = tiles.getXPosition() + x;

                            final Tile tile = tiles.getTileAt(x, y);

                            // Determine if hash has changed.
                            if (tileHashes[y][x] == 0 || tileHashes[y][x] != tile.getCacheHash()) {
                                tileHashes[y][x] = tile.getCacheHash();
                                tiles.getTileAt(x, y).draw(gc, imageCache, xPosition, yPosition);
                            }
                        }
                    }

                    gc.dispose();
                } catch (final NullPointerException | IllegalStateException e) {
                    if (bs == null) {
                        // Create Canvas BufferStrategy
                        if (isInFullScreenExclusiveMode && SystemUtils.IS_OS_WINDOWS) {
                            canvas.createBufferStrategy(1);
                        } else {
                            canvas.createBufferStrategy(2);
                        }

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

        if (component instanceof Layer) {
            for (final Component layerComponent : ((Layer) component).getComponents()) {
                addComponent(layerComponent);
            }
        }

        // Add the component
        componentsLock.writeLock().lock();
        components.add(component);
        componentsLock.writeLock().unlock();

        // Set the component's redraw function
        component.setRedrawFunction(this::draw);

        // Create the component's event listeners
        component.createEventListeners(this);

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

        if (component instanceof Layer) {
            for (final Component layerComponent : ((Layer) component).getComponents()) {
                removeComponent(layerComponent);
            }
        }

        // Remove the component
        componentsLock.writeLock().lock();
        components.remove(component);
        componentsLock.writeLock().unlock();

        // Unset the component's redraw function
        component.setRedrawFunction(() -> {});

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
    public void addListener(final EventListener eventListener) {
        if (eventListener == null) {
            return;
        }

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
    public void removeListener(final EventListener eventListener) {
        if (eventListener == null) {
            return;
        }

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
     * Retrieves the last known tile-based position of the mouse.
     *
     * @return
     *          The last known tile-based position of the mouse.
     */
    public Point getMousePosition() {
        return new Point(mousePosition);
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
     *          of the screen.
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
     *          of the screen or if the position is null.
     */
    public Tile getTileAt(final Point position) {
        if (position == null) {
            return null;
        }

        return tiles.getTileAt(position);
    }

    /**
     * Sets the fore/background color of every tile on the screen to the
     * default fore/background colors of a ColorPalette.
     *
     * This does not affect child components and this is not permanent.
     * These color changes may be overwritten during a draw call.
     *
     * @param colorPalette
     *          The color palette.
     */
    public void setColorPalette(final @NonNull ColorPalette colorPalette) {
        if (colorPalette == null) {
            return;
        }

        this.colorPalette = colorPalette;

        for (int y = 0 ; y < tiles.getHeight() ; y++) {
            for (int x = 0 ; x < tiles.getWidth() ; x++) {
                final Tile tile = tiles.getTileAt(x, y);
                tile.setForegroundColor(colorPalette.getDefaultForeground());
                tile.setBackgroundColor(colorPalette.getDefaultBackground());
            }
        }
    }
}
