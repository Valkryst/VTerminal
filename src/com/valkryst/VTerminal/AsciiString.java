package com.valkryst.VTerminal;

import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.misc.IntRange;
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

        for (final AsciiCharacter c : characters) {
            builder.append(c.getCharacter());
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
     *         The graphics context to draw with.
     *
     * @param font
     *         The font to draw with.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate where the characters are to be drawn.
     *         Includes the first index and excludes the last index.
     */
    public void draw(final Graphics2D gc, final Font font, int rowIndex) {
        if (gc == null || font == null) {
            return;
        }

        if (rowIndex < 0) {
            rowIndex = 0;
        }

        for (int columnIndex = 0; columnIndex < charactersToBeRedrawn.length; columnIndex++) {
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
    public boolean isRangeValid(final IntRange rangeIndices) {
        if (rangeIndices == null) {
            return false;
        }

        final int beginIndex = rangeIndices.getBegin();
        final int endIndex = rangeIndices.getEnd();

        boolean isValid = beginIndex < 0 == false;
        isValid &= endIndex > characters.length == false;
        isValid &= beginIndex > endIndex == false;

        return isValid;
    }

    public void setAllCharactersToBeRedrawn() {
        Arrays.fill(charactersToBeRedrawn, true);
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
    public void setCharacter(final int columnIndex, final AsciiCharacter character) {
        if (character == null) {
            return;
        }

        if (columnIndex >= 0 && columnIndex < characters.length) {
            characters[columnIndex] = character;
            charactersToBeRedrawn[columnIndex] = true;
        }
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
    public void setCharacter(final int columnIndex, final char character) {
        if (columnIndex >= 0 && columnIndex < characters.length) {
            characters[columnIndex].setCharacter(character);
            charactersToBeRedrawn[columnIndex] = true;
        }
    }

    /**
     * Sets every character in the specified range to the specified character.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     *
     * @param character
     *         The character to change to.
     */
    public void setCharacters(final IntRange rangeIndices, final char character) {
        if (rangeIndices == null) {
            return;
        }

        rangeIndices.clampValuesToRange(0, characters.length);

        final int beginIndex = rangeIndices.getBegin();
        final int endIndex = rangeIndices.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            characters[columnIndex].setCharacter(character);
        }
    }

    /**
     * Sets every character to the specified character.
     *
     * @param character
     *         The character to change to.
     */
    public void setAllCharacters(final char character) {
        for (final AsciiCharacter c : characters) {
            c.setCharacter(character);
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
     *         Includes the first index and excludes the last index.
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
        if (rangeIndices == null) {
            return;
        }

        rangeIndices.clampValuesToRange(0, characters.length);

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

        // Determine the amount to increment the RGB values by and convert the values to the 0-1 scale:
        final float redChangePerColumn = (redDifference / characters.length) / 255f;
        final float greenChangePerColumn = (greenDifference / characters.length) / 255f;
        final float blueChangePerColumn = (blueDifference / characters.length) / 255f;

        // Set the starting RGB values and convert them to the 0-1 scale:
        float redCurrent = colorFrom.getRed() / 255f;
        float greenCurrent = colorFrom.getGreen() / 255f;
        float blueCurrent = colorFrom.getBlue() / 255f;

        // Set the new color values:
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
     *         Includes the first index and excludes the last index.
     */
    public void enableBlinkEffect(final short millsBetweenBlinks, final Radio<String> radio, final IntRange rangeIndices) {
        if (rangeIndices == null) {
            return;
        }

        rangeIndices.clampValuesToRange(0, characters.length);

        if (isRangeValid(rangeIndices) == false) {
            return;
        }

        final int beginIndex = rangeIndices.getBegin();
        final int endIndex = rangeIndices.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            charactersToBeRedrawn[columnIndex] = true;
            characters[columnIndex].enableBlinkEffect(millsBetweenBlinks, radio);
        }
    }


    /**
     * Resumes the blink effect for every character in the specified range.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the resume between.
     *         Includes the first index and excludes the last index.
     */
    public void resumeBlinkEffect(final IntRange rangeIndices) {
        if (rangeIndices == null) {
            return;
        }

        rangeIndices.clampValuesToRange(0, characters.length);

        if (isRangeValid(rangeIndices) == false) {
            return;
        }

        final int beginIndex = rangeIndices.getBegin();
        final int endIndex = rangeIndices.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            characters[columnIndex].resumeBlinkEffect();
        }
    }

    /**
     * Pauses the blink effect for every character in the specified range.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the pause between.
     *         Includes the first index and excludes the last index.
     */
    public void pauseBlinkEffect(final IntRange rangeIndices) {
        if (rangeIndices == null) {
            return;
        }

        rangeIndices.clampValuesToRange(0, characters.length);

        if (isRangeValid(rangeIndices) == false) {
            return;
        }

        final int beginIndex = rangeIndices.getBegin();
        final int endIndex = rangeIndices.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            characters[columnIndex].pauseBlinkEffect();
        }
    }

    /**
     * Disables the blink effect for every character in the specified range.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     */
    public void disableBlinkEffect(final IntRange rangeIndices) {
        if (rangeIndices == null) {
            return;
        }

        rangeIndices.clampValuesToRange(0, characters.length);

        if (isRangeValid(rangeIndices) == false) {
            return;
        }

        final int beginIndex = rangeIndices.getBegin();
        final int endIndex = rangeIndices.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            charactersToBeRedrawn[columnIndex] = true;
            characters[columnIndex].disableBlinkEffect();
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
     *         Includes the first index and excludes the last index.
     */
    public void invertColors(final IntRange rangeIndices) {
        if (rangeIndices == null) {
            return;
        }

        rangeIndices.clampValuesToRange(0, characters.length);

        final int beginIndex = rangeIndices.getBegin();
        final int endIndex = rangeIndices.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            charactersToBeRedrawn[columnIndex] = true;
            characters[columnIndex].invertColors();
        }
    }

    /**
     * Sets the background color of all characters.
     *
     * @param color
     *         The new background color.
     */
    public void setBackgroundColor(final Color color) {
        setBackgroundColor(color, new IntRange(0, characters.length));
    }

    /**
     * Sets the foreground color of all characters.
     *
     * @param color
     *         The new foreground color.
     */
    public void setForegroundColor(final Color color) {
        setForegroundColor(color, new IntRange(0, characters.length));
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
        setBackgroundAndForegroundColor(background, foreground, new IntRange(0, characters.length));
    }

    /**
     * Sets the background color of the specified range of characters.
     *
     * @param color
     *         The new background color.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     */
    public void setBackgroundColor(final Color color, final IntRange rangeIndices) {
        if (color == null) {
            return;
        }

        if (rangeIndices == null) {
            return;
        }

        rangeIndices.clampValuesToRange(0, characters.length);

        if (isRangeValid(rangeIndices) == false) {
            return;
        }

        final int beginIndex = rangeIndices.getBegin();
        final int endIndex = rangeIndices.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            charactersToBeRedrawn[columnIndex] = true;
            characters[columnIndex].setBackgroundColor(color);
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
     *         Includes the first index and excludes the last index.
     */
    public void setForegroundColor(final Color color, final IntRange rangeIndices) {
        if (color == null) {
            return;
        }

        if (rangeIndices == null) {
            return;
        }

        rangeIndices.clampValuesToRange(0, characters.length);

        if (isRangeValid(rangeIndices) == false) {
            return;
        }

        final int beginIndex = rangeIndices.getBegin();
        final int endIndex = rangeIndices.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            charactersToBeRedrawn[columnIndex] = true;
            characters[columnIndex].setForegroundColor(color);
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
     *         Includes the first index and excludes the last index.
     */
    public void setBackgroundAndForegroundColor(final Color background, final Color foreground, final IntRange rangeIndices) {
        if (background == null) {
            return;
        }

        if (foreground == null) {
            return;
        }

        if (rangeIndices == null) {
            return;
        }

        rangeIndices.clampValuesToRange(0, characters.length);

        if (isRangeValid(rangeIndices) == false) {
            return;
        }

        final int beginIndex = rangeIndices.getBegin();
        final int endIndex = rangeIndices.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            charactersToBeRedrawn[columnIndex] = true;
            characters[columnIndex].setBackgroundColor(background);
            characters[columnIndex].setForegroundColor(foreground);
        }
    }

    /**
     * Sets all characters to either be hidden or visible.
     *
     * @param isHidden
     *         Whether or not the characters are to be hidden.
     */
    public void setHidden(final boolean isHidden) {
        for (int columnIndex = 0 ; columnIndex < characters.length ; columnIndex++) {
            charactersToBeRedrawn[columnIndex] = true;
            characters[columnIndex].setHidden(isHidden);
        }
    }

    /** Sets all characters to be flipped horizontally when being drawn. */
    public void flipCharactersHorizontally() {
        flipCharactersHorizontally(new IntRange(0, characters.length));
    }

    /** Sets all characters to not be flipped horizontally when being drawn. */
    public void unFlipCharactersHorizontally() {
        unFlipCharactersHorizontally(new IntRange(0, characters.length));
    }

    /** Sets all characters to be flipped vertically when being drawn. */
    public void flipCharactersVertically() {
        flipCharactersVertically(new IntRange(0, characters.length));
    }

    /** Sets all characters not to be flipped vertically when being drawn. */
    public void unFlipCharactersVertically() {
        unFlipCharactersVertically(new IntRange(0, characters.length));
    }

    /** Sets all characters to be flipped both horizontally and vertically when being drawn. */
    public void flipCharactersHorizontallyAndVertically() {
        flipCharactersHorizontallyAndVertically(new IntRange(0, characters.length));
    }

    /** Sets all characters to not be flipped both horizontally and vertically when being drawn. */
    public void unFlipCharactersHorizontallyAndVertically() {
        unFlipCharactersHorizontallyAndVertically(new IntRange(0, characters.length));
    }

    /**
     * Sets the specified range of characters to be flipped horizontally when being drawn.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     */
    public void flipCharactersHorizontally(final IntRange rangeIndices) {
        if (rangeIndices == null) {
            return;
        }

        rangeIndices.clampValuesToRange(0, characters.length);

        if (isRangeValid(rangeIndices) == false) {
            return;
        }

        final int beginIndex = rangeIndices.getBegin();
        final int endIndex = rangeIndices.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            if (characters[columnIndex].isFlippedHorizontally() == false) {
                charactersToBeRedrawn[columnIndex] = true;
                characters[columnIndex].setFlippedHorizontally(true);
            }
        }
    }

    /**
     * Sets the specified range of characters to not be flipped horizontally when being drawn.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     */
    public void unFlipCharactersHorizontally(final IntRange rangeIndices) {
        if (rangeIndices == null) {
            return;
        }

        rangeIndices.clampValuesToRange(0, characters.length);

        if (isRangeValid(rangeIndices) == false) {
            return;
        }

        final int beginIndex = rangeIndices.getBegin();
        final int endIndex = rangeIndices.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            if (characters[columnIndex].isFlippedHorizontally() == true) {
                charactersToBeRedrawn[columnIndex] = true;
                characters[columnIndex].setFlippedHorizontally(false);
            }
        }
    }

    /**
     * Sets the specified range of characters to be flipped vertically when being drawn.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     */
    public void flipCharactersVertically(final IntRange rangeIndices) {
        if (rangeIndices == null) {
            return;
        }

        rangeIndices.clampValuesToRange(0, characters.length);

        if (isRangeValid(rangeIndices) == false) {
            return;
        }

        final int beginIndex = rangeIndices.getBegin();
        final int endIndex = rangeIndices.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            if (characters[columnIndex].isFlippedVertically() == false) {
                charactersToBeRedrawn[columnIndex] = true;
                characters[columnIndex].setFlippedVertically(true);
            }
        }
    }

    /**
     * Sets the specified range of characters to not be flipped vertically when being drawn.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     */
    public void unFlipCharactersVertically(final IntRange rangeIndices) {
        if (rangeIndices == null) {
            return;
        }

        rangeIndices.clampValuesToRange(0, characters.length);

        if (isRangeValid(rangeIndices) == false) {
            return;
        }

        final int beginIndex = rangeIndices.getBegin();
        final int endIndex = rangeIndices.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            if (characters[columnIndex].isFlippedVertically() == true) {
                charactersToBeRedrawn[columnIndex] = true;
                characters[columnIndex].setFlippedVertically(false);
            }
        }
    }

    /**
     * Sets the specified range of characters to be flipped both horizontally and vertically when being drawn.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     */
    public void flipCharactersHorizontallyAndVertically(final IntRange rangeIndices) {
        flipCharactersHorizontally(rangeIndices);
        flipCharactersVertically(rangeIndices);
    }

    /**
     * Sets the specified range of characters to not be flipped both horizontally and vertically when being drawn.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     */
    public void unFlipCharactersHorizontallyAndVertically(final IntRange rangeIndices) {
        unFlipCharactersHorizontally(rangeIndices);
        unFlipCharactersVertically(rangeIndices);
    }

    /** Sets all characters to be underlined when being drawn. */
    public void underlineCharacters() {
        underlineCharacters(new IntRange(0, characters.length));
    }

    /** Sets all characters to not be underlined when being drawn. */
    public void unUnderlineCharacters() {
        unUnderlineCharacters(new IntRange(0, characters.length));
    }

    /**
     * Sets the specified range of characters to be underlined when being drawn.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     */
    public void underlineCharacters(final IntRange rangeIndices) {
        if (rangeIndices == null) {
            return;
        }

        rangeIndices.clampValuesToRange(0, characters.length);

        if (isRangeValid(rangeIndices) == false) {
            return;
        }

        final int beginIndex = rangeIndices.getBegin();
        final int endIndex = rangeIndices.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            if (characters[columnIndex].isUnderlined() == false) {
                charactersToBeRedrawn[columnIndex] = true;
                characters[columnIndex].setUnderlined(true);
            }
        }
    }

    /**
     * Sets the specified range of characters to not be underlined when being drawn.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     */
    public void unUnderlineCharacters(final IntRange rangeIndices) {
        if (rangeIndices == null) {
            return;
        }

        rangeIndices.clampValuesToRange(0, characters.length);

        if (isRangeValid(rangeIndices) == false) {
            return;
        }

        final int beginIndex = rangeIndices.getBegin();
        final int endIndex = rangeIndices.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            if (characters[columnIndex].isUnderlined() == true) {
                charactersToBeRedrawn[columnIndex] = true;
                characters[columnIndex].setUnderlined(false);
            }
        }
    }
}