package com.valkryst.VTerminal.samples;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.builder.component.*;
import com.valkryst.VTerminal.component.*;
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

        printer.setWidth(48);
        printer.print(panel, 0, 0);

        // Title #1
        final Label labelA = new LabelBuilder().setColumnAndRowIndices(1, 1)
                                               .setText("Component Library Test")
                                               .build();
        labelA.getString(0).setUnderlined(true);

        // Button
        final Button button = new ButtonBuilder().setColumnAndRowIndices(1, 3)
                                                 .setText("Click")
                                                 .setOnClickFunction(() -> System.out.println("Clicked!"))
                                                 .setRadio(panel)
                                                 .build();

        // First Radio Button Group
        final RadioButtonGroup groupA = new RadioButtonGroup();

        final RadioButton radioButtonAA = new RadioButtonBuilder().setColumnAndRowIndices(1, 5)
                                                                  .setGroup(groupA)
                                                                  .setText("Group A, Option 1")
                                                                  .setRadio(panel)
                                                                  .build();

        final RadioButton radioButtonAB = new RadioButtonBuilder().setColumnAndRowIndices(1, 6)
                                                                  .setGroup(groupA)
                                                                  .setText("Group A, Option 2")
                                                                  .setRadio(panel)
                                                                  .build();


        // Second Radio Button Group
        final RadioButtonGroup groupB = new RadioButtonGroup();

        final RadioButton radioButtonBA = new RadioButtonBuilder().setColumnAndRowIndices(1, 8)
                                                                  .setGroup(groupB)
                                                                  .setText("Group B, Option 1")
                                                                  .setRadio(panel)
                                                                  .build();

        final RadioButton radioButtonBB = new RadioButtonBuilder().setColumnAndRowIndices(1, 9)
                                                                  .setGroup(groupB)
                                                                  .setText("Group B, Option 2")
                                                                  .setRadio(panel)
                                                                  .build();

        // Check Boxes
        final CheckBox checkBoxA = new CheckBoxBuilder().setColumnAndRowIndices(1, 11)
                                                       .setText("Checkbox A")
                                                       .setRadio(panel)
                                                       .build();

        final CheckBox checkBoxB = new CheckBoxBuilder().setColumnAndRowIndices(1, 12)
                                                        .setText("Checkbox B")
                                                        .setRadio(panel)
                                                        .build();

        // Fixed Width Text Field
        final TextField textField_fixed = new TextFieldBuilder().setColumnAndRowIndices(1, 14)
                                                                .setWidth(20)
                                                                .setRadio(panel)
                                                                .build();

        // Variable Width Text Field
        final TextField textField_variable = new TextFieldBuilder().setColumnAndRowIndices(1, 16)
                                                                   .setWidth(20)
                                                                   .setMaxCharacters(40)
                                                                   .setRadio(panel)
                                                                   .build();




        // Title #2
        final Label labelB = new LabelBuilder().setColumnAndRowIndices(24, 1)
                                               .setText("TextArea Tests")
                                               .build();
        labelB.getString(0).setUnderlined(true);


        // Fixed Width & Height Text Area:
        final Label labelC = new LabelBuilder().setColumnAndRowIndices(24, 3)
                                               .setText("Fixed Width & Height")
                                               .build();
        labelC.getString(0).setUnderlined(true);

        final TextArea textArea_fixedWH = new TextAreaBuilder().setColumnAndRowIndices(24, 4)
                                                               .setWidth(23)
                                                               .setHeight(3)
                                                               .setMaxVerticalCharacters(3)
                                                               .setRadio(panel)
                                                               .build();

        // Variable Width Text Area:
        final Label labelD = new LabelBuilder().setColumnAndRowIndices(24, 8)
                                               .setText("Variable Width Only")
                                               .build();

        final TextArea textArea_vW = new TextAreaBuilder().setColumnAndRowIndices(24, 9)
                                                          .setWidth(23)
                                                          .setHeight(3)
                                                          .setMaxVerticalCharacters(3)
                                                          .setMaxHorizontalCharacters(40)
                                                          .setRadio(panel)
                                                          .build();

        // Variable Height Text Area:
        final Label labelE = new LabelBuilder().setColumnAndRowIndices(24, 13)
                                               .setText("Variable Height Only")
                                               .build();

        final TextArea textArea_vH = new TextAreaBuilder().setColumnAndRowIndices(24, 14)
                                                          .setWidth(23)
                                                          .setHeight(3)
                                                          .setMaxVerticalCharacters(3)
                                                          .setMaxVerticalCharacters(6)
                                                          .setRadio(panel)
                                                          .build();

        // Variable Width & HeightText Area:
        final Label labelF = new LabelBuilder().setColumnAndRowIndices(24, 18)
                                               .setText("Variable Width & Height")
                                               .build();

        final TextArea textArea_vWH = new TextAreaBuilder().setColumnAndRowIndices(24, 19)
                                                           .setWidth(23)
                                                           .setHeight(3)
                                                           .setMaxHorizontalCharacters(40)
                                                           .setMaxVerticalCharacters(6)
                                                           .setRadio(panel)
                                                           .build();

        // Loading Bar
        final LoadingBar loadingBar = new LoadingBarBuilder().setColumnAndRowIndices(1, 19)
                                                             .setWidth(20)
                                                             .setRadio(panel)
                                                             .build();

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




        // Add everything to panel:
        panel.addComponents(labelA, button, radioButtonAA, radioButtonAB, radioButtonBA, radioButtonBB, checkBoxA,
                            checkBoxB, textField_fixed, textField_variable, textArea_fixedWH, labelD, textArea_vW,
                            labelE, textArea_vH, labelF, textArea_vWH, loadingBar);

        panel.draw();
    }
}
