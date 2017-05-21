package com.valkryst.VTerminal;

import com.valkryst.VTerminal.builder.AsciiPanelBuilder;
import com.valkryst.VTerminal.component.AsciiComponent;
import com.valkryst.VTerminal.component.AsciiScreen;
import com.valkryst.VTerminal.font.AsciiFont;
import com.valkryst.radio.Radio;
import com.valkryst.radio.Receiver;
import lombok.Getter;

import java.awt.*;

public class AsciiPanel extends Canvas implements Receiver<String> {
    /** The width of the panel, in characters. */
    @Getter private int widthInCharacters;
    /** The height of the panel, in characters. */
    @Getter private int heightInCharacters;
    /** The asciiFont to draw with. */
    @Getter private AsciiFont asciiFont;

    /** The screen being displayed on the panel. */
    @Getter private AsciiScreen currentScreen;

    @Getter private Radio<String> radio = new Radio<>();

    /**
     * Constructs a new VTerminal.
     *
     * @param builder
     *         The builder to use.
     */
    public AsciiPanel(final AsciiPanelBuilder builder) {
        this.widthInCharacters = builder.getWidthInCharacters();
        this.heightInCharacters = builder.getHeightInCharacters();

        this.asciiFont = builder.getAsciiFont();

        int pixelWidth = widthInCharacters * asciiFont.getWidth();
        pixelWidth -= asciiFont.getWidth() * 2; // todo Screen always appears to be 2 characters too large, this is a temp fix.

        int pixelHeight = heightInCharacters * asciiFont.getHeight();
        pixelHeight -= asciiFont.getHeight() / 2; // todo Screen always appears to be 0.5 characters too large, this is a temp fix.

        this.setSize(pixelWidth, pixelHeight);

        if (builder.getCurrentScreen() == null) {
            currentScreen = new AsciiScreen(0, 0, widthInCharacters, heightInCharacters);
        } else {
            currentScreen = builder.getCurrentScreen();
        }

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
        currentScreen.draw(this, asciiFont);
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
    public AsciiScreen swapScreen(final AsciiScreen newScreen) {
        final AsciiScreen oldScreen = currentScreen;
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
    public void addComponent(final AsciiComponent component) {
        currentScreen.addComponent(component);
    }

    /**
     * Removes a component from the current screen.
     *
     * @param component
     *          The component.
     */
    public void removeComponent(final AsciiComponent component) {
        currentScreen.removeComponent(component);
    }
}
