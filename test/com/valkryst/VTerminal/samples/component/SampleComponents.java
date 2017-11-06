package com.valkryst.VTerminal.samples.component;

import com.valkryst.VTerminal.AsciiString;
import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.builder.component.*;
import com.valkryst.VTerminal.component.Label;
import com.valkryst.VTerminal.component.Layer;
import com.valkryst.VTerminal.component.ProgressBar;
import com.valkryst.VTerminal.component.RadioButtonGroup;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;
import com.valkryst.VTerminal.printer.RectanglePrinter;
import com.valkryst.VTerminal.printer.RectangleType;

import javax.swing.Timer;
import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.net.URISyntaxException;

public class SampleComponents {
    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);

        final PanelBuilder builder = new PanelBuilder();
        builder.setFont(font);

        final Panel panel = builder.build();

        Thread.sleep(100);

        panel.getScreen().setBackgroundColor(new Color(45, 45, 45, 255));

        // Border
        final RectanglePrinter printer = new RectanglePrinter();
        printer.setRectangleType(RectangleType.HEAVY);
        printer.setWidth(80);
        printer.setHeight(24);
        printer.print(panel, new Point(0, 0));

        printer.setWidth(24);
        printer.print(panel, new Point(0, 0));

        printer.setWidth(48);
        printer.print(panel, new Point(0, 0));

        // Title #1
        final LabelBuilder labelBuilder = new LabelBuilder();
        labelBuilder.setPosition(1, 1);
        labelBuilder.setText("Component Library Test");

        Label label = labelBuilder.build();
        label.getString(0).setUnderlined(true);

        panel.addComponents(label);

        // Button
        final ButtonBuilder buttonBuilder = new ButtonBuilder();
        buttonBuilder.setPosition(1, 3);
        buttonBuilder.setText("<Click Me>");
        buttonBuilder.setOnClickFunction(() -> System.out.println("Clicked!"));

        panel.addComponents(buttonBuilder.build());

        // First Radio Button Group
        final RadioButtonGroup groupA = new RadioButtonGroup();

        final RadioButtonBuilder radioButtonBuilder = new RadioButtonBuilder();
        radioButtonBuilder.setPosition(1, 5);
        radioButtonBuilder.setGroup(groupA);
        radioButtonBuilder.setText("Group A, Option 1");

        panel.addComponents(radioButtonBuilder.build());


        radioButtonBuilder.setRowIndex(6);
        radioButtonBuilder.setGroup(groupA);
        radioButtonBuilder.setText("Group A, Option 2");

        panel.addComponents(radioButtonBuilder.build());


        // Second Radio Button Group
        final RadioButtonGroup groupB = new RadioButtonGroup();

        radioButtonBuilder.setRowIndex(8);
        radioButtonBuilder.setGroup(groupB);
        radioButtonBuilder.setText("Group B, Option 1");

        panel.addComponents(radioButtonBuilder.build());


        radioButtonBuilder.setRowIndex(9);
        radioButtonBuilder.setGroup(groupB);
        radioButtonBuilder.setText("Group B, Option 2");

        panel.addComponents(radioButtonBuilder.build());


        // Check Boxes
        final CheckBoxBuilder checkBoxBuilder = new CheckBoxBuilder();
        checkBoxBuilder.setPosition(1, 11);
        checkBoxBuilder.setText("Checkbox A");

        panel.addComponents(checkBoxBuilder.build());


        checkBoxBuilder.setRowIndex(12);
        checkBoxBuilder.setText("Checkbox B");

        panel.addComponents(checkBoxBuilder.build());

        // Fixed Width Text Field
        final TextFieldBuilder textFieldBuilder = new TextFieldBuilder();
        textFieldBuilder.setColumnIndex(1);
        textFieldBuilder.setRowIndex(14);
        textFieldBuilder.setWidth(20);

        panel.addComponents(textFieldBuilder.build());

        // Variable Width Text Field
        textFieldBuilder.setRowIndex(16);
        textFieldBuilder.setWidth(20);
        textFieldBuilder.setMaxHorizontalCharacters(40);

        panel.addComponents(textFieldBuilder.build());

        // Loading Bar
        final ProgressBarBuilder loadingBarBuilder = new ProgressBarBuilder();
        loadingBarBuilder.setPosition(1, 19);
        loadingBarBuilder.setWidth(20);

        final ProgressBar loadingBar = loadingBarBuilder.build();

        panel.addComponents(loadingBar);

        final Timer timer = new Timer(1000, e -> {
            int pct = loadingBar.getPercentComplete();

            if (pct < 100) {
                pct += 5;
            } else {
                pct = 0;
            }

            loadingBar.setPercentComplete(pct);
        });
        timer.start();




        // Title #2
        labelBuilder.setColumnIndex(24);
        labelBuilder.setRowIndex(1);
        labelBuilder.setText("TextArea Tests");

        label = labelBuilder.build();
        label.getString(0).setUnderlined(true);

        panel.addComponents(label);


        // Fixed Width & Height Text Area:
        labelBuilder.setRowIndex(3);
        labelBuilder.setText("Fixed Width & Height");

        label = labelBuilder.build();
        label.getString(0).setUnderlined(true);

        panel.addComponents(label);

        final TextAreaBuilder textAreaBuilder = new TextAreaBuilder();
        textAreaBuilder.setColumnIndex(24);
        textAreaBuilder.setRowIndex(4);
        textAreaBuilder.setWidth(23);
        textAreaBuilder.setHeight(3);
        textAreaBuilder.setMaxVerticalCharacters(3);

        panel.addComponents(textAreaBuilder.build());

        // Variable Width Text Area:
        labelBuilder.setRowIndex(8);
        labelBuilder.setText("Variable Width Only");

        label = labelBuilder.build();
        label.getString(0).setUnderlined(true);

        panel.addComponents(label);


        textAreaBuilder.setRowIndex(9);
        textAreaBuilder.setMaxHorizontalCharacters(40);

        panel.addComponents(textAreaBuilder.build());

        // Variable Height Text Area:
        labelBuilder.setRowIndex(13);
        labelBuilder.setText("Variable Height Only");

        label = labelBuilder.build();
        label.getString(0).setUnderlined(true);

        panel.addComponents(label);


        textAreaBuilder.setRowIndex(14);
        textAreaBuilder.setMaxHorizontalCharacters(23);
        textAreaBuilder.setMaxVerticalCharacters(6);

        panel.addComponents(textAreaBuilder.build());

        // Variable Width & HeightText Area:
        labelBuilder.setRowIndex(18);
        labelBuilder.setText("Variable Width & Height");

        label = labelBuilder.build();
        label.getString(0).setUnderlined(true);

        panel.addComponents(label);


        textAreaBuilder.setRowIndex(19);
        textAreaBuilder.setMaxVerticalCharacters(6);
        textAreaBuilder.setMaxHorizontalCharacters(40);

        panel.addComponents(textAreaBuilder.build());




        // Layer
        labelBuilder.setColumnIndex(50);
        labelBuilder.setRowIndex(1);
        labelBuilder.setText("Layer Test");
        panel.addComponents(labelBuilder.build());

        final LayerBuilder layerBuilder = new LayerBuilder();
        layerBuilder.setColumnIndex(50);
        layerBuilder.setRowIndex(2);
        layerBuilder.setWidth(23);
        layerBuilder.setHeight(10);
        final Layer layerA = layerBuilder.build();
        for (final AsciiString string : layerA.getStrings()) {
            string.setBackgroundColor(new Color(255, 0, 0, 255));
        }

        layerBuilder.setColumnIndex(50);
        layerBuilder.setRowIndex(8);
        layerBuilder.setWidth(23);
        layerBuilder.setHeight(10);
        final Layer layerB = layerBuilder.build();
        for (final AsciiString string : layerB.getStrings()) {
            string.setBackgroundColor(new Color(0, 0, 255, 155));
        }

        panel.addComponents(layerA, layerB);


        panel.draw();
    }
}
