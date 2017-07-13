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
        System.setProperty("sun.java2d.opengl", "true");
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);
        final Panel panel = new PanelBuilder().setFont(font).build();
        final Screen screen = panel.getScreen();

        Thread.sleep(100);

        screen.clear('X');

        screen.write("Writing a String on Line #2", 0, 1);

        screen.write("Applying a Color Gradient on Line #3", 0, 2);
        screen.getString(2).applyColorGradient(Color.PINK, Color.CYAN, true);

        screen.write("Horizontally Flipping a String on Line #4", 0, 3);
        screen.getString(3).flipCharactersHorizontally();

        screen.write("Vertically Flipping a String on Line #5", 0, 4);
        screen.getString(4).flipCharactersVertically();

        screen.write("Vertically & Horizontally Flipping a String on Line #6", 0, 5);
        screen.getString(5).flipCharactersHorizontallyAndVertically();

        panel.draw();
    }
}
