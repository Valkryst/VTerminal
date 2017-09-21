package com.valkryst.VTerminal.component;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.builder.component.*;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.misc.ImageCache;
import com.valkryst.VTerminal.printer.RectanglePrinter;
import lombok.NonNull;
import lombok.ToString;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.LinkedHashSet;
import java.util.Set;

@ToString
public class Screen extends Component {
    /** The non-layer components displayed on the screen. */
    private Set<Component> components = new LinkedHashSet<>();

    /** The layer components displayed on the screen. */
    private Set<Layer> layerComponents = new LinkedHashSet<>();

    /**
     * Constructs a new Screen.
     *
     * @param builder
     *         The builder to use.
     *
     * @throws NullPointerException
     *         If the builder is null.
     */
    public Screen (final @NonNull ScreenBuilder builder) {
        super(builder);

        setBackgroundColor(new Color(45, 45, 45, 255));

        if (builder.getJsonObject() != null) {
            parseJSON(builder.getJsonObject());
        }
    }

    private void parseJSON(final @NonNull JSONObject jsonObject) {
        final JSONArray components = (JSONArray) jsonObject.get("components");

        if (components != null) {
            for (final Object obj : components) {
                final JSONObject arrayElement = (JSONObject) obj;

                if (arrayElement != null) {
                    final ComponentBuilder componentBuilder = loadElementFromJSON(arrayElement, super.getRadio());

                    if (componentBuilder != null) {
                        if (componentBuilder instanceof LayerBuilder) {
                            layerComponents.add((Layer) componentBuilder.build());
                        } else {
                            this.components.add(componentBuilder.build());
                        }
                    }
                }
            }
        }
    }

    /**
     * Loads an element from it's JSON representation.
     *
     * @param jsonObject
     *        The JSON.
     *
     * @param radio
     *        The radio for the element to use, if necessary.
     *
     * @return
     *        The component.
     *
     * @throws IllegalArgumentException
     *        If the type of the element isn't supported.
     */
    private ComponentBuilder loadElementFromJSON(final @NonNull JSONObject jsonObject, final @NonNull Radio<String> radio) {
        String componentType = (String) jsonObject.get("type");

        if (componentType == null) {
            return null;
        }

        componentType = componentType.toLowerCase();

        switch (componentType) {
            case "button": {
                final ButtonBuilder buttonBuilder = new ButtonBuilder();
                buttonBuilder.parseJSON(jsonObject);
                buttonBuilder.setRadio(radio);
                return buttonBuilder;
            }

            case "check box": {
                final CheckBoxBuilder checkBoxBuilder = new CheckBoxBuilder();
                checkBoxBuilder.parseJSON(jsonObject);
                checkBoxBuilder.setRadio(radio);
                return checkBoxBuilder;
            }

            case "label": {
                final LabelBuilder labelBuilder = new LabelBuilder();
                labelBuilder.parseJSON(jsonObject);
                labelBuilder.setRadio(radio);
                return labelBuilder;
            }

            case "layer": {
                final LayerBuilder layerBuilder = new LayerBuilder();
                layerBuilder.parseJSON(jsonObject);
                layerBuilder.setRadio(radio);
                return layerBuilder;
            }

            case "progress bar": {
                final ProgressBarBuilder progressBarBuilder = new ProgressBarBuilder();
                progressBarBuilder.parseJSON(jsonObject);
                progressBarBuilder.setRadio(radio);
                return progressBarBuilder;
            }

            case "radio button": {
                final RadioButtonBuilder radioButtonBuilder = new RadioButtonBuilder();
                radioButtonBuilder.parseJSON(jsonObject);
                radioButtonBuilder.setRadio(radio);
                return radioButtonBuilder;
            }

            case "radio button group": {
                final RadioButtonGroup radioButtonGroup = new RadioButtonGroup();

                final JSONArray radioButtons = (JSONArray) jsonObject.get("components");

                if (radioButtons != null) {
                    for (final Object object : radioButtons) {
                        final JSONObject buttonJSON = (JSONObject) object;

                        final RadioButtonBuilder builder = (RadioButtonBuilder) loadElementFromJSON(buttonJSON, radio);
                        builder.setGroup(radioButtonGroup);

                        components.add(builder.build());
                    }
                }

                return null;
            }

            case "text field": {
                final TextFieldBuilder textFieldBuilder = new TextFieldBuilder();
                textFieldBuilder.parseJSON(jsonObject);
                textFieldBuilder.setRadio(radio);
                return textFieldBuilder;
            }

            case "text area": {
                final TextAreaBuilder textAreaBuilder = new TextAreaBuilder();
                textAreaBuilder.parseJSON(jsonObject);
                textAreaBuilder.setRadio(radio);
                return textAreaBuilder;
            }

            case "rectangle printer": {
                final RectanglePrinter rectanglePrinter = new RectanglePrinter();
                rectanglePrinter.printFromJSON(this, jsonObject);
                return null;
            }

            default: {
                throw new IllegalArgumentException("The element type '" + componentType + "' is not supported.");
            }
        }
    }

    @Override
    public void draw(final @NonNull Screen screen) {
        throw new UnsupportedOperationException("A Screen must be drawn using the draw(canvas, font) method.");
    }

    /**
     * Draws the screen onto the specified graphics context..
     *
     * @param gc
     *         The graphics context to draw with.
     *
     * @param imageCache
     *         The image cache to retrieve character images from.
     *
     * @throws NullPointerException
     *         If the gc or image cache is null.
     */
    public void draw(final @NonNull Graphics2D gc, final @NonNull ImageCache imageCache) {
        // Draw non-layer components onto the screen:
        components.forEach(component -> component.draw(this));

        // Draw the screen onto the canvas:
        for (int row = 0 ; row < getHeight() ; row++) {
            super.getString(row).draw(gc, imageCache, row);
        }

        // Draw layer components onto the screen:
        layerComponents.forEach(layer -> {
            layer.draw(gc, imageCache);
        });
    }

    /**
     * Clears the specified section of the screen.
     *
     * Does nothing if the (columnIndex, rowIndex) or (width, height) pairs point to invalid positions.
     *
     * @param character
     *         The character to replace all characters being cleared with.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate of the cell to clear.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate of the cell to clear.
     *
     * @param width
     *         The width of the area to clear.
     *
     * @param height
     *         The height of the area to clear.
     */
    public void clear(final char character, final int columnIndex, final int rowIndex, int width, int height) {
        boolean canProceed = isPositionValid(columnIndex, rowIndex);
        canProceed &= width >= 0;
        canProceed &= height >= 0;

        if (canProceed) {
            width += columnIndex;
            height += rowIndex;

            for (int column = columnIndex ; column < width ; column++) {
                for (int row = rowIndex ; row < height ; row++) {
                    write(character, column, row);
                }
            }
        }
    }

    /**
     * Clears the entire screen.
     *
     * @param character
     *         The character to replace every character on the screen with.
     */
    public void clear(final char character) {
        clear(character, 0, 0, super.getWidth(), super.getHeight());
    }

    /**
     * Write the specified character to the specified position.
     *
     * @param character
     *         The character.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate to write to.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate to write to.
     *
     * @throws NullPointerException
     *         If the character is null.
     */
    public void write(final @NonNull AsciiCharacter character, final int columnIndex, final int rowIndex) {
        if (isPositionValid(columnIndex, rowIndex)) {
            super.getString(rowIndex).setCharacter(columnIndex, character);
        }
    }

    /**
     * Write the specified character to the specified position.
     *
     * @param character
     *         The character.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate to write to.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate to write to.
     */
    public void write(final char character, final int columnIndex, final int rowIndex) {
        if (isPositionValid(columnIndex, rowIndex)) {
            super.getString(rowIndex).setCharacter(columnIndex, character);
        }
    }

    /**
     * Write a string to the specified position.
     *
     * Does nothing if the (columnIndex, rowIndex) points to invalid position.
     *
     * @param string
     *         The string.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate to begin writing from.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate to begin writing from.
     *
     * @throws NullPointerException
     *         If the string is null.
     */
    public void write(final @NonNull AsciiString string, final int columnIndex, final int rowIndex) {
        if (isPositionValid(columnIndex, rowIndex)) {
            final AsciiCharacter[] characters = string.getCharacters();

            for (int i = 0; i < characters.length && i < super.getWidth(); i++) {
                write(characters[i], columnIndex + i, rowIndex);
            }
        }
    }

    /**
     * Write a string to the specified position.
     *
     * Does nothing if the (columnIndex, rowIndex) points to invalid position.
     *
     * @param string
     *         The string.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate to begin writing from.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate to begin writing from.
     *
     * @throws NullPointerException
     *         If the string is null.
     */
    public void write(final @NonNull String string, final int columnIndex, final int rowIndex) {
        write(new AsciiString(string), columnIndex, rowIndex);
    }

    /**
     * Clears the specified section of the screen.
     *
     * Does nothing if the (columnIndex, rowIndex) or (width, height) pairs point to invalid positions.
     *
     * @param character
     *         The character to replace all characters being cleared with.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates of the cell to clear.
     *
     * @param width
     *         The width of the area to clear.
     *
     * @param height
     *         The height of the area to clear.
     */
    public void clear(final char character, final Point position, int width, int height) {
        clear(character, position.x, position.y, width, height);
    }

    /**
     * Write the specified character to the specified position.
     *
     * @param character
     *         The character.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates to write to.
     *
     * @throws NullPointerException
     *         If the character is null.
     */
    public void write(final @NonNull AsciiCharacter character, final Point position) {
        write(character, position.x, position.y);
    }

    /**
     * Write the specified character to the specified position.
     *
     * @param character
     *         The character.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates to write to.
     */
    public void write(final char character, final Point position) {
        write(character, position.x, position.y);
    }

    /**
     * Write a string to the specified position.
     *
     * Does nothing if the (columnIndex, rowIndex) points to invalid position.
     *
     * @param string
     *         The string.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates to begin writing from.
     *
     * @throws NullPointerException
     *         If the string is null.
     */
    public void write(final @NonNull AsciiString string, final Point position) {
        write(string, position.x, position.y);
    }

    /**
     * Write a string to the specified position.
     *
     * Does nothing if the (columnIndex, rowIndex) points to invalid position.
     *
     * @param string
     *         The string.
     *
     * @param position
     *         The x/y-axis (column/row) coordinates to begin writing from.
     *
     * @throws NullPointerException
     *         If the string is null.
     */
    public void write(final @NonNull String string, final Point position) {
        write(new AsciiString(string), position);
    }

    /**
     * Draws the screen onto an image.
     *
     * This calls the draw function, so the screen may look a
     * little different if there are blink effects or new updates
     * to characters that haven't yet been drawn.
     *
     * This is an expensive operation as it essentially creates
     * an in-memory screen and draws each AsciiCharacter onto
     * that screen.
     *
     * @param imageCache
     *         The image cache to retrieve character images from.
     *
     * @return
     *        An image of the screen.
     *
     * @throws NullPointerException
     *         If the image cache is null.
     */
    public BufferedImage screenshot(final @NonNull ImageCache imageCache) {
        final Font font = imageCache.getFont();
        final int width = this.getWidth() * font.getWidth();
        final int height = this.getHeight() * font.getHeight();

        final BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D gc = img.createGraphics();

        draw(gc, imageCache);
        gc.dispose();

        return img;
    }

    /**
     * Sets the background color of all characters.
     *
     * @param color
     *         The new background color.
     *
     * @throws NullPointerException
     *         If the color is null.
     */
    public void setBackgroundColor(final @NonNull Color color) {
        for (final AsciiString string : getStrings()) {
            string.setBackgroundColor(color);
        }
    }

    /**
     * Sets the foreground color of all characters.
     *
     * @param color
     *         The new foreground color.
     *
     * @throws NullPointerException
     *         If the color is null.
     */
    public void setForegroundColor(final @NonNull Color color) {
        for (final AsciiString string : getStrings()) {
            string.setForegroundColor(color);
        }
    }

    /**
     * Sets the background and foreground color of all characters.
     *
     * @param background
     *         The new background color.
     *
     * @param foreground
     *         The new foreground color.
     *
     * @throws NullPointerException
     *         If the background or foreground color is null.
     */
    public void setBackgroundAndForegroundColor(final @NonNull Color background, final @NonNull Color foreground) {
        for (final AsciiString string : getStrings()) {
            string.setBackgroundColor(background);
            string.setForegroundColor(foreground);
        }
    }

    /**
     * Adds a component to the screen.
     *
     * @param component
     *          The component.
     *
     * @throws NullPointerException
     *         If the component is null.
     *
     * @throws IllegalArgumentException
     *         If the component is a screen.
     */
    public void addComponent(final @NonNull Component component) {
        if (component instanceof Screen) {
            throw new IllegalArgumentException("A screen cannot be added to another screen.");
        }

        if (component instanceof Layer) {
            component.setScreen(this);
            layerComponents.add((Layer) component);
        } else {
            component.setScreen(this);
            components.add(component);
        }
    }

    /**
     * Adds one or more components to the screen.
     *
     * @param components
     *        The components.
     *
     * @throws NullPointerException
     *         If the components are null.
     */
    public void addComponents(final @NonNull Component ... components) {
        for (int i = 0 ; i < components.length ; i++) {
            addComponent(components[i]);
        }
    }

    /**
     * Removes a component from the screen.
     *
     * @param component
     *          The component.
     *
     * @throws NullPointerException
     *         If the component is null.
     *
     * @throws IllegalArgumentException
     *         If the component is this.
     */
    public void removeComponent(final @NonNull Component component) {
        if (component == this) {
            throw new IllegalArgumentException("A screen cannot be removed from itself.");
        }

        if (component instanceof Layer) {
            component.setScreen(null);
            layerComponents.remove(component);
        } else {
            component.setScreen(null);
            components.remove(component);
        }
    }

    /**
     * Removes one or more components from the screen.
     *
     * @param components
     *        The components.
     *
     * @throws NullPointerException
     *         If the components are null.
     */
    public void removeComponents(final @NonNull Component ... components) {
        for (int i = 0 ; i < components.length ; i++) {
            removeComponent(components[i]);
        }
    }

    /**
     * Determines whether or not the screen contains a specific
     * component.
     *
     * @param component
     *        The component.
     *
     * @return
     *        Whether or not the screen contains the component.
     *
     * @throws NullPointerException
     *         If the component is null.
     */
    public boolean containsComponent(final @NonNull Component component) {
        if (component == this) {
            return false;
        }

        if (component instanceof Layer) {
            if (layerComponents.contains(component)) {
                return true;
            }
        }

        if (components.contains(component)) {
            return true;
        }

        return false;
    }

    /**
     * Determines whether or not the screen, or any sub-screen of
     * the screen, contains a specific component.
     *
     * @param component
     *        The component.
     *
     * @return
     *        Whether or not the component is contained within the
     *        screen or any sub-screen.
     *
     * @throws NullPointerException
     *         If the component is null.
     */
    public boolean recursiveContainsComponent(final @NonNull Component component) {
        if (component == this) {
            return false;
        }

        if (containsComponent(component)) {
            return true;
        }

        if (component instanceof Screen) {
            if (((Screen) component).containsComponent(this)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Determines the total number of components.
     *
     * @return
     *        The total number of components.
     */
    public int totalComponents() {
        int sum = components.size();
        sum += layerComponents.size();

        return sum;
    }

    /**
     * Retrieves the first encountered component that uses the
     * specified ID.
     *
     * @param id
     *        The id.
     *
     * @return
     *        If no component matches the ID, the null is returned.
     *        Else the component is returned.
     */
    public Component getComponentByID(final String id) {
        for (final Component component : components) {
            if (component.getId().equals(id)) {
                return component;
            }
        }

        for (final Layer layer : layerComponents) {
            if (layer.getId().equals(id)) {
                return layer;
            }
        }

        return null;
    }

    /**
     * Works the same as getComponentByID, but only returns
     * if the result is a Button component.
     *
     * @param id
     *        The id.
     *
     * @return
     *        If no component matches the ID, then null is returned.
     *        If no button component matches the ID, then null is returned.
     *        Else the component is returned.
     */
    public Button getButtonByID(final String id) {
        final Component component = getComponentByID(id);

        if (component instanceof Button) {
            return (Button) component;
        }

        return null;
    }

    /**
     * Works the same as getComponentByID, but only returns
     * if the result is a Check Box component.
     *
     * @param id
     *        The id.
     *
     * @return
     *        If no component matches the ID, then null is returned.
     *        If no check box component matches the ID, then null is returned.
     *        Else the component is returned.
     */
    public CheckBox getCheckBoxByID(final String id) {
        final Component component = getComponentByID(id);

        if (component instanceof CheckBox) {
            return (CheckBox) component;
        }

        return null;
    }

    /**
     * Works the same as getComponentByID, but only returns
     * if the result is a Layer component.
     *
     * @param id
     *        The id.
     *
     * @return
     *        If no component matches the ID, then null is returned.
     *        If no layer component matches the ID, then null is returned.
     *        Else the component is returned.
     */
    public Layer getLayerByID(final String id) {
        final Component component = getComponentByID(id);

        if (component instanceof Layer) {
            return (Layer) component;
        }

        return null;
    }

    /**
     * Works the same as getComponentByID, but only returns
     * if the result is a ProgressBar component.
     *
     * @param id
     *        The id.
     *
     * @return
     *        If no component matches the ID, then null is returned.
     *        If no progress bar component matches the ID, then null is returned.
     *        Else the component is returned.
     */
    public ProgressBar getProgressBarByID(final String id) {
        final Component component = getComponentByID(id);

        if (component instanceof ProgressBar) {
            return (ProgressBar) component;
        }

        return null;
    }

    /**
     * Works the same as getComponentByID, but only returns
     * if the result is a RadioButton component.
     *
     * @param id
     *        The id.
     *
     * @return
     *        If no component matches the ID, then null is returned.
     *        If no radio button component matches the ID, then null is returned.
     *        Else the component is returned.
     */
    public RadioButton getRadioButtonByID(final String id) {
        final Component component = getComponentByID(id);

        if (component instanceof RadioButton) {
            return (RadioButton) component;
        }

        return null;
    }

    /**
     * Works the same as getComponentByID, but only returns
     * if the result is a TextArea component.
     *
     * @param id
     *        The id.
     *
     * @return
     *        If no component matches the ID, then null is returned.
     *        If no text area component matches the ID, then null is returned.
     *        Else the component is returned.
     */
    public TextArea getTextAreaByID(final String id) {
        final Component component = getComponentByID(id);

        if (component instanceof TextArea) {
            return (TextArea) component;
        }

        return null;
    }

    /**
     * Works the same as getComponentByID, but only returns
     * if the result is a TextField component.
     *
     * @param id
     *        The id.
     *
     * @return
     *        If no component matches the ID, then null is returned.
     *        If no text field component matches the ID, then null is returned.
     *        Else the component is returned.
     */
    public TextField getTextFieldByID(final String id) {
        final Component component = getComponentByID(id);

        if (component instanceof TextField) {
            return (TextField) component;
        }

        return null;
    }

    /**
     * Retrieves a combined set of all components.
     *
     * @return
     *        A combined set of all components.
     */
    public Set<Component> getComponents() {
        final Set<Component> set = new LinkedHashSet<>(components);
        set.addAll(layerComponents);
        return set;
    }
}