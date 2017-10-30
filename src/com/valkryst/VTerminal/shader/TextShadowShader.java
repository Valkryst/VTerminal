package com.valkryst.VTerminal.shader;

import lombok.Data;
import lombok.NonNull;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

@Data
public class TextShadowShader implements Shader {
    /** The offset to draw the shadow at, on the x-axis. */
    private int xOffset = 2;
    /** The offset to draw the shadow at, on the y-axis. */
    private int yOffset = 2;

    @Override
    public BufferedImage run(final @NonNull BufferedImage image) {
        // Count occurrences of each color:
        final HashMap<Integer, Integer> colorOccurrences = new HashMap<>();

        for (int y = 0 ; y < image.getHeight() ; y++) {
            for (int x = 0 ; x < image.getWidth() ; x++) {
                int currentColor = image.getRGB(x, y);
                int occurrences = colorOccurrences.getOrDefault(currentColor, 0);

                colorOccurrences.put(currentColor, occurrences + 1);
            }
        }

        // If there is only one color, then we're dealing with a blank AsciiCharacter.
        // In this case, the shader does nothing.
        if (colorOccurrences.size() == 1) {
            return image;
        }

        // If there are more than two colors, we're not dealing with an
        // AsciiCharacter. In this case, the shader does nothing.
        if (colorOccurrences.size() > 2) {
            return image;
        }

        // The most common color is probably the background color and the least
        // common color is probably the foreground color.
        // This isn't always going to be true, but for most characters it will be.
        final Integer[] keySet = colorOccurrences.keySet().toArray(new Integer[colorOccurrences.size()]);
        final Integer[] valueSet = colorOccurrences.values().toArray(new Integer[colorOccurrences.size()]);

        final Color backgroundColor;
        final Color foregroundColor;

        if (valueSet[0] > valueSet[1]) {
            backgroundColor = new Color(keySet[0]);
            foregroundColor = new Color(keySet[1]);
        } else {
            backgroundColor = new Color(keySet[1]);
            foregroundColor = new Color(keySet[0]);
        }

        // There's no character to add a shadow for, if the back/foreground colors
        // are the same.
        if (backgroundColor.equals(foregroundColor)) {
            return image;
        }

        // Get the normal & background character images:
        final BufferedImage normalChar = swapColor(image, backgroundColor, new Color(0, 0, 0, 0));
        final BufferedImage shadowChar = swapColor(normalChar, foregroundColor, Color.BLACK);

        // Combine images:
        final BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        final Graphics2D gc = (Graphics2D) result.getGraphics();

        gc.setColor(backgroundColor);
        gc.fillRect(0, 0, result.getWidth(), result.getHeight());
        gc.drawImage(shadowChar, xOffset, yOffset, null);
        gc.drawImage(normalChar, 0, 0, null);
        gc.dispose();

        return result;
    }
}
