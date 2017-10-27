package com.valkryst.VTerminal.shader;

import com.jhlabs.image.Colormap;
import com.jhlabs.image.RaysFilter;
import lombok.Data;
import lombok.NonNull;

import java.awt.image.BufferedImage;

@Data
public class RayShader implements Shader {
    private float opacity = 1.0F;
    private float threshold = 0.0F;
    private float strength = 0.5F;
    private boolean raysOnly = false;
    private Colormap colormap;

    @Override
    public BufferedImage run(@NonNull BufferedImage bufferedImage) {
        final RaysFilter filter = new RaysFilter();
        filter.setOpacity(opacity);
        filter.setThreshold(threshold);
        filter.setStrength(strength);
        filter.setRaysOnly(raysOnly);
        filter.setColormap(colormap);
        return filter.filter(bufferedImage, null);
    }
}
