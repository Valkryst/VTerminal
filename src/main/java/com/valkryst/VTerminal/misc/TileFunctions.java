package com.valkryst.VTerminal.misc;

import com.valkryst.VTerminal.Tile;
import lombok.NonNull;

import java.awt.Color;

public class TileFunctions {
    public void applyColorGradient(final @NonNull Color colorFrom, final @NonNull Color colorTo, final boolean applyToBackground, final Tile... tiles) {
        if (tiles == null || tiles.length == 0) {
            return;
        }

        // Determine the difference between the RGB values of the colors:
        final float redDifference = colorTo.getRed() - colorFrom.getRed();
        final float greenDifference = colorTo.getGreen() - colorFrom.getGreen();
        final float blueDifference = colorTo.getBlue() - colorFrom.getBlue();

        // Determine the amount to increment the RGB values by and convert the values to the 0-1 scale:
        final float redChangePerColumn = (redDifference / tiles.length) / 255f;
        final float greenChangePerColumn = (greenDifference / tiles.length) / 255f;
        final float blueChangePerColumn = (blueDifference / tiles.length) / 255f;

        // Set the starting RGB values and convert them to the 0-1 scale:
        float redCurrent = colorFrom.getRed() / 255f;
        float greenCurrent = colorFrom.getGreen() / 255f;
        float blueCurrent = colorFrom.getBlue() / 255f;

        // Set the new color values:
        for (final Tile tile : tiles) {
            if (applyToBackground) {
                tile.setBackgroundColor(new Color(redCurrent, greenCurrent, blueCurrent));
            } else {
                tile.setForegroundColor(new Color(redCurrent, greenCurrent, blueCurrent));
            }

            redCurrent += redChangePerColumn;
            greenCurrent += greenChangePerColumn;
            blueCurrent += blueChangePerColumn;
        }
    }
}
