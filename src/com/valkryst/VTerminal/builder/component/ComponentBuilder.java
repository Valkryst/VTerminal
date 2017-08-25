package com.valkryst.VTerminal.builder.component;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.component.Component;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.misc.JSONFunctions;
import lombok.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.Color;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@EqualsAndHashCode
@ToString
public class ComponentBuilder<C extends Component> {
    /** The ID. Not guaranteed to be unique. */
    @Getter @Setter private String id;

    /** The x-axis (column) coordinate of the top-left character. */
    @Getter @Setter private int columnIndex;
    /** The y-axis (row) coordinate of the top-left character. */
    @Getter @Setter private int rowIndex;

    /** The radio to transmit events to. */
    @Getter @Setter @NonNull private Radio<String> radio;

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
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        id = "No ID Set. Random ID = " + ThreadLocalRandom.current().nextLong(Long.MAX_VALUE);

        columnIndex = 0;
        rowIndex = 0;

        radio = null;
    }

    /**
     * Loads a portion of the builder's data from JSON.
     *
     * Resets the builder's state before loading.
     *
     * @param jsonFilePath
     *        The path to the JSON file.
     *
     * @throws FileNotFoundException
     *         If the file does not exist, is a directory rather
     *         than a regular file, or for some other reason cannot
     *         be opened for reading.
     *
     * @throws ParseException
     *         If there's an error when parsing the JSON.
     */
    public void loadFromJSON(final @NonNull String jsonFilePath) throws ParseException, FileNotFoundException {
        if (jsonFilePath.isEmpty()) {
            throw new IllegalArgumentException("The JSON file path cannot be empty.");
        }

        loadFromJSON(new FileInputStream(jsonFilePath));
    }

    /**
     * Loads a portion of the builder's data from a JSON file
     * within the Jar.
     *
     * Resets the builder's state before loading.
     *
     * @param jsonFilePath
     *        The path to the JSON file.
     *
     * @throws ParseException
     *         If there's an error when parsing the JSON.
     */
    public void loadFromJSONInJar(final @NonNull String jsonFilePath) throws ParseException {
        if (jsonFilePath.isEmpty()) {
            throw new IllegalArgumentException("The JSON file path cannot be empty.");
        }

        final ClassLoader classLoader = FontLoader.class.getClassLoader();
        final InputStream jsonFileStream = classLoader.getResourceAsStream(jsonFilePath);

        loadFromJSON(jsonFileStream);
    }

    /**
     * Loads a portion of the builder's data from JSON.
     *
     * Resets the builder's state before loading.
     *
     * @param jsonFileStream
     *        The JSON input stream.
     *
     * @throws ParseException
     *         If there's an error when parsing the JSON.
     */
    public void loadFromJSON(final @NonNull InputStream jsonFileStream) throws ParseException {
        // Load lines
        final InputStreamReader isr = new InputStreamReader(jsonFileStream, StandardCharsets.UTF_8);
        final BufferedReader br = new BufferedReader(isr);
        final List<String> lines = br.lines().collect(Collectors.toList());

        parseJSON(String.join("\n", lines));
    }

    /**
     * Loads a portion of the builder's data from JSON.
     *
     * Resets the builder's state before loading.
     *
     * @param jsonData
     *        The JSON.
     *
     * @throws ParseException
     *         If there's an error when parsing the JSON.
     */
    public void parseJSON(@NonNull String jsonData) throws ParseException {
        // Remove comments from JSON:
        jsonData = jsonData.replaceAll("/\\*.*\\*/", ""); // Strip '/**/' comments
        jsonData = jsonData.replaceAll("//.*(?=\\n)", ""); // Strip '//' comments

        final JSONParser parser = new JSONParser();
        final Object object = parser.parse(jsonData);

        parseJSON((JSONObject) object);
    }

    /**
     * Loads a portion of the builder's data from JSON.
     *
     * Resets the builder's state before loading.
     *
     * @param jsonObject
     *        The JSON.
     */
    public void parseJSON(final @NonNull JSONObject jsonObject) {
        reset();

        final String id = (String) jsonObject.get("id");

        final Integer columnIndex = JSONFunctions.getIntElement(jsonObject, "columnIndex");
        final Integer rowIndex = JSONFunctions.getIntElement(jsonObject, "rowIndex");


        if (id != null) {
            this.id = id;
        }


        if (columnIndex != null) {
            this.columnIndex = columnIndex;
        }

        if (rowIndex != null) {
            this.rowIndex = rowIndex;
        }
    }

    /**
     * Loads a Color from a JSON array.
     *
     * The array is loaded as [red, green, blue] or a [red, green, blue, alpha].
     * Any values after alpha are ignored.
     *
     * @param jsonArray
     *        The array of color values.
     *
     * @return
     *        The Color or null if the jsonArray is null.
     *
     * @throws IllegalStateException
     *        If the array contains fewer than three values.
     */
    public Color loadColorFromJSON(final JSONArray jsonArray) {
        if (jsonArray == null) {
            return null;
        }

        if (jsonArray.size() >= 3) {
            final Integer red = (int) (long) jsonArray.get(0);
            final Integer green = (int) (long) jsonArray.get(1);
            final Integer blue = (int) (long) jsonArray.get(2);
            Integer alpha = jsonArray.size() >= 4 ? (int) (long) jsonArray.get(3) : 255;

            return new Color(red, green, blue, alpha);
        }

        throw new IllegalStateException("Cannot load a color with fewer than 3 values.");
    }
}
