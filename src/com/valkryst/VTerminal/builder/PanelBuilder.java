package com.valkryst.VTerminal.builder;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.component.Screen;
import com.valkryst.VTerminal.font.Font;
import lombok.Getter;

import javax.swing.*;

public class PanelBuilder {
    /** The width of the panel, in characters. */
    @Getter private int widthInCharacters = 80;
    /** The height of the panel, in characters. */
    @Getter private int heightInCharacters = 24;
    /** The asciiFont to draw with. */
    @Getter private Font asciiFont;

    /** The screen being displayed on the panel. */
    @Getter private Screen currentScreen;

    /** The frame in which the panel is to be placed. */
    @Getter private JFrame frame;

    /**
     * Uses the builder to construct a new VTerminal.
     *
     * If no frame is set, a default frame will be used.
     *
     * @return
     *         The new VTerminal.
     *
     * @throws IllegalStateException
     *          If something is wrong with the builder's state.
     */
    public Panel build() throws IllegalStateException {
        checkState();

        final Panel panel = new Panel(this);

        if (frame == null) {
            frame = new JFrame();
            frame.add(panel);
            frame.pack();
            frame.setResizable(false);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }

        panel.createBufferStrategy(2);
        panel.setIgnoreRepaint(true);
        panel.setFocusable(true);
        return panel;
    }

    /**
     * Checks the current state of the builder.
     *
     * @throws IllegalStateException
     *          If something is wrong with the builder's state.
     */
    private void checkState() throws IllegalStateException {
        if (asciiFont == null) {
            throw new NullPointerException("The panel must have an AsciiFont to draw with.");
        }

        if (currentScreen == null) {
            currentScreen = new Screen(0, 0, widthInCharacters, heightInCharacters);
        }
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        widthInCharacters = 80;
        heightInCharacters = 24;
        asciiFont = null;
        currentScreen = null;
        frame = null;
    }

    public PanelBuilder setJFrame(final JFrame frame) {
        this.frame = frame;
        return this;
    }

    public PanelBuilder setWidthInCharacters(final int widthInCharacters) {
        if (widthInCharacters < 1) {
            this.widthInCharacters = 1;
        } else {
            this.widthInCharacters = widthInCharacters;
        }

        return this;
    }

    public PanelBuilder setHeightInCharacters(final int heightInCharacters) {
        if (heightInCharacters < 1) {
            this.heightInCharacters = 1;
        } else {
            this.heightInCharacters = heightInCharacters;
        }

        return this;
    }

    public PanelBuilder setAsciiFont(final Font asciiFont) {
        if (asciiFont != null) {
            this.asciiFont = asciiFont;
        }

        return this;
    }

    public PanelBuilder setCurrentScreen(final Screen currentScreen) {
        this.currentScreen = currentScreen;
        return this;
    }
}
