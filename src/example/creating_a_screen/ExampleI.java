package creating_a_screen;

import com.valkryst.VTerminal.component.VPanel;
import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ExampleI {
	public static void main(final String[] args) {
		try {
			final var file = new File("Font.ttf");
			final var fis = new FileInputStream(file);

			UIManager.setLookAndFeel(VTerminalLookAndFeel.getInstance(fis, 24));

			fis.close();
		} catch (final UnsupportedLookAndFeelException | IOException | FontFormatException e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(() -> {
			final var panel = new VPanel(40, 20);

			final var frame = new JFrame();
			frame.add(panel);
			frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

			frame.setVisible(true);
			frame.pack();
			frame.setLocationRelativeTo(null);

			panel.setCodePointAt(0, 0, 'A');
		});
	}
}
