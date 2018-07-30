package com.valkryst.VTerminal.component;


import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.TileGrid;
import com.valkryst.VTerminal.builder.TextAreaBuilder;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.palette.ColorPalette;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
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
    @Getter @Setter private boolean editable;

    /** The current position of the caret. */
    @Getter private Point caretPosition = new Point(0, 0);

    /** The text entered by the user. */
    @Getter private char[][] enteredText;

    /**
     * The pattern used to determine which typed characters can be entered into
     * the field.
     */
    @Getter @Setter private Pattern allowedCharacterPattern;

    /** Whether the special listener code for the Enter key is enabled. */
    @Getter @Setter private boolean isEnterKeyEnabled;

    /** Whether the special listener code for the Backspace key is enabled. */
    @Getter @Setter private boolean isBackspaceKeyEnabled;

    /** Whether the special listener code for the Delete key is enabled. */
    @Getter @Setter private boolean isDeleteKeyEnabled;

    /** Whether the special listener code for the Home key is enabled. */
    @Getter @Setter private boolean isHomeKeyEnabled;

    /** Whether the special listener code for the End key is enabled. */
    @Getter @Setter private boolean isEndKeyEnabled;

    /** Whether the special listener code for the Page Up key is enabled. */
    @Getter @Setter private boolean isPageUpKeyEnabled;

    /** Whether the special listener code for the Page Down key is enabled. */
    @Getter @Setter private boolean isPageDownKeyEnabled;

    /** Whether the special listener code for the Up Arrow key is enabled. */
    @Getter @Setter private boolean isUpArrowKeyEnabled;

    /** Whether the special listener code for the Down Arrow key is enabled. */
    @Getter @Setter private boolean isDownArrowKeyEnabled;

    /** Whether the special listener code for the Left Arrow key is enabled. */
    @Getter @Setter private boolean isLeftArrowKeyEnabled;

    /** Whether the special listener code for the Right Arrow key is enabled. */
    @Getter @Setter private boolean isRightArrowKeyEnabled;

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
        super(builder.getDimensions(), builder.getPosition());

        final ColorPalette colorPalette = builder.getColorPalette();
        caretForegroundColor = colorPalette.getTextArea_caretForeground();
        caretBackgroundColor = colorPalette.getTextArea_caretBackground();

        foregroundColor = colorPalette.getTextArea_defaultForeground();
        backgroundColor = colorPalette.getTextArea_defaultBackground();

        editable = builder.isEditable();

        enteredText = new char[builder.getHeight()][builder.getWidth()];

        allowedCharacterPattern = builder.getAllowedCharacterPattern();

        isEnterKeyEnabled = builder.isEnterKeyEnabled();
        isBackspaceKeyEnabled = builder.isBackspaceKeyEnabled();
        isDeleteKeyEnabled = builder.isDeleteKeyEnabled();
        isHomeKeyEnabled = builder.isHomeKeyEnabled();
        isEndKeyEnabled = builder.isEndKeyEnabled();
        isPageUpKeyEnabled = builder.isPageUpKeyEnabled();
        isPageDownKeyEnabled = builder.isPageDownKeyEnabled();
        isUpArrowKeyEnabled = builder.isUpArrowKeyEnabled();
        isDownArrowKeyEnabled = builder.isDownArrowKeyEnabled();
        isLeftArrowKeyEnabled = builder.isLeftArrowKeyEnabled();
        isRightArrowKeyEnabled = builder.isRightArrowKeyEnabled();

        // Set the area's initial colors:
        for (int y = 0 ; y < super.tiles.getHeight() ; y++) {
            for (int x = 0 ; x < super.tiles.getWidth() ; x++) {
                final Tile tile = super.getTileAt(x, y);
                tile.setBackgroundColor(backgroundColor);
                tile.setForegroundColor(foregroundColor);

                enteredText[y][x] = ' ';
            }
        }

        // Set initial caret position:
        changeCaretPosition(caretPosition.x, caretPosition.y);
    }

    @Override
    public void createEventListeners(final @NonNull Screen parentScreen) {
        if (super.getEventListeners().size() > 0) {
            return;
        }
        
        final TileGrid tiles = super.tiles;

        final MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (editable == false || isFocused() == false) {
                    return;
                }

                if (e.getButton() != MouseEvent.BUTTON1) {
                    return;
                }

                final Font font = parentScreen.getImageCache().getFont();
                final int columnIndexInArea = (e.getX() / font.getWidth()) - tiles.getXPosition();
                final int rowIndexInArea = (e.getY() / font.getHeight()) - tiles.getYPosition();

                int dx = columnIndexInArea - caretPosition.x;
                int dy = rowIndexInArea - caretPosition.y;

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
                if (editable == false || isFocused() == false) {
                    return;
                }

                final char character = e.getKeyChar();
                final Matcher matcher = allowedCharacterPattern.matcher(character + "");

                if (matcher.matches()) {
                    tiles.getTileAt(caretPosition.x, caretPosition.y).setCharacter(character);
                    enteredText[caretPosition.y][caretPosition.x] = character;

                    final boolean caretAtEndOfLine = caretPosition.x == tiles.getWidth() - 1;

                    if (caretAtEndOfLine) {
                        if (caretPosition.y < tiles.getHeight() - 1) {
                            moveCaretDown();
                            moveCaretToStartOfLine();
                        }
                    } else {
                        moveCaretRight();
                    }

                    updateDisplayedCharacters();
                }
            }

            @Override
            public void keyPressed(final KeyEvent e) {
                if (editable == false || isFocused() == false) {
                    return;
                }

                int keyCode = e.getKeyCode();

                switch (keyCode) {
                    // Move the caret to the first position of the next row:
                    case KeyEvent.VK_ENTER: {
                        if (isEnterKeyEnabled == false) {
                            return;
                        }

                        boolean canWork = caretPosition.y < tiles.getHeight() - 1;

                        if (canWork) {
                            moveCaretDown();
                            moveCaretToStartOfLine();
                            updateDisplayedCharacters();
                        }
                        break;
                    }

                    // Delete the character to the left of the caret, then move the caret one position left:
                    case KeyEvent.VK_BACK_SPACE: {
                        if (isBackspaceKeyEnabled == false) {
                            return;
                        }

                        final boolean caretAtStartOfLine = caretPosition.x == 0;
                        final boolean caretAtEndOfLine = caretPosition.x == tiles.getWidth() - 1;

                        if (caretAtStartOfLine) {
                            if (caretPosition.y > 0) {
                                moveCaretUp();
                                moveCaretToEndOfLine();
                            }
                        } else if (caretAtEndOfLine) {
                            final Tile currentChar = tiles.getTileAt(caretPosition.x, caretPosition.y);

                            if (currentChar.getCharacter() == ' ') {
                                moveCaretLeft();
                            }
                        } else {
                            moveCaretLeft();
                        }

                        clearCurrentCell();
                        updateDisplayedCharacters();
                        break;
                    }
                }
            }

            @Override
            public void keyReleased(final KeyEvent e) {
                if (editable == false || isFocused() == false) {
                    return;
                }

                int keyCode = e.getKeyCode();

                switch (keyCode) {
                    // Erase the current character:
                    case KeyEvent.VK_DELETE: {
                        if (isDeleteKeyEnabled) {
                            clearCurrentCell();
                        }
                        break;
                    }

                    // Move the caret to the first position on the left:
                    case KeyEvent.VK_HOME: {
                        if (isHomeKeyEnabled) {
                            moveCaretToStartOfLine();
                        }
                        break;
                    }

                    // Move the caret to the last position on the right:
                    case KeyEvent.VK_END: {
                        if (isEndKeyEnabled) {
                            moveCaretToEndOfLine();
                        }
                        break;
                    }

                    // Move the caret to the first row:
                    case KeyEvent.VK_PAGE_UP: {
                        if (isPageUpKeyEnabled) {
                            moveCaretToFirstLine();
                        }
                        break;
                    }

                    // Move the caret to the last row:
                    case KeyEvent.VK_PAGE_DOWN: {
                        if (isPageDownKeyEnabled) {
                            moveCaretToLastLine();
                        }
                        break;
                    }

                    // Move the caret one position to the left:
                    case KeyEvent.VK_LEFT: {
                        if (isLeftArrowKeyEnabled) {
                            return;
                        }

                        boolean moveToPreviousLine = caretPosition.x == 0;
                        moveToPreviousLine &= caretPosition.y > 0;

                        if (moveToPreviousLine) {
                            moveCaretUp();
                            moveCaretToEndOfLine();
                        } else {
                            moveCaretLeft();
                        }

                        break;
                    }

                    // Move the caret one position to the right:
                    case KeyEvent.VK_RIGHT: {
                        if (isRightArrowKeyEnabled) {
                            return;
                        }

                        boolean moveToNextLine = caretPosition.x == tiles.getWidth() - 1;
                        moveToNextLine &= caretPosition.y < tiles.getHeight() - 1;

                        if (moveToNextLine) {
                            moveCaretDown();
                            moveCaretToStartOfLine();
                        } else {
                            moveCaretRight();
                        }

                        break;
                    }

                    // Move the caret one position up:
                    case KeyEvent.VK_UP: {
                        if (isUpArrowKeyEnabled) {
                            moveCaretUp();
                        }
                        break;
                    }

                    // Move the caret one position down:
                    case KeyEvent.VK_DOWN: {
                        if (isDownArrowKeyEnabled) {
                            moveCaretDown();
                        }
                        break;
                    }
                }

                updateDisplayedCharacters();
            }
        };

        super.eventListeners.add(keyListener);
        super.eventListeners.add(mouseListener);
    }

    /** Moves the caret one cell up. */
    private void moveCaretUp() {
        if (caretPosition.y > 0) {
            changeCaretPosition(caretPosition.x, caretPosition.y - 1);
        }
    }

    /** Moves the caret one cell down. */
    private void moveCaretDown() {
        if (caretPosition.y < super.tiles.getHeight() - 1) {
            changeCaretPosition(caretPosition.x, caretPosition.y + 1);
        }
    }

    /** Moves the caret one cell left. */
    private void moveCaretLeft() {
        if (caretPosition.x > 0) {
            changeCaretPosition(caretPosition.x - 1, caretPosition.y);
        }
    }

    /** Moves the caret one cell right. */
    private void moveCaretRight() {
        if (caretPosition.x < super.tiles.getWidth() - 1) {
            changeCaretPosition(caretPosition.x + 1, caretPosition.y);
        }
    }

    /**
     * Moves the caret to the first line. Does not change the x-axis position of
     * the caret.
     */
    private void moveCaretToFirstLine() {
        changeCaretPosition(caretPosition.x, 0);
    }

    /**
     * Moves the caret to the last line. Does not change the x-axis position of
     * the caret.
     */
    private void moveCaretToLastLine() {
        changeCaretPosition(caretPosition.x, super.tiles.getHeight() - 1);
    }

    /** Moves the caret to the beginning of the current line. */
    private void moveCaretToStartOfLine() {
        changeCaretPosition(0, caretPosition.y);
    }

    /** Moves the caret to the end of the current line. */
    private void moveCaretToEndOfLine() {
        changeCaretPosition(super.tiles.getWidth() - 1, caretPosition.y);
    }

    /** Deletes the character in the current cell. */
    private void clearCurrentCell() {
        super.getTiles().getTileAt(caretPosition.x, caretPosition.y).setCharacter(' ');
        enteredText[caretPosition.y][caretPosition.x] = ' ';
    }

    private void changeCaretPosition(final int x, final int y) {
        // Reset current position.
        Tile tile = super.tiles.getTileAt(caretPosition.x, caretPosition.y);
        tile.setBackgroundColor(backgroundColor);
        tile.setForegroundColor(foregroundColor);

        // Set new position.
        caretPosition.setLocation(x, y);

        if (editable) {
            tile = super.tiles.getTileAt(caretPosition.x, caretPosition.y);
            tile.setBackgroundColor(caretBackgroundColor);
            tile.setForegroundColor(caretForegroundColor);
        }
    }

    private void updateDisplayedCharacters() {
        for (int y = 0 ; y < super.tiles.getHeight() ; y++) {
            for (int x = 0 ; x < super.tiles.getWidth() ; x++) {
                super.tiles.getTileAt(x, y).setCharacter(enteredText[y][x]);
            }
        }

        super.redrawFunction.run();
    }

    /**
     * Appends text to the first empty row of the area.
     *
     * If there are no empty rows, then the first row is discarded and all rows
     * are moved up, then the new text is appended to the bottom row.
     *
     * @param text
     *        The new text.
     */
    public void appendText(final @NonNull Tile[] text) {
        // Find first empty row and append text:
        for (int y = 0 ; y < super.tiles.getHeight() ; y++) {
            boolean rowIsEmpty = true;

            for (int x = 0 ; x < super.tiles.getWidth() ; x++) {
                final char character = super.tiles.getTileAt(x, y).getCharacter();
                rowIsEmpty &= Character.isSpaceChar(character);
            }

            if (rowIsEmpty) {
                setText(y, text);
                return;
            }
        }

        // If no empty rows found, move all rows up:
        for (int y = 0 ; y < super.tiles.getHeight() - 1 ;y++) {
            setText(y, super.tiles.getRow(y + 1));
        }

        setText(super.tiles.getHeight() - 1, text);

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
    public void setText(final int rowIndex, @NonNull Tile[] text) {
        clearText(rowIndex);

        for (int x = 0 ; x < Math.min(super.tiles.getWidth(), text.length) ; x++) {
            super.tiles.getTileAt(x, rowIndex).copy(text[x]);
            enteredText[rowIndex][x] = text[x].getCharacter();
        }

        updateDisplayedCharacters();
    }

    /**
     * Clears text from a row.
     *
     * @param rowIndex
     *        The row index.
     */
    public void clearText(final int rowIndex) {
        if (rowIndex < 0 || rowIndex > super.tiles.getHeight()) {
            return;
        }

        Arrays.fill(enteredText[rowIndex], ' ');


        for (int x = 0 ; x < super.tiles.getWidth() ; x++) {
            final Tile tile = super.tiles.getTileAt(x, rowIndex);
            tile.reset();
            tile.setBackgroundColor(backgroundColor);
            tile.setForegroundColor(foregroundColor);
        }

        updateDisplayedCharacters();
    }

    /** Clears all text from the field. */
    public void clearText() {
        for (final char[] line : enteredText) {
            Arrays.fill(line, ' ');
        }

        for (int y = 0 ; y < super.tiles.getHeight() ; y++) {
            for (int x = 0 ; x < super.tiles.getWidth() ; x++) {
                super.tiles.getTileAt(x, y).setCharacter(' ');
            }
        }

        updateDisplayedCharacters();
    }
}