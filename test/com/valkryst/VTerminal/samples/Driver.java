package com.valkryst.VTerminal.samples;

import com.valkryst.VTerminal.Panel;
import com.valkryst.VTerminal.builder.PanelBuilder;
import com.valkryst.VTerminal.builder.component.RadioButtonBuilder;
import com.valkryst.VTerminal.component.RadioButtonGroup;

import java.io.IOException;
import java.net.URISyntaxException;

public class Driver {
    public static void main(final String[] args) throws IOException, URISyntaxException {
        final Panel panel = new PanelBuilder().build();

        final RadioButtonBuilder radioButtonBuilder = new RadioButtonBuilder();

        // Create the first group of RadioButtons:
        final RadioButtonGroup groupA = new RadioButtonGroup();

        radioButtonBuilder.setPosition(10, 10);
        radioButtonBuilder.setGroup(groupA);
        radioButtonBuilder.setText("Group A, Option #1");
        panel.addComponents(radioButtonBuilder.build());

        radioButtonBuilder.setPosition(10, 11);
        radioButtonBuilder.setText("Group A, Option #2");
        panel.addComponents(radioButtonBuilder.build());


        // Create the second group of Radio Buttons:
        final RadioButtonGroup groupB = new RadioButtonGroup();

        radioButtonBuilder.setPosition(10, 13);
        radioButtonBuilder.setGroup(groupB);
        radioButtonBuilder.setText("Group B, Option #1");
        panel.addComponents(radioButtonBuilder.build());

        radioButtonBuilder.setPosition(10, 14);
        radioButtonBuilder.setText("Group B, Option #2");
        panel.addComponents(radioButtonBuilder.build());

        panel.draw();
    }
}