package com.valkryst.AsciiPanel;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    /** An array of strings representing the character-rows of the terminal. */
    private AsciiString[] strings;
    
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

        strings = new AsciiString[heightInCharacters];

        for (int row = 0 ; row < heightInCharacters ; row++) {
            strings[row] = new AsciiString(widthInCharacters);
        }
    }

    /** Draws every character of every row onto the canvas. */
    public void draw() {
        final GraphicsContext gc = this.getGraphicsContext2D();
        gc.setFont(font.getFont());

        for (int row = 0 ; row < strings.length ; row++) {
            strings[row].draw(gc, font, row);
        }
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

    /**
     * Clears the entire screen.
     *
     * @param character
     *         The character to replace all characters being cleared with.
     *
     * @return
     *         This.
     */
    public AsciiPanel clear(final AsciiCharacter character) {
        return clear(character, 0, 0, widthInCharacters, heightInCharacters);
    }

    /**
     * Clears the specified cell of the screen.
     *
     * Does nothing if the (columnIndex, rowIndex) pair points to invalid position.
     *
     * @param character
     *         The character to replace all characters being cleared with.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate of the cell to clear.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate of the cell to clear.
     *
     * @return
     *         This.
     */
    public AsciiPanel clear(final AsciiCharacter character, final int columnIndex, final int rowIndex) {
        return clear(character, columnIndex, rowIndex, columnIndex, rowIndex);
    }

    /**
     * Clears the specified section of the screen.
     *
     * Does nothing if the (columnIndex, rowIndex) or (width, height) pairs point to invalid positions.
     *
     * @param character
     *         The character to replace all characters being cleared with.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate of the cell to clear.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate of the cell to clear.
     *
     * @param width
     *         The width of the area to clear.
     *
     * @param height
     *         The height of the area to clear.
     *
     * @return
     *         This.
     */
    public AsciiPanel clear(final AsciiCharacter character, int columnIndex, int rowIndex, int width, int height) {
        boolean canProceed = isPositionValid(columnIndex, rowIndex);
        canProceed &= isPositionValid(width, height);

        if (canProceed) {
            for (int column = columnIndex ; column < width ; column++) {
                for (int row = rowIndex ; row < height ; row++) {
                    write(character, column, row);
                }
            }
        }

        return this;
    }

    /**
     * Write the specified character.
     *
     * @param character
     *         The character.
     *
     * @return
     *         This.
     */
    public AsciiPanel write(final AsciiCharacter character) {
        return write(character, asciiCursor.getColumnIndex(), asciiCursor.getRowIndex());
    }

    /**
     * Write the specified character to the specified position.
     *
     * @param character
     *         The character.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate to write to.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate to write to.
     *
     * @return
     *         This.
     */
    public AsciiPanel write(final AsciiCharacter character, final int columnIndex, final int rowIndex) {
        boolean canProceed = isPositionValid(columnIndex, rowIndex);

        if (canProceed) {
            strings[rowIndex].replaceCharacter(columnIndex, character);
        }

        return this;
    }

    /**
     * Write a string to the cursor's position and updates the cursor's position.
     *
     * @param string
     *         The string.
     *
     * @return
     *         This.
     */
    public AsciiPanel write(final AsciiString string) {
        return write(string, asciiCursor.getColumnIndex(), asciiCursor.getRowIndex());
    }

    /**
     * Write a string to the specified position.
     *
     * Does nothing if the (columnIndex, rowIndex) points to invalid position.
     *
     * @param string
     *         The string.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate to begin writing from.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate to begin writing from.
     *
     * @return
     *         This.
     */
    public AsciiPanel write(final AsciiString string, final int columnIndex, final int rowIndex) {
        if (isPositionValid(rowIndex, columnIndex) == false) {
            return this;
        }

        final AsciiCharacter[] characters = string.getCharacters();

        for (int i = 0 ; i < characters.length && i < widthInCharacters; i++) {
            write(characters[i], columnIndex + i, rowIndex);
        }

        return this;
    }

    /**
     * Write a string to the center of the panel beginning at the specified row.
     *
     * Does nothing for any character where the (columnIndex, rowIndex) points to invalid position.
     *
     * @param string
     *         The string.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate to begin writing from.
     *
     * @return
     *         This.
     */
    public AsciiPanel writeCenter(final AsciiString string, final int rowIndex) {
        int columnIndex = (widthInCharacters - string.getCharacters().length) / 2;


        if (isPositionValid(rowIndex, columnIndex)) {
            return write(string, columnIndex, rowIndex);
        } else {
            return this;
        }
    }
}
