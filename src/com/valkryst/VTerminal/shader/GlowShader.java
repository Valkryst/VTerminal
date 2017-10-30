package com.valkryst.VTerminal.shader;

import com.jhlabs.image.GlowFilter;
import com.valkryst.VTerminal.AsciiCharacter;
import lombok.Data;
import lombok.NonNull;

import java.awt.image.BufferedImage;

@Data
public class GlowShader implements Shader {
    private float amount = 0.5f;
    private int radius = 2;

    @Override
    public BufferedImage run(final @NonNull BufferedImage image, final @NonNull AsciiCharacter character) {
        final GlowFilter filter = new GlowFilter();
        filter.setAmount(amount);
        filter.setRadius(radius);
        return filter.filter(image, null);
    }

    @Override
    public Shader copy() {
        final GlowShader glowShader = new GlowShader();
        glowShader.setAmount(amount);
        glowShader.setRadius(radius);
        return glowShader;
    }
}
