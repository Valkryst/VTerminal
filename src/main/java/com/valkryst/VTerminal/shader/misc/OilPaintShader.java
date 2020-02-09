package com.valkryst.VTerminal.shader.misc;

import com.jhlabs.image.OilFilter;
import com.valkryst.VTerminal.shader.Shader;
import lombok.Data;
import lombok.NonNull;

import java.awt.image.BufferedImage;

@Data
public class OilPaintShader implements Shader {
    /** The range of the effect, in pixels. */
    private int range = 3;
    /** The number of levels of the effect. */
    private int levels = 256;

    @Override
    public BufferedImage run(final @NonNull BufferedImage image) {
        final OilFilter filter = new OilFilter();
        filter.setLevels(levels);
        filter.setRange(range);
        return filter.filter(image, null);
    }

    @Override
    public Shader copy() {
        final OilPaintShader shader = new OilPaintShader();
        shader.setLevels(levels);
        shader.setRange(range);
        return shader;
    }
}