package com.valkryst.AsciiPanel;

import javafx.geometry.VPos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import lombok.Getter;
import lombok.Setter;


public class AsciiCharacter {
    /** The character. */
	@Getter @Setter private char character;
	/** Whether or not the foreground should be drawn using the background color. */
	@Getter @Setter private boolean isHidden = false;
    /** The background color. Defaults to black. */
    @Getter private Paint backgroundColor = Color.BLACK;
	/** The foreground color. Defaults to white. */
	@Getter private Paint foregroundColor = Color.WHITE;
	/** The bounding box of the character's area. */
	@Getter private final Rectangle boundingBox = new Rectangle();

	/** Whether or not the blink effect is enabled. */
	private boolean blinkEffectEnabled = false;
	/** The time, in milliseconds, of when the last blink occurred. */
	private long timeOfLastBlink = 0;
	/** The amount of time, in milliseconds, before the blink effect can occur. */
	private short millsBetweenBlinks = 0;

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
	    final StringBuilder stringBuilder = new StringBuilder("Character:\n");
	    stringBuilder.append("\tCharacter: '").append(character).append("\n");
	    stringBuilder.append("\tBackground Color: ").append(backgroundColor).append("\n");
        stringBuilder.append("\tForeground Color: ").append(foregroundColor).append("\n");
        return stringBuilder.toString();
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
        // Handle Blink Effect:
        if (blinkEffectEnabled) {
            final long currentTime = System.currentTimeMillis();
            final long timeSinceLastBlink = currentTime - timeOfLastBlink;

            if (timeSinceLastBlink >= millsBetweenBlinks) {
                timeOfLastBlink = currentTime;

                isHidden = !isHidden;
            }
        }

        // Draw background & character:
	    final int fontWidth = font.getWidth();
	    final int fontHeight = font.getHeight();

	    columnIndex *= fontWidth;
	    rowIndex *= fontHeight;

	    gc.setTextBaseline(VPos.TOP);

	    gc.setFill(backgroundColor);
        gc.fillRect(columnIndex, rowIndex, fontWidth, fontHeight);
        gc.setFill((isHidden ? backgroundColor : foregroundColor));
        gc.fillText(String.valueOf(character), columnIndex, rowIndex, fontWidth);

        boundingBox.setX(columnIndex);
        boundingBox.setY(rowIndex);
        boundingBox.setWidth(fontWidth);
        boundingBox.setHeight(fontHeight);
    }

    /**
     * Enables the blink effect.
     *
     * @param millsBetweenBlinks
     *         The amount of time, in milliseconds, before the blink effect can occur.
     */
    public void enableBlinkEffect(final short millsBetweenBlinks) {
        blinkEffectEnabled = true;
        this.timeOfLastBlink = 0;

        if (millsBetweenBlinks <= 0) {
            this.millsBetweenBlinks = 1000;
        } else {
            this.millsBetweenBlinks = millsBetweenBlinks;
        }
    }

    /** Disables the blink effect. */
    public void disableBlinkEffect() {
        blinkEffectEnabled = false;
        this.timeOfLastBlink = 0;
        this.millsBetweenBlinks = 0;
    }

    /** Swaps the background and foreground colors. */
    public void invertColors() {
        final Paint temp = backgroundColor;
        setBackgroundColor(foregroundColor);
        setForegroundColor(temp);
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