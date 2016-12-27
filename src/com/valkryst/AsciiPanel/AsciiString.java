package com.valkryst.AsciiPanel;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lombok.Getter;

public class AsciiString {
    /** The characters of the string. */
    @Getter private AsciiCharacter[] characters;

    /**
     * Constructs a new AsciiString of the specified length with all characters set to ' '.
     *
     * @param length
     *         The length to make the string.
     */
    public AsciiString(final int length) {
        characters = new AsciiCharacter[length];

        for (int i = 0; i < characters.length; i++) {
            characters[i] = new AsciiCharacter(' ');
        }
    }

    /**
     * Constructs a new AsciiString.
     *
     * @param string
     *         The string.
     */
    public AsciiString(final String string) {
        if (string == null) {
            characters = new AsciiCharacter[0];
        } else {
            characters = new AsciiCharacter[string.length()];

            for (int i = 0; i < characters.length; i++) {
                characters[i] = new AsciiCharacter(string.charAt(i));
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        for (int i = 0 ; i < characters.length ; i++) {
            builder.append(characters[i].getCharacter());
        }

        return builder.toString();
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof AsciiString == false) {
            return false;
        }

        final AsciiString otherString = (AsciiString) object;

        if (characters.length != otherString.getCharacters().length) {
            return false;
        }

        for (int i = 0 ; i < characters.length ; i++) {
           final AsciiCharacter thisChar = characters[i];
           final AsciiCharacter otherChar = otherString.getCharacters()[i];

           if (thisChar.equals(otherChar) == false) {
               return false;
           }
        }

        return true;
    }

    /**
     * Draws the characters of the string onto the specified context.
     *
     * @param gc
     *         The context on which to draw.
     *
     * @param font
     *         The font to draw with.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate where the characters are to be drawn.
     */
    public void draw(final GraphicsContext gc, final AsciiFont font, final int rowIndex) {
        for (int columnIndex = 0 ; columnIndex < characters.length ; columnIndex++) {
            characters[columnIndex].draw(gc, font, columnIndex, rowIndex);
        }
    }

    /**
     * Applies a color gradient to the entire string.
     *
     * @param colorFrom
     *         The color to begin the gradient with.
     *
     * @param colorTo
     *         The color to end the gradient with.
     *
     * @param applyToBackground
     *         Whether or not to apply the gradient to the background or foreground of the characters.
     */
    public void applyColorGradient(final Color colorFrom, final Color colorTo, final boolean applyToBackground) {
        applyColorGradient(0, characters.length, colorFrom, colorTo, applyToBackground);
    }

    /**
     * Applies a color gradient to a portion of the string.
     *
     * @param beginIndex
     *         The x-axis (column) coordinate of the character to begin the gradient at.
     *
     * @param endIndex
     *         The x-axis (column) coordinate of the character to end the gradient at.
     *
     * @param colorFrom
     *         The color to begin the gradient with.
     *
     * @param colorTo
     *         The color to end the gradient with.
     *
     * @param applyToBackground
     *         Whether or not to apply the gradient to the background or foreground of the characters.
     */
    public void applyColorGradient(int beginIndex, int endIndex, final Color colorFrom, final Color colorTo, final boolean applyToBackground) {
        if (beginIndex < 0) {
            beginIndex = 0;
        }

        if (endIndex > characters.length) {
            endIndex = characters.length;
        }

        if (colorFrom == null) {
            throw new IllegalArgumentException("You must specify a color to begin the gradient with.");
        }

        if (colorTo == null) {
            throw new IllegalArgumentException("You must specify a color to end the gradient with.");
        }

        // Determine the difference between the RGB values of the colors:
        final double redDifference = colorTo.getRed() - colorFrom.getRed();
        final double greenDifference = colorTo.getGreen() - colorFrom.getGreen();
        final double blueDifference = colorTo.getBlue() - colorFrom.getBlue();

        // Determine the amount to increment the RGB values by and convert the values to the 0-255 scale:
        final double redChangePerColumn = (redDifference / characters.length) * 255;
        final double greenChangePerColumn = (greenDifference / characters.length) * 255;
        final double blueChangePerColumn = (blueDifference / characters.length) * 255;

        // Set the starting RGB values and convert them to the 0-255 scale:
        double redCurrent = colorFrom.getRed() * 255;
        double greenCurrent = colorFrom.getGreen() * 255;
        double blueCurrent = colorFrom.getBlue() * 255;

        // Set the new color values:
        for (int column = beginIndex ; column < endIndex ; column++) {
            final String rgb = "rgb(" + (int) redCurrent + ", " + (int) greenCurrent + ", " + (int) blueCurrent + ")";

            if (applyToBackground) {
                characters[column].setBackgroundColor(Color.web(rgb));
            } else {
                characters[column].setForegroundColor(Color.web(rgb));
            }

            redCurrent += redChangePerColumn;
            greenCurrent += greenChangePerColumn;
            blueCurrent += blueChangePerColumn;
        }
    }

    /** Swaps the background and foreground colors of every character. */
    public void invertColors() {
        invertColors(0, characters.length);
    }

    /**
     * Swaps the background and foreground colors of every character in the specified range.
     *
     *
     * @param beginIndex
     *         The x-axis (column) coordinate of the character to begin the inversion at.
     *
     * @param endIndex
     *         The x-axis (column) coordinate of the character to end the inversion at.
     */
    public void invertColors(int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            beginIndex = 0;
        }

        if (endIndex > characters.length) {
            endIndex = characters.length;
        }

        for (int column = beginIndex ; column < endIndex ; column++) {
            characters[column].invertColors();
        }
    }

    /**
     * Sets the background color of all characters.
     *
     * @param color
     *         The new background color.
     */
    public void setBackgroundColor(final Paint color) {
        for (final AsciiCharacter c : characters) {
            c.setBackgroundColor(color);
        }
    }

    /**
     * Sets the foreground color of all characters.
     *
     * @param color
     *         The new foreground color.
     */
    public void setForegroundColor(final Paint color) {
        for (final AsciiCharacter c : characters) {
            c.setForegroundColor(color);
        }
    }

    /**
     * Sets all characters to either be hidden or visible.
     *
     * @param isHidden
     *         Whether or not the characters are to be hidden.
     */
    public void setHidden(final boolean isHidden) {
        for (final AsciiCharacter c : characters) {
            c.setHidden(isHidden);
        }
    }
}
