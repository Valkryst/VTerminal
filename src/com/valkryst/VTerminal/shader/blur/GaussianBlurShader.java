package com.valkryst.VTerminal.shader.blur;

import com.jhlabs.image.GaussianFilter;
import com.valkryst.VTerminal.shader.Shader;
import lombok.Data;
import lombok.NonNull;

import java.awt.image.BufferedImage;

@Data
public class GaussianBlurShader implements Shader {
    private int radius = 2;

    @Override
    public BufferedImage run(@NonNull BufferedImage bufferedImage) {
        final GaussianFilter filter = new GaussianFilter();
        filter.setRadius(radius);
        return filter.filter(bufferedImage, null);
    }
}
