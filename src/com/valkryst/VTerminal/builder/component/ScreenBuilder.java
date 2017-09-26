package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.Screen;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.json.simple.JSONObject;

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
