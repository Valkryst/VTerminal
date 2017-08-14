package com.valkryst.VTerminal.component;


import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.component.CheckBoxBuilder;
import com.valkryst.VTerminal.font.Font;
import lombok.*;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

@EqualsAndHashCode(callSuper=true)
@ToString
public class CheckBox extends Component {
    /** Whether or not the check box is in the normal state. */
    private boolean isInNormalState = true;
    /** whether or not the check box is in the hovered state. */
    private boolean isInHoveredState = false;

    /** The character to display when the checkbox is not checked. */
    @Getter @Setter private char emptyBoxChar;
    /** The character to display when the checkbox is checked. */
    @Getter @Setter private char checkedBoxChar;

    /** Whether or not the check box is checked. */
    @Getter private boolean isChecked;

    /** The background color for when the check box is in the normal state. */
    @Getter private Color backgroundColor_normal;
    /** The foreground color for when the check box is in the normal state. */
    @Getter private Color foregroundColor_normal;

    /** The background color for when the check box is in the hover state. */
    @Getter private Color backgroundColor_hover;
    /** The foreground color for when the check box is in the hover state. */
    @Getter private Color foregroundColor_hover;

    /** The background color for when the check box is in the checked state. */
    @Getter private Color backgroundColor_checked;
    /** The foreground color for when the check box is in the checked state. */
    @Getter private Color foregroundColor_checked;

    /**
     * Constructs a new AsciiCheckBox.
     *
     * @param builder
     *         The builder to use.
     *
     * @throws NullPointerException
     *         If the builder is null.
     */
    public CheckBox(final @NonNull CheckBoxBuilder builder) {
        super(builder.getColumnIndex(), builder.getRowIndex(), builder.getText().length() + 2, 1);

        super.setRadio(builder.getRadio());

        this.emptyBoxChar = builder.getEmptyBoxChar();
        this.checkedBoxChar = builder.getCheckedBoxChar();

        this.isChecked = builder.isChecked();

        this.backgroundColor_normal = builder.getBackgroundColor_normal();
        this.foregroundColor_normal = builder.getForegroundColor_normal();

        this.backgroundColor_hover = builder.getBackgroundColor_hover();
        this.foregroundColor_hover = builder.getForegroundColor_hover();

        this.backgroundColor_checked = builder.getBackgroundColor_checked();
        this.foregroundColor_checked = builder.getForegroundColor_checked();

        // Set the label's text:
        final AsciiString string = super.getString(0);

        // Set the checkbox's text, place a space between the checkbox and the label, then set the label
        string.setCharacter(0, emptyBoxChar);
        string.setCharacter(1, ' ');


        final char[] text = builder.getText().toCharArray();

        for (int column = 2 ; column < text.length + 2 ; column++) {
            string.setCharacter(column, text[column - 2]);
        }

        // Set the back/foreground colors:
        string.setBackgroundColor(backgroundColor_normal);
        string.setForegroundColor(foregroundColor_normal);
    }

    @Override
    public void createEventListeners(final @NonNull Panel panel) {
        super.createEventListeners(panel);

        final Font font = panel.getImageCache().getFont();
        final int fontWidth = font.getWidth();
        final int fontHeight = font.getHeight();

        final MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {}

            @Override
            public void mousePressed(final MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (intersects(e, fontWidth, fontHeight)) {
                        if (isChecked) {
                            uncheck();
                        } else {
                            check();
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
        };

        final MouseMotionListener mouseMotionListener = new MouseMotionListener() {
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
        };

        super.getEventListeners().add(mouseListener);
        super.getEventListeners().add(mouseMotionListener);
    }

    /** Sets the check box state to normal if the current state allows for the normal state to be set. */
    private void setStateNormal() {
        boolean canSetState = isInNormalState == false;
        canSetState &= isInHoveredState || isChecked;

        if (canSetState) {
            isInNormalState = true;
            isInHoveredState = false;

            setColors(backgroundColor_normal, foregroundColor_normal);
            super.transmitDraw();
        }
    }

    /** Sets the check box state to hovered if the current state allows for the normal state to be set. */
    private void setStateHovered() {
        boolean canSetState = isInNormalState || isChecked;
        canSetState &= isInHoveredState == false;

        if (canSetState) {
            isInNormalState = false;
            isInHoveredState = true;

            setColors(backgroundColor_hover, foregroundColor_hover);
            super.transmitDraw();
        }
    }

    /** Sets the check box state to pressed if the current state allows for the normal state to be set. */
    private void setStateChecked() {
        boolean canSetState = isInNormalState || isInHoveredState;
        canSetState &= isChecked;

        if (canSetState) {
            isInNormalState = false;
            isInHoveredState = false;

            setColors(backgroundColor_checked, foregroundColor_checked);
            super.transmitDraw();
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
     *
     * @throws NullPointerException
     *         If the background or foreground color is null.
     */
    private void setColors(final @NonNull Color backgroundColor, final @NonNull Color foregroundColor) {
        for (final AsciiString s : super.getStrings()) {
            s.setBackgroundColor(backgroundColor);
            s.setForegroundColor(foregroundColor);
        }
    }

    /**
     * Sets the starting character of the box's text.
     *
     * @param emptyBoxChar
     *         The new empty box character.
     */
    public void setEmptyBoxCharacter(final char emptyBoxChar) {
        this.emptyBoxChar = emptyBoxChar;

        super.getString(0).getCharacters()[0].setCharacter(emptyBoxChar);
    }

    /**
     * Sets the ending character of the box's text.
     *
     * @param checkedBoxChar
     *         The new checked box character.
     *
     * @throws NullPointerException
     *         If the color is null.
     */
    public void setEndingCharacter(final char checkedBoxChar) {
        this.checkedBoxChar = checkedBoxChar;

        final AsciiCharacter[] characters = super.getString(0).getCharacters();
        super.getString(0).setCharacter(characters.length - 1, checkedBoxChar);
    }

    /**
     * Sets the normal background color.
     *
     * @param color
     *         The new normal background color.
     *
     * @throws NullPointerException
     *         If the color is null.
     */
    public void setBackgroundColor_normal(final @NonNull Color color) {
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
     *
     * @throws NullPointerException
     *         If the color is null.
     */
    public void setForegroundColor_normal(final @NonNull Color color) {
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
     *
     * @throws NullPointerException
     *         If the color is null.
     */
    public void setBackgroundColor_hover(final @NonNull Color color) {
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
     *
     * @throws NullPointerException
     *         If the color is null.
     */
    public void setForegroundColor_hover(final @NonNull Color color) {
        foregroundColor_hover = color;

        if (isInHoveredState) {
            setColors(backgroundColor_normal, foregroundColor_normal);
        }
    }

    /** Checks the check box. */
    public void check() {
        isChecked = true;
        super.getString(0).setCharacter(0, checkedBoxChar);

        super.transmitDraw();
    }

    /** Unchecks the check box. */
    public void uncheck() {
        isChecked = false;
        super.getString(0).setCharacter(0, emptyBoxChar);

        super.transmitDraw();
    }
}
