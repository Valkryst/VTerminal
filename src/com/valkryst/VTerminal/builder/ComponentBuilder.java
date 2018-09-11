package com.valkryst.VTerminal.builder;

import com.valkryst.VJSON.VJSONParser;
import com.valkryst.VTerminal.component.Component;
import com.valkryst.VTerminal.palette.*;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

import java.awt.*;

public class ComponentBuilder<C extends Component> implements VJSONParser {
    /** The position of the component within it's parent. */
    private Point position = new Point(0, 0);

    /** The dimensions of the component. */
    private Dimension dimensions = new Dimension(1, 1);

    /** The color palette to color the label with. */
    @Getter @Setter private ColorPalette colorPalette;

    /** Constructs a new ComponentBuilder. */
    public ComponentBuilder() {
        reset();
    }

    /**
     * Uses the builder to construct a new component.
     *
     * @return
     *         The new component.
     */
    public C build() {
        checkState();
        return null;
    }

    /** Checks the current state of the builder. */
    protected void checkState() {
        if (dimensions.width < 1) {
            dimensions.width = 1;
        }

        if (dimensions.height < 1) {
            dimensions.height = 1;
        }

        if (colorPalette == null) {
            colorPalette = new ColorPalette();
        }
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        position.setLocation(0, 0);
        dimensions.setSize(1, 1);
        colorPalette = new ColorPalette();
    }

    @Override
    public void parse(final JSONObject jsonObject) {
        if (jsonObject == null) {
            return;
        }

        reset();

        final Integer xPosition = getInteger(jsonObject, "x");
        final Integer yPosition = getInteger(jsonObject, "y");
        final Integer width = getInteger(jsonObject, "width");
        final Integer height = getInteger(jsonObject, "height");
        final String colorPalette = getString(jsonObject, "palette");

        if (xPosition == null) {
            throw new NullPointerException("The 'x' value was not found.");
        } else {
            position.x = xPosition;
        }

        if (yPosition == null) {
            throw new NullPointerException("The 'y' value was not found.");
        } else {
            position.y = yPosition;
        }

        if (width == null) {
            throw new NullPointerException("The 'width' value was not found.");
        } else {
            dimensions.width = width;
        }

        if (height == null) {
            throw new NullPointerException("The 'height' value was not found.");
        } else {
            dimensions.height = height;
        }

        // todo Support custom themes or maybe some generic way of writing the theme, rather than hardcoding them like this.
        switch (colorPalette) {
            case "P1Phosphor": {
                this.colorPalette = new P1PhosphorColorPalette();
                break;
            }
            case "P3Phosphor": {
                this.colorPalette = new P3PhosphorColorPalette();
                break;
            }
            case "P12Phosphor": {
                this.colorPalette = new P12PhosphorColorPalette();
                break;
            }
            case "P21Phosphor": {
                this.colorPalette = new P21PhosphorColorPalette();
                break;
            }
            case "P24Phosphor": {
                this.colorPalette = new P24PhosphorColorPalette();
                break;
            }
            default: {
                this.colorPalette = new ColorPalette();
            }
        }
    }

    /**
     * Retrieves a copy of the component's position.
     *
     * @return
     *          A copy of the component's position.
     */
    public Point getPosition() {
        return new Point(position);
    }

    /**
     * Retrieves a copy of the component's dimensions.
     *
     * @return
     *          A copy of the component's dimensions.
     */
    public Dimension getDimensions() {
        return new Dimension(dimensions);
    }

    /**
     * Retrieves the x-axis position of the component within it's parent.
     *
     * @return
     *          The x-axis position of the component within it's parent.
     */
    public int getXPosition() {
        return position.x;
    }

    /**
     * Retrieves the y-axis position of the component within it's parent.
     *
     * @return
     *          The y-axis position of the component within it's parent.
     */
    public int getYPosition() {
        return position.y;
    }

    /**
     * Retrieves the width of the component.
     *
     * @return
     *          The width of the component.
     */
    public int getWidth() {
        return dimensions.width;
    }

    /**
     * Retrieves the height of the component.
     *
     * @return
     *          The height of the component.
     */
    public int getHeight() {
        return dimensions.height;
    }

    /**
     * Sets the new position of the component within it's parent.
     *
     * @param point
     *          The new position.
     */
    public void setPosition(final Point point) {
        setPosition(point.x, point.y);
    }

    /**
     * Sets the new position of the component within it's parent.
     *
     * @param x
     *          The x-axis position.
     *
     * @param y
     *          The y-axis position.
     */
    public void setPosition(final int x, final int y) {
        if (x >= 0 && y >= 0) {
            position.setLocation(x, y);
        }
    }

    /**
     * Sets the new x-axis position of the component within it's parent.
     *
     * @param x
     *          The new x-axis position.
     */
    public void setXPosition(final int x) {
        if (x >= 0) {
            position.setLocation(x, position.y);
        }
    }

    /**
     * Sets the new y-axis position of the component within it's parent.
     *
     * @param y
     *          The new y-axis position.
     */
    public void setYPosition(final int y) {
        if (y >= 0) {
            position.setLocation(position.x, y);
        }
    }

    /**
     * Sets the new dimensions of the component.
     *
     * @param dimensions
     *          The new dimensions.
     */
    public void setDimensions(final Dimension dimensions) {
        setDimensions(dimensions.width, dimensions.height);
    }

    /**
     * Sets the new dimensions of the component.
     *
     * @param width
     *          The new width.
     *
     * @param height
     *          The new height.
     */
    public void setDimensions(final int width, final int height) {
        if (width > 0 && height > 0) {
            dimensions.setSize(width, height);
        }
    }

    /**
     * Sets the new width of the component.
     *
     * @param width
     *          The new width.
     */
    public void setWidth(final int width) {
        if (width > 0) {
            dimensions.setSize(width, dimensions.height);
        }
    }

    /**
     * Sets the new height of the component.
     *
     * @param height
     *          The new height.
     */
    public void setHeight(final int height) {
        if (height > 0) {
            dimensions.setSize(dimensions.width, height);
        }
    }
}
