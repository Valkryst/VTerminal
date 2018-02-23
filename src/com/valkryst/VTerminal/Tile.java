package com.valkryst.VTerminal;

import com.valkryst.VTerminal.misc.ImageCache;
import com.valkryst.VTerminal.shader.Shader;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@ToString
public class Tile {
    /** The hash value, of the tile, used by the image cache. */
    @Getter protected int cacheHash;
    /** Whether or not to update the cache hash. */
    protected boolean updateCacheHash;

    /** The shaders to run on each image. */
    @Getter private final List<Shader> shaders = new LinkedList<>();

    /** The character. */
	@Getter private char character;
	/** Whether or not the foreground should be drawn using the background color. */
	@Getter @Setter private boolean isHidden;
    /** The background color. Defaults to black. */
    @Getter private Color backgroundColor;
	/** The foreground color. Defaults to white. */
	@Getter private Color foregroundColor;

	/** Whether or not to draw the tile as underlined. */
	@Getter @Setter private boolean isUnderlined;
    /** The thickness of the underline to draw beneath the tile. */
	@Getter private int underlineThickness;

	private Timer blinkTimer;
	/** The amount of time, in milliseconds, before the blink effect can occur. */
	@Getter private short millsBetweenBlinks;

	/** Whether or not the foreground and background colors are equal. */
	@Getter private boolean foregroundAndBackgroundColorEqual;

    /**
     * Constructs a new Tile.
     *
     * @param character
     *          The character.
     */
	public Tile(final char character) {
	    reset();
	    this.character = character;
    }

    /**
     * Constructs a new tile by copying the data of a tile.
     *
     * Does not copy the blinkTimer.
     *
     * @param otherTile
     *          The tile.
     *
     * @throws NullPointerException
     *          If the tile is null.
     */
    public Tile(final @NonNull Tile otherTile) {
        reset();
        copy(otherTile);
    }

    /** Resets the tile to it's default state. */
    public void reset() {
        removeAllShaders();

        character = ' ';
        isHidden = false;
        backgroundColor = Color.BLACK;
        foregroundColor = Color.WHITE;

        isUnderlined = false;
        underlineThickness = 2;

        disableBlinkEffect();
        millsBetweenBlinks = 1000;

        foregroundAndBackgroundColorEqual = false;
    }

    /**
     * Copies the settings of a tile to this tile.
     *
     * Does not copy the blinkTimer.
     *
     * @param otherTile
     *          The other tile.
     */
    public void copy(final @NonNull Tile otherTile) {
        for (final Shader shader : otherTile.getShaders()) {
            shaders.add(shader.copy());
        }

        this.character = otherTile.getCharacter();

        isHidden = otherTile.isHidden();

        backgroundColor = otherTile.getBackgroundColor();
        foregroundColor = otherTile.getForegroundColor();

        isUnderlined = otherTile.isUnderlined();
        underlineThickness = otherTile.getUnderlineThickness();

        foregroundAndBackgroundColorEqual = otherTile.isForegroundAndBackgroundColorEqual();

        cacheHash = otherTile.getCacheHash();
        updateCacheHash = false;
    }

    /** Updates the cache hash value. */
    public void updateCacheHash() {
        if (updateCacheHash) {
            updateCacheHash = false;
            cacheHash = Objects.hash(character, backgroundColor, foregroundColor, shaders);
        }
    }

    /**
     * Draws the tile onto the specified context.
     *
     * @param gc
     *         The graphics context to draw with.
     *
     * @param imageCache
     *         The image cache to retrieve tile images from.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate where the tile is to be drawn.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate where the tile is to be drawn.
     *
     * @throws NullPointerException
     *         If the gc or image cache are null.
     */
    public void draw(final @NonNull Graphics2D gc, final @NonNull ImageCache imageCache, int columnIndex, int rowIndex) {
        final int fontWidth = imageCache.getFont().getWidth();
        final int fontHeight = imageCache.getFont().getHeight();

        columnIndex *= fontWidth;
        rowIndex *= fontHeight;

        // Handle hidden state:
        if (isHidden || foregroundAndBackgroundColorEqual) {
            gc.setColor(backgroundColor);
            gc.fillRect(columnIndex, rowIndex, fontWidth, fontHeight);
        } else {
            final Image image = imageCache.retrieve(this);
            gc.drawImage(image, columnIndex, rowIndex, null);

            // Draw underline:
            if (isUnderlined) {
                gc.setColor(foregroundColor);

                if (underlineThickness > fontHeight) {
                    underlineThickness = fontHeight;
                }

                final int y = rowIndex + fontHeight - underlineThickness;
                gc.fillRect(columnIndex, y, fontWidth, underlineThickness);
            }
        }
    }

    /**
     * Enables the blink effect.
     *
     * @param millsBetweenBlinks
     *          The amount of time, in milliseconds, before the blink effect can occur.
     *
     * @param redrawFunction
     *          The redraw function to call whenever a blink occurs.
     */
    public void enableBlinkEffect(final short millsBetweenBlinks, final @NonNull Runnable redrawFunction) {
        if (millsBetweenBlinks <= 0) {
            this.millsBetweenBlinks = 1000;
        } else {
            this.millsBetweenBlinks = millsBetweenBlinks;
        }

        blinkTimer = new Timer(this.millsBetweenBlinks, e -> {
            isHidden = !isHidden;
            redrawFunction.run();
        });
        blinkTimer.setInitialDelay(this.millsBetweenBlinks);
        blinkTimer.setRepeats(true);
        blinkTimer.start();
    }

    /** Disables the blink effect. */
    public void disableBlinkEffect() {
        if (blinkTimer != null) {
            blinkTimer.stop();
            blinkTimer = null;
        }
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
            foregroundAndBackgroundColorEqual = foregroundColor.equals(backgroundColor);
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
            foregroundAndBackgroundColorEqual = foregroundColor.equals(backgroundColor);
        }
    }

    /**
     * Sets the new underline thickness.
     *
     * If the specified thickness is negative, then the thickness is set to 1.
     *
     * If the font height is greater than Byte.MAX_VALUE, then the thickness is
     * set to Byte.MAX_VALUE.
     *
     * @param underlineThickness
     *          The new underline thickness.
     */
    public void setUnderlineThickness(final int underlineThickness) {
        if (underlineThickness <= 0) {
            this.underlineThickness = 1;
        } else {
            this.underlineThickness = underlineThickness;
        }
    }

    /**
     * Adds one or more shaders to the tile.
     *
     * @param shaders
     *          The shaders.
     */
    public void addShaders(final @NonNull Shader ... shaders) {
        this.shaders.addAll(Arrays.asList(shaders));
        updateCacheHash = true;
    }

    /**
     * Removes one or more shaders from the tile.
     *
     * @param shaders
     *          The shaders.
     */
    public void removeShaders(final @NonNull Shader ... shaders) {
        this.shaders.removeAll(Arrays.asList(shaders));
        updateCacheHash = true;
    }

    /** Removes all shaders from the tile. */
    public void removeAllShaders() {
        shaders.clear();
        updateCacheHash = true;
    }
}