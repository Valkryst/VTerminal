package com.valkryst.VTerminal.font;

import lombok.Getter;
import lombok.ToString;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

@ToString
class FontCharacter {
    /** The character. */
    @Getter private final int character;
    /** The character's image. */
    @Getter private BufferedImage image;

    /**
     * Constructs a new FontCharacter.
     *
     * @param character
     *          The character.
     *
     * @param image
     *          The character's image.
     */
    FontCharacter(final int character, BufferedImage image) {
        if (image == null || image.getWidth() < 1 || image.getHeight() < 1) {
            image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);

            final Graphics gc = image.getGraphics();
            gc.setColor(Color.MAGENTA);
            gc.drawRect(0, 0, image.getWidth(), image.getHeight());
            gc.dispose();
        }

        this.character = character;
        this.image = image;
    }

    /**
     * Resizes the character's image by some scale percentages.
     *
     * @param scaleWidth
     *          The amount to scale the width by.
     *
     * @param scaleHeight
     *          The amount to scale the height by.
     */
    void resizeImage(final double scaleWidth, final double scaleHeight) {
        if (scaleWidth <= 0 || scaleHeight <= 0) {
            return;
        }

        final AffineTransform tx = AffineTransform.getScaleInstance(scaleWidth, scaleHeight);
        final AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

        image = op.filter(image, null);
    }

    /**
     * Resizes the character's image to a specific width and height.
     *
     * @param width
     *          The new width.
     *
     * @param height
     *          The new height.
     */
    void resizeImage(final int width, final int height) {
        if (width <= 0 || height <= 0) {
            return;
        }

        final int imgWidth = getWidth();
        final int imgHeight = getHeight();
        final double scaleWidth = Math.abs(width - imgWidth) / (double) imgWidth;
        final double scaleHeight = Math.abs(height - imgHeight) / (double) imgHeight;

        resizeImage(scaleWidth, scaleHeight);
    }

    /**
     * Retrieves the width of the character's image.
     *
     * @return
     *          The width of the character's image.
     */
    public int getWidth() {
        return image.getWidth();
    }

    /**
     * Retrieves the height of the character's image.
     *
     * @return
     *          The height of the character's image.
     */
    public int getHeight() {
        return image.getHeight();
    }
}
