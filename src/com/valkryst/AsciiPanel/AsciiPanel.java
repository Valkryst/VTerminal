package com.valkryst.AsciiPanel;

import com.valkryst.AsciiPanel.component.AsciiComponent;
import com.valkryst.AsciiPanel.component.AsciiScreen;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class AsciiPanel extends Canvas {
	private static final long serialVersionUID = -4167851861147593092L; // todo Generate new value

	public final static Color COLOR_BLACK = Color.valueOf("0x000000");
	public final static Color COLOR_RED = Color.valueOf("0x800000");
	public final static Color COLOR_GREEN = Color.valueOf("0x008000");
    public final static Color COLOR_YELLOW = Color.valueOf("0x808000");
    public final static Color COLOR_BLUE = Color.valueOf("0x000080");
    public final static Color COLOR_MAGENTA = Color.valueOf("0x800080");
    public final static Color COLOR_CYAN = Color.valueOf("0x008080");
    public final static Color COLOR_WHITE = Color.valueOf("0xC0C0C0");
    public final static Color COLOR_BRIGHT_BLACK = Color.valueOf("0x808080");
    public final static Color COLOR_BRIGHT_RED = Color.valueOf("0xFF0000");
    public final static Color COLOR_BRIGHT_GREEN = Color.valueOf("0x00FF00");
    public final static Color COLOR_BRIGHT_YELLOW = Color.valueOf("0xFFFF00");
    public final static Color COLOR_BRIGHT_BLUE = Color.valueOf("0x0000FF");
    public final static Color COLOR_BRIGHT_MAGENTA = Color.valueOf("0xFF00FF");
    public final static Color COLOR_BRIGHT_CYAN = Color.valueOf("0x00FFFF");
    public final static Color COLOR_BRIGHT_WHITE = Color.valueOf("0xFFFFFF");

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
