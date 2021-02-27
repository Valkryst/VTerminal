package com.valkryst.VTerminal.example.view;

import com.valkryst.VTerminal.component.VPanel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.concurrent.ThreadLocalRandom;

public class ScrollPaneView extends View {
	public ScrollPaneView() {
		final var panel = new VPanel(250, 250);
		for (int y = 0 ; y < panel.getHeightInTiles() ; y++) {
			for (int x = 0 ; x < panel.getWidthInTiles() ; x++) {
				panel.setCodePointAt(x, y, ThreadLocalRandom.current().nextInt(255));
			}
		}

		final var scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(512, 512));
		scrollPane.setViewportView(panel);
		scrollPane.setFocusable(true);
		this.add(scrollPane, BorderLayout.CENTER);
	}

	@Override
	public void modelPropertyChange(final PropertyChangeEvent event) {
	}
}
