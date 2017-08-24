package com.valkryst.VTerminal.component;


import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.component.TextAreaBuilder;
import com.valkryst.VTerminal.font.Font;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ToString
public class TextArea extends Component {
    /** The foreground color of the caret. */
    @Getter @Setter private Color caretForegroundColor;
    /** The background color of the caret. */
    @Getter @Setter private Color caretBackgroundColor;

    /** The foreground color of non-caret characters. */
    @Getter @Setter private Color foregroundColor;
    /** The background color of non-caret characters. */
    @Getter @Setter private Color backgroundColor;

    /** Whether or not the TextArea can be edited. */
    @Getter private boolean isEditable;

    /** Whether or not the HOME key can be used to move the caret to the first index of the current line. */
    @Getter @Setter private boolean homeKeyEnabled;
    /** Whether or not the END key can be used to move the caret to the last index of the current line. */
    @Getter @Setter private boolean endKeyEnabled;
    /** Whether or not the PAGE UP key can be used to move the caret to the first row. */
    @Getter @Setter private boolean pageUpKeyEnabled;
    /** Whether or nor the PAGE DOWN key can be used to move the caret to the last row. */
    @Getter @Setter private boolean pageDownKeyEnabled;
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
    /** Whether or not the TAB key can be used to indent by some number of spaces. */
    @Getter @Setter private boolean tabKeyEnabled;

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

    /** The amount of spaces to insert when the TAB key is pressed. */
    @Getter private int tabSize;

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
    public TextArea(final @NonNull TextAreaBuilder builder) {
        super(builder.getColumnIndex(), builder.getRowIndex(), builder.getWidth(), builder.getHeight());

        super.setRadio(builder.getRadio());

        caretForegroundColor = builder.getCaretForegroundColor();
        caretBackgroundColor = builder.getCaretBackgroundColor();

        foregroundColor = builder.getForegroundColor();
        backgroundColor = builder.getBackgroundColor();

        isEditable = builder.isEditable();

        homeKeyEnabled = builder.isHomeKeyEnabled();
        endKeyEnabled = builder.isEndKeyEnabled();
        pageUpKeyEnabled = builder.isPageUpKeyEnabled();
        pageDownKeyEnabled = builder.isPageDownKeyEnabled();
        deleteKeyEnabled = builder.isDeleteKeyEnabled();
        leftArrowKeyEnabled = builder.isLeftArrowKeyEnabled();
        rightArrowKeyEnabled = builder.isRightArrowKeyEnabled();
        upArrowKeyEnabled = builder.isUpArrowKeyEnabled();
        downArrowKeyEnabled = builder.isDownArrowKeyEnabled();
        enterKeyEnabled = builder.isEnterKeyEnabled();
        backSpaceKeyEnabled = builder.isBackSpaceKeyEnabled();
        tabKeyEnabled = builder.isTabKeyEnabled();

        maxHorizontalCharacters = builder.getMaxHorizontalCharacters();
        maxVerticalCharacters = builder.getMaxVerticalCharacters();

        tabSize = builder.getTabSize();

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
    public void createEventListeners(final @NonNull Panel panel) {
        // Intentionally not calling the super func because it's functionality
        // was added into the MouseListener of this method in order to allow
        // the user to move the caret with the mouse.
        // super.createEventListeners(panel);

        final Font font = panel.getImageCache().getFont();
        final int fontWidth = font.getWidth();
        final int fontHeight = font.getHeight();

        final MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (isEditable == false) {
                    return;
                }

                if (e.getButton() == MouseEvent.BUTTON1) {
                    TextArea.super.isFocused = intersects(e, fontWidth, fontHeight);

                    if (TextArea.super.isFocused()) {
                        final int columnIndexInArea = (e.getX() / fontWidth) - TextArea.super.getColumnIndex();
                        final int rowIndexInArea = (e.getY() / fontHeight) - TextArea.super.getRowIndex();

                        int dx = columnIndexInArea - x_index_caret_visual;
                        int dy = rowIndexInArea - y_index_caret_visual;

                        while (dx != 0) {
                            if (dx > 0) {
                                moveCaretRight();
                                dx--;
                            } else {
                                moveCaretLeft();
                                dx++;
                            }
                        }

                        while (dy != 0) {
                            if (dy > 0) {
                                moveCaretDown();
                                dy--;
                            } else {
                                moveCaretUp();
                                dy++;
                            }
                        }

                        updateDisplayedCharacters();
                        transmitDraw();
                    }
                }
            }

            @Override
            public void mousePressed(final MouseEvent e) {}

            @Override
            public void mouseReleased(final MouseEvent e) {}

            @Override
            public void mouseEntered(final MouseEvent e) {}

            @Override
            public void mouseExited(final MouseEvent e) {}
        };

        final KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(final KeyEvent e) {
                if (isEditable == false) {
                    return;
                }
                if (isFocused()) {
                    final char character = e.getKeyChar();
                    final Matcher matcher = allowedCharacterPattern.matcher(character + "");

                    if (matcher.matches()) {
                        changeVisualCharacter(x_index_caret_visual, y_index_caret_visual, character);
                        changeActualCharacter(x_index_caret_actual, y_index_caret_actual, character);

                        final boolean caretAtEndOfLine = x_index_caret_actual == maxHorizontalCharacters - 1;

                        if (caretAtEndOfLine) {
                            if (y_index_caret_actual < maxVerticalCharacters - 1) {
                                moveCaretDown();
                                moveCaretToStartOfLine();
                            }
                        } else {
                            moveCaretRight();
                        }

                        updateDisplayedCharacters();
                        transmitDraw();
                    }
                }
            }

            @Override
            public void keyPressed(final KeyEvent e) {
                if (isEditable == false) {
                    return;
                }
                if (isFocused()) {
                    int keyCode = e.getKeyCode();

                    switch (keyCode) {
                        // Move the caret to the first position of the next row:
                        case KeyEvent.VK_ENTER: {
                            boolean canWork = enterKeyEnabled;
                            canWork &= y_index_caret_actual < maxVerticalCharacters - 1;

                            if (canWork) {
                                moveCaretDown();
                                moveCaretToStartOfLine();
                                updateDisplayedCharacters();
                                transmitDraw();
                            }
                            break;
                        }

                        // Delete the character to the left of the caret, then move the caret one position left:
                        case KeyEvent.VK_BACK_SPACE: {
                            if (!backSpaceKeyEnabled) {
                                break;
                            }

                            final boolean caretAtStartOfLine = x_index_caret_actual == 0;
                            final boolean caretAtEndOfLine = x_index_caret_actual == maxHorizontalCharacters - 1;

                            if (caretAtStartOfLine) {
                                if (y_index_caret_actual > 0) {
                                    moveCaretUp();
                                    moveCaretToEndOfLine();
                                }
                            } else if (caretAtEndOfLine) {
                                final AsciiCharacter currentChar = TextArea.super.getStrings()[y_index_caret_visual].getCharacters()[x_index_caret_visual];

                                if (currentChar.getCharacter() == ' ') {
                                    moveCaretLeft();
                                }
                            } else {
                                moveCaretLeft();
                            }

                            clearCurrentCell();
                            updateDisplayedCharacters();
                            transmitDraw();
                            break;
                        }
                    }
                }
            }

            @Override
            public void keyReleased(final KeyEvent e) {
                if (isEditable == false) {
                    return;
                }
                if (isFocused()) {
                    int keyCode = e.getKeyCode();

                    switch (keyCode) {
                        // Erase the current character:
                        case KeyEvent.VK_DELETE: {
                            if (deleteKeyEnabled) {
                                clearCurrentCell();
                                updateDisplayedCharacters();
                                transmitDraw();
                            }
                            break;
                        }

                        // Move the caret to the first position on the left:
                        case KeyEvent.VK_HOME: {
                            if (homeKeyEnabled) {
                                moveCaretToStartOfLine();
                                updateDisplayedCharacters();
                                transmitDraw();
                            }
                            break;
                        }

                        // Move the caret to the last position on the right:
                        case KeyEvent.VK_END: {
                            if (endKeyEnabled) {
                                moveCaretToEndOfLine();
                                updateDisplayedCharacters();
                                transmitDraw();
                            }
                            break;
                        }

                        // Move the caret to the first row:
                        case KeyEvent.VK_PAGE_UP: {
                            if (pageUpKeyEnabled) {
                                moveCaretToFirstLine();
                                updateDisplayedCharacters();
                                transmitDraw();
                            }
                            break;
                        }

                        // Move the caret to the last row:
                        case KeyEvent.VK_PAGE_DOWN: {
                            if (pageDownKeyEnabled) {
                                moveCaretToLastLine();
                                updateDisplayedCharacters();
                                transmitDraw();
                            }
                            break;
                        }

                        // Move the caret one position to the left:
                        case KeyEvent.VK_LEFT: {
                            if (leftArrowKeyEnabled) {
                                boolean moveToPreviousLine = x_index_caret_actual == 0;
                                moveToPreviousLine &= y_index_caret_actual > 0;

                                if (moveToPreviousLine) {
                                    moveCaretUp();
                                    moveCaretToEndOfLine();
                                } else {
                                    moveCaretLeft();
                                }

                                updateDisplayedCharacters();
                                transmitDraw();
                            }

                            break;
                        }

                        // Move the caret one position to the right:
                        case KeyEvent.VK_RIGHT: {
                            if (isRightArrowKeyEnabled()) {
                                boolean moveToNextLine = x_index_caret_actual == maxHorizontalCharacters - 1;
                                moveToNextLine &= y_index_caret_actual < maxVerticalCharacters - 1;

                                if (moveToNextLine) {
                                    moveCaretDown();
                                    moveCaretToStartOfLine();
                                } else {
                                    moveCaretRight();
                                }

                                updateDisplayedCharacters();
                                transmitDraw();
                            }

                            break;
                        }

                        // Move the caret one position up:
                        case KeyEvent.VK_UP: {
                            if (upArrowKeyEnabled) {
                                moveCaretUp();
                                updateDisplayedCharacters();
                                transmitDraw();
                            }
                            break;
                        }

                        // Move the caret one position down:
                        case KeyEvent.VK_DOWN: {
                            if (downArrowKeyEnabled) {
                                moveCaretDown();
                                updateDisplayedCharacters();
                                transmitDraw();
                            }
                            break;
                        }

                        case KeyEvent.VK_TAB: {
                            if (tabKeyEnabled) {
                                for (int i = 0 ; i < tabSize ; i++) {
                                    if (i < maxHorizontalCharacters - 1) {
                                        moveCaretRight();
                                    }
                                }
                            }

                            updateDisplayedCharacters();
                            transmitDraw();
                            break;
                        }
                    }
                }
            }
        };

        super.getEventListeners().add(keyListener);
        super.getEventListeners().add(mouseListener);
    }

    /** Moves the caret one cell up. */
    public void moveCaretUp() {
        if (y_index_caret_visual > 0) {
            changeVisualCaretPosition(x_index_caret_visual, y_index_caret_visual - 1);
        }

        if (y_index_caret_actual > 0) {
            changeActualCaretPosition(x_index_caret_actual, y_index_caret_actual - 1);
        }
    }

    /** Moves the caret one cell down. */
    public void moveCaretDown() {
        if (y_index_caret_visual < super.getHeight() - 1) {
            changeVisualCaretPosition(x_index_caret_visual, y_index_caret_visual + 1);
        }

        if (y_index_caret_actual < maxVerticalCharacters - 1) {
            changeActualCaretPosition(x_index_caret_actual, y_index_caret_actual + 1);
        }
    }

    /** Moves the caret one cell left. */
    public void moveCaretLeft() {
        if (x_index_caret_visual > 0) {
            changeVisualCaretPosition(x_index_caret_visual - 1, y_index_caret_visual);
        }

        if (x_index_caret_actual > 0) {
            changeActualCaretPosition(x_index_caret_actual - 1, y_index_caret_actual);
        }
    }

    /** Moves the caret one cell right. */
    public void moveCaretRight() {
        if (x_index_caret_visual < super.getWidth() - 1) {
            changeVisualCaretPosition(x_index_caret_visual + 1, y_index_caret_visual);
        }

        if (x_index_caret_actual < maxHorizontalCharacters - 1) {
            changeActualCaretPosition(x_index_caret_actual + 1, y_index_caret_actual);
        }
    }

    /** Moves the caret to the first line. Does not change the x-axis position of the caret. */
    public void moveCaretToFirstLine() {
        changeVisualCaretPosition(x_index_caret_visual, 0);
        changeActualCaretPosition(x_index_caret_actual, 0);
    }

    /** Moves the caret to the last line. Does not change the x-axis position of the caret. */
    public void moveCaretToLastLine() {
        changeVisualCaretPosition(x_index_caret_visual, super.getHeight() - 1);
        changeActualCaretPosition(x_index_caret_actual, maxVerticalCharacters - 1);
    }

    /** Moves the caret to the beginning of the current line. */
    public void moveCaretToStartOfLine() {
        changeVisualCaretPosition(0, y_index_caret_visual);
        changeActualCaretPosition(0, y_index_caret_actual);
    }

    /** Moves the caret to the end of the current line. */
    public void moveCaretToEndOfLine() {
        changeVisualCaretPosition(super.getWidth() - 1, y_index_caret_visual);
        changeActualCaretPosition(maxHorizontalCharacters - 1, y_index_caret_actual);
    }

    /** Deletes the character in the current cell. */
    public void clearCurrentCell() {
        changeVisualCharacter(x_index_caret_visual, y_index_caret_visual, ' ');
        changeActualCharacter(x_index_caret_actual, y_index_caret_actual, ' ');
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
    private void changeVisualCaretPosition(final int newColumnIndex, final int newRowIndex) {
        final Radio<String> radio = super.getRadio();

        // Reset current position's fore/background:
        AsciiCharacter[] characters = super.getString(y_index_caret_visual).getCharacters();
        characters[x_index_caret_visual].setForegroundColor(foregroundColor);
        characters[x_index_caret_visual].setBackgroundColor(backgroundColor);

        // Reset current position's blink/hidden state:
        characters[x_index_caret_visual].disableBlinkEffect();
        characters[x_index_caret_visual].setHidden(false);

        // Set new position's fore/background:
        if (isEditable) {
            characters = super.getString(newRowIndex).getCharacters();
            characters[newColumnIndex].setForegroundColor(caretForegroundColor);
            characters[newColumnIndex].setBackgroundColor(caretBackgroundColor);

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
    private void changeActualCaretPosition(final int newColumnIndex, final int newRowIndex) {
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

        for (int y = yDifference ; y < super.getHeight() + yDifference ; y++) {
            final AsciiString string = super.getString(y - yDifference);
            final AsciiCharacter[] characters = string.getCharacters();

            for (int x = xDifference ; x < super.getWidth() + xDifference ; x++) {
                characters[x - xDifference].setCharacter(enteredText[y][x]);
            }
        }
    }

    /**
     * Appends text to the first empty row of the area.
     *
     * If there are no empty rows, then the first row is discarded
     * and all rows are moved up, then the new text is appended
     * to the bottom row.
     *
     * @param text
     *        The new text.
     */
    public void appendText(final @NonNull String text) {
        final String[] textAreaContents = getText();

        for (int i = 0 ; i < textAreaContents.length ; i++) {
            if (textAreaContents[i].isEmpty()) {
                setText(i, text);
                return;
            }
        }

        for (int i = 0 ; i < textAreaContents.length - 1 ; i++) {
            setText(i, textAreaContents[i + 1]);
        }

        setText(textAreaContents.length - 1, text);

        updateDisplayedCharacters();
    }

    /**
     * Sets the text contained within a row of the area.
     *
     * @param rowIndex
     *        The row index.
     *
     * @param text
     *        The text.
     *
     * @throws NullPointerException
     *        If the text is null.
     */
    public void setText(final int rowIndex, @NonNull String text) {
        clearText(rowIndex);

        if (text.length() > maxHorizontalCharacters) {
            text = text.substring(0, maxHorizontalCharacters);
        }

        System.arraycopy(text.toCharArray(), 0, enteredText[rowIndex], 0, text.length());

        updateDisplayedCharacters();
    }

    /**
     * Sets the text contained within the area.
     *
     * Clears the field before setting.
     *
     * @param text
     *        The list of text.
     *
     * @throws NullPointerException
     *        If the text is null.
     */
    public void setText(final @NonNull List<String> text) {
        clearText();

        for (int i = 0 ; i < text.size() && i < maxVerticalCharacters ; i++) {
            setText(i, text.get(i));
        }
    }

    /** @return The text contained within the area. */
    public String[] getText() {
        final String[] strings = new String[super.getHeight()];

        String temp = "";

        for (int i = 0 ; i < super.getHeight() ; i++) {
            for (final char c : enteredText[i]) {
                temp += c;
            }

            strings[i] = temp;
            temp = "";
        }

        return strings;
    }

    /**
     * Clears text from a row.
     *
     * @param rowIndex
     *        The row index,
     *
     * @throws java.lang.IllegalArgumentException
     *        If the row index is below zero.
     *        If the row index exceeds the TextArea's height.
     */
    public void clearText(final int rowIndex) {
        if (rowIndex < 0) {
            throw new IllegalArgumentException("The row index cannot be below 0.");
        }

        if (rowIndex > super.getHeight()) {
            throw new IllegalArgumentException("The row index cannot be above " + super.getHeight() +".");
        }

        Arrays.fill(enteredText[rowIndex], ' ');

        if (y_index_caret_actual == y_index_caret_visual && y_index_caret_actual == rowIndex) {
            for (final AsciiCharacter character : super.getString(rowIndex).getCharacters()) {
                character.setCharacter(' ');
            }
        }

        updateDisplayedCharacters();
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

        updateDisplayedCharacters();
    }

    /**
     * Sets whether or not the area is editable.
     *
     * @param isEditable
     *        Whether or not the area is editable.
     */
    public void setEditable(final boolean isEditable) {
        this.isEditable = isEditable;
        changeVisualCaretPosition(x_index_caret_visual, y_index_caret_visual);
    }
}
