package com.valkryst.VTerminal.shader;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.misc.ColorFunctions;
import lombok.NonNull;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ScanLineShader implements Shader {
    @Override
    public BufferedImage run(final @NonNull BufferedImage image, final @NonNull AsciiCharacter character) {
        for (int y = 0 ; y < image.getHeight() ; y+= 3) {
            for (int x = 0 ; x < image.getWidth() ; x++) {
                Color color = new Color(image.getRGB(x, y));
                color = ColorFunctions.shade(color, 0.25);
                image.setRGB(x, y, color.getRGB());
            }
        }

        return image;
    }

    @Override
    public Shader copy() {
        return new ScanLineShader();
    }
}
