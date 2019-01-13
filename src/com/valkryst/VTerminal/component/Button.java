package com.valkryst.VTerminal.component;

import com.valkryst.VTerminal.Screen;
import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.builder.ButtonBuilder;
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
public class Button extends Component {
    /** Whether or not the button is in the normal state. */
    private boolean isInNormalState = true;
    /** whether or not the button is in the hovered state. */
    private boolean isInHoveredState = false;
    /** Whether or not the button is in the pressed state. */
    private boolean isInPressedState = false;

    /** The background color for when the button is in the normal state. */
    protected Color backgroundColor_normal;
    /** The foreground color for when the button is in the normal state. */
    protected Color foregroundColor_normal;

    /** The background color for when the button is in the hover state. */
    protected Color backgroundColor_hover;
    /** The foreground color for when the button is in the hover state. */
    protected Color foregroundColor_hover;

    /** The background color for when the button is in the pressed state. */
    protected Color backgroundColor_pressed;
    /** The foreground color for when the button is in the pressed state. */
    protected Color foregroundColor_pressed;

    /** The function to run when the button is clicked. */
    @Getter @Setter @NonNull private Runnable onClickFunction;

    /**
     * Constructs a new AsciiButton.
     *
     * @param builder
     *         The builder to use.
     *
     * @throws NullPointerException
     *         If the builder is null.
     */
    public Button(final @NonNull ButtonBuilder builder) {
        super(builder.getDimensions(), builder.getPosition());

        setPalette(builder.getPalette(), false);

        this.onClickFunction = builder.getOnClickFunction();

        // Set the button's text:
        final char[] text = builder.getText().toCharArray();
        final Tile[] tiles = super.tiles.getRow(0);

        for (int x = 0; x < tiles.length; x++) {
            tiles[x].setCharacter(text[x]);
            tiles[x].setBackgroundColor(backgroundColor_normal);
            tiles[x].setForegroundColor(foregroundColor_normal);
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
                        setStatePressed();
                    }
                }
            }

            @Override
            public void mouseReleased(final MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (isInPressedState) {
                        onClickFunction.run();
                    }

                    if (intersects(parentScreen.getMousePosition())) {
                        setStateHovered();
                    } else {
                        setStateNormal();
                    }
                }
            }

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
                    setStateNormal();
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

        this.backgroundColor_normal = palette.getButtonPalette().getBackground();
        this.foregroundColor_normal = palette.getButtonPalette().getForeground();

        this.backgroundColor_pressed = palette.getButtonPalette().getBackgroundPressed();
        this.foregroundColor_pressed = palette.getButtonPalette().getForegroundPressed();

        this.backgroundColor_hover = palette.getButtonPalette().getBackgroundHover();
        this.foregroundColor_hover = palette.getButtonPalette().getForegroundHover();

        // Determine the colors to color the tiles with.
        final Color backgroundColor;
        final Color foregroundColor;

        if (isInPressedState) {
            backgroundColor = backgroundColor_pressed;
            foregroundColor = foregroundColor_pressed;
        } else if (isInHoveredState) {
            backgroundColor = backgroundColor_hover;
            foregroundColor = foregroundColor_hover;
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

    /** Sets the button state to normal if the current state allows for the normal state to be set. */
    protected void setStateNormal() {
        boolean canSetState = isInNormalState == false;
        canSetState &= isInHoveredState || isInPressedState;

        if (canSetState) {
            isInNormalState = true;
            isInHoveredState = false;
            isInPressedState = false;

            setColors(backgroundColor_normal, foregroundColor_normal);
            redrawFunction.run();
        }
    }

    /** Sets the button state to hovered if the current state allows for the normal state to be set. */
    protected void setStateHovered() {
        boolean canSetState = isInNormalState || isInPressedState;
        canSetState &= isInHoveredState == false;

        if (canSetState) {
            isInNormalState = false;
            isInHoveredState = true;
            isInPressedState = false;

            setColors(backgroundColor_hover, foregroundColor_hover);
            redrawFunction.run();
        }
    }

    /** Sets the button state to pressed if the current state allows for the normal state to be set. */
    protected void setStatePressed() {
        boolean canSetState = isInNormalState || isInHoveredState;
        canSetState &= isInPressedState == false;

        if (canSetState) {
            isInNormalState = false;
            isInHoveredState = false;
            isInPressedState = true;

            setColors(backgroundColor_pressed, foregroundColor_pressed);
            redrawFunction.run();
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
     */
    private void setColors(final Color backgroundColor, final Color foregroundColor) {
        for (final Tile tile : super.tiles.getRow(0)) {
            if (backgroundColor != null) {
                tile.setBackgroundColor(backgroundColor);
            }

            if (foregroundColor != null) {
                tile.setForegroundColor(foregroundColor);
            }
        }
    }

    /**
     * Sets the text displayed on the button.
     *
     * The button will not change in size, so it will not show more text than it can currently display.
     *
     * @param text
     *          The new text.
     */
    public void setText(final String text) {
        final char[] chars = (text == null ? new char[0] : text.toCharArray());

        for (int x = 0 ; x < super.tiles.getWidth() ; x++) {
            final Tile tile = super.getTileAt(x, 0);

            if (tile == null) {
                continue;
            }

            if (x < chars.length) {
                tile.setCharacter(chars[x]);
            } else {
                tile.setCharacter(' ');
            }
        }
    }
}