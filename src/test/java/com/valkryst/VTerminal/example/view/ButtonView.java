package com.valkryst.VTerminal.example.view;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

public class ButtonView extends View {
	public ButtonView() {
		var component = new JButton("[This JButton is enabled.]");
		component.setFocusable(true);
		this.add(component);

		component = new JButton("[This JButton is disabled.]");
		component.setEnabled(false);
		this.add(component);
	}

	@Override
	public void modelPropertyChange(final PropertyChangeEvent event) {
	}
}
