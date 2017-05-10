package com.valkryst.AsciiPanel;

import com.valkryst.AsciiPanel.font.AsciiFont;
import com.valkryst.AsciiPanel.misc.IntRange;
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
        Arrays.fill(charactersToBeRedrawn, true);

        // Cannot use Arrays.fill as it will use the same AsciiCharacter object to fill every element:
        for (int columnIndex = 0 ; columnIndex < length ; columnIndex++) {
            characters[columnIndex] = new AsciiCharacter(' ');
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
     * @param rangeIndices
     *         The range.
     *
     * @return
     *         Whether or not the specified range is valid.
     */
    private boolean isRangeValid(final IntRange rangeIndices) {
        final int beginIndex = rangeIndices.getBegin();
        final int endIndex = rangeIndices.getEnd();

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
     * Sets every character in the specified range to the specified character.
     *
     * @param range
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *
     * @param character
     *         The character to change to.
     */
    public void setCharacters(final IntRange range, final char character) {
        if (range != null) {
            range.clampValuesToRange(0, characters.length);

            for (int columnIndex = range.getBegin() ; columnIndex < range.getEnd() ; columnIndex++) {
                characters[columnIndex].setCharacter(character);
            }
        }
    }

    /**
     * Sets every character to the specified character.
     *
     * @param character
     *         The character to change to.
     */
    public void setAllCharacters(final char character) {
        for (int columnIndex = 0 ; columnIndex < characters.length ; columnIndex++) {
            characters[columnIndex].setCharacter(character);
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
        applyColorGradient(new IntRange(0, characters.length), colorFrom, colorTo, applyToBackground);
    }

    /**
     * Applies a color gradient to a portion of the string.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the gradient between.
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
    public void applyColorGradient(final IntRange rangeIndices, final Color colorFrom, final Color colorTo, final boolean applyToBackground) {
        rangeIndices.clampValuesToRange(0, characters.length);

        if (isRangeValid(rangeIndices) == false) {
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

        final int beginIndex = rangeIndices.getBegin();
        final int endIndex = rangeIndices.getEnd();

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

    /** Resumes the blink effect for every character. */
    public void resumeBlinkEffect() {
        for (final AsciiCharacter c : characters) {
            c.resumeBlinkEffect();
        }
    }

    /** Pauses the blink effect for every character. */
    public void pauseBlinkEffect() {
        for (final AsciiCharacter c : characters) {
            c.pauseBlinkEffect();
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
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     */
    public void enableBlinkEffect(final short millsBetweenBlinks, final Radio<String> radio, final IntRange rangeIndices) {
        rangeIndices.clampValuesToRange(0, characters.length);

        if (isRangeValid(rangeIndices)) {
            final int beginIndex = rangeIndices.getBegin();
            final int endIndex = rangeIndices.getEnd();

            for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
                charactersToBeRedrawn[columnIndex] = true;
                characters[columnIndex].enableBlinkEffect(millsBetweenBlinks, radio);
            }
        }
    }


    /**
     * Resumes the blink effect for every character in the specified range.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the resume between.
     */
    public void resumeBlinkEffect(final IntRange rangeIndices) {
        rangeIndices.clampValuesToRange(0, characters.length);

        if (isRangeValid(rangeIndices)) {
            final int beginIndex = rangeIndices.getBegin();
            final int endIndex = rangeIndices.getEnd();

            for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
                characters[columnIndex].resumeBlinkEffect();
            }
        }
    }

    /**
     * Pauses the blink effect for every character in the specified range.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the pause between.
     */
    public void pauseBlinkEffect(final IntRange rangeIndices) {
        rangeIndices.clampValuesToRange(0, characters.length);

        if (isRangeValid(rangeIndices)) {
            final int beginIndex = rangeIndices.getBegin();
            final int endIndex = rangeIndices.getEnd();

            for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
                characters[columnIndex].pauseBlinkEffect();
            }
        }
    }

    /**
     * Disables the blink effect for every character in the specified range.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     */
    public void disableBlinkEffect(final IntRange rangeIndices) {
        rangeIndices.clampValuesToRange(0, characters.length);

        if (isRangeValid(rangeIndices)) {
            final int beginIndex = rangeIndices.getBegin();
            final int endIndex = rangeIndices.getEnd();

            for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
                charactersToBeRedrawn[columnIndex] = true;
                characters[columnIndex].disableBlinkEffect();
            }
        }
    }

    /** Swaps the background and foreground colors of every character. */
    public void invertColors() {
        invertColors(new IntRange(0, characters.length));
    }

    /**
     * Swaps the background and foreground colors of every character in the specified range.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the inversion between.
     */
    public void invertColors(final IntRange rangeIndices) {
        rangeIndices.clampValuesToRange(0, characters.length);

        if (isRangeValid(rangeIndices)) {
            final int beginIndex = rangeIndices.getBegin();
            final int endIndex = rangeIndices.getEnd();

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
     * Sets the background and foreground color of all characters.
     *
     * @param background
     *         The new background color.
     *
     * @param foreground
     *         The new foreground color.
     */
    public void setBackgroundAndForegroundColor(final Color background, final Color foreground) {
        if (background == null || foreground == null) {
            return;
        }

        for (final AsciiCharacter c : characters) {
            c.setBackgroundColor(background);
            c.setForegroundColor(foreground);
        }
    }

    /**
     * Sets the background color of the specified range of characters.
     *
     * @param color
     *         The new background color.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     */
    public void setBackgroundColor(final Color color, final IntRange rangeIndices) {
        rangeIndices.clampValuesToRange(0, characters.length);

        boolean canProceed = color != null;
        canProceed &= isRangeValid(rangeIndices);

        if (canProceed) {
            final int beginIndex = rangeIndices.getBegin();
            final int endIndex = rangeIndices.getEnd();

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
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     */
    public void setForegroundColor(final Color color, final IntRange rangeIndices) {
        rangeIndices.clampValuesToRange(0, characters.length);

        boolean canProceed = color != null;
        canProceed &= isRangeValid(rangeIndices);

        if (canProceed) {
            final int beginIndex = rangeIndices.getBegin();
            final int endIndex = rangeIndices.getEnd();

            for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
                charactersToBeRedrawn[columnIndex] = true;
                characters[columnIndex].setForegroundColor(color);
            }
        }
    }

    /**
     * Sets the foreground color of the specified range of characters.
     *
     * @param background
     *         The new background color.
     *
     * @param foreground
     *         The new foreground color.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     */
    public void setBackgroundAndForegroundColor(final Color background, final Color foreground, final IntRange rangeIndices) {
        rangeIndices.clampValuesToRange(0, characters.length);

        boolean canProceed = background != null;
        canProceed &= foreground != null;
        canProceed &= isRangeValid(rangeIndices);

        if (canProceed) {
            final int beginIndex = rangeIndices.getBegin();
            final int endIndex = rangeIndices.getEnd();

            for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
                charactersToBeRedrawn[columnIndex] = true;
                characters[columnIndex].setBackgroundColor(background);
                characters[columnIndex].setForegroundColor(foreground);
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