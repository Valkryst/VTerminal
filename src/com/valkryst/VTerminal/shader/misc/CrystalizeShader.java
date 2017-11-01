package com.valkryst.VTerminal.shader.misc;

import com.jhlabs.image.CrystallizeFilter;
import com.valkryst.VTerminal.shader.Shader;
import lombok.Data;
import lombok.NonNull;

import java.awt.Color;
import java.awt.image.BufferedImage;

@Data
public class CrystalizeShader implements Shader {
    private float edgeThickness = 0.4f;
    private boolean fadeEdges = false;
    private int edgeColor = 0xff000000;

    @Override
    public BufferedImage run(final @NonNull BufferedImage image) {
        final CrystallizeFilter filter = new CrystallizeFilter();
        filter.setEdgeThickness(edgeThickness);
        filter.setFadeEdges(fadeEdges);
        filter.setEdgeColor(edgeColor);
        return filter.filter(image, null);
    }

    @Override
    public Shader copy() {
        final CrystalizeShader shader = new CrystalizeShader();
        shader.setEdgeThickness(edgeThickness);
        shader.setFadeEdges(fadeEdges);
        shader.setEdgeColor(edgeColor);
        return shader;
    }

    /**
     * Sets the edge color.
     *
     * @param color
     *          The new color.
     */
    public void setEdgeColor(final int color) {
        edgeColor = color;
    }

    /**
     * Sets the edge color.
     *
     * @param color
     *          The new color.
     *
     * @throws NullPointerException
     *          If the color is null.
     */
    public void setEdgeColor(final @NonNull Color color) {
        edgeColor = color.getRGB();
    }
}
