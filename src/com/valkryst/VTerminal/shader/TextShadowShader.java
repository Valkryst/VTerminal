package com.valkryst.VTerminal.shader;

import lombok.Data;
import lombok.NonNull;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

@Data
public class TextShadowShader implements Shader {
    /** The offset to draw the shadow at, on the x-axis. */
    private int xOffset = 2;
    /** The offset to draw the shadow at, on the y-axis. */
    private int yOffset = 2;

    @Override
    public BufferedImage run(final @NonNull BufferedImage image) {
        try {
            final Color[] colors = getBackgroundAndForegroundRGBColor(image);

            // Get the normal & background character images:
            final BufferedImage normalChar = swapColor(image, colors[0], new Color(0, 0, 0, 0));
            final BufferedImage shadowChar = swapColor(normalChar, colors[1], Color.BLACK);

            // Combine images:
            final BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
            final Graphics2D gc = (Graphics2D) result.getGraphics();

            gc.setColor(colors[0]);
            gc.fillRect(0, 0, result.getWidth(), result.getHeight());
            gc.drawImage(shadowChar, xOffset, yOffset, null);
            gc.drawImage(normalChar, 0, 0, null);
            gc.dispose();

            return result;
        } catch (final IllegalStateException e) {
            return image;
        }
    }
}
