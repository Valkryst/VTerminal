package com.valkryst.VTerminal.builder.component;

import com.valkryst.VJSON.VJSONParser;
import com.valkryst.VTerminal.component.Button;
import com.valkryst.VTerminal.misc.ColorFunctions;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.Color;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class ButtonBuilder extends ComponentBuilder<Button> implements VJSONParser {
    /** The text to display on the button. */
    @NonNull private String text;

    /** The background color for when the button is in the normal state. */
    @NonNull private Color backgroundColor_normal;
    /** The foreground color for when the button is in the normal state. */
    @NonNull private Color foregroundColor_normal;

    /** The background color for when the button is in the hover state. */
    @NonNull private Color backgroundColor_hover;
    /** The foreground color for when the button is in the hover state. */
    @NonNull private Color foregroundColor_hover;

    /** The background color for when the button is in the pressed state. */
    @NonNull private Color backgroundColor_pressed;
    /** The foreground color for when the button is in the pressed state. */
    @NonNull private Color foregroundColor_pressed;

    /** The function to run when the button is clicked. */
    private Runnable onClickFunction;

    @Override
    public Button build() {
        checkState();

        super.width = text.length();
        super.height = 1;

        return new Button(this);
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        text = "";

        backgroundColor_normal = new Color(45, 45, 45, 255);
        foregroundColor_normal = new Color(0xFF2DBEFF, true);

        backgroundColor_hover = new Color(0xFF2DFF63, true);
        foregroundColor_hover = ColorFunctions.shade(backgroundColor_hover, 0.5);

        backgroundColor_pressed = ColorFunctions.shade(backgroundColor_hover, 0.25);
        foregroundColor_pressed = ColorFunctions.shade(foregroundColor_hover, 0.25);

        onClickFunction = () -> {};
    }

    @Override
    public void parse(final @NonNull JSONObject jsonObject) {
        reset();
        super.parse(jsonObject);


        final String text = getString(jsonObject, "text");


        if (text != null) {
            this.text = text;
        }


        try {
            this.backgroundColor_normal = getColor((JSONArray) jsonObject.get("backgroundColor_normal"));
        } catch (final NullPointerException ignored) {}

        try {
            this.foregroundColor_normal = getColor((JSONArray) jsonObject.get("foregroundColor_normal"));
        } catch (final NullPointerException ignored) {}



        try {
            this.backgroundColor_hover = getColor((JSONArray) jsonObject.get("backgroundColor_hover"));
        } catch (final NullPointerException ignored) {}

        try {
            this.foregroundColor_hover = getColor((JSONArray) jsonObject.get("foregroundColor_hover"));
        } catch (final NullPointerException ignored) {}



        try {
            this.backgroundColor_pressed = getColor((JSONArray) jsonObject.get("backgroundColor_pressed"));
        } catch (final NullPointerException ignored) {}

        try {
            this.foregroundColor_pressed = getColor((JSONArray) jsonObject.get("foregroundColor_pressed"));
        } catch (final NullPointerException ignored) {}
    }
}
