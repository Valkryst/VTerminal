package com.valkryst.VTerminal.builder;

import com.valkryst.VTerminal.AsciiPanel;
import com.valkryst.VTerminal.component.AsciiScreen;
import com.valkryst.VTerminal.font.AsciiFont;
import lombok.Getter;

import javax.swing.*;

public class AsciiPanelBuilder {
    /** The width of the panel, in characters. */
    @Getter private int widthInCharacters = 80;
    /** The height of the panel, in characters. */
    @Getter private int heightInCharacters = 50;
    /** The asciiFont to draw with. */
    @Getter private AsciiFont asciiFont;

    /** The screen being displayed on the panel. */
    @Getter private AsciiScreen currentScreen;

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
    public AsciiPanel build() throws IllegalStateException {
        checkState();

        final AsciiPanel panel = new AsciiPanel(this);

        if (frame == null) {
            frame = new JFrame();
            frame.add(panel);
            frame.pack();
            frame.setResizable(false);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }

        panel.createBufferStrategy(2); // Reduces draw time by ~200%.
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
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        widthInCharacters = 80;
        heightInCharacters = 50;
        asciiFont = null;
        currentScreen = null;
        frame = null;
    }

    public AsciiPanelBuilder setJFrame(final JFrame frame) {
        this.frame = frame;
        return this;
    }

    public AsciiPanelBuilder setWidthInCharacters(final int widthInCharacters) {
        if (widthInCharacters < 1) {
            this.widthInCharacters = 1;
        } else {
            this.widthInCharacters = widthInCharacters;
        }

        return this;
    }

    public AsciiPanelBuilder setHeightInCharacters(final int heightInCharacters) {
        if (heightInCharacters < 1) {
            this.heightInCharacters = 1;
        } else {
            this.heightInCharacters = heightInCharacters;
        }

        return this;
    }

    public AsciiPanelBuilder setAsciiFont(final AsciiFont asciiFont) {
        if (asciiFont != null) {
            this.asciiFont = asciiFont;
        }

        return this;
    }

    public AsciiPanelBuilder setCurrentScreen(final AsciiScreen currentScreen) {
        this.currentScreen = currentScreen;
        return this;
    }
}
