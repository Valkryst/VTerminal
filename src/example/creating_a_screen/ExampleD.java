package creating_a_screen;

import com.valkryst.VTerminal.component.VFrame;
import com.valkryst.VTerminal.component.VPanel;
import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;

import javax.swing.*;

public class ExampleD {
	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(VTerminalLookAndFeel.getInstance());
		} catch (final UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(() -> {
			final var frame = new VFrame(new VPanel(40, 20));
			frame.setVisible(true);
			frame.pack();
			frame.setLocationRelativeTo(null);
		});
	}
}
