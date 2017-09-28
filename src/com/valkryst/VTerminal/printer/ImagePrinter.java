package com.valkryst.VTerminal.printer;

import com.valkryst.VTerminal.AsciiCharacter;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.component.Component;
import com.valkryst.VTerminal.component.Screen;
import lombok.*;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

@EqualsAndHashCode
@ToString
public class ImagePrinter {
    /** The image to print. */
    private final BufferedImage image;

    /** Whether or not to flip the image horizontally when printing. */
    @Getter @Setter private boolean flipHorizontally = false;
    /** Whether or not to flip the image vertically when printing. */
    @Getter @Setter private boolean flipVertically = false;

    /** The amount to scale the image by, horizontally, when printing. */
    @Getter private int scaleX = 2;
    /** The amount to scale the image by, vertically, when printing. */
    @Getter private int scaleY = 1;

    /** The character to print the ellipse with. */
    @Getter @Setter private char printChar = '█';

    /**
     * Constructs a new ImagePrinter.
     *
     * @param image
     *         The image to print.
     *
     * @throws NullPointerException
     *         If the image is null.
     */
    public ImagePrinter(final @NonNull BufferedImage image) {
        this.image = image;
    }

    /**
     * Prints an image on the screen of a panel.
     *
     * @param panel
     *         The panel.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate of the top-left character.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate of the top-left character.
     *
     * @throws NullPointerException
     *         If the panel is null.
     */
    public void print(final @NonNull Panel panel, final int columnIndex, final int rowIndex) {
        print((Component) panel.getScreen(), columnIndex, rowIndex);
    }

    /**
     * Prints an image on a screen.
     *
     * @param screen
     *         The screen.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate of the top-left character.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate of the top-left character.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    public void print(final @NonNull Screen screen, final int columnIndex, final int rowIndex) {
        print((Component) screen, columnIndex, rowIndex);
    }

    /**
     * Prints an image on a component.
     *
     * @param component
     *         The component.
     *
     * @param columnIndex
     *         The x-axis (column) coordinate of the top-left character.
     *
     * @param rowIndex
     *         The y-axis (row) coordinate of the top-left character.
     *
     * @throws NullPointerException
     *         If the screen is null.
     */
    private void print(final @NonNull Component component, final int columnIndex, final int rowIndex) {
        final BufferedImage temp = applyTransformations();
        final Point charPosition = new Point(0, 0);

        for (int y = 0 ; y < temp.getHeight() && y < component.getHeight() ; y++) {
            for (int x = 0 ; x < temp.getWidth() && x < component.getWidth() ; x++) {
                final int hexColor = temp.getRGB(x,y);
                final int red = (hexColor & 0x00ff0000) >> 16;
                final int green = (hexColor & 0x0000ff00) >> 8;
                final int blue =  hexColor & 0x000000ff;

                final int charX = x + columnIndex;
                final int charY = y + rowIndex;
                charPosition.setLocation(charX, charY);

                final AsciiCharacter character = component.getCharacterAt(charPosition);
                character.setCharacter(printChar);
                character.setForegroundColor(new Color(red, green, blue));
            }
        }
    }

    /**
     * Flips and scales the image.
     *
     * @return
     *         The flipped and scaled image.
     */
    private BufferedImage applyTransformations() {
        final AffineTransform tx;

        if (flipHorizontally && flipVertically) {
            tx = AffineTransform.getScaleInstance(-scaleX, -scaleY);
            tx.translate(-image.getWidth(), -image.getHeight());
        } else if (flipHorizontally) {
            tx = AffineTransform.getScaleInstance(-scaleX, scaleY);
            tx.translate(-image.getWidth(), 0);
        } else if(flipVertically) {
            tx = AffineTransform.getScaleInstance(scaleX, -scaleY);
            tx.translate(0, -image.getHeight());
        } else {
            tx = AffineTransform.getScaleInstance(scaleX, scaleY);
        }

        final AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        return op.filter(image, null);
    }

    public void setScaleX(final int scaleX) {
        if (scaleX >= 1) {
            this.scaleX = scaleX;
        }
    }

    public void setScaleY(final int scaleY) {
        if (scaleY >= 1) {
            this.scaleY = scaleY;
        }
    }
}
