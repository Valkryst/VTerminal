package com.valkryst.VTerminal.component;


import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.component.CheckBoxBuilder;
import com.valkryst.VTerminal.font.Font;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

@ToString
public class CheckBox extends Button {
    /** The character to display when the checkbox is not checked. */
    @Getter private char emptyBoxChar;
    /** The character to display when the checkbox is checked. */
    @Getter private char checkedBoxChar;

    /** Whether or not the check box is checked. */
    @Getter private boolean isChecked;

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
        super(builder);

        this.emptyBoxChar = builder.getEmptyBoxChar();
        this.checkedBoxChar = builder.getCheckedBoxChar();

        this.isChecked = builder.isChecked();
    }

    @Override
    public void createEventListeners(final Panel panel) {
        if (panel == null) {
            return;
        }

        if (super.getEventListeners().size() > 0) {
            return;
        }

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
                            CheckBox.super.getOnClickFunction().run();
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
                        setStatePressed();
                    } else {
                        setStateNormal();
                    }
                }
            }
        };

        super.getEventListeners().add(mouseListener);
        super.getEventListeners().add(mouseMotionListener);
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
