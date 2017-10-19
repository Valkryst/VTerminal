package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.component.RadioButtonBuilder;
import com.valkryst.VTerminal.font.Font;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

@ToString
public class RadioButton extends Button {
    /** The character to display when the radio button is not checked. */
    @Getter @Setter private char emptyButtonChar;
    /** The character to display when the radio button is checked. */
    @Getter @Setter private char checkedButtonChar;

    /** Whether or not the radio button is checked. */
    @Getter private boolean isChecked = false;

    /** The radio button group that the radio button belongs to. */
    @Getter private RadioButtonGroup group;

    /**
     * Constructs a new AsciiRadioButton.
     *
     * @param builder
     *         The builder to use.
     *
     * @throws NullPointerException
     *         If the builder is null.
     */
    public RadioButton(final @NonNull RadioButtonBuilder builder) {
        super(builder);

        this.emptyButtonChar = builder.getEmptyButtonChar();
        this.checkedButtonChar = builder.getCheckedButtonChar();

        this.group = builder.getGroup();
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

        final RadioButton thisButton = this;

        final MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {}

            @Override
            public void mousePressed(final MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (intersects(e, fontWidth, fontHeight)) {
                        if (isChecked == false) {
                            RadioButton.super.getOnClickFunction().run();
                            group.setCheckedButton(thisButton);
                            setStateHovered();
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
     * Sets the starting character of the button's text.
     *
     * @param emptyButtonChar
     *         The new empty button character.
     */
    public void setEmptyBoxCharacter(final char emptyButtonChar) {
        this.emptyButtonChar = emptyButtonChar;

        super.getString(0).getCharacters()[0].setCharacter(emptyButtonChar);
    }

    /**
     * Sets the ending character of the button's text.
     *
     * @param checkedButtonChar
     *         The new checked button character.
     */
    public void setEndingCharacter(final char checkedButtonChar) {
        this.checkedButtonChar = checkedButtonChar;

        final AsciiCharacter[] characters = super.getString(0).getCharacters();
        super.getString(0).setCharacter(characters.length - 1, checkedButtonChar);
    }

    public void setChecked(final boolean isChecked) {
        this.isChecked = isChecked;

        if (isChecked) {
            super.getString(0).setCharacter(0, checkedButtonChar);
        } else {
            super.getString(0).setCharacter(0, emptyButtonChar);
        }

        transmitDraw();
    }
}
