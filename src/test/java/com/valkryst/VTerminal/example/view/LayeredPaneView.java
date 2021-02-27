package com.valkryst.VTerminal.example.view;

import com.valkryst.VTerminal.component.VPanel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.util.concurrent.ThreadLocalRandom;

public class LayeredPaneView extends View {
	public LayeredPaneView() {
		final var layeredPane = new JLayeredPane();
		layeredPane.setLayout(new OverlayLayout(layeredPane));

		var panel = new VPanel(64, 32);
		panel.setBackground(new Color(0, 0, 255, 125));
		panel.setOpaque(false);
		for (int y = 0 ; y < panel.getHeightInTiles() ; y++) {
			for (int x = 0 ; x < panel.getWidthInTiles() ; x++) {
				panel.setCodePointAt(x, y, ThreadLocalRandom.current().nextInt(255));
			}
		}
		layeredPane.add(panel, Integer.valueOf(0));

		panel = new VPanel(64, 32);
		panel.setBackground(new Color(255, 0, 0, 125));
		panel.setOpaque(false);
		for (int y = 0 ; y < panel.getHeightInTiles() ; y++) {
			for (int x = 0 ; x < panel.getWidthInTiles() ; x++) {
				panel.setCodePointAt(x, y, ThreadLocalRandom.current().nextInt(255));
			}
		}
		layeredPane.add(panel, Integer.valueOf(1));

		this.add(layeredPane);
	}

	@Override
	public void modelPropertyChange(final PropertyChangeEvent event) {
	}
}
