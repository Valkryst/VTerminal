package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.Screen;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;

@Data
@EqualsAndHashCode(callSuper=true)
public class ScreenBuilder extends ComponentBuilder<Screen> {
    /** The JSON definition of a GUI. */
    private JSONObject jsonObject;

    /** Constructs a new ScreenBuilder. */
    public ScreenBuilder() {}

    /**
     * Constructs a new ScreenBuilder.
     *
     * @param width
     *        @see ComponentBuilder#width
     *
     * @param height
     *        @see ComponentBuilder#height
     */
    public ScreenBuilder(final int width, final int height) {
        super.width = width;
        super.height = height;
    }

    /**
     * Constructs a ScreenBuilder from JSON.
     *
     * @see ComponentBuilder#loadFromJSON(String)
     *
     * @param jsonFilePath
     *        The path to the JSON file.
     *
     * @param isInJar
     *        Whether or not to load the JSON from within the Jar file.
     *
     * @throws FileNotFoundException
     *         If the file does not exist, is a directory rather
     *         than a regular file, or for some other reason cannot
     *         be opened for reading.
     *
     * @throws ParseException
     *         If there's an error when parsing the JSON.
     */
    public ScreenBuilder(final @NonNull String jsonFilePath, final boolean isInJar) throws FileNotFoundException, ParseException {
        if (isInJar) {
            super.loadFromJSONInJar(jsonFilePath);
        } else {
            super.loadFromJSON(jsonFilePath);
        }
    }

    @Override
    public Screen build() {
        checkState();
        return new Screen(this);
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        super.width = 80;
        super.height = 24;
    }

    @Override
    public void parseJSON(final @NonNull JSONObject jsonObject) {
        this.jsonObject = jsonObject;
        super.parseJSON(jsonObject);
    }
}
