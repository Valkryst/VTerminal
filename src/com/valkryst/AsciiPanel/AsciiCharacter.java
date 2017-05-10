package com.valkryst.AsciiPanel;

import com.valkryst.AsciiPanel.font.AsciiFont;
import com.valkryst.radio.Radio;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.RGBImageFilter;


public class AsciiCharacter {
    /** The character. */
	@Getter @Setter private char character;
	/** Whether or not the foreground should be drawn using the background color. */
	@Getter @Setter private boolean isHidden = false;
    /** The background color. Defaults to black. */
    @Getter private Color backgroundColor = Color.BLACK;
	/** The foreground color. Defaults to white. */
	@Getter private Color foregroundColor = Color.WHITE;
	/** The bounding box of the character's area. */
	@Getter private final Rectangle boundingBox = new Rectangle();

	private Timer blinkTimer;
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
        return "Character:\n" +
                "\tCharacter: '" + character + "\n" +
                "\tBackground Color: " + backgroundColor + "\n" +
                "\tForeground Color: " + foregroundColor + "\n";
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
    public void draw(final Graphics gc, final AsciiFont font, int columnIndex, int rowIndex) {
        // Handle Blink Effect:
        if (blinkEffectEnabled) {
            final long currentTime = System.currentTimeMillis();
            final long timeSinceLastBlink = currentTime - timeOfLastBlink;

            if (timeSinceLastBlink >= millsBetweenBlinks) {
                timeOfLastBlink = currentTime;

                isHidden = !isHidden;
            }
        }

        // Retrieve character image & set colors:
        Image image = font.getCharacterImages().get(character);

        final ImageFilter filter = new RGBImageFilter() {
            @Override
            public int filterRGB(int x, int y, int rgb) {
                if (rgb == 0xFFFFFFFF) {
                    return foregroundColor.getRGB();
                } else {
                    return backgroundColor.getRGB();
                }
            }
        };

        image = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), filter));

        // Draw character:
	    final int fontWidth = font.getWidth();
	    final int fontHeight = font.getHeight();

	    columnIndex *= fontWidth;
	    rowIndex *= fontHeight;

        gc.drawImage(image, columnIndex, rowIndex,null);

        boundingBox.setLocation(columnIndex, rowIndex);
        boundingBox.setSize(fontWidth, fontHeight);
    }

    /**
     * Enables the blink effect.
     *
     * @param millsBetweenBlinks
     *         The amount of time, in milliseconds, before the blink effect can occur.
     *
     * @param radio
     *         The Radio to transmit a DRAW event to whenever a blink occurs.
     */
    public void enableBlinkEffect(final short millsBetweenBlinks, final Radio<String> radio) {
        blinkEffectEnabled = true;
        this.timeOfLastBlink = 0;

        if (millsBetweenBlinks <= 0) {
            this.millsBetweenBlinks = 1000;
        } else {
            this.millsBetweenBlinks = millsBetweenBlinks;
        }

        blinkTimer = new Timer(this.millsBetweenBlinks, e -> {
            invertColors();
            radio.transmit("DRAW");
        });
        blinkTimer.setInitialDelay(this.millsBetweenBlinks);
        blinkTimer.setRepeats(true);
        blinkTimer.start();
    }

    /** Resumes the blink effect. */
    public void resumeBlinkEffect() {
        if (blinkTimer != null) {
            if (blinkTimer.isRunning() == false) {
                blinkTimer.start();
            }
        }
    }

    /** Pauses the blink effect. */
    public void pauseBlinkEffect() {
        if (blinkTimer != null) {
            if (blinkTimer.isRunning()) {
                blinkTimer.stop();
            }
        }
    }

    /** Disables the blink effect. */
    public void disableBlinkEffect() {
        blinkEffectEnabled = false;
        this.timeOfLastBlink = 0;
        this.millsBetweenBlinks = 0;

        blinkTimer.stop();
        blinkTimer = null;
    }

    /** Swaps the background and foreground colors. */
    public void invertColors() {
        final Color temp = backgroundColor;
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
    public void setBackgroundColor(final Color color) {
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
    public void setForegroundColor(final Color color) {
	    boolean canProceed = color != null;
	    canProceed &= foregroundColor.equals(color) == false;

	    if (canProceed) {
	        this.foregroundColor = color;
        }
    }
}