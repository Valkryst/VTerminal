package com.valkryst.VTerminal.example.view;

import com.valkryst.VTerminal.component.VPanel;

import java.beans.PropertyChangeEvent;
import java.util.concurrent.ThreadLocalRandom;

public class PanelView extends View {
	public PanelView() {
		final var component = new VPanel(64, 32);
		for (int y = 0 ; y < component.getHeightInTiles() ; y++) {
			for (int x = 0 ; x < component.getWidthInTiles() ; x++) {
				component.setCodePointAt(x, y, ThreadLocalRandom.current().nextInt(1000));
			}
		}

		this.add(component);
	}

	@Override
	public void modelPropertyChange(final PropertyChangeEvent event) {
	}
}
