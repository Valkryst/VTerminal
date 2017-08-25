package com.valkryst.VTerminal.builder.component;

import com.valkryst.VRadio.Radio;
import com.valkryst.VTerminal.component.*;
import com.valkryst.VTerminal.misc.JSONFunctions;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.LinkedHashSet;
import java.util.Set;

public class ScreenBuilder extends ComponentBuilder<Screen> {
    /** The width of the screen, in characters. */
    @Getter @Setter private int width;
    /** The height of the screen, in characters. */
    @Getter @Setter private int height;

    /** The non-layer components displayed on the screen. */
    @Getter @Setter @NonNull private Set<Component> components = new LinkedHashSet<>();

    /** The layer components displayed on the screen. */
    @Getter @Setter @NonNull private Set<Layer> layerComponents = new LinkedHashSet<>();

    @Override
    public Screen build() {
        checkState();
        return new Screen(this);
    }

    /**
     * Checks the current state of the builder.
     *
     * @throws IllegalArgumentException
     *          If the width or height is less than one.
     */
    protected void checkState() throws NullPointerException {
        super.checkState();

        if (width < 1) {
            throw new IllegalArgumentException("The width cannot be less than one.");
        }

        if (height < 1) {
            throw new IllegalArgumentException("The height cannot be less than one.");
        }
    }

    /** Resets the builder to it's default state. */
    public void reset() {
        super.reset();

        width = 80;
        height = 24;

        components = new LinkedHashSet<>();
        layerComponents = new LinkedHashSet<>();
    }

    @Override
    public void parseJSON(final @NonNull JSONObject jsonObject) {
        final Radio<String> radio = super.getRadio();

        reset();
        super.parseJSON(jsonObject);


        final Integer width = JSONFunctions.getIntElement(jsonObject, "width");
        final Integer height = JSONFunctions.getIntElement(jsonObject, "height");
        final JSONArray components = (JSONArray) jsonObject.get("components");


        if (width != null) {
            this.width = width;
        }

        if (height != null) {
            this.height = height;
        }


        if (components != null) {
            for (final Object obj : components) {
                final JSONObject arrayElement = (JSONObject) obj;

                if (arrayElement != null) {
                    final ComponentBuilder componentBuilder = loadComponentFromJSON(arrayElement, radio);

                    if (componentBuilder != null) {
                        // todo Add special case for LayerBuilder
                        /*
                        if (component instanceof Layer) {
                            layerComponents.add((Layer) component);
                        } else {
                            this.components.add(component);
                        }
                        */
                        this.components.add(componentBuilder.build());
                    }
                }
            }
        }
    }

    /**
     * Loads a component from it's JSON representation.
     *
     * @param jsonObject
     *        The JSON.
     *
     * @param radio
     *        The radio for the component to use.
     *
     * @return
     *        The component.
     *
     * @throws IllegalArgumentException
     *        If the type of the component isn't supported.
     */
    private ComponentBuilder loadComponentFromJSON(final @NonNull JSONObject jsonObject, final @NonNull Radio<String> radio) {
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

                        final RadioButtonBuilder builder = (RadioButtonBuilder) loadComponentFromJSON(buttonJSON, radio);
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

            default: {
                throw new IllegalArgumentException("The type '" + componentType + "' is not supported.");
            }
        }
    }
}
