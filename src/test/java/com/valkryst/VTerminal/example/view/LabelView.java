package com.valkryst.VTerminal.example.view;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

public class LabelView extends View {
	public LabelView() {
		var component = new JLabel("This JLabel is enabled.");
		this.add(component);

		component = new JLabel("This JLabel is disabled.");
		component.setEnabled(false);
		this.add(component);
	}

	@Override
	public void modelPropertyChange(final PropertyChangeEvent event) {
	}
}
