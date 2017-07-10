package com.valkryst.VTerminal;

import com.valkryst.VTerminal.font.Font;
import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.misc.ImageColorReplacer;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.*;


public class AsciiCharacter {
    private final static ImageColorReplacer COLOR_REPLACER = new ImageColorReplacer();

    /** The transparent color. */
    public final static Color TRANSPARENT_COLOR = new Color(0, 0, 0, 0);

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

	/** Whether or not to draw the character as underlined. */
	@Getter @Setter private boolean isUnderlined = false;
    /** The thickness of the underline to draw beneath the character. */
	@Getter private int underlineThickness = 2;

	/** Whether or not the character should be flipped horizontally when drawn. */
	@Getter @Setter private boolean isFlippedHorizontally = false;
	/** Whether or not the character should be flipped vertically when drawn. */
	@Getter @Setter private boolean isFlippedVertically = false;

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
    }

    @Override
    public String toString() {
        return "Character:\n" +
                "\tCharacter: '" + character + "'\n" +
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
     *         The graphics context to draw with.
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
    public void draw(final Graphics2D gc, final Font font, int columnIndex, int rowIndex) {
        BufferedImage bufferedImage = font.getCharacterImage(character, backgroundColor != TRANSPARENT_COLOR);

        // Handle Horizontal/Vertical Flipping:
        if (isFlippedHorizontally || isFlippedVertically) {
            AffineTransform tx;

            if (isFlippedHorizontally && isFlippedVertically) {
                tx = AffineTransform.getScaleInstance(-1, -1);
                tx.translate(-bufferedImage.getWidth(), -bufferedImage.getHeight());
            } else if (isFlippedHorizontally) {
                tx = AffineTransform.getScaleInstance(-1, 1);
                tx.translate(-bufferedImage.getWidth(), 0);
            } else  {
                tx = AffineTransform.getScaleInstance(1, -1);
                tx.translate(0, -bufferedImage.getHeight());
            }

            final BufferedImageOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BICUBIC);
            bufferedImage = op.filter(bufferedImage, null);
        }

        // Retrieve character image & set colors:
        Image image = bufferedImage;

        if (foregroundColor.equals(Color.WHITE) == false || (backgroundColor.equals(Color.BLACK) == false && backgroundColor.equals(TRANSPARENT_COLOR) == false)) {
            final BufferedImageOp op = COLOR_REPLACER.retrieveOperation(backgroundColor, foregroundColor);
            image = op.filter(bufferedImage, null);
        }

        // Draw character:
	    final int fontWidth = font.getWidth();
	    final int fontHeight = font.getHeight();

	    columnIndex *= fontWidth;
	    rowIndex *= fontHeight;

        gc.drawImage(image, columnIndex, rowIndex,null);

        boundingBox.setLocation(columnIndex, rowIndex);
        boundingBox.setSize(fontWidth, fontHeight);

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
            isHidden = !isHidden;
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
                isHidden = false;
                blinkTimer.stop();
            }
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