package com.valkryst.AsciiPanel;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;


public class AsciiCharacter {
    /** The character. */
	@Getter @Setter private char character;
    /** The background color. Defaults to black. */
    @Getter private Paint backgroundColor = Color.BLACK;
	/** The foreground color. Defaults to white. */
	@Getter private Paint foregroundColor = Color.WHITE;
	/** The bounding box of the character's area. */
	@Getter private final Rectangle boundingBox = new Rectangle();

    /**
     * Constructs a new AsciiCharacter.
     *
     * @param character
     *         The character.
     */
	public AsciiCharacter(final char character) {
	    this.character = character;
    }

    @Override
    public String toString() {
	    String string = "Character:\n";
        string += "\tCharacter: '" + character + "'\n";
        string += "\tBackground Color: " + backgroundColor + "\n";
        string += "\tForeground Color: " + foregroundColor + "\n";

        return string;
    }

    @Override
    public boolean equals(final Object object) {
	    if (object instanceof AsciiCharacter == false) {
	        return false;
        }

        final AsciiCharacter otherCharacter = (AsciiCharacter) object;

	    if (character != otherCharacter.character) {
	        return false;
        }

        if (backgroundColor.equals(otherCharacter.backgroundColor) == false) {
            return false;
        }

        if (foregroundColor.equals(otherCharacter.foregroundColor) == false) {
	        return false;
        }

        return true;
    }

    /**
     * Draws the character onto the specified context.
     *
     * @param gc
     *         The context on which to draw.
     *
     * @param font
     *         The font to draw with.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate where the character is to be drawn.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate where the character is to be drawn.
     */
    public void draw(final GraphicsContext gc, final AsciiFont font, double columnIndex, double rowIndex) {
	    final double fontWidth = font.getWidth();
	    final double fontHeight = font.getHeight();

	    columnIndex *= fontWidth;
	    rowIndex *= fontHeight;

	    gc.setFill(backgroundColor);
	    gc.fillRect(columnIndex, rowIndex, columnIndex + fontWidth, rowIndex + fontHeight);
	    gc.setFill(foregroundColor);
	    gc.fillText(String.valueOf(character), columnIndex, rowIndex + fontHeight, fontWidth);

        boundingBox.setX(columnIndex);
        boundingBox.setY(rowIndex);
        boundingBox.setWidth(fontWidth);
        boundingBox.setHeight(fontHeight);
    }

    /**
     * Sets the new background color.
     *
     * Does nothing if the specified color is null.
     *
     * @param color
     *         The new background color.
     */
    public void setBackgroundColor(final Paint color) {
	    boolean canProceed = color != null;
	    canProceed &= backgroundColor.equals(color) == false;

	    if (canProceed) {
            this.backgroundColor = color;
        }
    }

    /**
     * Sets the new foreground color.
     *
     * Does nothing if the specified color is null.
     *
     * @param color
     *         The new foreground color.
     */
    public void setForegroundColor(final Paint color) {
	    boolean canProceed = color != null;
	    canProceed &= foregroundColor.equals(color) == false;

	    if (canProceed) {
	        this.foregroundColor = color;
        }
    }
}