package com.valkryst.VTerminal;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.misc.ColorFunctions;
import com.valkryst.VTerminal.misc.ColoredImageCache;
import com.valkryst.VTerminal.misc.IntRange;
import lombok.Getter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.Objects;

public class AsciiString {
    /** The characters of the string. */
    @Getter private AsciiCharacter[] characters;

    /** The characters that need to be redrawn. */
    @Getter private boolean[] charactersToBeRedrawn;

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

    public String detailedToString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("String:");
        builder.append("\n\tCharacters:\t").append(toString());
        builder.append("\n\tCharacters to be Redrawn:\t").append(Arrays.toString(charactersToBeRedrawn)).append("\n");

        for (final AsciiCharacter c : characters) {
            builder.append("\t").append(c.toString().replace("\n\t", "\n\t\t"));
        }

        return builder.toString();
    }

    @Override
    public boolean equals(final Object object) {
        if (object instanceof AsciiString == false) {
            return false;
        }

        if (object == this) {
            return true;
        }

        final AsciiString otherString = (AsciiString) object;

        boolean isEqual = Arrays.equals(characters, otherString.getCharacters());
        isEqual &= Arrays.equals(charactersToBeRedrawn, otherString.getCharactersToBeRedrawn());

        return isEqual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(characters, charactersToBeRedrawn);
    }

    /**
     * Draws the characters of the string onto the specified context.
     *
     * @param gc
     *         The graphics context to draw with.
     *
     * @param imageCache
     *         The image cache to retrieve character images from.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate where the characters are to be drawn.
     *         Includes the first index and excludes the last index.
     *
     * @throws NullPointerException
     *         If the gc or image cache is null.
     */
    public void draw(final Graphics2D gc, final ColoredImageCache imageCache, int rowIndex) {
        Objects.requireNonNull(gc);
        Objects.requireNonNull(imageCache);

        if (rowIndex < 0) {
            rowIndex = 0;
        }

        for (int columnIndex = 0; columnIndex < charactersToBeRedrawn.length; columnIndex++) {
            if (charactersToBeRedrawn[columnIndex]) {
                charactersToBeRedrawn[columnIndex] = false;
                characters[columnIndex].draw(gc, imageCache, columnIndex, rowIndex);
            }
        }
    }

    /** @return The length of the string. */
    public int length() {
        return characters.length;
    }

    /**
     * Determines whether or not the specified range is valid.
     *
     * @param range
     *         The range.
     *
     * @return
     *         Whether or not the specified range is valid.
     */
    public boolean isRangeValid(final IntRange range) {
        if (range == null) {
            return false;
        }

        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();

        boolean isValid = beginIndex < 0 == false;
        isValid &= endIndex > characters.length == false;
        isValid &= beginIndex > endIndex == false;

        return isValid;
    }

    /** Sets all characters to be redrawn on the next draw call. */
    public void setAllCharactersToBeRedrawn() {
        Arrays.fill(charactersToBeRedrawn, true);
    }

    public void setCharacterRangeToBeRedrawn(final IntRange range) {
        Objects.requireNonNull(range);

        if (isRangeValid(range) == false) {
            range.clampValuesToRange(0, characters.length);
        }

        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();

        for (int col = beginIndex ; col < endIndex ; col++) {
            charactersToBeRedrawn[col] = true;
        }
    }

    /**
     * Sets a new character in the specified position.
     *
     * @param column
     *         The x-axis (column) coordinate to place the character at.
     *
     * @param character
     *         The new character.
     *
     * @throws NullPointerException
     *         If the character is null.
     *
     * @throws IllegalArgumentException
     *         If the column is less than 0.
     *         If the column is greater than the length of the string.
     */
    public void setCharacter(final int column, final AsciiCharacter character) {
        Objects.requireNonNull(character);

        if (column < 0) {
            throw new IllegalArgumentException("The column index " + column + " cannot be below 0.");
        }

        if (column >= characters.length) {
            throw new IllegalArgumentException("The column index " + column + " cannot be greater than or equal to "
                                               + characters.length + ".");
        }

        characters[column] = character;
        charactersToBeRedrawn[column] = true;
    }

    /**
     * Sets a new character in the specified position.
     *
     * @param column
     *         The x-axis (column) coordinate to place the character at.
     *
     * @param character
     *         The new character.
     *
     * @throws IllegalArgumentException
     *         If the column is less than 0.
     *         If the column is greater than the length of the string.
     */
    public void setCharacter(final int column, final char character) {
        if (column < 0) {
            throw new IllegalArgumentException("The column index " + column + " cannot be below 0.");
        }

        if (column >= characters.length) {
            throw new IllegalArgumentException("The column index " + column + " cannot be greater than or equal to "
                                               + characters.length + ".");
        }

        characters[column].setCharacter(character);
        charactersToBeRedrawn[column] = true;
    }

    /**
     * Sets every character in the specified range to the specified character.
     *
     * @param character
     *         The character to change to.
     *
     * @param range
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     *
     * @throws NullPointerException
     *         If the range is null.
     */
    public void setCharacters(final char character, final IntRange range) {
        Objects.requireNonNull(range);

        if (isRangeValid(range) == false) {
            range.clampValuesToRange(0, characters.length);
        }

        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            characters[columnIndex].setCharacter(character);
            charactersToBeRedrawn[columnIndex] = true;
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

        setAllCharactersToBeRedrawn();
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
     * @param range
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
     *
     * @throws NullPointerException
     *         If the range, colorFrom, or colorTo is null.
     */
    public void applyColorGradient(final IntRange range, final Color colorFrom, final Color colorTo, final boolean applyToBackground) {
        Objects.requireNonNull(range);
        Objects.requireNonNull(colorFrom);
        Objects.requireNonNull(colorTo);

        if (isRangeValid(range) == false) {
            range.clampValuesToRange(0, characters.length);
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
        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();

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
     * Applies a shade gradient to the entire string.
     *
     * @param color
     *         The color to start the gradient with.
     *
     * @param applyToBackground
     *         Whether or not to apply the gradient to the background or foreground of the characters.
     */
    public void applyShadeGradient(final Color color, final boolean applyToBackground) {
        applyShadeGradient(new IntRange(0, characters.length), color, applyToBackground);
    }

    /**
     * Applies a shade gradient to a portion of the string.
     *
     * @param range
     *         The x-axis (column) coordinates of the characters to begin/end the gradient between.
     *         Includes the first index and excludes the last index.
     *
     * @param color
     *         The color to start the gradient with.
     *
     * @param applyToBackground
     *         Whether or not to apply the gradient to the background or foreground of the characters.
     *
     * @throws NullPointerException
     *         If the range or color is null.
     */
    public void applyShadeGradient(final IntRange range, Color color, final boolean applyToBackground) {
        Objects.requireNonNull(range);
        Objects.requireNonNull(color);

        if (isRangeValid(range) == false) {
            range.clampValuesToRange(0, characters.length);
        }

        // Set the new color values:
        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();
        final double shadeFactor = 1 / (double) endIndex;

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            charactersToBeRedrawn[columnIndex] = true;

            if (applyToBackground) {
                characters[columnIndex].setBackgroundColor(color);
            } else {
                characters[columnIndex].setForegroundColor(color);
            }

            color = ColorFunctions.shade(color, shadeFactor);
        }
    }

    /**
     * Applies a tint gradient to the entire string.
     *
     * @param color
     *         The color to start the gradient with.
     *
     * @param applyToBackground
     *         Whether or not to apply the gradient to the background or foreground of the characters.
     */
    public void applyTintGradient(final Color color, final boolean applyToBackground) {
        applyTintGradient(new IntRange(0, characters.length), color, applyToBackground);
    }

    /**
     * Applies a tint gradient to a portion of the string.
     *
     * @param range
     *         The x-axis (column) coordinates of the characters to begin/end the gradient between.
     *         Includes the first index and excludes the last index.
     *
     * @param color
     *         The color to start the gradient with.
     *
     * @param applyToBackground
     *         Whether or not to apply the gradient to the background or foreground of the characters.
     *
     * @throws NullPointerException
     *         If the range or color is null.
     */
    public void applyTintGradient(final IntRange range, Color color, final boolean applyToBackground) {
        Objects.requireNonNull(range);
        Objects.requireNonNull(color);

        if (isRangeValid(range) == false) {
            range.clampValuesToRange(0, characters.length);
        }

        // Set the new color values:
        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();
        final double tintFactor = 1 / (double) endIndex;

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            charactersToBeRedrawn[columnIndex] = true;

            if (applyToBackground) {
                characters[columnIndex].setBackgroundColor(color);
            } else {
                characters[columnIndex].setForegroundColor(color);
            }

            color = ColorFunctions.tint(color, tintFactor);
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
     *
     * @throws NullPointerException
     *         If the radio is null.
     */
    public void enableBlinkEffect(final short millsBetweenBlinks, final Radio<String> radio) {
        Objects.requireNonNull(radio);

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
     * @param range
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     *
     * @throws NullPointerException
     *         If the radio or range is null.
     */
    public void enableBlinkEffect(final short millsBetweenBlinks, final Radio<String> radio, final IntRange range) {
        Objects.requireNonNull(radio);
        Objects.requireNonNull(range);

        if (isRangeValid(range) == false) {
            range.clampValuesToRange(0, characters.length);
        }

        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            charactersToBeRedrawn[columnIndex] = true;
            characters[columnIndex].enableBlinkEffect(millsBetweenBlinks, radio);
        }
    }


    /**
     * Resumes the blink effect for every character in the specified range.
     *
     * @param range
     *         The x-axis (column) coordinates of the characters to begin/end the resume between.
     *         Includes the first index and excludes the last index.
     *
     * @throws NullPointerException
     *         If the range is null.
     */
    public void resumeBlinkEffect(final IntRange range) {
        Objects.requireNonNull(range);

        if (isRangeValid(range) == false) {
            range.clampValuesToRange(0, characters.length);
        }

        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            characters[columnIndex].resumeBlinkEffect();
        }
    }

    /**
     * Pauses the blink effect for every character in the specified range.
     *
     * @param range
     *         The x-axis (column) coordinates of the characters to begin/end the pause between.
     *         Includes the first index and excludes the last index.
     *
     * @throws NullPointerException
     *         If the range is null.
     */
    public void pauseBlinkEffect(final IntRange range) {
        Objects.requireNonNull(range);

        if (isRangeValid(range) == false) {
            range.clampValuesToRange(0, characters.length);
        }

        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            characters[columnIndex].pauseBlinkEffect();
        }
    }

    /**
     * Disables the blink effect for every character in the specified range.
     *
     * @param range
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     *
     * @throws NullPointerException
     *         If the range is null.
     */
    public void disableBlinkEffect(final IntRange range) {
        Objects.requireNonNull(range);

        if (isRangeValid(range) == false) {
            range.clampValuesToRange(0, characters.length);
        }

        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();

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
     * @param range
     *         The x-axis (column) coordinates of the characters to begin/end the inversion between.
     *         Includes the first index and excludes the last index.
     *
     * @throws NullPointerException
     *         If the range is null.
     */
    public void invertColors(final IntRange range) {
        Objects.requireNonNull(range);

        if (isRangeValid(range) == false) {
            range.clampValuesToRange(0, characters.length);
        }

        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            charactersToBeRedrawn[columnIndex] = true;
            characters[columnIndex].invertColors();
        }
    }

    /**
     * Tints the either the foreground or background color, of every character, by some factor,
     * where a higher factor results in a lighter tint.
     *
     * @param tintFactor
     *        The factor.
     *
     *        Values should range from 0.0 to 1.0.
     *
     * @param applyToBackground
     *         Whether or not to apply the tint to the background or foreground of the characters.
     */
    public void tintColors(final double tintFactor, final boolean applyToBackground) {
        tintColors(new IntRange(0, characters.length), tintFactor, applyToBackground);
    }

    /**
     * Tints either the foreground or background  color, of every character in the specified
     * range, by some factor, where a higher factor results in a lighter tint.
     *
     * @param range
     *         The x-axis (column) coordinates of the characters to begin/end the tint between.
     *         Includes the first index and excludes the last index.
     *
     * @param tintFactor
     *        The factor.
     *
     *        Values should range from 0.0 to 1.0.
     *
     * @param applyToBackground
     *         Whether or not to apply the tint to the background or foreground of the characters.
     *
     * @throws NullPointerException
     *         If the range is null.
     */
    public void tintColors(final IntRange range, final double tintFactor, final boolean applyToBackground) {
        Objects.requireNonNull(range);

        if (isRangeValid(range) == false) {
            range.clampValuesToRange(0, characters.length);
        }

        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            charactersToBeRedrawn[columnIndex] = true;

            if (applyToBackground) {
                characters[columnIndex].tintBackgroundColor(tintFactor);
            } else {
                characters[columnIndex].tintForegroundColor(tintFactor);
            }
        }
    }

    /**
     * Tints the background and foreground color, of every character, by some factor, where a higher
     * factor results in a lighter tint.
     *
     * @param tintFactor
     *        The factor.
     *
     *        Values should range from 0.0 to 1.0.
     */
    public void tintBackgroundAndForegroundColors(final double tintFactor) {
        final IntRange rangeIndices = new IntRange(0, characters.length);
        tintColors(rangeIndices, tintFactor, false);
        tintColors(rangeIndices, tintFactor, true);
    }

    /**
     * Tints the background and foreground color, of every character in the specified range, by some
     * factor, where a higher factor results in a lighter tint.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the tint between.
     *         Includes the first index and excludes the last index.
     *
     * @param tintFactor
     *        The factor.
     *
     *        Values should range from 0.0 to 1.0.
     */
    public void tintBackgroundAndForegroundColors(final IntRange rangeIndices, final double tintFactor) {
        tintColors(rangeIndices, tintFactor, false);
        tintColors(rangeIndices, tintFactor, true);
    }

    /**
     * Shades either the foreground or background  color, of every character, by some factor, where
     * a higher factor results in a darker shade.
     *
     * @param shadeFactor
     *        The factor.
     *
     *        Values should range from 0.0 to 1.0.
     *
     * @param applyToBackground
     *         Whether or not to apply the shade to the background or foreground of the characters.
     */
    public void shadeColors(final double shadeFactor, final boolean applyToBackground) {
        shadeColors(new IntRange(0, characters.length), shadeFactor, applyToBackground);
    }

    /**
     * Shades either the foreground or background  color, of every character in the specified range,
     * by some factor, where a higher factor results in a darker shade.
     *
     * @param range
     *         The x-axis (column) coordinates of the characters to begin/end the shade between.
     *         Includes the first index and excludes the last index.
     *
     * @param tintFactor
     *        The factor.
     *
     *        Values should range from 0.0 to 1.0.
     *
     * @param applyToBackground
     *         Whether or not to apply the shade to the background or foreground of the characters.
     *
     * @throws NullPointerException
     *         If the range is null.
     */
    public void shadeColors(final IntRange range, final double tintFactor, final boolean applyToBackground) {
        Objects.requireNonNull(range);

        if (isRangeValid(range) == false) {
            range.clampValuesToRange(0, characters.length);
        }

        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            charactersToBeRedrawn[columnIndex] = true;

            if (applyToBackground) {
                characters[columnIndex].shadeBackgroundColor(tintFactor);
            } else {
                characters[columnIndex].shadeForegroundColor(tintFactor);
            }
        }
    }

    /**
     * Shades the background and foreground color, of every character, by some factor, where a higher
     * factor results in a darker shade.
     *
     * @param shadeFactor
     *        The factor.
     *
     *        Values should range from 0.0 to 1.0.
     */
    public void shadeBackgroundAndForegroundColors(final double shadeFactor) {
        final IntRange rangeIndices = new IntRange(0, characters.length);
        shadeColors(rangeIndices, shadeFactor, false);
        shadeColors(rangeIndices, shadeFactor, true);
    }

    /**
     * Shades the background and foreground color, of every character in the specified range, by some
     * factor, where a higher factor results in a darker shade.
     *
     * @param rangeIndices
     *         The x-axis (column) coordinates of the characters to begin/end the shade between.
     *         Includes the first index and excludes the last index.
     *
     * @param shadeFactor
     *        The factor.
     *
     *        Values should range from 0.0 to 1.0.
     */
    public void shadeBackgroundAndForegroundColors(final IntRange rangeIndices, final double shadeFactor) {
        shadeColors(rangeIndices, shadeFactor, false);
        shadeColors(rangeIndices, shadeFactor, true);
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
     * @param range
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     *
     * @throws NullPointerException
     *         If the color or range is null.
     */
    public void setBackgroundColor(final Color color, final IntRange range) {
        Objects.requireNonNull(color);
        Objects.requireNonNull(range);

        if (isRangeValid(range) == false) {
            range.clampValuesToRange(0, characters.length);
        }

        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();

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
     * @param range
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     *
     * @throws NullPointerException
     *         If the color or range is null.
     */
    public void setForegroundColor(final Color color, final IntRange range) {
        Objects.requireNonNull(color);
        Objects.requireNonNull(range);

        if (isRangeValid(range) == false) {
            range.clampValuesToRange(0, characters.length);
        }

        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();

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
     * @param range
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     *
     * @throws NullPointerException
     *         If the background/foreground color or range is null.
     */
    public void setBackgroundAndForegroundColor(final Color background, final Color foreground, final IntRange range) {
        Objects.requireNonNull(background);
        Objects.requireNonNull(foreground);
        Objects.requireNonNull(range);

        if (isRangeValid(range) == false) {
            range.clampValuesToRange(0, characters.length);
        }

        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();

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
     * @param range
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     *
     * @throws NullPointerException
     *         If the range is null.
     */
    public void flipCharactersHorizontally(final IntRange range) {
        Objects.requireNonNull(range);

        if (isRangeValid(range) == false) {
            range.clampValuesToRange(0, characters.length);
        }

        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();

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
     * @param range
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     *
     * @throws NullPointerException
     *         If the range is null.
     */
    public void unFlipCharactersHorizontally(final IntRange range) {
        Objects.requireNonNull(range);

        if (isRangeValid(range) == false) {
            range.clampValuesToRange(0, characters.length);
        }

        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();

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
     * @param range
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     *
     * @throws NullPointerException
     *         If the range is null.
     */
    public void flipCharactersVertically(final IntRange range) {
        Objects.requireNonNull(range);

        if (isRangeValid(range) == false) {
            range.clampValuesToRange(0, characters.length);
        }

        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();

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
     * @param range
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     *
     * @throws NullPointerException
     *         If the range is null.
     */
    public void unFlipCharactersVertically(final IntRange range) {
        Objects.requireNonNull(range);

        if (isRangeValid(range) == false) {
            range.clampValuesToRange(0, characters.length);
        }

        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();

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
     * @param range
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     *
     * @throws NullPointerException
     *         If the range is null.
     */
    public void underlineCharacters(final IntRange range) {
        Objects.requireNonNull(range);

        if (isRangeValid(range) == false) {
            range.clampValuesToRange(0, characters.length);
        }

        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();

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
     * @param range
     *         The x-axis (column) coordinates of the characters to begin/end the change between.
     *         Includes the first index and excludes the last index.
     *
     * @throws NullPointerException
     *         If the range is null.
     */
    public void unUnderlineCharacters(final IntRange range) {
        Objects.requireNonNull(range);

        if (isRangeValid(range) == false) {
            range.clampValuesToRange(0, characters.length);
        }

        final int beginIndex = range.getBegin();
        final int endIndex = range.getEnd();

        for (int columnIndex = beginIndex ; columnIndex < endIndex ; columnIndex++) {
            if (characters[columnIndex].isUnderlined() == true) {
                charactersToBeRedrawn[columnIndex] = true;
                characters[columnIndex].setUnderlined(false);
            }
        }
    }
}