package layering_a_screen;

import com.valkryst.VTerminal.component.VPanel;
import com.valkryst.VTerminal.plaf.VTerminalLookAndFeel;

import javax.swing.*;

public class ExampleB {
	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(VTerminalLookAndFeel.getInstance(24));
		} catch (final UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		SwingUtilities.invokeLater(() -> {
			final var layeredPane = new JLayeredPane();
			layeredPane.setLayout(new OverlayLayout(layeredPane));

			final var bottomPanel = new VPanel(40, 20);
			layeredPane.add(bottomPanel, Integer.valueOf(0));

			final var topPanel = new VPanel(40, 20);
			layeredPane.add(topPanel, Integer.valueOf(0));
		});
	}
}
