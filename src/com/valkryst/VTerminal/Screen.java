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

import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.Consumer;

public class Screen {
    /** The canvas on which the screen is drawn. */
    @Getter private final Canvas canvas = new Canvas();

    /** The image cache. */
    @Getter private final ImageCache imageCache;

    /** The tiles. */
    @Getter private final TileGrid tiles;

    private long[][] positionHashes_previous;
    private long[][] positionHashes_current;

    /** The components on the screen. */
    private final List<Component> components = new ArrayList<>(0);

    /** The lock used to control access to the components. */
    private final ReentrantReadWriteLock componentsLock = new ReentrantReadWriteLock();

    /** The last known tile-based position of the mouse. */
    private final Point mousePosition = new Point(0, 0);

    /** The color palette of the Screen. Does not apply to child components. */
    @Getter private ColorPalette colorPalette;

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
        positionHashes_previous = new long[dimensions.width][dimensions.height];
        positionHashes_current = new long[dimensions.width][dimensions.height];
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
                final int mouseX = e.getX() / font.getWidth();
                final int mouseY = e.getY() / font.getHeight();

                mousePosition.setLocation(mouseX, mouseY);
            }
        });

        // Add mouse click listener, used to determine which Component(s)
        // are focused.
        addListener(new MouseInputListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {}

            @Override
            public void mousePressed(final MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    final int mouseX = e.getX() / font.getWidth();
                    final int mouseY = e.getY() / font.getHeight();
                    final Point mousePosition = new Point(mouseX, mouseY);

                    componentsLock.readLock().lock();

                    for (final Component component : components) {
                        component.setFocused(component.intersects(mousePosition));
                    }

                    componentsLock.readLock().unlock();
                }
            }

            @Override
            public void mouseReleased(final MouseEvent e) {}

            @Override
            public void mouseEntered(final MouseEvent e) {}

            @Override
            public void mouseExited(final MouseEvent e) {}

            @Override
            public void mouseDragged(final MouseEvent e) {}

            @Override
            public void mouseMoved(final MouseEvent e) {}
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

        frame.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(final ComponentEvent e) {
                draw();
            }

            @Override
            public void componentMoved(final ComponentEvent e) {
                draw();
            }

            @Override
            public void componentShown(final ComponentEvent e) {
                draw();
            }

            @Override
            public void componentHidden(final ComponentEvent e) {}
        });

        frame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(final WindowEvent e) {
                draw();
            }

            @Override
            public void windowLostFocus(final WindowEvent e) {}
        });

        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(final WindowEvent e) {
                draw();
            }

            @Override
            public void windowClosing(final WindowEvent e) {}

            @Override
            public void windowClosed(final WindowEvent e) {}

            @Override
            public void windowIconified(final WindowEvent e) {}

            @Override
            public void windowDeiconified(final WindowEvent e) {
                draw();
            }

            @Override
            public void windowActivated(final WindowEvent e) {
                draw();
            }

            @Override
            public void windowDeactivated(final WindowEvent e) {}
        });

        /*
         * There are two rare, hard to reproduce, issues that this sleep fixes.
         *
         * It's assumed that the reason for these issues is that the Swing components aren't fully initialized
         * when the first draw occurs, so we need to give Swing some time before we draw to the Canvas.
         *
         *      1) Nothing is drawn to the Canvas on first draw, it remains completely white/blank.
         *
         *      2) A random number of tiles, at random positions, are either rendered incorrectly or aren't
         *         rendered.
         */
        try {
            Thread.sleep(200);
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

        // Resize the canvas, so that it has enough room to fit the resized font.
        final int pixelWidth = tiles.getWidth() * imageCache.getFont().getWidth();
        final int pixelHeight = tiles.getHeight() * imageCache.getFont().getHeight();

        canvas.setPreferredSize(new Dimension(pixelWidth, pixelHeight));

        // Create the frame.
        final Frame frame = new Frame();
        frame.add(canvas);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setIgnoreRepaint(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(final WindowEvent e) {
                device.setFullScreenWindow(null);
                frame.dispose();
                System.exit(0);
            }
        });
        frame.setBackground(colorPalette.getDefaultBackground());
        frame.setForeground(colorPalette.getDefaultForeground());
        frame.setVisible(true);

        device.setFullScreenWindow(frame);
        isInFullScreenExclusiveMode = true;

        /*
         * There are two rare, hard to reproduce, issues that this sleep fixes.
         *
         * It's assumed that the reason for these issues is that the Swing components aren't fully initialized
         * when the first draw occurs, so we need to give Swing some time before we draw to the Canvas.
         *
         *      1) Nothing is drawn to the Canvas on first draw, it remains completely white/blank.
         *
         *      2) A random number of tiles, at random positions, are either rendered incorrectly or aren't
         *         rendered.
         */
        try {
            Thread.sleep(200);
        } catch (final InterruptedException ignored) {}

        draw();

        return frame;
    }

    /** Draws all of the screen's tiles onto the canvas. */
    public void draw() {
        final int screenHeight = tiles.getHeight();
        final int screenWidth = tiles.getWidth();

        // Copy current hashes to previous hashes:
        for (int x = 0 ; x < screenWidth ; x++) {
            if (screenHeight >= 0) {
                System.arraycopy(positionHashes_current[x], 0, positionHashes_previous[x], 0, screenHeight);
            }
        }

        // Reset current hashes:
        for (int x = 0 ; x < screenWidth ; x++) {
            Arrays.fill(positionHashes_current[x], 0);
        }

        // Update current hashes:
        for (int x = 0 ; x < screenWidth ; x++) {
            for (int y = 0 ; y < screenHeight ; y++) {
                positionHashes_current[x][y] = tiles.getPositionHash(x, y);

                // Add the hashes of all component tiles that overlap the current position.
                componentsLock.readLock().lock();

                for (final Component component : components) {
                    final TileGrid tileGrid = component.getTiles();
                    final int tempX = x - tileGrid.getXPosition();
                    final int tempY = y - tileGrid.getYPosition();

                    positionHashes_current[x][y] += tileGrid.getPositionHash(tempX, tempY);
                }

                componentsLock.readLock().unlock();
            }
        }

        // Draw screen on canvas.
        final BufferStrategy bs = canvas.getBufferStrategy();

        do {
            do {
                final Graphics2D gc;

                try {
                    gc = (Graphics2D) bs.getDrawGraphics();

                    // Whether to bias algorithm choices more for speed or quality when evaluating tradeoffs.
                    gc.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);

                    // Controls how closely to approximate a color when storing into a destination with limited
                    // color resolution.
                    gc.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);

                    // Controls the accuracy of approximation and conversion when storing colors into a
                    // destination image or surface.
                    gc.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);

                    // Controls how image pixels are filtered or resampled during an image rendering operation.
                    gc.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

                    // Font characters are pre-rendered images, so no need for AA.
                    //
                    // Controls whether or not the geometry rendering methods of a Graphics2D object will
                    // attempt to reduce aliasing artifacts along the edges of shapes.
                    gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

                    // Everything is done VIA images, so there's no need for text rendering options.
                    gc.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
                    gc.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);

                    // It's possible that users will be using a large amount of transparent layers, so we want
                    // to ensure that rendering is as quick as we can get it.
                    //
                    // A general hint that provides a high level recommendation as to whether to bias alpha
                    // blending algorithm choices more for speed or quality when evaluating tradeoffs.
                    gc.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);

                    // Draw every position, whose hash has changed, onto the canvas.
                    for (int x = 0; x < tiles.getWidth(); x++) {
                        final int xPosition = tiles.getXPosition() + x;

                        for (int y = 0; y < tiles.getHeight(); y++) {
                            final int yPosition = tiles.getYPosition() + y;

                            final boolean hashChanged = (positionHashes_previous[x][y] != positionHashes_current[x][y]);
                            final boolean notYetRendered = (positionHashes_previous[x][y] == 0);

                            if (notYetRendered || hashChanged) {
                                tiles.getTileAt(x, y).draw(gc, imageCache, xPosition, yPosition);

                                // Draw all of component tiles that overlap the current position.
                                componentsLock.readLock().lock();

                                for (final Component component : components) {
                                    final TileGrid tileGrid = component.getTiles();
                                    final int tempX = x - tileGrid.getXPosition();
                                    final int tempY = y - tileGrid.getYPosition();

                                    tileGrid.drawTile(gc, imageCache, tempX, tempY, x, y);
                                }

                                componentsLock.readLock().unlock();
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

                        // Screen needs to be redrawn if the Buffer Strategy changes, so we have to reset the
                        // hashes to ensure the whole screen is redrawn.
                        for (int x = 0 ; x < screenWidth ; x++) {
                            Arrays.fill(positionHashes_current[x], 0);
                        }

                        draw();
                        return;
                    }
                }
            } while (bs.contentsRestored()); // Repeat render if drawing buffer contents were restored.

            bs.show();
        } while (bs.contentsLost()); // Repeat render if drawing buffer was lost.
    }

    /**
     * Adds a component to the screen.
     *
     * This function should never be called when the parameter is a layer within a layer. It will throw-off
     * the algorithm that adjusts the bounding box offsets for all of the components.
     *
     * @param component
     *          The component.
     */
    public void addComponent(final Component component) {
        if (component == null) {
            return;
        }

        if (component instanceof Layer) {
            setupChildComponentsOfLayer((Layer) component);
            updateChildBoundingBoxesOfLayer((Layer) component);
        }

        // Add the component
        componentsLock.writeLock().lock();
        components.add(component);
        componentsLock.writeLock().unlock();

        // Set the component's redraw function
        component.setRedrawFunction(this::draw);

        // Create and add the component's event listeners
        component.createEventListeners(this);

        for (final EventListener listener : component.getEventListeners()) {
            addListener(listener);
        }
    }

    /**
     * Recursively sets up a layer, all of it's child components, and all of the child components of any layer
     * that is a child to the screen.
     *
     * @param layer
     *          The layer.
     */
    private void setupChildComponentsOfLayer(final Layer layer) {
        layer.setRootScreen(this);

        for (final Component component : layer.getComponents()) {
            // Set the component's redraw function
            component.setRedrawFunction(this::draw);

            // Create and add the component's event listeners
            component.createEventListeners(this);

            for (final EventListener listener : component.getEventListeners()) {
                addListener(listener);
            }

            if (component instanceof Layer) {
                setupChildComponentsOfLayer((Layer) component);
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
            final int x = layer.getTiles().getXPosition() + component.getTiles().getXPosition();
            final int y = layer.getTiles().getYPosition() + component.getTiles().getYPosition();
            component.setBoundingBoxOrigin(x, y);

            if (component instanceof Layer) {
                updateChildBoundingBoxesOfLayer((Layer) component);
            }
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
            ((Layer) component).setRootScreen(null);
        }

        // Remove the component
        componentsLock.writeLock().lock();
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
                tile.setBackgroundColor(colorPalette.getDefaultBackground());
                tile.setForegroundColor(colorPalette.getDefaultForeground());
            }
        }
    }

    /** Removes all components from the screen. */
    public void removeAllComponents() {
        componentsLock.writeLock().lock();

        final ListIterator<Component> iterator = components.listIterator();

        while(iterator.hasNext()) {
            final Component component = iterator.next();

            // Remove the component
            tiles.removeChild(component.getTiles());
            iterator.remove();

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
     * Removes all event listeners, that belong to a layer and it's sub components, from the screen.
     *
     * @param layer
     *          The layer.
     */
    public void removeLayerListeners(final Layer layer) {
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
