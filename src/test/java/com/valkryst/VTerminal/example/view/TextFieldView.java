package com.valkryst.VTerminal.example.view;

import com.valkryst.VTerminal.component.VTextField;

import java.beans.PropertyChangeEvent;

public class TextFieldView extends View {
	public TextFieldView() {
		var component = new VTextField();
		component.setText("This VTextField is enabled.");
		this.add(component);

		component = new VTextField();
		component.setEnabled(false);
		component.setText("This VTextField is disabled.");
		this.add(component);
	}

	@Override
	public void modelPropertyChange(final PropertyChangeEvent event) {

	}
}
