package com.valkryst.VTerminal.shader.misc;

import com.jhlabs.image.EdgeFilter;
import com.valkryst.VTerminal.shader.Shader;
import lombok.NonNull;

import java.awt.image.BufferedImage;

public class EdgeDetectionShader implements Shader {
    @Override
    public BufferedImage run(final @NonNull BufferedImage image) {
        return new EdgeFilter().filter(image, null);
    }

    @Override
    public Shader copy() {
        return new EdgeDetectionShader();
    }
}
