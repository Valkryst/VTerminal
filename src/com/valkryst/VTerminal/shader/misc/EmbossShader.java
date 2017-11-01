package com.valkryst.VTerminal.shader.misc;

import com.jhlabs.image.BumpFilter;
import com.jhlabs.image.ConvolveFilter;
import com.valkryst.VTerminal.shader.Shader;
import lombok.Data;
import lombok.NonNull;

import java.awt.image.BufferedImage;

@Data
public class EmbossShader implements Shader {
    /** What do do at the image edges. */
    private int edgeAction = ConvolveFilter.CLAMP_EDGES;
    /** Whether to promultiply the alpha before convolving. */
    private boolean premultiplyAlpha = true;
    /** Whether to convolve alpha. */
    private boolean useAlpha = true;

    @Override
    public BufferedImage run(final @NonNull BufferedImage image) {
        final BumpFilter filter = new BumpFilter();
        filter.setEdgeAction(edgeAction);
        filter.setPremultiplyAlpha(premultiplyAlpha);
        filter.setUseAlpha(useAlpha);
        return filter.filter(image, null);
    }

    @Override
    public Shader copy() {
        final EmbossShader shader = new EmbossShader();
        shader.setEdgeAction(edgeAction);
        shader.setPremultiplyAlpha(premultiplyAlpha);
        shader.setUseAlpha(useAlpha);
        return shader;
    }
}
