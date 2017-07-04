package com.valkryst.VTerminal;

import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.component.Component;
import com.valkryst.VTerminal.component.Screen;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VRadio.Radio;
import com.valkryst.VRadio.Receiver;
import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Panel extends Canvas implements Receiver<String> {
    /** The width of the panel, in characters. */
    @Getter private int widthInCharacters;
    /** The height of the panel, in characters. */
    @Getter private int heightInCharacters;
    /** The asciiFont to draw with. */
    @Getter private Font asciiFont;

    /** The screen being displayed on the panel. */
    @Getter private Screen currentScreen;

    @Getter private Radio<String> radio = new Radio<>();

    /**
     * Constructs a new VTerminal.
     *
     * @param builder
     *         The builder to use.
     */
    public Panel(final PanelBuilder builder) {
        this.widthInCharacters = builder.getWidthInCharacters();
        this.heightInCharacters = builder.getHeightInCharacters();

        this.asciiFont = builder.getAsciiFont();

        int pixelWidth = widthInCharacters * asciiFont.getWidth();
        int pixelHeight = heightInCharacters * asciiFont.getHeight();

        this.setPreferredSize(new Dimension(pixelWidth, pixelHeight));

        currentScreen = builder.getCurrentScreen();

        radio.addReceiver("DRAW", this);
    }

    @Override
    public void receive(final String event, final String data) {
        if (event.equals("DRAW")) {
            draw();
        }
    }

    /** Draws every character of every row onto the canvas. */
    public void draw() {
        final BufferStrategy bufferStrategy = this.getBufferStrategy();
        final Graphics2D gc = (Graphics2D) bufferStrategy.getDrawGraphics();

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
        gc.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);

        currentScreen.draw(gc, asciiFont);

        bufferStrategy.show();
        gc.dispose();
    }

    /**
     * Draws the canvas onto an image.
     *
     * This calls the current screen's draw function, so the
     * screen may look a little different if there are blink
     * effects or new updates to characters that haven't yet
     * been drawn.
     *
     * This is an expensive operation as it essentially creates
     * an in-memory screen and draws each AsciiCharacter onto
     * that screen.
     *
     * @return
     *        An image of the canvas.
     */
    public BufferedImage screenshot() {
        return currentScreen.screenshot(asciiFont);
    }

    /**
     * Swaps-out the current screen for the new screen.
     *
     * @param newScreen
     *         The new screen to swap-in.
     *
     * @return
     *         The swapped-out screen.
     */
    public Screen swapScreen(final Screen newScreen) {
        final Screen oldScreen = currentScreen;
        currentScreen = newScreen;
        draw();
        return oldScreen;
    }

    /**
     * Adds a component to the current screen.
     *
     * @param component
     *          The component.
     */
    public void addComponent(final Component component) {
        currentScreen.addComponent(component);
    }

    /**
     * Removes a component from the current screen.
     *
     * @param component
     *          The component.
     */
    public void removeComponent(final Component component) {
        currentScreen.removeComponent(component);
    }
}
