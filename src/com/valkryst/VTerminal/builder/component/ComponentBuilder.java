package com.valkryst.VTerminal.builder.component;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.component.Component;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.misc.JSONFunctions;
import lombok.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Data
public class ComponentBuilder<C extends Component> {
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

    /** The radio to transmit events to. */
    @NonNull private Radio<String> radio;

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
     * Loads a portion of the builder's data from a JSON file within the Jar.
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
        final Integer width = JSONFunctions.getIntElement(jsonObject, "width");
        final Integer height = JSONFunctions.getIntElement(jsonObject, "height");


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
