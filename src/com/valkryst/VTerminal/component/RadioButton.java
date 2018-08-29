package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.builder.RadioButtonBuilder;
import com.valkryst.VTerminal.palette.ColorPalette;
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

        final ColorPalette colorPalette = builder.getColorPalette();

        super.backgroundColor_normal = colorPalette.getRadioButton_defaultBackground();
        super.foregroundColor_normal = colorPalette.getRadioButton_defaultForeground();

        super.backgroundColor_hover = colorPalette.getRadioButton_hoverBackground();
        super.foregroundColor_hover = colorPalette.getRadioButton_hoverForeground();

        super.backgroundColor_pressed = colorPalette.getRadioButton_pressedBackground();
        super.foregroundColor_pressed = colorPalette.getRadioButton_pressedForeground();
    }

    @Override
    public void createEventListeners(final @NonNull Screen parentScreen) {
        if (super.eventListeners.size() > 0) {
            return;
        }

        final RadioButton thisButton = this;

        final MouseInputListener mouseListener = new MouseInputListener() {
            @Override
            public void mouseDragged(final MouseEvent e) {}

            @Override
            public void mouseMoved(final MouseEvent e) {
                if (intersects(parentScreen.getMousePosition())) {
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
                    if (intersects(parentScreen.getMousePosition())) {
                        RadioButton.super.getOnClickFunction().run();

                        if (isChecked == false) {
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

        super.eventListeners.add(mouseListener);
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
            super.getTiles().getTileAt(0, 0).setCharacter(checkedButtonChar);

            for (final Tile tile : super.tiles.getRow(0)) {
                tile.setBackgroundColor(backgroundColor_pressed);
                tile.setForegroundColor(foregroundColor_pressed);
            }
        } else {
            super.getTiles().getTileAt(0, 0).setCharacter(emptyButtonChar);

            for (final Tile tile : super.tiles.getRow(0)) {
                tile.setBackgroundColor(backgroundColor_normal);
                tile.setForegroundColor(foregroundColor_normal);
            }
        }

        super.redrawFunction.run();
    }

    @Override
    public void setText(String text) {
        if (text == null) {
            text = "";
        }

        for (int x = 0 ; x < tiles.getWidth() ; x++) {
            if (x == 0 || x == 1) {
                continue;
            }

            if (x <= text.length()) {
                // Because we skip the first two tiles (checkbox & space after it), we have to use -2 to
                // compensate.
                tiles.getTileAt(x, 0).setCharacter(text.charAt(x - 2));
            } else {
                tiles.getTileAt(x, 0).setCharacter(' ');
            }
        }
    }
}
