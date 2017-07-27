package com.valkryst.VTerminal.samples;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.builder.component.*;
import com.valkryst.VTerminal.component.LoadingBar;
import com.valkryst.VTerminal.component.RadioButtonGroup;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.printer.RectanglePrinter;
import com.valkryst.VTerminal.printer.RectangleType;

import javax.swing.Timer;
import java.awt.Color;
import java.io.IOException;
import java.net.URISyntaxException;

public class SampleComponents {
    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);
        final Panel panel = new PanelBuilder().setFont(font).build();

        Thread.sleep(100);

        panel.getScreen().setBackgroundColor(new Color(0xFF366C9F, true));

        // Border
        final RectanglePrinter printer = new RectanglePrinter();
        printer.setRectangleType(RectangleType.HEAVY);
        printer.setWidth(80);
        printer.setHeight(24);
        printer.print(panel, 0, 0);

        printer.setWidth(24);
        printer.print(panel, 0, 0);

        // Title #1
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

        // Fixed Width Text Field
        new TextFieldBuilder().setColumnAndRowIndices(1, 14)
                              .setPanel(panel)
                              .setWidth(20)
                              .build();

        // Variable Width Text Field
        new TextFieldBuilder().setColumnAndRowIndices(1, 16)
                              .setPanel(panel)
                              .setWidth(20)
                              .setMaxCharacters(40)
                              .build();

        // Fixed Width & Height Text Area:
        new LabelBuilder().setColumnAndRowIndices(25, 2)
                          .setPanel(panel)
                          .setText("Fixed Width & Height")
                          .build()
                          .getString(0)
                          .setUnderlined(true);

        new TextAreaBuilder().setColumnAndRowIndices(25, 3)
                             .setPanel(panel)
                             .setWidth(20)
                             .setHeight(3)
                             .setMaxVerticalCharacters(3)
                             .build();

        // Variable Width Text Area:
        new LabelBuilder().setColumnAndRowIndices(25, 7)
                          .setPanel(panel)
                          .setText("Variable Width Only")
                          .build()
                          .getString(0)
                          .setUnderlined(true);

        new TextAreaBuilder().setColumnAndRowIndices(25, 8)
                             .setPanel(panel)
                             .setWidth(20)
                             .setHeight(3)
                             .setMaxVerticalCharacters(3)
                             .setMaxHorizontalCharacters(40)
                             .build();

        // Variable Height Text Area:
        new LabelBuilder().setColumnAndRowIndices(25, 12)
                          .setPanel(panel)
                          .setText("Variable Height Only")
                          .build()
                          .getString(0)
                          .setUnderlined(true);

        new TextAreaBuilder().setColumnAndRowIndices(25, 13)
                             .setPanel(panel)
                             .setWidth(20)
                             .setHeight(3)
                             .setMaxVerticalCharacters(3)
                             .setMaxVerticalCharacters(6)
                             .build();

        // Variable Width & HeightText Area:
        new LabelBuilder().setColumnAndRowIndices(25, 17)
                          .setPanel(panel)
                          .setText("Variable Width & Height")
                          .build()
                          .getString(0)
                          .setUnderlined(true);

        new TextAreaBuilder().setColumnAndRowIndices(25, 18)
                             .setPanel(panel)
                             .setWidth(20)
                             .setHeight(3)
                             .setMaxHorizontalCharacters(40)
                             .setMaxVerticalCharacters(6)
                             .build();

        // Loading Bar
        final LoadingBar loadingBar = new LoadingBarBuilder().setColumnAndRowIndices(1, 19)
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
