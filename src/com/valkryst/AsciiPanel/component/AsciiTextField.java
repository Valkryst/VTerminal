package com.valkryst.AsciiPanel.component;


import com.valkryst.AsciiPanel.AsciiCharacter;
import com.valkryst.AsciiPanel.AsciiPanel;
import com.valkryst.AsciiPanel.AsciiString;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AsciiTextField extends AsciiComponent {
    /** The foreground color of the caret. */
    @Getter @Setter private Color caretForegroundColor = Color.BLACK;
    /** The background color of the caret. */
    @Getter @Setter private Color caretBackgroundColor = Color.YELLOW;

    /** The foreground color of non-caret characters. */
    @Getter @Setter private Color foregroundColor = Color.WHITE;
    /** The background color of non-caret characters. */
    @Getter @Setter private Color backgroundColor = Color.BLUE;

    /** Whether or not the HOME key can be used to move the caret to the first index of the field. */
    @Getter @Setter private boolean homeKeyEnabled = true;
    /** Whether or not the END key can be used to move the caret to the last index of the field. */
    @Getter @Setter private boolean endKeyEnabled = true;
    /** Whether or not the DELETE key can be used to erase the character that the caret is on. */
    @Getter @Setter private boolean deleteKeyEnabled = true;
    /** Whether or not the LEFT ARROW key can be used to move the caret one index to the left. */
    @Getter @Setter private boolean leftArrowKeyEnabled = true;
    /** Whether or not the RIGHT ARROW key can be used to move the caret one index to the right. */
    @Getter @Setter private boolean rightArrowKeyEnabled = true;
    /** Whether or not the BACK SPACE key can be used to erase the character before the caret and move the caret backwards. */
    @Getter @Setter private boolean backSpaceKeyEnabled = true;

    private int index_caret = 0;

    private int maxCharacters;

    /** The pattern used to determine which typed characters can be entered into the field. */
    @Getter @Setter private Pattern allowedCharacterPattern = Pattern.compile("^[a-zA-z0-9$-/:-?{-~!\"^_`\\[\\]@# ]$");

    /**
     * Constructs a new AsciiTextField.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate of the top-left character.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate of the top-left character.
     *
     * @param width
     *         Thw width, in characters.
     */
    public AsciiTextField(final int columnIndex, final int rowIndex, final int width) {
        super(columnIndex, rowIndex, width, 1);

        if (maxCharacters <= 0) {
            this.maxCharacters = width;
        }

        // Set the button's text:
        final AsciiString string = super.getStrings()[0];
        string.setAllCharacters(' ');
        string.setBackgroundAndForegroundColor(backgroundColor, foregroundColor);

        // Set initial caret position:
        changeCaretPosition(index_caret);
    }

    @Override
    public void registerEventHandlers(final AsciiPanel panel) {
        super.registerEventHandlers(panel);

        panel.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(final KeyEvent e) {
                if (isFocused()) {
                    final char character = e.getKeyChar();
                    final Matcher matcher = allowedCharacterPattern.matcher(character + "");

                    if (matcher.matches()) {
                        changeCharacter(index_caret, character);
                        changeCaretPosition(index_caret + 1);
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
                                changeCaretPosition(0);
                                transmitDraw();
                            }
                            break;
                        }

                        // Move the caret to the last position on the right:
                        case KeyEvent.VK_END: {
                            if (endKeyEnabled) {
                                changeCaretPosition(maxCharacters);
                                transmitDraw();
                            }
                            break;
                        }

                        // Erase the current character:
                        case KeyEvent.VK_DELETE: {
                            if (deleteKeyEnabled) {
                                changeCharacter(index_caret, ' ');
                                transmitDraw();
                            }
                            break;
                        }

                        // Move the caret one position to the left:
                        case KeyEvent.VK_LEFT: {
                            boolean canWork = leftArrowKeyEnabled;
                            canWork &= index_caret > 0;

                            if (canWork) {
                                changeCaretPosition(index_caret - 1);
                                transmitDraw();
                            }
                            break;
                        }

                        // Move the caret one position to the right:
                        case KeyEvent.VK_RIGHT: {
                            boolean canWork = rightArrowKeyEnabled;
                            canWork &= index_caret < maxCharacters;

                            if (canWork) {
                                changeCaretPosition(index_caret + 1);
                                transmitDraw();
                            }
                            break;
                        }

                        // Delete the character to the left of the caret, then move the caret one position left:
                        case KeyEvent.VK_BACK_SPACE: {
                            boolean canWork = backSpaceKeyEnabled;
                            canWork &= index_caret > 0;

                            if (! canWork) {
                                break;
                            }

                            if (index_caret == maxCharacters - 1) {
                                final AsciiCharacter currentChar = AsciiTextField.super.strings[0].getCharacters()[index_caret];

                                if (currentChar.getCharacter() != ' ') {
                                    changeCharacter(index_caret, ' ');
                                    break;
                                }
                            }

                            changeCharacter(index_caret - 1, ' ');
                            changeCaretPosition(index_caret - 1);
                            transmitDraw();
                            break;
                        }
                    }
                }
            }
        });
    }

    /**
     * Moves the caret to the specified index.
     *
     * @param newIndex
     *         The new index for the caret.
     */
    private void changeCaretPosition(int newIndex) {
        if (newIndex >= maxCharacters) {
            newIndex = maxCharacters - 1;
        }

        if (newIndex < 0) {
            newIndex = 0;
        }

        final AsciiCharacter[] characters = super.strings[0].getCharacters();

        characters[index_caret].setForegroundColor(foregroundColor);
        characters[index_caret].setBackgroundColor(backgroundColor);

        characters[newIndex].setForegroundColor(caretForegroundColor);
        characters[newIndex].setBackgroundColor(caretBackgroundColor);

        index_caret = newIndex;
    }

    /**
     * Changes the character at the specified index to the specified character.
     *
     * @param characterIndex
     *         The index.
     *
     * @param character
     *         The new character.
     */
    private void changeCharacter(int characterIndex, final char character) {
        if (characterIndex < 0 || characterIndex > maxCharacters) {
            return;
        }

        final AsciiCharacter[] characters = super.getStrings()[0].getCharacters();
        characters[characterIndex].setCharacter(character);
    }
}
