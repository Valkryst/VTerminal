package drawing_a_screen;

import com.valkryst.VTerminal.component.VFrame;
import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;

import javax.swing.*;
import java.awt.*;

public class ExampleB {
	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(VTerminalLookAndFeel.getInstance(24));
		} catch (final UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(() -> {
			final var frame = new VFrame(40, 20);
			frame.setVisible(true);
			frame.pack();
			frame.setLocationRelativeTo(null);

			final var panel = frame.getContentPane();
			panel.setCodePointAt(0, 0, 'A');
			panel.setBackgroundAt(0, 0, Color.MAGENTA);
		});
	}
}