package com.valkryst.VTerminal.shader;

import lombok.NonNull;

import java.awt.image.BufferedImage;

public interface Shader {
    /**
     * Runs the shader on an image.
     *
     * @param image
     *          The image.
     *
     * @return
     *          The processed image.
     */
    BufferedImage run(final @NonNull BufferedImage image);
}
