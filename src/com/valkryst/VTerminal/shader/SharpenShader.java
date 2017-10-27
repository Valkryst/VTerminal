package com.valkryst.VTerminal.shader;

import com.jhlabs.image.UnsharpFilter;
import lombok.Data;
import lombok.NonNull;

import java.awt.image.BufferedImage;

@Data
public class SharpenShader implements Shader {
    private float amount = 0.5f;
    private int threshold = 1;

    @Override
    public BufferedImage run(@NonNull BufferedImage bufferedImage) {
        final UnsharpFilter filter = new UnsharpFilter();
        filter.setAmount(amount);
        filter.setThreshold(threshold);
        return filter.filter(bufferedImage, null);
    }
}
