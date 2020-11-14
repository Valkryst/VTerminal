package com.valkryst.VTerminal.example.view;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

public class RadioButtonView extends View {
	public RadioButtonView() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		var radioButtonGroup = new ButtonGroup();
		var component = new JRadioButton("Enabled Radio Button A");
		component.setFocusable(true);
		radioButtonGroup.add(component);
		this.add(component);

		component = new JRadioButton("Enabled Radio Button B");
		component.setFocusable(true);
		radioButtonGroup.add(component);
		this.add(component);

		radioButtonGroup = new ButtonGroup();
		component = new JRadioButton("Disabled Radio Button A");
		component.setEnabled(false);
		radioButtonGroup.add(component);
		this.add(component);

		component = new JRadioButton("Disabled Radio Button B");
		component.setEnabled(false);
		radioButtonGroup.add(component);
		this.add(component);
	}

	@Override
	public void modelPropertyChange(final PropertyChangeEvent event) {

	}
}
