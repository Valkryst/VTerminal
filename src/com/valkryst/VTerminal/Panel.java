package com.valkryst.VTerminal;

import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.component.Component;
import com.valkryst.VTerminal.component.Screen;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.radio.Radio;
import com.valkryst.radio.Receiver;
import lombok.Getter;

import java.awt.*;

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
        pixelWidth -= asciiFont.getWidth() * 2; // todo Screen always appears to be 2 characters too large, this is a temp fix.

        int pixelHeight = heightInCharacters * asciiFont.getHeight();
        pixelHeight -= asciiFont.getHeight() / 2; // todo Screen always appears to be 0.5 characters too large, this is a temp fix.

        this.setSize(pixelWidth, pixelHeight);

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
