package com.valkryst.VTerminal.samples;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.builder.component.ScreenBuilder;
import com.valkryst.VTerminal.component.Screen;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import org.json.simple.parser.ParseException;

import java.awt.Color;
import java.io.IOException;
import java.net.URISyntaxException;

public class SampleScreenOnScreen {
    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException, ParseException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);

        final PanelBuilder builder = new PanelBuilder();
        builder.setFont(font);

        final Panel panel = builder.build();

        Thread.sleep(100);

        final ScreenBuilder screenBuilder = new ScreenBuilder();
        screenBuilder.setColumnIndex(3);
        screenBuilder.setRowIndex(3);
        screenBuilder.setWidth(panel.getWidthInCharacters() - 10);
        screenBuilder.setHeight(panel.getHeightInCharacters() - 10);

        final Screen screenA = screenBuilder.build();
        screenA.setBackgroundColor(Color.CYAN);

        panel.addComponents(screenA);

        panel.draw();
    }
}
