package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiPanel;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.builder.component.AsciiRadioButtonBuilder;
import com.valkryst.VTerminal.font.AsciiFont;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class AsciiRadioButton extends AsciiComponent {
    /** Whether or not the radio button is in the normal state. */
    private boolean isInNormalState = true;
    /** whether or not the radio button is in the hovered state. */
    private boolean isInHoveredState = false;

    @Getter @Setter private char emptyButtonChar;
    @Getter @Setter private char checkedButtonChar;

    /** Whether or not the radio button is checked. */
    @Getter private boolean isChecked = false;

    /** The radio button group that the radio button belongs to. */
    @Getter private AsciiRadioButtonGroup group;

    /** The background color for when the radio button is in the normal state. */
    @Getter private Color backgroundColor_normal;
    /** The foreground color for when the radio button is in the normal state. */
    @Getter private Color foregroundColor_normal;

    /** The background color for when the radio button is in the hover state. */
    @Getter private Color backgroundColor_hover;
    /** The foreground color for when the radio button is in the hover state. */
    @Getter private Color foregroundColor_hover;

    /** The background color for when the radio button is in the checked state. */
    @Getter private Color backgroundColor_checked;
    /** The foreground color for when the radio button is in the checked state. */
    @Getter private Color foregroundColor_checked;

    /**
     * Constructs a new AsciiRadioButton.
     *
     * @param builder
     *         The builder to use.
     */
    public AsciiRadioButton(final AsciiRadioButtonBuilder builder) {
        super(builder.getColumnIndex(), builder.getRowIndex(), builder.getText().length() + 2, 1);

        super.radio = builder.getRadio();

        this.emptyButtonChar = builder.getEmptyButtonChar();
        this.checkedButtonChar = builder.getCheckedButtonChar();

        this.group = builder.getGroup();

        this.backgroundColor_normal = builder.getBackgroundColor_normal();
        this.foregroundColor_normal = builder.getForegroundColor_normal();

        this.backgroundColor_hover = builder.getBackgroundColor_hover();
        this.foregroundColor_hover = builder.getForegroundColor_hover();

        this.backgroundColor_checked = builder.getBackgroundColor_checked();
        this.foregroundColor_checked = builder.getForegroundColor_checked();

        // Set the label's text:
        final AsciiString string = super.strings[0];

        // Set the radio button's text, place a space between the button and the label, then set the label
        string.setCharacter(0, emptyButtonChar);
        string.setCharacter(1, ' ');


        final char[] text = builder.getText().toCharArray();

        for (int column = 2 ; column < text.length + 2 ; column++) {
            string.setCharacter(column, text[column - 2]);
        }

        // Set the back/foreground colors:
        string.setBackgroundAndForegroundColor(backgroundColor_normal, foregroundColor_normal);
    }

    @Override
    public void registerEventHandlers(final AsciiPanel panel) {
        final AsciiFont font = panel.getAsciiFont();
        final int fontWidth = font.getWidth();
        final int fontHeight = font.getHeight();

        final AsciiRadioButton thisButton = this;

        panel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {}

            @Override
            public void mousePressed(final MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (intersects(e, fontWidth, fontHeight)) {
                        if (isChecked == false) {
                            group.setCheckedButton(thisButton);
                        }
                    }
                }
            }

            @Override
            public void mouseReleased(final MouseEvent e) {}

            @Override
            public void mouseEntered(final MouseEvent e) {}

            @Override
            public void mouseExited(final MouseEvent e) {}
        });

        panel.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(final MouseEvent e) {}

            @Override
            public void mouseMoved(final MouseEvent e) {
                if (intersects(e, fontWidth, fontHeight)) {
                    setStateHovered();
                } else {
                    if (isChecked) {
                        setStateChecked();
                    } else {
                        setStateNormal();
                    }
                }
            }
        });
    }

    /** Sets the radio button state to normal if the current state allows for the normal state to be set. */
    private void setStateNormal() {
        boolean canSetState = isInNormalState == false;
        canSetState &= isInHoveredState || isChecked;

        if (canSetState) {
            isInNormalState = true;
            isInHoveredState = false;

            setColors(backgroundColor_normal, foregroundColor_normal);
            transmitDraw();
        }
    }

    /** Sets the radio button state to hovered if the current state allows for the normal state to be set. */
    private void setStateHovered() {
        boolean canSetState = isInNormalState || isChecked;
        canSetState &= isInHoveredState == false;

        if (canSetState) {
            isInNormalState = false;
            isInHoveredState = true;

            setColors(backgroundColor_hover, foregroundColor_hover);
            transmitDraw();
        }
    }

    /** Sets the radio button state to pressed if the current state allows for the normal state to be set. */
    private void setStateChecked() {
        boolean canSetState = isInNormalState || isInHoveredState;
        canSetState &= isChecked;

        if (canSetState) {
            isInNormalState = false;
            isInHoveredState = false;

            setColors(backgroundColor_checked, foregroundColor_checked);
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
        for (final AsciiString s : super.strings) {
            s.setBackgroundAndForegroundColor(backgroundColor, foregroundColor);
        }
    }

    /**
     * Sets the starting character of the button's text.
     *
     * @param emptyButtonChar
     *         The new empty button character.
     */
    public void setEmptyBoxCharacter(final char emptyButtonChar) {
        this.emptyButtonChar = emptyButtonChar;

        super.strings[0].getCharacters()[0].setCharacter(emptyButtonChar);
    }

    /**
     * Sets the ending character of the button's text.
     *
     * @param checkedButtonChar
     *         The new checked button character.
     */
    public void setEndingCharacter(final char checkedButtonChar) {
        this.checkedButtonChar = checkedButtonChar;

        final AsciiCharacter[] characters = super.strings[0].getCharacters();
        super.strings[0].setCharacter(characters.length - 1, checkedButtonChar);
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

    /** Checks the radio button. */
    public void check() {
        setStateChecked();
        isChecked = true;
        super.getStrings()[0].setCharacter(0, checkedButtonChar);

        transmitDraw();
    }

    /** Unchecks the radio button. */
    public void uncheck() {
        setStateNormal();
        isChecked = false;
        super.getStrings()[0].setCharacter(0, emptyButtonChar);

        transmitDraw();
    }
}
