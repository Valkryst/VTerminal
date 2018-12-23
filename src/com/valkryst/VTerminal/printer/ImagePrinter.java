package com.valkryst.VTerminal.printer;

import com.valkryst.VTerminal.Tile;
import com.valkryst.VTerminal.TileGrid;
import lombok.*;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

@ToString
public class ImagePrinter {
    /** The image to print. */
    private final BufferedImage image;

    /** Whether or not to flip the image horizontally when printing. */
    @Getter @Setter private boolean flipHorizontally = false;
    /** Whether or not to flip the image vertically when printing. */
    @Getter @Setter private boolean flipVertically = false;

    /** The amount to scale the image by, horizontally, when printing. */
    @Getter private double scaleX = 2;
    /** The amount to scale the image by, vertically, when printing. */
    @Getter private double scaleY = 1;

    /** The character to print the image with. */
    @Getter @Setter private char printChar = '█';

    /**
     * Constructs a new ImagePrinter.
     *
     * @param image
     *          The image to print.
     *
     * @throws NullPointerException
     *          If the image is null.
     */
    public ImagePrinter(final @NonNull BufferedImage image) {
        this.image = image;
    }

    /**
     * Prints an image on a grid.
     *
     * Does nothing if the grid or point are null.
     *
     * @param grid
     *          The grid.
     *
     * @param position
     *          The x/y-axis (column/row) coordinates of the top-left character.
     */
    public void print(final TileGrid grid, final Point position) {
        if (grid == null || position == null) {
            return;
        }

        final BufferedImage temp = applyTransformations();

        for (int y = 0 ; y < temp.getHeight() && y < grid.getHeight() ; y++) {
            for (int x = 0 ; x < temp.getWidth() && x < grid.getWidth() ; x++) {
                final int hexColor = temp.getRGB(x,y);
                final int alpha = (hexColor >> 24) & 0xFF;
                final int red = (hexColor >> 16) & 0xFF;
                final int green = (hexColor >> 8) & 0xFF;
                final int blue = (hexColor) & 0xFF;

                final int charX = x + position.x;
                final int charY = y + position.y;

                final Tile character = grid.getTileAt(charX, charY);

                if (character != null) {
                    character.setCharacter(printChar);
                    character.setForegroundColor(new Color(red, green, blue, alpha));
                }
            }
        }
    }

    /**
     * Prints an image on a grid using special characters in order to display 2x2 pixels per tile.
     *
     * Does nothing if the grid or point are null.
     *
     * @param grid
     *          The grid.
     *
     * @param position
     *          The x/y-axis (column/row) coordinates of the top-left character.
     *
     * @throws IllegalArgumentException
     *          If the image'ss width/height isn't divisible by 2.
     *          This check is performed after any transformations/scaling.
     *
     * @throws IllegalStateException
     *          If a 2x2 chunk of the image contains more than 2 unique colors.
     */
    public void printDetailed(final TileGrid grid, final Point position) {
        if (grid == null || position == null) {
            return;
        }

        final BufferedImage temp = applyTransformations();

        if (temp.getWidth() % 2 != 0) {
            throw new IllegalArgumentException("The image must have a width that is divisible by 2. The width is currently " + temp.getWidth() + ".");
        }

        if (temp.getHeight() % 2 != 0) {
            throw new IllegalArgumentException("The image must have a height that is divisible by 2. The height is currently " + temp.getHeight() + ".");
        }

        for (int imageY = 0 ; imageY < temp.getHeight() ; imageY += 2) {
            for (int imageX = 0 ; imageX < temp.getWidth() ; imageX += 2) {
                // Retrieve pixel values for this 4x4 chunk of the image.
                final int rgb_topLeft = temp.getRGB(imageX, imageY);
                final int rgb_topRight = temp.getRGB(imageX + 1 , imageY);
                final int rgb_botLeft = temp.getRGB(imageX, imageY + 1);
                final int rgb_botRight = temp.getRGB(imageX + 1, imageY + 1);

                final int rgb_firstUniqueColor = rgb_topLeft;
                final int rgb_secondUniqueColor;

                /*
                 * Ensure there are only two RGB values in this 2x2 chunk
                 * of pixels.
                 *
                 * We assume that the Top Left value is unique.
                 * If we have a unique color in the Top Right value, then
                 * the bottom values must be equal to either the Top Left
                 * or Top Right values.
                */
                if (rgb_topRight != rgb_topLeft) {
                    /*
                     * If the Bottom Left value isn't equal to either of the
                     * top values, then we have a third unique color.
                     */
                    if (rgb_botLeft != rgb_topLeft && rgb_botLeft != rgb_topRight) {
                        throw new IllegalStateException("The 2x2 pixel chunk, starting at (" + imageX + ", " + imageY + ") contains more than two colors.");
                    }

                    /*
                     * If the Bottom Right value isn't equal to either of the
                     * top values, then we have a third unique color.
                     */
                    if (rgb_botRight != rgb_topLeft && rgb_botRight != rgb_topRight) {
                        throw new IllegalStateException("The 2x2 pixel chunk, starting at (" + imageX + ", " + imageY + ") contains more than two colors.");
                    }

                    rgb_secondUniqueColor = rgb_topRight;
                } else {
                    /*
                     * If the Bottom Right value isn't equal to the Bottom
                     * Left value and it's not equal to the Top Left value
                     * (when the Top Left & Top Right are equal), then
                     * we have a third unique color.
                     */
                    if (rgb_botRight != rgb_botLeft) {
                        throw new IllegalStateException("The 2x2 pixel chunk, starting at (" + imageX + ", " + imageY + ") contains more than two colors.");
                    }

                    rgb_secondUniqueColor = rgb_botRight;
                }

                /*
                 * Calculate a unique value to represent the 2x2 chunk of
                 * pixels.
                 *
                 * Starting from the Top Left, we add a specific power of
                 * 2 to our value, for every pixel that matches the color
                 * of the Top Left RGB value.
                 *
                 * Powers of Two
                 *      Top Left = 1
                 *      Top Right = 2
                 *      Bottom Right = 4
                 *      Bottom Left = 8
                 */
                int chunkValue = 1;

                if (rgb_topLeft == rgb_topRight) {
                    chunkValue += 2;
                }

                if (rgb_topLeft == rgb_botRight) {
                    chunkValue += 4;
                }

                if (rgb_topLeft == rgb_botLeft) {
                    chunkValue += 8;
                }

                // Determine the character to use, based on the chunkValue.
                final char printChar;

                switch (chunkValue) {
                    case 1: {
                        printChar = '▘';
                        break;
                    }
                    case 3: {
                        printChar = '▀';
                        break;
                    }
                    case 2: {
                        printChar = '▝';
                        break;
                    }
                    case 4: {
                        printChar = '▗';
                        break;
                    }
                    case 5: {
                        printChar = '▚';
                        break;
                    }
                    case 6: {
                        printChar = '▌';
                        break;
                    }
                    case 7: {
                        printChar = '▜';
                        break;
                    }
                    case 8: {
                        printChar = '▖';
                        break;
                    }
                    case 9: {
                        printChar = '▐';
                        break;
                    }
                    case 10: {
                        printChar = '▞';
                        break;
                    }
                    case 11: {
                        printChar = '▛';
                        break;
                    }
                    case 12: {
                        printChar = '▄';
                        break;
                    }
                    case 13: {
                        printChar = '▙';
                        break;
                    }
                    case 14: {
                        printChar = '▟';
                        break;
                    }
                    case 15: {
                        printChar = '█';
                        break;
                    }
                    default: {
                        printChar = '?';
                        break;
                    }
                }

                // Update the TileGrid
                final Color backgroundColor = new Color(rgb_secondUniqueColor, true);
                final Color foregroundColor = new Color(rgb_firstUniqueColor, true);

                final int gridX = imageX / 2;
                final int gridY = imageY / 2;

                final int tileX = gridX + position.x;
                final int tileY = gridY + position.y;

                final Tile tile = grid.getTileAt(tileX, tileY);

                if (tile != null) {
                    tile.setCharacter(printChar);
                    tile.setBackgroundColor(backgroundColor);
                    tile.setForegroundColor(foregroundColor);
                }
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

    /**
     * Sets the amount to scale the image by, horizontally, when printing.
     *
     * @param scaleX
     *          The new x-axis scale.
     */
    public void setScaleX(final double scaleX) {
        if (scaleX > 0) {
            this.scaleX = scaleX;
        }
    }

    /**
     * Sets the amount to scale the image by, vertically, when printing.
     *
     * @param scaleY
     *          The new y-axis scale.
     */
    public void setScaleY(final double scaleY) {
        if (scaleY > 0) {
            this.scaleY = scaleY;
        }
    }
}
