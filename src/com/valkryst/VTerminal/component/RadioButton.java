package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.builder.component.RadioButtonBuilder;
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
    public void createEventListeners() {
        if (super.getEventListeners().size() > 0) {
            return;
        }

        super.createEventListeners();

        final RadioButton thisButton = this;

        final MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {}

            @Override
            public void mousePressed(final MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (intersects(e)) {
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
                if (intersects(e)) {
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
     * Sets the radio button as checked.
     *
     * @param isChecked
     *          Whether or not the radio button is checked.
     */
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
