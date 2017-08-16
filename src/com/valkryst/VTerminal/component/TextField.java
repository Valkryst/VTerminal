package com.valkryst.VTerminal.component;


import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.component.TextFieldBuilder;
import lombok.*;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ToString
public class TextField extends Component {
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
    /** Whether or not the BACK SPACE key can be used to erase the character before the caret and move the caret backwards. */
    @Getter @Setter private boolean backSpaceKeyEnabled;

    /** The current position of the visual caret. */
    @Getter private int index_caret_visual = 0;

    /** The current position of the caret in the enteredText array. */
    @Getter private int index_caret_actual = 0;

    /** The maximum number of characters that the field can contain. */
    @Getter private int maxCharacters;

    /** The text entered by the user. */
    @Getter private char[] enteredText;

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
    public TextField(final @NonNull TextFieldBuilder builder) {
        super(builder.getColumnIndex(), builder.getRowIndex(), builder.getWidth(), 1);

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
        backSpaceKeyEnabled = builder.isBackSpaceKeyEnabled();

        maxCharacters = builder.getMaxCharacters();

        enteredText = new char[maxCharacters];
        clearText();

        allowedCharacterPattern = builder.getAllowedCharacterPattern();

        // Set the fields's initial colors:
        final AsciiString string = super.getString(0);
        string.setBackgroundColor(backgroundColor);
        string.setForegroundColor(foregroundColor);

        // Set initial caret position:
        changeVisualCaretPosition(index_caret_visual);
        changeActualCaretPosition(index_caret_actual);
    }

    @Override
    public void createEventListeners(final @NonNull Panel panel) {
        super.createEventListeners(panel);

        final KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(final KeyEvent e) {
                if (isFocused()) {
                    final char character = e.getKeyChar();
                    final Matcher matcher = allowedCharacterPattern.matcher(character + "");

                    if (matcher.matches()) {
                        changeVisualCharacter(index_caret_visual, character);
                        changeActualCharacter(index_caret_actual, character);

                        moveCaretRight();

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

                        // Erase the current character:
                        case KeyEvent.VK_DELETE: {
                            if (deleteKeyEnabled) {
                                clearCurrentCell();
                                updateDisplayedCharacters();
                                transmitDraw();
                            }
                            break;
                        }

                        // Move the caret one position to the left:
                        case KeyEvent.VK_LEFT: {
                            if (leftArrowKeyEnabled) {
                                moveCaretLeft();
                                updateDisplayedCharacters();
                                transmitDraw();
                            }
                            break;
                        }

                        // Move the caret one position to the right:
                        case KeyEvent.VK_RIGHT: {
                            if (rightArrowKeyEnabled) {
                                moveCaretRight();
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

                            final boolean caretAtEndOfLine = index_caret_actual == maxCharacters - 1;

                            if (caretAtEndOfLine) {
                                final AsciiCharacter currentChar = TextField.super.getString(0).getCharacters()[index_caret_visual];

                                if (currentChar.getCharacter() != ' ') {
                                    clearCurrentCell();
                                    updateDisplayedCharacters();
                                    transmitDraw();
                                    break;
                                }
                            }

                            moveCaretLeft();
                            clearCurrentCell();

                            updateDisplayedCharacters();
                            transmitDraw();
                            break;
                        }
                    }
                }
            }
        };

        super.getEventListeners().add(keyListener);
    }

    /** Moves the caret one cell left. */
    private void moveCaretLeft() {
        if (index_caret_visual > 0) {
            changeVisualCaretPosition(index_caret_visual - 1);
        }

        if (index_caret_actual > 0) {
            changeActualCaretPosition(index_caret_actual - 1);
        }
    }

    /** Moves the caret one cell right. */
    private void moveCaretRight() {
        if (index_caret_visual < super.getWidth() - 1) {
            changeVisualCaretPosition(index_caret_visual + 1);
        }

        if (index_caret_actual < maxCharacters - 1) {
            changeActualCaretPosition(index_caret_actual + 1);
        }
    }

    /** Moves the caret to the beginning of the current line. */
    private void moveCaretToStartOfLine() {
        changeVisualCaretPosition(0);
        changeActualCaretPosition(0);
    }

    /** Moves the caret to the end of the current line. */
    private void moveCaretToEndOfLine() {
        changeVisualCaretPosition(super.getWidth() - 1);
        changeActualCaretPosition(maxCharacters - 1);
    }

    /** Deletes the character in the current cell. */
    private void clearCurrentCell() {
        changeVisualCharacter(index_caret_visual, ' ');
        changeActualCharacter(index_caret_actual, ' ');
    }

    /**
     * Moves the visual caret to the specified index.
     *
     * @param newIndex
     *         The new index for the caret.
     */
    private void changeVisualCaretPosition(int newIndex) {
        if (newIndex >= super.getWidth()) {
            newIndex = super.getWidth() - 1;
        }

        if (newIndex < 0) {
            newIndex = 0;
        }

        final AsciiCharacter[] characters = super.getString(0).getCharacters();

        characters[index_caret_visual].setForegroundColor(foregroundColor);
        characters[index_caret_visual].setBackgroundColor(backgroundColor);

        characters[newIndex].setForegroundColor(caretForegroundColor);
        characters[newIndex].setBackgroundColor(caretBackgroundColor);

        final Radio<String> radio = super.getRadio();

        if (radio != null) {
            characters[index_caret_visual].disableBlinkEffect();
            characters[index_caret_visual].setHidden(false);
            characters[newIndex].enableBlinkEffect((short) 1000, radio);
        }

        index_caret_visual = newIndex;
    }

    /**
     * Moves the actual caret to the specified index.
     *
     * @param newIndex
     *        The new index for the caret.
     */
    private void changeActualCaretPosition(int newIndex) {
        if (newIndex >= maxCharacters) {
            newIndex = maxCharacters - 1;
        }

        if (newIndex < 0) {
            newIndex = 0;
        }

        index_caret_actual = newIndex;
    }

    /**
     * Changes the visual character at the specified index.
     *
     * @param characterIndex
     *         The index.
     *
     * @param character
     *         The new character.
     */
    private void changeVisualCharacter(final int characterIndex, final char character) {
        if (characterIndex < 0 || characterIndex > maxCharacters) {
            return;
        }

        final AsciiCharacter[] characters = super.getString(0).getCharacters();
        characters[characterIndex].setCharacter(character);
    }

    /**
     * Changes the actual character at the specified index.
     *
     * @param characterIndex
     *        The index.
     *
     * @param character
     *        The new character.
     */
    private void changeActualCharacter(final int characterIndex, final char character) {
        if (characterIndex < 0 || characterIndex > maxCharacters) {
            return;
        }

        enteredText[characterIndex] = character;
    }

    private void updateDisplayedCharacters() {
        final int caretPositionDifference = index_caret_actual - index_caret_visual;

        for (int i = caretPositionDifference ; i < super.getWidth() + caretPositionDifference ; i++) {
            super.getString(0).getCharacters()[i - caretPositionDifference].setCharacter(enteredText[i]);
        }
    }

    /** @return The text contained within the field. */
    public String getText() {
        final StringBuilder sb = new StringBuilder();

        for (final char c : enteredText) {
            sb.append(c);
        }

        return sb.toString();
    }

    /**
     * Sets the text contained within the field.
     *
     * @param text
     *        The new text.
     *
     * @throws NullPointerException
     *         If the text is null.
     */
    public void setText(@NonNull String text) {
        if (text.isEmpty()) {
            clearText();
            return;
        }

        if (text.length() > maxCharacters) {
            text = text.substring(0, maxCharacters);
        }

        System.arraycopy(text.toCharArray(), 0, enteredText, 0, text.length());
    }

    /** Clears all text from the field. */
    public void clearText() {
        Arrays.fill(enteredText, ' ');

        for (final AsciiCharacter character : super.getString(0).getCharacters()) {
            character.setCharacter(' ');
        }
    }
}
