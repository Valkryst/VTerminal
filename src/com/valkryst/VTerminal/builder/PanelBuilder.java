package com.valkryst.VTerminal.builder;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.component.Screen;
import com.valkryst.VTerminal.font.Font;
import lombok.Getter;
import lombok.Setter;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class PanelBuilder {
    /** The width of the panel, in characters. */
    @Getter @Setter private int widthInCharacters = 80;
    /** The height of the panel, in characters. */
    @Getter @Setter private int heightInCharacters = 24;

    /** The font to draw with. */
    @Getter @Setter private Font font;

    /** The screen being displayed on the panel. */
    @Getter @Setter private Screen screen;

    /** The frame in which the panel is to be placed. */
    @Getter @Setter private JFrame frame;

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
     * @throws java.lang.IllegalArgumentException
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
