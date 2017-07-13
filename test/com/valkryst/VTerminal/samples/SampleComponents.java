package com.valkryst.VTerminal.samples;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.builder.component.*;
import com.valkryst.VTerminal.component.*;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;

import java.io.IOException;
import java.net.URISyntaxException;

public class SampleComponents {
    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException {
        System.setProperty("sun.java2d.opengl", "true");
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);
        final Panel panel = new PanelBuilder().setFont(font).build();

        Thread.sleep(100);

        panel.getScreen().clear('X');

        // Buttons
        final Button button_printout = new ButtonBuilder().setText("Printout Button").setPanel(panel).setColumnIndex(0).setRowIndex(0)
                                                          .setOnClickFunction(() -> System.out.println("You clicked a button.")).build();

        new ButtonBuilder().setText("Printout Button").setPanel(panel).setColumnIndex(0).setRowIndex(1)
                           .setOnClickFunction(() -> button_printout.getString(0).invertColors()).build();

        // Checkboxes:
        new CheckBoxBuilder().setText("Checkbox A").setPanel(panel).setColumnIndex(0).setRowIndex(2).build();
        new CheckBoxBuilder().setText("Checkbox B").setPanel(panel).setColumnIndex(0).setRowIndex(3).build();

        // Labels:
        new LabelBuilder().setText("Label A").setPanel(panel).setColumnIndex(0).setRowIndex(4).build();
        new LabelBuilder().setText("Label B").setPanel(panel).setColumnIndex(0).setRowIndex(5).build();

        // Loading Bar:
        final LoadingBar loadingBar = new LoadingBarBuilder().setPanel(panel).setColumnIndex(0).setRowIndex(6).build();

        // Radio Buttons:
        final RadioButtonGroup group = new RadioButtonGroup();
        new RadioButtonBuilder().setText("Radio Button A").setPanel(panel).setGroup(group).setColumnIndex(0).setRowIndex(7).build();
        new RadioButtonBuilder().setText("Radio Button A").setPanel(panel).setGroup(group).setColumnIndex(0).setRowIndex(8).build();

        // Text Fields:
        new TextFieldBuilder().setPanel(panel).setColumnIndex(0).setRowIndex(9).setWidth(6).build();
        new TextFieldBuilder().setPanel(panel).setColumnIndex(0).setRowIndex(10).setWidth(12).build();

        panel.draw();
    }
}
