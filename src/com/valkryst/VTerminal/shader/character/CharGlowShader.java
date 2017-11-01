package com.valkryst.VTerminal.shader.character;

import com.jhlabs.image.GaussianFilter;
import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.AsciiTile;
import com.valkryst.VTerminal.shader.Shader;
import lombok.NonNull;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class CharGlowShader implements CharShader {
    @Override
    public BufferedImage run(final @NonNull BufferedImage image, final @NonNull AsciiCharacter character) {
        if (character instanceof AsciiTile) {
            return image;
        }

        if (character.isForegroundAndBackgroundColorEqual()) {
            return image;
        }

        // Get character image:
        final BufferedImage charImage = swapColor(image, character.getBackgroundColor(), new Color(0, 0, 0, 0));

        // Generate glow image:
        final GaussianFilter filter = new GaussianFilter();
        filter.setRadius(5);
        final BufferedImage glowImage = filter.filter(charImage, null);

        // Combine images and background:
        final BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        final Graphics2D gc = (Graphics2D) result.getGraphics();

        gc.setColor(character.getBackgroundColor());
        gc.fillRect(0, 0, result.getWidth(), result.getHeight());
        gc.drawImage(glowImage, 0, 0, null);
        gc.drawImage(charImage, 0, 0, null);
        gc.dispose();

        return result;
    }

    @Override
    public Shader copy() {
        return new CharGlowShader();
    }
}
