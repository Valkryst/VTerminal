package com.valkryst.AsciiPanel;

import com.valkryst.AsciiPanel.font.AsciiFont;
import com.valkryst.radio.Radio;
import lombok.Getter;

import java.awt.*;
import java.util.Arrays;

public class AsciiString {
    /** The characters of the string. */
    @Getter private AsciiCharacter[] characters;

    /** The characters that need to be redrawn. */
    private boolean[] charactersToBeRedrawn;

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

        characters = new AsciiCharacter[length];
        charactersToBeRedrawn = new boolean[length];
        Arrays.fill(characters, new AsciiCharacter(' '));
        Arrays.fill(charactersToBeRedrawn, true);
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
            charactersToBeRedrawn = new boolean[string.length()];

            for (int columnIndex = 0 ; columnIndex < string.length() ; columnIndex++) {
                charactersToBeRedrawn[columnIndex] = false;
                characters[columnIndex] = new AsciiCharacter(string.charAt(columnIndex));
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
    public void draw(final Graphics gc, final AsciiFont font, int rowIndex) {
        if (gc == null || font == null) {
            return;
        }

        if (rowIndex < 0) {
            rowIndex = 0;
        }

        for (int columnIndex = 0 ; columnIndex < charactersToBeRedrawn.length ; columnIndex++) {
            if (charactersToBeRedrawn[columnIndex]) {
                charactersToBeRedrawn[columnIndex] = false;
                characters[columnIndex].draw(gc, font, columnIndex, rowIndex);
            }
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
        isValid &= endIndex > characters.length == false;
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
            if (columnIndex >= 0 && columnIndex < characters.length) {
                characters[columnIndex] = character;
                charactersToBeRedrawn[columnIndex] = true;
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
        applyColorGradient(0, characters.length, colorFrom, colorTo, applyToBackground);
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

        if (endIndex > characters.length) {
            endIndex = characters.length;
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
        final float redDifference = colorTo.getRed() - colorFrom.getRed();
        final float greenDifference = colorTo.getGreen() - colorFrom.getGreen();
        final float blueDifference = colorTo.getBlue() - colorFrom.getBlue();

        // Determine the amount to increment the RGB values by and convert the values to the 0-255 scale:
        final float redChangePerColumn = (redDifference / characters.length) * 255;
        final float greenChangePerColumn = (greenDifference / characters.length) * 255;
        final float blueChangePerColumn = (blueDifference / characters.length) * 255;

        // Set the starting RGB values and convert them to the 0-255 scale:
        float redCurrent = colorFrom.getRed() * 255;
        float greenCurrent = colorFrom.getGreen() * 255;
        float blueCurrent = colorFrom.getBlue() * 255;

        // Set the new color values:
        final StringBuilder stringBuilder = new StringBuilder();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            charactersToBeRedrawn[columnIndex] = true;

            if (applyToBackground) {
                characters[columnIndex].setBackgroundColor(new Color(redCurrent, greenCurrent, blueCurrent));
            } else {
                characters[columnIndex].setForegroundColor(new Color(redCurrent, greenCurrent, blueCurrent));
            }

            redCurrent += redChangePerColumn;
            greenCurrent += greenChangePerColumn;
            blueCurrent += blueChangePerColumn;

            stringBuilder.setLength(0);
        }
    }

    /**
     * Enables the blink effect for every character.
     *
     * @param millsBetweenBlinks
     *         The amount of time, in milliseconds, before the blink effect can occur.
     *
     * @param radio
     *         The Radio to transmit a DRAW event to whenever a blink occurs.
     */
    public void enableBlinkEffect(final short millsBetweenBlinks, final Radio<String> radio) {
        for (final AsciiCharacter c : characters) {
            c.enableBlinkEffect(millsBetweenBlinks, radio);
        }
    }

    /** Disables the blink effect for every character. */
    public void disableBlinkEffect() {
        for (final AsciiCharacter c : characters) {
            c.disableBlinkEffect();
        }
    }

    /**
     * Enables the blink effect for every character in the specified range.
     *
     * @param millsBetweenBlinks
     *         The amount of time, in milliseconds, before the blink effect can occur.
     *
     * @param radio
     *         The Radio to transmit a DRAW event to whenever a blink occurs.
     *
     * @param beginIndex
     *         The x-axis (column) coordinate of the character to begin the change at.
     *
     * @param endIndex
     *         The x-axis (column) coordinate of the character to end the change before.
     */
    public void enableBlinkEffect(final short millsBetweenBlinks, final Radio<String> radio, int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            beginIndex = 0;
        }

        if (endIndex > characters.length) {
            endIndex = characters.length;
        }

        if (isRangeValid(beginIndex, endIndex)) {
            for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
                charactersToBeRedrawn[columnIndex] = true;
                characters[columnIndex].enableBlinkEffect(millsBetweenBlinks, radio);
            }
        }
    }

    /**
     * Disables the blink effect for every character in the specified range.
     *
     * @param beginIndex
     *         The x-axis (column) coordinate of the character to begin the change at.
     *
     * @param endIndex
     *         The x-axis (column) coordinate of the character to end the change before.
     */
    public void disableBlinkEffect(int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            beginIndex = 0;
        }

        if (endIndex > characters.length) {
            endIndex = characters.length;
        }

        if (isRangeValid(beginIndex, endIndex)) {
            for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
                charactersToBeRedrawn[columnIndex] = true;
                characters[columnIndex].disableBlinkEffect();
            }
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
     *         The x-axis (column) coordinate of the character to end the inversion before.
     */
    public void invertColors(int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            beginIndex = 0;
        }

        if (endIndex > characters.length) {
            endIndex = characters.length;
        }

        if (isRangeValid(beginIndex, endIndex)) {
            for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
                charactersToBeRedrawn[columnIndex] = true;
                characters[columnIndex].invertColors();
            }
        }
    }

    /**
     * Sets the background color of all characters.
     *
     * @param color
     *         The new background color.
     */
    public void setBackgroundColor(final Color color) {
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
    public void setForegroundColor(final Color color) {
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
    public void setBackgroundColor(final Color color, int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            beginIndex = 0;
        }

        if (endIndex > characters.length) {
            endIndex = characters.length;
        }

        boolean canProceed = color != null;
        canProceed &= isRangeValid(beginIndex, endIndex);

        if (canProceed) {
            for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
                charactersToBeRedrawn[columnIndex] = true;
                characters[columnIndex].setBackgroundColor(color);
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
    public void setForegroundColor(final Color color, int beginIndex, int endIndex) {
        if (beginIndex < 0) {
            beginIndex = 0;
        }

        if (endIndex > characters.length) {
            endIndex = characters.length;
        }

        boolean canProceed = color != null;
        canProceed &= isRangeValid(beginIndex, endIndex);

        if (canProceed) {
            for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
                charactersToBeRedrawn[columnIndex] = true;
                characters[columnIndex].setForegroundColor(color);
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