package com.valkryst.VTerminal.shader.character;

import com.jhlabs.image.ChromeFilter;
import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.shader.Shader;
import lombok.Data;
import lombok.NonNull;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

@Data
public class CharChromeShader implements CharShader {
    /** The amount of chrome. */
    private float amount = 0.5f;
    /** The amount of exposure. */
    private float exposure = 0.85f;

    @Override
    public BufferedImage run(final @NonNull BufferedImage image, final @NonNull AsciiCharacter character) {
        final ChromeFilter filter = new ChromeFilter();
        filter.setAmount(amount);
        filter.setExposure(exposure);

        // Get the character image and filter it:
        BufferedImage charImage = swapColor(image, character.getBackgroundColor(), new Color(0, 0, 0, 0));
        charImage = filter.filter(charImage, null);

        // Combine image and background
        final BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        final Graphics2D gc = (Graphics2D) result.getGraphics();

        gc.setColor(character.getBackgroundColor());
        gc.fillRect(0, 0, result.getWidth(), result.getHeight());
        gc.drawImage(charImage, 1, 0, null);
        gc.drawImage(charImage, 0, 0, null);
        gc.dispose();

        return result;
    }

    @Override
    public Shader copy() {
        final CharChromeShader shader = new CharChromeShader();
        shader.setAmount(amount);
        shader.setExposure(exposure);
        return shader;
    }
}
