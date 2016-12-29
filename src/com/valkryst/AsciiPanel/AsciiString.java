package com.valkryst.AsciiPanel;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;

public class AsciiString {
    /** The characters of the string. */
    @Getter private ArrayList<AsciiCharacter> characters;

    /**
     * Constructs a new AsciiString of the specified length with all characters set to ' '.
     *
     * @param length
     *         The length to make the string.
     */
    public AsciiString(int length) {
        if (length < 0) {
            length = 0;
        }

        characters = new ArrayList<>(Collections.nCopies(length, new AsciiCharacter(' ')));
    }

    /**
     * Constructs a new AsciiString.
     *
     * @param string
     *         The string.
     */
    public AsciiString(final String string) {
        if (string == null) {
            characters = new ArrayList<>();
        } else {
            characters = new ArrayList<>(string.length());

            for (final char c : string.toCharArray()) {
                characters.add(new AsciiCharacter(c));
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();

        for (int i = 0 ; i < characters.size() ; i++) {
            builder.append(characters.get(i).getCharacter());
        }

        return builder.toString();
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof AsciiString == false) {
            return false;
        }

        final AsciiString otherString = (AsciiString) object;

        if (characters.size() != otherString.getCharacters().size()) {
            return false;
        }

        for (int i = 0 ; i < characters.size() ; i++) {
           final AsciiCharacter thisChar = characters.get(i);
           final AsciiCharacter otherChar = otherString.getCharacters().get(i);

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
    public void draw(final GraphicsContext gc, final AsciiFont font, int rowIndex) {
        if (gc == null || font == null) {
            return;
        }

        if (rowIndex < 0) {
            rowIndex = 0;
        }

        for (int columnIndex = 0 ; columnIndex < characters.size() ; columnIndex++) {
            characters.get(columnIndex).draw(gc, font, columnIndex, rowIndex);
        }
    }

    /**
     * Determines whether or not the specified range is valid.
     *
     * @param beginIndex
     *         The first index of the range.
     *
     * @param endIndex
     *         The last index of the range.
     *
     * @return
     *         Whether or not the specified range is valid.
     */
    private boolean isRangeValid(final int beginIndex, final int endIndex) {
        boolean isValid = beginIndex < 0 == false;
        isValid &= endIndex > characters.size() == false;
        isValid &= beginIndex > endIndex == false;

        return isValid;
    }

    /**
     * Sets a new character in the specified position.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate to place the character at.
     *
     * @param character
     *         The new character.
     */
    public void setCharacter(int columnIndex, final AsciiCharacter character) {
        if (character != null) {
            if (columnIndex >= 0 && columnIndex < characters.size()) {
                characters.set(columnIndex, character);
            }
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
        applyColorGradient(0, characters.size(), colorFrom, colorTo, applyToBackground);
    }

    /**
     * Applies a color gradient to a portion of the string.
     *
     * @param beginIndex
     *         The x-axis (column) coordinate of the character to begin the gradient at.
     *
     * @param endIndex
     *         The x-axis (column) coordinate of the character to end the gradient before.
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

        if (endIndex > characters.size()) {
            endIndex = characters.size();
        }

        if (isRangeValid(beginIndex, endIndex) == false) {
            return;
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
        final double redChangePerColumn = (redDifference / characters.size()) * 255;
        final double greenChangePerColumn = (greenDifference / characters.size()) * 255;
        final double blueChangePerColumn = (blueDifference / characters.size()) * 255;

        // Set the starting RGB values and convert them to the 0-255 scale:
        double redCurrent = colorFrom.getRed() * 255;
        double greenCurrent = colorFrom.getGreen() * 255;
        double blueCurrent = colorFrom.getBlue() * 255;

        // Set the new color values:
        final StringBuilder stringBuilder = new StringBuilder();

        for (int column = beginIndex ; column < endIndex ; column++) {
            stringBuilder.append("rgb(")
                         .append((int) redCurrent).append(",")
                         .append((int) greenCurrent).append(",")
                         .append((int) blueCurrent)
                         .append(")");

            if (applyToBackground) {
                characters.get(column).setBackgroundColor(Color.web(stringBuilder.toString()));
            } else {
                characters.get(column).setForegroundColor(Color.web(stringBuilder.toString()));
            }

            redCurrent += redChangePerColumn;
            greenCurrent += greenChangePerColumn;
            blueCurrent += blueChangePerColumn;

            stringBuilder.setLength(0);
        }
    }

    /** Swaps the background and foreground colors of every character. */
    public void invertColors() {
        invertColors(0, characters.size());
    }

    /**
     * Swaps the background and foreground colors of every character in the specified range.
     *
     *
     * @param beginIndex
     *         The x-axis (column) coordinate of the character to begin the inversion at.
     *
     * @param endIndex
     *         The x-axis (column) coordinate of the character to end the inversion before.
     */
    public void invertColors(int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            beginIndex = 0;
        }

        if (endIndex > characters.size()) {
            endIndex = characters.size();
        }

        if (isRangeValid(beginIndex, endIndex)) {
            for (int column = beginIndex ; column < endIndex ; column++) {
                characters.get(column).invertColors();
            }
        }
    }

    /**
     * Sets the background color of all characters.
     *
     * @param color
     *         The new background color.
     */
    public void setBackgroundColor(final Paint color) {
        if (color != null) {
            for (final AsciiCharacter c : characters) {
                c.setBackgroundColor(color);
            }
        }
    }

    /**
     * Sets the foreground color of all characters.
     *
     * @param color
     *         The new foreground color.
     */
    public void setForegroundColor(final Paint color) {
        if (color != null) {
            for (final AsciiCharacter c : characters) {
                c.setForegroundColor(color);
            }
        }
    }

    /**
     * Sets the background color of the specified range of characters.
     *
     * @param color
     *         The new background color.
     *
     * @param beginIndex
     *         The x-axis (column) coordinate of the character to begin the change at.
     *
     * @param endIndex
     *         The x-axis (column) coordinate of the character to end the change before.
     */
    public void setBackgroundColor(final Paint color, int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            beginIndex = 0;
        }

        if (endIndex > characters.size()) {
            endIndex = characters.size();
        }

        boolean canProceed = color != null;
        canProceed &= isRangeValid(beginIndex, endIndex);

        if (canProceed) {
            for (int column = beginIndex ; column < endIndex ; column++) {
                characters.get(column).setBackgroundColor(color);
            }
        }
    }

    /**
     * Sets the foreground color of the specified range of characters.
     *
     * @param color
     *         The new foreground color.
     *
     * @param beginIndex
     *         The x-axis (column) coordinate of the character to begin the change at.
     *
     * @param endIndex
     *         The x-axis (column) coordinate of the character to end the change before.
     */
    public void setForegroundColor(final Paint color, int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            beginIndex = 0;
        }

        if (endIndex > characters.size()) {
            endIndex = characters.size();
        }

        boolean canProceed = color != null;
        canProceed &= isRangeValid(beginIndex, endIndex);

        if (canProceed) {
            for (int column = beginIndex ; column < endIndex ; column++) {
                characters.get(column).setForegroundColor(color);
            }
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
