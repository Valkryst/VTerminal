package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.Label;
import lombok.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.Color;

@EqualsAndHashCode(callSuper=true)
@ToString
public class LabelBuilder extends ComponentBuilder<Label> {
    /** The text to display on the label. */
    @Getter @Setter @NonNull private String text;

    @Getter @Setter private boolean underlined;

    /** The background color for when the label. */
    @Getter @Setter @NonNull private Color backgroundColor;
    /** The foreground color for when the label. */
    @Getter @Setter @NonNull private Color foregroundColor;

    @Override
    public Label build() {
        checkState();
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

        final Color backgroundColor = super.loadColorFromJSON((JSONArray) jsonObject.get("backgroundColor"));
        final Color foregroundColor = super.loadColorFromJSON((JSONArray) jsonObject.get("foregroundColor"));


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
