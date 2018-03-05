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
                        clearCurrentCell();
                        break;
                    }

                    // Move the caret to the first position on the left:
                    case KeyEvent.VK_HOME: {
                        moveCaretToStartOfLine();
                        break;
                    }

                    // Move the caret to the last position on the right:
                    case KeyEvent.VK_END: {
                        moveCaretToEndOfLine();
                        break;
                    }

                    // Move the caret to the first row:
                    case KeyEvent.VK_PAGE_UP: {
                        moveCaretToFirstLine();
                        break;
                    }

                    // Move the caret to the last row:
                    case KeyEvent.VK_PAGE_DOWN: {
                        moveCaretToLastLine();
                        break;
                    }

                    // Move the caret one position to the left:
                    case KeyEvent.VK_LEFT: {
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
                        moveCaretUp();
                        break;
                    }

                    // Move the caret one position down:
                    case KeyEvent.VK_DOWN: {
                        moveCaretDown();
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
}