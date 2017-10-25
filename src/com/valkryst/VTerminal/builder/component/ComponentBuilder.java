package com.valkryst.VTerminal.builder.component;

import com.valkryst.VJSON.VJSONParser;
import com.valkryst.VTerminal.component.Component;
import lombok.Data;
import lombok.NonNull;
import org.json.simple.JSONObject;

import java.util.concurrent.ThreadLocalRandom;

@Data
public class ComponentBuilder<C extends Component> implements VJSONParser {
    /** The ID. Not guaranteed to be unique. */
   private String id;

    /** The x-axis (column) coordinate of the top-left character. */
    private int columnIndex;
    /** The y-axis (row) coordinate of the top-left character. */
    private int rowIndex;

    /** The width, in characters. */
    protected int width;
    /** The height, in characters. */
    protected int height;

    /** Constructs a new ComponentBuilder. */
    public ComponentBuilder() {
        reset();
    }

    /**
     * Uses the builder to construct a new component.
     *
     * @return
     *         The new component.
     *
     * @throws IllegalStateException
     *          If something is wrong with the builder's state.
     */
    public C build() {
        checkState();
        return null;
    }

    /**
     * Checks the current state of the builder.
     *
     * @throws IllegalArgumentException
     *          If the column or row indices are less than zero.
     *
     * @throws NullPointerException
     *          If the panel is null.
     */
    protected void checkState() throws NullPointerException {
        if (columnIndex < 0) {
            throw new IllegalArgumentException("The column index cannot be less than zero.");
        }

        if (rowIndex < 0) {
            throw new IllegalArgumentException("The row index cannot be less than zero.");
        }

        if (width < 1) {
            throw new IllegalArgumentException("You must specify a width of 1 or greater.");
        }

        if (height < 1) {
            throw new IllegalArgumentException("You must specify a height of 1 or greater.");
        }
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        id = "No ID Set. Random ID = " + ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);

        columnIndex = 0;
        rowIndex = 0;
        width = 1;
        height = 1;
    }

    @Override
    public void parse(final @NonNull JSONObject jsonObject) {
        reset();

        final String id = (String) jsonObject.get("id");

        final Integer columnIndex = getInteger(jsonObject, "column");
        final Integer rowIndex = getInteger(jsonObject, "row");
        final Integer width = getInteger(jsonObject, "width");
        final Integer height = getInteger(jsonObject, "height");


        if (id != null) {
            this.id = id;
        }


        if (columnIndex != null) {
            this.columnIndex = columnIndex;
        }

        if (rowIndex != null) {
            this.rowIndex = rowIndex;
        }

        if (width != null) {
            this.width = width;
        }

        if (height != null) {
            this.height = height;
        }
    }
}
