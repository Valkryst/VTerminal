package com.valkryst.VTerminal.component;


import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.builder.CheckBoxBuilder;
import com.valkryst.VTerminal.palette.java2d.Java2DPalette;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.awt.*;
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

        setPalette(builder.getPalette(), false);

        // Set the check box's colors:
        if (isChecked) {
            final Tile firstTile = super.getTileAt(0, 0);
            
            if (firstTile == null) {
                return;
            }

            firstTile.setCharacter(checkedBoxChar);

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

        final MouseListener mouseListener = new MouseListener() {
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

        this.backgroundColor_normal = palette.getCheckBoxPalette().getBackground();
        this.foregroundColor_normal = palette.getCheckBoxPalette().getForeground();

        this.backgroundColor_pressed = palette.getCheckBoxPalette().getBackgroundPressed();
        this.foregroundColor_pressed = palette.getCheckBoxPalette().getForegroundPressed();

        this.backgroundColor_hover = palette.getCheckBoxPalette().getBackgroundHover();
        this.foregroundColor_hover = palette.getCheckBoxPalette().getForegroundHover();

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
                final Tile tile = super.getTileAt(x, y);
                
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
     * Sets the checked state.
     *
     * @param isChecked
     *          Whether or not the check box is checked.
     */
    public void setChecked(final boolean isChecked) {
        this.isChecked = isChecked;
        
        final Tile tile = super.getTileAt(0, 0);
        
        if (tile == null) {
            return;
        }

        if (isChecked) {
            tile.setCharacter(checkedBoxChar);
        } else {
            tile.setCharacter(emptyBoxChar);
        }

        super.redrawFunction.run();
    }

    @Override
    public void setText(final String text) {
        final char[] chars = (text == null ? new char[0] : text.toCharArray());

        // The first two characters are the checked box and a space, so we start after them.
        for (int x = 2 ; x < super.tiles.getWidth() ; x++) {
            if ((x - 2) < chars.length) {
                super.getTileAt(x, 0).setCharacter(chars[x - 2]);
            } else {
                super.getTileAt(x, 0).setCharacter(' ');
            }
        }
    }
}
