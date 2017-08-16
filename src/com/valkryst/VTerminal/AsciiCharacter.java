package com.valkryst.VTerminal;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.misc.ColorFunctions;
import com.valkryst.VTerminal.misc.ColoredImageCache;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Objects;

@ToString
public class AsciiCharacter {
    /** The hash value, of the character, used by the image cache. */
    @Getter protected int cacheHash;
    /** Whether or not to update the cache hash. */
    protected boolean updateCacheHash = true;

    /** The character. */
	@Getter private char character;
	/** Whether or not the foreground should be drawn using the background color. */
	@Getter @Setter private boolean isHidden = false;
    /** The background color. Defaults to black. */
    @Getter private Color backgroundColor = Color.BLACK;
	/** The foreground color. Defaults to white. */
	@Getter private Color foregroundColor = Color.WHITE;
	/** The bounding box of the character's area. */
	@Getter private final Rectangle boundingBox;

	/** Whether or not to draw the character as underlined. */
	@Getter @Setter private boolean isUnderlined = false;
    /** The thickness of the underline to draw beneath the character. */
	@Getter private int underlineThickness = 2;

	/** Whether or not the character should be flipped horizontally when drawn. */
	@Getter private boolean isFlippedHorizontally = false;
	/** Whether or not the character should be flipped vertically when drawn. */
	@Getter private boolean isFlippedVertically = false;

	private Timer blinkTimer;
	/** The amount of time, in milliseconds, before the blink effect can occur. */
	@Getter private short millsBetweenBlinks = 1000;

    /**
     * Constructs a new AsciiCharacter.
     *
     * @param character
     *         The character.
     */
	public AsciiCharacter(final char character) {
	    this.character = character;
        boundingBox = new Rectangle();
    }
    /**
     * Constructs a new AsciiCharacter by copying the data
     * of an AsciiCharacter.
     *
     * Does not copy the blinkTimer.
     *
     * @param character
     *         The AsciiCharacter.
     *
     * @throws NullPointerException
     *        If the character is null.
     */
    public AsciiCharacter(final @NonNull AsciiCharacter character) {
        boundingBox = new Rectangle();

        copy(character);
    }

    /**
     * Copies the settings of an AsciiCharacter to this
     * AsciiCharacter.
     *
     * Does not copy the blinkTimer.
     *
     * @param character
     *        The other AsciiCharacter.
     */
    public void copy(final @NonNull AsciiCharacter character) {
        this.character = character.getCharacter();

        isHidden = character.isHidden();

        backgroundColor = character.getBackgroundColor();
        foregroundColor = character.getForegroundColor();

        boundingBox.setSize(character.getBoundingBox().getSize());
        boundingBox.setLocation(character.getBoundingBox().getLocation());

        isUnderlined = character.isUnderlined();
        underlineThickness = character.getUnderlineThickness();

        isFlippedHorizontally = character.isFlippedHorizontally();
        isFlippedVertically = character.isFlippedVertically();
    }

    /** Updates the cache hash value. */
    protected void updateCacheHash() {
        cacheHash = Objects.hash(character, backgroundColor, foregroundColor, isFlippedHorizontally, isFlippedVertically);
    }

    /**
     * Draws the character onto the specified context.
     *
     * @param gc
     *         The graphics context to draw with.
     *
     * @param imageCache
     *         The image cache to retrieve character images from.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate where the character is to be drawn.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate where the character is to be drawn.
     *
     * @throws NullPointerException
     *         If the gc or image cache are null.
     */
    public void draw(final @NonNull Graphics2D gc, final @NonNull ColoredImageCache imageCache, int columnIndex, int rowIndex) {
        if (updateCacheHash) {
            updateCacheHash();
            updateCacheHash = false;
        }

        final int fontWidth = imageCache.getFont().getWidth();
        final int fontHeight = imageCache.getFont().getHeight();

        columnIndex *= fontWidth;
        rowIndex *= fontHeight;

        boundingBox.setLocation(columnIndex, rowIndex);
        boundingBox.setSize(fontWidth, fontHeight);

        // Handle hidden state:
        if (isHidden) {
            gc.setColor(backgroundColor);
            gc.fillRect(columnIndex, rowIndex, fontWidth, fontHeight);
        } else {
            final BufferedImage image = imageCache.retrieveFromCache(this);
            gc.drawImage(image, columnIndex, rowIndex, null);
        }

        // Draw underline:
        if (isUnderlined) {
            gc.setColor(foregroundColor);

            final int y = rowIndex + fontHeight - underlineThickness;
            gc.fillRect(columnIndex, y, fontWidth, underlineThickness);
        }
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
        if (radio == null) {
            throw new NullPointerException("You must specify a Radio when enabling a blink effect.");
        }

        if (millsBetweenBlinks <= 0) {
            this.millsBetweenBlinks = 1000;
        } else {
            this.millsBetweenBlinks = millsBetweenBlinks;
        }

        blinkTimer = new Timer(this.millsBetweenBlinks, e -> {
            if (character == ' ') {
                invertColors();
            } else {
                isHidden = !isHidden;
            }

            radio.transmit("DRAW");
        });
        blinkTimer.setInitialDelay(this.millsBetweenBlinks);
        blinkTimer.setRepeats(true);
        blinkTimer.start();
    }

    /** Resumes the blink effect. */
    public void resumeBlinkEffect() {
        if (blinkTimer != null && blinkTimer.isRunning() == false) {
            blinkTimer.start();
        }
    }

    /** Pauses the blink effect. */
    public void pauseBlinkEffect() {
        if (blinkTimer != null && blinkTimer.isRunning()) {
            isHidden = false;
            blinkTimer.stop();
        }
    }

    /** Disables the blink effect. */
    public void disableBlinkEffect() {
        if (blinkTimer != null) {
            blinkTimer.stop();
            blinkTimer = null;
        }
    }

    /** Swaps the background and foreground colors. */
    public void invertColors() {
        final Color temp = backgroundColor;
        setBackgroundColor(foregroundColor);
        setForegroundColor(temp);
    }

    /**
     * Shades the background color by some factor, where a higher factor results
     * in a darker shade.
     *
     * @param tintFactor
     *        The factor.
     *
     *        Values should range from 0.0 to 1.0.
     */
    public void tintBackgroundColor(final double tintFactor) {
        backgroundColor = ColorFunctions.tint(backgroundColor, tintFactor);
        updateCacheHash = true;
    }


    /**
     * Shades the foreground color by some factor, where a higher factor results
     * in a darker shade.
     *
     * @param tintFactor
     *        The factor.
     *
     *        Values should range from 0.0 to 1.0.
     */
    public void tintForegroundColor(final double tintFactor) {
        foregroundColor = ColorFunctions.tint(foregroundColor, tintFactor);
        updateCacheHash = true;
    }

    /**
     * Shades the background and foreground color by some factor, where a higher
     * factor results in a darker shade.
     *
     * @param tintFactor
     *        The factor.
     *
     *        Values should range from 0.0 to 1.0.
     */
    public void tintBackgroundAndForegroundColor(final double tintFactor) {
        tintBackgroundColor(tintFactor);
        tintForegroundColor(tintFactor);
        updateCacheHash = true;
    }

    /**
     * Tints the background color by some factor, where a higher factor results
     * in a lighter tint.
     *
     * @param shadeFactor
     *        The factor.
     *
     *        Values should range from 0.0 to 1.0.
     */
    public void shadeBackgroundColor(final double shadeFactor) {
        backgroundColor = ColorFunctions.shade(backgroundColor, shadeFactor);
        updateCacheHash = true;
    }

    /**
     * Tints the foreground color by some factor, where a higher factor results
     * in a lighter tint.
     *
     * @param shadeFactor
     *        The factor.
     *
     *        Values should range from 0.0 to 1.0.
     */
    public void shadeForegroundColor(final double shadeFactor) {
        foregroundColor = ColorFunctions.shade(foregroundColor, shadeFactor);
        updateCacheHash = true;
    }

    /**
     * Tints the background and foreground color by some factor, where a higher
     * factor results in a lighter tint.
     *
     * @param shadeFactor
     *        The factor.
     *
     *        Values should range from 0.0 to 1.0.
     */
    public void shadeBackgroundAndForegroundColor(final double shadeFactor) {
        shadeBackgroundColor(shadeFactor);
        shadeForegroundColor(shadeFactor);
        updateCacheHash = true;
    }

    /**
     * Sets the new character.
     *
     * @param character
     *        The new character.
     */
    public void setCharacter(final char character) {
        if (this.character != character) {
            this.character = character;
            updateCacheHash = true;
        }
    }

    /**
     * Sets the new background color.
     *
     * @param color
     *         The new background color.
     *
     * @throws NullPointerException
     *         If the color is null.
     */
    public void setBackgroundColor(final @NonNull Color color) {
        if (backgroundColor.equals(color) == false) {
            backgroundColor = color;
            updateCacheHash = true;
        }
    }

    /**
     * Sets the new foreground color.
     *
     * @param color
     *         The new foreground color.
     *
     * @throws NullPointerException
     *         If the color is null.
     */
    public void setForegroundColor(final @NonNull Color color) {
        if (foregroundColor.equals(color) == false) {
            foregroundColor = color;
            updateCacheHash = true;
        }
    }

    /**
     * Sets whether or not the character is flipped horizontally.
     *
     * @param isFlippedHorizontally
     *        Whether or not the character is flipped horizontally.
     */
    public void setFlippedHorizontally(final boolean isFlippedHorizontally) {
        if (this.isFlippedHorizontally != isFlippedHorizontally) {
            this.isFlippedHorizontally = isFlippedHorizontally;
            updateCacheHash = true;
        }
    }

    /**
     * Sets whether or not the character is flipped vertically.
     *
     * @param isFlippedVertically
     *        Whether or not the character is flipped vertically.
     */
    public void setFlippedVertically(final boolean isFlippedVertically) {
        if (this.isFlippedVertically != isFlippedVertically) {
            this.isFlippedVertically = isFlippedVertically;
            updateCacheHash = true;
        }
    }

    /**
     * Sets the new underline thickness.
     *
     * If the specified thickness is negative, then the thickness is set to 1.
     * If the specified thickness is greater than the font height, then the thickness is set to the font height.
     * If the font height is greater than Byte.MAX_VALUE, then the thickness is set to Byte.MAX_VALUE.
     *
     * @param underlineThickness
     *         The new underline thickness.
     */
    public void setUnderlineThickness(final int underlineThickness) {
        if (underlineThickness > boundingBox.getHeight()) {
            this.underlineThickness = (int) boundingBox.getHeight();
        } else if (underlineThickness <= 0) {
            this.underlineThickness = 1;
        } else {
            this.underlineThickness = underlineThickness;
        }
    }
}