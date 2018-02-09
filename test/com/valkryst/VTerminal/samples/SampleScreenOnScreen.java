package com.valkryst.VTerminal.samples;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.builder.component.ScreenBuilder;
import com.valkryst.VTerminal.component.Screen;

import java.awt.Color;
import java.io.IOException;
import java.net.URISyntaxException;

public class SampleScreenOnScreen {
    public static void main(final String[] args) throws IOException, URISyntaxException {
        final Panel panel = new PanelBuilder().build();

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
