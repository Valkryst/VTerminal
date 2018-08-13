package com.valkryst.VTerminal.shader.misc;

import com.valkryst.VTerminal.shader.Shader;
import lombok.Data;

import java.awt.*;
import java.awt.image.BufferedImage;

@Data
public class BorderShader implements Shader {
    /** Whether the top border line should be drawn. */
    private boolean topEnabled = true;
    /** Whether the bottom border line should be drawn. */
    private boolean bottomEnabled = true;
    /** Whether the left border line should be drawn. */
    private boolean leftEnabled = true;
    /** Whether the right border line should be drawn. */
    private boolean rightEnabled = true;

    /** The color of the top border line. */
    private Color topColor = Color.WHITE;
    /** The color of the bottom border line. */
    private Color bottomColor = Color.WHITE;
    /** The color of the left border line. */
    private Color leftColor = Color.WHITE;
    /** The color of the right border line. */
    private Color rightColor = Color.WHITE;

    @Override
    public BufferedImage run(final BufferedImage image) {
        if (!topEnabled && !bottomEnabled && !leftEnabled && !rightEnabled) {
            return image;
        }

        final int width = image.getWidth() - 1;
        final int height = image.getHeight() - 1;
        final Graphics2D gc = (Graphics2D) image.getGraphics();

        if (topEnabled) {
            gc.setColor(topColor);
            gc.drawLine(0, 0, width, 0);
        }

        if (bottomEnabled) {
            gc.setColor(bottomColor);
            gc.drawLine(0, height, width, height);
        }

        if (leftEnabled) {
            gc.setColor(leftColor);
            gc.drawLine(0, 0, 0, height);
        }

        if (rightEnabled) {
            gc.setColor(rightColor);
            gc.drawLine(width, 0, width, height);
        }

        return image;
    }

    @Override
    public Shader copy() {
        final BorderShader shader = new BorderShader();

        shader.setTopEnabled(topEnabled);
        shader.setBottomEnabled(bottomEnabled);
        shader.setLeftEnabled(leftEnabled);
        shader.setRightEnabled(rightEnabled);

        shader.setTopColor(new Color(topColor.getRed(), topColor.getGreen(), topColor.getBlue(), topColor.getAlpha()));
        shader.setBottomColor(new Color(bottomColor.getRed(), bottomColor.getGreen(), bottomColor.getBlue(), bottomColor.getAlpha()));
        shader.setLeftColor(new Color(leftColor.getRed(), leftColor.getGreen(), leftColor.getBlue(), leftColor.getAlpha()));
        shader.setRightColor(new Color(rightColor.getRed(), rightColor.getGreen(), rightColor.getBlue(), rightColor.getAlpha()));

        return null;
    }

    /**
     * Sets the value of all border colors to the same color.
     *
     * @param color
     *          The color.
     */
    public void setAllBorderColors(final Color color) {
        if (color != null) {
            topColor = color;
            bottomColor = color;
            leftColor = color;
            rightColor = color;
        }
    }
}
