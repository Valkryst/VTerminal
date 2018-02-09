package com.valkryst.VTerminal.builder;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.component.ScreenBuilder;
import com.valkryst.VTerminal.component.Screen;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.misc.ImageCache;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Canvas;
import java.awt.Color;
import java.io.IOException;
import java.net.URISyntaxException;

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
     * Constructs a new PanelBuilder.
     *
     * @throws IOException
     *         If an IOException occurs while loading the default font.
     *
     * @throws URISyntaxException
     *         If a URISyntaxException occurs while loading the default font.
     */
    public PanelBuilder() throws IOException, URISyntaxException {
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

        final Color backgroundColor = new Color(45, 45, 45, 255);

        final Panel panel = new Panel(this);
        final Canvas canvas = panel.getCanvas();

        // Setup Frame
        frame.add(panel.getCanvas());
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setBackground(backgroundColor);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Setup Canvas:
        canvas.setIgnoreRepaint(true);
        canvas.createBufferStrategy(2);
        canvas.setFocusable(true);
        canvas.setFocusTraversalKeysEnabled(false);
        canvas.setBackground(new Color(45, 45, 45, 255));

        screen.setParentPanel(panel);
        screen.setFont(imageCache.getFont());

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
            screenBuilder.setColumnIndex(0);
            screenBuilder.setRowIndex(0);
            screenBuilder.setWidth(widthInCharacters);
            screenBuilder.setHeight(heightInCharacters);

            screen = screenBuilder.build();
            screen.setRadio(radio);
        }

        if (frame == null) {
            frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }

        if (imageCache == null) {
            imageCache = new ImageCache(font);
        }
    }

    /**
     * Resets the builder to it's default state.
     *
     * @throws IOException
     *         If an IOException occurs while loading the default font.
     *
     * @throws URISyntaxException
     *         If a URISyntaxException occurs while loading the default font.
     */
    public void reset() throws IOException, URISyntaxException {
        widthInCharacters = 80;
        heightInCharacters = 24;
        font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);
        screen = null;
        frame = null;
    }
}
