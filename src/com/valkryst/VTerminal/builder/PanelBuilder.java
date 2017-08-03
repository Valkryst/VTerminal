package com.valkryst.VTerminal.builder;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.component.Screen;
import com.valkryst.VTerminal.font.Font;
import lombok.Getter;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class PanelBuilder {
    /** The width of the panel, in characters. */
    @Getter private int widthInCharacters;
    /** The height of the panel, in characters. */
    @Getter private int heightInCharacters;
    /** The font to draw with. */
    @Getter private Font font;

    /** The screen being displayed on the panel. */
    @Getter private Screen screen;

    /** The frame in which the panel is to be placed. */
    @Getter private JFrame frame;

    /** Constructs a new PanelBuilder. */
    public PanelBuilder() {
        reset();
    }

    /**
     * Uses the builder to construct a new VTerminal.
     *
     * If no frame is set, a default frame will be used.
     *
     * @return
     *         The new VTerminal.
     */
    public Panel build() {
        checkState();

        final Panel panel = new Panel(this);
        frame.add(panel);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        panel.setIgnoreRepaint(true);
        panel.createBufferStrategy(2);
        panel.setFocusable(true);
        return panel;
    }

    /**
     * Checks the current state of the builder.
     *
     * @throws NullPointerException
     *          If the font is null.
     */
    private void checkState() throws NullPointerException {
        if (font == null) {
            throw new NullPointerException("The panel must have an AsciiFont to draw with.");
        }

        if (screen == null) {
            screen = new Screen(0, 0, widthInCharacters, heightInCharacters);
        }

        if (frame == null) {
            frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        widthInCharacters = 80;
        heightInCharacters = 24;
        font = null;
        screen = null;
        frame = null;
    }

    /**
     * Sets the frame.
     *
     * @param frame
     *        The new frame.
     *
     * @return
     *        This.
     */
    public PanelBuilder setJFrame(final JFrame frame) {
        this.frame = frame;
        return this;
    }

    /**
     * Sets the width in characters.
     *
     * @param widthInCharacters
     *        The new width in characters.
     *
     * @return
     *        This.
     */
    public PanelBuilder setWidthInCharacters(final int widthInCharacters) {
        if (widthInCharacters < 1) {
            this.widthInCharacters = 1;
        } else {
            this.widthInCharacters = widthInCharacters;
        }

        return this;
    }

    /**
     * Sets the height in characters.
     *
     * @param heightInCharacters
     *        The new height in characters.
     *
     * @return
     *        This.
     */
    public PanelBuilder setHeightInCharacters(final int heightInCharacters) {
        if (heightInCharacters < 1) {
            this.heightInCharacters = 1;
        } else {
            this.heightInCharacters = heightInCharacters;
        }

        return this;
    }

    /**
     * Sets the font.
     *
     * @param asciiFont
     *        The new font.
     *
     * @return
     *        This.
     */
    public PanelBuilder setFont(final Font asciiFont) {
        if (asciiFont != null) {
            this.font = asciiFont;
        }

        return this;
    }

    /**
     * Sets the screen.
     *
     * @param screen
     *        The new screen.
     *
     * @return
     *        This.
     */
    public PanelBuilder setScreen(final Screen screen) {
        this.screen = screen;
        return this;
    }
}
