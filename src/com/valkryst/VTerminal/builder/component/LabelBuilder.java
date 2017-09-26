package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.Label;
import com.valkryst.VTerminal.misc.JSONFunctions;
import lombok.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.Color;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class LabelBuilder extends ComponentBuilder<Label> {
    /** The text to display on the label. */
    @NonNull private String text;

    private boolean underlined;

    /** The background color for when the label. */
    @NonNull private Color backgroundColor;
    /** The foreground color for when the label. */
    @NonNull private Color foregroundColor;

    @Override
    public Label build() {
        checkState();

        super.width = text.length();
        super.height = 1;

        return new Label(this);
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        text = "";

        underlined = false;

        backgroundColor = new Color(45, 45, 45, 255);
        foregroundColor = new Color(0xFFF9CA00, true);
    }

    @Override
    public void parseJSON(final @NonNull JSONObject jsonObject) {
        reset();
        super.parseJSON(jsonObject);


        final String text = (String) jsonObject.get("text");

        final Boolean underlined = (Boolean) jsonObject.get("underlined");

        final Color backgroundColor = JSONFunctions.loadColorFromJSON((JSONArray) jsonObject.get("backgroundColor"));
        final Color foregroundColor = JSONFunctions.loadColorFromJSON((JSONArray) jsonObject.get("foregroundColor"));


        if (text != null) {
            this.text = text;
        }


        if (underlined != null) {
            this.underlined = underlined;
        }


        if (backgroundColor != null) {
            this.backgroundColor = backgroundColor;
        }

        if (foregroundColor != null) {
            this.foregroundColor = foregroundColor;
        }
    }
}
