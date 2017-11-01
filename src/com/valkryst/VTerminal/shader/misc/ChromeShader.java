package com.valkryst.VTerminal.shader.misc;

import com.jhlabs.image.ChromeFilter;
import com.valkryst.VTerminal.shader.Shader;
import lombok.Data;
import lombok.NonNull;

import java.awt.image.BufferedImage;

@Data
public class ChromeShader implements Shader {
    /** The amount of chrome. */
    private float amount = 0.5f;
    /** The amount of exposure. */
    private float exposure = 1.0f;

    @Override
    public BufferedImage run(final @NonNull BufferedImage image) {
        final ChromeFilter filter = new ChromeFilter();
        filter.setAmount(amount);
        filter.setExposure(exposure);
        return filter.filter(image, null);
    }

    @Override
    public Shader copy() {
        final ChromeShader shader = new ChromeShader();
        shader.setAmount(amount);
        shader.setExposure(exposure);
        return shader;
    }
}
