package com.valkryst.AsciiPanel;

import com.valkryst.AsciiPanel.component.AsciiButton;
import com.valkryst.AsciiPanel.component.AsciiComponent;
import com.valkryst.AsciiPanel.component.AsciiScreen;
import com.valkryst.AsciiPanel.component.AsciiTextField;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class AsciiPanel extends Canvas {
    /** The width of the panel, in characters. */
    @Getter private int widthInCharacters;
    /** The height of the panel, in characters. */
    @Getter private int heightInCharacters;
    /** The font to draw with. */
    @Getter private AsciiFont font;

    /** The cursor. */
    @Getter private final AsciiCursor asciiCursor = new AsciiCursor(this);

    @Getter private AsciiScreen currentScreen;

    private ArrayList<AsciiComponent> components = new ArrayList<>();

    /**
     * Constructs a new AsciiPanel.
     *
     * @param widthInCharacters
     *         The width of the panel, in characters.
     *
     * @param heightInCharacters
     *         The height of the panel, in characters.
     *
     * @param font
     *         The font to use.
     */
    public AsciiPanel(int widthInCharacters, int heightInCharacters, final AsciiFont font) throws NullPointerException {
        if (font == null) {
            throw new NullPointerException("You must specify a font to use.");
        }

        if (widthInCharacters < 1) {
            widthInCharacters = 1;
        }

        if (heightInCharacters < 1) {
            heightInCharacters = 1;
        }

        this.font = font;

        this.widthInCharacters = widthInCharacters;
        this.heightInCharacters = heightInCharacters;

        this.setWidth(widthInCharacters * font.getWidth());
        this.setHeight(heightInCharacters * font.getHeight());

        currentScreen = new AsciiScreen(0, 0, widthInCharacters, heightInCharacters);

        final AsciiButton button = new AsciiButton(35, 5, "Click Me", () -> System.out.println("I've been clicked"));
        button.registerEventHandlers(this);
        components.add(button);

        final AsciiTextField textField = new AsciiTextField(35, 6, 10);
        textField.registerEventHandlers(this);
        components.add(textField);
    }

    /** Draws every character of every row onto the canvas. */
    public void draw() {
        final GraphicsContext gc = this.getGraphicsContext2D();
        gc.setFont(font.getFont());


        // Draw all non-AsciiScreen components:
        components.stream()
                  .filter(component -> component instanceof AsciiScreen == false)
                  .forEach(component -> component.draw(currentScreen));

        // Draw current screen:
        currentScreen.draw(this, font);
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
    private boolean isPositionValid(final int columnIndex, final int rowIndex) {
        if (rowIndex < 0 || rowIndex >= heightInCharacters) {
            final Logger logger = LogManager.getLogger();
            logger.error("The specified column of " + columnIndex + " exceeds the maximum width of " + widthInCharacters + ".");
            return false;
        }

        if (columnIndex < 0 || columnIndex >= widthInCharacters) {
            final Logger logger = LogManager.getLogger();
            logger.error("The specified row of " + rowIndex + " exceeds the maximum width of " + widthInCharacters + ".");
            return false;
        }

        return true;
    }
}
