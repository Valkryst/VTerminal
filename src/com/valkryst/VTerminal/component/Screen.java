package com.valkryst.VTerminal.component;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.component.*;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.misc.ImageCache;
import com.valkryst.VTerminal.misc.IntRange;
import com.valkryst.VTerminal.printer.RectanglePrinter;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@ToString
public class Screen extends Component {
    /** The panel on which the screen is displayed. */
    @Getter @Setter private Panel parentPanel;

    /** The non-layer components displayed on the screen. */
    private ArrayList<Component> components = new ArrayList<>();

    /** The layer components displayed on the screen. */
    private ArrayList<Layer> layerComponents = new ArrayList<>();

    /** The screen components displayed on the screen. */
    private ArrayList<Screen> screenComponents = new ArrayList<>();

    private ReentrantReadWriteLock componentsLock = new ReentrantReadWriteLock();

    /**
     * Constructs a new Screen.
     *
     * @param builder
     *         The builder to use.
     *
     * @throws NullPointerException
     *         If the builder is null.
     */
    public Screen(final @NonNull ScreenBuilder builder) {
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
                buttonBuilder.parse(jsonObject);
                buttonBuilder.setRadio(radio);
                return buttonBuilder;
            }

            case "check box": {
                final CheckBoxBuilder checkBoxBuilder = new CheckBoxBuilder();
                checkBoxBuilder.parse(jsonObject);
                checkBoxBuilder.setRadio(radio);
                return checkBoxBuilder;
            }

            case "label": {
                final LabelBuilder labelBuilder = new LabelBuilder();
                labelBuilder.parse(jsonObject);
                labelBuilder.setRadio(radio);
                return labelBuilder;
            }

            case "layer": {
                final LayerBuilder layerBuilder = new LayerBuilder();
                layerBuilder.parse(jsonObject);
                layerBuilder.setRadio(radio);
                return layerBuilder;
            }

            case "progress bar": {
                final ProgressBarBuilder progressBarBuilder = new ProgressBarBuilder();
                progressBarBuilder.parse(jsonObject);
                progressBarBuilder.setRadio(radio);
                return progressBarBuilder;
            }

            case "radio button": {
                final RadioButtonBuilder radioButtonBuilder = new RadioButtonBuilder();
                radioButtonBuilder.parse(jsonObject);
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

            case "screen": {
                final ScreenBuilder screenBuilder = new ScreenBuilder();
                screenBuilder.parse(jsonObject);
                screenBuilder.setRadio(radio);
                return screenBuilder;
            }

            case "text field": {
                final TextFieldBuilder textFieldBuilder = new TextFieldBuilder();
                textFieldBuilder.parse(jsonObject);
                textFieldBuilder.setRadio(radio);
                return textFieldBuilder;
            }

            case "text area": {
                final TextAreaBuilder textAreaBuilder = new TextAreaBuilder();
                textAreaBuilder.parse(jsonObject);
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
        draw(gc, imageCache, getPosition());
    }

    /**
     * Draws the screen onto the specified graphics context..
     *
     * @param gc
     *         The graphics context to draw with.
     *
     * @param imageCache
     *          The image cache to retrieve character images from.
     *
     * @param offset
     *          The x/y-axis (column/row) offsets to alter the position at which the
     *          screen is drawn.
     *
     * @throws NullPointerException
     *          If the gc or image cache is null.
     */
    public void draw(final @NonNull Graphics2D gc, final @NonNull ImageCache imageCache, final Point offset) {
        componentsLock.readLock().lock();

        // Draw non-layer components onto the screen:
        components.forEach(component -> component.draw(this));

        // Draw the screen onto the canvas:
        final AsciiString[] strings = super.getStrings();

        final Thread threadA = new Thread(() -> {
            for (int row = 0; row < getHeight() / 2; row++) {
                strings[row].draw(gc, imageCache, row, offset);
            }
        });

        final Thread threadB = new Thread(() -> {
            for (int row = getHeight() / 2; row < getHeight(); row++) {
                strings[row].draw(gc, imageCache, row, offset);
            }
        });

        threadA.run();
        threadB.run();

        try {
            threadA.join();
            threadB.join();
        } catch(final InterruptedException e) {
            e.printStackTrace();
        }

        // Draw layer components onto the screen:
        layerComponents.forEach(layer -> layer.draw(gc, imageCache, offset));

        // Draw screen components onto the screen:
        screenComponents.forEach(screen -> {
            final Point position = screen.getPosition();
            screen.draw(gc, imageCache, position);
        });

        componentsLock.readLock().unlock();
    }

    /**
     * Clears the specified section of the screen.
     *
     * Does nothing if the (columnIndex, rowIndex) or (width, height) pairs point
     * to invalid positions.
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
        boolean canProceed = isPositionValid(position);
        canProceed &= width >= 0;
        canProceed &= height >= 0;

        if (canProceed) {
            width += position.x;
            height += position.y;

            final Point writePosition = new Point(0, 0);
            for (int column = position.x ; column < width ; column++) {
                for (int row = position.y ; row < height ; row++) {
                    writePosition.setLocation(column, row);
                    write(character, writePosition);
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
        clear(character, new Point(0, 0), super.getWidth(), super.getHeight());
    }

    /**
     * Write the specified character to the specified position.
     *
     * @param character
     *         The character.
     *
     * @param position
     *         The x/y-axis (column/row) coordinate to write to.
     *
     * @throws NullPointerException
     *         If the character is null.
     */
    public void write(final @NonNull AsciiCharacter character, final Point position) {
        if (isPositionValid(position)) {
            super.getString(position.y).setCharacter(position.x, character);
        }
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
        if (isPositionValid(position)) {
            super.getString(position.y).setCharacter(position.x, character);
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
     * @param position
     *         The x/y-axis (column/row) coordinates to begin writing from.
     *
     * @throws NullPointerException
     *         If the string is null.
     */
    public void write(final @NonNull AsciiString string, final Point position) {
        if (isPositionValid(position)) {
            final AsciiCharacter[] characters = string.getCharacters();

            for (int i = 0; i < characters.length && i < super.getWidth(); i++) {
                write(characters[i], new Point(position.x + i, position.y));
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
     * This calls the draw function, so the screen may look a little different
     * if there are blink effects or new updates to characters that haven't yet
     * been drawn.
     *
     * This is an expensive operation as it essentially creates an in-memory
     * screen and draws each AsciiCharacter onto that screen.
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
     * Adds a component to the screen and registers event listeners of the
     * component, to the parent panel, if required.
     *
     * If the component is already present on the screen, then the component is
     * not added.
     *
     * If the component is a screen and it has already been added to this screen,
     * or any sub-screen of this screen, then the component is not added.
     *
     * @param component
     *          The component.
     */
    public void addComponent(final Component component) {
        componentsLock.writeLock().lock();

        boolean containsComponent = containsComponent(component);

        if (containsComponent) {
            componentsLock.writeLock().unlock();
            return;
        }

        // Add the component to one of the component lists:
        if (component instanceof Screen) {
            component.setScreen(this);
            screenComponents.add((Screen) component);
        } else if (component instanceof Layer) {
            component.setScreen(this);
            layerComponents.add((Layer) component);
        } else {
            component.setScreen(this);
            components.add(component);
        }

        componentsLock.writeLock().unlock();

        // Set up event listeners:
        component.createEventListeners(parentPanel);

        for (final EventListener eventListener : component.getEventListeners()) {
            parentPanel.addListener(eventListener);
        }
    }

    /**
     * Adds one or more components to the screen.
     *
     * @param components
     *        The components.
     */
    public void addComponents(final Component ... components) {
        if (components == null) {
            return;
        }

        for (final Component component : components) {
            addComponent(component);
        }
    }

    /**
     * Removes a component from the screen and removes event listeners of the
     * component from the parent panel.
     *
     * @param component
     *          The component.
     *
     * @throws IllegalArgumentException
     *         If the component is this.
     */
    public void removeComponent(final Component component) {
        componentsLock.writeLock().lock();

        if (component == null) {
            componentsLock.writeLock().unlock();
            return;
        }

        if (component == this) {
            componentsLock.writeLock().unlock();
            throw new IllegalArgumentException("A screen cannot be removed from itself.");
        }

        if (component instanceof Screen) {
            component.setScreen(null);
            screenComponents.remove(component);
        } else if (component instanceof Layer) {
            component.setScreen(null);
            layerComponents.remove(component);
        } else {
            component.setScreen(null);
            components.remove(component);
        }

        componentsLock.writeLock().unlock();

        for (final EventListener eventListener : component.getEventListeners()) {
            parentPanel.removeListener(eventListener);
        }

        // Reset component's characters to empty cells.
        final Point position = component.getPosition();
        final IntRange redrawRange = new IntRange(position.x, position.x + component.getWidth());

        for (int y = position.y ; y < position.y + component.getHeight() ; y++) {
            final AsciiString string = super.getString(y);

            string.setCharacters(' ', redrawRange);
            string.setBackgroundColor(new Color(45, 45, 45, 255), redrawRange);
            string.setForegroundColor(Color.WHITE, redrawRange);
            string.setUnderlined(redrawRange, false);
            string.setFlippedHorizontally(redrawRange, false);
            string.setFlippedVertically(redrawRange, false);
        }
    }

    /**
     * Removes one or more components from the screen.
     *
     * @param components
     *        The components.
     */
    public void removeComponents(final Component ... components) {
        if (components == null) {
            return;
        }

        for (final Component component : components) {
            removeComponent(component);
        }
    }

    /**
     * Moves one component above another component, in the
     * draw order.
     *
     * Does nothing if either component is null.
     *
     * Does nothing if components are not of the same type.
     *
     * @param stationary
     *          The component that is not being moved.
     *
     * @param moving
     *          The component that is being moved.
     */
    public void changeDrawOrder(final Component stationary, final Component moving) {
        if (stationary == null || moving == null) {
            return;
        }

        if (stationary.getClass().equals(moving.getClass()) == false) {
            return;
        }

        final List list;

        if (stationary instanceof Screen) {
            list = screenComponents;
        } else if (stationary instanceof Layer) {
            list = layerComponents;
        } else {
            list = components;
        }

        final int index = list.indexOf(stationary);

        if (index != -1) {
            list.remove(moving);
            list.add(index, moving);
        }
    }

    /**
     * Determines whether or not the screen contains a specific component.
     *
     * @param component
     *        The component.
     *
     * @return
     *        Whether or not the screen contains the component.
     */
    public boolean containsComponent(final Component component) {
        componentsLock.readLock().lock();

        if (component == null) {
            componentsLock.readLock().unlock();
            return false;
        }

        if (component == this) {
            componentsLock.readLock().unlock();
            return false;
        }

        if (component instanceof Screen) {
            if (screenComponents.contains(component)) {
                componentsLock.readLock().unlock();
                return true;
            }
        } else if (component instanceof Layer) {
            if (layerComponents.contains(component)) {
                componentsLock.readLock().unlock();
                return true;
            }
        }

        final boolean result = components.contains(component);
        componentsLock.readLock().unlock();
        return result;
    }

    /**
     * Determines the total number of components.
     *
     * @return
     *        The total number of components.
     */
    public int totalComponents() {
        componentsLock.readLock().lock();

        int sum = components.size();
        sum += layerComponents.size();
        sum += screenComponents.size();

        componentsLock.readLock().unlock();

        return sum;
    }

    /**
     * Retrieves the first encountered component that uses the specified ID.
     *
     * @param id
     *        The id.
     *
     * @return
     *        If no component matches the ID, the null is returned.
     *        Else the component is returned.
     */
    public Component getComponentByID(final String id) {
        componentsLock.readLock().lock();

        for (final Component component : components) {
            if (component.getId().equals(id)) {
                componentsLock.readLock().unlock();
                return component;
            }
        }

        for (final Layer layer : layerComponents) {
            if (layer.getId().equals(id)) {
                componentsLock.readLock().unlock();
                return layer;
            }
        }

        for (final Screen screen : screenComponents) {
            if (screen.getId().equals(id)) {
                componentsLock.readLock().unlock();
                return screen;
            }
        }

        componentsLock.readLock().unlock();
        return null;
    }

    /**
     * Works the same as getComponentByID, but only returns if the result is a
     * Button component.
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
     * Works the same as getComponentByID, but only returns if the result is a
     * Check Box component.
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
     * Works the same as getComponentByID, but only returns if the result is a
     * Layer component.
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
     * Works the same as getComponentByID, but only returns if the result is a
     * ProgressBar component.
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
     * Works the same as getComponentByID, but only returns if the result is a
     * RadioButton component.
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
     * Works the same as getComponentByID, but only returns if the result is a
     * TextArea component.
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
     * Works the same as getComponentByID, but only returns if the result is a
     * TextField component.
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
        componentsLock.readLock().lock();

        final Set<Component> set = new LinkedHashSet<>(components);
        set.addAll(layerComponents);
        set.addAll(screenComponents);

        componentsLock.readLock().unlock();

        return set;
    }
}