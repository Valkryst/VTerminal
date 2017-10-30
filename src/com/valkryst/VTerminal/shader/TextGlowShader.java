package com.valkryst.VTerminal.shader;

import com.jhlabs.image.GaussianFilter;
import com.valkryst.VTerminal.shader.blur.GaussianBlurShader;
import lombok.NonNull;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class TextGlowShader extends GaussianBlurShader {
    /** Constructs a new TextGlowShader. */
    public TextGlowShader() {
        super.setRadius(4);
    }

    @Override
    public BufferedImage run(final @NonNull BufferedImage image) {
        try {
            // Get character image:
            final Color[] colors = getBackgroundAndForegroundRGBColor(image);
            final BufferedImage charImage = swapColor(image, colors[0], new Color(0, 0, 0, 0));

            // Generate glow image:
            final GaussianFilter filter = new GaussianFilter();
            filter.setRadius(super.getRadius());
            final BufferedImage glowImage = filter.filter(charImage, null);

            // Combine images and background:
            final BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            final Graphics2D gc = (Graphics2D) result.getGraphics();

            gc.setColor(colors[0]);
            gc.fillRect(0, 0, result.getWidth(), result.getHeight());
            gc.drawImage(glowImage, 0, 0, null);
            gc.drawImage(charImage, 0, 0, null);
            gc.dispose();

            return result;
        } catch (final IllegalStateException e) {
            return image;
        }
    }
}
