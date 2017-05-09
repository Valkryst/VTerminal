package com.valkryst.AsciiPanel;

import com.valkryst.AsciiPanel.component.AsciiScreen;
import com.valkryst.AsciiPanel.font.AsciiFont;
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

    /** The cursor. */
    @Getter private final AsciiCursor asciiCursor = new AsciiCursor(this);

    /** The screen being displayed on the panel. */
    @Getter private AsciiScreen currentScreen;

    @Getter private Radio<String> radio = new Radio<>();

    /**
     * Constructs a new AsciiPanel.
     *
     * @param widthInCharacters
     *         The width of the panel, in characters.
     *
     * @param heightInCharacters
     *         The height of the panel, in characters.
     *
     * @param asciiFont
     *         The asciiFont to use.
     */
    public AsciiPanel(int widthInCharacters, int heightInCharacters, final AsciiFont asciiFont) throws NullPointerException {
        if (asciiFont == null) {
            throw new NullPointerException("You must specify an asciiFont to use.");
        }

        if (widthInCharacters < 1) {
            widthInCharacters = 1;
        }

        if (heightInCharacters < 1) {
            heightInCharacters = 1;
        }

        this.asciiFont = asciiFont;

        this.widthInCharacters = widthInCharacters;
        this.heightInCharacters = heightInCharacters;

        this.setSize(widthInCharacters * asciiFont.getWidth(), heightInCharacters * asciiFont.getHeight());

        currentScreen = new AsciiScreen(0, 0, widthInCharacters, heightInCharacters);

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
     * Determines whether or not the specified position is within the bounds of the panel.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate.
     *
     * @return
     *         Whether or not the specified position is within the bounds of the panel.
     */
    public boolean isPositionValid(final int columnIndex, final int rowIndex) {
        boolean isWithinBounds = rowIndex >= 0;
        isWithinBounds &= rowIndex < heightInCharacters;
        isWithinBounds &= columnIndex >= 0;
        isWithinBounds &= columnIndex < widthInCharacters;

        return isWithinBounds;
    }
}
