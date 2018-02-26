package com.valkryst.VTerminal.component;


import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.builder.CheckBoxBuilder;
import com.valkryst.VTerminal.palette.ColorPalette;
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

        final ColorPalette colorPalette = builder.getColorPalette();

        super.backgroundColor_normal = colorPalette.getCheckBox_defaultBackground();
        super.foregroundColor_normal = colorPalette.getCheckBox_defaultForeground();

        super.backgroundColor_hover = colorPalette.getCheckBox_hoverBackground();
        super.foregroundColor_hover = colorPalette.getCheckBox_hoverForeground();

        super.backgroundColor_pressed = colorPalette.getCheckBox_checkedBackground();
        super.foregroundColor_pressed = colorPalette.getCheckBox_checkedForeground();

        if (isChecked) {
            super.tiles.getTileAt(0, 0).setCharacter(checkedBoxChar);

            for (final Tile tile : super.tiles.getRow(0)) {
                tile.setBackgroundColor(backgroundColor_pressed);
                tile.setForegroundColor(foregroundColor_pressed);
            }
        }
    }

    @Override
    public void createEventListeners(final @NonNull Screen parentScreen) {
        if (super.eventListeners.size() > 0) {
            return;
        }

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

        super.eventListeners.add(mouseListener);
    }

    /**
     * Sets the checked state.
     *
     * @param isChecked
     *          Whether or not the check box is checked.
     */
    public void setChecked(final boolean isChecked) {
        this.isChecked = isChecked;

        if (isChecked) {
            super.tiles.getTileAt(0, 0).setCharacter(checkedBoxChar);
        } else {
            super.tiles.getTileAt(0, 0).setCharacter(emptyBoxChar);
        }

        super.redrawFunction.run();
    }
}
