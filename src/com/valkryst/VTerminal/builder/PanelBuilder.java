package com.valkryst.VTerminal.builder;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.component.ScreenBuilder;
import com.valkryst.VTerminal.component.Screen;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.misc.ImageCache;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Color;

@Data
@EqualsAndHashCode
public class PanelBuilder {
    /** The width of the panel, in characters. */
    private int widthInCharacters;
    /** The height of the panel, in characters. */
    private int heightInCharacters;
    /** The font to draw with. */
    private Font font;

    /** The radio being listened to. */
    @Getter private Radio<String> radio = new Radio<>();

    /** The screen being displayed on the panel. */
    private Screen screen;

    /** The frame in which the panel is to be placed. */
    private JFrame frame;

    /** The image cache to retrieve character images from. */
    private ImageCache imageCache;

    /**
     * Whether or not to allow the Panel to redraw itself based on received radio
     * transmissions.
     */
    private boolean dynamicallyRedrawn;

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

        final Color backgroundColor = new Color(255, 0, 255, 255);

        final Panel panel = new Panel(this);
        frame.add(panel);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setBackground(backgroundColor);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel.setIgnoreRepaint(true);
        panel.createBufferStrategy(2);
        panel.setFocusable(true);
        panel.setFocusTraversalKeysEnabled(false);
        panel.setBackground(backgroundColor);
        return panel;
    }

    /**
     * Checks the current state of the builder.
     *
     * @throws IllegalArgumentException
     *          If the width or height is less than one.
     *
     * @throws NullPointerException
     *          If the font is null.
     */
    private void checkState() throws NullPointerException {
        if (widthInCharacters < 1) {
            throw new IllegalArgumentException("The width, in characters, cannot be less than one.");
        }

        if (heightInCharacters < 1) {
            throw new IllegalArgumentException("The height, in characters, cannot be less than one.");
        }

        if (font == null) {
            throw new NullPointerException("The panel must have an AsciiFont to draw with.");
        }

        if (screen == null) {
            final ScreenBuilder screenBuilder = new ScreenBuilder();
            screenBuilder.setRadio(radio);
            screenBuilder.setColumnIndex(0);
            screenBuilder.setRowIndex(0);
            screenBuilder.setWidth(widthInCharacters);
            screenBuilder.setHeight(heightInCharacters);

            screen = screenBuilder.build();
        }

        if (frame == null) {
            frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }

        if (imageCache == null) {
            imageCache = new ImageCache(font);
        }
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        widthInCharacters = 80;
        heightInCharacters = 24;
        font = null;
        screen = null;
        frame = null;
        dynamicallyRedrawn = true;
    }
}
