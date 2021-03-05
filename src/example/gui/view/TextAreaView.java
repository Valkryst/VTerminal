package gui.view;

import com.valkryst.VTerminal.component.VTextArea;

import java.awt.*;
import java.beans.PropertyChangeEvent;

public class TextAreaView extends View {
	public TextAreaView() {
		this.setLayout(new GridLayout(2, 1, 16, 16));

		var component = new VTextArea(10, 32);
		component.setLineWrap(true);
		component.setText("This VTextArea is enabled.");
		this.add(component);

		component = new VTextArea(10, 32);
		component.setEnabled(false);
		component.setLineWrap(true);
		component.setText("This VTextArea is disabled.");
		this.add(component);
	}

	@Override
	public void modelPropertyChange(final PropertyChangeEvent event) {
	}
}
