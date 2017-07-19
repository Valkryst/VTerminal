package com.valkryst.VTerminal.samples;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.builder.component.*;
import com.valkryst.VTerminal.component.LoadingBar;
import com.valkryst.VTerminal.component.RadioButtonGroup;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;

import javax.swing.Timer;
import java.awt.Color;
import java.io.IOException;
import java.net.URISyntaxException;

public class SampleComponents {
    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException {
        System.setProperty("sun.java2d.opengl", "true");
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);
        final Panel panel = new PanelBuilder().setFont(font).build();

        Thread.sleep(100);

        panel.getScreen().setBackgroundColor(new Color(0xFF366C9F, true));

        // Title
        new LabelBuilder().setColumnAndRowIndices(1, 1)
                            .setPanel(panel)
                            .setText("Component Library Test")
                            .build()
                            .getString(0)
                            .setUnderlined(true);

        // Button
        new ButtonBuilder().setColumnAndRowIndices(1, 3)
                            .setPanel(panel)
                            .setText("Click")
                            .setOnClickFunction(() -> System.out.println("Clicked!"))
                            .build();

        // First Radio Button Group
        final RadioButtonGroup groupA = new RadioButtonGroup();

        new RadioButtonBuilder().setColumnAndRowIndices(1, 5)
                                .setPanel(panel)
                                .setGroup(groupA)
                                .setText("Group A, Option 1")
                                .build();

        new RadioButtonBuilder().setColumnAndRowIndices(1, 6)
                                .setPanel(panel)
                                .setGroup(groupA)
                                .setText("Group A, Option 2")
                                .build();


        // Second Radio Button Group
        final RadioButtonGroup groupB = new RadioButtonGroup();

        new RadioButtonBuilder().setColumnAndRowIndices(1, 8)
                                .setPanel(panel)
                                .setGroup(groupB)
                                .setText("Group B, Option 1")
                                .build();

        new RadioButtonBuilder().setColumnAndRowIndices(1, 9)
                                .setPanel(panel)
                                .setGroup(groupB)
                                .setText("Group B, Option 2")
                                .build();

        // Check Boxes
        new CheckBoxBuilder().setColumnAndRowIndices(1, 11)
                            .setPanel(panel)
                            .setText("Checkbox A")
                            .build();

        new CheckBoxBuilder().setColumnAndRowIndices(1, 12)
                            .setPanel(panel)
                            .setText("Checkbox B")
                            .build();

        // Text Field
        new TextFieldBuilder().setColumnAndRowIndices(1, 14)
                                .setPanel(panel)
                                .setWidth(20)
                                .build();

        // Loading Bar
        final LoadingBar loadingBar = new LoadingBarBuilder().setColumnAndRowIndices(1, 16)
                                                                .setPanel(panel)
                                                                .setWidth(20)
                                                                .build();

        Timer timer = new Timer(1000, e -> {
            int pct = loadingBar.getPercentComplete();

            if (pct < 100) {
                pct += 5;
            } else {
                pct = 0;
            }

            loadingBar.setPercentComplete(pct);
        });
        timer.start();

        panel.draw();
    }
}
