package com.valkryst.VTerminal.builder.component;

import com.valkryst.VTerminal.component.Screen;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.json.simple.JSONObject;

@Data
@NoArgsConstructor
public class ScreenBuilder extends ComponentBuilder<Screen> {
    /** The JSON definition of a GUI. */
    private JSONObject jsonObject;

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
