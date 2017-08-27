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

        super.width = 1;
        super.height = 1;

        backgroundColor = new Color(45, 45, 45, 255);
        foregroundColor = Color.WHITE;
    }

    @Override
    public void parseJSON(final @NonNull JSONObject jsonObject) {
        reset();
        super.parseJSON(jsonObject);

        final Color foregroundColor = JSONFunctions.loadColorFromJSON((JSONArray) jsonObject.get("foregroundColor"));
        final Color backgroundColor = JSONFunctions.loadColorFromJSON((JSONArray) jsonObject.get("backgroundColor"));

        if (foregroundColor != null) {
            this.foregroundColor = foregroundColor;
        }

        if (backgroundColor != null) {
            this.backgroundColor = backgroundColor;
        }
    }
}
