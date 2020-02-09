package com.valkryst.VTerminal.shader.misc;

import com.valkryst.VTerminal.shader.Shader;
import lombok.Data;
import lombok.NonNull;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

@Data
public class FlipShader implements Shader {
    /** Whether or not the image should be flipped horizontally. */
    private boolean isFlippedHorizontally = false;
    /** Whether or not the image should be flipped vertically. */
    private boolean isFlippedVertically = false;

    /** Constructs a new FlipShader. */
    public FlipShader() {}

    /**
     * Constructs a new FlipShader.
     *
     * @param isFlippedHorizontally
     *          Whether or not the image should be flipped horizontally.
     *
     * @param isFlippedVertically
     *          Whether or not the image should be flipped vertically.
     */
    public FlipShader(final boolean isFlippedHorizontally, final boolean isFlippedVertically) {
        this.isFlippedHorizontally = isFlippedHorizontally;
        this.isFlippedVertically = isFlippedVertically;
    }

    @Override
    public BufferedImage run(final @NonNull BufferedImage image) {
        if (isFlippedHorizontally || isFlippedVertically) {
            final double scaleX = isFlippedHorizontally ? -1 : 1;
            final double scaleY = isFlippedVertically ? -1 : 1;
            final double translateX = isFlippedHorizontally ? -image.getWidth() : 0;
            final double translateY = isFlippedVertically ? -image.getHeight() : 0;

            final AffineTransform tx = AffineTransform.getScaleInstance(scaleX, scaleY);
            tx.translate(translateX, translateY);

            final BufferedImageOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            return op.filter(image, null);
        }

        return image;
    }

    @Override
    public Shader copy() {
        final FlipShader flipShader = new FlipShader();
        flipShader.setFlippedHorizontally(isFlippedHorizontally);
        flipShader.setFlippedVertically(isFlippedVertically);
        return flipShader;
    }
}
