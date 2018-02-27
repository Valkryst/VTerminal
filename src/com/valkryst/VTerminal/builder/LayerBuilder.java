package com.valkryst.VTerminal.builder;

import com.valkryst.VTerminal.component.Layer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=true)
public class LayerBuilder extends ComponentBuilder<Layer> {
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
}
