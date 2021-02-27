package com.valkryst.VTerminal.example.controller;

import com.valkryst.VTerminal.example.Display;
import com.valkryst.VTerminal.example.view.*;

import java.awt.*;

public class DefaultController extends Controller {
	public void displayMainView() {
		Display.getInstance().setLayout(new BorderLayout());

		final var tabbedPaneView = new TabbedPaneView();
		this.addView(tabbedPaneView, BorderLayout.CENTER);

		tabbedPaneView.addTab("Button", new ButtonView());
		tabbedPaneView.addTab("Check Box", new CheckBoxView());
		tabbedPaneView.addTab("Editor Pane", new EditorPaneView());
		tabbedPaneView.addTab("Filtered VPanel", new FilteredPanelView());
		tabbedPaneView.addTab("Label", new LabelView());
		tabbedPaneView.addTab("Layered Pane", new LayeredPaneView());
		tabbedPaneView.addTab("Password Field", new PasswordFieldView());
		tabbedPaneView.addTab("Progress Bar", new ProgressBarView());
		tabbedPaneView.addTab("Radio Button", new RadioButtonView());
		tabbedPaneView.addTab("Scroll Pane", new ScrollPaneView());
		tabbedPaneView.addTab("Text Area", new TextAreaView());
		tabbedPaneView.addTab("Text Field", new TextFieldView());
		tabbedPaneView.addTab("Text Pane", new TextPaneView());
		tabbedPaneView.addTab("VPanel", new PanelView());
	}
}
