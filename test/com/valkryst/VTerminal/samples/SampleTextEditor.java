package com.valkryst.VTerminal.samples;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.builder.component.ButtonBuilder;
import com.valkryst.VTerminal.builder.component.TextAreaBuilder;
import com.valkryst.VTerminal.component.TextArea;
import com.valkryst.VTerminal.font.Font;
import com.valkryst.VTerminal.font.FontLoader;

import javax.swing.JFileChooser;
import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;

public class SampleTextEditor {
    public static void main(final String[] args) throws IOException, URISyntaxException, InterruptedException {
        final Font font = FontLoader.loadFontFromJar("Fonts/DejaVu Sans Mono/18pt/bitmap.png", "Fonts/DejaVu Sans Mono/18pt/data.fnt", 1);

        final PanelBuilder builder = new PanelBuilder();
        builder.setFont(font);

        final Panel panel = builder.build();

        Thread.sleep(100);

        panel.getScreen().setBackgroundColor(new Color(0xFF366C9F, true));

        final TextAreaBuilder textAreaBuilder = new TextAreaBuilder();
        textAreaBuilder.setRadio(panel.getRadio());
        textAreaBuilder.setColumnIndex(0);
        textAreaBuilder.setRowIndex(1);
        textAreaBuilder.setWidth(80);
        textAreaBuilder.setHeight(23);
        textAreaBuilder.setMaxVerticalCharacters(23);
        final TextArea textArea = textAreaBuilder.build();
        panel.addComponent(textArea);

        final ButtonBuilder buttonBuilder = new ButtonBuilder();
        buttonBuilder.setRadio(panel.getRadio());
        buttonBuilder.setColumnIndex(0);
        buttonBuilder.setRowIndex(0);
        buttonBuilder.setText("New");
        buttonBuilder.setOnClickFunction(() -> {
            textArea.clearText();
            textArea.moveCaretToStartOfLine();
            textArea.moveCaretToFirstLine();
        });
        panel.addComponent(buttonBuilder.build());


        buttonBuilder.setColumnIndex(5);
        buttonBuilder.setText("Save");
        buttonBuilder.setOnClickFunction(() -> {
            final JFileChooser fileChooser = new JFileChooser();
            fileChooser.setApproveButtonText("Save");
            fileChooser.setDragEnabled(false);
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

            int returnVal = fileChooser.showSaveDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                final File file = fileChooser.getSelectedFile();

                try (final PrintWriter printWriter = new PrintWriter(new FileWriter(file))) {
                    for (final String string : textArea.getText()) {
                        printWriter.append(string.replaceAll("^\\s+$", ""));
                        printWriter.append("\n");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        panel.addComponent(buttonBuilder.build());

        panel.draw();
    }
}
