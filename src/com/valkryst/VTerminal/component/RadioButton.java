package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.builder.component.RadioButtonBuilder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

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
    protected void createEventListeners() {
        if (super.getEventListeners().size() > 0) {
            return;
        }

        super.createEventListeners();

        final RadioButton thisButton = this;

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

        super.getEventListeners().add(mouseListener);
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
