package com.valkryst.VTerminal.example.view;

import com.valkryst.VTerminal.component.VPasswordField;

import java.beans.PropertyChangeEvent;

public class PasswordFieldView extends View {
	public PasswordFieldView() {
		var component = new VPasswordField();
		component.setText("This VPasswordField is enabled.");
		this.add(component);

		component = new VPasswordField();
		component.setEnabled(false);
		component.setText("This VPasswordField is disabled.");
		this.add(component);
	}

	@Override
	public void modelPropertyChange(final PropertyChangeEvent event) {

	}
}
