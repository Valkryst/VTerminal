package com.valkryst.VTerminal.samples;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.builder.component.*;
import com.valkryst.VTerminal.component.Label;
import com.valkryst.VTerminal.component.ProgressBar;
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
        printer.print(panel, 0, 0);

        printer.setWidth(24);
        printer.print(panel, 0, 0);

        printer.setWidth(48);
        printer.print(panel, 0, 0);

        // Title #1
        final LabelBuilder labelBuilder = new LabelBuilder();
        labelBuilder.setColumnIndex(1);
        labelBuilder.setRowIndex(1);
        labelBuilder.setText("Component Library Test");

        Label label = labelBuilder.build();
        label.getString(0).setUnderlined(true);

        panel.addComponent(label);

        // Button
        final ButtonBuilder buttonBuilder = new ButtonBuilder();
        buttonBuilder.setRadio(panel.getRadio());
        buttonBuilder.setColumnIndex(1);
        buttonBuilder.setRowIndex(3);
        buttonBuilder.setText("Click Me");
        buttonBuilder.setOnClickFunction(() -> System.out.println("Clicked!"));

        panel.addComponent(buttonBuilder.build());

        // First Radio Button Group
        final RadioButtonGroup groupA = new RadioButtonGroup();

        final RadioButtonBuilder radioButtonBuilder = new RadioButtonBuilder();
        radioButtonBuilder.setRadio(panel.getRadio());
        radioButtonBuilder.setColumnIndex(1);
        radioButtonBuilder.setRowIndex(5);
        radioButtonBuilder.setGroup(groupA);
        radioButtonBuilder.setText("Group A, Option 1");

        panel.addComponent(radioButtonBuilder.build());


        radioButtonBuilder.setRowIndex(6);
        radioButtonBuilder.setGroup(groupA);
        radioButtonBuilder.setText("Group A, Option 2");

        panel.addComponent(radioButtonBuilder.build());


        // Second Radio Button Group
        final RadioButtonGroup groupB = new RadioButtonGroup();

        radioButtonBuilder.setRowIndex(8);
        radioButtonBuilder.setGroup(groupB);
        radioButtonBuilder.setText("Group B, Option 1");

        panel.addComponent(radioButtonBuilder.build());


        radioButtonBuilder.setRowIndex(9);
        radioButtonBuilder.setGroup(groupB);
        radioButtonBuilder.setText("Group B, Option 2");

        panel.addComponent(radioButtonBuilder.build());


        // Check Boxes
        final CheckBoxBuilder checkBoxBuilder = new CheckBoxBuilder();
        checkBoxBuilder.setRadio(panel.getRadio());
        checkBoxBuilder.setColumnIndex(1);
        checkBoxBuilder.setRowIndex(11);
        checkBoxBuilder.setText("Checkbox A");

        panel.addComponent(checkBoxBuilder.build());


        checkBoxBuilder.setRowIndex(12);
        checkBoxBuilder.setText("Checkbox B");

        panel.addComponent(checkBoxBuilder.build());

        // Fixed Width Text Field
        final TextFieldBuilder textFieldBuilder = new TextFieldBuilder();
        textFieldBuilder.setRadio(panel.getRadio());
        textFieldBuilder.setColumnIndex(1);
        textFieldBuilder.setRowIndex(14);
        textFieldBuilder.setWidth(20);

        panel.addComponent(textFieldBuilder.build());

        // Variable Width Text Field
        textFieldBuilder.setRowIndex(16);
        textFieldBuilder.setWidth(20);
        textFieldBuilder.setMaxCharacters(40);

        panel.addComponent(textFieldBuilder.build());




        // Title #2
        labelBuilder.setColumnIndex(24);
        labelBuilder.setRowIndex(1);
        labelBuilder.setText("TextArea Tests");

        label = labelBuilder.build();
        label.getString(0).setUnderlined(true);

        panel.addComponent(label);


        // Fixed Width & Height Text Area:
        labelBuilder.setRowIndex(3);
        labelBuilder.setText("Fixed Width & Height");

        label = labelBuilder.build();
        label.getString(0).setUnderlined(true);

        panel.addComponent(label);

        final TextAreaBuilder textAreaBuilder = new TextAreaBuilder();
        textAreaBuilder.setRadio(panel.getRadio());
        textAreaBuilder.setColumnIndex(24);
        textAreaBuilder.setRowIndex(4);
        textAreaBuilder.setWidth(23);
        textAreaBuilder.setHeight(3);
        textAreaBuilder.setMaxVerticalCharacters(3);

        panel.addComponent(textAreaBuilder.build());

        // Variable Width Text Area:
        labelBuilder.setRowIndex(8);
        labelBuilder.setText("Variable Width Only");

        label = labelBuilder.build();
        label.getString(0).setUnderlined(true);

        panel.addComponent(label);


        textAreaBuilder.setRowIndex(9);
        textAreaBuilder.setMaxHorizontalCharacters(40);

        panel.addComponent(textAreaBuilder.build());

        // Variable Height Text Area:
        labelBuilder.setRowIndex(13);
        labelBuilder.setText("Variable Height Only");

        label = labelBuilder.build();
        label.getString(0).setUnderlined(true);

        panel.addComponent(label);


        textAreaBuilder.setRowIndex(14);
        textAreaBuilder.setMaxHorizontalCharacters(23);
        textAreaBuilder.setMaxVerticalCharacters(6);

        panel.addComponent(textAreaBuilder.build());

        // Variable Width & HeightText Area:
        labelBuilder.setRowIndex(18);
        labelBuilder.setText("Variable Width & Height");

        label = labelBuilder.build();
        label.getString(0).setUnderlined(true);

        panel.addComponent(label);


        textAreaBuilder.setRowIndex(19);
        textAreaBuilder.setMaxVerticalCharacters(6);
        textAreaBuilder.setMaxHorizontalCharacters(40);

        panel.addComponent(textAreaBuilder.build());

        // Loading Bar
        final ProgressBarBuilder loadingBarBuilder = new ProgressBarBuilder();
        loadingBarBuilder.setRadio(panel.getRadio());
        loadingBarBuilder.setColumnIndex(1);
        loadingBarBuilder.setRowIndex(19);
        loadingBarBuilder.setWidth(20);

        final ProgressBar loadingBar = loadingBarBuilder.build();

        panel.addComponent(loadingBar);

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

        panel.draw();
    }
}
