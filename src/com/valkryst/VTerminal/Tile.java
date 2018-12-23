package com.valkryst.VTerminal;

import com.valkryst.VTerminal.misc.ImageCache;
import com.valkryst.VTerminal.shader.Shader;
import com.valkryst.VTerminal.shader.misc.FlipShader;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

@ToString
public class Tile {
    /** The hash value, of the tile, used by the image cache. */
    @Getter protected int cacheHash;

    /** The shaders to run on each image. */
    @Getter private final List<Shader> shaders = new LinkedList<>();

    /** The character. */
	@Getter private int character = ' ';
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

	/** Whether or not the foreground and background colors are equal. */
	@Getter private boolean foregroundAndBackgroundColorEqual;

    /**
     * Constructs a new Tile.
     *
     * @param character
     *          The character.
     */
	public Tile(final int character) {
	    reset();
	    this.character = character;
    }

    /**
     * Constructs a new tile by copying the data of a tile.
     *
     * @param otherTile
     *          The tile.
     */
    public Tile(final Tile otherTile) {
        reset();

        if (otherTile == null) {
            this.character = ' ';
        } else {
            copy(otherTile);
        }
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

        foregroundAndBackgroundColorEqual = false;

        cacheHash = hash();
    }

    /**
     * Copies the settings of a tile to this tile.
     *
     * @param otherTile
     *          The other tile.
     */
    public void copy(final Tile otherTile) {
        if (otherTile == null) {
            return;
        }

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

        if (cacheHash == otherTile.getCacheHash()) {
            cacheHash = otherTile.getCacheHash();
        } else {
            cacheHash = hash();
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
     */
    public void draw(final Graphics2D gc, final ImageCache imageCache, int columnIndex, int rowIndex) {
        if (gc == null || imageCache == null) {
            return;
        }

        final int fontWidth = imageCache.getFont().getWidth();
        final int fontHeight = imageCache.getFont().getHeight();

        columnIndex *= fontWidth;
        rowIndex *= fontHeight;

        // Handle hidden state:
        if (isHidden || foregroundAndBackgroundColorEqual || Character.isSpaceChar(character)) {
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
     * Generates a hash value for the Tile.
     *
     * @return
     *          The hash value.
     */
    public int hash() {
        return Objects.hash(character, backgroundColor, foregroundColor, shaders);
    }

    /**
     * Sets the new character.
     *
     * @param character
     *        The new character.
     */
    public void setCharacter(final int character) {
        if (this.character != character) {
            this.character = character;

            cacheHash = hash();
        }
    }

    /**
     * Sets the new background color.
     *
     * @param color
     *         The new background color.
     */
    public void setBackgroundColor(final Color color) {
        if (color != null) {
            if (backgroundColor.equals(color) == false) {
                backgroundColor = color;
                foregroundAndBackgroundColorEqual = foregroundColor.equals(backgroundColor);

                cacheHash = hash();
            }
        }
    }

    /**
     * Sets the new foreground color.
     *
     * @param color
     *         The new foreground color.
     */
    public void setForegroundColor(final Color color) {
        if (color != null) {
            if (foregroundColor.equals(color) == false) {
                foregroundColor = color;
                foregroundAndBackgroundColorEqual = foregroundColor.equals(backgroundColor);

                cacheHash = hash();
            }
        }
    }

    /**
     * Sets whether or not the tile is flipped horizontally.
     *
     * @param isFlippedHorizontally
     *        Whether or not the tile is flipped horizontally.
     */
    public void setFlippedHorizontally(final boolean isFlippedHorizontally) {
        for (final Shader shader : shaders) {
            if (shader instanceof FlipShader) {
                ((FlipShader) shader).setFlippedHorizontally(isFlippedHorizontally);

                cacheHash = hash();
                return;
            }
        }

        final FlipShader flipShader = new FlipShader();
        flipShader.setFlippedHorizontally(isFlippedHorizontally);
        shaders.add(flipShader);

        cacheHash = hash();
    }

    /**
     * Sets whether or not the tile is flipped vertically.
     *
     * @param isFlippedVertically
     *        Whether or not the tile is flipped vertically.
     */
    public void setFlippedVertically(final boolean isFlippedVertically) {
        for (final Shader shader : shaders) {
            if (shader instanceof FlipShader) {
                ((FlipShader) shader).setFlippedVertically(isFlippedVertically);

                cacheHash = hash();
                return;
            }
        }

        final FlipShader flipShader = new FlipShader();
        flipShader.setFlippedVertically(isFlippedVertically);
        shaders.add(flipShader);

        cacheHash = hash();
    }

    /**
     * Sets the new underline thickness.
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
    public void addShaders(final Shader ... shaders) {
        if (shaders != null) {
            this.shaders.addAll(Arrays.asList(shaders));

            cacheHash = hash();
        }
    }

    /**
     * Removes one or more shaders from the tile.
     *
     * @param shaders
     *          The shaders.
     */
    public void removeShaders(final Shader ... shaders) {
        if (shaders != null) {
            this.shaders.removeAll(Arrays.asList(shaders));

            cacheHash = hash();
        }
    }

    /** Removes all shaders from the tile. */
    public void removeAllShaders() {
        shaders.clear();

        cacheHash = hash();
    }
}