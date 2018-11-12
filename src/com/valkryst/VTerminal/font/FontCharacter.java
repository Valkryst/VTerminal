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
    @Getter private final char character;
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
    FontCharacter(final char character, BufferedImage image) {
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
     * Resizes the character's image.
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
