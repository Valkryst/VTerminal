package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.Button;
import com.valkryst.VTerminal.misc.ColorFunctions;
import lombok.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.Color;

@EqualsAndHashCode(callSuper=true)
@ToString
public class ButtonBuilder extends ComponentBuilder<Button> {
    /** The text to display on the button. */
    @Getter @Setter @NonNull private String text;

    /** The first character of the button's text. This is used to identify the text as a button. */
    @Getter @Setter private char startingCharacter;
    /** The last character of the button's text. This is used to identify the text as a button. */
    @Getter @Setter private char endingCharacter;

    /** Whether or not the button should use the starting/ending characters. */
    @Getter @Setter private boolean usingStartingAndEndingCharacters;

    /** The background color for when the button is in the normal state. */
    @Getter @Setter @NonNull private Color backgroundColor_normal;
    /** The foreground color for when the button is in the normal state. */
    @Getter @Setter @NonNull private Color foregroundColor_normal;

    /** The background color for when the button is in the hover state. */
    @Getter @Setter @NonNull private Color backgroundColor_hover;
    /** The foreground color for when the button is in the hover state. */
    @Getter @Setter @NonNull private Color foregroundColor_hover;

    /** The background color for when the button is in the pressed state. */
    @Getter @Setter @NonNull private Color backgroundColor_pressed;
    /** The foreground color for when the button is in the pressed state. */
    @Getter @Setter @NonNull private Color foregroundColor_pressed;

    /** The function to run when the button is clicked. */
    @Getter @Setter private Runnable onClickFunction;

    @Override
    public Button build() {
        checkState();
        return new Button(this);
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        text = "";

        startingCharacter = '<';
        endingCharacter = '>';

        usingStartingAndEndingCharacters = true;

        backgroundColor_normal = new Color(45, 45, 45, 255);
        foregroundColor_normal = new Color(0xFF2DBEFF, true);

        backgroundColor_hover = new Color(0xFF2DFF63, true);
        foregroundColor_hover = ColorFunctions.shade(backgroundColor_hover, 0.5);

        backgroundColor_pressed = ColorFunctions.shade(backgroundColor_hover, 0.25);
        foregroundColor_pressed = ColorFunctions.shade(foregroundColor_hover, 0.25);

        onClickFunction = () -> {};
    }

    @Override
    public void parseJSON(final JSONObject jsonObject) {
        reset();
        super.parseJSON(jsonObject);


        final String text = (String) jsonObject.get("text");

        final Character startingCharacter = (Character) jsonObject.get("startingCharacter");
        final Character endingCharacter = (Character) jsonObject.get("endingCharacter");

        final Boolean usingStartingAndEndingCharacters = (Boolean) jsonObject.get("usingStartingAndEndingCharacters");

        final Color backgroundColor_normal = super.loadColorFromJSON((JSONArray) jsonObject.get("backgroundColor_normal"));
        final Color foregroundColor_normal = super.loadColorFromJSON((JSONArray) jsonObject.get("foregroundColor_normal"));

        final Color backgroundColor_hover = super.loadColorFromJSON((JSONArray) jsonObject.get("backgroundColor_hover"));
        final Color foregroundColor_hover = super.loadColorFromJSON((JSONArray) jsonObject.get("foregroundColor_hover"));

        final Color backgroundColor_pressed = super.loadColorFromJSON((JSONArray) jsonObject.get("backgroundColor_pressed"));
        final Color foregroundColor_pressed = super.loadColorFromJSON((JSONArray) jsonObject.get("foregroundColor_pressed"));


        if (text != null) {
            this.text = text;
        }



        if (startingCharacter != null) {
            this.startingCharacter = startingCharacter;
        }

        if (endingCharacter != null) {
            this.endingCharacter = endingCharacter;
        }



        if (usingStartingAndEndingCharacters != null) {
            this.usingStartingAndEndingCharacters = usingStartingAndEndingCharacters;
        }



        if (backgroundColor_normal != null) {
            this.backgroundColor_normal = backgroundColor_normal;
        }

        if (foregroundColor_normal != null) {
            this.foregroundColor_normal = foregroundColor_normal;
        }



        if (backgroundColor_hover != null) {
            this.backgroundColor_hover = backgroundColor_hover;
        }

        if (foregroundColor_hover != null) {
            this.foregroundColor_hover = foregroundColor_hover;
        }



        if (backgroundColor_pressed != null) {
            this.backgroundColor_pressed = backgroundColor_pressed;
        }

        if (foregroundColor_pressed != null) {
            this.backgroundColor_pressed = backgroundColor_pressed;
        }
    }
}
