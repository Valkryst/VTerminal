package com.valkryst.VTerminal.shader.misc;

import com.jhlabs.image.BlockFilter;
import com.valkryst.VTerminal.shader.Shader;
import lombok.Data;
import lombok.NonNull;

import java.awt.image.BufferedImage;

@Data
public class PixelateShader implements Shader {
    /** The pixel block size. */
    private int blockSize = 3;

    @Override
    public BufferedImage run(final @NonNull BufferedImage image) {
        final BlockFilter filter = new BlockFilter();
        filter.setBlockSize(blockSize);
        return filter.filter(image, null);
    }

    @Override
    public Shader copy() {
        final PixelateShader shader = new PixelateShader();
        shader.setBlockSize(blockSize);
        return shader;
    }
}
