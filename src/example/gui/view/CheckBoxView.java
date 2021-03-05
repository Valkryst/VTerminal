package gui.view;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

public class CheckBoxView extends View {
	public CheckBoxView() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		var component = new JCheckBox("Enabled Check Box");
		component.setFocusable(true);
		this.add(component);

		component = new JCheckBox("Disabled Check Box");
		component.setEnabled(false);
		this.add(component);
	}

	@Override
	public void modelPropertyChange(final PropertyChangeEvent event) {
	}
}
