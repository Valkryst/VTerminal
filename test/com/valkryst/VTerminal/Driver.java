package com.valkryst.VTerminal;

import com.valkryst.VTerminal.component.RadioButtonGroup;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.builder.RadioButtonBuilder;
import com.valkryst.VTerminal.component.Component;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.net.URISyntaxException;

public class Driver {
    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        // Load Font and create ImageCache
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/20pt/bitmap.png", "Fonts/DejaVu Sans Mono/20pt/data.fnt", 1);

        // Construct Canvas & Components
        final Dimension dimensions = new Dimension(60, 26);
        final Screen screen = new Screen(dimensions, font);

        // Component A
        Component component = new Component(dimensions, new Point(0, 0));

        final Tile[][] tiles = component.getTiles().getRectangularSubset(0, 0, dimensions.width, dimensions.height);

        for (int y = 0 ; y < tiles.length ; y++) {
            for (int x = 0 ; x < tiles[0].length ; x++) {
                tiles[y][x].setBackgroundColor(new Color(255 - (x * 4), 0, y * 6));
                tiles[y][x].setForegroundColor(new Color(y * 6, 255 - (x * 4), (x + y)));
                tiles[y][x].setCharacter((char) (x + 65));
            }
        }

        screen.addComponent(component);

        // Component B
        final RadioButtonBuilder radioButtonBuilder = new RadioButtonBuilder();
        radioButtonBuilder.getPosition().setLocation(0, 1);
        radioButtonBuilder.setText("sdtfghfghjgf");
        radioButtonBuilder.setGroup(new RadioButtonGroup());

        screen.addComponent(radioButtonBuilder.build());

        // Construct and show frame
        final JFrame frame = screen.addCanvasToJFrame();
        frame.setVisible(true);

        // Test Drawing
        screen.draw();
    }
}
