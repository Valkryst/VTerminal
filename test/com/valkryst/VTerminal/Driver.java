package com.valkryst.VTerminal;

import com.valkryst.VTerminal.builder.LabelBuilder;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;

import java.awt.Dimension;
import java.io.IOException;
import java.net.URISyntaxException;

public class Driver {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        // Load Font and create ImageCache
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt", 1);

        // Construct Canvas & Components
        final Dimension dimensions = new Dimension(60, 26);
        final Screen screen = new Screen(dimensions, font);

        // Component B
        final LabelBuilder labelBuilder = new LabelBuilder();
        labelBuilder.getPosition().setLocation(0, 1);
        labelBuilder.setText("sdtfghfghjgf");

        screen.addComponent(labelBuilder.build());

        // Construct and show frame
        screen.addCanvasToJFrame();
    }
}
