package com.valkryst.VTerminal.example.view;

import javax.swing.*;
import java.beans.PropertyChangeEvent;

public class TabbedPaneView extends View {
	private final JTabbedPane tabbedPane;

	public TabbedPaneView() {
		tabbedPane = new JTabbedPane();
		this.add(tabbedPane);
	}

	@Override
	public void modelPropertyChange(final PropertyChangeEvent event) {
	}

	public void addTab(final String title, final View view) {
		tabbedPane.addTab(title, view);
	}
}
