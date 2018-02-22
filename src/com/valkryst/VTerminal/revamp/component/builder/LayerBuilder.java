package com.valkryst.VTerminal.revamp.component.builder;

import com.valkryst.VTerminal.revamp.component.component.Layer;
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

        super.getDimensions().setSize(1, 1);
    }
}