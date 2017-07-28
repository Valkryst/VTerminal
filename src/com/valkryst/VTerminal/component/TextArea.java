package com.valkryst.VTerminal.component;


import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.component.TextAreaBuilder;
import lombok.Getter;
import lombok.Setter;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TextArea extends Component {
    /** The foreground color of the caret. */
    @Getter @Setter private Color caretForegroundColor;
    /** The background color of the caret. */
    @Getter @Setter private Color caretBackgroundColor;

    /** The foreground color of non-caret characters. */
    @Getter @Setter private Color foregroundColor;
    /** The background color of non-caret characters. */
    @Getter @Setter private Color backgroundColor;

    /** Whether or not the HOME key can be used to move the caret to the first index of the field. */
    @Getter @Setter private boolean homeKeyEnabled;
    /** Whether or not the END key can be used to move the caret to the last index of the field. */
    @Getter @Setter private boolean endKeyEnabled;
    /** Whether or not the DELETE key can be used to erase the character that the caret is on. */
    @Getter @Setter private boolean deleteKeyEnabled;
    /** Whether or not the LEFT ARROW key can be used to move the caret one index to the left. */
    @Getter @Setter private boolean leftArrowKeyEnabled;
    /** Whether or not the RIGHT ARROW key can be used to move the caret one index to the right. */
    @Getter @Setter private boolean rightArrowKeyEnabled;
    /** Whether or not the UP ARROW key can be used to move the caret one index up. */
    @Getter @Setter private boolean upArrowKeyEnabled;
    /** Whether or not the DOWN ARROW key can be used to move the caret one index up. */
    @Getter @Setter private boolean downArrowKeyEnabled;
    /** Whether or not the ENTER key can be used to advance the caret to the first position of the next line. */
    @Getter @Setter private boolean enterKeyEnabled;
    /** Whether or not the BACK SPACE key can be used to erase the character before the caret and move the caret backwards. */
    @Getter @Setter private boolean backSpaceKeyEnabled;

    /** The current position of the visual caret on the x-axis. */
    @Getter private int x_index_caret_visual = 0;
    /** The current position of the visual caret on the y-axis. */
    @Getter private int y_index_caret_visual = 0;

    /** The current position of the caret, on the x-axis, in the enteredText array. */
    @Getter private int x_index_caret_actual = 0;
    /** The current position of the caret, on the y-axis, in the enteredText array. */
    @Getter private int y_index_caret_actual = 0;

    /** The maximum number of characters that the field can contain along the x-axis. */
    @Getter private int maxHorizontalCharacters;
    /** The maximum number of characters that the field can contain along the y-axis. */
    @Getter private int maxVerticalCharacters;

    /** The text entered by the user. */
    @Getter private char[][] enteredText;

    /** The pattern used to determine which typed characters can be entered into the field. */
    @Getter @Setter private Pattern allowedCharacterPattern;

    /**
     * Constructs a new AsciiTextField.
     *
     * @param builder
     *         The builder to use.
     *
     * @throws NullPointerException
     *         If the builder is null.
     */
    public TextArea(final TextAreaBuilder builder) {
        super(builder.getColumnIndex(), builder.getRowIndex(), builder.getWidth(), builder.getHeight());

        Objects.requireNonNull(builder);

        super.setRadio(builder.getRadio());

        caretForegroundColor = builder.getCaretForegroundColor();
        caretBackgroundColor = builder.getCaretBackgroundColor();

        foregroundColor = builder.getForegroundColor();
        backgroundColor = builder.getBackgroundColor();

        homeKeyEnabled = builder.isHomeKeyEnabled();
        endKeyEnabled = builder.isEndKeyEnabled();
        deleteKeyEnabled = builder.isDeleteKeyEnabled();
        leftArrowKeyEnabled = builder.isLeftArrowKeyEnabled();
        rightArrowKeyEnabled = builder.isRightArrowKeyEnabled();
        upArrowKeyEnabled = builder.isUpArrowKeyEnabled();
        downArrowKeyEnabled = builder.isDownArrowKeyEnabled();
        enterKeyEnabled = builder.isEnterKeyEnabled();
        backSpaceKeyEnabled = builder.isBackSpaceKeyEnabled();

        maxHorizontalCharacters = builder.getMaxHorizontalCharacters();
        maxVerticalCharacters = builder.getMaxVerticalCharacters();

        enteredText = new char[maxVerticalCharacters][maxHorizontalCharacters];
        clearText();

        allowedCharacterPattern = builder.getAllowedCharacterPattern();

        // Set the area's initial colors:
        for (final AsciiString string : super.getStrings()) {
            string.setBackgroundColor(backgroundColor);
            string.setForegroundColor(foregroundColor);
        }

        // Set initial caret position:
        changeVisualCaretPosition(x_index_caret_visual, y_index_caret_visual);
        changeActualCaretPosition(x_index_caret_actual, y_index_caret_actual);
    }

    @Override
    public boolean equals(final Object otherObj) {
        if (otherObj instanceof TextArea == false) {
            return false;
        }

        if (otherObj == this) {
            return true;
        }

        final TextArea otherArea = (TextArea) otherObj;
        boolean isEqual = super.equals(otherObj);
        isEqual &= Objects.equals(caretForegroundColor, otherArea.getCaretForegroundColor());
        isEqual &= Objects.equals(caretBackgroundColor, otherArea.getCaretBackgroundColor());
        isEqual &= Objects.equals(foregroundColor, otherArea.getForegroundColor());
        isEqual &= Objects.equals(backgroundColor, otherArea.getBackgroundColor());
        isEqual &= Objects.equals(homeKeyEnabled, otherArea.isHomeKeyEnabled());
        isEqual &= Objects.equals(endKeyEnabled, otherArea.isEndKeyEnabled());
        isEqual &= Objects.equals(deleteKeyEnabled, otherArea.isDeleteKeyEnabled());
        isEqual &= Objects.equals(leftArrowKeyEnabled, otherArea.isLeftArrowKeyEnabled());
        isEqual &= Objects.equals(rightArrowKeyEnabled, otherArea.isRightArrowKeyEnabled());
        isEqual &= Objects.equals(enterKeyEnabled, otherArea.isEnterKeyEnabled());
        isEqual &= Objects.equals(backSpaceKeyEnabled, otherArea.isBackSpaceKeyEnabled());
        isEqual &= Objects.equals(x_index_caret_actual, otherArea.getX_index_caret_actual());
        isEqual &= Objects.equals(y_index_caret_actual, otherArea.getY_index_caret_actual());
        isEqual &= Objects.equals(x_index_caret_visual, otherArea.getX_index_caret_visual());
        isEqual &= Objects.equals(y_index_caret_visual, otherArea.getY_index_caret_visual());
        isEqual &= Objects.equals(maxHorizontalCharacters, otherArea.getMaxHorizontalCharacters());
        isEqual &= Objects.equals(maxVerticalCharacters, otherArea.getMaxVerticalCharacters());
        isEqual &= Objects.equals(allowedCharacterPattern, otherArea.getAllowedCharacterPattern());
        return isEqual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), caretForegroundColor, caretBackgroundColor, foregroundColor,
                            backgroundColor, homeKeyEnabled, endKeyEnabled, deleteKeyEnabled, leftArrowKeyEnabled,
                            rightArrowKeyEnabled, enterKeyEnabled, backSpaceKeyEnabled, x_index_caret_actual,
                            y_index_caret_actual, x_index_caret_visual, y_index_caret_visual, maxHorizontalCharacters,
                            maxVerticalCharacters, allowedCharacterPattern);
    }

    @Override
    public void registerEventHandlers(final Panel panel) {
        Objects.requireNonNull(panel);

        super.registerEventHandlers(panel);

        final int width = super.getWidth();

        panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(final KeyEvent e) {
                if (isFocused()) {
                    final char character = e.getKeyChar();
                    final Matcher matcher = allowedCharacterPattern.matcher(character + "");

                    if (matcher.matches()) {
                        changeVisualCharacter(x_index_caret_visual, y_index_caret_visual, character);
                        changeActualCharacter(x_index_caret_actual, y_index_caret_actual, character);
                        changeVisualCaretPosition(x_index_caret_visual + 1, y_index_caret_visual);
                        changeActualCaretPosition(x_index_caret_actual + 1, y_index_caret_actual);

                        // Move caret to beginning of next line:
                        if (x_index_caret_actual == maxHorizontalCharacters - 1) {
                            final AsciiCharacter currentChar = TextArea.super.getStrings()[y_index_caret_visual].getCharacters()[x_index_caret_visual];

                            if (currentChar.getCharacter() != ' ') {
                                boolean canMoveDown = rightArrowKeyEnabled;
                                canMoveDown &= y_index_caret_actual < maxVerticalCharacters - 1;

                                if (canMoveDown) {
                                    changeVisualCaretPosition(0, y_index_caret_visual + 1);
                                    changeActualCaretPosition(0, y_index_caret_actual + 1);
                                }
                            }
                        }

                        updateDisplayedCharacters();
                        transmitDraw();
                    }
                }
            }

            @Override
            public void keyPressed(final KeyEvent e) {

            }

            @Override
            public void keyReleased(final KeyEvent e) {
                if (isFocused()) {
                    int keyCode = e.getKeyCode();

                    switch (keyCode) {
                        // Move the caret to the first position on the left:
                        case KeyEvent.VK_HOME: {
                            if (homeKeyEnabled) {
                                changeVisualCaretPosition(0, y_index_caret_visual);
                                changeActualCaretPosition(0, y_index_caret_actual);
                                updateDisplayedCharacters();
                                transmitDraw();
                            }
                            break;
                        }

                        // Move the caret to the last position on the right:
                        case KeyEvent.VK_END: {
                            if (endKeyEnabled) {
                                changeVisualCaretPosition(width, y_index_caret_visual);
                                changeActualCaretPosition(maxHorizontalCharacters, y_index_caret_actual);
                                updateDisplayedCharacters();
                                transmitDraw();
                            }
                            break;
                        }

                        // Erase the current character:
                        case KeyEvent.VK_DELETE: {
                            if (deleteKeyEnabled) {
                                changeVisualCharacter(x_index_caret_visual, y_index_caret_visual, ' ');
                                changeActualCharacter(x_index_caret_actual, y_index_caret_actual,  ' ');
                                updateDisplayedCharacters();
                                transmitDraw();
                            }
                            break;
                        }

                        // Move the caret one position to the left:
                        case KeyEvent.VK_LEFT: {
                            boolean canMoveLeft = leftArrowKeyEnabled;
                            canMoveLeft &= x_index_caret_visual > 0;

                            boolean canMoveUp = leftArrowKeyEnabled;
                            canMoveUp &= x_index_caret_visual == 0;
                            canMoveUp &= y_index_caret_actual > 0;

                            if (canMoveLeft) {
                                changeVisualCaretPosition(x_index_caret_visual - 1, y_index_caret_visual);
                                changeActualCaretPosition(x_index_caret_actual - 1, y_index_caret_actual);
                            }

                            if (canMoveUp) {
                                changeVisualCaretPosition(maxHorizontalCharacters - 1, y_index_caret_visual - 1);
                                changeActualCaretPosition(maxHorizontalCharacters - 1, y_index_caret_actual - 1);
                            }

                            updateDisplayedCharacters();
                            transmitDraw();
                            break;
                        }

                        // Move the caret one position to the right:
                        case KeyEvent.VK_RIGHT: {
                            boolean canMoveRight = rightArrowKeyEnabled;
                            canMoveRight &= x_index_caret_visual < maxHorizontalCharacters - 1;

                            boolean canMoveDown = rightArrowKeyEnabled;
                            canMoveDown &= x_index_caret_visual == maxHorizontalCharacters - 1;
                            canMoveDown &= y_index_caret_actual < maxVerticalCharacters - 1;

                            if (canMoveRight) {
                                changeVisualCaretPosition(x_index_caret_visual + 1, y_index_caret_visual);
                                changeActualCaretPosition(x_index_caret_actual + 1, y_index_caret_actual);
                            }

                            if (canMoveDown) {
                                changeVisualCaretPosition(0, y_index_caret_visual + 1);
                                changeActualCaretPosition(0, y_index_caret_actual + 1);
                            }

                            updateDisplayedCharacters();
                            transmitDraw();
                            break;
                        }

                        // Move the caret one position up:
                        case KeyEvent.VK_UP: {
                            boolean canWork = upArrowKeyEnabled;
                            canWork &= y_index_caret_actual > 0;

                            if (canWork) {
                                changeVisualCaretPosition(x_index_caret_visual, y_index_caret_visual - 1);
                                changeActualCaretPosition(x_index_caret_actual, y_index_caret_actual - 1);
                                updateDisplayedCharacters();
                                transmitDraw();
                            }
                            break;
                        }

                        // Move the caret one position down:
                        case KeyEvent.VK_DOWN: {
                            boolean canWork = downArrowKeyEnabled;
                            canWork &= y_index_caret_visual < maxVerticalCharacters;

                            if (canWork) {
                                changeVisualCaretPosition(x_index_caret_visual, y_index_caret_visual + 1);
                                changeActualCaretPosition(x_index_caret_actual, y_index_caret_actual + 1);
                                updateDisplayedCharacters();
                                transmitDraw();
                            }
                            break;
                        }

                        // Move the caret to the first position of the next row:
                        case KeyEvent.VK_ENTER: {
                            boolean canWork = enterKeyEnabled;
                            canWork &= y_index_caret_actual < maxVerticalCharacters - 1;

                            if (canWork) {
                                changeVisualCaretPosition(0, y_index_caret_visual + 1);
                                changeActualCaretPosition(0, y_index_caret_actual + 1);
                                updateDisplayedCharacters();
                                transmitDraw();
                            }
                            break;
                        }

                        // Delete the character to the left of the caret, then move the caret one position left:
                        case KeyEvent.VK_BACK_SPACE: {
                            if (! backSpaceKeyEnabled) {
                                break;
                            }

                            final boolean caretAtStartOfLine = x_index_caret_visual == 0;
                            final boolean caretAtEndOfLine = x_index_caret_visual == maxHorizontalCharacters - 1;

                            if (caretAtStartOfLine || caretAtEndOfLine) {
                                final AsciiCharacter currentChar = TextArea.super.getStrings()[y_index_caret_visual].getCharacters()[x_index_caret_visual];

                                if (currentChar.getCharacter() != ' ') {
                                    changeVisualCharacter(x_index_caret_visual, y_index_caret_visual, ' ');
                                    changeActualCharacter(x_index_caret_actual, y_index_caret_actual, ' ');

                                    if (caretAtEndOfLine) {
                                        break;
                                    }
                                }

                            }

                            if (caretAtStartOfLine) {
                                if (y_index_caret_actual > 0) {
                                    changeVisualCaretPosition(maxHorizontalCharacters - 1, y_index_caret_visual - 1);
                                    changeActualCaretPosition(maxHorizontalCharacters - 1, y_index_caret_actual - 1);
                                }

                                break;
                            }

                            changeVisualCharacter(x_index_caret_visual - 1, y_index_caret_visual, ' ');
                            changeActualCharacter(x_index_caret_actual - 1, y_index_caret_actual,  ' ');

                            changeVisualCaretPosition(x_index_caret_visual - 1, y_index_caret_visual);
                            changeActualCaretPosition(x_index_caret_actual - 1, y_index_caret_actual);
                            updateDisplayedCharacters();
                            transmitDraw();
                            break;
                        }
                    }
                }
            }
        });
    }

    /**
     * Moves the visual caret to the specified location.
     *
     * @param newColumnIndex
     *         The new column index for the caret.
     *
     * @param newRowIndex
     *         The new row index for the caret.
     */
    private void changeVisualCaretPosition(int newColumnIndex, int newRowIndex) {
        if (newColumnIndex >= super.getWidth()) {
            newColumnIndex = super.getWidth() - 1;
        }

        if (newColumnIndex < 0) {
            newColumnIndex = 0;
        }

        if (newRowIndex >= super.getHeight()) {
            newRowIndex = super.getHeight() - 1;
        }

        if (newRowIndex < 0) {
            newRowIndex = 0;
        }

        final Radio<String> radio = super.getRadio();

        // Reset current position's fore/background:
        AsciiCharacter[] characters = super.getString(y_index_caret_visual).getCharacters();
        characters[x_index_caret_visual].setForegroundColor(foregroundColor);
        characters[x_index_caret_visual].setBackgroundColor(backgroundColor);

        if (radio != null) {
            // Reset current position's blink/hidden state:
            characters[x_index_caret_visual].disableBlinkEffect();
            characters[x_index_caret_visual].setHidden(false);
        }

        // Set new position's fore/background:
        characters = super.getString(newRowIndex).getCharacters();
        characters[newColumnIndex].setForegroundColor(caretForegroundColor);
        characters[newColumnIndex].setBackgroundColor(caretBackgroundColor);

        if (radio != null) {
            // Set new position's blink state:
            characters[newColumnIndex].enableBlinkEffect((short) 1000, radio);
        }

        x_index_caret_visual = newColumnIndex;
        y_index_caret_visual = newRowIndex;
    }

    /**
     * Moves the actual caret to the specified location.
     *
     * @param newColumnIndex
     *         The new column index for the caret.
     *
     * @param newRowIndex
     *         The new row index for the caret.
     */
    private void changeActualCaretPosition(int newColumnIndex, int newRowIndex) {
        if (newColumnIndex >= maxHorizontalCharacters) {
            newColumnIndex = maxHorizontalCharacters - 1;
        }

        if (newColumnIndex < 0) {
            newColumnIndex = 0;
        }

        if (newRowIndex >= maxVerticalCharacters) {
            newRowIndex = maxVerticalCharacters - 1;
        }

        if (newRowIndex < 0) {
            newRowIndex = 0;
        }

        x_index_caret_actual = newColumnIndex;
        y_index_caret_actual = newRowIndex;
    }

    /**
     * Changes the visual character at the specified location.
     *
     * @param columnIndex
     *         The column index.
     *
     * @param rowIndex
     *         The row index.
     *
     * @param character
     *         The new character.
     */
    private void changeVisualCharacter(final int columnIndex, final int rowIndex, final char character) {
        if (columnIndex < 0 || columnIndex > maxHorizontalCharacters) {
            return;
        }

        if (rowIndex < 0 || rowIndex > maxVerticalCharacters) {
            return;
        }

        final AsciiCharacter[] characters = super.getString(rowIndex).getCharacters();
        characters[columnIndex].setCharacter(character);
    }

    /**
     * Changes the actual character at the specified index.
     *
     * @param columnIndex
     *         The column index.
     *
     * @param rowIndex
     *         The row index.
     *
     * @param character
     *        The new character.
     */
    private void changeActualCharacter(final int columnIndex, final int rowIndex, final char character) {
        if (columnIndex < 0 || columnIndex > maxHorizontalCharacters) {
            return;
        }

        if (rowIndex < 0 || rowIndex > maxVerticalCharacters) {
            return;
        }

        enteredText[rowIndex][columnIndex] = character;
    }

    private void updateDisplayedCharacters() {
        final int xDifference = x_index_caret_actual - x_index_caret_visual;
        final int yDifference = y_index_caret_actual - y_index_caret_visual;

        for (int x = xDifference ; x < super.getWidth() + xDifference ; x++) {
            for (int y = yDifference ; y < super.getHeight() + yDifference ; y++) {
                final AsciiString string = super.getString(y - yDifference);

                final AsciiCharacter[] characters = string.getCharacters();
                characters[x - xDifference].setCharacter(enteredText[y][x]);
            }
        }
    }

    /**
     * Sets the text contained within a row of the area.
     *
     * @param rowIndex
     *        The row index.
     *
     * @param text
     *        The text.
     */
    public void setText(final int rowIndex, String text) {
        if (text == null || text.isEmpty()) {
            clearText(rowIndex);
            return;
        }

        if (text.length() > maxHorizontalCharacters) {
            text = text.substring(0, maxHorizontalCharacters);
        }

        System.arraycopy(text.toCharArray(), 0, enteredText[rowIndex], 0, text.length());
    }

    /**
     * Sets the text contained within the area.
     *
     * Clears the field before setting.
     *
     * @param text
     *        The list of text.
     */
    public void setText(final List<String> text) {
        clearText();

        for (int i = 0 ; i < text.size() && i < maxVerticalCharacters ; i++) {
            setText(i, text.get(i));
        }
    }

    /** @return The text contained within the area. */
    public String getText() {
        final StringBuilder sb = new StringBuilder();

        for (int i = 0 ; i < enteredText.length ; i++) {
            for (final char c : enteredText[i]) {
                sb.append(c);
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    /**
     * Clears text from a row.
     *
     * @param rowIndex
     *        The row index,
     */
    public void clearText(final int rowIndex) {
        Arrays.fill(enteredText[rowIndex], ' ');

        if (y_index_caret_actual == y_index_caret_visual && y_index_caret_actual == rowIndex) {
            for (final AsciiCharacter character : super.getString(rowIndex).getCharacters()) {
                character.setCharacter(' ');
            }
        }
    }

    /** Clears all text from the field. */
    public void clearText() {
        for (int i = 0 ; i < enteredText.length ; i++) {
            Arrays.fill(enteredText[i], ' ');
        }

        for (final AsciiString string : super.getStrings()) {
            for (final AsciiCharacter character : string.getCharacters()) {
                character.setCharacter(' ');
            }
        }
    }
}
