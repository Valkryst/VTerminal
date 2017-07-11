package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.component.ButtonBuilder;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.misc.IntRange;
import lombok.Getter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Objects;


public class Button extends Component {
    /** Whether or not the button is in the normal state. */
    private boolean isInNormalState = true;
    /** whether or not the button is in the hovered state. */
    private boolean isInHoveredState = false;
    /** Whether or not the button is in the pressed state. */
    private boolean isInPressedState = false;

    /** The first character of the button's text. This is used to identify the text as a button. */
    @Getter private char startingCharacter;
    /** The last character of the button's text. This is used to identify the text as a button. */
    @Getter private char endingCharacter;

    /** The background color for when the button is in the normal state. */
    @Getter private Color backgroundColor_normal;
    /** The foreground color for when the button is in the normal state. */
    @Getter private Color foregroundColor_normal;

    /** The background color for when the button is in the hover state. */
    @Getter private Color backgroundColor_hover;
    /** The foreground color for when the button is in the hover state. */
    @Getter private Color foregroundColor_hover;

    /** The background color for when the button is in the pressed state. */
    @Getter private Color backgroundColor_pressed;
    /** The foreground color for when the button is in the pressed state. */
    @Getter private Color foregroundColor_pressed;

    /** The function to run when the button is clicked. */
    @Getter private final Runnable onClickFunction;

    /**
     * Constructs a new AsciiButton.
     *
     * @param builder
     *         The builder to use.
     */
    public Button(final ButtonBuilder builder) {
        // The width of the button is "text.length() + 2" because the button text is startingCharacter + text + endingCharacter.
        super(builder.getColumnIndex(), builder.getRowIndex(), builder.getText().length() + 2, 1);

        super.setRadio(builder.getRadio());

        this.startingCharacter = builder.getStartingCharacter();
        this.endingCharacter = builder.getEndingCharacter();

        this.backgroundColor_normal = builder.getBackgroundColor_normal();
        this.foregroundColor_normal = builder.getForegroundColor_normal();

        this.backgroundColor_hover = builder.getBackgroundColor_hover();
        this.foregroundColor_hover = builder.getForegroundColor_hover();

        this.backgroundColor_pressed = builder.getBackgroundColor_pressed();
        this.foregroundColor_pressed = builder.getForegroundColor_pressed();

        this.onClickFunction = builder.getOnClickFunction();

        // Set the button's text:
        final char[] text = builder.getText().toCharArray();

        final AsciiCharacter[] characters = getStrings()[0].getCharacters();
        characters[0].setCharacter(startingCharacter);
        characters[characters.length - 1].setCharacter(endingCharacter);

        for (int column = 1 ; column < characters.length - 1 ; column++) {
            characters[column].setCharacter(text[column - 1]);
        }

        // Set the button's colors (must be done after setting text):
        setColors(backgroundColor_normal, foregroundColor_normal);
    }

    @Override
    public boolean equals(final Object otherObj) {
        if (otherObj instanceof Button == false) {
            return false;
        }

        if (otherObj == this) {
            return true;
        }

        final Button otherButton = (Button) otherObj;

        boolean isEqual = super.equals(otherObj);
        isEqual &= Objects.equals(startingCharacter, otherButton.getStartingCharacter());
        isEqual &= Objects.equals(endingCharacter, otherButton.getEndingCharacter());
        isEqual &= Objects.equals(backgroundColor_normal, otherButton.getBackgroundColor_normal());
        isEqual &= Objects.equals(foregroundColor_normal, otherButton.getForegroundColor_normal());
        isEqual &= Objects.equals(backgroundColor_hover, otherButton.getBackgroundColor_hover());
        isEqual &= Objects.equals(foregroundColor_hover, otherButton.getForegroundColor_hover());
        isEqual &= Objects.equals(backgroundColor_pressed, otherButton.getBackgroundColor_pressed());
        isEqual &= Objects.equals(foregroundColor_pressed, otherButton.getForegroundColor_pressed());
        isEqual &= Objects.equals(onClickFunction, otherButton.getOnClickFunction());
        return isEqual;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), startingCharacter, endingCharacter, backgroundColor_normal,
                            foregroundColor_normal, backgroundColor_hover, foregroundColor_hover, backgroundColor_pressed,
                            foregroundColor_pressed, onClickFunction);
    }

    @Override
    public void registerEventHandlers(final Panel panel) {
        final Font font = panel.getImageCache().getFont();
        final int fontWidth = font.getWidth();
        final int fontHeight = font.getHeight();

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (intersects(e, fontWidth, fontHeight)) {
                        setStatePressed();
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (isInPressedState) {
                        onClickFunction.run();
                    }

                    if (intersects(e, fontWidth, fontHeight)) {
                        setStateHovered();
                    } else {
                        setStateNormal();
                    }
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        panel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (intersects(e, fontWidth, fontHeight)) {
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
            transmitDraw();
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
            transmitDraw();
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
            transmitDraw();
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
    private void setColors(final Color backgroundColor, final Color foregroundColor) {
        for (final AsciiString s : getStrings()) {
            s.setBackgroundAndForegroundColor(backgroundColor, foregroundColor);
        }
    }

    /**
     * Enables the blink effect on the button's text, but not on the starting and ending characters.
     *
     * @param millsBetweenBlinks
     *         The amount of time, in milliseconds, before the blink effect can occur.
     */
    public void enableBlinkEffect(final short millsBetweenBlinks) {
        final int beginIndex = 1;
        final int endIndex = getStrings()[0].getCharacters().length - 1;
        final IntRange range = new IntRange(beginIndex, endIndex);

        getStrings()[0].enableBlinkEffect(millsBetweenBlinks, getRadio(), range);
    }

    /** Disables the blink effect on the button's text, but not on the starting and ending characters. */
    public void disableBlinkEffect() {
        final int beginIndex = 1;
        final int endIndex = getStrings()[0].getCharacters().length - 1;
        final IntRange range = new IntRange(beginIndex, endIndex);

        getStrings()[0].disableBlinkEffect(range);
    }

    /**
     * Sets the starting character of the button's text.
     *
     * @param startingCharacter
     *         The new starting character.
     */
    public void setStartingCharacter(final char startingCharacter) {
        this.startingCharacter = startingCharacter;

        getStrings()[0].getCharacters()[0].setCharacter(startingCharacter);
    }

    /**
     * Sets the ending character of the button's text.
     *
     * @param endingCharacter
     *         The new ending character.
     */
    public void setEndingCharacter(final char endingCharacter) {
        this.endingCharacter = endingCharacter;

        final AsciiCharacter[] characters = getStrings()[0].getCharacters();
        getStrings()[0].setCharacter(characters.length - 1, endingCharacter);
    }

    /**
     * Sets the normal background color.
     *
     * @param color
     *         The new normal background color.
     */
    public void setBackgroundColor_normal(final Color color) {
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
    public void setForegroundColor_normal(final Color color) {
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
    public void setBackgroundColor_hover(final Color color) {
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
    public void setForegroundColor_hover(final Color color) {
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
    public void setBackgroundColor_pressed(final Color color) {
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
    public void setForegroundColor_pressed(final Color color) {
        if (color == null) {
            return;
        }

        foregroundColor_pressed = color;

        if (isInPressedState) {
            setColors(backgroundColor_normal, foregroundColor_normal);
        }
    }
}