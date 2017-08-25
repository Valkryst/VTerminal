package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.Layer;
import com.valkryst.VTerminal.misc.JSONFunctions;
import lombok.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.Color;

@EqualsAndHashCode(callSuper=true)
@ToString
public class LayerBuilder extends ComponentBuilder<Layer> {
    /** The width of the layer, in characters. */
    @Getter @Setter private int width;
    /** The height of the layer, in characters. */
    @Getter @Setter private int height;

    /** The foreground color. */
    @Getter @Setter @NonNull private Color foregroundColor;
    /** The background color. */
    @Getter @Setter @NonNull private Color backgroundColor;

    @Override
    public Layer build() {
        checkState();
        return new Layer(this);
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        width = 1;
        height = 1;

        foregroundColor = Color.BLACK;
        backgroundColor = Color.WHITE;
    }

    @Override
    public void parseJSON(final @NonNull JSONObject jsonObject) {
        reset();
        super.parseJSON(jsonObject);


        final Integer width = JSONFunctions.getIntElement(jsonObject, "width");
        final Integer height = JSONFunctions.getIntElement(jsonObject, "height");

        final Color foregroundColor = super.loadColorFromJSON((JSONArray) jsonObject.get("foregroundColor"));
        final Color backgroundColor = super.loadColorFromJSON((JSONArray) jsonObject.get("backgroundColor"));


        if (width != null) {
            this.width = width;
        }

        if (height != null) {
            this.height = height;
        }


        if (foregroundColor != null) {
            this.foregroundColor = foregroundColor;
        }

        if (backgroundColor != null) {
            this.backgroundColor = backgroundColor;
        }
    }
}
