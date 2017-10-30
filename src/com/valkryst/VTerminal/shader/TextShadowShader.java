package com.valkryst.VTerminal.shader;

import com.valkryst.VTerminal.AsciiCharacter;
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
    public BufferedImage run(final @NonNull BufferedImage image, final @NonNull AsciiCharacter character) {
        // Get the normal & background character images:
        final BufferedImage normalChar = swapColor(image, character.getBackgroundColor(), new Color(0, 0, 0, 0));
        final BufferedImage shadowChar = swapColor(normalChar, character.getForegroundColor(), Color.BLACK);

        // Combine images:
        final BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        final Graphics2D gc = (Graphics2D) result.getGraphics();

        gc.setColor(character.getBackgroundColor());
        gc.fillRect(0, 0, result.getWidth(), result.getHeight());
        gc.drawImage(shadowChar, xOffset, yOffset, null);
        gc.drawImage(normalChar, 0, 0, null);
        gc.dispose();

        return result;
    }

    @Override
    public Shader copy() {
        final TextShadowShader textShadowShader = new TextShadowShader();
        textShadowShader.setXOffset(xOffset);
        textShadowShader.setYOffset(yOffset);
        return textShadowShader;
    }
}
