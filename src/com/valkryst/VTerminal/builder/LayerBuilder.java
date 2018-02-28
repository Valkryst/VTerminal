package com.valkryst.VTerminal.builder;

import com.valkryst.VJSON.VJSONParser;
import com.valkryst.VTerminal.component.Layer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.json.simple.JSONObject;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class LayerBuilder extends ComponentBuilder<Layer> implements VJSONParser {
    @Override
    public Layer build() {
        checkState();
        return new Layer(this);
    }

    @Override
    public void reset() {
        super.reset();

        super.setDimensions(1, 1);
    }

    @Override
    public void parse(final @NonNull JSONObject jsonObject) {
        super.parse(jsonObject);
    }
}
