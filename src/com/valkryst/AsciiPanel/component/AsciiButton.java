package com.valkryst.AsciiPanel.component;

import com.valkryst.AsciiPanel.AsciiCharacter;
import com.valkryst.AsciiPanel.AsciiFont;
import com.valkryst.AsciiPanel.AsciiPanel;
import com.valkryst.AsciiPanel.AsciiString;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import lombok.Getter;

import java.util.ArrayList;

public class AsciiButton extends AsciiComponent {
    /** Whether or not the button is in the normal state. */
    private boolean isInNormalState = true;
    /** whether or not the button is in the hovered state. */
    private boolean isInHoveredState = false;
    /** Whether or not the button is in the pressed state. */
    private boolean isInPressedState = false;

    /** The first character of the button's text. This is used to identify the text as a button. */
    @Getter private char startingCharacter = '<';
    /** The last character of the button's text. This is used to identify the text as a button. */
    @Getter private char endingCharacter = '>';

    /** The background color for when the button is in the normal state. */
    @Getter private Paint backgroundColor_normal = Color.BLACK;
    /** The foreground color for when the button is in the normal state. */
    @Getter private Paint foregroundColor_normal = Color.WHITE;

    /** The background color for when the button is in the hover state. */
    @Getter private Paint backgroundColor_hover = Color.YELLOW;
    /** The foreground color for when the button is in the hover state. */
    @Getter private Paint foregroundColor_hover = Color.BLACK;

    /** The background color for when the button is in the pressed state. */
    @Getter private Paint backgroundColor_pressed = Color.WHITE;
    /** The foreground color for when the button is in the pressed state. */
    @Getter private Paint foregroundColor_pressed = Color.BLACK;

    /** The function to run when the button is clicked. */
    @Getter private final Runnable onClickFunction;

    /**
     * Constructs a new AsciiButton.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate of the top-left character.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate of the top-left character.
     *
     * @param text
     *         The text to display on the button.
     *
     * @param onClickFunction
     *         The function to run when the button is clicked.
     */
    public AsciiButton(final int columnIndex, final int rowIndex, final String text, final Runnable onClickFunction) {
        // The width of the button is "text.length() + 2" because the button text is startingCharacter + text endingCharacter.
        super(columnIndex, rowIndex, text.length() + 2, 1);

        if (onClickFunction == null) {
            throw new IllegalArgumentException("You must specify an instance of runnable when creating an AsciiButton.");
        }

        this.onClickFunction = onClickFunction;

        // Set the button's text:
        final ArrayList<AsciiCharacter> characters = super.getStrings()[0].getCharacters();
        characters.set(0, new AsciiCharacter(startingCharacter));
        characters.set(characters.size() - 1, new AsciiCharacter(endingCharacter));

        for (int column = 1 ; column < characters.size() - 1 ; column++) {
            characters.set(columnIndex, new AsciiCharacter(text.charAt(column - 1)));
        }

        // Set the button's colors (must be done after setting text):
        setColors(backgroundColor_normal, foregroundColor_normal);
    }

    @Override
    public void registerEventHandlers(final AsciiPanel panel) {
        final AsciiFont font = panel.getFont();
        final int fontWidth = font.getWidth();
        final int fontHeight = font.getHeight();

        panel.addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
            if (intersects(event, fontWidth, fontHeight)) {
                setStateHovered();
            } else {
                setStateNormal();
            }
        });

        panel.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (intersects(event, fontWidth, fontHeight)) {
                    setStatePressed();
                }
            }
        });

        panel.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                if (isInPressedState) {
                    onClickFunction.run();
                }

                if (intersects(event, fontWidth, fontHeight)) {
                    setStateHovered();
                } else {
                    setStateNormal();
                }
            }
        });
    }

    /** Sets the button state to normal if the current state allows for the normal state to be set. */
    private void setStateNormal() {
        boolean canSetState = isInNormalState == false;
        canSetState &= isInHoveredState || isInPressedState;

        if (canSetState) {
            isInNormalState = true;
            isInHoveredState = false;
            isInPressedState = false;

            setColors(backgroundColor_normal, foregroundColor_normal);
        }
    }

    /** Sets the button state to hovered if the current state allows for the normal state to be set. */
    private void setStateHovered() {
        boolean canSetState = isInNormalState || isInPressedState;
        canSetState &= isInHoveredState == false;

        if (canSetState) {
            isInNormalState = false;
            isInHoveredState = true;
            isInPressedState = false;

            setColors(backgroundColor_hover, foregroundColor_hover);
        }
    }

    /** Sets the button state to pressed if the current state allows for the normal state to be set. */
    private void setStatePressed() {
        boolean canSetState = isInNormalState || isInHoveredState;
        canSetState &= isInPressedState == false;

        if (canSetState) {
            isInNormalState = false;
            isInHoveredState = false;
            isInPressedState = true;

            setColors(backgroundColor_pressed, foregroundColor_pressed);
        }
    }

    /**
     * Sets the back/foreground colors of all characters to the specified colors.
     *
     * @param backgroundColor
     *         The new background color.
     *
     * @param foregroundColor
     *         The new foreground color.
     */
    private void setColors(final Paint backgroundColor, final Paint foregroundColor) {
        for (final AsciiString s : super.getStrings()) {
            s.setBackgroundColor(backgroundColor);
            s.setForegroundColor(foregroundColor);
        }
    }

    /**
     * Sets the starting character of the button's text.
     *
     * @param startingCharacter
     *         The new starting character.
     */
    public void setStartingCharacter(final char startingCharacter) {
        this.startingCharacter = startingCharacter;

        super.getStrings()[0].getCharacters().get(0).setCharacter(startingCharacter);
    }

    /**
     * Sets the ending character of the button's text.
     *
     * @param endingCharacter
     *         The new ending character.
     */
    public void setEndingCharacter(final char endingCharacter) {
        this.endingCharacter = endingCharacter;

        final ArrayList<AsciiCharacter> characters = super.getStrings()[0].getCharacters();
        characters.get(characters.size() - 1).setCharacter(endingCharacter);
    }

    /**
     * Sets the normal background color.
     *
     * @param color
     *         The new normal background color.
     */
    public void setBackgroundColor_normal(final Paint color) {
        if (color == null) {
            return;
        }

        backgroundColor_normal = color;

        if (isInNormalState) {
            setColors(backgroundColor_normal, foregroundColor_normal);
        }
    }

    /**
     * Sets the normal foreground color.
     *
     * @param color
     *         The new normal foreground color.
     */
    public void setForegroundColor_normal(final Paint color) {
        if (color == null) {
            return;
        }

        foregroundColor_normal = color;

        if (isInNormalState) {
            setColors(backgroundColor_normal, foregroundColor_normal);
        }
    }

    /**
     * Sets the hovered background color.
     *
     * @param color
     *         The new normal background color.
     */
    public void setBackgroundColor_hover(final Paint color) {
        if (color == null) {
            return;
        }

        backgroundColor_hover = color;

        if (isInHoveredState) {
            setColors(backgroundColor_normal, foregroundColor_normal);
        }
    }

    /**
     * Sets the hovered foreground color.
     *
     * @param color
     *         The new hovered foreground color.
     */
    public void setForegroundColor_hover(final Paint color) {
        if (color == null) {
            return;
        }

        foregroundColor_hover = color;

        if (isInHoveredState) {
            setColors(backgroundColor_normal, foregroundColor_normal);
        }
    }

    /**
     * Sets the pressed background color.
     *
     * @param color
     *         The new pressed background color.
     */
    public void setBackgroundColor_pressed(final Paint color) {
        if (color == null) {
            return;
        }

        backgroundColor_pressed = color;

        if (isInPressedState) {
            setColors(backgroundColor_normal, foregroundColor_normal);
        }
    }

    /**
     * Sets the pressed foreground color.
     *
     * @param color
     *         The new pressed foreground color.
     */
    public void setForegroundColor_pressed(final Paint color) {
        if (color == null) {
            return;
        }

        foregroundColor_pressed = color;

        if (isInPressedState) {
            setColors(backgroundColor_normal, foregroundColor_normal);
        }
    }
}
