package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.LoadingBar;
import lombok.Getter;

import java.awt.Color;

public class LoadingBarBuilder extends ComponentBuilder<LoadingBar, LoadingBarBuilder> {
    /** The width of the loading bar, in characters. */
    @Getter private int width;
    /** The height of the loading bar, in characters. */
    @Getter private int height;

    /** The character that represents an incomplete cell. */
    @Getter private char incompleteCharacter;
    /** The character that represents a complete cell. */
    @Getter private char completeCharacter;

    /** The background color for incomplete cells. */
    @Getter private Color backgroundColor_incomplete;
    /** The foreground color for incomplete cells. */
    @Getter private Color foregroundColor_incomplete;

    /** The background color for complete cells. */
    @Getter private Color backgroundColor_complete;
    /** The foreground color for complete cells. */
    @Getter private Color foregroundColor_complete;

    @Override
    public LoadingBar build() {
        checkState();
        return new LoadingBar(this);
    }

    /**
     * Checks the current state of the builder.
     *
     * @throws IllegalStateException
     *          If the radio is null.
     */
    protected void checkState() throws IllegalStateException {
        super.checkState();

        if (radio == null) {
            throw new NullPointerException("The box must have a radio to transmit to.");
        }
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        width = 10;
        height = 1;

        radio = null;

        incompleteCharacter = '█';
        completeCharacter = '█';

        backgroundColor_incomplete = new Color(0xFF366C9F, true);
        foregroundColor_incomplete = new Color(255, 0, 0, 255);

        backgroundColor_complete = new Color(0xFF366C9F, true);
        foregroundColor_complete = new Color(0, 255, 0, 255);
    }

    /**
     * Sets the width of the loading bar, in characters.
     *
     * @param width
     *         The new width.
     *
     * @return
     *         This.
     */
    public LoadingBarBuilder setWidth(final int width) {
        if (width >= 1) {
            this.width = width;
        }

        return this;
    }

    /**
     * Sets the height of the loading bar, in characters.
     *
     * @param height
     *         The new height.
     *
     * @return
     *         This.
     */
    public LoadingBarBuilder setHeight(final int height) {
        if (height >= 1) {
            this.height = height;
        }

        return this;
    }

    /**
     * Sets the character that represents an incomplete cell.
     *
     * @param incompleteCharacter
     *        The character.
     *
     * @return
     *        This.
     */
    public LoadingBarBuilder setIncompleteCharacter(final char incompleteCharacter) {
        this.incompleteCharacter = incompleteCharacter;
        return this;
    }

    /**
     * Sets the character that represents a complete cell.
     *
     * @param completeCharacter
     *        The character.
     *
     * @return
     *        This.
     */
    public LoadingBarBuilder setCompleteCharacter(final char completeCharacter) {
        this.completeCharacter = completeCharacter;
        return this;
    }

    /**
     * Sets the background color for incomplete cells
     *
     * @param backgroundColor_incomplete
     *        The color.
     *
     * @return
     *        This.
     */
    public LoadingBarBuilder setBackgroundColor_incomplete(final Color backgroundColor_incomplete) {
        if (backgroundColor_incomplete != null) {
            this.backgroundColor_incomplete = backgroundColor_incomplete;
        }

        return this;
    }

    /**
     * Sets the foreground color for incomplete cells
     *
     * @param foregroundColor_incomplete
     *        The color.
     *
     * @return
     *        This.
     */
    public LoadingBarBuilder setForegroundColor_incomplete(final Color foregroundColor_incomplete) {
        if (foregroundColor_incomplete != null) {
            this.foregroundColor_incomplete = foregroundColor_incomplete;
        }

        return this;
    }

    /**
     * Sets the background color for complete cells
     *
     * @param backgroundColor_complete
     *        The color.
     *
     * @return
     *        This.
     */
    public LoadingBarBuilder setBackgroundColor_complete(final Color backgroundColor_complete) {
        if (backgroundColor_complete != null) {
            this.backgroundColor_complete = backgroundColor_complete;
        }

        return this;
    }

    /**
     * Sets the foreground color for complete cells
     *
     * @param foregroundColor_complete
     *        The color.
     *
     * @return
     *        This.
     */
    public LoadingBarBuilder setForegroundColor_complete(final Color foregroundColor_complete) {
        if (foregroundColor_complete != null) {
            this.foregroundColor_complete = foregroundColor_complete;
        }

        return this;
    }
}
