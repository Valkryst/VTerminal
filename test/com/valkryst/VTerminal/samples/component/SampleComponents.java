package com.valkryst.VTerminal.samples.component;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.builder.component.*;
import com.valkryst.VTerminal.component.ProgressBar;
import com.valkryst.VTerminal.component.RadioButtonGroup;
import com.valkryst.VTerminal.printer.RectanglePrinter;
import com.valkryst.VTerminal.printer.RectangleType;

import javax.swing.Timer;
import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.net.URISyntaxException;

public class SampleComponents {
    public static void main(final String[] args) throws IOException, URISyntaxException {
        final Panel panel = new PanelBuilder().build();

        panel.getScreen().setBackgroundColor(new Color(45, 45, 45, 255));


        // Declare Builders & Printers:
        final ButtonBuilder buttonBuilder = new ButtonBuilder();
        final CheckBoxBuilder checkBoxBuilder = new CheckBoxBuilder();
        final ProgressBarBuilder loadingBarBuilder = new ProgressBarBuilder();
        final RadioButtonBuilder radioButtonBuilder = new RadioButtonBuilder();
        final LabelBuilder labelBuilder = new LabelBuilder();
        final LayerBuilder layerBuilder = new LayerBuilder();
        final TextFieldBuilder textFieldBuilder = new TextFieldBuilder();

        final RectanglePrinter printer = new RectanglePrinter();


        // Print the main and section borders:
        printer.setRectangleType(RectangleType.HEAVY);
        printer.setWidth(80);
        printer.setHeight(24);
        printer.print(panel, new Point(0, 0));

        printer.setWidth(24);
        printer.print(panel, new Point(0, 0));

        printer.setWidth(48);
        printer.print(panel, new Point(0, 0));

        // Title #1
        labelBuilder.setPosition(1, 1);
        labelBuilder.setText("Component Library Test");
        labelBuilder.setUnderlined(true);

        panel.addComponents(labelBuilder.build());

        // Button
        buttonBuilder.setPosition(1, 3);
        buttonBuilder.setText("<Click Me>");
        buttonBuilder.setOnClickFunction(() -> System.out.println("Clicked!"));

        panel.addComponents(buttonBuilder.build());

        // First Radio Button Group
        final RadioButtonGroup groupA = new RadioButtonGroup();

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
        checkBoxBuilder.setPosition(1, 11);
        checkBoxBuilder.setText("Checkbox A");
        panel.addComponents(checkBoxBuilder.build());


        checkBoxBuilder.setRowIndex(12);
        checkBoxBuilder.setText("Checkbox B");
        panel.addComponents(checkBoxBuilder.build());

        // Fixed Width Text Field
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
        panel.addComponents(labelBuilder.build());


        // Fixed Width & Height Text Area:
        labelBuilder.setRowIndex(3);
        labelBuilder.setText("Fixed Width & Height");
        panel.addComponents(labelBuilder.build());

        final TextAreaBuilder textAreaBuilder = new TextAreaBuilder();
        textAreaBuilder.setColumnIndex(24);
        textAreaBuilder.setRowIndex(4);
        textAreaBuilder.setDimensions(23, 3);
        textAreaBuilder.setMaxVerticalCharacters(3);

        panel.addComponents(textAreaBuilder.build());

        // Variable Width Text Area:
        labelBuilder.setRowIndex(8);
        labelBuilder.setText("Variable Width Only");
        panel.addComponents(labelBuilder.build());


        textAreaBuilder.setRowIndex(9);
        textAreaBuilder.setMaxHorizontalCharacters(40);

        panel.addComponents(textAreaBuilder.build());

        // Variable Height Text Area:
        labelBuilder.setRowIndex(13);
        labelBuilder.setText("Variable Height Only");
        panel.addComponents(labelBuilder.build());


        textAreaBuilder.setRowIndex(14);
        textAreaBuilder.setMaxHorizontalCharacters(23);
        textAreaBuilder.setMaxVerticalCharacters(6);

        panel.addComponents(textAreaBuilder.build());

        // Variable Width & HeightText Area:
        labelBuilder.setRowIndex(18);
        labelBuilder.setText("Variable Width & Height");
        panel.addComponents(labelBuilder.build());


        textAreaBuilder.setRowIndex(19);
        textAreaBuilder.setMaxVerticalCharacters(6);
        textAreaBuilder.setMaxHorizontalCharacters(40);
        panel.addComponents(textAreaBuilder.build());




        // Add layer section title:
        labelBuilder.setPosition(50, 1);
        labelBuilder.setText("Layer Test");
        panel.addComponents(labelBuilder.build());

        // Construct layers:
        layerBuilder.setColumnIndex(50);
        layerBuilder.setRowIndex(2);
        layerBuilder.setDimensions(23, 10);
        layerBuilder.setBackgroundColor(Color.RED);
        panel.addComponents(layerBuilder.build());

        layerBuilder.setRowIndex(8);
        layerBuilder.setBackgroundColor(new Color(0, 0, 255, 155));
        panel.addComponents(layerBuilder.build());


        panel.draw();
    }
}
