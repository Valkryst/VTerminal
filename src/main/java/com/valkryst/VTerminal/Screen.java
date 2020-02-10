package com.valkryst.VTerminal;

import com.valkryst.VTerminal.builder.*;
import com.valkryst.VTerminal.component.Component;
import com.valkryst.VTerminal.component.Layer;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.misc.ImageCache;
import com.valkryst.VTerminal.palette.java2d.Java2DPalette;
import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.lang3.SystemUtils;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.awt.Frame.ICONIFIED;

public class Screen {
    /** The canvas on which the screen is drawn. */
    @Getter private final Canvas canvas = new Canvas();

    /** The image cache. */
    @Getter private final ImageCache imageCache;

    /** The tiles. */
    @Getter private final TileGrid tiles;

    /** The previous hash values of each tile position. */
    private long[][] positionHashes_previous;
    /** The current hash va;ies pf each tile position. */
    private long[][] positionHashes_current;

    /** The components on the screen. */
    private final List<Component> components = new ArrayList<>(0);

    /** The lock used to control access to the components. */
    private final ReentrantReadWriteLock componentsLock = new ReentrantReadWriteLock();

    /** The lock used to prevent calls to the draw function. */
    private final ReentrantLock drawLock = new ReentrantLock();

    /** The last known tile-based position of the mouse. */
    private final Point mousePosition = new Point(0, 0);

    /** The color palette of the Screen. Does not apply to child components. */
    @Getter private Java2DPalette palette;

    private boolean isInFullScreenExclusiveMode = false;

    private boolean hasFirstRenderCompleted = false;

    /** The monitor on which the frame/canvas are currently displayed. */
    private GraphicsDevice currentMonitor;

    /**
     * Constructs a new 80x40 Screen with the 18pt DejaVu Sans Mono font.
     *
     * @throws IOException
     *          If an IOException occurs while loading the font.
     *
     */
    public Screen() throws IOException {
        this(FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/", 1));
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
        this(width, height, FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/", 1));
    }

    /**
     * Constructs a new 80x40 Screen.
     *
     * @param font
     *          The font.
     */
    public Screen(final @NonNull Font font) throws IOException {
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
    public Screen(final int width, final int height, final @NonNull Font font) throws IOException {
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
    public Screen(final @NonNull Dimension dimensions, final @NonNull Font font) throws IOException {
        tiles = new TileGrid(dimensions, new Point(0, 0));
        positionHashes_previous = new long[dimensions.width][dimensions.height];
        positionHashes_current = new long[dimensions.width][dimensions.height];
        setPalette(new Java2DPalette(), false);

        this.imageCache = new ImageCache(font);

        // Initialize canvas.
        final var pixelWidth = dimensions.width * imageCache.getFont().getWidth();
        final var pixelHeight = dimensions.height * imageCache.getFont().getHeight();

        canvas.setPreferredSize(new Dimension(pixelWidth, pixelHeight));
        canvas.setIgnoreRepaint(true);

        // Add mouse movement listener.
        addListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(final MouseEvent e) {}

            @Override
            public void mouseMoved(final MouseEvent e) {
                final var mouseX = e.getX() / font.getWidth();
                final var mouseY = e.getY() / font.getHeight();

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
        final var frame = new Frame();
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
        frame.setBackground(palette.getDefaultBackground());
        frame.setForeground(palette.getDefaultForeground());
        frame.setVisible(true);

        frame.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(final ComponentEvent e) {
                if (hasFirstRenderCompleted) {
                    draw();
                }
            }

            @Override
            public void componentMoved(final ComponentEvent e) {
                if (hasFirstRenderCompleted) {
                    drawLock.lock();
                    final var newMonitor = frame.getGraphicsConfiguration().getDevice();

                    if (currentMonitor != newMonitor) {
                        currentMonitor = newMonitor;

                        // Screen needs to be redrawn if the monitor changes, so we have to reset the
                        // hashes to ensure the whole screen is redrawn.
                        for (final long[] hashes : positionHashes_current) {
                            Arrays.fill(hashes, 0);
                        }
                    }
                    drawLock.unlock();

                    draw();
                }
            }

            @Override
            public void componentShown(final ComponentEvent e) {
                if (hasFirstRenderCompleted) {
                    draw();
                }
            }

            @Override
            public void componentHidden(final ComponentEvent e) {}
        });

        frame.addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(final WindowEvent e) {
                if (hasFirstRenderCompleted) {
                    draw();
                }
            }

            @Override
            public void windowLostFocus(final WindowEvent e) {}
        });

        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(final WindowEvent e) {
                if (hasFirstRenderCompleted) {
                    draw();
                }
            }

            @Override
            public void windowClosing(final WindowEvent e) {}

            @Override
            public void windowClosed(final WindowEvent e) {}

            @Override
            public void windowIconified(final WindowEvent e) {}

            @Override
            public void windowDeiconified(final WindowEvent e) {
                if (hasFirstRenderCompleted) {
                    draw();
                }
            }

            @Override
            public void windowActivated(final WindowEvent e) {
                if (hasFirstRenderCompleted) {
                    draw();
                }
            }

            @Override
            public void windowDeactivated(final WindowEvent e) {}
        });

        currentMonitor = frame.getGraphicsConfiguration().getDevice();

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
     */
    public Frame addCanvasToFullScreenFrame(final GraphicsDevice device) {
        if (device == null) {
            System.err.println("No device was specified when calling 'addCanvasToFullScreenFrame'.");
            return addCanvasToFrame();
        }

        if (!device.isFullScreenSupported()) {
            System.err.println("Full screen is not supported for the device '" + device.getIDstring() + "'.");
            return addCanvasToFrame();
        }

        currentMonitor = device;

        // Resize the font, so that it fills the screen properly.
        final var scaleX = device.getDisplayMode().getWidth() / canvas.getPreferredSize().getWidth();
        final var scaleY = device.getDisplayMode().getHeight() / canvas.getPreferredSize().getHeight();
        imageCache.getFont().resize(scaleX, scaleY);

        // Resize the canvas, so that it has enough room to fit the resized font.
        final var pixelWidth = tiles.getWidth() * imageCache.getFont().getWidth();
        final var pixelHeight = tiles.getHeight() * imageCache.getFont().getHeight();

        canvas.setPreferredSize(new Dimension(pixelWidth, pixelHeight));

        // Create the frame.
        final var frame = new Frame();
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
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(final KeyEvent keyEvent) {}

            @Override
            public void keyPressed(final KeyEvent keyEvent) {
                final var keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, java.awt.event.InputEvent.ALT_DOWN_MASK);
                if(keyStroke != null && keyEvent.getKeyCode() == KeyEvent.VK_F4){
                    device.setFullScreenWindow(null);
                    frame.dispose();
                    System.exit(0);
                }
            }

            @Override
            public void keyReleased(final KeyEvent keyEvent) {}
        });
        frame.setBackground(palette.getDefaultBackground());
        frame.setForeground(palette.getDefaultForeground());
        frame.setVisible(true);

        device.setFullScreenWindow(frame);
        isInFullScreenExclusiveMode = true;

        draw();

        return frame;
    }

    /** Draws all of the screen's tiles onto the canvas. */
    public void draw() {
        // Don't bother drawing if there's nothing displayed on the screen.
        if (canvas.getParent() instanceof Frame) {
            final var parent = (Frame) canvas.getParent();

            if (parent == null || parent.getState() == ICONIFIED) {
                return;
            }
        }

        drawLock.lock();

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
        final var bs = canvas.getBufferStrategy();

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
                        final var xPosition = tiles.getXPosition() + x;

                        for (int y = 0; y < tiles.getHeight(); y++) {
                            final var yPosition = tiles.getYPosition() + y;

                            final var hashChanged = (positionHashes_previous[x][y] != positionHashes_current[x][y]);
                            final var notYetRendered = (positionHashes_previous[x][y] == 0);

                            if (!hasFirstRenderCompleted || notYetRendered || hashChanged) {
                                final var tile = getTileAt(x, y);

                                if (tile == null) {
                                    continue;
                                }

                                tile.draw(gc, imageCache, xPosition, yPosition);

                                // Draw all of component tiles that overlap the current position.
                                componentsLock.readLock().lock();

                                for (final Component component : components) {
                                    final var tileGrid = component.getTiles();
                                    final var tempX = x - tileGrid.getXPosition();
                                    final var tempY = y - tileGrid.getYPosition();

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
                            try {
                                canvas.createBufferStrategy(1);
                            } catch (final IllegalStateException ex) {
                                drawLock.unlock();
                                return;
                            }
                        } else {
                            try {
                                canvas.createBufferStrategy(2);
                            } catch (final IllegalStateException ex) {
                                drawLock.unlock();
                                return;
                            }
                        }

                        // Screen needs to be redrawn if the Buffer Strategy changes, so we have to reset the
                        // hashes to ensure the whole screen is redrawn.
                        for (final long[] hashes : positionHashes_current) {
                            Arrays.fill(hashes, 0);
                        }

                        drawLock.unlock();
                        draw();
                        return;
                    }
                }
            } while (bs.contentsRestored()); // Repeat render if drawing buffer contents were restored.

            try {
                bs.show();
            } catch (final IllegalStateException ignored) {
                // Occurs when the program is closed while the screen is rendering.
            }
        } while (bs.contentsLost()); // Repeat render if drawing buffer was lost.

        hasFirstRenderCompleted = true;

        drawLock.unlock();
    }

    /**
     * Loads a component from a JSON file.
     *
     * @param filePath
     *          The file path.
     *
     * @return
     *          The component.
     */
    public Component loadComponent(final String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            return null;
        }

        return loadComponent(new JSONObject(filePath));
    }

    /**
     * Loads a component from a JSON object.
     *
     * @param json
     *          The JSON object.
     *
     * @return
     *          The component.
     */
    public Component loadComponent(final JSONObject json) throws IOException {
        final var type = json.getString("Type");

        if (type == null || type.isEmpty()) {
            return null;
        }

        switch (type) {
            case "Button": {
                return new ButtonBuilder(json).build();
            }
            case "Check Box": {
                return new CheckBoxBuilder(json).build();
            }
            case "Label": {
                return new LabelBuilder(json).build();
            }
            case "Layer": {
                final var x = json.getInt("X");
                final var y = json.getInt("Y");
                final var width = json.getInt("Width");
                final var height = json.getInt("Height");
                final var colorPalette = json.getString("Color Palette");
                final var components = json.getJSONArray("Components");

                final var point = new Point(x, y);
                final var dimension = new Dimension(width, height);

                final var layer = new Layer(dimension, point, new Java2DPalette(colorPalette));

                if (components != null) {
                    for (final var componentJson : components) {
                        final var newComponent = loadComponent((JSONObject) componentJson);

                        for (final var existingComponent : layer.getComponents()) {
                            if (newComponent.getId().equals(existingComponent.getId())) {
                                newComponent.setId(UUID.randomUUID().toString());
                                // todo Display debug info, saying that there was anaming conflict.
                            }
                        }

                        layer.addComponent(loadComponent((JSONObject) componentJson));
                    }
                }

                return layer;
            }
            case "Progress Bar": {
                return new ProgressBarBuilder(json).build();
            }
            case "Radio Button": {
                return new RadioButtonBuilder(json).build();
            }
            case "Text Area": {
                return new TextAreaBuilder(json).build();
            }
            default: {
                return null;
            }
        }
    }

    /**
     * Adds one or more components to the screen.
     *
     * This function should never be called when the parameter is a layer within a layer. It will throw-off
     * the algorithm that adjusts the bounding box offsets for all of the components.
     *
     * @param components
     *          The components.
     */
    public void addComponent(final Component ... components) {
        if (components == null) {
            return;
        }

        drawLock.lock();

        for (final var component : components) {
            if (component == null) {
                drawLock.unlock();
                return;
            }

            if (component instanceof Layer) {
                final Queue<Component> subComponents = new ConcurrentLinkedQueue<>();
                subComponents.add(component);

                while (subComponents.size() > 0) {
                    final var temp = subComponents.remove();

                    // Set the component's redraw function
                    temp.setRedrawFunction(this::draw);

                    // Create and add component event listeners to Canvas
                    temp.createEventListeners(this);
                    temp.getEventListeners().forEach(this::addListener);

                    // If the component's a layer, then we need to deal with it's sub-components.
                    if (temp instanceof Layer) {
                        ((Layer) temp).setRootScreen(this);
                        subComponents.addAll(((Layer) temp).getComponents());

                        updateChildBoundingBoxesOfLayer((Layer) temp);
                    }
                }
            }

            // Add the component
            componentsLock.writeLock().lock();
            this.components.add(component);
            componentsLock.writeLock().unlock();

            // Set the component's redraw function
            component.setRedrawFunction(this::draw);

            // Create and add the component's event listeners
            component.createEventListeners(this);

            for (final EventListener listener : component.getEventListeners()) {
                addListener(listener);
            }
        }

        drawLock.unlock();
    }

    /**
     * Removes one or more components from the screen.
     *
     * @param components
     *          The components.
     */
    public void removeComponent(final Component ... components) {
        if (components == null) {
            return;
        }

        drawLock.lock();

        for (final var component : components) {
            if (component == null) {
                drawLock.unlock();
                return;
            }

            // Remove the component
            componentsLock.writeLock().lock();
            this.components.remove(component);
            componentsLock.writeLock().unlock();

            // Unset the component's redraw function
            component.setRedrawFunction(() -> {});

            // Remove the event listeners of the component and all of it's sub-components.
            final Queue<Component> subComponents = new ConcurrentLinkedQueue<>();
            subComponents.add(component);

            while (subComponents.size() > 0) {
                final Component temp = subComponents.remove();

                if (temp instanceof Layer) {
                    subComponents.addAll(((Layer) temp).getComponents());
                }

                for (final EventListener listener : temp.getEventListeners()) {
                    removeListener(listener);
                }
            }

            // Reset all of the tiles where the component used to be.
            final int startX = component.getTiles().getXPosition();
            final int startY = component.getTiles().getYPosition();

            final int endX = startX + component.getTiles().getWidth();
            final int endY = startY + component.getTiles().getHeight();

            for (int y = startY; y < endY; y++) {
                for (int x = startX; x < endX; x++) {
                    final Tile tile = tiles.getTileAt(x, y);

                    if (tile != null) {
                        tile.reset();
                        tile.setBackgroundColor(palette.getDefaultBackground());
                        tile.setForegroundColor(palette.getDefaultForeground());
                    }
                }
            }
        }

        drawLock.unlock();
    }

    /** Removes all components from the screen. */
    public void removeAllComponents() {
        final Queue<Component> subComponents = new ConcurrentLinkedQueue<>(components);

        while (subComponents.size() > 0) {
            removeComponent(subComponents.remove());
        }
    }

    /**
     * Adds one or more event listeners to the canvas.
     *
     * @param eventListeners
     *          The event listeners.
     *
     * @throws IllegalArgumentException
     *          If an event listener isn't supported by this function.
     */
    public void addListener(final EventListener ... eventListeners) {
        if (eventListeners == null) {
            return;
        }

        for (final EventListener listener : eventListeners) {
            if (listener == null) {
                continue;
            }

            // Ensure the listener hasn't already been added to the canvas:
            if (Arrays.asList(canvas.getKeyListeners()).contains(listener)) {
                continue;
            }

            if (Arrays.asList(canvas.getMouseListeners()).contains(listener)) {
                continue;
            }

            if (Arrays.asList(canvas.getMouseMotionListeners()).contains(listener)) {
                continue;
            }

            // Add the listener to the canvas:
            if (listener instanceof KeyListener) {
                canvas.addKeyListener((KeyListener) listener);
                continue;
            }

            if (listener instanceof MouseListener) {
                canvas.addMouseListener((MouseListener) listener);
                continue;
            }

            if (listener instanceof MouseMotionListener) {
                canvas.addMouseMotionListener((MouseMotionListener) listener);
                continue;
            }

            throw new IllegalArgumentException("The " + listener.getClass().getSimpleName() + " is not supported.");
        }
    }

    /**
     * Removes one or more event listeners from the canvas.
     *
     * @param eventListeners
     *          The event listeners.
     *
     * @throws IllegalArgumentException
     *          If an event listener isn't supported by this function.
     */
    public void removeListener(final EventListener ... eventListeners) {
        if (eventListeners == null) {
            return;
        }

        for (final EventListener listener : eventListeners) {
            if (listener == null) {
                continue;
            }

            if (listener instanceof KeyListener) {
                canvas.removeKeyListener((KeyListener) listener);
                continue;
            }

            if (listener instanceof MouseListener) {
                canvas.removeMouseListener((MouseListener) listener);
                continue;
            }

            if (listener instanceof MouseMotionListener) {
                canvas.removeMouseMotionListener((MouseMotionListener) listener);
                continue;
            }

            throw new IllegalArgumentException("The " + listener.getClass().getSimpleName() + " is not supported.");
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
        if (layer == null) {
            return;
        }

        for (final Component component : layer.getComponents()) {
            final var x = layer.getTiles().getXPosition() + component.getTiles().getXPosition();
            final var y = layer.getTiles().getYPosition() + component.getTiles().getYPosition();
            component.setBoundingBoxOrigin(x, y);

            if (component instanceof Layer) {
                updateChildBoundingBoxesOfLayer((Layer) component);
            }
        }
    }

    /**
     * Retrieves the component which use a specific ID.
     *
     * @param id
     *          The ID to search for.
     *
     * @return
     *          All components using the ID.
     */
    public Component getComponentById(final String id) {
        if (id == null || id.isEmpty() || components.size() == 0) {
            return null;
        }

        for (final var component : components) {
            if (component.getId().equals(id)) {
                return component;
            }
        }

        return null;
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
     * Changes the color palette of the screen and all of it's components.
     *
     * @param palette
     *          The color palette.
     *
     * @param redraw
     *          Whether to call the redraw function after changing the color palette.
     */
    public void setPalette(final Java2DPalette palette, final boolean redraw) {
        if (palette == null) {
            return;
        }

        drawLock.lock();

        this.palette = palette;

        // Change the color of the screen's tiles.
        for (var y = 0 ; y < tiles.getHeight() ; y++) {
            for (var x = 0 ; x < tiles.getWidth() ; x++) {
                final var tile = tiles.getTileAt(x, y);

                if (tile != null) {
                    tile.setForegroundColor(palette.getDefaultBackground());
                    tile.setBackgroundColor(palette.getDefaultForeground());
                }
            }
        }

        // Change child component color palettes.
        componentsLock.readLock().lock();

        for (final var component : components) {
            component.setPalette(palette, false);
        }

        componentsLock.readLock().unlock();

        // Redraw if necessary
        if (redraw) {
            drawLock.unlock();
            draw();
        } else {
            drawLock.unlock();
        }
    }
}
