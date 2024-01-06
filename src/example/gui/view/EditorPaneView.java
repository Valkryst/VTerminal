package gui.view;

import com.valkryst.VTerminal.component.VEditorPane;

import java.awt.*;
import java.beans.PropertyChangeEvent;

public class EditorPaneView extends View {
	public EditorPaneView() {
		this.setLayout(new BorderLayout());

		var component = new VEditorPane();
		component.setContentType("text/html");
		component.setEditable(true);
		component.setText("<html>\n" +
                          "	<head>\n" +
                          "		<style type=\"text/css\">\n" +
                          "			i {\n" +
                          "				color: red;\n" +
                          "			}\n" +
                          "		</style>\n" +
                          "	</head>\n" +
                          "	<body>\n" +
                          "		<p>This <i>VEditorPane</i> is editable and <b>enabled</b>.</p>\n" +
                          "	</body>\n" +
                          "</html>\n");
		this.add(component, BorderLayout.NORTH);

		component = new VEditorPane();
		component.setContentType("text/html");
		component.setEditable(true);
		component.setEnabled(false);
		component.setText("<html>\n" +
                          "	<head>\n" +
                          "		<style type=\"text/css\">\n" +
                          "			i {\n" +
                          "				color: red;\n" +
                          "			}\n" +
                          "		</style>\n" +
                          "	</head>\n" +
                          "	<body>\n" +
                          "		<p>This <i>VEditorPane</i> is editable and <b>disabled</b>.</p>\n" +
                          "	</body>\n" +
                          "</html>\n");
		this.add(component, BorderLayout.SOUTH);
	}

	@Override
	public void modelPropertyChange(final PropertyChangeEvent event) {
	}
}
