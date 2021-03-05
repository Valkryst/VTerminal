package gui.view;

import com.valkryst.VTerminal.component.VTextPane;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.beans.PropertyChangeEvent;

public class TextPaneView extends View {
	public TextPaneView() {
		var component = new VTextPane();
		component.setEditable(true);
		addColoredText(component, "This VTextPane is enabled.");
		this.add(component);

		component = new VTextPane();
		component.setEditable(true);
		component.setEnabled(false);
		addColoredText(component, "This VTextPane is disabled.");
		this.add(component);
	}

	@Override
	public void modelPropertyChange(final PropertyChangeEvent event) {
	}

	public static void addColoredText(JTextPane pane, String text) {
		StyledDocument doc = pane.getStyledDocument();

		Style style = pane.addStyle("Color Style", null);
		StyleConstants.setForeground(style, (Color) UIManager.get("TextPane.foreground"));
		try {
			doc.insertString(doc.getLength(), text, style);
		}
		catch (BadLocationException e) {
			e.printStackTrace();
		}
	}
}
