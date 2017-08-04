package com.valkryst.VTerminal.samples;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.component.Screen;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;

import java.awt.Color;
import java.io.IOException;
import java.net.URISyntaxException;

public class SampleStringOperations {
    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);

        final PanelBuilder builder = new PanelBuilder();
        builder.setFont(font);

        final Panel panel = builder.build();

        final Screen screen = panel.getScreen();

        Thread.sleep(100);

        screen.write("Writing a String on Line #2", 0, 1);

        screen.write("Horizontally Flipping a String on Line #4", 0, 3);
        screen.getString(3).setFlippedHorizontally(true);

        screen.write("Vertically Flipping a String on Line #5", 0, 4);
        screen.getString(4).setFlippedVertically(true);

        screen.write("Vertically & Horizontally Flipping a String on Line #6", 0, 5);
        screen.getString(5).setFlippedHorizontally(true);
        screen.getString(5).setFlippedVertically(true);

        screen.write("Applying a Tint Gradient on Line #7", 0, 6);
        screen.getString(6).applyTintGradient(Color.CYAN, true);

        screen.write("Applying a Shade Gradient on Line #8", 0, 7);
        screen.getString(7).applyShadeGradient(Color.CYAN, true);

        screen.write("Applying a Background Color Gradient on Line #9", 0, 8);
        screen.getString(8).applyColorGradient(Color.CYAN, Color.PINK, true);

        screen.write("Applying a Foreground Color Gradient on Line #10", 0, 9);
        screen.getString(9).applyColorGradient(Color.CYAN, Color.PINK, false);

        screen.write("Applying a Back/Foreground Color Gradient on Line #11", 0, 10);
        screen.getString(10).applyColorGradient(Color.RED, Color.GREEN, false);
        screen.getString(10).applyColorGradient(Color.CYAN, Color.PINK, true);

        screen.write("Changing Background Color on Line #12", 0, 11);
        screen.getString(11).setBackgroundColor(Color.RED);

        screen.write("Changing Foreground Color on Line #13", 0, 12);
        screen.getString(12).setForegroundColor(Color.RED);

        screen.write("Changing Back/Foreground Color on Line #14", 0, 13);
        screen.getString(13).setBackgroundColor(Color.RED);
        screen.getString(13).setForegroundColor(Color.BLUE);

        screen.write("Applying Underline on Line #15", 0, 14);
        screen.getString(14).setUnderlined(true);

        panel.draw();
    }
}
