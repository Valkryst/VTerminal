package com.valkryst.VTerminal.shader.blur;

import com.jhlabs.image.GaussianFilter;
import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.shader.Shader;
import lombok.Data;
import lombok.NonNull;

import java.awt.image.BufferedImage;

@Data
public class GaussianBlurShader implements Shader {
    private int radius = 2;

    @Override
    public BufferedImage run(@NonNull BufferedImage bufferedImage, final @NonNull AsciiCharacter character) {
        final GaussianFilter filter = new GaussianFilter();
        filter.setRadius(radius);
        return filter.filter(bufferedImage, null);
    }

    @Override
    public Shader copy() {
        final GaussianBlurShader gaussianBlurShader = new GaussianBlurShader();
        gaussianBlurShader.setRadius(radius);
        return gaussianBlurShader;
    }
}
