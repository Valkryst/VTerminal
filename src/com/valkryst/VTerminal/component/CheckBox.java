package com.valkryst.VTerminal.component;


import com.valkryst.VTerminal.builder.component.CheckBoxBuilder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

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
    public void createEventListeners() {
        if (super.getEventListeners().size() > 0) {
            return;
        }

        super.createEventListeners();

        final MouseInputListener mouseListener = new MouseInputListener() {
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

            @Override
            public void mouseClicked(final MouseEvent e) {}

            @Override
            public void mousePressed(final MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (intersects(e)) {
                        if (isChecked) {
                            setChecked(false);
                        } else {
                            CheckBox.super.getOnClickFunction().run();
                            setChecked(true);
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

        super.getEventListeners().add(mouseListener);
    }

    /**
     * Sets the check box as checked.
     *
     * @param isChecked
     *          Whether or not the check box is checked.
     */
    public void setChecked(final boolean isChecked) {
        this.isChecked = isChecked;

        if (isChecked) {
            super.getString(0).setCharacter(0, checkedBoxChar);
        } else {
            super.getString(0).setCharacter(0, emptyBoxChar);
        }

        transmitDraw();
    }
}
