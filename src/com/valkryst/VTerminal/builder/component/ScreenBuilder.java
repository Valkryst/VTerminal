package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.Screen;
import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONObject;

public class ScreenBuilder extends ComponentBuilder<Screen> {
    /** The width of the screen, in characters. */
    @Getter @Setter private int width;
    /** The height of the screen, in characters. */
    @Getter @Setter private int height;

    /** The JSON definition of a GUI. */
    @Getter @Setter private JSONObject jsonObject;

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
    }
}
