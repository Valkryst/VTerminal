package com.valkryst.VTerminal.shader.character;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.shader.Shader;
import lombok.NonNull;

import java.awt.image.BufferedImage;

public interface CharShader extends Shader {
    @Override
    default BufferedImage run(final @NonNull BufferedImage image) {
        throw new IllegalStateException("A CharacterShader must be run using the run(BufferedImage, AsciiCharacter) function.");
    }

    /**
     * Runs the shader on a character image.
     *
     * @param image
     *          The character image.
     *
     * @param character
     *          The character.
     *
     * @return
     *          The processed character image.
     *
     * @throws NullPointerException
     *           If the image or character is null.
     */
    BufferedImage run(final @NonNull BufferedImage image, final @NonNull AsciiCharacter character);
}
