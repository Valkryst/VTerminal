package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.builder.RadioButtonBuilder;
import com.valkryst.VTerminal.palette.java2d.Java2DPalette;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.awt.*;
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

        setPalette(builder.getPalette(), false);

        // Set the radio button's colors:
        if (isChecked) {
            for (final Tile tile : super.tiles.getRow(0)) {
                tile.setBackgroundColor(backgroundColor_pressed);
                tile.setForegroundColor(foregroundColor_pressed);
            }
        } else {
            for (final Tile tile : super.tiles.getRow(0)) {
                tile.setBackgroundColor(backgroundColor_normal);
                tile.setForegroundColor(foregroundColor_normal);
            }
        }
    }

    @Override
    public void createEventListeners(final Screen parentScreen) {
        if (parentScreen == null || super.eventListeners.size() > 0) {
            return;
        }

        final RadioButton thisButton = this;

        final MouseListener mouseListener = new MouseListener() {
            @Override
            public void mouseClicked(final MouseEvent e) {}

            @Override
            public void mousePressed(final MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (intersects(parentScreen.getMousePosition())) {
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
        };

        super.eventListeners.add(mouseListener);
        super.eventListeners.add(mouseMotionListener);
    }

    @Override
    public void setPalette(final Java2DPalette palette, final boolean redraw) {
        if (palette == null) {
            return;
        }

        // Set the instance variables.
        this.palette = palette;

        this.backgroundColor_normal = palette.getRadioButtonPalette().getBackground();
        this.foregroundColor_normal = palette.getRadioButtonPalette().getForeground();

        this.backgroundColor_pressed = palette.getRadioButtonPalette().getBackgroundPressed();
        this.foregroundColor_pressed = palette.getRadioButtonPalette().getForegroundPressed();

        this.backgroundColor_hover = palette.getRadioButtonPalette().getBackgroundHover();
        this.foregroundColor_hover = palette.getRadioButtonPalette().getForegroundHover();

        // Determine the colors to color the tiles with.
        final Color backgroundColor;
        final Color foregroundColor;

        if (isChecked) {
            backgroundColor = backgroundColor_pressed;
            foregroundColor = foregroundColor_pressed;
        } else {
            backgroundColor = backgroundColor_normal;
            foregroundColor = foregroundColor_normal;
        }

        for (int y = 0 ; y < super.tiles.getHeight() ; y++) {
            for (int x = 0 ; x < super.tiles.getWidth() ; x++) {
                final Tile tile = super.tiles.getTileAt(x, y);

                if (tile != null) {
                    tile.setBackgroundColor(backgroundColor);
                    tile.setForegroundColor(foregroundColor);
                }
            }
        }

        if (redraw) {
            try {
                redrawFunction.run();
            } catch (final IllegalStateException ignored) {
                /*
                 * If we set the color palette before the screen is displayed, then it'll throw...
                 *
                 *      IllegalStateException: Component must have a valid peer
                 *
                 * We can just ignore it in this case, because the screen will be drawn when it is displayed for
                 * the first time.
                 */
            }
        }
    }

    /**
     * Sets the radio button as checked.
     *
     * @param isChecked
     *          Whether or not the radio button is checked.
     */
    public void setChecked(final boolean isChecked) {
        this.isChecked = isChecked;
        
        final Tile firstTile = super.getTileAt(0, 0);

        if (firstTile == null) {
            return;
        }

        if (isChecked) {
            firstTile.setCharacter(checkedButtonChar);

            for (final Tile tile : super.tiles.getRow(0)) {
                tile.setBackgroundColor(backgroundColor_pressed);
                tile.setForegroundColor(foregroundColor_pressed);
            }
        } else {
            firstTile.setCharacter(emptyButtonChar);

            for (final Tile tile : super.tiles.getRow(0)) {
                tile.setBackgroundColor(backgroundColor_normal);
                tile.setForegroundColor(foregroundColor_normal);
            }
        }

        super.redrawFunction.run();
    }

    @Override
    public void setText(final String text) {
        final char[] chars = (text == null ? new char[0] : text.toCharArray());

        // The first two characters are the checked circle and a space, so we start after them.
        for (int x = 2 ; x < super.tiles.getWidth() ; x++) {
            final Tile tile = super.getTileAt(x, 0);

            if (tile == null) {
                continue;
            }

            if ((x - 2) < chars.length) {
                tile.setCharacter(chars[x - 2]);
            } else {
                tile.setCharacter(' ');
            }
        }
    }
}
