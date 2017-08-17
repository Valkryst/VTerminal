package com.valkryst.VTerminal.builder;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.component.Screen;
import com.valkryst.VTerminal.font.Font;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Color;

@EqualsAndHashCode
@ToString
public class PanelBuilder {
    /** The width of the panel, in characters. */
    @Getter @Setter private int widthInCharacters;
    /** The height of the panel, in characters. */
    @Getter @Setter private int heightInCharacters;
    /** The font to draw with. */
    @Getter @Setter private Font font;

    /** The screen being displayed on the panel. */
    @Getter @Setter private Screen screen;

    /** The frame in which the panel is to be placed. */
    @Getter @Setter private JFrame frame;

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
}
