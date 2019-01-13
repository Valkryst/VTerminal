package com.valkryst.VTerminal.builder;

import com.valkryst.VJSON.VJSON;
import com.valkryst.VTerminal.component.Component;
import com.valkryst.VTerminal.palette.java2d.Java2DPalette;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.util.UUID;

public abstract class ComponentBuilder<C extends Component> {
    /** The ID of the component. Not guaranteed to be unique. */
    private String id;

    /** The position of the component within it's parent. */
    private Point position = new Point(0, 0);

    /** The dimensions of the component. */
    private Dimension dimensions = new Dimension(1, 1);

    /** The color palette to color the label with. */
    @Getter @Setter private Java2DPalette palette;

    /** Constructs a new ComponentBuilder. */
    public ComponentBuilder() {
        reset();
    }

    /**
     * Constructs a new ComponentBuilder and initializes it using the JSON representation of a component.
     *
     * @param json
     *          The JSON representation of a component.
     */
    public ComponentBuilder(final JSONObject json) {
        if (json == null) {
            return;
        }

        final String id = VJSON.getString(json, "Id");
        final Integer x = VJSON.getInt(json, "X");
        final Integer y = VJSON.getInt(json, "Y");
        final Integer width = VJSON.getInt(json, "Width");
        final Integer height = VJSON.getInt(json, "Height");
        final String colorPalette = VJSON.getString(json, "Color Palette");

        this.id = (id == null || id.isEmpty() ? UUID.randomUUID().toString() : id);
        this.position.setLocation((x == null ? 0 : x), (y == null ? 0 : y));
        this.dimensions.setSize((width == null ? 0 : width), (height == null ? 0 : height));

        if (colorPalette == null) {
            try {
                this.palette = new Java2DPalette();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            try {
                this.palette = new Java2DPalette(colorPalette);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
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
        id = UUID.randomUUID().toString();

        if (dimensions.width < 1) {
            dimensions.width = 1;
        }

        if (dimensions.height < 1) {
            dimensions.height = 1;
        }

        if (palette == null) {
            try {
                palette = new Java2DPalette();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        position.setLocation(0, 0);
        dimensions.setSize(1, 1);
        try {
            palette = new Java2DPalette();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
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
