package com.valkryst.AsciiPanel.component;

import com.valkryst.AsciiPanel.AsciiFont;
import com.valkryst.AsciiPanel.AsciiPanel;
import com.valkryst.AsciiPanel.AsciiString;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class AsciiButton extends AsciiComponent {
    /** Whether or not the button is in the normal state. */
    private boolean isInNormalState = true;
    /** whether or not the button is in the hovered state. */
    private boolean isInHoveredState = false;
    /** Whether or not the button is in the pressed state. */
    private boolean isInPressedState = false;

    /** The background color for when the button is in the normal state. */
    private Paint backgroundColor_normal = Color.BLACK;
    /** The foreground color for when the button is in the normal state. */
    private Paint foregroundColor_normal = Color.WHITE;

    /** The background color for when the button is in the hover state. */
    private Paint backgroundColor_hover = Color.YELLOW;
    /** The foreground color for when the button is in the hover state. */
    private Paint foregroundColor_hover = Color.BLACK;

    /** The background color for when the button is in the pressed state. */
    private Paint backgroundColor_pressed = Color.WHITE;
    /** The foreground color for when the button is in the pressed state. */
    private Paint foregroundColor_pressed = Color.BLACK;

    /**
     * Constructs a new AsciiButton.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate of the top-left character.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate of the top-left character.
     *
     * @param width
     *         Thw width, in characters.
     *
     * @param height
     *         The height, in characters.
     */
    public AsciiButton(final int columnIndex, final int rowIndex, final int width, final int height) {
        super(columnIndex, rowIndex, width, height);
        setColors(backgroundColor_normal, foregroundColor_normal);
    }

    /**
     * Registers events, required by the button, with the specified panel.
     *
     * @param panel
     *         The panel to register events with.
     */
    public void registerEventHandlers(final AsciiPanel panel) {
        final AsciiFont font = panel.getFont();
        final int fontWidth = font.getWidth();
        final int fontHeight = font.getHeight();

        panel.addEventFilter(MouseEvent.MOUSE_MOVED, event -> {
            if (intersects(event, fontWidth, fontHeight)) {
                setStateHovered(panel);
            } else {
                setStateNormal(panel);
            }
        });

        panel.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (intersects(event, fontWidth, fontHeight)) {
                setStatePressed(panel);
            }
        });

        panel.addEventFilter(MouseEvent.MOUSE_RELEASED, event -> {
            if (intersects(event, fontWidth, fontHeight)) {
                setStateHovered(panel);
            } else {
                setStateNormal(panel);
            }
        });
    }

    /**
     * Determines whether or not the specified mouse event is at a point that intersects this button.
     *
     * @param event
     *         The event.
     *
     * @param fontWidth
     *         The width of the font being used to draw the button's characters.
     *
     * @param fontHeight
     *         The height of the font being used to draw the button's characters.
     *
     * @return
     *         Whether or not the mouse event is at a point that intersects this button.
     */
    private boolean intersects(final MouseEvent event, final int fontWidth, final int fontHeight) {
        final int mouseX = (int) (event.getX() / fontWidth);
        final int mouseY = (int) (event.getY() / fontHeight);
        return super.intersects(mouseX, mouseY);
    }

    /**
     * Sets the button state to normal if the current state allows for the normal state to be set.
     *
     * @param panel
     *         todo JavaDoc
     */
    private void setStateNormal(final AsciiPanel panel) {
        boolean canSetState = isInNormalState == false;
        canSetState &= isInHoveredState || isInPressedState;

        if (canSetState) {
            isInNormalState = true;
            isInHoveredState = false;
            isInPressedState = false;

            setColors(backgroundColor_normal, foregroundColor_normal);
            panel.draw();
        }
    }

    /**
     * Sets the button state to hovered if the current state allows for the normal state to be set.
     *
     * @param panel
     *         todo JavaDoc
     */
    private void setStateHovered(final AsciiPanel panel) {
        boolean canSetState = isInNormalState || isInPressedState;
        canSetState &= isInHoveredState == false;

        if (canSetState) {
            isInNormalState = false;
            isInHoveredState = true;
            isInPressedState = false;

            setColors(backgroundColor_hover, foregroundColor_hover);
            panel.draw();
        }
    }

    /**
     * Sets the button state to pressed if the current state allows for the normal state to be set.
     *
     * @param panel
     *         todo JavaDoc
     */
    private void setStatePressed(final AsciiPanel panel) {
        boolean canSetState = isInNormalState || isInHoveredState;
        canSetState &= isInPressedState == false;

        if (canSetState) {
            isInNormalState = false;
            isInHoveredState = false;
            isInPressedState = true;

            setColors(backgroundColor_pressed, foregroundColor_pressed);
            panel.draw();
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
    private void setColors(final Paint backgroundColor, final Paint foregroundColor) {
        for (final AsciiString s : super.getStrings()) {
            s.setBackgroundColor(backgroundColor);
            s.setForegroundColor(foregroundColor);
        }
    }
}
